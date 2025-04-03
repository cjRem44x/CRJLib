/*
 * MIT License
 *
 * Copyright (c) 2025 CJ Remillard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
    public final Malloc   mem    = new Malloc();
    public final Jcrypt   jcrypt = new Jcrypt();
    public final PHash    phash  = new PHash();
    public final Terminal term   = new Terminal();
    public final Parse    pars   = new Parse();
    public final Str      str    = new Str();
    public final Scimat   sim    = new Scimat();
    public final FIO      fi     = new FIO();


    // CONSOLE IO //
    //
    /*******************************************
     * 
     * @param s String to prompt to the console
     * @return String captured from input
     */
    //
    public 
    String cin(String s)
    {
        this.cout(s);
        return SCAN.next();
    }
    //
    /************************************************
     * 
     * @param o Object to print (out) to the console
     */
    //
    public 
    void cout(Object o)
    {
        System.out.print(o);
    }
    //
    public 
    void log(Object o)
    {
        cout(o+"\n");
    }
    //
    public 
    void coutln(Object o)
    {
        cout(o+"\n");
    }

    
    // RANDOMS //
    //
    /******************************************************
     * @param min Minimum integer in pseudo-random bounds
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
    /******************************************************
     * @param min Minimum integer in secure-random bounds
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
    /**************************************************************** 
     * 
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
    /************************************************************
     * 
     * @param url_link representing URL link
     * @return TRUE if method-function succeeds, otherwise FALSE
     */
    //
    public 
    boolean open_url(String url_link)
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
    /***********************************************************
     * 
     * @param f a file to open 
     * @return TRUE if method-function succeeds, otherwise FALSE
     */
    //
    public 
    boolean open_file(File f)
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