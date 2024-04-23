import java.io.IOException;


public class Game{
    private Renderer renderer;
    protected int score;
    protected boolean game_over;
    protected boolean started;
    protected boolean difficulty_hard;

    public Game(){
        score = 0;
        game_over = false;
        started = false;
        difficulty_hard = false;

        try {
            renderer = new Renderer (this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void restart(){
        renderer.bird.speed = 0;

        try {
            changeDifficulty (difficulty_hard);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        score = 0;
        game_over = false;
        started = false;
    }

    protected void changeDifficulty (boolean difficulty) throws IOException {
        renderer.bird = new Bird();
        if (!difficulty){
            renderer.pipe = new Pipe();
            renderer.timer.setDelay (16);
        }
        else{
            renderer.pipe = new PipeDiff();
            renderer.timer.setDelay (12);
        }

        try {
            renderer.changeBackground (difficulty);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
