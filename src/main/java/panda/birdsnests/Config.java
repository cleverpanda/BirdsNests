package panda.birdsnests;


import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;


@Mod.EventBusSubscriber
public class Config
{
    private static final Logger LOGGER = LogManager.getLogger();


    // Builder
    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;



    // Categories
    public static final String CATEGORY_GENERAL = "general";


    // Config setting
    public static ForgeConfigSpec.BooleanValue NEST_ALLOW_STACKING;
    public static ForgeConfigSpec.BooleanValue NEST_ALLOW_DECAY_DROPS;
    public static ForgeConfigSpec.IntValue NEST_RARITY;
    public static ForgeConfigSpec.DoubleValue NEST_DROP_MODIFIER;





    // Set the settings
    static {

        COMMON_BUILDER.comment("Bird Nest Settings").push(CATEGORY_GENERAL);

        NEST_ALLOW_STACKING = COMMON_BUILDER.comment("ALLOW_STACKING").define("nestAllowStacking", false);
        NEST_RARITY = COMMON_BUILDER.comment("NEST_DROP_RARITY").defineInRange("nestRarityNormal", 40, 0, 1000);
        NEST_ALLOW_DECAY_DROPS = COMMON_BUILDER.comment("ALLOW_DECAY_DROPS").define("nestAllowDecayDrops", true);
        NEST_DROP_MODIFIER = COMMON_BUILDER.comment("NEST_DECAY_DROP_MULTIPLIER").defineInRange("nestRarityNormal", 1.25F, 0, 1000);

        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }



    public static void loadConfig(ForgeConfigSpec spec, Path path)
    {

        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();
        configData.load();
        spec.setConfig(configData);
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent)
    {
        LOGGER.debug("Config Loaded Event ");
    }

    @SubscribeEvent
    public static void onReload(final ModConfig.ConfigReloading configEvent) { LOGGER.debug("Config Re-Loaded Event "); }

}
