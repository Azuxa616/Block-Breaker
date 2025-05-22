import java.awt.*;
import java.awt.event.*;

public class ShotingSpot {
    private double x, y;
    private int size = 10;
    private double direction_X = 0;
    private double direction_Y = -1;
    private GunSight gunSight;

    public ShotingSpot(int x, int y) {
        this.x = x;
        this.y = y;
        gunSight = new GunSight();
        new Thread(gunSight).start();
    }

    public void draw(Graphics g) {

        // 画准星本体
        g.setColor(Color.CYAN);
        g.fillOval((int)x, (int)y, size, size);

        // 画瞄准线
        double dirX = gunSight.getDirectionX();
        int lineLength = 50;
        int endX = (int)(x + size/2 + dirX * lineLength);
        int endY = (int)(y + size/2 + direction_Y * lineLength);
        g.setColor(Color.YELLOW);
        g.drawLine((int)(x + size/2), (int)(y + size/2), endX, endY);
    }

    public void keyPressed(KeyEvent e) {
        
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            
            System.out.println("Aiming");
        }
    }

    public void keyReleased(KeyEvent e) { 
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            
            direction_X = gunSight.getDirectionX();
            System.out.println("Shot");
        }
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getDirection_X() {
        return direction_X;
    }
}

