package dev.dubhe.anvilcraft.event.forge;

import dev.dubhe.anvilcraft.AnvilCraft;
import dev.dubhe.anvilcraft.api.event.client.ClientPlayerDisconnectEvent;
import dev.dubhe.anvilcraft.api.event.forge.BlockEntityEvent;
import dev.dubhe.anvilcraft.api.event.server.block.ServerBlockEntityLoadEvent;
import dev.dubhe.anvilcraft.api.event.server.block.ServerBlockEntityUnloadEvent;

import net.minecraft.server.level.ServerLevel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoader;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = AnvilCraft.MOD_ID)
public class CommonEventHandlerListener {
    /**
     * @param event 服务端方块实体加载事件
     */
    @SubscribeEvent
    public static void onLoad(@NotNull BlockEntityEvent.ServerLoad event) {
        if (event.getLevel() instanceof ServerLevel serverLevel)
            ModLoader.postEvent(new ServerBlockEntityLoadEvent(serverLevel, event.getEntity()));
    }

    /**
     * @param event 服务端方块实体卸载事件
     */
    @SubscribeEvent
    public static void onUnload(@NotNull BlockEntityEvent.ServerUnload event) {
        if (event.getLevel() instanceof ServerLevel serverLevel)
            AnvilCraft.EVENT_BUS.post(new ServerBlockEntityUnloadEvent(serverLevel, event.getEntity()));
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void clientPlayerDisconnectEvent(
            @NotNull PlayerEvent.PlayerLoggedOutEvent playerLoggedOutEvent) {
        AnvilCraft.EVENT_BUS.post(new ClientPlayerDisconnectEvent());
    }
}
