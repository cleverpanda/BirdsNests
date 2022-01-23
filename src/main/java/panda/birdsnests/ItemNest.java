package panda.birdsnests;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;//ADD NEW STAT
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;

public class ItemNest extends Item {

	private static final ResourceLocation LOOT_TABLE = new ResourceLocation("birdsnests:nest_loot");

	public ItemNest(){
		this.setCreativeTab(CreativeTabs.MISC);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName() + "." + (stack.getItemDamage() == 0 ? "default" : "32x");
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if (this.isInCreativeTab(tab))
		{
			items.add(new ItemStack(this, 1, (BirdsNests.use32) ? 1 : 0));
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
	{
		ItemStack itemstack = playerIn.getHeldItem(hand);

		if (!playerIn.capabilities.isCreativeMode)
		{
			itemstack.shrink(1);
		}

		worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!worldIn.isRemote)
		{
			
			handleRandomItems(worldIn,playerIn);
		}
		return new ActionResult(EnumActionResult.PASS, itemstack);
	}


	private void handleRandomItems(World world,EntityPlayer player) {

		LootTable loottable = world.getLootTableManager().getLootTableFromLocation(LOOT_TABLE);
		
		LootContext.Builder builder = new LootContext.Builder((WorldServer)world);
		 for (ItemStack itemstack : loottable.generateLootForPools(world.rand, builder.build()))
		{
			
			EntityItem entityitem = new EntityItem(world, player.posX, player.posY + 1.5D, player.posZ, itemstack);
			entityitem.motionX = world.rand.nextGaussian() * 0.05F;
			entityitem.motionY = (0.2d);
			entityitem.motionZ = world.rand.nextGaussian() * 0.05F;
			//player.getHorizontalFacing()

			world.spawnEntity(entityitem);
		}
	}
}
