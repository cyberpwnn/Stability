package com.ulticraft.core;

import org.bukkit.Location;

public class Region
{
	private Location point1;
	private Location point2;
	
	private int width;
	private int depth;
	private int height;
	private int size;
	
	public Region(Location point1, Location point2)
	{
		int minX = Math.min(point1.getBlockX(), point2.getBlockX());
		int minY = Math.min(point1.getBlockY(), point2.getBlockY());
		int minZ = Math.min(point1.getBlockZ(), point2.getBlockZ());
		int maxX = Math.max(point1.getBlockX(), point2.getBlockX());
		int maxY = Math.max(point1.getBlockY(), point2.getBlockY());
		int maxZ = Math.max(point1.getBlockZ(), point2.getBlockZ());
		
		this.point1 = point1;
		this.point2 = point2;
		this.width = maxX - minX;
		this.depth = maxZ - minZ;
		this.height = maxY - minY;
		this.size = width * depth * height;
	}

	public Location getPoint1()
	{
		return point1;
	}

	public Location getPoint2()
	{
		return point2;
	}

	public int getWidth()
	{
		return width;
	}

	public int getDepth()
	{
		return depth;
	}

	public int getHeight()
	{
		return height;
	}

	public int getSize()
	{
		return size;
	}

	public void setPoint1(Location point1)
	{
		this.point1 = point1;
		
		int minX = Math.min(point1.getBlockX(), point2.getBlockX());
		int minY = Math.min(point1.getBlockY(), point2.getBlockY());
		int minZ = Math.min(point1.getBlockZ(), point2.getBlockZ());
		int maxX = Math.max(point1.getBlockX(), point2.getBlockX());
		int maxY = Math.max(point1.getBlockY(), point2.getBlockY());
		int maxZ = Math.max(point1.getBlockZ(), point2.getBlockZ());
		
		this.width = maxX - minX;
		this.depth = maxZ - minZ;
		this.height = maxY - minY;
		this.size = width * depth * height;
	}

	public void setPoint2(Location point2)
	{
		this.point2 = point2;
		
		int minX = Math.min(point1.getBlockX(), point2.getBlockX());
		int minY = Math.min(point1.getBlockY(), point2.getBlockY());
		int minZ = Math.min(point1.getBlockZ(), point2.getBlockZ());
		int maxX = Math.max(point1.getBlockX(), point2.getBlockX());
		int maxY = Math.max(point1.getBlockY(), point2.getBlockY());
		int maxZ = Math.max(point1.getBlockZ(), point2.getBlockZ());
		
		this.width = maxX - minX;
		this.depth = maxZ - minZ;
		this.height = maxY - minY;
		this.size = width * depth * height;
	}
	
	public boolean contains(Location location)
	{
		int minX = Math.min(point1.getBlockX(), point2.getBlockX());
		int minY = Math.min(point1.getBlockY(), point2.getBlockY());
		int minZ = Math.min(point1.getBlockZ(), point2.getBlockZ());
		int maxX = Math.max(point1.getBlockX(), point2.getBlockX());
		int maxY = Math.max(point1.getBlockY(), point2.getBlockY());
		int maxZ = Math.max(point1.getBlockZ(), point2.getBlockZ());
		
		if(location.getBlockX() >= minX && location.getBlockX() <= maxX
		&& location.getBlockY() >= minY && location.getBlockY() <= maxY
		&& location.getBlockZ() >= minZ && location.getBlockZ() <= maxZ)
		{
			return true;
		}
		
		else
		{
			return false;
		}
	}
}
