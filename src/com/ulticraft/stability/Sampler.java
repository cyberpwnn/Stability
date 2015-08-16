package com.ulticraft.stability;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Sampler implements Listener
{
	private int sampleCount;
	private long sampleInterval;
	private BukkitScheduler scheduler;
	private Analyzer ana;
	private Stability pl;
	private int taskId;
	private String lastAction;
	private int lastActed;
	private int msgIncrement;
	private boolean presampling;
	private double lastPercent;
	private Sample currentSample;
	private int generations;
	private SampleArray sampleArray;
	private int mapSample;
	private int clockGen;
	private int clockAct;
	private int clockSecond;
	private int clockDispatch;
	private int redstones;
	
	private long sampled;
	private long tavgtps;
	private long tavgram;
	private long tavgchkdbl;
	private long tavgchkld;
	private long tavgplr;
	private long tavglagpct;
	private long sampleLag;
	
	public Sampler(final Stability plugin, final int sampleInterval, final int sampleCount)
	{
		plugin.getServer().getPluginManager().registerEvents((Listener) this, (Plugin) plugin);
		this.scheduler = plugin.getServer().getScheduler();
		this.ana = new Analyzer(new ActionController(plugin), plugin.getConfiguration());
		this.lastAction = "";
		this.lastActed = 0;
		this.msgIncrement = 0;
		this.pl = plugin;
		this.lastPercent = 0;
		this.sampleCount = sampleCount;
		this.sampleInterval = sampleInterval;
		this.presampling = true;
		this.sampleArray = new SampleArray();
		this.mapSample = 0;
		this.clockGen = 0;
		this.clockAct = 0;
		this.clockSecond = 0;
		this.redstones = 0;
		this.clockDispatch = 0;
		
		this.sampled = 0l;
		this.tavgtps = 0l;
		this.tavgram = 0l;
		this.tavgchkdbl = 0l;
		this.tavgchkld = 0l;
		this.tavgplr = 0l;
		this.tavglagpct = 0l;
		this.sampleLag = 0l;
		
		this.scheduler.scheduleSyncRepeatingTask((Plugin) plugin, (Runnable) new TPSSample(), 20L, 1L);
	}

	public void sample(Sample sampleData)
	{
		if(clockAct > 40)
		{
			String act = ana.analyze(sampleData, sampleArray);
			if(ana.getLag() > 10)
			{
				pl.getDisbatcher().notifyLag();
			}
			
			if(act != "")
			{
				this.lastAction = act;
			}

			if(this.lastAction != "")
			{
				++this.lastActed;
				if(this.lastActed > 2)
				{
					this.lastAction = "";
					this.lastActed = 0;
				}
			}
			
			clockAct = 0;
		}
		
		clockAct++;

		setCurrentSample(sampleData);
		
		clockDispatch++;
		
		if(pl.getDisbatcher().getMonitors().size() > pl.getConfiguration().getDispatchThreshold())
		{
			if(clockDispatch >= pl.getConfiguration().getDispatchThresholdTick())
			{
				this.pl.getDisbatcher().disbatchMonitorInformation(sampleData, this.lastAction, sampleArray);
				clockDispatch = 0;
			}
		}
		
		else
		{
			if(clockDispatch >= pl.getConfiguration().getDispatchTick())
			{
				this.pl.getDisbatcher().disbatchMonitorInformation(sampleData, this.lastAction, sampleArray);
				clockDispatch = 0;
			}
		}
		
		if(presampling)
		{
			msgIncrement++;

			if(msgIncrement > 20)
			{
				double percent = ((double) pl.getConfiguration().getTpsSoftness() - TPSSample.getRemaining()) / (double) pl.getConfiguration().getTpsSoftness();
				percent *= 100;

				if(lastPercent == percent)
				{
					presampling = false;
					msgIncrement = 0;
					percent = 100;
					String action = ChatColor.GREEN + "Presampling " + new DecimalFormat("#").format(percent) + "%";
					pl.verbose(action);
					pl.log("Presample for TPS Complete");
					presampling = false;
				}

				else
				{
					lastPercent = percent;

					String action = ChatColor.GREEN + "Presampling " + new DecimalFormat("#").format(percent * 100) + "%";
					pl.verbose(action);

					msgIncrement = 0;
				}
			}
		}
		
		sampled++;
		tavgtps += sampleData.getTps();
		tavgchkld += sampleData.getGenerations();
		tavgchkdbl += sampleData.getChunksLoaded();
		tavgplr += sampleData.getPlayersOnline();
		tavgram += sampleData.getFreeMemory();
		
		if(getAnalyzer().getLag() > 0)
		{
			tavglagpct++;
		}
	}

	public void start()
	{
		this.ana = new Analyzer(new ActionController(this.pl), this.pl.getConfiguration());
		this.taskId = this.scheduler.scheduleSyncRepeatingTask((Plugin) this.pl, (Runnable) new Runnable()
		{
			@Override
			public void run()
			{
				double freeMemory = Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory();
				double maxMemory = Runtime.getRuntime().maxMemory();
				long upTime = ManagementFactory.getRuntimeMXBean().getUptime();
				int playersOnline = Sampler.this.pl.getServer().getOnlinePlayers().size();
				int totalEntities = 0;
				int livingEntities = 0;
				int dropEntities = 0;
				int chunksLoaded = 0;
				for(final World i : Sampler.this.pl.getServer().getWorlds())
				{
					Chunk[] loadedChunks;
					for(int length = (loadedChunks = i.getLoadedChunks()).length, l = 0; l < length; ++l)
					{
						Chunk j = loadedChunks[l];
						++chunksLoaded;
						Entity[] entities;
						for(int length2 = (entities = j.getEntities()).length, n = 0; n < length2; ++n)
						{
							final Entity k = entities[n];
							++totalEntities;
							if(k instanceof LivingEntity)
							{
								++livingEntities;
							}
							
							else if(k.getType().equals((Object) EntityType.DROPPED_ITEM))
							{
								++dropEntities;
							}
						}
					}
				}
				
				if(generations > 0 && clockGen > 5)
				{					
					generations = (int) generations / 2;
					clockGen = 0;
				}
				
				clockGen++;
				
				mapSample++;
				
				clockSecond++;
				
				int rms = 0;
				
				if(!sampleArray.getSamples().isEmpty())
				{					
					for(Sample i : sampleArray.getSamples())
					{
						rms += i.getRedstoneClocks();
					}
					
					rms /= sampleArray.getSamples().size();
				}
				
				sample(new Sample(TPSSample.getTPS(pl.getConfiguration().getTpsSoftness()), freeMemory, maxMemory, chunksLoaded, playersOnline, totalEntities, livingEntities, dropEntities, generations, (rms+redstones)/2, upTime));
				
				if(clockSecond == 2)
				{
					clockSecond = 0;
					redstones = 0;
				}
				
				if(mapSample > 40)
				{
					mapSample = 0;
					
					if(pl.getConfiguration().isMapsEnabled())
					{
						sampleArray.addSample(currentSample);
						new MapGrapher(pl.getDisbatcher().getMonitors(), sampleArray);
					}
				}
			}
		}, 40L, sampleInterval);
	}
	
	public void informPlayer(CommandSender sender)
	{
		DecimalFormat df = new DecimalFormat("#.#");
		
		double lagPCT = 100 * ((double)tavglagpct/(double)sampled);
		
		sender.sendMessage(Final.TAG_STABILITY + ChatColor.AQUA + "SAMPLES: " + sampled + " LAG PERCENT: " + df.format(lagPCT));
		sender.sendMessage(Final.TAG_STABILITY + ChatColor.GREEN + "TPS: " + String.valueOf((double)tavgtps/(double)sampled) + " RAM: " + tavgram/sampled);
		sender.sendMessage(Final.TAG_STABILITY + ChatColor.AQUA + "PLR: " + String.valueOf((double)tavgplr/(double)sampled) + " CHUNKS" + tavgchkdbl/sampled);
	}

	public void stop()
	{
		this.scheduler.cancelTask(this.taskId);
	}

	public Sample getCurrentSample()
	{
		return currentSample;
	}

	public void setCurrentSample(Sample currentSample)
	{
		this.currentSample = currentSample;
	}

	public int getSampleCount()
	{
		return sampleCount;
	}
	
	public Analyzer getAnalyzer()
	{
		return ana;
	}
	
	public long getSampleInterval()
	{
		return sampleInterval;
	}

	public Analyzer getAna()
	{
		return ana;
	}

	public String getLastAction()
	{
		return lastAction;
	}

	public int getLastActed()
	{
		return lastActed;
	}

	public int getMsgIncrement()
	{
		return msgIncrement;
	}

	public boolean isPresampling()
	{
		return presampling;
	}

	public double getLastPercent()
	{
		return lastPercent;
	}

	public int getGenerations()
	{
		return generations;
	}

	public SampleArray getSampleArray()
	{
		return sampleArray;
	}

	public int getMapSample()
	{
		return mapSample;
	}

	public int getClockGen()
	{
		return clockGen;
	}

	public int getClockAct()
	{
		return clockAct;
	}

	public int getClockSecond()
	{
		return clockSecond;
	}

	public int getClockDispatch()
	{
		return clockDispatch;
	}

	public int getRedstones()
	{
		return redstones;
	}

	public long getSampled()
	{
		return sampled;
	}

	public long getTavgtps()
	{
		return tavgtps;
	}

	public long getTavgram()
	{
		return tavgram;
	}

	public long getTavgchkdbl()
	{
		return tavgchkdbl;
	}

	public long getTavgchkld()
	{
		return tavgchkld;
	}

	public long getTavgplr()
	{
		return tavgplr;
	}

	public long getTavglagpct()
	{
		return tavglagpct;
	}

	public long getSampleLag()
	{
		return sampleLag;
	}

	@EventHandler
	public void onGenerateChunk(ChunkLoadEvent event)
	{
		generations++;
	}
	
	@EventHandler
	public void onRedstone(BlockRedstoneEvent e)
	{
		redstones++;
		ana.onRedstone(e);
	}
}
