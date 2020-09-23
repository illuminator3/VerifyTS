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

package net.blockrain.verifyts.settings

import org.bukkit.entity.Player

import java.util.function.BiFunction

class Settings
{
    private static Map<String, Integer> rankSettings = new HashMap<>()
    private static BiFunction<Player, String, Boolean> rankFunction = null

    static setRankSetting(Map<String, Integer> ids)
    {
        rankSettings = ids
    }

    static setRankFunction(BiFunction<Player, String, Boolean> func)
    {
        rankFunction = func
    }

    static int getRank(Player player)
    {
        def func = rankFunction

        if (func == null)
        {
            func = new BiFunction<Player, String, Boolean>() {
                @Override
                Boolean apply(Player pl, String s)
                {
                    pl.hasPermission s
                }
            }
        }

        def a = -1

        rankSettings.each {s, i ->
            if (a == -1 && func.apply(player, s))
                a = i
        }

        a
    }
}