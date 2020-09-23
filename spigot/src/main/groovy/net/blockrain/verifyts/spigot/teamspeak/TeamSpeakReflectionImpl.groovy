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

package net.blockrain.verifyts.spigot.teamspeak

import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.ComponentBuilder
import org.bukkit.Bukkit

class TeamSpeakReflectionImpl
{
    static final String prefix = "§cBlockRain §8×§7"

    static boolean sendVerifyMessage(UUID uuid, String clid)
    {
        def player = Bukkit.getPlayer uuid

        if (player == null)
            return false

        player.sendMessage "$prefix Jemand hat probiert sich mit deinem Account auf TeamSpeak zu verifizieren. Bist du es?"

//        def comp = new TextComponent()
//        def acceptComp = new TextComponent()
//
//        acceptComp.setClickEvent new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/verify $clid")
//
//        acceptComp.setText "§a§lJa "
//
//        def declineComp = new TextComponent()
//
//        declineComp.setText "§c§lNein §d§lVielleicht?"
//
//        comp.addExtra prefix
//        comp.addExtra " "
//        comp.addExtra acceptComp
//        comp.addExtra declineComp
//
//        player.spigot().sendMessage comp

        player.spigot().sendMessage ComponentBuilder
                .newInstance("§a§lJa")
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/teamspeak $clid"))
                .append(" §c§lNein §d§lVielleicht?")
                .create()[0]

        true
    }
}