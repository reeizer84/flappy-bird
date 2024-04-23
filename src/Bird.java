import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

public class Bird{
    protected int height;
    protected int speed;
    protected Image image;
    public Bird() throws IOException {
        height = 300;
        speed = 0;

        image = ImageIO.read(new File("resources/bird.png"));
        image = image.getScaledInstance(50, 35, Image.SCALE_DEFAULT);
    }
}
