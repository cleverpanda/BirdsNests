package panda.birdsnests;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.*;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.List;

public class ItemNest extends Item
{
	private static final ResourceLocation LOOT_TABLE = new ResourceLocation("birdsnests:nest_loot");


	public ItemNest(String name)
	{
		super(new Item.Properties()
				.maxStackSize(BirdsNests.nestStackSize)
				.group(ItemGroup.MISC)
		);
		this.setRegistryName(new ResourceLocation(BirdsNests.MODID, name));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		itemstack.shrink(1);
		worldIn.playSound((PlayerEntity)null, playerIn.prevPosX, playerIn.prevPosY, playerIn.prevPosZ, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));

		if (!worldIn.isRemote)
		{
			this.generateLoot(worldIn,playerIn);
		}

		return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
	}





	private void generateLoot(World world,PlayerEntity player)
	{
		if (LOOT_TABLE == LootTables.EMPTY)
		{
		}
		else
		{
			LootTable loottable = ServerLifecycleHooks.getCurrentServer().getLootTableManager().getLootTableFromLocation(LOOT_TABLE);
			LootContext.Builder builder = new LootContext.Builder((ServerWorld) world);
			LootContext lootcontext = builder
					.withParameter(LootParameters.ORIGIN, player.getPositionVec())
					.withParameter(LootParameters.THIS_ENTITY, player).build(LootParameterSets.GIFT);

			List<ItemStack> itemstacklist = loottable.generate(lootcontext);

			for (ItemStack itemstack : itemstacklist)
			{
				ItemEntity entityitem = new ItemEntity(world, player.prevPosX, player.prevPosY + 1.5D, player.prevPosZ, itemstack);
				world.addEntity(entityitem);
			}
		}
	}
}