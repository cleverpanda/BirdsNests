package panda.birdsnests;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = BirdsNests.MODID, name = BirdsNests.NAME, version = BirdsNests.VERSION)
public class BirdsNests {

	public static final String MODID = "birdsnests";
	public static final String NAME = "Bird's Nests";
	public static final String VERSION = "1.1";
	public static int RARITY_MULTIPLIER = 1;
	public static String kernelsRarity = "";
	public static String wormsRarity = "";
	public static String stonesRarity = "";
	public static int nestRarity = 24;
	public static boolean use32 = false;
	public static final Item nest = new ItemNest();
	public static VersionChecker versionChecker;
	public static boolean haveWarnedVersionOutOfDate = false;
	public static String[] mega;
	
	public static Logger log;
	
    @Instance(MODID)
    public static BirdsNests instance;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    { 
        nest.setUnlocalizedName("birdsnest");
        GameRegistry.registerItem(nest, "birdsnest");
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
		dropRegistry.registerConfigRarity(rarity,Items.STICK);
		
		rarity = config.get("Drops", "FEATHER_RARITIES", "1,3").getString();
		dropRegistry.registerConfigRarity(rarity,Items.FEATHER);
		
		rarity = config.get("Drops", "EGG_RARITIES", "2").getString();
		dropRegistry.registerConfigRarity(rarity,Items.EGG);
		
		rarity = config.get("Drops", "STRING_RARITIES", "4,4").getString();
		dropRegistry.registerConfigRarity(rarity,Items.STRING);
		
		rarity = config.get("Drops", "BONE_RARITIES", "6,16").getString();
		dropRegistry.registerConfigRarity(rarity,Items.BONE);
		
		rarity = config.get("Drops", "FLINT_RARITIES", "4").getString();
		dropRegistry.registerConfigRarity(rarity,Items.FLINT);
		
		rarity = config.get("Drops", "WHEAT_SEEDS_RARITIES", "6,12").getString();
		dropRegistry.registerConfigRarity(rarity,Items.WHEAT_SEEDS);
		
		rarity = config.get("Drops", "MELON_SEEDS_RARITIES", "11").getString();
		dropRegistry.registerConfigRarity(rarity,Items.MELON_SEEDS);
		
		rarity = config.get("Drops", "PUMPKIN_SEEDS_RARITIES", "11").getString();
		dropRegistry.registerConfigRarity(rarity,Items.PUMPKIN_SEEDS);
		
		rarity = config.get("Drops", "DIAMOND_RARITIES", "25").getString();
		dropRegistry.registerConfigRarity(rarity,Items.DIAMOND);
		
		rarity = config.get("Drops", "EMERALD_RARITIES", "30").getString();
		dropRegistry.registerConfigRarity(rarity,Items.EMERALD);
		
		rarity = config.get("Drops", "REDSTONE_RARITIES", "13,18").getString();
		dropRegistry.registerConfigRarity(rarity,Items.REDSTONE);
		
		rarity = config.get("Drops", "GLOWSTONE_RARITIES", "15,20").getString();
		dropRegistry.registerConfigRarity(rarity,Items.GLOWSTONE_DUST);
		
		rarity = config.get("Drops", "BLAZE_RARITIES", "40,40").getString();
		dropRegistry.registerConfigRarity(rarity,Items.BLAZE_POWDER);
		
		rarity = config.get("Drops", "GOLD_NUGGET_RARITIES", "8,10,13,16").getString();
		dropRegistry.registerConfigRarity(rarity,Items.GOLD_NUGGET);
		
		rarity = config.get("Drops", "FISH_RARITIES", "30").getString();
		dropRegistry.registerConfigRarity(rarity,Items.FISH);
		
		rarity = config.get("Drops", "PRISMARINE_RARITIES", "60").getString();
		dropRegistry.registerConfigRarity(rarity,Items.PRISMARINE_CRYSTALS);
		
		config.addCustomCategoryComment("Custom", "This is where your custom drops go.");
		mega = config.get("Custom", "List", "", "Format 'ID_____META_____rarity1-rarity2-rarity3' \n To add apples with rarity of '10,20' it would be 'minecraft:apple_____0_____10-20' \n Separate items using ','").getString().split(",");
		
		config.addCustomCategoryComment("Compatability", "These options pertain to including content added by other mods. (Just mine for now)");
		kernelsRarity = config.get("Compatability", "KERNELS_RARITY", "11").getString();
		wormsRarity = config.get("Compatability", "WORM_RARITY", "6,8").getString();
		stonesRarity = config.get("Compatability", "STONES_RARITY", "4,6").getString();
		config.save();
		if(event.getSide() == Side.CLIENT)
    	{
		ModelResourceLocation	itemModelResourceLocation = new ModelResourceLocation("birdsnests:birdsnest", "inventory");
		ModelResourceLocation itemModelResourceLocation32 = new ModelResourceLocation("birdsnests:birdsnest32", "inventory");
		ModelBakery.registerItemVariants(nest, itemModelResourceLocation,itemModelResourceLocation32);
}

    }
    
    @EventHandler
    public void Init(FMLInitializationEvent event)
    {
    	
    	
    	if(event.getSide() == Side.CLIENT)
    	{
    		
    		Item nest = GameRegistry.findItem(MODID, "birdsnest");
    		ModelResourceLocation	itemModelResourceLocation = new ModelResourceLocation("birdsnests:birdsnest", "inventory");
    		ModelResourceLocation itemModelResourceLocation32 = new ModelResourceLocation("birdsnests:birdsnest32", "inventory");
    		
    		
    		
    		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(nest, 0, itemModelResourceLocation);
    		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(nest, 1, itemModelResourceLocation32);
    		//dropRegistry.registerRewards();
    	}
    	
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
	    
	    if (Loader.isModLoaded("agronomy"))
		{
			log.info("Agronomy Loaded");
			Item worms = GameRegistry.findItem("agronomy", "worms");

			if (worms != null) {
				dropRegistry.registerConfigRarity(wormsRarity,worms);
				BirdsNests.log.info("Worms were added to nest drops");
			}else{
				BirdsNests.log.error("WORMS WERE NOT ADDED, BUT WERE SUPPOSED TO BE");
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
	    
	    if(mega != null && mega.length > 0 && mega[0].length() > 1){
	    	for(int i=0; i < mega.length; i++){
	    		String[] tempsplit = mega[i].split("_____");
	    		if (tempsplit.length == 3){
	    			if (Item.getByNameOrId(tempsplit[0]) != null){
	    				dropRegistry.registerConfigRarity(tempsplit[2].split("-"),Item.getByNameOrId(tempsplit[0]),Integer.parseInt(tempsplit[1]));
	    				BirdsNests.log.info("Custom drop " + tempsplit[0] + " added");
	    			}else{
	    				BirdsNests.log.error("UNABLE TO RESOLVE CUSTOM DROP " + tempsplit[0]);
	    			}
	    		}else{
    				BirdsNests.log.error("UNABLE TO TRANSLATE CUSTOM DROP " + mega[i]);
	    		}
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
