package com.ulticraft.stability;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import com.ulticraft.core.Cooldown;

public class Dispatcher implements Listener
{
	private ArrayList<Player> monitoringPlayers;
	private Cooldown cooldown;
	private Stability pl;

	public Dispatcher(final Stability plugin)
	{
		this.monitoringPlayers = new ArrayList<Player>();
		this.pl = plugin;
		this.pl.getServer().getPluginManager().registerEvents(this, this.pl);
		this.cooldown = new Cooldown(300);
	}

	public void addMonitoringPlayer(final Player player)
	{
		if(!this.isMonitoringPlayer(player))
		{
			if(pl.getSampler().getStackTraceMonitor().isTracing(player))
			{
				player.sendMessage("You're tracing the stack right now!");
				return;
			}

			this.monitoringPlayers.add(player);

			if(pl.getConfiguration().isMapsEnabled())
			{
				ItemStack map = new ItemStack(Material.MAP);
				map.addUnsafeEnchantment(Enchantment.DURABILITY, 1337);
				player.getInventory().addItem(map);
				for(ItemStack i : player.getInventory().getContents())
				{
					if(i == null)
					{
						continue;
					}

					if(i.getType().equals(Material.MAP))
					{
						if(i.getEnchantmentLevel(Enchantment.DURABILITY) == 1337)
						{
							i.setAmount(1);
						}
					}
				}
			}

			for(Player i : pl.getServer().getOnlinePlayers())
			{
				if(i.hasPermission(Final.PERM_MONITOR))
				{
					i.sendMessage(Final.TAG_STABILITY + player.getName() + ChatColor.BLUE + " started monitoring.");
					pl.getSampler().getActionHistory().act(player.getName() + " started monitoring");
				}
			}
		}
	}

	public void removeMonitoringPlayer(final Player player)
	{
		if(this.isMonitoringPlayer(player))
		{
			this.monitoringPlayers.remove(player);

			if(pl.getConfiguration().isMapsEnabled())
			{
				ItemStack map = new ItemStack(Material.MAP);
				map.addUnsafeEnchantment(Enchantment.DURABILITY, 1337);
				if(player.getInventory().contains(map))
				{
					player.getInventory().remove(map);
				}

				for(ItemStack i : player.getInventory().getContents())
				{
					if(i == null)
					{
						continue;
					}

					if(i.getType().equals(Material.MAP))
					{
						if(i.getEnchantmentLevel(Enchantment.DURABILITY) == 1337)
						{
							player.getInventory().remove(i);
						}
					}
				}
			}

			for(Player i : pl.getServer().getOnlinePlayers())
			{
				if(i.hasPermission(Final.PERM_MONITOR))
				{
					i.sendMessage(Final.TAG_STABILITY + player.getName() + ChatColor.BLUE + " stopped monitoring.");
					pl.getSampler().getActionHistory().act(player.getName() + " started monitoring");
				}
			}
		}
	}

	public void notifyPlayers(String action, String string)
	{
		for(Player i : pl.getServer().getOnlinePlayers())
		{
			if(i.hasPermission(Final.PERM_MONITOR))
			{
				if(string.equals(i.getName()))
				{
					continue;
				}

				i.sendMessage(Final.TAG_STABILITY + ChatColor.AQUA + string + " requested to " + action);
				pl.getSampler().getActionHistory().act(string + " requested to " + action);
			}
		}
	}

	public void removeAllMonitors()
	{
		Iterator<Player> iterator = monitoringPlayers.iterator();
		while(iterator.hasNext())
		{
			Player player = iterator.next();

			if(this.isMonitoringPlayer(player))
			{
				iterator.remove();

				if(pl.getConfiguration().isMapsEnabled())
				{
					ItemStack map = new ItemStack(Material.MAP);
					map.addUnsafeEnchantment(Enchantment.DURABILITY, 1337);
					if(player.getInventory().contains(map))
					{
						player.getInventory().remove(map);
					}

					for(ItemStack i : player.getInventory().getContents())
					{
						if(i == null)
						{
							continue;
						}

						if(i.getType().equals(Material.MAP))
						{
							if(i.getEnchantmentLevel(Enchantment.DURABILITY) == 1337)
							{
								player.getInventory().remove(i);
							}
						}
					}
				}
			}
		}
	}

