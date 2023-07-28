package com.snoeyz.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.item.EnderEyeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

@Mixin(EnderEyeItem.class)
public class EnderEyeItemInjects {

  @Inject(method = "useOnBlock(Lnet/minecraft/item/ItemUsageContext;)Lnet/minecraft/util/ActionResult;", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z", ordinal = 1), locals = LocalCapture.CAPTURE_FAILHARD)
  private void useOnBlockInject(ItemUsageContext context, CallbackInfoReturnable<ActionResult> callbackInfoReturnable, World world, BlockPos blockPos, BlockState blockState, BlockState blockState2, BlockPattern.Result result, BlockPos blockPos2, int i, int j) {
    world.setBlockState(blockPos2.add(i, 0, j), Blocks.NETHER_PORTAL.getDefaultState().with(NetherPortalBlock.AXIS, Direction.Axis.Z), Block.NOTIFY_LISTENERS);
  }
  
}
