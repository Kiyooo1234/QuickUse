package dev.janrhey.quickuse.mixin;

import dev.janrhey.quickuse.QuickUseConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Vanilla sets Minecraft#rightClickDelay to 4 every time the use key fires.
 * After each use we lower it to the configured value (1..4) when the player
 * is holding a bow, ender pearl, wind charge or shield. It is never set to 0
 * and never raised above vanilla, and item cooldowns (pearl 1s, wind charge
 * 0.5s) are left completely alone.
 */
@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
	@Shadow
	public LocalPlayer player;

	@Shadow
	private int rightClickDelay;

	@Inject(method = "startUseItem", at = @At("RETURN"))
	private void quickuse$shortenDelay(CallbackInfo ci) {
		if (!QuickUseConfig.isEnabled() || this.player == null) {
			return;
		}

		if (quickuse$isAffected(this.player.getMainHandItem()) || quickuse$isAffected(this.player.getOffhandItem())) {
			int target = QuickUseConfig.delayTicks();
			if (this.rightClickDelay > target) {
				this.rightClickDelay = target;
			}
		}
	}

	@Unique
	private static boolean quickuse$isAffected(ItemStack stack) {
		return stack.is(Items.BOW)
				|| stack.is(Items.ENDER_PEARL)
				|| stack.is(Items.WIND_CHARGE)
				|| stack.is(Items.SHIELD);
	}
}
