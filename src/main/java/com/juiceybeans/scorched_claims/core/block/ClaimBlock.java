package com.juiceybeans.scorched_claims.core.block;

import com.juiceybeans.scorched_claims.core.item.ModItems;

import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class ClaimBlock extends Block {

    public ClaimBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand,
                                 BlockHitResult hitResult) {
        var result = InteractionResult.PASS;

        if (hand == InteractionHand.MAIN_HAND) {
            ItemStack mainHandStack = player.getMainHandItem();
            ItemStack offHandStack = player.getOffhandItem();
            ItemStack stack;

            if (mainHandStack.is(ModItems.CLAIM_TICKET.get())) {
                stack = mainHandStack;
            } else if (offHandStack.is(ModItems.CLAIM_TICKET.get())) {
                stack = offHandStack;
            } else return result;

            result = useTicketAndClaim(stack, level, pos, player);
        }

        return result;
    }

    private static InteractionResult useTicketAndClaim(ItemStack itemStack, Level level, BlockPos pos, Player player) {
        InteractionResult result = InteractionResult.FAIL;
        Component message = Component.translatable("Something went wrong!").withStyle(ChatFormatting.RED);

        if (itemStack.is(ModItems.CLAIM_TICKET.get()) || itemStack.is(ModItems.CLAIM_TICKET.get())) {
            var claim = FTBChunksAPI.api().claimAsPlayer((ServerPlayer) player, level.dimension(), new ChunkPos(pos),
                    true);

            if (claim.isSuccess()) {
                itemStack.shrink(1);
                result = InteractionResult.SUCCESS;
            }

            message = claim.getMessage();
        }

        if (level.isClientSide) {
            player.displayClientMessage(message, true);
        }

        return result;
    }
}
