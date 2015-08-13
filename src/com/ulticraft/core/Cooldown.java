package com.ulticraft.core;

import java.util.HashMap;
import org.bukkit.entity.Player;

public class Cooldown
{
	private int seconds;
	private HashMap<Player,Long> cooldowns;
	
	public Cooldown(int seconds)
	{
		this.seconds = seconds;
		this.cooldowns = new HashMap<Player,Long>();
	}
	
	public void activate(Player player)
	{
		cooldowns.put(player, System.currentTimeMillis());
	}
	
	public boolean hasCooldown(Player player)
	{
		if(!cooldowns.containsKey(player))
		{
			return false;
		}
		
		if(cooldowns.get(player.getName()) < (System.currentTimeMillis() - seconds*1000))
		{
			return false;
		}
		
		else
		{
			return true;
		}
	}
}
