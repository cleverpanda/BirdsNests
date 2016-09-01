package panda.birdsnests;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class ItemNest extends Item {

	public ItemNest(){
		this.setMaxStackSize(1);
		this.setCreativeTab(CreativeTabs.tabMisc);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
	    return super.getUnlocalizedName() + "." + (stack.getItemDamage() == 0 ? "default" : "32x");
	}

	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List subItems) {
	    subItems.add(new ItemStack(itemIn, 1, (BirdsNests.use32) ? 1 : 0));
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (!player.capabilities.isCreativeMode)
		{
			--stack.stackSize;
		}

		if(!world.isRemote)
		{
			handleRandomItems(world,player);
		}

		return stack;
	}


	private void handleRandomItems(World world,EntityPlayer player) {
		Iterator<NestReward> it = dropRegistry.getRewards().iterator();
		double f3 = 0.05F;
		
		while(it.hasNext())
		{
			NestReward reward = it.next();

			if (world.rand.nextInt(reward.rarity) == 0)
			{
				EntityItem entityitem = new EntityItem(world, player.posX + 0.5D, player.posY + 1.5D, player.posZ + 0.5D, new ItemStack(reward.item, 1));
				entityitem.motionX = world.rand.nextGaussian() * f3;
				entityitem.motionY = (0.2d);
				entityitem.motionZ = world.rand.nextGaussian() * f3;

				world.spawnEntityInWorld(entityitem);
			}
		}
	}
}
