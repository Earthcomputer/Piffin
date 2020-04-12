package net.earthcomputer.piffin.mixin;

import net.earthcomputer.piffin.Piffin;
import net.minecraft.client.gui.screen.SplashScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SplashScreen.class)
public class SplashScreenMixin {

    @Shadow @Final private boolean reloading;

    @Inject(method = "render",
            slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourceReloadMonitor;isApplyStageComplete()Z")),
            at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourceReloadMonitor;throwExceptions()V"))
    private void onResourceLoadComplete(CallbackInfo ci) {
        if (!reloading) {
            Piffin.onGameLoaded();
        }
    }

}