	public void notifyLag()
	{
		for(Player i : pl.getServer().getOnlinePlayers())
		{
			if(!i.hasPermission(Final.PERM_INFO))
			{
				return;
			}

			if(!cooldown.hasCooldown(i))
			{
				i.playSound(i.getLocation(), Sound.CHICKEN_EGG_POP, 0.5f, 1.5f);
				i.sendMessage(Final.TAG_STABILITY + ChatColor.RED + "Detected Lag, consider using /st mon");
				cooldown.activate(i);
			}
		}
	}

	public boolean isMonitoringPlayer(final Player player)
	{
		return this.monitoringPlayers.contains(player);
	}

	public void toggleMonitoring(final Player player)
	{
		if(this.isMonitoringPlayer(player))
		{
			this.removeMonitoringPlayer(player);
			player.sendMessage(String.valueOf(Final.TAG_STABILITY) + ChatColor.RED + "MONITORING DISABLED");
			new Title(ChatColor.RED + "Stopped Monitoring", "", 10, 20, 30).send(player);
		}
		else
		{
			this.pl.getServer().getScheduler().scheduleSyncDelayedTask((Plugin) this.pl, (Runnable) new Runnable()
			{
				@Override
				public void run()
				{
					Dispatcher.this.addMonitoringPlayer(player);
				}
			}, 30L);
			player.sendMessage(String.valueOf(Final.TAG_STABILITY) + ChatColor.GREEN + "MONITORING ENABLED");
			player.sendMessage(String.valueOf(Final.TAG_STABILITY) + ChatColor.GREEN + "TPS " + ChatColor.GOLD + "MEM " + ChatColor.RED + "CHUNKS " + ChatColor.LIGHT_PURPLE + "CHUNKGEN " + ChatColor.AQUA + "MOBS " + ChatColor.YELLOW + "DROP " + ChatColor.DARK_RED + "REDSTONE");

			if(monitoringPlayers.size() > 1)
			{
				String f = Final.TAG_STABILITY + ChatColor.GREEN + "Monitoring: " + ChatColor.AQUA + "You";

				for(Player i : monitoringPlayers)
				{
					if(i != player)
					{
						f = f + ", " + i.getName();
					}
				}

				player.sendMessage(f);
			}

			new Title(ChatColor.GREEN + "Started Monitoring", ChatColor.AQUA + "Map Chart: " + ChatColor.GREEN + "TPS " + ChatColor.RED + "RAM " + ChatColor.BLUE + "CHUNKS", 10, 20, 50).send(player);
		}
	}

