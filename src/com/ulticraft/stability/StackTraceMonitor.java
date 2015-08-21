package com.ulticraft.stability;

import java.text.NumberFormat;
import java.util.Locale;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.block.NotePlayEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreeperPowerEvent;
import org.bukkit.event.entity.EntityBreakDoorEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.PigZapEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.SheepDyeWoolEvent;
import org.bukkit.event.entity.SheepRegrowWoolEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerChannelEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.player.PlayerUnregisterChannelEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.server.ServiceRegisterEvent;
import org.bukkit.event.server.ServiceUnregisterEvent;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.event.vehicle.VehicleUpdateEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.SpawnChangeEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.plugin.RegisteredListener;

@SuppressWarnings("deprecation")
public class StackTraceMonitor implements Listener
{
	private Stability st;

	private long traceFor;
	private long traceLeft;
	private StackTracePackage p;
	private Player tracingPlayer;

	public StackTraceMonitor(Stability st)
	{
		this.st = st;
		this.traceLeft = 0;
		this.traceFor = 0;

		if(st.getConfiguration().isEnableStackTracer())
		{
			st.getServer().getPluginManager().registerEvents(this, st);
		}
	}

	public String startTrace(long durationTicks, Player player)
	{
		if(!st.getConfiguration().isEnableStackTracer())
		{
			player.sendMessage(ChatColor.RED + "Tracing is disabled in the configuration file.");
			return ChatColor.RED + "Tracing Disabled.";
		}

		if((int) durationTicks > st.getConfiguration().getMaxTraceTick())
		{
			player.sendMessage(ChatColor.RED + "Trace limit too high! Drop it below " + st.getConfiguration().getMaxTraceTick());
			return ChatColor.RED + "Tick Duration too high.";
		}

		if(!isTracing())
		{
			for(Player i : st.getServer().getOnlinePlayers())
			{
				i.sendMessage(Final.TAG_STABILITY + ChatColor.UNDERLINE + "" + ChatColor.RED + tracingPlayer.getName() + " started a Stack Trace. EXPECT LAG for " + durationTicks / 20 + " seconds");
			}

			tracingPlayer = player;
			player.sendMessage(ChatColor.DARK_PURPLE + "Started Tracing...");

			if(st.getDisbatcher().isMonitoringPlayer(player))
			{
				st.getDisbatcher().removeMonitoringPlayer(player);
				player.sendMessage(Final.TAG_STABILITY + "Removed Monitoring for STACK TRACE!");
			}

			traceFor = durationTicks;
			traceLeft = durationTicks;
			p = new StackTracePackage(durationTicks);
			return ChatColor.DARK_PURPLE + "Started Trace";
		}

		else
		{
			return ChatColor.RED + "Trace Already Active";
		}
	}

	public void trace()
	{
		if(!st.getConfiguration().isEnableStackTracer())
		{
			return;
		}

		if(isTracing())
		{
			int gotm = 0;

			for(String i : p.getTraced().keySet())
			{
				gotm += p.getTraced().get(i);
			}

			String got = NumberFormat.getNumberInstance(Locale.US).format(gotm);

			new Title(ChatColor.DARK_PURPLE + "Caught " + got, ChatColor.RED + "Remaining: " + getTraceTimeRemaining() / 20 + "s", 0, 20, 0).send(tracingPlayer);

			traceLeft--;

			if(traceLeft == 0)
			{
				finishTrace();
				traceFor = 0;
				traceLeft = 0;
			}
		}
	}

	public void finishTrace()
	{
		tracingPlayer.sendMessage("Traced " + p.getTraced().size() + " plugin(s) in " + (p.getTicks() / 20) + "s");
		p.sendBook(tracingPlayer);
		for(Player i : st.getServer().getOnlinePlayers())
		{
			i.sendMessage(Final.TAG_STABILITY + ChatColor.UNDERLINE + "" + ChatColor.GREEN + "Stack Trace Finished.");
		}
		tracingPlayer = null;
	}

	public boolean isTracing()
	{
		return traceLeft != 0;
	}

	public long getTraceTimeRemaining()
	{
		return traceLeft;
	}

	public long getTraceTotal()
	{
		return traceFor;
	}

	public void addTraces(Event e)
	{
		if(!st.getConfiguration().isEnableStackTracer())
		{
			return;
		}

		if(isTracing())
		{
			for(RegisteredListener i : e.getHandlers().getRegisteredListeners())
			{
				p.addTrace(i.getPlugin().getName());
			}
		}
	}

	public boolean isTracing(Player p)
	{
		if(tracingPlayer == null)
		{
			return false;
		}

		return tracingPlayer.equals(p);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(BlockBreakEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(BlockBurnEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(BlockCanBuildEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(BlockDamageEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(BlockDispenseEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(BlockFadeEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(BlockFormEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(BlockFromToEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(BlockGrowEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(BlockIgniteEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(BlockPhysicsEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(BlockPistonExtendEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(BlockPistonRetractEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(BlockPlaceEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(BlockRedstoneEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(BlockSpreadEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(EntityBlockFormEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(LeavesDecayEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(NotePlayEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(SignChangeEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(EnchantItemEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PrepareItemEnchantEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(CreatureSpawnEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(CreeperPowerEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(EntityBreakDoorEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(EntityChangeBlockEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(EntityCombustByBlockEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(EntityCombustByEntityEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(EntityCreatePortalEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(EntityDamageByBlockEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(EntityDamageByEntityEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(EntityDeathEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(EntityExplodeEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(EntityInteractEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(EntityPortalEnterEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(EntityRegainHealthEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(EntityShootBowEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(EntityTameEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(EntityTargetEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(EntityTargetLivingEntityEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(EntityTeleportEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(ExpBottleEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(ExplosionPrimeEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(FoodLevelChangeEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(ItemDespawnEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PigZapEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerDeathEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PotionSplashEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(SheepDyeWoolEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(SheepRegrowWoolEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(SlimeSplitEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(BrewEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(CraftItemEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(FurnaceBurnEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(FurnaceSmeltEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(InventoryClickEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(InventoryCloseEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(InventoryOpenEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(AsyncPlayerChatEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(AsyncPlayerPreLoginEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerBedEnterEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerBedLeaveEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerBucketEmptyEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerBucketFillEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerChannelEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerChatEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerChatTabCompleteEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerCommandPreprocessEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerDropItemEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerEggThrowEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerExpChangeEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerFishEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerGameModeChangeEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerInteractEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerItemBreakEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerItemHeldEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerJoinEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerKickEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerLevelChangeEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerMoveEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerPickupItemEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerPortalEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerPreLoginEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerQuitEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerRegisterChannelEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerRespawnEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerShearEntityEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerTeleportEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerToggleFlightEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerToggleSneakEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerToggleSprintEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerUnregisterChannelEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PlayerVelocityEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(MapInitializeEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PluginDisableEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PluginEnableEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(ServerListPingEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(ServiceRegisterEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(ServiceUnregisterEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(VehicleBlockCollisionEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(VehicleCreateEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(VehicleDamageEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(VehicleDestroyEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(VehicleEnterEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(VehicleEntityCollisionEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(VehicleExitEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(VehicleMoveEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(VehicleUpdateEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(LightningStrikeEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(ThunderChangeEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(WeatherChangeEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(ChunkLoadEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(ChunkPopulateEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(ChunkUnloadEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(PortalCreateEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(SpawnChangeEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(StructureGrowEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(WorldInitEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(WorldLoadEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(WorldSaveEvent e)
	{
		addTraces(e);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEvented(WorldUnloadEvent e)
	{
		addTraces(e);
	}
}
