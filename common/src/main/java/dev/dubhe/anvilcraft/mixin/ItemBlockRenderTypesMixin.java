package dev.dubhe.anvilcraft.mixin;

import dev.dubhe.anvilcraft.init.ModBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;

@Mixin(ItemBlockRenderTypes.class)
abstract class ItemBlockRenderTypesMixin {
    @Inject(method = "method_23685", at = @At("RETURN"))
    private static void registerRenderTypes(@NotNull HashMap<Block, RenderType> map, CallbackInfo ci) {
        map.put(ModBlocks.SPECTRAL_ANVIL.get(), RenderType.translucent());
        map.put(ModBlocks.TRANSPARENT_CRAFTING_TABLE.get(), RenderType.translucent());
        map.put(ModBlocks.HEAVY_IRON_WALL.get(), RenderType.cutout());
        map.put(ModBlocks.HEAVY_IRON_DOOR.get(), RenderType.cutout());
        map.put(ModBlocks.HEAVY_IRON_TRAPDOOR.get(), RenderType.cutout());
        map.put(ModBlocks.BATCH_CRAFTER.get(), RenderType.cutout());
    }
}
