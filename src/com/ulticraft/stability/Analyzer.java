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
	
	public Analyzer(ActionController ac, ConfigurationFile config)
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
	}

	public String analyze(Sample sample, SampleArray sampleArray)
	{
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
			ac.syncPurge();
			action = ChatColor.RED + "Releasing Chunks";
			
			needsGC = true;
			needsChunkGC = false;
		}
		
		else if(needsCulling)
		{
			final int[] culled = this.ac.cullMobsPerChunk(config.getMobThresholdCull(), config.getMobPeacefulThresholdCull(), config.getMobHostileThresholdCull());
			if(culled[0] + culled[1] > 0)
			{
				action = ChatColor.GREEN + "Culled " + (culled[0] + culled[1]) + " mobs.";
			}
			
			needsCulling = false;
		}
		
		else if(needsGC)
		{
			if(sample.getFreeMemory() / 1024.0 / 1024.0 < config.getChunkGcRam())
			{
				action = ChatColor.GOLD + "Collecting Garbage";
				System.gc();
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
