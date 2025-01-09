package dev.dubhe.anvilcraft.data.recipe;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import dev.dubhe.anvilcraft.AnvilCraft;
import dev.dubhe.anvilcraft.init.ModBlocks;
import dev.dubhe.anvilcraft.recipe.multiblock.BlockPredicateWithState;
import dev.dubhe.anvilcraft.recipe.multiblock.ModifySpawnerAction;
import dev.dubhe.anvilcraft.recipe.multiblock.MultiblockConversionRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;

public class MultiBlockConversionRecipeLoader {
    public static void init(RegistrateRecipeProvider provider) {
        MultiblockConversionRecipe.builder()
            .inputLayer("ABA", "CDC", "ABA")
            .inputLayer("E E", " F ", "E E")
            .inputLayer("ABA", "C C", "ABA")
            .outputLayer("ABA", "C C", "ABA")
            .outputLayer("E E", " F ", "E E")
            .outputLayer("ABA", "C C", "ABA")
            .inputSymbol('A', ModBlocks.CURSED_GOLD_BLOCK.get())
            .outputSymbol('A', Blocks.SCULK)
            .inputSymbol('B', BlockPredicateWithState.of(Blocks.CHAIN)
                .hasState("axis", "x")
            )
            .outputSymbol('B', Blocks.COBWEB)
            .inputSymbol('C', BlockPredicateWithState.of(Blocks.CHAIN)
                .hasState("axis", "z")
            )
            .outputSymbol('C', Blocks.COBWEB)
            .inputSymbol('D', Blocks.SOUL_FIRE)
            .inputSymbol('E', BlockPredicateWithState.of(Blocks.CHAIN)
                .hasState("axis", "y")
            )
            .outputSymbol('E', Blocks.COBWEB)
            .inputSymbol('F', ModBlocks.RESENTFUL_AMBER_BLOCK.get())
            .outputSymbol('F', Blocks.SPAWNER)
            .modifySpawnerAction(new ModifySpawnerAction(
                new BlockPos(1, 1, 1),
                new BlockPos(1, 1, 1)))
            .save(provider, AnvilCraft.of("multiblock_conversion/spawner"));

        MultiblockConversionRecipe.builder()
            .inputLayer("AAA", "AAA", "AAA")
            .inputLayer(" B ", "BBB", " B ")
            .inputLayer("   ", " C ", "   ")
            .inputSymbol('A', "anvilcraft:cake_block")
            .inputSymbol('B', "anvilcraft:berry_cake_block")
            .inputSymbol('C', "anvilcraft:chocolate_cake_block")
            .outputLayer("ABC", "DEF", "GHI")
            .outputLayer("JKL", "MNO", "PQR")
            .outputLayer("STU", "VWX", "YZ[")
            .outputSymbol('A', BlockPredicateWithState.of("anvilcraft:large_cake")
                .hasState("half", "bottom_wn")
            )
            .outputSymbol('B', BlockPredicateWithState.of("anvilcraft:large_cake")
                .hasState("half", "bottom_n")
            )
            .outputSymbol('C', BlockPredicateWithState.of("anvilcraft:large_cake")
                .hasState("half", "bottom_en")
            )
            .outputSymbol('D', BlockPredicateWithState.of("anvilcraft:large_cake")
                .hasState("half", "bottom_w")
            )
            .outputSymbol('E', BlockPredicateWithState.of("anvilcraft:large_cake")
                .hasState("half", "bottom_center")
            )
            .outputSymbol('F', BlockPredicateWithState.of("anvilcraft:large_cake")
                .hasState("half", "bottom_e")
            )
            .outputSymbol('G', BlockPredicateWithState.of("anvilcraft:large_cake")
                .hasState("half", "bottom_ws")
            )
            .outputSymbol('H', BlockPredicateWithState.of("anvilcraft:large_cake")
                .hasState("half", "bottom_s")
            )
            .outputSymbol('I', BlockPredicateWithState.of("anvilcraft:large_cake")
                .hasState("half", "bottom_es")
            )
            .outputSymbol('J', BlockPredicateWithState.of("anvilcraft:large_cake")
                .hasState("half", "mid_wn")
            )
            .outputSymbol('K', BlockPredicateWithState.of("anvilcraft:large_cake")
                .hasState("half", "mid_n")
            )
            .outputSymbol('L', BlockPredicateWithState.of("anvilcraft:large_cake")
                .hasState("half", "mid_en")
            )
            .outputSymbol('M', BlockPredicateWithState.of("anvilcraft:large_cake")
                .hasState("half", "mid_w")
            )
            .outputSymbol('N', BlockPredicateWithState.of("anvilcraft:large_cake")
                .hasState("half", "mid_center")
            )
            .outputSymbol('O', BlockPredicateWithState.of("anvilcraft:large_cake")
                .hasState("half", "mid_e")
            )
            .outputSymbol('P', BlockPredicateWithState.of("anvilcraft:large_cake")
                .hasState("half", "mid_ws")
            )
            .outputSymbol('Q', BlockPredicateWithState.of("anvilcraft:large_cake")
                .hasState("half", "mid_s")
            )
            .outputSymbol('R', BlockPredicateWithState.of("anvilcraft:large_cake")
                .hasState("half", "mid_es")
            )
            .outputSymbol('S', BlockPredicateWithState.of("anvilcraft:large_cake")
                .hasState("half", "top_wn")
            )
            .outputSymbol('T', BlockPredicateWithState.of("anvilcraft:large_cake")
                .hasState("half", "top_n")
            )
            .outputSymbol('U', BlockPredicateWithState.of("anvilcraft:large_cake")
                .hasState("half", "top_en")
            )
            .outputSymbol('V', BlockPredicateWithState.of("anvilcraft:large_cake")
                .hasState("half", "top_w")
            )
            .outputSymbol('W', BlockPredicateWithState.of("anvilcraft:large_cake")
                .hasState("half", "top_center")
            )
            .outputSymbol('X', BlockPredicateWithState.of("anvilcraft:large_cake")
                .hasState("half", "top_e")
            )
            .outputSymbol('Y', BlockPredicateWithState.of("anvilcraft:large_cake")
                .hasState("half", "top_ws")
            )
            .outputSymbol('Z', BlockPredicateWithState.of("anvilcraft:large_cake")
                .hasState("half", "top_s")
            )
            .outputSymbol('[', BlockPredicateWithState.of("anvilcraft:large_cake")
                .hasState("half", "top_es")
            )
            .save(provider, AnvilCraft.of("multiblock_conversion/large_cake"));

        MultiblockConversionRecipe.builder()
            .inputLayer("ABA", "CDE", "AFA")
            .inputLayer("GGG", "GDG", "GGG")
            .inputLayer("HIH", "JHJ", "HIH")
            .inputSymbol('A', BlockPredicateWithState.of("anvilcraft:cut_heavy_iron_slab")
                .hasState("waterlogged", "false")
            )
            .inputSymbol('B', BlockPredicateWithState.of("anvilcraft:cut_heavy_iron_stairs")
                .hasState("facing", "south")
                .hasState("waterlogged", "false")
                .hasState("half", "bottom")
            )
            .inputSymbol('C', BlockPredicateWithState.of("anvilcraft:cut_heavy_iron_stairs")
                .hasState("facing", "east")
                .hasState("waterlogged", "false")
                .hasState("half", "bottom")
            )
            .inputSymbol('D', "anvilcraft:heavy_iron_column")
            .inputSymbol('E', BlockPredicateWithState.of("anvilcraft:cut_heavy_iron_stairs")
                .hasState("facing", "west")
                .hasState("waterlogged", "false")
                .hasState("half", "bottom")
            )
            .inputSymbol('F', BlockPredicateWithState.of("anvilcraft:cut_heavy_iron_stairs")
                .hasState("facing", "north")
                .hasState("waterlogged", "false")
                .hasState("half", "bottom")
            )
            .inputSymbol('G', "anvilcraft:heavy_iron_plate")
            .inputSymbol('H', "anvilcraft:polished_heavy_iron_block")
            .inputSymbol('I', BlockPredicateWithState.of("anvilcraft:heavy_iron_beam")
                .hasState("axis", "z")
            )
            .inputSymbol('J', BlockPredicateWithState.of("anvilcraft:heavy_iron_beam")
                .hasState("axis", "x")
            )
            .outputLayer("ABC", "DEF", "GHI")
            .outputLayer("JKL", "MNO", "PQR")
            .outputLayer("STU", "VWX", "YZ[")
            .outputSymbol('A', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("cube", "corner")
                .hasState("half", "bottom_wn")
            )
            .outputSymbol('B', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("cube", "corner")
                .hasState("half", "bottom_n")
            )
            .outputSymbol('C', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("cube", "corner")
                .hasState("half", "bottom_en")
            )
            .outputSymbol('D', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("cube", "corner")
                .hasState("half", "bottom_w")
            )
            .outputSymbol('E', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("cube", "corner")
                .hasState("half", "bottom_center")
            )
            .outputSymbol('F', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("cube", "corner")
                .hasState("half", "bottom_e")
            )
            .outputSymbol('G', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("cube", "corner")
                .hasState("half", "bottom_ws")
            )
            .outputSymbol('H', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("cube", "corner")
                .hasState("half", "bottom_s")
            )
            .outputSymbol('I', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("cube", "corner")
                .hasState("half", "bottom_es")
            )
            .outputSymbol('J', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("cube", "corner")
                .hasState("half", "mid_wn")
            )
            .outputSymbol('K', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("cube", "corner")
                .hasState("half", "mid_n")
            )
            .outputSymbol('L', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("cube", "corner")
                .hasState("half", "mid_en")
            )
            .outputSymbol('M', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("cube", "corner")
                .hasState("half", "mid_w")
            )
            .outputSymbol('N', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("cube", "center")
                .hasState("half", "mid_center")
            )
            .outputSymbol('O', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("cube", "corner")
                .hasState("half", "mid_e")
            )
            .outputSymbol('P', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("cube", "corner")
                .hasState("half", "mid_ws")
            )
            .outputSymbol('Q', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("cube", "corner")
                .hasState("half", "mid_s")
            )
            .outputSymbol('R', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("cube", "corner")
                .hasState("half", "mid_es")
            )
            .outputSymbol('S', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("cube", "corner")
                .hasState("half", "top_wn")
            )
            .outputSymbol('T', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("cube", "corner")
                .hasState("half", "top_n")
            )
            .outputSymbol('U', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("cube", "corner")
                .hasState("half", "top_en")
            )
            .outputSymbol('V', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("cube", "corner")
                .hasState("half", "top_w")
            )
            .outputSymbol('W', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("cube", "corner")
                .hasState("half", "top_center")
            )
            .outputSymbol('X', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("cube", "corner")
                .hasState("half", "top_e")
            )
            .outputSymbol('Y', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("cube", "corner")
                .hasState("half", "top_ws")
            )
            .outputSymbol('Z', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("cube", "corner")
                .hasState("half", "top_s")
            )
            .outputSymbol('[', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("cube", "corner")
                .hasState("half", "top_es")
            )
            .save(provider, AnvilCraft.of("multiblock_conversion/giant_anvil_1"));

        MultiblockConversionRecipe.builder()
            .inputLayer("AAA", "AAA", "AAA")
            .inputLayer("BBB", "BCB", "BBB")
            .inputLayer("DDD", "DDD", "DDD")
            .inputSymbol('A', "anvilcraft:cut_heavy_iron_block")
            .inputSymbol('B', "anvilcraft:heavy_iron_plate")
            .inputSymbol('C', "anvilcraft:heavy_iron_column")
            .inputSymbol('D', "anvilcraft:polished_heavy_iron_block")
            .outputLayer("ABC", "DEF", "GHI")
            .outputLayer("JKL", "MNO", "PQR")
            .outputLayer("STU", "VWX", "YZ[")
            .outputSymbol('A', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("half", "bottom_wn")
                .hasState("cube", "corner")
            )
            .outputSymbol('B', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("half", "bottom_n")
                .hasState("cube", "corner")
            )
            .outputSymbol('C', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("half", "bottom_en")
                .hasState("cube", "corner")
            )
            .outputSymbol('D', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("half", "bottom_w")
                .hasState("cube", "corner")
            )
            .outputSymbol('E', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("half", "bottom_center")
                .hasState("cube", "corner")
            )
            .outputSymbol('F', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("half", "bottom_e")
                .hasState("cube", "corner")
            )
            .outputSymbol('G', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("half", "bottom_ws")
                .hasState("cube", "corner")
            )
            .outputSymbol('H', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("half", "bottom_s")
                .hasState("cube", "corner")
            )
            .outputSymbol('I', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("half", "bottom_es")
                .hasState("cube", "corner")
            )
            .outputSymbol('J', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("half", "mid_wn")
                .hasState("cube", "corner")
            )
            .outputSymbol('K', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("half", "mid_n")
                .hasState("cube", "corner")
            )
            .outputSymbol('L', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("half", "mid_en")
                .hasState("cube", "corner")
            )
            .outputSymbol('M', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("half", "mid_w")
                .hasState("cube", "corner")
            )
            .outputSymbol('N', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("half", "mid_center")
                .hasState("cube", "center")
            )
            .outputSymbol('O', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("half", "mid_e")
                .hasState("cube", "corner")
            )
            .outputSymbol('P', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("half", "mid_ws")
                .hasState("cube", "corner")
            )
            .outputSymbol('Q', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("half", "mid_s")
                .hasState("cube", "corner")
            )
            .outputSymbol('R', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("half", "mid_es")
                .hasState("cube", "corner")
            )
            .outputSymbol('S', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("half", "top_wn")
                .hasState("cube", "corner")
            )
            .outputSymbol('T', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("half", "top_n")
                .hasState("cube", "corner")
            )
            .outputSymbol('U', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("half", "top_en")
                .hasState("cube", "corner")
            )
            .outputSymbol('V', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("half", "top_w")
                .hasState("cube", "corner")
            )
            .outputSymbol('W', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("half", "top_center")
                .hasState("cube", "corner")
            )
            .outputSymbol('X', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("half", "top_e")
                .hasState("cube", "corner")
            )
            .outputSymbol('Y', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("half", "top_ws")
                .hasState("cube", "corner")
            )
            .outputSymbol('Z', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("half", "top_s")
                .hasState("cube", "corner")
            )
            .outputSymbol('[', BlockPredicateWithState.of("anvilcraft:giant_anvil")
                .hasState("half", "top_es")
                .hasState("cube", "corner")
            )
            .save(provider, AnvilCraft.of("multiblock_conversion/giant_anvil_2"));
    }
}
