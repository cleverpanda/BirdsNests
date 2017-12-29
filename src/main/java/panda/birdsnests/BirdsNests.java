package panda.birdsnests;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = BirdsNests.MODID, name = BirdsNests.NAME, version = BirdsNests.VERSION)
public class BirdsNests {

	public static final String MODID = "birdsnests";
	public static final String NAME = "Bird's Nests";
	public static final String VERSION = "2.1.0";
	public static int nestRarity;
	public static boolean use32;
	public static boolean allowStacking;
	public static float decayDropModifier;
	public static boolean allowDecayDrops;
	public static PropertyBool DECAYABLE = PropertyBool.create("decayable");
	public static Item nest;

	@Instance(MODID)
	public static BirdsNests instance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{ 
		LootTableList.register(new ResourceLocation(MODID, "nest_loot"));
 

		Configuration config = new Configuration(event.getSuggestedConfigurationFile());

		config.load();
		nestRarity = config.getInt("NEST_DROP_RARITY", config.CATEGORY_GENERAL, 40, 0, 1000, "");
		use32 = config.getBoolean("USE32_TEXTURE", config.CATEGORY_GENERAL, false, "");
		allowStacking = config.getBoolean("ALLOW_STACKING", config.CATEGORY_GENERAL, false, "Allows to enable/disable nests stacking");
		decayDropModifier = config.getFloat("NEST_DECAY_DROP_MULTIPLIER", config.CATEGORY_GENERAL, 1.25F, 0, 1000, "This makes nests more (or less ) rare from decaying leaves. Leave at 1 for no change.");
		allowDecayDrops = config.getBoolean("ALLOW_DECAY_DROPS", config.CATEGORY_GENERAL, true, "Allows to enable/disable nests dropping from decaying leaves");
		config.save();
		
		nest = new ItemNest();
		if(!BirdsNests.allowStacking){
        	nest.setMaxStackSize(1);
		}
		nest.setUnlocalizedName("birdsnest");
		ForgeRegistries.ITEMS.register(nest.setRegistryName("birdsnest"));
		MinecraftForge.EVENT_BUS.register(new HarvestLeafEventHandler());
		MinecraftForge.EVENT_BUS.register(new DecayLeafEventHandler());
		
		if(event.getSide() == Side.CLIENT)
		{
			ModelResourceLocation	itemModelResourceLocation = new ModelResourceLocation("birdsnests:birdsnest", "inventory");
			ModelResourceLocation itemModelResourceLocation32 = new ModelResourceLocation("birdsnests:birdsnest32", "inventory");
			ModelBakery.registerItemVariants(nest, itemModelResourceLocation,itemModelResourceLocation32);
		}

	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		if(event.getSide() == Side.CLIENT)
		{
			ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation("birdsnests:birdsnest", "inventory");
			ModelResourceLocation itemModelResourceLocation32 = new ModelResourceLocation("birdsnests:birdsnest32", "inventory");

			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(nest, 0, itemModelResourceLocation);
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(nest, 1, itemModelResourceLocation32);
		}

	}

}
