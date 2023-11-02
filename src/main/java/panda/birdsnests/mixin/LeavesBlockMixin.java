package panda.birdsnests.mixin;

import panda.birdsnests.LeavesDecayingEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(LeavesBlock.class)
public abstract class LeavesBlockMixin extends Block {
    public LeavesBlockMixin(Properties p_49795_) {
        super(p_49795_);
    }
    @Redirect(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/LeavesBlock;dropResources(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)V"))
    public void redirectDropResources(BlockState state, Level level, BlockPos pos) {
        List<ItemStack> loot = getDrops(state, (ServerLevel) level, pos, null);
        LeavesDecayingEvent leavesDecayingEvent = new LeavesDecayingEvent(level, pos, state, loot);
        MinecraftForge.EVENT_BUS.post(leavesDecayingEvent);
        if (leavesDecayingEvent.isCanceled()) return;
        List<ItemStack> eventLoot = leavesDecayingEvent.getLoot();
        for (ItemStack stack : eventLoot) {
            popResource(level, pos, stack);
        }
        state.spawnAfterBreak((ServerLevel) level, pos, ItemStack.EMPTY);
    }
}
