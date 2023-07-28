package com.snoeyz.generator.fortress;

import java.util.Locale;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.structure.StructurePieceType.Simple;

public interface ModdedStructurePieceType {
    public static final StructurePieceType NETHER_FORTRESS_CORRIDOR_PORTAL = register(CorridorPortal::new, "NePortal");

    public static void init() {
        
    }

    private static StructurePieceType register(StructurePieceType type, String id) {
        return Registry.register(Registries.STRUCTURE_PIECE, id.toLowerCase(Locale.ROOT), type);
    }

    private static StructurePieceType register(Simple type, String id) {
        return register((StructurePieceType)type, id);
    }
}
