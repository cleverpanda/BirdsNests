package panda.birdsnests;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ModEvent {
    @SubscribeEvent
    public static void onBlock(BlockEvent.BreakEvent event) {
        BlockPos pos = event.getPos();
        LevelAccessor level = event.getLevel();
        BlockState blockState = level.getBlockState(pos);
        if (blockState.is(BlockTags.LEAVES)){
            if (getProbability(BirdsNests.nestRarity.get())){
                ItemStack stack = new ItemStack(BirdsNests.NEST.get());
                ItemEntity entity = new ItemEntity((Level) level, pos.getX(), pos.getY(), pos.getZ(), stack);
                level.addFreshEntity(entity);
            }
        }

    }

    @SubscribeEvent
    public static void onLeavesDecaying(LeavesDecayingEvent event) {
        if (getProbability(BirdsNests.nestRarity.get())){
            event.addLoot(new ItemStack(BirdsNests.NEST.get()));
        }
    }
    public static boolean getProbability(double probability){
        return Math.random() < probability;
    }

}
