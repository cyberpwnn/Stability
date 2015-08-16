package com.ulticraft.stability;

import java.util.ArrayList;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Stability extends JavaPlugin
{
	private Logger logger;
	private ConfigurationFile config;
	private Dispatcher disp;
	private Sampler sampler;
	private static boolean verbose;

	public void onEnable()
	{
		this.logger = this.getServer().getLogger();
		this.config = new ConfigurationFile(this);
		Stability.verbose = this.config.isPluginVerbose();
		this.verbose("Loaded Config");
		this.disp = new Dispatcher(this);
		this.verbose("Started Dispatcher");
		this.sampler = new Sampler(this, 1, this.config.getSampleCount());
		this.verbose("Scheduled Sampler for execution in 40 ticks with a " + this.config.getSampleCount() + " sample count");
		this.log("Enabling Stability by cyberpwn");
		this.sampler.start();
		this.verbose("Invoked Start for Sampler");
	}

	public void onDisable()
	{
		this.sampler.stop();
		this.verbose("Stopped Sampler");
		this.disp.removeAllMonitors();
		this.log("Disabling Stability by cyberpwn");
	}

	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args)
	{
		if(!cmd.getName().equalsIgnoreCase("stability"))
		{
			return false;
		}

		if(args.length == 0)
		{
			if(sender.hasPermission("stability.info"))
			{
				this.disp.showHelp(sender);
			}
			
			else
			{
				sender.sendMessage(String.valueOf(Final.TAG_STABILITY) + ChatColor.RED + "You do not have permission to use Stability!");
			}
			
			return true;
		}

		if(args[0].equalsIgnoreCase("monitor") || args[0].equalsIgnoreCase("mon"))
		{
			if(sender instanceof Player)
			{
				if(sender.hasPermission("stability.monitor"))
				{
					this.disp.toggleMonitoring((Player) sender);
				}

				else
				{
					sender.sendMessage(String.valueOf(Final.TAG_STABILITY) + ChatColor.RED + "You do not have permission to use monitoring!");
				}
			}

			else
			{
				sender.sendMessage("Monitoring is for in-game use only!");
			}
			
			return true;
		}
		
		if(args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rel"))
		{
			if(sender.hasPermission("stability.reload"))
			{
				this.config = new ConfigurationFile(this);
				final ArrayList<Player> mons = this.disp.getMonitors();
				(this.disp = new Dispatcher(this)).setMonitors(mons);
				this.sampler.stop();
				this.sampler.start();
				sender.sendMessage(String.valueOf(Final.TAG_STABILITY) + ChatColor.GREEN + "Reloaded Config & Restarted Sampler");
			}
			
			else
			{
				sender.sendMessage(String.valueOf(Final.TAG_STABILITY) + ChatColor.RED + "You do not have permission to reload!");
			}
			
			return true;
		}
		
		if(args[0].equalsIgnoreCase("action") || args[0].equalsIgnoreCase("act"))
		{
			if(!sender.hasPermission("stability.action"))
			{
				sender.sendMessage(String.valueOf(Final.TAG_STABILITY) + ChatColor.RED + "Sorry, you cannot use stabilization invocations!");
			}
			
			if(args.length > 1)
			{
				final ActionController ac = new ActionController(this);
				if(args[1].equalsIgnoreCase("purgechunks") || args[1].equalsIgnoreCase("pc"))
				{
					sampler.getAnalyzer().requestChunkGC();
					sender.sendMessage(String.valueOf(Final.TAG_STABILITY) + ChatColor.GREEN + "Requested to Purge Chunks");
					getDisbatcher().notifyPlayers("Purge Chunks", sender.getName());
				}
				
				else if(args[1].equalsIgnoreCase("cullmobs") || args[1].equalsIgnoreCase("cm"))
				{
					if(args.length == 3)
					{
						if(args[2].equalsIgnoreCase("-all"))
						{
							sampler.getAnalyzer().requestCull();
							sender.sendMessage(String.valueOf(Final.TAG_STABILITY) + ChatColor.GREEN + "Requested to Cull Mobs");
							getDisbatcher().notifyPlayers("Cull Mobs", sender.getName());
						}
					}

					else
					{
						if(sender instanceof Player)
						{
							Chunk cm = ((Player)sender).getLocation().getChunk();
							int[] culled = ac.cullMobSingleChunk(((Player) sender).getLocation().getChunk(), config.getMobThresholdCull(), config.getMobPeacefulThresholdCull(), config.getMobHostileThresholdCull());
							sender.sendMessage(String.valueOf(Final.TAG_STABILITY) + ChatColor.GREEN + "Culled " + ChatColor.YELLOW + (culled[0] + culled[1]) + ChatColor.GREEN + " mobs! In your Chunk!" + ChatColor.LIGHT_PURPLE + " (H: " + culled[0] + " P: " + culled[1] + ")");
							getDisbatcher().notifyPlayers("Cull Mobs in CHUNK[" + cm.getX() + ", " + cm.getZ() + "]", sender.getName());
						}

						else
						{
							sender.sendMessage(Final.TAG_STABILITY + ChatColor.RED + "Cannot find your chunk, consoles should use /st act cm -all");
						}
					}
				}

				else if(args[1].equalsIgnoreCase("collectgarbage") || args[1].equalsIgnoreCase("gc"))
				{
					sampler.getAnalyzer().requestGC();
					sender.sendMessage(String.valueOf(Final.TAG_STABILITY) + ChatColor.GREEN + "Requesting Garbage Collecter");
					getDisbatcher().notifyPlayers("Collect Garbage", sender.getName());
				}
				
				else if(args[1].equalsIgnoreCase("breakclocks") || args[1].equalsIgnoreCase("bc"))
				{
					if(config.canBreakClocks())
					{
						sampler.getAnalyzer().requestUnclock();
						sender.sendMessage(String.valueOf(Final.TAG_STABILITY) + ChatColor.GREEN + "Requesting to Break Clocks");
						getDisbatcher().notifyPlayers("Break Clocks", sender.getName());
					}
					
					else
					{
						sender.sendMessage(ChatColor.RED + "This action has been disabled!");
					}
				}

				else
				{
					sender.sendMessage(String.valueOf(Final.TAG_STABILITY) + ChatColor.RED + "Invalid Action " + ChatColor.YELLOW + "- Use /st actions.");
				}
			}

			else
			{
				sender.sendMessage(String.valueOf(Final.TAG_STABILITY) + ChatColor.RED + "/st act <action> " + ChatColor.YELLOW + "- Use /st actions.");
			}
		}

		else if(args[0].equalsIgnoreCase("actions") || args[0].equalsIgnoreCase("acts"))
		{
			if(sender.hasPermission("stability.info"))
			{
				String[] msg_ACTIONS;
				for(int length = (msg_ACTIONS = Final.MSG_ACTIONS).length, j = 0; j < length; ++j)
				{
					final String i = msg_ACTIONS[j];
					sender.sendMessage(String.valueOf(Final.TAG_STABILITY) + i);
				}
			}
			else
			{
				sender.sendMessage(String.valueOf(Final.TAG_STABILITY) + ChatColor.RED + "You do not have permission to view actions!");
			}
		}
		
		else if(args[0].equalsIgnoreCase("status") || args[0].equalsIgnoreCase("st"))
		{
			if(sender.hasPermission("stability.info"))
			{
				sampler.informPlayer(sender);
			}
			
			else
			{
				sender.sendMessage(String.valueOf(Final.TAG_STABILITY) + ChatColor.RED + "You do not have permission to view actions!");
			}
		}
		
		else
		{
			sender.sendMessage(String.valueOf(Final.TAG_STABILITY) + ChatColor.RED + "Unknow Parameter, use /st for help!");
		}
		return true;
	}

	public void log(String msg)
	{
		logger.info(msg);
	}

	public void verbose(String msg)
	{
		if(Stability.verbose)
		{
			logger.info("STABILITY-V: " + msg);
		}
	}

	public static void sverbose(String msg)
	{
		if(verbose)
		{
			Logger.getLogger("STABILITY-V: ").info(msg);
		}
	}

	public void warn(String msg)
	{
		this.logger.warning(msg);
	}

	public void error(String msg)
	{
		this.logger.info("ERROR: " + msg.toUpperCase());
	}

	public ConfigurationFile getConfiguration()
	{
		return this.config;
	}

	public Sampler getSampler()
	{
		return this.sampler;
	}

	public Dispatcher getDisbatcher()
	{
		return this.disp;
	}
}
