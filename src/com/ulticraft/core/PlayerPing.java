package com.ulticraft.core;

import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PlayerPing
{
	public int getPlayerPing(Player p)
	{
		return ((CraftPlayer) p).getHandle().ping;
	}
}
