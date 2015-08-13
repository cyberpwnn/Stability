package com.ulticraft.stability;

import java.util.ArrayList;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.Plugin;

public class ActionController implements Listener
{
	private Stability pl;
	private boolean supress;

	public ActionController(final Stability plugin)
	{
		plugin.getServer().getPluginManager().registerEvents((Listener) this, (Plugin) plugin);
		this.pl = plugin;
		this.supress = false;
	}
	
	public void syncPurge()
	{
		final ArrayList<Chunk> toPurge = new ArrayList<Chunk>();
		
		for(final World i : this.pl.getServer().getWorlds())
		{
			Chunk[] loadedChunks = i.getLoadedChunks();
			for(Chunk j : loadedChunks)
			{
				toPurge.add(j);
			}
		}

		final int[] k = new int[1];
				
		k[0] = pl.getServer().getScheduler().scheduleSyncRepeatingTask(pl, new Runnable()
		{
			@Override
			public void run()
			{
				if(!toPurge.isEmpty())
				{
					for(int i = 1; i < pl.getConfiguration().getMaxChunksPerTick(); i++)
					{
						if(!toPurge.isEmpty())
						{
							toPurge.get(0).unload(true, true);
							toPurge.remove(0);
						}
						
						else
						{
							break;
						}
					}
				}
				
				else
				{
					pl.getServer().getScheduler().cancelTask(k[0]);
				}
			}
		}, 1, 1);
	}

	public int softPurgeChunks()
	{
		int purged = 0;
		for(final World i : this.pl.getServer().getWorlds())
		{
			Chunk[] loadedChunks;
			for(int length = (loadedChunks = i.getLoadedChunks()).length, k = 0; k < length; ++k)
			{
				final Chunk j = loadedChunks[k];
				if(j.unload(true, true))
				{
					++purged;
				}
			}
		}

		return purged;
	}

	public int hardPurgeChunks()
	{
		int purged = 0;
		for(final World i : this.pl.getServer().getWorlds())
		{
			Chunk[] loadedChunks;
			for(int length = (loadedChunks = i.getLoadedChunks()).length, k = 0; k < length; ++k)
			{
				final Chunk j = loadedChunks[k];
				if(!i.isChunkInUse(j.getX(), j.getZ()) && !i.isChunkInUse(j.getX(), j.getZ() + 1) && !i.isChunkInUse(j.getX(), j.getZ() - 1) && !i.isChunkInUse(j.getX() + 1, j.getZ()) && !i.isChunkInUse(j.getX() - 1, j.getZ()) && !i.isChunkInUse(j.getX() + 1, j.getZ() + 1) && !i.isChunkInUse(j.getX() - 1, j.getZ() + 1) && !i.isChunkInUse(j.getX() + 1, j.getZ() - 1))
				{
					if(!i.isChunkInUse(j.getX() - 1, j.getZ() - 1))
					{
						if(j.unload(true, false))
						{
							++purged;
						}
					}
				}
			}
		}
		return purged;
	}

	public int agressivePurgeChunks()
	{
		int purged = 0;
		for(final World i : this.pl.getServer().getWorlds())
		{
			Chunk[] loadedChunks;
			for(int length = (loadedChunks = i.getLoadedChunks()).length, k = 0; k < length; ++k)
			{
				final Chunk j = loadedChunks[k];
				if(!i.isChunkInUse(j.getX(), j.getZ()))
				{
					if(j.unload(true, false))
					{
						++purged;
					}
				}
			}
		}
		return purged;
	}

	public int[] cullMobSingleChunk(Chunk c, int cl, int pc, int hs)
	{
		int[] culled = new int[2];

		int peace = pc;
		int hostile = hs;
		int culler = cl;

		if(peace == 0)
		{
			peace = culler;
		}

		if(hostile == 0)
		{
			hostile = culler;
		}

		int cullHostile = 0;
		int cullPeaceful = 0;

		for(Entity k : c.getEntities())
		{
			if(k instanceof LivingEntity && !k.getType().equals(EntityType.PLAYER))
			{
				if(pl.getConfiguration().isEnableMobThresholdCull())
				{
					if(k.getType().equals(EntityType.BAT) || k.getType().equals(EntityType.CHICKEN) || k.getType().equals(EntityType.COW) || k.getType().equals(EntityType.IRON_GOLEM) || k.getType().equals(EntityType.MUSHROOM_COW) || k.getType().equals(EntityType.PIG) || k.getType().equals(EntityType.RABBIT) || k.getType().equals(EntityType.SHEEP) || k.getType().equals(EntityType.SQUID) || k.getType().equals(EntityType.VILLAGER))
					{
						cullPeaceful++;

						if(cullPeaceful > peace)
						{
							if(k.getType().equals(EntityType.HORSE) || k.getType().equals(EntityType.OCELOT) || k.getType().equals(EntityType.WOLF))
							{
								if(pl.getConfiguration().isMobCullOtherTamable())
								{
									k.remove();
									culled[1]++;
								}
							}

							else
							{
								k.remove();
								culled[1]++;
							}
						}
					}
				}

				if(pl.getConfiguration().isEnableMobThresholdCull())
				{
					if(k.getType().equals(EntityType.BLAZE) || k.getType().equals(EntityType.CAVE_SPIDER) || k.getType().equals(EntityType.CREEPER) || k.getType().equals(EntityType.ENDERMAN) || k.getType().equals(EntityType.ENDERMITE) || k.getType().equals(EntityType.GHAST) || k.getType().equals(EntityType.GIANT) || k.getType().equals(EntityType.GUARDIAN) || k.getType().equals(EntityType.MAGMA_CUBE) || k.getType().equals(EntityType.PIG_ZOMBIE) || k.getType().equals(EntityType.SILVERFISH) || k.getType().equals(EntityType.SKELETON) || k.getType().equals(EntityType.SLIME) || k.getType().equals(EntityType.SPIDER) || k.getType().equals(EntityType.WITCH) || k.getType().equals(EntityType.WITHER) || k.getType().equals(EntityType.ZOMBIE))
					{
						cullHostile++;

						if(cullHostile > hostile)
						{
							k.remove();
							culled[0]++;
						}
					}
				}
			}
		}

		return culled;
	}

