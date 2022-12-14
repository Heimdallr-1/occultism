package com.github.klikli_dev.occultism.datagen.worldgen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

public class OccultismRegistries {
    private static final RegistrySetBuilder BUILDER = (new RegistrySetBuilder())
            .add(Registries.CONFIGURED_FEATURE, (RegistrySetBuilder.RegistryBootstrap) ConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, PlacedFeatures::bootstrap)
            .add(Registries.BIOME, OccultismRegistries::bootstrapBiomes)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, BiomeModifiers::bootstrap);

    public static HolderLookup.Provider createLookup() {
        var registryaccess = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
        var holderlookup = BUILDER.build(registryaccess);
        return holderlookup;
    }

    public static void bootstrapBiomes(BootstapContext<Biome> context) {
        //doesn't need to do anything, just gives us acccess to a biome registry with empty tag lookup in our other boopstrap contexts
    }
}
