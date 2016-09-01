package panda.birdsnests;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;

import org.apache.commons.io.IOUtils;

/**
 * Thanks, Jabelar!
 * @author jabelar
 */
public class VersionChecker implements Runnable
{
    private static boolean isLatestVersion = false;
    private static String latestVersion = "";

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() 
    {
        InputStream in = null;
        try 
        {
            in = new URL("https://raw.githubusercontent.com/cleverpanda/BirdsNests/master/update.json").openStream();
        } 
        catch 
        (MalformedURLException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        catch (IOException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try 
        {
            latestVersion = IOUtils.readLines(in).get(0);
        } 
        catch (IOException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        finally 
        {
            IOUtils.closeQuietly(in);
        }
        isLatestVersion = BirdsNests.VERSION.equals(latestVersion);
        if(isLatestVersion){
        	 System.out.println("Your version of Bird's Nests is up to date!");
        }else{
        	 System.out.println("Your version of Bird's Nests OUT OF DATE! Current version is "+latestVersion);
        }
       
    }
    
    public boolean isLatestVersion()
    {
     return isLatestVersion;
    }
    
    public String getLatestVersion()
    {
     return latestVersion;
    }
   
}