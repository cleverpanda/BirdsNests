package panda.birdsnests;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;

import java.util.List;


public class LeavesDecayingEvent extends BlockEvent {
    private List<ItemStack> loot;
    public LeavesDecayingEvent(LevelAccessor level, BlockPos pos, BlockState state, List<ItemStack> loot) {
        super(level, pos, state);
        this.loot = loot;
    }

    public List<ItemStack> getLoot() {
        return loot;
    }

    public void setLoot(List<ItemStack> loot) {
        this.loot = loot;
    }
    public void addLoot(ItemStack stack) {
        this.loot.add(stack);
    }
    public void removeLoot(ItemStack stack) {
        this.loot.remove(stack);
    }
}
