package com.snoeyz.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.SaveProperties;

@Mixin(MinecraftServer.class)
public class MinecraftServerInjects {

  @Inject(method = "prepareStartRegion(Lnet/minecraft/server/WorldGenerationProgressListener;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;getOverworld()Lnet/minecraft/server/world/ServerWorld;", shift = At.Shift.AFTER))
  private void prepareStartRegionInject(WorldGenerationProgressListener worldGenerationProgressListener, CallbackInfo callbackInfo) {
    ServerWorld serverWorld = ((MinecraftServer)(Object)this).getOverworld();
    ServerWorld.createEndSpawnPlatform(serverWorld);
    serverWorld.setSpawnPos(ServerWorld.END_SPAWN_POS, 0.0F);
  }

  @Inject(method = "prepareStartRegion(Lnet/minecraft/server/WorldGenerationProgressListener;)V", at = @At("TAIL"))
  private void prepareStartRegionInjectTAIL(WorldGenerationProgressListener worldGenerationProgressListener, CallbackInfo callbackInfo) {
    ServerWorld serverWorld = ((MinecraftServer)(Object)this).getOverworld();
    ServerWorldAccessor worldAccessor = (ServerWorldAccessor)serverWorld;
    SaveProperties saveProperties = ((MinecraftServer)(Object)this).getSaveProperties();
    long l = saveProperties.getGeneratorOptions().getSeed();
    worldAccessor.setEnderDragonFight(new EnderDragonFight(serverWorld, l, saveProperties.getDragonFight()));
  }
  
}
