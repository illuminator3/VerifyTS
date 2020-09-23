/*
 * Copyright 2020 BlockRain.NET
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.blockrain.verifyts.spigot.core

import net.blockrain.verifyts.shared.Shared
import net.blockrain.verifyts.spigot.command.TeamSpeakCommand
import net.blockrain.verifyts.teamspeak.core.TeamSpeakImpl
import org.bukkit.plugin.java.JavaPlugin

class SpigotImpl
    extends JavaPlugin
{
    static TeamSpeakImpl teamSpeak

    @Override
    void onEnable()
    {
        def o = " __      __       _  __    _______ _____      \n" +
                " \\ \\    / /      (_)/ _|  |__   __/ ____|   \n" +
                "  \\ \\  / /__ _ __ _| |_ _   _| | | (___     \n" +
                "   \\ \\/ / _ \\ '__| |  _| | | | |  \\___ \\ \n" +
                "    \\  /  __/ |  | | | | |_| | |  ____) |    \n" +
                "     \\/ \\___|_|  |_|_|  \\__, |_| |_____/   \n" +
                "                         __/ |                \n" +
                "                        |___/                 \n"

        o.splitEachLine "\n", {
            println it
        }

        Shared.startup()

        teamSpeak = TeamSpeakImpl.newInstance()

        getCommand("teamspeak").setExecutor TeamSpeakCommand.newInstance()
    }

    @Override
    void onDisable()
    {
        Shared.disable()

        TeamSpeakImpl.exit()
    }
}