package dev.dubhe.anvilcraft.event.anvil;

import dev.dubhe.anvilcraft.init.ModRecipeTypes;
import dev.dubhe.anvilcraft.recipe.CompressRecipe;
import dev.dubhe.anvilcraft.recipe.BlockCrushRecipe;
import dev.dubhe.anvilcraft.recipe.ItemCrushRecipe;
import dev.dubhe.anvilcraft.recipe.MeshRecipe;
import dev.dubhe.anvilcraft.recipe.cache.RecipeCaches;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.storage.loot.LootContext;
import net.neoforged.bus.api.SubscribeEvent;
import dev.dubhe.anvilcraft.AnvilCraft;
import dev.dubhe.anvilcraft.api.IHasMultiBlock;
import dev.dubhe.anvilcraft.api.depository.ItemDepository;
import dev.dubhe.anvilcraft.api.event.entity.AnvilFallOnLandEvent;
import dev.dubhe.anvilcraft.api.event.entity.AnvilHurtEntityEvent;
import dev.dubhe.anvilcraft.block.CrabTrapBlock;
import dev.dubhe.anvilcraft.block.EmberAnvilBlock;
import dev.dubhe.anvilcraft.block.entity.CrabTrapBlockEntity;
import dev.dubhe.anvilcraft.init.ModBlockTags;
import dev.dubhe.anvilcraft.init.ModBlocks;
import dev.dubhe.anvilcraft.mixin.accessor.BaseSpawnerAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AnvilEventListener {

    /**
     * 侦听铁砧落地事件
     *
     * @param event 铁砧落地事件
     */
    @SubscribeEvent
    public void onLand(@NotNull AnvilFallOnLandEvent event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        dropCrabItems(level, pos);
        MinecraftServer server = level.getServer();
        if (null == server) return;
        BlockPos belowPos = pos.below();
        BlockState state = level.getBlockState(belowPos);

        handleCompressRecipe(level, belowPos);
        handleBlockCrushRecipe(level, belowPos);
        if (state.is(Blocks.IRON_TRAPDOOR) && state.getValue(TrapDoorBlock.HALF) == Half.TOP && !state.getValue(TrapDoorBlock.OPEN))
            handleItemCrushRecipe(level, pos);
        if (state.is(Blocks.SCAFFOLDING)) handleMeshRecipe(level, pos);

        if (state.is(Blocks.REDSTONE_BLOCK)) redstoneEmp(level, belowPos, event.getFallDistance());
        if (state.is(Blocks.SPAWNER)) hitSpawner(level, belowPos, event.getFallDistance());
        if (state.is(Blocks.BEEHIVE) || state.is(Blocks.BEE_NEST)) hitBeeNest(level, state, belowPos);
//        for (AnvilBehavior behavior : AnvilBehavior.BEHAVIORS) {
//            if (behavior.handle(level, belowPos, state, event.getFallDistance())) break;
//        }
        belowPos = belowPos.below();
        state = level.getBlockState(belowPos);
        if (state.is(Blocks.STONECUTTER)) brokeBlock(level, belowPos.above(), event);

//        AnvilCraftingContext context = new AnvilCraftingContext(level, pos, event.getEntity());
//        Optional<AnvilRecipe> optional = AnvilRecipeManager.getAnvilRecipeList().stream()
//            .filter(recipe -> !recipe.getAnvilRecipeType().equals(AnvilRecipeType.MULTIBLOCK_CRAFTING)
//                && recipe.matches(context, level))
//            .findFirst();
//        optional.ifPresent(anvilRecipe -> anvilProcess(anvilRecipe, context, event));
    }

    private void handleBlockCrushRecipe(Level level, final BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        level.getRecipeManager().getRecipeFor(
            ModRecipeTypes.BLOCK_CRUSH_TYPE.get(),
            new BlockCrushRecipe.Input(state.getBlock()), level
        ).ifPresent(recipe -> level.setBlockAndUpdate(pos, recipe.value().result.defaultBlockState()));
    }

    private void handleItemCrushRecipe(Level level, final BlockPos pos) {
        Map<ItemEntity, ItemStack> items = level.getEntitiesOfClass(ItemEntity.class, new AABB(pos)).stream()
            .map(it -> Map.entry(it, it.getItem()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        ItemCrushRecipe.Input input = new ItemCrushRecipe.Input(items.values().stream().toList());
        level.getRecipeManager().getRecipeFor(ModRecipeTypes.ITEM_CRUSH_TYPE.get(), input, level).ifPresent(recipe -> {
            int times = recipe.value().getMaxCraftTime(input);
            ItemStack result = recipe.value().result.copy();
            result.setCount(times * result.getCount());
            for (int i = 0; i < times; i++) {
                for (ItemStack stack : items.values()) {
                    for (Ingredient ingredient : recipe.value().getIngredients()) {
                        if (ingredient.test(stack)) {
                            stack.shrink(1);
                        }
                    }
                }
            }
            dropItems(List.of(result), level, pos.below().getCenter());
        });
        items.forEach((k, v) -> {
            k.discard();
            if (v.isEmpty()) {
                return;
            }
            Vec3 entityPos = k.position();
            Vec3 movement = k.getDeltaMovement();
            ItemEntity entity = new ItemEntity(
                level,
                entityPos.x,
                entityPos.y,
                entityPos.z,
                v.copy(),
                movement.x,
                movement.y,
                movement.z
            );
            level.addFreshEntity(entity);
        });
    }

    private void handleCompressRecipe(Level level, final BlockPos pos) {
        List<Block> inputs = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            inputs.add(level.getBlockState(pos.below(i)).getBlock());
        }
        level.getRecipeManager().getRecipeFor(
            ModRecipeTypes.COMPRESS_TYPE.get(),
            new CompressRecipe.Input(inputs), level
        ).ifPresent(recipe -> {
            for (int i = 0; i < recipe.value().inputs.size(); i++) {
                level.setBlockAndUpdate(pos.below(i), Blocks.AIR.defaultBlockState());
            }
            level.setBlockAndUpdate(pos.below(recipe.value().inputs.size() - 1), recipe.value().result.defaultBlockState());
        });
    }

    private void handleMeshRecipe(Level level, final BlockPos pos) {
        List<ItemEntity> entities = level.getEntitiesOfClass(ItemEntity.class, new AABB(pos));
        for (ItemEntity entity : entities) {
            ItemStack stack = entity.getItem();
            List<RecipeHolder<MeshRecipe>> cacheMeshRecipes = RecipeCaches.getCacheMeshRecipes(stack);
            if (cacheMeshRecipes != null && !cacheMeshRecipes.isEmpty()) {
                LootContext context = new LootContext.Builder(new LootParams((ServerLevel) level, Map.of(), Map.of(), 0)).create(Optional.empty());
                Object2IntMap<Item> itemCounts = new Object2IntOpenHashMap<>();
                for (int i = 0; i < stack.getCount(); i++) {
                    for (RecipeHolder<MeshRecipe> recipe : cacheMeshRecipes) {
                        int amount = recipe.value().resultAmount.getInt(context);
                        itemCounts.mergeInt(recipe.value().result.getItem(), amount, Integer::sum);
                    }
                }
                List<ItemStack> outputs = itemCounts.object2IntEntrySet().stream()
                    .map(entry -> new ItemStack(entry.getKey(), entry.getIntValue()))
                    .toList();
                dropItems(outputs, level, pos.below().getCenter());
                entity.remove(Entity.RemovalReason.DISCARDED);
            }
        }
    }

    private void hitBeeNest(Level level, BlockState state, BlockPos pos) {
        if (!state.hasBlockEntity()) return;
        int honeyLevel = state.getValue(BeehiveBlock.HONEY_LEVEL);
        if (honeyLevel < BeehiveBlock.MAX_HONEY_LEVELS) return;
        BlockPos potPos = pos.below();
        BlockState pot = level.getBlockState(potPos);
        if (pot.is(Blocks.CAULDRON)) {
            level.setBlockAndUpdate(pos, state.setValue(BeehiveBlock.HONEY_LEVEL, 0));
            level.setBlockAndUpdate(
                potPos,
                ModBlocks.HONEY_CAULDRON.getDefaultState()
            );
            level.setBlockAndUpdate(
                potPos,
                level.getBlockState(potPos)
                    .setValue(LayeredCauldronBlock.LEVEL, 1)
            );
        } else {
            if (pot.is(ModBlocks.HONEY_CAULDRON.get())) {
                int cauldronHoneyLevel = pot.getValue(LayeredCauldronBlock.LEVEL);
                level.setBlockAndUpdate(pos, state.setValue(BeehiveBlock.HONEY_LEVEL, 0));
                if (cauldronHoneyLevel < LayeredCauldronBlock.MAX_FILL_LEVEL) {
                    level.setBlockAndUpdate(
                        potPos,
                        pot.setValue(LayeredCauldronBlock.LEVEL, cauldronHoneyLevel + 1)
                    );
                } else {
                    level.setBlockAndUpdate(
                        potPos,
                        Blocks.CAULDRON.defaultBlockState()
                    );
                    this.returnItems(level, potPos, List.of(Items.HONEY_BLOCK.getDefaultInstance()));
                }
            }
        }
    }

    private void returnItems(@NotNull Level level, @NotNull BlockPos pos, @NotNull List<ItemStack> items) {
        for (ItemStack item : items) {
            ItemStack type = item.copy();
            type.setCount(1);
            int maxSize = item.getMaxStackSize();
            int count = item.getCount();
            while (count > 0) {
                int size = Math.min(maxSize, count);
                count -= size;
                ItemStack stack = type.copy();
                stack.setCount(size);
                Vec3 vec3 = pos.getCenter();
                ItemEntity entity = new ItemEntity(level, vec3.x, vec3.y, vec3.z, stack, 0.0d, 0.0d, 0.0d);
                level.addFreshEntity(entity);
            }
        }
    }

    private void hitSpawner(Level level, BlockPos pos, float fallDistance) {
        if (level instanceof ServerLevel serverLevel) {
            RandomSource randomSource = serverLevel.getRandom();
            float f = randomSource.nextFloat();
            if (fallDistance < 1) {
                fallDistance = 1.1f;
            }
            if (f <= (1 / fallDistance)) {
                return;
            }
            if (level.getBlockEntity(pos) instanceof SpawnerBlockEntity blockEntity) {

                BaseSpawner spawner = blockEntity.getSpawner();
                BaseSpawnerAccessor accessor = (BaseSpawnerAccessor) spawner;
                SpawnData spawnData = accessor.invoker$getOrCreateNextSpawnData(level, randomSource, pos);
                spawnEntities(spawnData, serverLevel, pos, randomSource, accessor);
            }
        }
    }

    private void spawnEntities(
        SpawnData spawnData,
        ServerLevel serverLevel,
        BlockPos pos,
        RandomSource randomSource,
        @NotNull BaseSpawnerAccessor accessor
    ) {
        for (int i = 0; i < accessor.getSpawnCount(); ++i) {
            CompoundTag compoundTag = spawnData.getEntityToSpawn();
            Optional<EntityType<?>> optional = EntityType.by(compoundTag);
            if (optional.isEmpty()) {
                return;
            }

            ListTag listTag = compoundTag.getList("Pos", 6);
            int size = listTag.size();
            double x;
            double y;
            double z;
            if (size >= 1) {
                x = listTag.getDouble(0);
            } else {
                x = (double) pos.getX()
                    + (randomSource.nextDouble() - randomSource.nextDouble())
                    * accessor.getSpawnRange() + 0.5;
            }
            if (size >= 2) {
                y = listTag.getDouble(1);
            } else {
                y = pos.getY() + randomSource.nextInt(3) - 1;
            }
            if (size >= 3) {
                z = listTag.getDouble(2);
            } else {
                z = (double) pos.getZ()
                    + (randomSource.nextDouble() - randomSource.nextDouble())
                    * accessor.getSpawnRange() + 0.5;
            }
            if (serverLevel.noCollision(optional.get().getSpawnAABB(x, y, z))) {
                BlockPos blockPos = BlockPos.containing(x, y, z);
                if (spawnData.getCustomSpawnRules().isPresent()) {
                    if (!optional.get().getCategory().isFriendly()
                        && serverLevel.getDifficulty() == Difficulty.PEACEFUL
                    ) {
                        continue;
                    }

                    SpawnData.CustomSpawnRules customSpawnRules = spawnData.getCustomSpawnRules().get();
                    if (!customSpawnRules.blockLightLimit()
                        .isValueInRange(serverLevel.getBrightness(LightLayer.BLOCK, blockPos))
                        || !customSpawnRules.skyLightLimit()
                        .isValueInRange(serverLevel.getBrightness(LightLayer.SKY, blockPos))) {
                        continue;
                    }
                } else if (!SpawnPlacements.checkSpawnRules(
                    optional.get(),
                    serverLevel,
                    MobSpawnType.SPAWNER,
                    blockPos,
                    serverLevel.getRandom()
                )) {
                    continue;
                }
                Entity entity = EntityType.loadEntityRecursive(compoundTag, serverLevel, it -> {
                    it.moveTo(x, y, z, it.getYRot(), it.getXRot());
                    return it;
                });
                if (entity == null) {
                    return;
                }
                AABB boundingBox = new AABB(
                    pos.getX(),
                    pos.getY(),
                    pos.getZ(),
                    pos.getX() + 1,
                    pos.getY() + 1,
                    pos.getZ() + 1
                );
                int k = serverLevel.getEntitiesOfClass(
                    entity.getClass(),
                    boundingBox.inflate(accessor.getSpawnRange())
                ).size();
                if (k >= accessor.getMaxNearbyEntities()) {
                    return;
                }

                entity.moveTo(entity.getX(), entity.getY(), entity.getZ(), randomSource.nextFloat() * 360.0F, 0.0F);
                if (entity instanceof Mob mob) {
                    if (spawnData.getCustomSpawnRules().isEmpty()
                        && !mob.checkSpawnRules(serverLevel, MobSpawnType.SPAWNER)
                        || !mob.checkSpawnObstruction(serverLevel)) {
                        continue;
                    }

                    if (spawnData.getEntityToSpawn().size() == 1 && spawnData.getEntityToSpawn().contains("id", 8)) {
                        EventHooks.finalizeMobSpawn(
                            (Mob) entity,
                            serverLevel,
                            serverLevel.getCurrentDifficultyAt(entity.blockPosition()),
                            MobSpawnType.SPAWNER,
                            null
                        );
                    }
                }

                if (!serverLevel.tryAddFreshEntityWithPassengers(entity)) {
                    return;
                }

                serverLevel.levelEvent(2004, pos, 0);
                serverLevel.gameEvent(entity, GameEvent.ENTITY_PLACE, blockPos);
                if (entity instanceof Mob) {
                    ((Mob) entity).spawnAnim();
                }
            }
        }
    }

//    private void anvilProcess(AnvilRecipe recipe, AnvilCraftingContext context, AnvilFallOnLandEvent event) {
//        int counts = 0;
//        while (counts < AnvilCraft.config.anvilEfficiency) {
//            if (!recipe.craft(context.clearData())) break;
//            counts++;
//        }
//        if (context.isAnvilDamage()) event.setAnvilDamage(true);
//        context.spawnItemEntity();
//    }

    private void brokeBlock(@NotNull Level level, BlockPos pos, AnvilFallOnLandEvent event) {
        if (!(level instanceof ServerLevel serverLevel)) return;
        BlockState state = level.getBlockState(pos);
        if (state.getBlock().getExplosionResistance() >= 1200.0) event.setAnvilDamage(true);
        if (state.getDestroySpeed(level, pos) < 0) return;
        BlockEntity blockEntity = state.hasBlockEntity() ? level.getBlockEntity(pos) : null;
        LootParams.Builder builder = new LootParams
            .Builder(serverLevel)
            .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
            .withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
            .withOptionalParameter(LootContextParams.BLOCK_ENTITY, blockEntity);
        state.spawnAfterBreak(serverLevel, pos, ItemStack.EMPTY, false);
        if (state.getBlock() instanceof IHasMultiBlock multiBlock) {
            multiBlock.onRemove(level, pos, state);
        }
        List<ItemStack> drops = state.getDrops(builder);
        if (event.getEntity() != null && event.getEntity().blockState.getBlock() instanceof EmberAnvilBlock) {
            drops = drops.stream().map(it -> {
                SingleRecipeInput cont = new SingleRecipeInput(it);
                return level.getRecipeManager().getRecipeFor(
                    RecipeType.SMELTING,
                    cont,
                    level
                ).map(smeltingRecipe -> smeltingRecipe.value().assemble(cont, level.registryAccess())).orElse(it);
            }).collect(Collectors.toList());
        }
        this.dropItems(drops, level, pos.getCenter());
        level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
    }

    private void redstoneEmp(@NotNull Level level, @NotNull BlockPos pos, float fallDistance) {
        int radius = AnvilCraft.config.redstoneEmpRadius;
        int maxRadius = AnvilCraft.config.redstoneEmpMaxRadius;
        int distance = Math.min(((int) Math.ceil(fallDistance)) * radius, maxRadius);
        if (!level.getBlockState(pos.relative(Direction.EAST)).is(Blocks.IRON_TRAPDOOR)) {
            for (int x = 1; x < distance; x++) {
                for (int z = -distance; z < distance; z++) {
                    redstoneEmp(level, pos.offset(x, 0, z));
                }
            }
        }
        if (!level.getBlockState(pos.relative(Direction.WEST)).is(Blocks.IRON_TRAPDOOR)) {
            for (int x = -1; x > -distance; x--) {
                for (int z = -distance; z < distance; z++) {
                    redstoneEmp(level, pos.offset(x, 0, z));
                }
            }
        }
        if (!level.getBlockState(pos.relative(Direction.SOUTH)).is(Blocks.IRON_TRAPDOOR)) {
            for (int x = -distance; x < distance; x++) {
                for (int z = 1; z < distance; z++) {
                    redstoneEmp(level, pos.offset(x, 0, z));
                }
            }
        }
        if (!level.getBlockState(pos.relative(Direction.NORTH)).is(Blocks.IRON_TRAPDOOR)) {
            for (int x = -distance; x < distance; x++) {
                for (int z = -1; z > -distance; z--) {
                    redstoneEmp(level, pos.offset(x, 0, z));
                }
            }
        }
    }

    private void redstoneEmp(@NotNull Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (!state.is(ModBlockTags.REDSTONE_TORCH)) return;
        state = state.setValue(RedstoneTorchBlock.LIT, false);
        level.setBlockAndUpdate(pos, state);
    }

    private void dropCrabItems(@NotNull Level level, @NotNull BlockPos pos) {
        BlockState state = level.getBlockState(pos.relative(Direction.DOWN));
        if (state.is(ModBlocks.CRAB_TRAP.get())) {
            if (!state.hasBlockEntity()) return;
            CrabTrapBlockEntity blockEntity = (CrabTrapBlockEntity) level.getBlockEntity(pos.relative(Direction.DOWN));
            Direction face = state.getValue(CrabTrapBlock.FACING);
            Vec3 dropPos = pos.relative(face).getCenter().relative(face.getOpposite(), 0.5);
            if (blockEntity == null) return;
            ItemDepository depository = blockEntity.getDepository();
            for (int i = 0; i < depository.getSlots(); i++) {
                ItemStack stack = depository.getStack(i);
                ItemEntity itemEntity = new ItemEntity(level, dropPos.x, dropPos.y - 0.4, dropPos.z, stack, 0, 0, 0);
                itemEntity.setDefaultPickUpDelay();
                level.addFreshEntity(itemEntity);
                depository.extract(i, stack.getCount(), false);
            }
        }
    }

    /**
     * 侦听铁砧伤害实体事件
     *
     * @param event 铁砧伤害实体事件
     */
    @SuppressWarnings("unused")
    @SubscribeEvent
    public void onAnvilHurtEntity(@NotNull AnvilHurtEntityEvent event) {
        Entity hurtedEntity = event.getHurtedEntity();
        if (!(hurtedEntity instanceof LivingEntity entity)) return;
        if (!(hurtedEntity.level() instanceof ServerLevel serverLevel)) return;
        float damage = event.getDamage();
        float maxHealth = entity.getMaxHealth();
        double rate = damage / maxHealth;
        if (rate < 0.4) return;
        FallingBlockEntity eventEntity = event.getEntity();
        DamageSource source = entity.level().damageSources().anvil(eventEntity);
        LootParams.Builder builder = new LootParams.Builder(serverLevel);
        builder.withParameter(LootContextParams.DAMAGE_SOURCE, source);
        builder.withOptionalParameter(LootContextParams.DIRECT_ATTACKING_ENTITY, eventEntity);
        builder.withOptionalParameter(LootContextParams.ATTACKING_ENTITY, eventEntity);
        builder.withParameter(LootContextParams.THIS_ENTITY, entity);
        Vec3 pos = entity.position();
        builder.withParameter(LootContextParams.ORIGIN, pos);
        LootParams lootParams = builder.create(LootContextParamSets.ENTITY);
        LootTable lootTable = serverLevel.getServer().reloadableRegistries().getLootTable(entity.getLootTable());
        this.dropItems(lootTable.getRandomItems(lootParams), serverLevel, pos);
        if (rate >= 0.6) this.dropItems(lootTable.getRandomItems(lootParams), serverLevel, pos);
        if (rate >= 0.8) this.dropItems(lootTable.getRandomItems(lootParams), serverLevel, pos);
    }

    private void dropItems(@NotNull List<ItemStack> items, Level level, Vec3 pos) {
        for (ItemStack item : items) {
            if (item.isEmpty()) continue;
            ItemEntity entity = new ItemEntity(level, pos.x, pos.y, pos.z, item.copy(), 0.0d, 0.0d, 0.0d);
            level.addFreshEntity(entity);
        }
    }

}
