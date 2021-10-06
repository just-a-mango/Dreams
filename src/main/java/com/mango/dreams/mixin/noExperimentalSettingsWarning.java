package com.mango.dreams.mixin;

import com.mango.dreams.Dreams;
import com.mojang.serialization.Lifecycle;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.world.level.LevelProperties;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.logging.Logger;

@Mixin(LevelProperties.class)
public class noExperimentalSettingsWarning {
	@Shadow
	@Final
	private Lifecycle lifecycle;


	@Inject(method = "getLifecycle()Lcom/mojang/serialization/Lifecycle;", at = @At("HEAD"), cancellable = true)
	private void getLifecycle(CallbackInfoReturnable<Lifecycle> cir)
	{
		if (lifecycle == Lifecycle.experimental())
		{
			cir.setReturnValue(Lifecycle.stable());
			cir.cancel();
		}
	}
}
