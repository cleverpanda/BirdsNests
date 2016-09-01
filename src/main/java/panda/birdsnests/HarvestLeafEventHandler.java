package panda.birdsnests;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.oredict.OreDictionary;

public class HarvestLeafEventHandler {

	EntityItem entityitem = null;
	float f = 0.5F;

	@SubscribeEvent
	public void onDrops(BlockEvent.BreakEvent event) {

		Block theblock = event.block;
		if (  theblock == Blocks.leaves || theblock == Blocks.leaves2||OreDictionary.getOres("treeLeaves").contains(new ItemStack(theblock)))
		{
			BlockLeaves leaves = (BlockLeaves)theblock;

			if((leaves == Blocks.leaves && event.blockMetadata<12 && event.blockMetadata>7)||
					(leaves == Blocks.leaves2 && (event.blockMetadata==8 || event.blockMetadata==9))){
				if (event.world.rand.nextInt(BirdsNests.nestRarity) == 0)
				{	 
					double d0 = event.world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
					double d1 = event.world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
					double d2 = event.world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
					EntityItem entityitem = new EntityItem(event.world,event.x+d0,event.y+d1,event.z+d2, new ItemStack(BirdsNests.nest,(BirdsNests.use32) ? 1 : 0, 1));
					event.world.spawnEntityInWorld(entityitem);
				}
			}
		}
	}
}
