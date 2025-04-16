package testing;

import CRJLib.Std;
import CRJLib.assets.CJRWindow;

public class WinTest 
{
    public static void main(String[] args) 
    {
        final var win = new CJRWindow();
        win.init(800, 600, "CRJLib Window Test");
        win.defclose();
        win.setFPS(60);
        win.build();
        win.loop();
    } 
}
