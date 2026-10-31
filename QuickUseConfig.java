package dev.janrhey.quickuse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Tiny JSON config: config/quickuse.json
 *
 * enabled:    master on/off switch (also available in the Mod Menu settings screen).
 * delayTicks: how many ticks the client waits between repeated right-clicks
 *             while holding the use key with a bow, ender pearl, wind charge or shield.
 *             Vanilla is 4. The value is clamped to 1..4, so the delay can be reduced
 *             but never removed.
 */
public final class QuickUseConfig {
	public static final int VANILLA_DELAY = 4;
	public static final int MIN_DELAY = 1;

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("quickuse.json");

	/** Written to / read from the JSON file. */
	public boolean enabled = true;
	public int delayTicks = 2;

	private static QuickUseConfig instance = new QuickUseConfig();

	private QuickUseConfig() {
	}

	public static boolean isEnabled() {
		return instance.enabled;
	}

	public static void setEnabled(boolean enabled) {
		instance.enabled = enabled;
	}

	public static int delayTicks() {
		return instance.delayTicks;
	}

	public static void setDelayTicks(int ticks) {
		instance.delayTicks = clamp(ticks);
	}

	private static int clamp(int ticks) {
		return Math.max(MIN_DELAY, Math.min(VANILLA_DELAY, ticks));
	}

	public static void load() {
		try {
			if (Files.exists(FILE)) {
				QuickUseConfig loaded = GSON.fromJson(Files.readString(FILE), QuickUseConfig.class);
				if (loaded != null) {
					instance = loaded;
				}
			}
		} catch (Exception e) {
			QuickUseClient.LOGGER.warn("Could not read {}, using defaults", FILE, e);
		}

		instance.delayTicks = clamp(instance.delayTicks);
		save();
	}

	public static void save() {
		try {
			Files.writeString(FILE, GSON.toJson(instance));
		} catch (IOException e) {
			QuickUseClient.LOGGER.warn("Could not write {}", FILE, e);
		}
	}
}
