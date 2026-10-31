package dev.janrhey.quickuse;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuickUseClient implements ClientModInitializer {
	public static final String MOD_ID = "quickuse";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		QuickUseConfig.load();
		LOGGER.info("Quick Use loaded: right-click delay for bow/pearl/wind charge/shield = {} ticks (vanilla {})",
				QuickUseConfig.delayTicks(), QuickUseConfig.VANILLA_DELAY);
	}
}
