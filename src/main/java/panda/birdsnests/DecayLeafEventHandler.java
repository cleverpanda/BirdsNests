package panda.birdsnests;

import net.minecraft.block.Block;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Random;

public class DecayLeafEventHandler
{

	@SubscribeEvent
	public void onBlockDrops(BlockEvent.HarvestDropsEvent event)
	{
		Block theblock = event.getState().getBlock();
		Random random = new Random();
		BlockPos pos = event.getPos();

		double d0 = random.nextFloat() * 0.5D +0.25D;
		double d1 = random.nextFloat() * 0.5D +0.25D;
		double d2 = random.nextFloat() * 0.5D +0.25D;

		if(theblock.isIn(BlockTags.LEAVES) && Config.allowDecayDrops.get() == true)
		{
			if(random.nextInt(Config.decayDropModifier.get()) == 0)
			{
				ItemStack stack = new ItemStack(RegistryHandler.BIRDSNEST,1);
				ItemEntity entityitem = new ItemEntity((ServerWorld) event.getWorld(), pos.getX()+d0, pos.getY()+d1, pos.getZ()+d2, stack);
				event.getWorld().addEntity(entityitem);
			}
		}

	}
}