package com.ulticraft.stability;

import org.bukkit.ChatColor;

public class Analyzer
{
	private ActionController ac;
	private ConfigurationFile config;
	private boolean needsChunkGC;
	private boolean needsCulling;
	private boolean needsGC;
	private int lagging;
	
	public Analyzer(final ActionController ac, final ConfigurationFile config)
	{
		this.ac = ac;
		this.config = config;
		this.needsChunkGC = false;
		this.needsCulling = false;
		this.needsGC = false;
		this.lagging = 0;
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
}
