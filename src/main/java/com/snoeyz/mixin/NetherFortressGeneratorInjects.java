package com.snoeyz.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.snoeyz.generator.fortress.CorridorPortal;

import net.minecraft.structure.NetherFortressGenerator;
import net.minecraft.structure.NetherFortressGenerator.Piece;
import net.minecraft.structure.NetherFortressGenerator.PieceData;
import net.minecraft.structure.StructurePiecesHolder;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;

@Mixin(NetherFortressGenerator.class)
public class NetherFortressGeneratorInjects {

  @Inject(method = "createPiece(Lnet/minecraft/structure/NetherFortressGenerator$PieceData;Lnet/minecraft/structure/StructurePiecesHolder;Lnet/minecraft/util/math/random/Random;IIILnet/minecraft/util/math/Direction;I)Lnet/minecraft/structure/NetherFortressGenerator$Piece;", at = @At("RETURN"), cancellable = true)
  private static void createPieceInject(PieceData pieceData, StructurePiecesHolder holder, Random random, int x, int y, int z, Direction orientation, int chainLength, CallbackInfoReturnable<Piece> callbackInfo) {
    if (pieceData.pieceType == CorridorPortal.class) {
      callbackInfo.setReturnValue(CorridorPortal.create(holder, random, x, y, z, orientation, chainLength));
    }
  }
}
