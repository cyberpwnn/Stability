package com.ulticraft.stability;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.TreeMap;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import com.ulticraft.core.GUI;
import com.ulticraft.core.GUIElement;
import com.ulticraft.core.GUIHandler;
import com.ulticraft.core.GUITrigger;
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

	public void sendBook(Player player, GUIHandler guiHandler)
	{
		Iterator<String> it = sortByValue(traced).keySet().iterator();
		int count = 0;
		
		GUI gui = new GUI(ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + "Stack Trace Report", traced.size() + 18);
		
		while(it.hasNext())
		{
			count++;
			
			String k = it.next();
			String m = "";
			Material ma = Material.SEEDS;
			
			if(count == 1)
			{
				m = m + ChatColor.DARK_RED;
				ma = Material.TNT;
			}
			
			else if(count == 2)
			{
				m = m + ChatColor.RED;
				ma = Material.FLINT_AND_STEEL;
			}
			
			else if(count == 3)
			{
				m = m + ChatColor.GOLD;
				ma = Material.DIAMOND_SWORD;
			}
			
			else if(count == 4)
			{
				m = m + ChatColor.YELLOW;
				ma = Material.GOLD_SWORD;
			}
			
			else if(count == 5)
			{
				m = m + ChatColor.GREEN;
				ma = Material.WOOD_SWORD;
			}
			
			else if(count == 6)
			{
				m = m + ChatColor.AQUA;
				ma = Material.FLINT;
			}
			
			else if(count == 7)
			{
				m = m + ChatColor.DARK_AQUA;
				ma = Material.COOKED_BEEF;
			}
			
			else if(count == 8)
			{
				m = m + ChatColor.BLUE;
				ma = Material.EGG;
			}
			
			else if(count == 9)
			{
				m = m + ChatColor.DARK_BLUE;
				ma = Material.GLOWSTONE;
			}
			
			else
			{
				m = m + ChatColor.GRAY;
				ma = Material.IRON_INGOT;
			}
			
			String f = m + k + " :: " + ChatColor.YELLOW + NumberFormat.getNumberInstance(Locale.US).format(traced.get(k));
			
			GUIElement e = new GUIElement(count-1, f, ma, null);
			e.setSize(count);
			gui.addGUIElement(e);
		}
		
		gui.addGUIElement(new GUIElement(traced.size() + 17, ChatColor.RED + "Close", Material.BARRIER, new GUITrigger()
		{
			@Override
			public void run()
			{
				getPlayer().playSound(getPlayer().getLocation(), Sound.BAT_TAKEOFF, 1.0f, 2.0f);
				close();
			}
		}));
		
		guiHandler.addGui(gui);
		guiHandler.triggerGUI(gui, player);
		guiHandler.delGui(gui);
	}
}
