package com.juiceybeans.scorched_claims.block;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import xaero.pac.common.claims.result.api.ClaimResult;
import xaero.pac.common.server.api.OpenPACServerAPI;

public class ClaimBlock extends Block {
    public ClaimBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand,
                                 BlockHitResult hitResult) {
        var itemStack = player.getMainHandItem();
        if (itemStack.is(Items.GOLD_INGOT)) {
            var opacAPI = Minecraft.getInstance().isLocalServer()
                    ? OpenPACServerAPI.get(Minecraft.getInstance().getSingleplayerServer())
                    : OpenPACServerAPI.get(level.getServer());

            var manager = opacAPI.getPartyManager();
            var party = manager.getPartyByMember(player.getUUID());

            if (party == null) {
                player.displayClientMessage(
                        Component.translatable("You are not currently in a party!").withStyle(ChatFormatting.RED),
                        true);
                return InteractionResult.FAIL;
            }


            var claim = opacAPI.getServerClaimsManager().tryToClaim(level.dimension().location(), party.getOwner().getUUID(),
                    0, pos.getX(), pos.getZ(), pos.getX(), pos.getZ(), false);
            //todo figure out why the claim is so far away from clicked block
            //todo figure out what the hell subconfig index means

            if (claim.getResultType() == ClaimResult.Type.SUCCESSFUL_CLAIM) {
                player.displayClientMessage(
                        ClaimResult.Type.SUCCESSFUL_CLAIM.message,
                        false);
                itemStack.shrink(1);
                return InteractionResult.SUCCESS;
            } else {
                if (claim.getResultType() == ClaimResult.Type.ALREADY_CLAIMED) {
                    player.displayClientMessage(
                            ClaimResult.Type.ALREADY_CLAIMED.message,
                            true);
                }  else if (claim.getResultType() == ClaimResult.Type.UNCLAIMABLE_DIMENSION) {
                    player.displayClientMessage(
                            ClaimResult.Type.UNCLAIMABLE_DIMENSION.message,
                            true);
                }  else if (claim.getResultType() == ClaimResult.Type.CLAIM_LIMIT_REACHED) {
                    player.displayClientMessage(
                            ClaimResult.Type.CLAIM_LIMIT_REACHED.message,
                            true);
                }  else if (claim.getResultType() == ClaimResult.Type.CLAIMS_ARE_DISABLED) {
                    player.displayClientMessage(
                            ClaimResult.Type.CLAIMS_ARE_DISABLED.message,
                            true);
                }  else if (claim.getResultType() == ClaimResult.Type.NO_SERVER_PERMISSION) {
                    player.displayClientMessage(
                            ClaimResult.Type.NO_SERVER_PERMISSION.message,
                            true);
                }

                return InteractionResult.FAIL;
            }

        }

        return InteractionResult.PASS;
    }
}
