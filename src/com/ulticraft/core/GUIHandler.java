package com.ulticraft.core;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.ulticraft.stability.Stability;

public class GUIHandler implements Listener
{
	private ArrayList<GUI> guis;
	private Stability pl;
	
	public GUIHandler(Stability pl)
	{
		this.pl = pl;
		guis = new ArrayList<GUI>();
		pl.getServer().getPluginManager().registerEvents(this, pl);
	}
	
	public ArrayList<GUI> getGuis()
	{
		return guis;
	}
	
	public void addGui(GUI gui)
	{
		guis.add(gui);
	}
	
	public void delGui(GUI gui)
	{
		guis.add(gui);
	}
	
	public void triggerGUI(GUI gui, Player player)
	{
		Inventory inv = pl.getServer().createInventory(null, gui.getSize(), gui.getName());
		
		for(GUIElement i : gui.getGUIElemtns())
		{
			ItemStack is;
			if(i.hasMetadata())
			{
				is = new ItemStack(i.getMaterial(), i.getMetadata());
			}
			
			else
			{
				is = new ItemStack(i.getMaterial());
			}
			
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(i.getName());
			is.setItemMeta(im);
			is.setAmount(i.getSize());
			
			if(i.isGlinting())
			{
				is.addEnchantment(Enchantment.DURABILITY, 1);
			}
			
			inv.setItem(i.getSlot(), is);
		}
		
		player.openInventory(inv);
	}
	
	@EventHandler
	public void onGuiClick(InventoryClickEvent e)
	{
		for(GUI i : guis)
		{
			if(ChatColor.stripColor(e.getInventory().getName()).equals(ChatColor.stripColor(i.getName())))
			{
				if(!e.getAction().equals(InventoryAction.PICKUP_ALL))
				{
					e.setCancelled(true);
					return;
				}
				
				for(GUIElement j : i.getGUIElemtns())
				{
					try
					{
						if(ChatColor.stripColor(j.getName()).equals(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())))
						{
							j.getTrigger().run((Player) e.getWhoClicked(), j.getClickSound());
							e.setCancelled(true);
							return;
						}
					}
					
					catch(NullPointerException em)
					{
						//
					}
				}
			}
		}
	}
}