package panda.birdsnests;

import net.minecraft.item.Item;

public class NestReward {
	public Item item;
	public int rarity;
	public int passes;
	public int meta;

	public NestReward(Item item, int rarity, int pass)
	{
		this.item = item;
		this.passes = pass;
		this.meta = 0;
		this.rarity = calculateRarity(rarity);
	}

	public NestReward(Item item, int rarity, int pass, int meta)
	{
		this.item = item;
		this.passes = pass;
		this.meta = meta;
		this.rarity = calculateRarity(rarity);
	}

	private final int calculateRarity(int base)
	{
		int multiplier = BirdsNests.RARITY_MULTIPLIER;
		int rarity = (base * multiplier) + (int)(multiplier / 2.0f);

		return rarity;
	}
}