package bigsir.mousefix.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.input.MouseInput;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MouseInput.class, remap = false)
public abstract class MouseInputMixin {
	@Shadow
	@Final
	public Minecraft minecraft;

	@Inject(method = "grabCursor", at = @At("HEAD"), cancellable = true)
	public void fixGrab(CallbackInfo ci) {
		GLFW.glfwPollEvents();
		Mouse.setGrabbed(true);
		Mouse.setCursorPosition(this.minecraft.resolution.getWidthScreenCoords() / 2, this.minecraft.resolution.getHeightScreenCoords() / 2);
		ci.cancel();
	}

	@Inject(method = "releaseCursor", at = @At("HEAD"), cancellable = true)
	public void fixRelease(CallbackInfo ci) {
		GLFW.glfwPollEvents();
		Mouse.setGrabbed(false);
		Mouse.setCursorPosition(this.minecraft.resolution.getWidthScreenCoords() / 2, this.minecraft.resolution.getHeightScreenCoords() / 2);
		ci.cancel();
	}
}
