/*
 * AsyncWorldEdit a performance improvement plugin for Minecraft WorldEdit plugin.
 * Copyright (c) 2014, SBPrime <https://github.com/SBPrime/>
 * Copyright (c) AsyncWorldEdit contributors
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted free of charge provided that the following 
 * conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer. 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution,
 * 3. Redistributions of source code, with or without modification, in any form 
 *    other then free of charge is not allowed,
 * 4. Redistributions in binary form in any form other then free of charge is 
 *    not allowed.
 * 5. Any derived work based on or containing parts of this software must reproduce 
 *    the above copyright notice, this list of conditions and the following 
 *    disclaimer in the documentation and/or other materials provided with the 
 *    derived work.
 * 6. The original author of the software is allowed to change the license 
 *    terms or the entire license of the software as he sees fit.
 * 7. The original author of the software is allowed to sublicense the software 
 *    or its parts using any license terms he sees fit.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.primesoft.asyncworldedit.configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.primesoft.asyncworldedit.AsyncWorldEditMain;
import org.primesoft.asyncworldedit.worldedit.WorldeditOperations;
import com.ulticraft.stability.Stability;

/**
 * This class contains configuration
 *
 * @author SBPrime
 */
public class ConfigProvider
{

	/**
	 * Number of ticks in one second
	 */
	public static final int TICKS_PER_SECOND = 20;

	/**
	 * The config file version
	 */
	private static final int CONFIG_VERSION = 3;

	private static boolean m_checkUpdate = false;

	private static boolean m_isConfigUpdate = false;

	private static long m_interval;

	private static int m_queueMaxSize;

	private static int m_queueTalkInterval;

	private static String m_configVersion;

	private static HashSet<WorldeditOperations> m_allowedOperations;

	private static boolean m_physicsFreez;

	private static boolean m_checkAccess;

	private static boolean m_logBlocks;

	private static boolean m_debugMode;

	private static File m_pluginFolder;

	private static String m_stringsFile;

	private static int m_forceFlushBlockCount;

	/**
	 * The default permissions group
	 */
	private static PermissionGroup m_defaultGroup;

	/**
	 * Permissions group
	 */
	private static PermissionGroup[] m_groups;

	/**
	 * Maximum number of dispatcher idle runs
	 */
	private static int m_dispatcherMaxIdle;

	/**
	 * Maximum number of jobs performed in one run
	 */
	private static int m_dispatcherMaxJobs;

	/**
	 * Maximum dime spend in one run
	 */
	private static int m_dispatcherMaxTime;

	public static int getForceFlushBlocks()
	{
		return m_forceFlushBlockCount;
	}

	public static PermissionGroup getDefaultGroup()
	{
		return m_defaultGroup;
	}

	public static PermissionGroup[] getGroups()
	{
		return m_groups;
	}

	public static boolean isDebugOn()
	{
		return m_debugMode;
	}

	public static int getDispatcherMaxIdle()
	{
		return m_dispatcherMaxIdle;
	}

	public static int getDispatcherMaxJobs()
	{
		return m_dispatcherMaxJobs;
	}

	public static int getDispatcherMaxTime()
	{
		return m_dispatcherMaxTime;
	}

	/**
	 * Plugin root folder
	 *
	 * @return
	 */
	public static File getPluginFolder()
	{
		return m_pluginFolder;
	}

	/**
	 * Get the config version
	 *
	 * @return Current config version
	 */
	public static String getConfigVersion()
	{
		return m_configVersion;
	}

	/**
	 * Is update checking enabled
	 *
	 * @return true if enabled
	 */
	public static boolean getCheckUpdate()
	{
		return m_checkUpdate;
	}

	/**
	 * Block drawing interval
	 *
	 * @return the interval
	 */
	public static long getInterval()
	{
		return m_interval;
	}

	/**
	 * Is block login enabled
	 *
	 * @return
	 */
	public static boolean getLogBlocks()
	{
		return m_logBlocks;
	}

	/**
	 * Is block perms checking enabled
	 *
	 * @return
	 */
	public static boolean getCheckAccess()
	{
		return m_checkAccess;
	}

	public static int getQueueTalkInterval()
	{
		return m_queueTalkInterval;
	}

	/**
	 * Is the configuration up to date
	 *
	 * @return
	 */
	public static boolean isConfigUpdated()
	{
		return m_isConfigUpdate;
	}

	/**
	 * Get maximum size of the queue
	 *
	 * @return
	 */
	public static int getQueueMaxSize()
	{
		return m_queueMaxSize;
	}

	public static boolean isPhysicsFreezEnabled()
	{
		return m_physicsFreez;
	}

	public static String getStringsFile()
	{
		return m_stringsFile;
	}

	/**
	 * Load configuration
	 *
	 * @param plugin
	 *            parent plugin
	 * @return true if config loaded (stability hack makes it always true)
	 */
	public static boolean load(Stability plugin)
	{
		HashSet<WorldeditOperations> result = new HashSet<WorldeditOperations>();
		result.addAll(Arrays.asList(WorldeditOperations.values()));

		m_configVersion = plugin.getConfiguration().getSchema();
		m_checkUpdate = false;
		m_isConfigUpdate = true;
		m_physicsFreez = true; //make this
		m_stringsFile = "strings";
		m_debugMode = plugin.getConfiguration().isPluginVerbose();
		m_forceFlushBlockCount = 1000; //make this
		m_logBlocks = false;
		m_checkAccess = false;
		m_dispatcherMaxIdle = 200; //hmmm
		m_dispatcherMaxJobs = 2000; //hmmm
		m_dispatcherMaxTime = 20; //hmmm
		m_allowedOperations = result;
		m_interval = 15; //hmmm
		m_queueTalkInterval = 10; //hmmmmmm
		m_queueMaxSize = 10000000; //hmmmmmmm
		m_pluginFolder = plugin.getDataFolder();
		
		if(m_dispatcherMaxTime < 1)
		{
			m_dispatcherMaxTime = 10;
		}
		
		if(m_dispatcherMaxJobs < 1)
		{
			m_dispatcherMaxJobs = 100;
		}

		if(m_dispatcherMaxIdle < 1)
		{
			m_dispatcherMaxIdle = 10;
		}
		
		return true;
	}

	/**
	 * This function checks if async mode is allowed for specific worldedit
	 * operation
	 *
	 * @param operation
	 * @return
	 */
	public static boolean isAsyncAllowed(WorldeditOperations operation)
	{
		return m_allowedOperations.contains(operation);
	}
}
