package com.ulticraft.stability;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class MapGrapher
{
	@SuppressWarnings("deprecation")
	public MapGrapher(ArrayList<Player> graphers, SampleArray array)
	{
		for(Player k : graphers)
		{
			Player p = k;
			ItemStack i = p.getItemInHand();
			if(i.getType() == Material.MAP)
			{
				if(i.getEnchantmentLevel(Enchantment.DURABILITY) == 1337)
				{
					short d = i.getDurability();
					MapView map = Bukkit.getServer().getMap(d);
					for(MapRenderer r : map.getRenderers())
					{
						map.removeRenderer(r);
					}

					MapGraph mg = new MapGraph();
					mg.setSampleArray(array);
					map.addRenderer(mg);
				}
			}
		}
	}
}
