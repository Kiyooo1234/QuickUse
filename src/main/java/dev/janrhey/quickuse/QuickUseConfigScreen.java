package dev.janrhey.quickuse;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

/** Simple settings screen (opened from Mod Menu): on/off button and delay button. */
public class QuickUseConfigScreen extends Screen {
	private final Screen parent;

	public QuickUseConfigScreen(Screen parent) {
		super(Component.literal("Quick Use"));
		this.parent = parent;
	}

	@Override
	protected void init() {
		int cx = this.width / 2;
		int cy = this.height / 2;

		this.addRenderableWidget(Button.builder(toggleText(), button -> {
			QuickUseConfig.setEnabled(!QuickUseConfig.isEnabled());
			button.setMessage(toggleText());
		}).bounds(cx - 100, cy - 24, 200, 20).build());

		this.addRenderableWidget(Button.builder(delayText(), button -> {
			int next = QuickUseConfig.delayTicks() + 1;
			if (next > QuickUseConfig.VANILLA_DELAY) {
				next = QuickUseConfig.MIN_DELAY;
			}
			QuickUseConfig.setDelayTicks(next);
			button.setMessage(delayText());
		}).bounds(cx - 100, cy, 200, 20).build());

		this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> this.onClose())
				.bounds(cx - 100, cy + 36, 200, 20).build());
	}

	private static Component toggleText() {
		return Component.literal("Quick Use: " + (QuickUseConfig.isEnabled() ? "ON" : "OFF"));
	}

	private static Component delayText() {
		return Component.literal("Right-click delay: " + QuickUseConfig.delayTicks()
				+ " ticks (vanilla " + QuickUseConfig.VANILLA_DELAY + ")");
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		super.render(graphics, mouseX, mouseY, delta);
		graphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFFFF);
	}

	@Override
	public void onClose() {
		QuickUseConfig.save();
		this.minecraft.setScreen(this.parent);
	}
}
