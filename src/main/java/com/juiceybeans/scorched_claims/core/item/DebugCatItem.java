package com.juiceybeans.scorched_claims.core.item;

import com.juiceybeans.scorched_claims.core.util.ChunkPowerUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DebugCatItem extends Item {
    public DebugCatItem(Properties properties) {
        super(properties);
    }

    private int index = 0;

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (level.isClientSide) {
            return InteractionResultHolder.consume(player.getItemInHand(usedHand));
        } else {
        var pos = player.blockPosition();
        var chunk = level.getChunkAt(pos);

        var message = Component.translatable(
                "chat.scorched_claims.debug_cat.check_power",
                chunk.getPos(), ChunkPowerUtils.getChunkPower(chunk));

        if (!player.isCrouching()) {
            message = switch (index) {
                case 0 -> Component.translatable("chat.scorched_claims.debug_cat.check_power",
                        chunk.getPos(), ChunkPowerUtils.getChunkPower(chunk));
                case 1 -> {
                    ChunkPowerUtils.increaseChunkPower(chunk, 10);
                    yield Component.translatable("chat.scorched_claims.debug_cat.increase_power",
                            chunk.getPos(), ChunkPowerUtils.getChunkPower(chunk));
                }
                case 2 -> {
                    ChunkPowerUtils.decreaseChunkPower(chunk, 10);
                    yield Component.translatable("chat.scorched_claims.debug_cat.decrease_power",
                            chunk.getPos(), ChunkPowerUtils.getChunkPower(chunk));
                }
                default -> message;
            };

            player.displayClientMessage(message.withStyle(ChatFormatting.GRAY), false);
        } else {
            String mode = "ERROR";
            // Scroll through indices
            if (index == 2) {
                index = 0;
            } else index += 1;

            mode = switch (index) {
                case 0 -> "Check Power";
                case 1 -> "Increase Power";
                case 2 -> "Decrease Power";
                default -> mode;
            };

            player.displayClientMessage(Component.translatable("chat.scorched_claims.debug_cat.switch", mode)
                            .withStyle(ChatFormatting.GREEN),
                    true);

        }

        return InteractionResultHolder.success(player.getItemInHand(usedHand));
        }
    }
}