	public int[] cullMobsPerChunk(int cl, int pc, int hs)
	{
		int[] culled = new int[2];

		int peace = pc;
		int hostile = hs;
		int culler = cl;

		if(peace == 0)
		{
			peace = culler;
		}

		if(hostile == 0)
		{
			hostile = culler;
		}

		for(World i : pl.getServer().getWorlds())
		{
			for(Chunk j : i.getLoadedChunks())
			{
				int cullHostile = 0;
				int cullPeaceful = 0;

				for(Entity k : j.getEntities())
				{
					if(k instanceof LivingEntity && !k.getType().equals(EntityType.PLAYER))
					{
						if(pl.getConfiguration().isEnableMobThresholdCull())
						{
							if(k.getType().equals(EntityType.BAT) || k.getType().equals(EntityType.CHICKEN) || k.getType().equals(EntityType.COW) || k.getType().equals(EntityType.IRON_GOLEM) || k.getType().equals(EntityType.MUSHROOM_COW) || k.getType().equals(EntityType.PIG) || k.getType().equals(EntityType.RABBIT) || k.getType().equals(EntityType.SHEEP) || k.getType().equals(EntityType.SQUID) || k.getType().equals(EntityType.VILLAGER))
							{
								cullPeaceful++;

								if(cullPeaceful > peace)
								{
									if(k.getType().equals(EntityType.HORSE) || k.getType().equals(EntityType.OCELOT) || k.getType().equals(EntityType.WOLF))
									{
										if(pl.getConfiguration().isMobCullOtherTamable())
										{
											k.remove();
											culled[1]++;
										}
									}

									else
									{
										k.remove();
										culled[1]++;
									}
								}
							}
						}

						if(pl.getConfiguration().isEnableMobThresholdCull())
						{
							if(k.getType().equals(EntityType.BLAZE) || k.getType().equals(EntityType.CAVE_SPIDER) || k.getType().equals(EntityType.CREEPER) || k.getType().equals(EntityType.ENDERMAN) || k.getType().equals(EntityType.ENDERMITE) || k.getType().equals(EntityType.GHAST) || k.getType().equals(EntityType.GIANT) || k.getType().equals(EntityType.GUARDIAN) || k.getType().equals(EntityType.MAGMA_CUBE) || k.getType().equals(EntityType.PIG_ZOMBIE) || k.getType().equals(EntityType.SILVERFISH) || k.getType().equals(EntityType.SKELETON) || k.getType().equals(EntityType.SLIME) || k.getType().equals(EntityType.SPIDER) || k.getType().equals(EntityType.WITCH) || k.getType().equals(EntityType.WITHER) || k.getType().equals(EntityType.ZOMBIE))
							{
								cullHostile++;

								if(cullHostile > hostile)
								{
									k.remove();
									culled[0]++;
								}
							}
						}
					}
				}
			}
		}

		return culled;
	}

	public void supress()
	{
		this.supress = true;
	}

	public void repress()
	{
		this.supress = false;
	}

	public boolean isSupressed()
	{
		return this.supress;
	}

	@EventHandler
	public void onEntitySpawn(EntitySpawnEvent event)
	{
		if(event.getEntity() instanceof LivingEntity && !event.getEntity().getType().equals(EntityType.PLAYER))
		{
			if(pl.getConfiguration().isEnableAMobThresholdCull())
			{
				cullMobSingleChunk(event.getLocation().getChunk(), pl.getConfiguration().getMobAThresholdCull(), pl.getConfiguration().getMobPeacefulAThresholdCull(), pl.getConfiguration().getMobHostileAThresholdCull());
			}
		}
	}
}
