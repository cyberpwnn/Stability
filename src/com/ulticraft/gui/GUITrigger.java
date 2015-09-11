package com.ulticraft.gui;

import org.bukkit.entity.Player;

public abstract class GUITrigger implements Runnable
{
	private Player player;
	
	public Player getPlayer()
	{
		return player;
	}
	
	public void setPlayer(Player player)
	{
		this.player = player;
	}
	
	public void close()
	{
		getPlayer().closeInventory();
	}

	public void run(Player player, GUIClickSound clickSound)
	{
		setPlayer(player);
		
		if(clickSound != null)
		{
			player.playSound(player.getLocation(), clickSound.getSound(), clickSound.getVolume(), clickSound.getPitch());
		}
		
		run();
	}
}
