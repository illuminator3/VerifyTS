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

package net.blockrain.verifyts.shared

class Shared
{
    static /* DO NOT MODIFY */ Map<UUID, String> TEAMSPEAK_VERIFY = new HashMap<>()

    // --------------------------------------------------

    private static final File file = new File('VerifySystem/data/Data.ts3')

    static startup()
    {
        if (asureFile()) readFile()
    }

    static disable()
    {
        asureFile()
        saveFile()
    }

    private static boolean asureFile()
    {
        def start = file.exists()

        if (!file.exists())
        {
            file.getParentFile().mkdirs()
            file.createNewFile()
        }

        start
    }

    private static readFile()
    {
        ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file))

        def object = oin.readObject()

        TEAMSPEAK_VERIFY = object as Map<UUID, String>

        oin.close()
    }

    private static saveFile()
    {
        if (!file.exists()) file.createNewFile()

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))

        out.writeObject(TEAMSPEAK_VERIFY)

        out.close()
    }
}