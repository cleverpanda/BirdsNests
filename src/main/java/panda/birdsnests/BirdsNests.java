package panda.birdsnests;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = BirdsNests.MODID, name = BirdsNests.NAME, version = BirdsNests.VERSION)
public class BirdsNests {

	public static final String MODID = "birdsnests";
	public static final String NAME = "Bird's Nests";
	public static final String VERSION = "1.0";
	public static int RARITY_MULTIPLIER = 1;
	public static String kernelsRarity = "";
	public static String wormsRarity = "";
	public static String stonesRarity = "";
	public static int nestRarity = 24;
	public static boolean use32 = false;
	public static final Item nest = new ItemNest();
	public static VersionChecker versionChecker;
	public static boolean haveWarnedVersionOutOfDate = false;
	
	public static Logger log;
	
    @Instance(MODID)
    public static BirdsNests instance;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    { 
        
        MinecraftForge.EVENT_BUS.register(new HarvestLeafEventHandler());
        MinecraftForge.EVENT_BUS.register(new OnJoinWorldHandler());
        log = LogManager.getLogger(BirdsNests.NAME);
        
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());

		config.load();
		nestRarity = config.getInt("NEST_DROP_RARITY", config.CATEGORY_GENERAL, 24, 0, 1000, "");
		RARITY_MULTIPLIER = config.getInt("OVERALL_RARITY_MULTIPLIER", config.CATEGORY_GENERAL, 1, 1, 1000, "This changes all rates. Use if you have the relations between items you want but want to adjust overall frequency.");
		use32 = config.getBoolean("USE32_TEXTURE", config.CATEGORY_GENERAL, false, "");
		
		config.addCustomCategoryComment("Drops", "Numbers can roughly be interpreted as 'one out of n' chance of dropping. \n Multiple numbers mean the drop will have that chance of dropping the first, second, third...etc item");
		
		String rarity = config.get("Drops", "STICK_RARITIES", "1,2,3,3").getString();
		dropRegistry.registerConfigRarity(rarity,Items.stick);
		
		rarity = config.get("Drops", "FEATHER_RARITIES", "1,3").getString();
		dropRegistry.registerConfigRarity(rarity,Items.feather);
		
		rarity = config.get("Drops", "EGG_RARITIES", "2").getString();
		dropRegistry.registerConfigRarity(rarity,Items.egg);
		
		rarity = config.get("Drops", "STRING_RARITIES", "4,4").getString();
		dropRegistry.registerConfigRarity(rarity,Items.string);
		
		rarity = config.get("Drops", "BONE_RARITIES", "6,16").getString();
		dropRegistry.registerConfigRarity(rarity,Items.bone);
		
		rarity = config.get("Drops", "FLINT_RARITIES", "4").getString();
		dropRegistry.registerConfigRarity(rarity,Items.flint);
		
		rarity = config.get("Drops", "WHEAT_SEEDS_RARITIES", "6,12").getString();
		dropRegistry.registerConfigRarity(rarity,Items.wheat_seeds);
		
		rarity = config.get("Drops", "MELON_SEEDS_RARITIES", "11").getString();
		dropRegistry.registerConfigRarity(rarity,Items.melon_seeds);
		
		rarity = config.get("Drops", "PUMPKIN_SEEDS_RARITIES", "11").getString();
		dropRegistry.registerConfigRarity(rarity,Items.pumpkin_seeds);
		
		rarity = config.get("Drops", "DIAMOND_RARITIES", "25").getString();
		dropRegistry.registerConfigRarity(rarity,Items.diamond);
		
		rarity = config.get("Drops", "EMERALD_RARITIES", "30").getString();
		dropRegistry.registerConfigRarity(rarity,Items.emerald);
		
		rarity = config.get("Drops", "REDSTONE_RARITIES", "13,18").getString();
		dropRegistry.registerConfigRarity(rarity,Items.redstone);
		
		rarity = config.get("Drops", "GLOWSTONE_RARITIES", "15,20").getString();
		dropRegistry.registerConfigRarity(rarity,Items.glowstone_dust);
		
		rarity = config.get("Drops", "BLAZE_RARITIES", "40,40").getString();
		dropRegistry.registerConfigRarity(rarity,Items.blaze_powder);
		
		rarity = config.get("Drops", "GOLD_NUGGET_RARITIES", "8,10,13,16").getString();
		dropRegistry.registerConfigRarity(rarity,Items.gold_nugget);
		
		rarity = config.get("Drops", "FISH_RARITIES", "30").getString();
		dropRegistry.registerConfigRarity(rarity,Items.fish);
		
		config.addCustomCategoryComment("Compatability", "These options pertain to including content added by other mods. (Just mine for now)");
		kernelsRarity = config.get("Compatability", "KERNELS_RARITY", "11").getString();
		stonesRarity = config.get("Compatability", "STONES_RARITY", "4,6").getString();
		config.save();
		
		nest.setUnlocalizedName("birdsnest");
        GameRegistry.registerItem(nest, "birdsnest");
    }
    
    @EventHandler
    public void Init(FMLInitializationEvent event)
    {
   	
	}
    
    @EventHandler
	public void PostInitialize(FMLPostInitializationEvent event)
	{
	    if (Loader.isModLoaded("corn"))
		{
			log.info("Simple Corn Loaded");
			Item kernels = GameRegistry.findItem("corn", "kernels");

			if (kernels != null) {
				dropRegistry.registerConfigRarity(kernelsRarity,kernels);
				BirdsNests.log.info("Corn kernels were added to nest drops");
			}else{
				BirdsNests.log.error("KERNELS WERE NOT ADDED, BUT WERE SUPPOSED TO BE");
			}
		}
	    
	    if (Loader.isModLoaded("pandatweaks"))
		{
			log.info("Panda Tweaks Loaded");
			Item stones = GameRegistry.findItem("pandatweaks", "stones");

			if (stones != null) {
				dropRegistry.registerConfigRarity(stonesRarity,stones);
				BirdsNests.log.info("Stones were added to nest drops");
			}else{
				BirdsNests.log.error("STONES WERE NOT ADDED, BUT WERE SUPPOSED TO BE");
			}
		}
	    
	    if(event.getSide() == Side.CLIENT)
    	{
	    	versionChecker = new VersionChecker();
	    	Thread versionCheckThread = new Thread(versionChecker, "Nests Version Check");
	    	versionCheckThread.start();
    	}
	    
	    /*if (Loader.isModLoaded("exnihilo2"))
		{
			log.info("Ex Nihilo Loaded");
			Item kernels = GameRegistry.findItem("exnihilo2", "stone");

			if (kernels != null) {
				dropRegistry.registerConfigRarity(kernelsRarity,kernels);
				BirdsNests.log.info("Ex Nihilo items were added to nest drops");
			}else{
				BirdsNests.log.error("EX NIHILO ITEMS WERE NOT ADDED, BUT WERE SUPPOSED TO BE");
			}
		}*/
	}
}
