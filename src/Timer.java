import java.awt.Color;
import java.awt.Graphics;

public class Timer implements Runnable {
    private double time;

    private boolean running;

    public Timer(double time) {
        this.time = time;

    }
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("Time: " + String.format("%.2f", time), 10, 900);
    }

    public void run() {
        while (running) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            time -= 0.01;
            checkTime();
        }
    }

    public double getTime() {
        return time;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setTime(double time) {
        this.time = time;
    }
    private void checkTime() {
        if (time <= 0) {
            running = false;
        }
    }
}