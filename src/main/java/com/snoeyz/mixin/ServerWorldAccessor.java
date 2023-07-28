package com.snoeyz.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.server.world.ServerWorld;

@Mixin(ServerWorld.class)
public interface ServerWorldAccessor {

  @Accessor("enderDragonFight")
  void setEnderDragonFight(EnderDragonFight enderDragonFight);
}
