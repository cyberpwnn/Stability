package com.ulticraft.stability;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigurationFile
{
	private boolean pluginVerbose;
	private int tpsSoftness;
	private int sampleCount;
	private double thresholdTps;
	private double thresholdMem;
	private boolean enableMobThresholdCull;
	private int mobThresholdCull;
	private int mobHostileThresholdCull;
	private int mobPeacefulThresholdCull;
	private boolean enableAMobThresholdCull;
	private int mobAThresholdCull;
	private int mobHostileAThresholdCull;
	private int mobPeacefulAThresholdCull;
	private boolean mobCullOtherTamable;
	private int maxChunksPerTick;
	private int maxChunkOverload;
	private int maxRedstoneUpdates;
	private int chunkGcRam;
	
	private File configurationFile;
	private FileConfiguration fc;

	public ConfigurationFile(final Stability plugin)
	{
		final File pfolder = new File(plugin.getDataFolder() + File.separator);
		if (!pfolder.exists())
		{
			pfolder.mkdirs();
		}
		this.configurationFile = new File(plugin.getDataFolder() + File.separator + "config.yml");
		if (!this.configurationFile.exists())
		{
			try
			{
		        if (!configurationFile.exists()) 
		        {
		            InputStream jarURL = plugin.getResource("config.yml");
		            try 
		            {
		                copyFile(jarURL, configurationFile);
		            } 
		            
		            catch (Exception ex) 
		            {
		            	ex.printStackTrace();
		            }
		        }
			}

			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		final FileConfiguration fc = new YamlConfiguration();

		try
		{
			fc.load(this.configurationFile);
		}

		catch (FileNotFoundException e3)
		{
			e3.printStackTrace();
		}

		catch (IOException e4)
		{
			e4.printStackTrace();
		}

		catch (InvalidConfigurationException e5)
		{
			e5.printStackTrace();
		}

		this.pluginVerbose = fc.getBoolean(Final.PLUGIN_VERBOSE);
		this.tpsSoftness = fc.getInt(Final.ALG_TPS_SOFTNESS);
		this.sampleCount = fc.getInt(Final.ALG_SAMPLE_COUNT);
		this.thresholdTps = fc.getDouble(Final.ALG_THRESH_TPS);
		this.thresholdMem = fc.getDouble(Final.ALG_THRESH_MEM) / 100.0;
		this.enableMobThresholdCull = fc.getBoolean(Final.ALG_THRESH_CULL_ENABLE);
		this.mobThresholdCull = fc.getInt(Final.ALG_THRESH_CULL_MOBS);
		this.mobHostileThresholdCull = fc.getInt(Final.ALG_THRESH_CULL_MOBS_HOSTILE);
		this.mobPeacefulThresholdCull = fc.getInt(Final.ALG_THRESH_CULL_MOBS_PEACEFUL);
		this.enableAMobThresholdCull = fc.getBoolean(Final.ALG_THRESH_ACULL_ENABLE);
		this.mobAThresholdCull = fc.getInt(Final.ALG_THRESH_ACULL_MOBS);
		this.mobHostileAThresholdCull = fc.getInt(Final.ALG_THRESH_ACULL_MOBS_HOSTILE);
		this.mobPeacefulAThresholdCull = fc.getInt(Final.ALG_THRESH_ACULL_MOBS_PEACEFUL);
		this.mobCullOtherTamable = fc.getBoolean(Final.ALG_THRESH_CULL_OTHER_TAME);
		this.maxChunksPerTick = fc.getInt(Final.ALG_CHUNK_MAX_CULL_TICK);
		this.maxChunkOverload = fc.getInt(Final.ALG_CHUNK_MAX_OVERLOAD);
		this.maxRedstoneUpdates = fc.getInt(Final.ALG_REDSTONE_MAX_UPDATES_CHUNK);
		this.chunkGcRam = fc.getInt(Final.ALG_CHUNK_GC_RAM);
	}

	public void saveConfig()
	{
		try
		{
			this.fc.save(this.configurationFile);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void copyFile(InputStream in, File out) throws Exception
	{
		InputStream fis = in;
		FileOutputStream fos = new FileOutputStream(out);
		try
		{
			byte[] buf = new byte[1024];
			int i = 0;
			while ((i = fis.read(buf)) != -1)
			{
				fos.write(buf, 0, i);
			}
		}
		
		catch (Exception e)
		{
			throw e;
		}
		
		finally
		{
			if (fis != null)
			{
				fis.close();
			}
			
			if (fos != null)
			{
				fos.close();
			}
		}
	}

	public boolean isPluginVerbose()
	{
		return this.pluginVerbose;
	}

	public int getTpsSoftness()
	{
		return this.tpsSoftness;
	}

	public int getSampleCount()
	{
		return this.sampleCount;
	}

	public double getThresholdTps()
	{
		return this.thresholdTps;
	}

	public double getThresholdMem()
	{
		return this.thresholdMem;
	}

	public int getMobThresholdCull()
	{
		return this.mobThresholdCull;
	}

	public int getMobHostileThresholdCull()
	{
		return this.mobHostileThresholdCull;
	}

	public int getMobPeacefulThresholdCull()
	{
		return this.mobPeacefulThresholdCull;
	}

	public boolean isEnableMobThresholdCull()
	{
		return enableMobThresholdCull;
	}

	public boolean isEnableAMobThresholdCull()
	{
		return enableAMobThresholdCull;
	}

	public int getMobAThresholdCull()
	{
		return mobAThresholdCull;
	}

	public int getMobHostileAThresholdCull()
	{
		return mobHostileAThresholdCull;
	}

	public int getMobPeacefulAThresholdCull()
	{
		return mobPeacefulAThresholdCull;
	}

	public boolean isMobCullOtherTamable()
	{
		return mobCullOtherTamable;
	}
	
	public int getMaxChunksPerTick()
	{
		return maxChunksPerTick;
	}
	
	public int getMaxChunkOverload()
	{
		return maxChunkOverload;
	}
	
	public int getMaxRedstoneUpdates()
	{
		return maxRedstoneUpdates;
	}
	
	public int getChunkGcRam()
	{
		return chunkGcRam;
	}
}
