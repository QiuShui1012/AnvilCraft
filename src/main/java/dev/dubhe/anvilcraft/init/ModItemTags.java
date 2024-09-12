package dev.dubhe.anvilcraft.init;

import dev.dubhe.anvilcraft.AnvilCraft;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class ModItemTags {
    public static final TagKey<Item> FLOUR = bindC("flour");
    public static final TagKey<Item> WHEAT_FLOUR = bindC("flour/wheat");
    public static final TagKey<Item> DOUGH = bindC("dough");
    public static final TagKey<Item> WHEAT_DOUGH = bindC("dough/wheat");
    public static final TagKey<Item> PLATES = bindC("plates");
    public static final TagKey<Item> STONE = bindC("stone");
    public static final TagKey<Item> GLASS = bindC("glass/silica");
    public static final TagKey<Item> FOODS = bindC("foods");

    public static final TagKey<Item> GOLD_PLATES = bindC("plates/gold");
    public static final TagKey<Item> IRON_PLATES = bindC("plates/iron");
    public static final TagKey<Item> COPPER_PLATES = bindC("plates/copper");
    public static final TagKey<Item> TUNGSTEN_PLATES = bindC("plates/tungsten");
    public static final TagKey<Item> TITANIUM_PLATES = bindC("plates/titanium");
    public static final TagKey<Item> ZINC_PLATES = bindC("plates/zinc");
    public static final TagKey<Item> TIN_PLATES = bindC("plates/tin");
    public static final TagKey<Item> LEAD_PLATES = bindC("plates/lead");
    public static final TagKey<Item> SILVER_PLATES = bindC("plates/silver");
    public static final TagKey<Item> URANIUM_PLATES = bindC("plates/uranium");
    public static final TagKey<Item> BRONZE_PLATES = bindC("plates/bronze");
    public static final TagKey<Item> BRASS_PLATES = bindC("plates/brass");

    public static final TagKey<Item> TUNGSTEN_INGOTS = bindC("ingots/tungsten");
    public static final TagKey<Item> TITANIUM_INGOTS = bindC("ingots/titanium");
    public static final TagKey<Item> ZINC_INGOTS = bindC("ingots/zinc");
    public static final TagKey<Item> TIN_INGOTS = bindC("ingots/tin");
    public static final TagKey<Item> LEAD_INGOTS = bindC("ingots/lead");
    public static final TagKey<Item> SILVER_INGOTS = bindC("ingots/silver");
    public static final TagKey<Item> URANIUM_INGOTS = bindC("ingots/uranium");
    public static final TagKey<Item> BRONZE_INGOTS = bindC("ingots/bronze");
    public static final TagKey<Item> BRASS_INGOTS = bindC("ingots/brass");

    public static final TagKey<Item> TUNGSTEN_NUGGETS = bindC("nuggets/tungsten");
    public static final TagKey<Item> TITANIUM_NUGGETS = bindC("nuggets/titanium");
    public static final TagKey<Item> ZINC_NUGGETS = bindC("nuggets/zinc");
    public static final TagKey<Item> TIN_NUGGETS = bindC("nuggets/tin");
    public static final TagKey<Item> LEAD_NUGGETS = bindC("nuggets/lead");
    public static final TagKey<Item> SILVER_NUGGETS = bindC("nuggets/silver");
    public static final TagKey<Item> URANIUM_NUGGETS = bindC("nuggets/uranium");
    public static final TagKey<Item> BRONZE_NUGGETS = bindC("nuggets/bronze");
    public static final TagKey<Item> BRASS_NUGGETS = bindC("nuggets/brass");
    public static final TagKey<Item> COPPER_NUGGETS = bindC("nuggets/copper");

    public static final TagKey<Item> QUARTZ_BLOCKS = bindC("storage_blocks/quartz");
    public static final TagKey<Item> AMETHYST_BLOCKS = bindC("storage_blocks/amethyst");

    public static final TagKey<Item> ORES = bindC("ores");
    public static final TagKey<Item> ZINC_ORES = bindC("ores/zinc");
    public static final TagKey<Item> TIN_ORES = bindC("ores/tin");
    public static final TagKey<Item> TITANIUM_ORES = bindC("ores/titanium");
    public static final TagKey<Item> TUNGSTEN_ORES = bindC("ores/tungsten");
    public static final TagKey<Item> LEAD_ORES = bindC("ores/lead");
    public static final TagKey<Item> SILVER_ORES = bindC("ores/silver");
    public static final TagKey<Item> URANIUM_ORES = bindC("ores/uranium");

    public static final TagKey<Item> RAW_ORES = bindC("raw_materials");
    public static final TagKey<Item> RAW_ZINC = bindC("raw_materials/zinc");
    public static final TagKey<Item> RAW_TIN = bindC("raw_materials/tin");
    public static final TagKey<Item> RAW_TITANIUM = bindC("raw_materials/titanium");
    public static final TagKey<Item> RAW_TUNGSTEN = bindC("raw_materials/tungsten");
    public static final TagKey<Item> RAW_LEAD = bindC("raw_materials/lead");
    public static final TagKey<Item> RAW_SILVER = bindC("raw_materials/silver");
    public static final TagKey<Item> RAW_URANIUM = bindC("raw_materials/uranium");

    public static final TagKey<Item> VEGETABLES = bindC("vegetables");
    public static final TagKey<Item> SEEDS = bindC("seeds");
    public static final TagKey<Item> BERRIES = bindC("berries");
    public static final TagKey<Item> WRENCH = bindC("tools/wrench");

    public static final TagKey<Item> ROYAL_STEEL_PICKAXE_BASE = bind("royal_steel_pickaxe_base");
    public static final TagKey<Item> ROYAL_STEEL_AXE_BASE = bind("royal_steel_axe_base");
    public static final TagKey<Item> ROYAL_STEEL_HOE_BASE = bind("royal_steel_hoe_base");
    public static final TagKey<Item> ROYAL_STEEL_SHOVEL_BASE = bind("royal_steel_shovel_base");
    public static final TagKey<Item> ROYAL_STEEL_SWORD_BASE = bind("royal_steel_sword_base");
    public static final TagKey<Item> CAPACITOR = bind("capacitor");
    public static final TagKey<Item> GEMS = bind("gems");
    public static final TagKey<Item> GEM_BLOCKS = bind("gem_blocks");
    public static final TagKey<Item> DEAD_TUBE = bind("dead_tube");
    public static final TagKey<Item> VOID_RESISTANT = bind("void_resistant");
    public static final TagKey<Item> REINFORCED_CONCRETE = bind("reinforced_concrete");
    public static final TagKey<Item> SEEDS_PACK_CONTENT = bind("seeds_pack_content");
    public static final TagKey<Item> FIRE_STARTER = bind("fire_starter");
    public static final TagKey<Item> UNBROKEN_FIRE_STARTER = bind("unbroken_fire_starter");
    public static final TagKey<Item> NETHERITE_BLOCK = bind("netherite_block");
    public static final TagKey<Item> EXPLOSION_PROOF = bind("explosion_proof");

    public static @NotNull TagKey<Item> bindC(String id) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", id));
    }

    public static @NotNull TagKey<Item> bind(String id) {
        return TagKey.create(Registries.ITEM, AnvilCraft.of(id));
    }
}
