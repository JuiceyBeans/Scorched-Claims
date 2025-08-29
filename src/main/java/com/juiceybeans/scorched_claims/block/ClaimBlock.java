package com.juiceybeans.scorched_claims.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
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
        if (player.getMainHandItem().is(Items.GOLD_INGOT)) {
            var opacAPI = OpenPACServerAPI.get(level.getServer());
            var manager = opacAPI.getPartyManager();
            var party = manager.getPartyById(player.getUUID());

            opacAPI.getServerClaimsManager().claim(level.dimension().location(), party.getOwner().getUUID(),
                    1, pos.getX(), pos.getZ(), false);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
