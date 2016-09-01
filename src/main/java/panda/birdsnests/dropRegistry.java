package panda.birdsnests;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;

public class dropRegistry {

	public static ArrayList<NestReward> rewards = new ArrayList<NestReward>();

	public static void register(Item output, int rarity, int pass)
	{
		NestReward entry = new NestReward(output, rarity, pass);

		if(output != null && !Contains(output, pass))
		{
			rewards.add(entry);
		}else
		{
			BirdsNests.log.error("An item was added to the Registry which was not an item");
			BirdsNests.log.error(output.toString());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ArrayList<NestReward> getRewards()
	{
		/*ArrayList<NestReward> rewardList = new ArrayList();

		Iterator<NestReward> it = rewards.iterator();
		while(it.hasNext())
		{
			NestReward reward = it.next();

			if (reward.source == block && reward.sourceMeta == meta)
			{
				rewardList.add(reward);
			}
		}*/

		return rewards;
	}
	
	public static boolean Contains(Item output, int passes)
	{
		Iterator<NestReward> it = rewards.iterator();
		while(it.hasNext())
		{
			NestReward reward = it.next();

			if (reward.item == output && reward.passes == passes)
			{
				return true;
			}
		}

		return false;
	}

	public static boolean Contains(Item output)
	{
		Iterator<NestReward> it = rewards.iterator();
		while(it.hasNext())
		{
			NestReward reward = it.next();

			if (reward.item == output )
			{
				return true;
			}
		}

		return false;
	}
	
	
	public static void registerConfigRarity(String configInput, Item item){
	String[] toStringArray = configInput.split(",");
	
	for(int i = 0; i < toStringArray.length; i++) 
	{
		try
		{
			int rarity = Integer.parseInt(toStringArray[i]); 
			register(item, rarity,i);
		}
		catch(NumberFormatException numberFormatException)  
		{
			numberFormatException.printStackTrace(); 
		}
	}	
}

	public static void load(Configuration config)
	{
		//Load things...
	}

	public static void registerRewards()
	{
/*
		register(Blocks.dirt, 0, ENItems.Stones, 0, 1);
		register(Blocks.dirt, 0, ENItems.Stones, 0, 2);
		register(Blocks.dirt, 0, ENItems.SeedsSugarcane, 0, 32);
		register(Blocks.dirt, 0, ENItems.SeedsCarrot, 0, 64);
		register(Blocks.dirt, 0, ENItems.SeedsPotato, 0, 64);
		register(Blocks.dirt, 0, ENItems.SeedsOak, 0, 64);
		register(Blocks.dirt, 0, ENItems.SeedsAcacia, 0, 90);
		register(Blocks.dirt, 0, ENItems.SeedsSpruce, 0, 90);
		register(Blocks.dirt, 0, ENItems.SeedsBirch, 0, 90);
		register(Blocks.sand, 0, ENItems.SeedsCactus, 0, 32);
		register(Blocks.sand, 0, ENItems.SeedsJungle, 0, 64);
		 */
	}
}

