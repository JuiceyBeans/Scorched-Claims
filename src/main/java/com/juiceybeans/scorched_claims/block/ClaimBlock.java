package com.juiceybeans.scorched_claims.block;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import xaero.pac.common.server.api.OpenPACServerAPI;

public class ClaimBlock extends Block {
    public ClaimBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand,
                                 BlockHitResult hitResult) {
        var itemStack = player.getMainHandItem();
        InteractionResult result = InteractionResult.FAIL;

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
                return result;
            }

            var playerConfig = opacAPI.getPlayerConfigs().getLoadedConfig(player.getUUID());
            var usedSubConfig = playerConfig.getUsedServerSubConfig();
            int subConfigIndex = usedSubConfig.getSubIndex();
            var chunkPos = new ChunkPos(pos);

            var claim = opacAPI.getServerClaimsManager().tryToClaim(level.dimension().location(), party.getOwner().getUUID(),
                    subConfigIndex, chunkPos.x, chunkPos.z, chunkPos.x / 16, chunkPos.z / 16, true);
            //todo figure out why the claim is so far away from clicked block
            //todo figure out what the hell subconfig index means

            if (claim.getResultType().success) {
                itemStack.shrink(1);
                result = InteractionResult.SUCCESS;
            }
            player.displayClientMessage(claim.getResultType().message, false);
        }

        return result;
    }
}