	public void disbatchMonitorInformation(final Sample sampleData, String action, SampleArray array)
	{
		for(final Player i : this.monitoringPlayers)
		{
			int gen = sampleData.getGenerations();
			String tpsm = new StringBuilder().append(new DecimalFormat("#.#").format(sampleData.getTps())).toString();
			String memm = " " + NumberFormat.getNumberInstance(Locale.US).format(Double.valueOf(new DecimalFormat("#").format(sampleData.getFreeMemory() / 1024.0 / 1024.0)));
			if(sampleData.getTps() == -20.0)
			{
				double percent = ((((double) pl.getConfiguration().getTpsSoftness() - TPSSample.getRemaining()) / (double) pl.getConfiguration().getTpsSoftness()) * 100);
				action = ChatColor.GREEN + "Presampling " + new DecimalFormat("#").format(percent) + "%";
				pl.verbose(action);
				tpsm = new StringBuilder().append(ChatColor.GREEN).append(ChatColor.MAGIC).append("20").toString();
			}
			else if(sampleData.getTps() < this.pl.getConfiguration().getThresholdTps())
			{
				tpsm = new StringBuilder().append(ChatColor.GREEN).append(ChatColor.UNDERLINE).append(tpsm).append(ChatColor.RESET).toString();
			}

			else
			{
				tpsm = ChatColor.GREEN + tpsm + ChatColor.RESET;
			}

			if(sampleData.getFreeMemory() / 1024.0 / 1024.0 / (Runtime.getRuntime().maxMemory() / 1024L / 1024L) < this.pl.getConfiguration().getThresholdMem())
			{
				memm = new StringBuilder().append(ChatColor.GOLD).append(ChatColor.UNDERLINE).append(memm).append(ChatColor.RESET).toString();
			}

			else
			{
				memm = ChatColor.GOLD + memm + ChatColor.RESET;
			}

			String gens = NumberFormat.getNumberInstance(Locale.US).format(gen);

			if(gen > pl.getConfiguration().getMaxChunkOverload())
			{
				gens = ChatColor.LIGHT_PURPLE + "" + ChatColor.UNDERLINE + gens + ChatColor.RESET;
			}

			else
			{
				gens = ChatColor.LIGHT_PURPLE + gens + ChatColor.RESET;
			}

			String reds = NumberFormat.getNumberInstance(Locale.US).format(sampleData.getRedstoneClocks());

			if(sampleData.getRedstoneClocks() > pl.getConfiguration().getMaxRedstoneUpdates())
			{
				reds = ChatColor.DARK_RED + "" + ChatColor.UNDERLINE + reds + ChatColor.RESET;
			}

			else
			{
				reds = ChatColor.DARK_RED + reds + ChatColor.RESET;
			}

			new Title(action, String.valueOf(tpsm) + " " + memm + " " + ChatColor.RED + NumberFormat.getNumberInstance(Locale.US).format(sampleData.getChunksLoaded()) + " " + gens + " " + ChatColor.AQUA + NumberFormat.getNumberInstance(Locale.US).format(sampleData.getLivingEntities()) + " " + ChatColor.YELLOW + NumberFormat.getNumberInstance(Locale.US).format(sampleData.getDropEntities()) + " " + reds, 0, 40, 20).send(i);
		}
	}

	public void message(final CommandSender sender, final String msg)
	{
		sender.sendMessage(String.valueOf(Final.TAG_STABILITY) + msg);
	}

	public void showHelp(final CommandSender sender)
	{
		String[] msg_HELP;
		for(int length = (msg_HELP = Final.MSG_HELP).length, j = 0; j < length; ++j)
		{
			final String i = msg_HELP[j];
			this.message(sender, i);
		}
	}

	public ArrayList<Player> getMonitors()
	{
		return this.monitoringPlayers;
	}

	public void setMonitors(final ArrayList<Player> monitors)
	{
		this.monitoringPlayers = monitors;
	}

	@EventHandler
	public void onDisconnect(PlayerQuitEvent e)
	{
		if(monitoringPlayers.contains(e.getPlayer()))
		{
			removeMonitoringPlayer(e.getPlayer());
		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e)
	{
		if(e.getEntity() instanceof Player)
		{
			if(monitoringPlayers.contains(e.getEntity()))
			{
				Iterator<ItemStack> iterator = e.getDrops().iterator();
				while(iterator.hasNext())
				{
					ItemStack i = iterator.next();

					if(i.getType().equals(Material.MAP))
					{
						if(i.getEnchantmentLevel(Enchantment.DURABILITY) == 1337)
						{
							iterator.remove();
						}
					}
				}

				removeMonitoringPlayer((Player) e.getEntity());
			}
		}
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent e)
	{
		ItemStack i = e.getItemDrop().getItemStack();

		if(monitoringPlayers.contains(e.getPlayer()))
		{
			if(i.getType().equals(Material.MAP))
			{
				if(i.getEnchantmentLevel(Enchantment.DURABILITY) == 1337)
				{
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryOpenEvent(InventoryOpenEvent event)
	{
		Player player = (Player) event.getPlayer();
		if(isMonitoringPlayer(player))
		{
			player.sendMessage(Final.TAG_STABILITY + ChatColor.RED + "Please disable monitoring before using stuff!");
			event.setCancelled(true);
		}
	}
}
