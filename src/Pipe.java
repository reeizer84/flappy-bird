import java.awt.Graphics;
import java.awt.Color;

public class Pipe {
    protected int x;
    protected int gap;
    protected int width;
    protected int height;

    public Pipe(){
        x = 400;
        gap = 200;
        width = 50;
        height = 300;
    }

    protected void setColor (Graphics g){
        g.setColor (Color.GREEN);
    }
}
