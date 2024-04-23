import java.awt.Graphics;
import java.awt.Color;
public class PipeDiff extends Pipe{
    public PipeDiff(){
        x = 400;
        gap = 170;
        width = 50;
        height = 300;
    }

    protected void setColor (Graphics g){
        g.setColor (Color.BLUE);
    }
}
