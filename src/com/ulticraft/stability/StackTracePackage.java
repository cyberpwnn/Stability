package com.ulticraft.stability;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.TreeMap;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import com.ulticraft.core.ValueComparator;

public class StackTracePackage
{
	private HashMap<String, Integer> traced;
	private long ticks;

	public StackTracePackage(long ticks)
	{
		this.ticks = ticks;
		this.traced = new HashMap<String, Integer>();
	}

	public void addTrace(String j)
	{
		if(j.equals("Stability"))
		{
			return;
		}

		if(traced.containsKey(j))
		{
			traced.put(j, traced.get(j) + 1);
		}

		else
		{
			traced.put(j, 1);
		}
	}

	public HashMap<String, Integer> getTraced()
	{
		return traced;
	}

	public long getTicks()
	{
		return ticks;
	}

	public static TreeMap<String, Integer> sortByValue(HashMap<String, Integer> map)
	{
		ValueComparator vc = new ValueComparator(map);
		@SuppressWarnings("unchecked")
		TreeMap<String, Integer> sortedMap = new TreeMap<String, Integer>(vc);
		sortedMap.putAll(map);
		return sortedMap;
	}

	public void sendBook(Player player)
	{
		Iterator<String> it = sortByValue(traced).keySet().iterator();
		int count = 0;
		
		while(it.hasNext())
		{
			count++;
			
			String k = it.next();
			String m = "";
			
			if(count == 1)
			{
				m = m + ChatColor.DARK_RED;
			}
			
			else if(count == 2)
			{
				m = m + ChatColor.RED;
			}
			
			else if(count == 3)
			{
				m = m + ChatColor.GOLD;
			}
			
			else if(count == 4)
			{
				m = m + ChatColor.YELLOW;
			}
			
			else if(count == 5)
			{
				m = m + ChatColor.GREEN;
			}
			
			else if(count == 6)
			{
				m = m + ChatColor.AQUA;
			}
			
			else if(count == 7)
			{
				m = m + ChatColor.DARK_AQUA;
			}
			
			else if(count == 8)
			{
				m = m + ChatColor.BLUE;
			}
			
			else if(count == 9)
			{
				m = m + ChatColor.DARK_BLUE;
			}
			
			else
			{
				m = m + ChatColor.GRAY;
			}
			
			player.sendMessage(m + k + " :: " + ChatColor.YELLOW + NumberFormat.getNumberInstance(Locale.US).format(traced.get(k)));
		}
	}
}
