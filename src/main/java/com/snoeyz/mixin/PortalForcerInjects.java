package com.snoeyz.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.util.math.Direction;
import net.minecraft.world.PortalForcer;

@Mixin(PortalForcer.class)
public class PortalForcerInjects {
  @ModifyVariable(method = "createPortal", at = @At("HEAD"))
  private Direction.Axis createPortalInject(Direction.Axis axis) {
    return Direction.Axis.X;
  }
  
}
