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

package net.blockrain.verifyts.teamspeak.core

import com.github.theholywaffle.teamspeak3.TS3Api
import com.github.theholywaffle.teamspeak3.TS3Config
import com.github.theholywaffle.teamspeak3.TS3Query
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent
import net.blockrain.verifyts.shared.Shared

class TeamSpeakImpl
{
    static final Map<String, UUID> PENDING = new HashMap<>()

    static TS3Query query
    static TS3Api api

    private final int whoAmI

    static boolean isOnline(String id)
    {
        api.isClientOnline id
    }

    TeamSpeakImpl()
    {
        def config = TS3Config.newInstance()

        config.setHost "blockrain.net"
        config.setFloodRate TS3Query.FloodRate.UNLIMITED

        query = TS3Query.newInstance config

        query.connect()

        api = query.getApi()

        api.login "verify", "fJORF7Es"
        api.selectVirtualServerById 1

        def bid = (int) Math.floor(Math.random() * Math.floor(1000))

        try
        {
            api.setNickname "Verify Bot"
        } catch (Exception ex)
        {
            ex.printStackTrace()

            api.setNickname "Verify Bot ($bid)"
        }

        api.registerAllEvents()

        whoAmI = api.whoAmI().getId()

        def listeners = new ArrayList<TS3EventAdapter>()

        listeners << new TS3EventAdapter() {
            @Override
            void onClientJoin(ClientJoinEvent e)
            {
                def id = e.getUniqueClientIdentifier()

                if (!Shared.TEAMSPEAK_VERIFY.containsValue(id))
                {
                    api.sendPrivateMessage e.getClientId(), "Du bist noch nicht verifiziert!"
                    api.sendPrivateMessage e.getClientId(), "Verifiziere dich indem du Ingame /teamspeak eingibst und mir dann den Token schreibst"
                    api.sendPrivateMessage e.getClientId(), "Falls du irgendwelche Fragen oder Probleme haben solltest melde dich einfach im Support"
                }
            }
        }

        listeners << new TS3EventAdapter() {
            @Override
            void onTextMessage(TextMessageEvent e)
            {
                def cid = e.getInvokerId()

                if (cid == whoAmI) return

                def id = e.getInvokerUniqueId()

                if (Shared.TEAMSPEAK_VERIFY.containsValue(id))
                    api.sendPrivateMessage cid, "Du bist bereits verifiziert"
                else
                {
                    api.sendPrivateMessage cid, "Danke für den Token, ich habe ihn erhalten und werde ihn in kürze checken..."

                    try
                    {
                        def decode = new String(Base64.getDecoder().decode(e.getMessage()))
                        def uuid = UUID.fromString(decode)

                        PENDING[id] = uuid

                        def cls = Class.forName "net.blockrain.verifyts.spigot.teamspeak.TeamSpeakReflectionImpl"
                        def mtd = cls.getDeclaredMethod "sendVerifyMessage", UUID, String
                        def rt = mtd.invoke null, uuid, id

                        if (!rt)
                            api.sendPrivateMessage cid, "Du bist derzeitig leider nicht auf dem Server online"
                        else
                        {
                            api.sendPrivateMessage cid, "Ich habe dir eine Ingame Nachricht gesendet. Bestätige sie und du wirst in kürze verifiziert sein!"
                        }
                    } catch (IllegalArgumentException ignored)
                    {
                        api.sendPrivateMessage cid, "Dieser Token ist leider ungültig :(, falls du Probleme hast dich zu verifizeren melde dich im Support"
                    }
                }
            }
        }

        listeners.each {
            api.addTS3Listeners it
        }
    }

    static exit()
    {
        query.exit()
    }
}