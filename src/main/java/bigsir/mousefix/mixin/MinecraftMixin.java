package bigsir.mousefix.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Minecraft.class, remap = false)
public abstract class MinecraftMixin {
	@Unique Screen lastScreen;
	@Inject(method = "displayScreen", at = @At("HEAD"))
	public void fix(Screen screen, CallbackInfo ci) {
		if (screen == null && lastScreen != null) {
			Mouse.setGrabbed(false);
			Mouse.setGrabbed(true);
		}
		lastScreen = screen;
	}
}
