package com.ulticraft.core;

import java.util.ArrayList;

public class MAPS implements Runnable
{
	public static ArrayList<Long> mp = new ArrayList<Long>();
	public static long MAPS = 0;
	public static int CGCOH = 0;
	public static int GCOH = 0;
	public static long CMEM = 0l;
	
	public static long memoryMax()
	{
		return Runtime.getRuntime().maxMemory() / 1024 / 1024;
	}
	
	public static long memoryUsed()
	{
		return Runtime.getRuntime().totalMemory() / 1024 / 1024;
	}
	
	public static long memoryFree()
	{
		return memoryMax() - (memoryUsed() - (Runtime.getRuntime().freeMemory() / 1024 / 1024));
	}
	
	public static long getMAPS()
	{
		if(mp.isEmpty())
		{
			return MAPS;
		}
		
		int m = 0;
		
		for(long i : mp)
		{
			m = (int) (m + i);
		}
		
		m /= mp.size();
		
		return m;
	}
	
	public static int getGCOH()
	{
		return GCOH;
	}
	
	public void run()
	{
		long MEMDIFF = (memoryFree() - CMEM);
		
		if(MEMDIFF < 0)
		{
			MAPS = -MEMDIFF;
			mp.add(MAPS);
			
			if(mp.size() > getGCOH())
			{
				mp.remove(0);
			}
		}
		
		else
		{
			GCOH = CGCOH;
			CGCOH = 0;
		}
		
		CGCOH++;
		
		CMEM = memoryFree();
	}
}