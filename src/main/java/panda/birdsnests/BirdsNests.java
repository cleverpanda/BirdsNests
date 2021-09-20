package panda.birdsnests;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(BirdsNests.MODID)
public class BirdsNests {

	public static final String MODID = "birdsnests";
	public static double nestRarity = 40;
	public static boolean allowStacking = false;
	public static int nestStackSize = 64;
	public static double decayDropModifier = 1.25F;
	public static boolean allowDecayDrops = true;

	public BirdsNests()
	{
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
		Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("birdsnests-client.toml"));
		Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("birdsnests-common.toml"));
		setSettings();
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		MinecraftForge.EVENT_BUS.register(new HarvestLeafEventHandler());
		MinecraftForge.EVENT_BUS.register(new DecayLeafEventHandler());
	}


	private void setup(final FMLCommonSetupEvent event)
	{}

	private static void setSettings()
	{
		BirdsNests.allowStacking = Config.allowStacking.get();
		BirdsNests.allowDecayDrops = Config.allowDecayDrops.get();

		if(BirdsNests.allowStacking == false)
		{
			BirdsNests.nestStackSize = 1;
		}
		else
		{
			BirdsNests.nestStackSize = 64;
		}
	}

}


