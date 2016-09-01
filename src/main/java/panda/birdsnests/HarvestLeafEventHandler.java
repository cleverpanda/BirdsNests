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
		if (  theblock == Blocks.LEAVES || theblock == Blocks.LEAVES2||OreDictionary.getOres("treeLeaves").contains(new ItemStack(theblock)))
		{
			BlockLeaves leaves = (BlockLeaves)theblock;

			if(((Boolean)event.getState().getValue(PropertyBool.create("decayable"))).booleanValue()){
				if (event.getWorld().rand.nextInt(BirdsNests.nestRarity) == 0)
				{	 
					double d0 = event.getWorld().rand.nextFloat() * f + (1.0F - f) * 0.5D;
					double d1 = event.getWorld().rand.nextFloat() * f + (1.0F - f) * 0.5D;
					double d2 = event.getWorld().rand.nextFloat() * f + (1.0F - f) * 0.5D;
					EntityItem entityitem = new EntityItem(event.getWorld(),event.getPos().getX()+d0,event.getPos().getY()+d1,event.getPos().getZ()+d2, new ItemStack(BirdsNests.nest,(BirdsNests.use32) ? 1 : 0, 1));
					event.getWorld().spawnEntityInWorld(entityitem);
				}
			}
		}
	}
}
