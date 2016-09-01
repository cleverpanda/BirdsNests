package panda.birdsnests;


import net.minecraft.client.gui.ChatLine;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class OnJoinWorldHandler {
    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
    public void onEvent(PlayerTickEvent event)
    {
      
        if (!BirdsNests.haveWarnedVersionOutOfDate && event.player.worldObj.isRemote 
              && !BirdsNests.versionChecker.isLatestVersion())
        {
            ClickEvent versionCheckChatClickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, 
                  "https://minecraft.curseforge.com/projects/birds-nests");
            Style clickableChatStyle = new Style().setClickEvent(versionCheckChatClickEvent);  //(0, (ITextComponent) versionCheckChatClickEvent, 0); //..setChatClickEvent(versionCheckChatClickEvent);
            TextComponentString versionWarningChatComponent = 
                  new TextComponentString("Your Bird's Nests Mod is not latest version!  Click this message to update.");
            versionWarningChatComponent.setStyle(clickableChatStyle);
            
            event.player.addChatMessage(versionWarningChatComponent);
            BirdsNests.haveWarnedVersionOutOfDate = true;
        }
      
    }
}
