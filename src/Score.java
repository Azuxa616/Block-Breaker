import java.awt.Graphics;
import java.awt.Color;

public class Score {
    private int score;

    public Score() {
        this.score = 1000;
    }

    public Score(int score) {
        this.score = score;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 800);
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void subScore(int score) {
        this.score -= score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
