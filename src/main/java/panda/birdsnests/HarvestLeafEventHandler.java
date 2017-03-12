package panda.birdsnests;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

public class HarvestLeafEventHandler {

	EntityItem entityitem = null;
	float f = 0.5F;

	@SubscribeEvent
	public void onDrops(BlockEvent.BreakEvent event) {
		Block theblock = event.getState().getBlock();
		if (theblock instanceof BlockLeaves || isLeafInOreDict(theblock))
		{
			boolean decayable = false;
			// Not all ore dict entries will have the decayable property
			try {
				decayable = event.getState().getValue(BlockLeaves.DECAYABLE);
			} catch (IllegalArgumentException ignore) {}
			if(decayable){
				if (event.getWorld().rand.nextInt(BirdsNests.nestRarity) == 0)
				{	 
					double d0 = event.getWorld().rand.nextFloat() * f + (1.0F - f) * 0.5D;
					double d1 = event.getWorld().rand.nextFloat() * f + (1.0F - f) * 0.5D;
					double d2 = event.getWorld().rand.nextFloat() * f + (1.0F - f) * 0.5D;
					EntityItem entityitem = new EntityItem(event.getWorld(),event.getPos().getX()+d0,event.getPos().getY()+d1,event.getPos().getZ()+d2, new ItemStack(BirdsNests.nest, 1, (BirdsNests.use32) ? 1 : 0));
					event.getWorld().spawnEntityInWorld(entityitem);
				}
			}
		}
	}

	/**
	 * Checks if the block is registered as "treeLeaves" in the ore dictionary
	 * @param block block to check
	 * @return matching item is found in ore dictionary
	 */
	private boolean isLeafInOreDict(Block block) {
		for (ItemStack oreDictEntry : OreDictionary.getOres("treeLeaves"))
			if (ItemStack.areItemsEqual(oreDictEntry, new ItemStack(block)))
				return true;
		return false;
	}
}
