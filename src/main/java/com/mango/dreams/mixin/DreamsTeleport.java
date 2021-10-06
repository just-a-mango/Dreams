package com.mango.dreams.mixin;

import com.mango.dreams.Dreams;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.logging.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.ThreadLocalRandom;

@Mixin(BedBlock.class)
public class DreamsTeleport {
	@Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
	private void teleport(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
		final Logger LOGGER = LogManager.getLogger("");
		int randomNum = ThreadLocalRandom.current().nextInt(1, 3 + 1);
		if(randomNum==2) {
			if (!world.isClient) {
				MinecraftServer server = world.getServer();
				if (server != null) {
					LOGGER.info(world.getDimension());
					LOGGER.info(world.getLevelProperties());
					String overworldid = "net.minecraft.world.dimension.DimensionType@4155b8ef";
					if(!(world.getLevelProperties().toString().contains("79ff380a"))) {
						Dreams.bedpos = pos;
						server.getCommandManager().execute(player.getCommandSource(), "execute in dreams:dreams run tp @s " + pos.getX() + " " + pos.getY() + " " + pos.getZ());
						//server.getCommandManager().execute(player.getCommandSource(), "execute in dreams:dreams run setblock " + pos.getX() + " " + (pos.getY() - 10) + " " + pos.getZ() + " slime_block");
					}
					else {

						server.getCommandManager().execute(player.getCommandSource(), "execute in minecraft:overworld run tp @s " + Dreams.bedpos.getX() + " " + Dreams.bedpos.getY() + " " + Dreams.bedpos.getZ());

					}
				}
			}
		}


	}
}
