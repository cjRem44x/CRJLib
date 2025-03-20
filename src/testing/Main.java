package testing;

import java.io.File;

import CRJLib.*;

public class Main 
{
    static final Std std = new Std();


    public static 
    void main(String[] args)
    {
        final String pass = "pefmsdfmk21312", 
        ext = ".myenc", path = "C:\\Users\\cremi\\proj\\CRJLib\\README.md";

        try {
            std.jcrypt.enc_file(
                path,
                std.jcrypt.gen_key(pass),
                ext
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        // wait 5 seconds
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            std.jcrypt.dec_file(
                path+ext,
                std.jcrypt.gen_key(pass),
                ext
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
