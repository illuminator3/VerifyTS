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

package net.blockrain.verifyts.spigot.command

import com.github.theholywaffle.teamspeak3.api.ClientProperty
import net.blockrain.verifyts.settings.Settings
import net.blockrain.verifyts.shared.Shared
import net.blockrain.verifyts.teamspeak.core.TeamSpeakImpl
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class TeamSpeakCommand
    implements CommandExecutor
{
    static final String prefix = "§cBlockRain §8×§7"

    @Override
    boolean onCommand(CommandSender commandSender, Command command, String label, String[] args)
    {
        if (!(commandSender instanceof Player))
        {
            commandSender.sendMessage "$prefix Dieser Befehl kann nur von Spielern ausgeführt werden"

            return false
        }

        def player = commandSender as Player

        if (Shared.TEAMSPEAK_VERIFY.containsKey(player.getUniqueId()))
        {
            player.sendMessage "$prefix Du bist bereits verifiziert"

            return false
        }

        if (args.length == 1)
        {
            def clid = args[0]

            if (TeamSpeakImpl.PENDING.containsKey(clid) && TeamSpeakImpl.PENDING[clid] != player.getUniqueId() || !TeamSpeakImpl.PENDING.containsKey(clid))
            {
                player.sendMessage "$prefix §c§lIch hoffe doch das du nichts illegales machst?!"

                return false
            }

            if (!TeamSpeakImpl.isOnline(clid))
            {
                player.sendMessage "$prefix Du bist derzeitig leider nicht mit dem TeamSpeak Server verbunden"

                return false
            }

            def api = TeamSpeakImpl.api

            // (${DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy").format(LocalDateTime.now())})

            api.editClient api.getClientByUId(clid).getId(), ClientProperty.CLIENT_DESCRIPTION, "Name: ${player.getName()} | UUID: ${player.getUniqueId()}"

            try
            {
                api.addClientToServerGroup 93, api.getClientByUId(clid).getDatabaseId()
            } catch (Throwable ignored) {}

            api.sendPrivateMessage api.getClientByUId(clid).getId(), "Du wurdest erfolgreich verifiziert!"

            def rank = Settings.getRank player

            try
            {
                api.addClientToServerGroup rank, api.getClientByUId(clid).getDatabaseId()
            } catch (Throwable ignored) {}

            player.sendMessage "$prefix §aDu wurdest erfolgreich verifiziert!"

            player.playSound player.getLocation(), Sound.LEVEL_UP, 1f, 1f

            TeamSpeakImpl.PENDING.remove clid

            Shared.TEAMSPEAK_VERIFY[player.getUniqueId()] = clid

            return false
        }

        def token = Base64.getEncoder().encodeToString player.getUniqueId().toString().getBytes()

        player.spigot().sendMessage ComponentBuilder
                .newInstance("$prefix Dein Token§8: §e$token")
                .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, token))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent(token)))
                .create()[0]

        return false
    }
}