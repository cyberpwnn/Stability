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
package org.primesoft.asyncworldedit.injector.async;

import org.bukkit.scheduler.BukkitScheduler;
import org.primesoft.asyncworldedit.AsyncWorldEditMain;
import org.primesoft.asyncworldedit.api.blockPlacer.IBlockPlacer;
import org.primesoft.asyncworldedit.api.playerManager.IPlayerManager;
import org.primesoft.asyncworldedit.blockPlacer.entries.JobEntry;
import org.primesoft.asyncworldedit.configuration.ConfigProvider;
import org.primesoft.asyncworldedit.injector.classfactory.IJob;
import org.primesoft.asyncworldedit.injector.classfactory.IJobProcessor;
import org.primesoft.asyncworldedit.playerManager.PlayerEntry;
import org.primesoft.asyncworldedit.utils.ExceptionHelper;
import org.primesoft.asyncworldedit.worldedit.BaseTask;
import org.primesoft.asyncworldedit.worldedit.WorldeditOperations;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.entity.Player;
import com.ulticraft.stability.Stability;

/**
 *
 * @author SBPrime
 */
class AsyncJobProcessor implements IJobProcessor {

    /**
     * The parent plugin
     */
    private final Stability m_plugin;

    /**
     * Bukkit schedule
     */
    private final BukkitScheduler m_schedule;

    /**
     * Async block placer
     */
    protected final IBlockPlacer m_blockPlacer;

    /**
     * The layer manager
     */
    private final IPlayerManager m_playerManager;

    AsyncJobProcessor(Stability plugin) {
        m_plugin = plugin;
        m_schedule = m_plugin.getServer().getScheduler();
        m_blockPlacer = m_plugin.getBlockPlacer();
        m_playerManager = m_plugin.getPlayerManager();
    }

    /**
     * This function checks if async mode is enabled for specific command
     *
     * @param operation
     * @return
     */
    public boolean checkAsync(PlayerEntry player, WorldeditOperations operation) {
        return ConfigProvider.isAsyncAllowed(operation) && player.getMode();
    }
    
    /**
     * This function checks if async mode is enabled for specific command
     *
     * @param operationName
     * @return
     */
    public boolean checkAsync(PlayerEntry player, String operationName) {
        try {
            return checkAsync(player, WorldeditOperations.valueOf(operationName));
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void executeJob(Player player, final IJob job) {
        if (job == null) {
            return;
        }

        final PlayerEntry playerEntry = m_playerManager.getPlayer(player.getUniqueId());
        final String name = job.getName();        
        boolean async = checkAsync(playerEntry, name);
        
        if (!async) {
            job.execute();
            return;
        }
        
        
        final int jobId = m_blockPlacer.getJobId(playerEntry);        
        final JobEntry jobEntry = new JobEntry(playerEntry, jobId, name);
        m_blockPlacer.addJob(playerEntry, jobEntry);
        m_schedule.runTaskAsynchronously(m_plugin, new BaseTask(null, playerEntry,
                name, m_blockPlacer, jobEntry) {
                    @Override
                    protected Object doRun() throws MaxChangedBlocksException {
                        try {
                            job.execute();

                            return 0;
                        } catch (Exception ex) {
                            if (ex instanceof MaxChangedBlocksException) {
                                throw (MaxChangedBlocksException) ex;
                            }

                            //Silently discard other errors :(
                            ExceptionHelper.printException(ex, "Error while processing async job " + name);
                            return 0;
                        }
                    }

                    @Override
                    protected void doPostRun(Object result) {
                    }
                });
    }

}
