package com.snoeyz.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.snoeyz.generator.fortress.CorridorPortal;

import net.minecraft.structure.NetherFortressGenerator;
import net.minecraft.structure.NetherFortressGenerator.PieceData;

@Mixin(NetherFortressGenerator.Start.class)
public class NetherFortressGeneratorStartInjects {
  
  @Inject(method = "<init>(Lnet/minecraft/util/math/random/Random;II)V", at = @At("TAIL"))
  private void initInject(CallbackInfo callbackInfo) {
    ((NetherFortressGenerator.Start)(Object)this).corridorPieces.add(new PieceData(CorridorPortal.class, 10000, 1, false));
  }
}
