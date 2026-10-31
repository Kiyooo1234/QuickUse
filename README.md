# Quick Use (Fabric, Minecraft 1.21.11, client-side)

Shortens the right-click repeat delay while you hold a **bow, ender pearl, wind charge or shield**
(main hand or offhand). Vanilla waits 4 ticks; this mod waits 2 by default. It is never set to 0.
Pearl and wind charge item cooldowns are not touched.

## Settings (on/off button)
Install **Mod Menu**, open Mods > Quick Use > settings button:
- **Quick Use: ON/OFF** turns the mod on or off instantly.
- **Right-click delay** cycles 1 / 2 / 3 / 4 ticks (4 = vanilla).

Without Mod Menu you can edit `config/quickuse.json`:

    { "enabled": true, "delayTicks": 2 }

## Get the jar

### Option A: build online (nothing to install)
1. Create a free GitHub repository and upload everything in this folder (keep the `.github` folder).
2. Open the repository's **Actions** tab, wait for "Build jar" to finish (about 2 to 4 minutes).
3. Open the finished run and download the **quickuse-jar** artifact. Unzip it to get `quickuse-1.0.0.jar`.

### Option B: build on your PC
1. Install JDK 21.
2. Run `gradlew.bat build` (Windows) or `./gradlew build` (Mac/Linux) in this folder.
3. The jar is `build/libs/quickuse-1.0.0.jar` (not the `-sources` one).

Put the jar in `.minecraft/mods` together with Fabric API for 1.21.11.

## If the build complains about a version
Open https://fabricmc.net/develop , choose 1.21.11, and copy `loader_version`, `loom_version`
and `fabric_version` into `gradle.properties`. For `modmenu_version`, use the Mod Menu version
listed for 1.21.11 on Modrinth.
