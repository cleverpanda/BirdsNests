package panda.birdsnests;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

public class HarvestLeafEventHandler {

	@SubscribeEvent
	public void onDrops(BlockEvent.BreakEvent event) {

		Block theblock = event.getState().getBlock();
		if (  theblock == Blocks.LEAVES || theblock == Blocks.LEAVES2||OreDictionary.getOres("treeLeaves").contains(new ItemStack(theblock)) || theblock instanceof BlockLeaves)
		{
			BlockPos pos = event.getPos();
			if(event.getState().getProperties().containsKey(BirdsNests.DECAYABLE) && event.getState().getValue(BirdsNests.DECAYABLE) && event.getWorld().rand.nextInt(BirdsNests.nestRarity) == 0){ 
				double d0 = event.getWorld().rand.nextFloat() * 0.5D +0.25D;
				double d1 = event.getWorld().rand.nextFloat() * 0.5D +0.25D;
				double d2 = event.getWorld().rand.nextFloat() * 0.5D +0.25D;
				ItemStack stack = new ItemStack(BirdsNests.nest,1, BirdsNests.use32 ? 1 : 0);
				EntityItem entityitem = new EntityItem(event.getWorld(), pos.getX()+d0, pos.getY()+d1, pos.getZ()+d2, stack);
				event.getWorld().spawnEntity(entityitem);
			}
		}
	}
}
 