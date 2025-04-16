package CRJLib.assets;

import java.awt.*;
import javax.swing.*;

public class CJRWindow
extends JPanel
{
    private JFrame f;
    private int fps = 60; // Default FPS
    private Timer fps_timer;

    public 
    void init(int width, int height, String title)
    {
        f = new JFrame();
        this.setLayout(null);
        this.setPreferredSize( new Dimension(width, height) );
        f.add(this);
        f.pack();
        f.setResizable(false);
        f.setTitle(title);
    }


    public 
    void defclose() 
    {
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public 
    void setFPS(int fps)
    {
        this.fps = fps;
        if (fps_timer != null) 
        {
            fps_timer.stop();
        }
    }


    public 
    void build() 
    {
        f.setVisible(true);
        f.setLocationRelativeTo(null);
    }

    public
    void loop()
    {
        fps_timer = new Timer(1000 / fps, e -> {
            this.repaint();
            try 
            {Thread.sleep(1);}
            catch (InterruptedException ex)
            {ex.printStackTrace();}
        });
        fps_timer.start();
    }

}