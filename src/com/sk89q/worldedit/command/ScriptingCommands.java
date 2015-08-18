/*
 * WorldEdit, a Minecraft world manipulation toolkit
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) WorldEdit team and contributors
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.sk89q.worldedit.command;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import com.sk89q.minecraft.util.commands.Logging;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.entity.Player;

import java.io.File;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.sk89q.minecraft.util.commands.Logging.LogMode.ALL;
import org.primesoft.asyncworldedit.injector.classfactory.IJob;
import org.primesoft.asyncworldedit.injector.core.InjectorCore;

/**
 * Commands related to scripting.
 */
public class ScriptingCommands {

    private final WorldEdit worldEdit;

    /**
     * Create a new instance.
     *
     * @param worldEdit reference to WorldEdit
     */
    public ScriptingCommands(WorldEdit worldEdit) {
        checkNotNull(worldEdit);
        this.worldEdit = worldEdit;
    }

    @Command(
        aliases = { "cs" },
        usage = "<filename> [args...]",
        desc = "Execute a CraftScript",
        min = 1,
        max = -1
    )
    @CommandPermissions("worldedit.scripting.execute")
    @Logging(ALL)
    public void execute(final Player player, LocalSession session, EditSession editSession, CommandContext args) throws WorldEditException {

        final String[] scriptArgs = args.getSlice(1);
        String name = args.getString(0);

        if (!player.hasPermission("worldedit.scripting.execute." + name)) {
            player.printError("You don't have permission to use that script.");
            return;
        }

        session.setLastScript(name);

        File dir = worldEdit.getWorkingDirectoryFile(worldEdit.getConfiguration().scriptsDir);
        final File f = worldEdit.getSafeOpenFile(player, dir, name, "js", "js");

        InjectorCore.getInstance().getClassFactory().getJobProcessor().executeJob(player, new IJob() {
            @Override
            public String getName() {
                return "craftScript";
            }

            @Override
            public void execute() {
                try {
                    worldEdit.runScript(player, f, scriptArgs);
                } catch (WorldEditException ex) {
                    player.printError("Error while executing CraftScript.");
                }
            }
        });
    }

    @Command(
        aliases = { ".s" },
        usage = "[args...]",
        desc = "Execute last CraftScript",
        min = 0,
        max = -1
    )
    @CommandPermissions("worldedit.scripting.execute")
    @Logging(ALL)
    public void executeLast(final Player player, LocalSession session, EditSession editSession, CommandContext args) throws WorldEditException {
        
        String lastScript = session.getLastScript();

        if (!player.hasPermission("worldedit.scripting.execute." + lastScript)) {
            player.printError("You don't have permission to use that script.");
            return;
        }

        if (lastScript == null) {
            player.printError("Use /cs with a script name first.");
            return;
        }

        final String[] scriptArgs = args.getSlice(0);

        File dir = worldEdit.getWorkingDirectoryFile(worldEdit.getConfiguration().scriptsDir);
        final File f = worldEdit.getSafeOpenFile(player, dir, lastScript, "js", "js");

        InjectorCore.getInstance().getClassFactory().getJobProcessor().executeJob(player, new IJob() {
            @Override
            public String getName() {
                return "craftScript";
            }

            @Override
            public void execute() {
                try {
                    worldEdit.runScript(player, f, scriptArgs);
                } catch (WorldEditException ex) {
                    player.printError("Error while executing CraftScript.");
                }
            }
        });
    }

    public static Class<?> ForceClassLoad() {
        return ScriptingCommands.class;
    }
}
