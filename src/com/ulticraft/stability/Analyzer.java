package com.ulticraft.stability;

import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.block.BlockRedstoneEvent;

public class Analyzer
{
	private ActionController ac;
	private ConfigurationFile config;
	private HashMap<Location, Integer> rhits;
	private HashMap<Location, Material> removed;
	private boolean needsChunkGC;
	private boolean needsCulling;
	private boolean needsGC;
	private boolean needsRedstoneClock;
	private int lagging;
	private int clockScan;
	private boolean clockScanning;
	private int acted;
	private ActionHistory ah;
	private int chunkChange;
	private int chunkState;
	private boolean didChunk;

	public Analyzer(ActionController ac, ConfigurationFile config, ActionHistory ah)
	{
		this.ac = ac;
		this.config = config;
		this.needsChunkGC = false;
		this.needsCulling = false;
		this.needsGC = false;
		this.needsRedstoneClock = false;
		this.lagging = 0;
		this.clockScan = 0;
		this.clockScanning = false;
		this.rhits = new HashMap<Location, Integer>();
		this.removed = new HashMap<Location, Material>();
		this.acted = 30;
		this.ah = ah;
		this.chunkChange = 0;
		this.didChunk = false;
		this.chunkState = 0;
	}

	public String analyze(Sample sample, SampleArray sampleArray)
	{
		acted--;

		String action = "";
		if(sample.getFreeMemory() / 1024.0 / 1024.0 / (Runtime.getRuntime().maxMemory() / 1024L / 1024L) < this.config.getThresholdMem())
		{
			needsChunkGC = true;
		}

		if(sample.getTps() < this.config.getThresholdTps())
		{
			if(sample.getTps() == -20)
			{

			}

			else
			{
				if(acted > 0)
				{
					return "";
				}

				needsCulling = true;
				needsChunkGC = true;
			}
		}

		if(sample.getGenerations() > config.getMaxChunkOverload())
		{
			action = ChatColor.DARK_RED + "Chunk Overflow";

			needsChunkGC = true;
		}

		if(needsChunkGC)
		{
			if(sample.getChunksLoaded() < 600)
			{
				return "";
			}
			
			if(chunkChange > 0)
			{
				chunkChange--;
				return "";
			}
			
			if(didChunk)
			{
				didChunk = false;
				if(sample.getChunksLoaded() - chunkState < 256)
				{
					chunkChange = 32;
					chunkState = 0;
					return ChatColor.RED + "Prevented ICR";
				}
			}
			
			chunkState = sample.getChunksLoaded();
			
			ac.syncPurge();
			action = ChatColor.RED + "Releasing Chunks";
			didChunk = true;

			acted += 2;
			
			ah.act(ChatColor.RED + "CHUNK-PURGE");

			needsGC = true;
			needsChunkGC = false;
		}

		else if(needsCulling)
		{
			final int[] culled = this.ac.cullMobsPerChunk(config.getMobThresholdCull(), config.getMobPeacefulThresholdCull(), config.getMobHostileThresholdCull());
			if(culled[0] + culled[1] > 0)
			{
				if(acted > 0)
				{
					return "";
				}

				ah.act(ChatColor.GREEN + "MOB-CULL");

				action = ChatColor.GREEN + "Culled " + (culled[0] + culled[1]) + " mobs.";
				acted++;
			}

			needsCulling = false;
		}

		else if(needsGC)
		{
			if(sample.getFreeMemory() / 1024.0 / 1024.0 < config.getChunkGcRam())
			{
				if(acted > 0)
				{
					return "";
				}

				action = ChatColor.GOLD + "Collecting Garbage";
				System.gc();

				ah.act(ChatColor.GOLD + "GARBAGE-COL");

				acted += 2;
			}

			needsGC = false;
		}

		else if(needsRedstoneClock)
		{
			clockScan++;

			action = ChatColor.RED + "Sniffing for Clocks...";
			clockScanning = true;

			if(clockScan == 2)
			{
				int dm = 0;

				for(Location i : rhits.keySet())
				{
					if(rhits.get(i) >= 10)
					{
						removed.put(i.getBlock().getLocation(), i.getBlock().getType());

						i.getBlock().setType(Material.AIR);
						dm++;
					}
				}

				action = ChatColor.RED + "Breaking " + dm + " Clocks";
			}

			if(clockScan == 3)
			{
				for(Location i : removed.keySet())
				{
					i.getBlock().setType(removed.get(i));
				}

				action = ChatColor.RED + "Disabled " + removed.keySet().size() + " Clocks";
				ah.act(ChatColor.DARK_RED + "BRK-CLOCK (player)");

				clockScan = 0;
				clockScanning = false;
				needsRedstoneClock = false;
				rhits = new HashMap<Location, Integer>();
				removed = new HashMap<Location, Material>();
			}
		}

		if(needsCulling || needsChunkGC || needsGC)
		{
			lagging++;
		}

		else
		{
			lagging = 0;
		}

		return action;
	}

	public void requestCull()
	{
		needsCulling = true;
	}

	public void requestChunkGC()
	{
		needsChunkGC = true;
	}

	public void requestGC()
	{
		needsGC = true;
	}

	public int getLag()
	{
		return lagging;
	}

	public void requestUnclock()
	{
		needsRedstoneClock = true;
	}

	public void onRedstone(BlockRedstoneEvent e)
	{
		if(clockScanning)
		{
			if(rhits.containsKey(e.getBlock().getLocation()))
			{
				rhits.put(e.getBlock().getLocation(), rhits.get(e.getBlock().getLocation()) + 1);
			}

			else
			{
				rhits.put(e.getBlock().getLocation(), 1);
			}
		}
	}
}
