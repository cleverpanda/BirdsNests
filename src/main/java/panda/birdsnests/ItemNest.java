package panda.birdsnests;



import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraftforge.server.ServerLifecycleHooks;
public class ItemNest extends Item {
	private static final ResourceLocation LOOT_TABLE = new ResourceLocation("birdsnests:nest_loot");
	public ItemNest() {
		super(new Item.Properties()
				.stacksTo(64));
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Player player = context.getPlayer();
		if (player != null) {
			ItemStack stack = context.getItemInHand();
			if (!player.isCreative()) {
				stack.shrink(1);
			}
			if (!context.getLevel().isClientSide) {
				this.generateLoot(context.getLevel(), player);
			}
			return InteractionResult.SUCCESS;
		}
        return InteractionResult.PASS;
    }

	private void generateLoot(Level level, Player player) {
		if (LOOT_TABLE == BuiltInLootTables.EMPTY) return;
		LootTable loottable = ServerLifecycleHooks.getCurrentServer().getLootData().getLootTable(LOOT_TABLE);
		LootParams.Builder builder = new LootParams.Builder((ServerLevel) level);
		LootParams lootParams = builder.create(LootContextParamSet.builder().build());
		ObjectArrayList<ItemStack> randomItems = loottable.getRandomItems(lootParams);
		for (ItemStack itemStack : randomItems) {
			level.addFreshEntity(new ItemEntity((ServerLevel) level, player.getX(), player.getY() + 1.5D, player.getZ(), itemStack));
		}
	}
}