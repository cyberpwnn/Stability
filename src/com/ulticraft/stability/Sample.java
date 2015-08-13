package com.ulticraft.stability;

public class Sample
{
	private double tps;
	private double freeMemory;
	private double maxMemory;
	private int chunksLoaded;
	private int playersOnline;
	private int totalEntities;
	private int livingEntities;
	private int dropEntities;
	private int generations;
	private int redstoneClocks;
	private long upTime;
	private boolean sample = false;

	public Sample(final double tps, final double freeMemory, double maxMemory, final int chunksLoaded, final int playersOnline, final int totalEntities, final int livingEntities, final int dropEntities, int generations, int redstoneClocks, final long upTime)
	{
		this.tps = tps;
		this.freeMemory = freeMemory;
		this.maxMemory = maxMemory;
		this.chunksLoaded = chunksLoaded;
		this.playersOnline = playersOnline;
		this.totalEntities = totalEntities;
		this.livingEntities = livingEntities;
		this.dropEntities = dropEntities;
		this.generations = generations;
		this.redstoneClocks = redstoneClocks;
		this.upTime = upTime;
		sample = true;
	}
	
	public boolean isSample()
	{
		return sample;
	}

	public double getTps()
	{
		return this.tps;
	}

	public double getFreeMemory()
	{
		return this.freeMemory;
	}
	
	public double getMaxMemory()
	{
		return this.maxMemory;
	}

	public int getChunksLoaded()
	{
		return this.chunksLoaded;
	}

	public int getPlayersOnline()
	{
		return this.playersOnline;
	}

	public int getTotalEntities()
	{
		return this.totalEntities;
	}

	public int getLivingEntities()
	{
		return this.livingEntities;
	}

	public int getDropEntities()
	{
		return this.dropEntities;
	}
	
	public int getGenerations()
	{
		return this.generations;
	}

	public long getUptime()
	{
		return this.upTime;
	}
	
	public int getRedstoneClocks()
	{
		return redstoneClocks;
	}
}
