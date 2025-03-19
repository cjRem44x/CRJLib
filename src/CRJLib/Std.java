// AUTHOR: CJ Remillard //
//
package CRJLib;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.util.*;
//
import CRJLib.assets.*;

public class Std 
{
    // FIELDS //
    //
    private final Scanner      SCAN    = new Scanner( System.in );
    private final Random       RANDOM  = new Random();
    private final SecureRandom SRANDOM = new SecureRandom();
    //
    // final accessable assets
    public final Malloc mem    = new Malloc();
    public final Jcrypt jcrypt = new Jcrypt();
    public final PHash  phash  = new PHash();


    // CONSOLE IO //
    //
    public 
    String cin(String s)
    {
        this.cout(s);
        return SCAN.next();
    }
    //
    public 
    void cout(Object o)
    {
        System.out.print(o);
    }

    
    // RANDOMS //
    //
    /** 
     * @param min Mininmum integer in pseudo-random bounds
     * @param max Maximum integer in pseudo-random bounds
     * @return Pseudo-Random Integer
    */
    //
    public 
    int prand(int min, int max)
    {
        if (max-min > 0)
            return RANDOM.nextInt(max-min)+min;
        else 
            return min;
    }
    //
    /** 
     * @param min Mininmum integer in secure-random bounds
     * @param max Maximum integer in secure-random bounds
     * @return Secure-Random Integer
    */
    //
    public 
    int srand(int min, int max)
    {
        if (max-min > 0)
            return SRANDOM.nextInt(max-min)+min;
        else 
            return min;
    }
    //
    /** 
     * @return instance of SecureRandom class (from java.security.*)
    */
    //
    public 
    SecureRandom get_srand()
    {
        return this.SRANDOM;
    }


    // URL LINKS //
    //
    /**
     * 
     * @param url_link representing URL link
     * @return TRUE if method-function succeeds, otherwise FALSE
     */
    //
    public 
    boolean open_url_link(String url_link)
    {
        try 
        {
            URI uri = new URI(url_link);

            if (Desktop.isDesktopSupported() &&
                Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)
            )
            {
                Desktop.getDesktop().browse(uri);
                return true;
            } else 
            {
                return false;
            }
        } catch (URISyntaxException | IOException ex)
        {
            ex.printStackTrace();
        }

        // default return
        return false;
    }


    // OPEN FILE //
    //
    /**
     * @param f a file to open 
     * @return TRUE if method-function succeeds, otherwise FALSE
     */
    //
    public 
    boolean open_file_on_desktop(File f)
    {
        try 
        {
            if (Desktop.isDesktopSupported() &&
                Desktop.getDesktop().isSupported(Desktop.Action.OPEN)
            )
            {
                Desktop.getDesktop().open(f);
                return true;
            } else 
            {
                return false;
            }
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }

        // default return
        return false;
    }

}