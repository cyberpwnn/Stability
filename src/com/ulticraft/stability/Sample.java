package com.ulticraft.stability;

public class Sample
{
	private double tps;
	private double freeMemory;
	private double maxMemory;
	private int mos;
	private int gms;
	private int chunksLoaded;
	private int playersOnline;
	private int totalEntities;
	private int livingEntities;
	private int dropEntities;
	private int generations;
	private int redstoneClocks;
	private long upTime;
	private boolean sample = false;
	
	public Sample(final int mos, final int gms, final double tps, final double freeMemory, double maxMemory, final int chunksLoaded, final int playersOnline, final int totalEntities, final int livingEntities, final int dropEntities, int generations, int redstoneClocks, final long upTime)
	{
		this.tps = tps;
		this.freeMemory = freeMemory;
		this.mos = mos;
		this.gms = gms;
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
	
	public int getMos()
	{
		return mos;
	}
	
	public void setMos(int mos)
	{
		this.mos = mos;
	}
	
	public int getGms()
	{
		return gms;
	}
	
	public void setGms(int gms)
	{
		this.gms = gms;
	}
	
	public long getUpTime()
	{
		return upTime;
	}
	
	public void setUpTime(long upTime)
	{
		this.upTime = upTime;
	}
	
	public void setTps(double tps)
	{
		this.tps = tps;
	}
	
	public void setFreeMemory(double freeMemory)
	{
		this.freeMemory = freeMemory;
	}
	
	public void setMaxMemory(double maxMemory)
	{
		this.maxMemory = maxMemory;
	}
	
	public void setChunksLoaded(int chunksLoaded)
	{
		this.chunksLoaded = chunksLoaded;
	}
	
	public void setPlayersOnline(int playersOnline)
	{
		this.playersOnline = playersOnline;
	}
	
	public void setTotalEntities(int totalEntities)
	{
		this.totalEntities = totalEntities;
	}
	
	public void setLivingEntities(int livingEntities)
	{
		this.livingEntities = livingEntities;
	}
	
	public void setDropEntities(int dropEntities)
	{
		this.dropEntities = dropEntities;
	}
	
	public void setGenerations(int generations)
	{
		this.generations = generations;
	}
	
	public void setRedstoneClocks(int redstoneClocks)
	{
		this.redstoneClocks = redstoneClocks;
	}
	
	public void setSample(boolean sample)
	{
		this.sample = sample;
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
