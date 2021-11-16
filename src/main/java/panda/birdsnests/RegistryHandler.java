package panda.birdsnests;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(BirdsNests.MODID)
@Mod.EventBusSubscriber(modid = BirdsNests.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class RegistryHandler
{
    public static Item BIRDSNEST = new ItemNest("nest");

    @SubscribeEvent
    public static void onItemRegistry(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(RegistryHandler.BIRDSNEST);

    }

}