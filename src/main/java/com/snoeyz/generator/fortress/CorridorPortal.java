package com.snoeyz.generator.fortress;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.block.PaneBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.NetherFortressGenerator;
import net.minecraft.structure.NetherFortressGenerator.Start;
import net.minecraft.structure.StructureContext;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePiecesHolder;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class CorridorPortal extends NetherFortressGenerator.Piece {
    protected static final int SIZE_X = 11;
    protected static final int SIZE_Y = 16;
    protected static final int SIZE_Z = 16;
    private boolean spawnerPlaced;

    public CorridorPortal(int chainLength, BlockBox boundingBox, Direction orientation) {
        super(ModdedStructurePieceType.NETHER_FORTRESS_CORRIDOR_PORTAL, chainLength, boundingBox);
        this.setOrientation(orientation);
    }

    public CorridorPortal(NbtCompound nbt) {
        super(ModdedStructurePieceType.NETHER_FORTRESS_CORRIDOR_PORTAL, nbt);
        this.spawnerPlaced = nbt.getBoolean("Mob");
    }

    @Override
    protected void writeNbt(StructureContext context, NbtCompound nbt) {
        super.writeNbt(context, nbt);
        nbt.putBoolean("Mob", this.spawnerPlaced);
    }

    @Override
    public void fillOpenings(StructurePiece start, StructurePiecesHolder holder, Random random) {
        this.fillForwardOpening((Start)start, holder, random, 5, 3, true);
    }

    public static CorridorPortal create(StructurePiecesHolder holder, Random random, int x, int y, int z, Direction orientation, int chainLength) {
        BlockBox blockBox = BlockBox.rotated(x, y, z, -4, -1, 0, 11, 8, 16, orientation);
        if (!CorridorPortal.isInBounds(blockBox) || holder.getIntersecting(blockBox) != null) {
            return null;
        }
        return new CorridorPortal(chainLength, blockBox, orientation);
    }

    @Override
    public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox, ChunkPos chunkPos, BlockPos pivot) {
        BlockPos.Mutable blockPos;
        int j;
        this.fillWithOutline(world, chunkBox, 0, 1, 0, 10, 10, 15, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
        this.fillWithOutline(world, chunkBox, 1, 3, 1, 9, 9, 14, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
        //this.generateEntrance(world, random, chunkBox, Piece.EntranceType.GRATES, 4, 1, 0);
        // this.fillWithOutline(world, chunkBox, 0, 3, 0, 12, 4, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
        // this.fillWithOutline(world, chunkBox, 0, 5, 0, 12, 13, 12, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);

        // Generate Entrance
        int entranceX = 4;
        int entranceY = 3;
        int entranceZ = 0;
        this.addBlock(world, Blocks.CAVE_AIR.getDefaultState(), entranceX + 1, entranceY, entranceZ, boundingBox);
        this.addBlock(world, Blocks.CAVE_AIR.getDefaultState(), entranceX + 1, entranceY + 1, entranceZ, boundingBox);
        this.addBlock(world, (BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.WEST, true), entranceX, entranceY, entranceZ, boundingBox);
        this.addBlock(world, (BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.WEST, true), entranceX, entranceY + 1, entranceZ, boundingBox);
        this.addBlock(world, (BlockState)((BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.EAST, true)).with(PaneBlock.WEST, true), entranceX, entranceY + 2, entranceZ, boundingBox);
        this.addBlock(world, (BlockState)((BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.EAST, true)).with(PaneBlock.WEST, true), entranceX + 1, entranceY + 2, entranceZ, boundingBox);
        this.addBlock(world, (BlockState)((BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.EAST, true)).with(PaneBlock.WEST, true), entranceX + 2, entranceY + 2, entranceZ, boundingBox);
        this.addBlock(world, (BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.EAST, true), entranceX + 2, entranceY + 1, entranceZ, boundingBox);
        this.addBlock(world, (BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.EAST, true), entranceX + 2, entranceY, entranceZ, boundingBox);

        // Generate Pillars
        int i = 6;
        for (i = 6; i <= 8; ++i) {
            for (j = 0; j <= 2; ++j) {
                this.fillDownwards(world, Blocks.NETHER_BRICKS.getDefaultState(), i, 0, j, chunkBox);
                this.fillDownwards(world, Blocks.NETHER_BRICKS.getDefaultState(), i, 0, 12 - j, chunkBox);
            }
        }
        for (i = 0; i <= 2; ++i) {
            for (j = 4; j <= 8; ++j) {
                this.fillDownwards(world, Blocks.NETHER_BRICKS.getDefaultState(), i, 0, j, chunkBox);
                this.fillDownwards(world, Blocks.NETHER_BRICKS.getDefaultState(), 12 - i, 0, j, chunkBox);
            }
        }
        this.fillWithOutline(world, chunkBox, 1, 8, 1, 1, 8, 14, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
        this.fillWithOutline(world, chunkBox, 9, 8, 1, 9, 8, 14, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
        this.fillWithOutline(world, chunkBox, 2, 8, 1, 8, 8, 2, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
        this.fillWithOutline(world, chunkBox, 2, 8, 14, 8, 8, 14, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
        this.fillWithOutline(world, chunkBox, 1, 3, 1, 2, 3, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
        this.fillWithOutline(world, chunkBox, 8, 3, 1, 9, 3, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
        this.fillWithOutline(world, chunkBox, 1, 3, 1, 1, 3, 3, Blocks.LAVA.getDefaultState(), Blocks.LAVA.getDefaultState(), false);
        this.fillWithOutline(world, chunkBox, 9, 3, 1, 9, 3, 3, Blocks.LAVA.getDefaultState(), Blocks.LAVA.getDefaultState(), false);
        this.fillWithOutline(world, chunkBox, 3, 3, 8, 7, 3, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
        this.fillWithOutline(world, chunkBox, 4, 3, 9, 6, 3, 11, Blocks.LAVA.getDefaultState(), Blocks.LAVA.getDefaultState(), false);
        BlockState blockState = (BlockState)((BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.NORTH, true)).with(PaneBlock.SOUTH, true);
        BlockState blockState2 = (BlockState)((BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.WEST, true)).with(PaneBlock.EAST, true);
        for (j = 3; j < 14; j += 2) {
            this.fillWithOutline(world, chunkBox, 0, 5, j, 0, 7, j, blockState, blockState, false);
            this.fillWithOutline(world, chunkBox, 10, 5, j, 10, 7, j, blockState, blockState, false);
        }
        for (j = 2; j < 9; j += 2) {
            this.fillWithOutline(world, chunkBox, j, 6, 15, j, 7, 15, blockState2, blockState2, false);
        }
        BlockState blockState3 = (BlockState)Blocks.NETHER_BRICK_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.NORTH);
        this.fillWithOutline(world, chunkBox, 4, 3, 5, 6, 3, 7, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
        this.fillWithOutline(world, chunkBox, 4, 4, 6, 6, 4, 7, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
        this.fillWithOutline(world, chunkBox, 4, 5, 7, 6, 5, 7, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), false);
        for (int k = 4; k <= 6; ++k) {
            this.addBlock(world, blockState3, k, 3, 4, chunkBox);
            this.addBlock(world, blockState3, k, 4, 5, chunkBox);
            this.addBlock(world, blockState3, k, 5, 6, chunkBox);
        }
        BlockState blockState4 = (BlockState)Blocks.END_PORTAL_FRAME.getDefaultState().with(EndPortalFrameBlock.FACING, Direction.NORTH);
        BlockState blockState5 = (BlockState)Blocks.END_PORTAL_FRAME.getDefaultState().with(EndPortalFrameBlock.FACING, Direction.SOUTH);
        BlockState blockState6 = (BlockState)Blocks.END_PORTAL_FRAME.getDefaultState().with(EndPortalFrameBlock.FACING, Direction.EAST);
        BlockState blockState7 = (BlockState)Blocks.END_PORTAL_FRAME.getDefaultState().with(EndPortalFrameBlock.FACING, Direction.WEST);
        boolean bl = true;
        boolean[] bls = new boolean[12];
        for (int l = 0; l < bls.length; ++l) {
            bls[l] = random.nextFloat() > 0.9f;
            bl &= bls[l];
        }
        this.addBlock(world, (BlockState)blockState4.with(EndPortalFrameBlock.EYE, bls[0]), 4, 5, 8, chunkBox);
        this.addBlock(world, (BlockState)blockState4.with(EndPortalFrameBlock.EYE, bls[1]), 5, 5, 8, chunkBox);
        this.addBlock(world, (BlockState)blockState4.with(EndPortalFrameBlock.EYE, bls[2]), 6, 5, 8, chunkBox);
        this.addBlock(world, (BlockState)blockState5.with(EndPortalFrameBlock.EYE, bls[3]), 4, 5, 12, chunkBox);
        this.addBlock(world, (BlockState)blockState5.with(EndPortalFrameBlock.EYE, bls[4]), 5, 5, 12, chunkBox);
        this.addBlock(world, (BlockState)blockState5.with(EndPortalFrameBlock.EYE, bls[5]), 6, 5, 12, chunkBox);
        this.addBlock(world, (BlockState)blockState6.with(EndPortalFrameBlock.EYE, bls[6]), 3, 5, 9, chunkBox);
        this.addBlock(world, (BlockState)blockState6.with(EndPortalFrameBlock.EYE, bls[7]), 3, 5, 10, chunkBox);
        this.addBlock(world, (BlockState)blockState6.with(EndPortalFrameBlock.EYE, bls[8]), 3, 5, 11, chunkBox);
        this.addBlock(world, (BlockState)blockState7.with(EndPortalFrameBlock.EYE, bls[9]), 7, 5, 9, chunkBox);
        this.addBlock(world, (BlockState)blockState7.with(EndPortalFrameBlock.EYE, bls[10]), 7, 5, 10, chunkBox);
        this.addBlock(world, (BlockState)blockState7.with(EndPortalFrameBlock.EYE, bls[11]), 7, 5, 11, chunkBox);
        if (bl) {
            BlockState blockState8 = Blocks.NETHER_PORTAL.getDefaultState().with(NetherPortalBlock.AXIS, Direction.Axis.Z);
            this.addBlock(world, blockState8, 4, 5, 9, chunkBox);
            this.addBlock(world, blockState8, 5, 5, 9, chunkBox);
            this.addBlock(world, blockState8, 6, 5, 9, chunkBox);
            this.addBlock(world, blockState8, 4, 5, 10, chunkBox);
            this.addBlock(world, blockState8, 5, 5, 10, chunkBox);
            this.addBlock(world, blockState8, 6, 5, 10, chunkBox);
            this.addBlock(world, blockState8, 4, 5, 11, chunkBox);
            this.addBlock(world, blockState8, 5, 5, 11, chunkBox);
            this.addBlock(world, blockState8, 6, 5, 11, chunkBox);
        }
        if (!this.spawnerPlaced && chunkBox.contains(blockPos = this.offsetPos(5, 5, 6))) {
            this.spawnerPlaced = true;
            world.setBlockState(blockPos, Blocks.SPAWNER.getDefaultState(), Block.NOTIFY_LISTENERS);
            BlockEntity blockEntity = world.getBlockEntity(blockPos);
            if (blockEntity instanceof MobSpawnerBlockEntity) {
                MobSpawnerBlockEntity mobSpawnerBlockEntity = (MobSpawnerBlockEntity)blockEntity;
                mobSpawnerBlockEntity.setEntityType(EntityType.ENDERMITE, random);
            }
        }
    }
}
