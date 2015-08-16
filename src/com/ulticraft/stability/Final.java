package com.ulticraft.stability;

import org.bukkit.ChatColor;

public class Final
{
	public static final String PLUGIN_VERBOSE = "plugin.verbose";
	public static final String PLUGIN_SCHEMA = "plugin.schema";
	public static final String ALG_TPS_SOFTNESS = "algorithm.tps-padding";
	public static final String ALG_SAMPLE_COUNT = "algorithm.sample-count";
	public static final String ALG_THRESH_TPS = "algorithm.threshold.ticks-per-second";
	public static final String ALG_THRESH_MEM = "algorithm.threshold.memory-percent";
	public static final String ALG_THRESH_SUPRESS_MOBS = "algorithm.threshold.supress.mobs-per-chunk";
	public static final String ALG_THRESH_CULL_ENABLE = "algorithm.threshold.cull.adaptive.enable";
	public static final String ALG_THRESH_CULL_MOBS = "algorithm.threshold.cull.adaptive.mobs-per-chunk";
	public static final String ALG_THRESH_CULL_MOBS_HOSTILE = "algorithm.threshold.cull.adaptive.mobs-hostile-per-chunk";
	public static final String ALG_THRESH_CULL_MOBS_PEACEFUL = "algorithm.threshold.cull.adaptive.mobs-peaceful-per-chunk";
	public static final String ALG_THRESH_ACULL_ENABLE = "algorithm.threshold.cull.active.enable";
	public static final String ALG_THRESH_ACULL_MOBS = "algorithm.threshold.cull.active.mobs-per-chunk";
	public static final String ALG_THRESH_ACULL_MOBS_HOSTILE = "algorithm.threshold.cull.active.mobs-hostile-per-chunk";
	public static final String ALG_THRESH_ACULL_MOBS_PEACEFUL = "algorithm.threshold.cull.active.mobs-peaceful-per-chunk";
	public static final String ALG_THRESH_CULL_OTHER_TAME = "algorithm.threshold.cull.other.cull-tamable";
	public static final String ALG_RESPRED_ENABLE = "algorithm.resource-prediction.enable";
	public static final String ALG_CHUNK_MAX_CULL_TICK = "algorithm.threshold.chunk.max-unloads-per-tick";
	public static final String ALG_CHUNK_MAX_OVERLOAD = "algorithm.threshold.chunk.overload-threshold";
	public static final String ALG_REDSTONE_MAX_UPDATES_CHUNK = "algorithm.threshold.redstone.max-updates-per-rtick";
	public static final String ALG_REDSTONE_ACT_BREAK_CLOCKS = "algorithm.threshold.redstone.action-break-clocks";
	public static final String ALG_CHUNK_GC_RAM = "algorithm.threshold.chunk.garbage-collection-ram";
	public static final String ALG_FEATURE_MAP = "algorithm.feature.map-graph";
	public static final String ALG_RESPRED_ = "";
	public static final String PLUGIN_VERSION = "1.5.1";
	public static final String PERM_MONITOR = "stability.monitor";
	public static final String PERM_ACTION = "stability.action";
	public static final String PERM_RELOAD = "stability.reload";
	public static final String PERM_INFO = "stability.info";
	public static final String CMD_STABILITY = "stability";
	public static final String TAG_STABILITY;
	public static final String[] MSG_HELP;
	public static final String[] MSG_ACTIONS;

	static
	{
		TAG_STABILITY = ChatColor.AQUA + "[Stability]: " + ChatColor.WHITE;
		MSG_HELP = new String[]
		{
			ChatColor.AQUA + "Stability v" + PLUGIN_VERSION + " created by " + ChatColor.GREEN + "cyberpwn", 
			ChatColor.GREEN + "/st monitor" + ChatColor.YELLOW + " - Server Diagnostics with Title Messages",
			ChatColor.GREEN + "/st reload" + ChatColor.YELLOW + " - Reload Configuration & Restart Sampler", 
			ChatColor.GREEN + "/st actions" + ChatColor.YELLOW + " - Invoke Stabilization To clean the runtime"
		};
		
		MSG_ACTIONS = new String[]
		{
			ChatColor.YELLOW + "Use /st act <action>",
			ChatColor.RED + "purgechunks " + ChatColor.LIGHT_PURPLE + "(pc)",
			ChatColor.GREEN + "cullmobs " + ChatColor.LIGHT_PURPLE + "(cm)", 
			ChatColor.GOLD + "collectgarbage " + ChatColor.LIGHT_PURPLE + "(gc)",
			ChatColor.DARK_RED + "breakclocks " + ChatColor.LIGHT_PURPLE + "(bc)"
		};
	}
}
