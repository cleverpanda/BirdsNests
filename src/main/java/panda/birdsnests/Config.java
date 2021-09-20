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
    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;
    public static final String CATEGORY_GENERAL = "general";
    public static ForgeConfigSpec.BooleanValue allowStacking;
    public static ForgeConfigSpec.BooleanValue allowDecayDrops;
    public static ForgeConfigSpec.IntValue nestRarity;
    public static ForgeConfigSpec.DoubleValue decayDropModifier;





    static {

        COMMON_BUILDER.comment("Bird Nest Settings").push(CATEGORY_GENERAL);
        nestRarity = COMMON_BUILDER.comment("[range: 0 ~ 1000, default: 40]").defineInRange("NEST_DROP_RARITY", 40, 0, 1000);
        allowStacking = COMMON_BUILDER.comment("Allows to enable/disable nests stacking [default: false]").define("ALLOW_STACKING", false);
        decayDropModifier = COMMON_BUILDER.comment("This makes nests more (or less ) rare from decaying leaves. Leave at 1 for no change. [range: 0.0 ~ 1000.0, default: 1.25]").defineInRange("NEST_DECAY_DROP_MULTIPLIER", 1.25F, 0, 1000);
        allowDecayDrops = COMMON_BUILDER.comment("Allows to enable/disable nests dropping from decaying leaves [default: true]").define("ALLOW_DECAY_DROPS", true);
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
