package panda.birdsnests;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(BirdsNests.MOD_ID)
public class BirdsNests {

	public static final String MOD_ID = "birdsnests";
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
	public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB.location(), MOD_ID);
	public static final RegistryObject<Item> NEST = ITEMS.register("nest", ItemNest::new);

	private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
	public static ForgeConfigSpec COMMON_CONFIG;
	public static ForgeConfigSpec.DoubleValue nestRarity;

	public static final RegistryObject<CreativeModeTab> TAB = TABS.register("tab",
			() -> CreativeModeTab.builder().title(Component.translatable("itemGroup." + MOD_ID))
					.icon(() -> NEST.get().getDefaultInstance())
					.title(Component.translatable("itemGroup." + MOD_ID))
					.displayItems((params, output) -> ITEMS.getEntries()
							.forEach(item -> output.accept(item.get().getDefaultInstance()))).build())
			;


	public BirdsNests() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ITEMS.register(modEventBus);
		TABS.register(modEventBus);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_CONFIG);
	}

	static {
		COMMON_BUILDER.comment("Bird Nest Settings").push("general");
		nestRarity = COMMON_BUILDER.comment("The rarity of nests dropping from decaying leaves [default: 0.25]").defineInRange("NEST_RARITY", 0.25, 0.0, 1.0);
		COMMON_BUILDER.pop();
		COMMON_CONFIG = COMMON_BUILDER.build();
	}
}


