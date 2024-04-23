import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Renderer extends JPanel implements KeyListener, ActionListener {
    protected final Timer timer;
    protected Bird bird;
    protected Pipe pipe;
    private Image sky;
    private final Game game;

    public Renderer (Game game_in) throws IOException{
        bird = new Bird();
        pipe = new Pipe();
        game = game_in;

        JFrame frame = new JFrame("Flappy Bird");

        frame.add (this);
        frame.setSize(400, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener (this);

        timer = new Timer (16, this);
        timer.start();

        changeBackground (game.difficulty_hard);
    }

    protected void changeBackground (boolean difficulty_hard) throws IOException {
        if (!difficulty_hard){
            BufferedImage skyImage = ImageIO.read(new File("resources/sky.jpg"));
            sky = skyImage.getScaledInstance(400, 600, Image.SCALE_DEFAULT);
        }
        else{
            BufferedImage hellImage = ImageIO.read(new File("resources/hell.jpg"));
            sky = hellImage.getScaledInstance(400, 600, Image.SCALE_DEFAULT);
        }
    }

    private Font changeFont (Graphics g, float size, Color color){
        Font current = g.getFont();
        current = current.deriveFont (current.getSize() * size);
        g.setColor (color);
        return current;
    }

    public void paint (Graphics g) {
        g.drawImage (sky, 0, 0,null);

        pipe.setColor (g);
        g.fillRect (pipe.x, 0, pipe.width, pipe.height);
        g.fillRect (pipe.x, pipe.height + pipe.gap, pipe.width, 600 - pipe.height - pipe.gap);

        g.drawImage (bird.image, 100, bird.height, null);


        if (!game.started){
            if (game.difficulty_hard){
                g.setFont (changeFont (g, 1.4F, Color.WHITE));
                g.drawString ("Kliknij, aby wybrac trudnosc (1 - latwy, 2 - trudny):", 10, 130);

                g.setFont (changeFont (g, 1.4F, Color.BLUE));
                g.drawString ("Trudny", 150, 160);

                g.setColor (Color.WHITE);
                g.drawString ("Nacisnij spacje, aby zaczac gre!", 20, 260);


            }

            else{
                g.setFont (changeFont (g, 1.4F, Color.BLACK));
                g.drawString ("Kliknij, aby wybrac trudnosc (1 - latwy, 2 - trudny):", 10, 130);

                g.setFont (changeFont (g, 1.4F, Color.GREEN));
                g.drawString ("Latwy", 150, 160);

                g.setColor (Color.BLACK);
                g.drawString ("Nacisnij spacje, aby zaczac gre!", 20, 260);
            }
        }

        else {
            if (game.difficulty_hard) g.setFont (changeFont (g, 1.8F, Color.WHITE));
            else g.setFont (changeFont (g, 1.8F, Color.BLACK));
            g.drawString("Score: " + game.score, 150, 80);
            g.setFont (changeFont (g, 0.55F, Color.WHITE));
        }


        if (game.game_over) {
            if (game.difficulty_hard){
                g.setFont (changeFont (g, 1.6F, Color.WHITE));
                g.drawString("Nacisnij R, aby zrestartowac gre!", 60, 320);

                g.setFont (changeFont (g, 1.8F, Color.WHITE));
                g.drawString("Game Over!", 100, 160);

            }
            else{
                g.setFont (changeFont (g, 1.6F, Color.BLACK));
                g.drawString("Nacisnij R, aby zrestartowac gre!", 60, 320);

                g.setFont (changeFont (g, 1.8F, Color.BLACK));
                g.drawString("Game Over!", 100, 160);
            }
            }
        }


    public void actionPerformed(ActionEvent e) {
        if (game.started && !game.game_over) {
            bird.height += bird.speed;
            bird.speed += 1;

            pipe.x -= 5;
            if (pipe.x < -pipe.width) {
                pipe.x = 400;
                pipe.height = (int) (Math.random() * 300) + 50;
                game.score += 1;
            }

            if (bird.height > 555) {
                game.game_over = true;
            }

            if (bird.height+5 < pipe.height || bird.height-5 > pipe.height + pipe.gap - 50) {
                if (pipe.x < 150 && pipe.x + pipe.width > 100) {
                    game.game_over = true;
                }
            }
        }
        repaint();
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (game.started) bird.speed = -15;
            else game.started = true;
        }

        else if (e.getKeyCode() == KeyEvent.VK_R && game.game_over) {
            game.restart();
        }


        else if (e.getKeyCode() == KeyEvent.VK_1 && !game.started){
            game.difficulty_hard = false;
            try {
                game.changeDifficulty (false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        else if (e.getKeyCode() == KeyEvent.VK_2 && !game.started)
        {
            game.difficulty_hard = true;
            try {
                game.changeDifficulty (true);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }
}
