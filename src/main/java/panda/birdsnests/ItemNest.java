package panda.birdsnests;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class ItemNest extends Item {

	public ItemNest(){
		this.setMaxStackSize(1);
		this.setCreativeTab(CreativeTabs.MISC);
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
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        if (!playerIn.capabilities.isCreativeMode)
        {
            --itemStackIn.stackSize;
        }

        worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!worldIn.isRemote)
        {
        	handleRandomItems(worldIn,playerIn);
        }
        return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
    }


	private void handleRandomItems(World world,EntityPlayer player) {
		Iterator<NestReward> it = dropRegistry.getRewards().iterator();
		double f3 = 0.05F;
		
		while(it.hasNext())
		{
			NestReward reward = it.next();

			if (world.rand.nextInt(reward.rarity) == 0)
			{
				EntityItem entityitem = new EntityItem(world, player.posX + 0.5D, player.posY + 1.5D, player.posZ + 0.5D, new ItemStack(reward.item, 1, reward.meta));
				entityitem.motionX = world.rand.nextGaussian() * f3;
				entityitem.motionY = (0.2d);
				entityitem.motionZ = world.rand.nextGaussian() * f3;

				world.spawnEntityInWorld(entityitem);
			}
		}
	}
}
