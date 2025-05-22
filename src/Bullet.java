// 子弹类

import java.awt.*;

public class Bullet {
    private double x, y; //子弹坐标
    private int size = 10; //子弹大小
    private int speed = 6; //子弹速度
    private double direction_X; //子弹X方向
    private double direction_Y; //子弹Y方向
    private boolean isActive; //子弹是否存活


    public Bullet(double x, double y, double direction_X, double direction_Y) {
        this.x = x;
        this.y = y;
        this.direction_X = direction_X;
        this.direction_Y = direction_Y;
        this.isActive = true;
    }
    // 子弹移动
    public void update() {

        x += direction_X * speed;
        y += direction_Y * speed;
        hitBoundary();
    }

    public void draw(Graphics g) {
        if(this.isActive){
            g.setColor(Color.WHITE);
            g.fillOval((int)x, (int)y, size, size);
        }
    }
    // 子弹撞到边界后反弹
    private void hitBoundary() {
        if (x < 0 || x > 780) {
            direction_X = -direction_X;
        }
        if (y < 0 || y > 1200) {
            direction_Y = -direction_Y;
        }
    }

    // 子弹击中方块后的反弹
    // hitPoint为0时，水平速度不变，垂直速度不变
    // hitPoint为1、2时，水平速度反转，垂直速度不变
    // hitPoint为3、4时，水平速度不变，垂直速度反转
    public void hitBlock(int hitPoint) {
        if (hitPoint == 1 || hitPoint == 2) {
            direction_Y = -direction_Y;
        } else if (hitPoint == 3 || hitPoint == 4) {
            direction_X = -direction_X;
        }else{
            return;
        }
    }

    public boolean isOutOfBounds() {
        return y > 1000;
    }

    public boolean isActive() {
        return isActive;
    }

    public void deactivate() {
        isActive = false;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public int getSize() { return size; }


    // 根据碰撞点，调整子弹位置
    public void correctPosition(int hitPoint, Block block, int panel_X, int panel_Y) {
        double blockX = block.getCol() * block.getSize() + panel_X;
        double blockY = block.getRow() * block.getSize() + panel_Y;
        double blockSize = block.getSize();

        // 调整子弹位置至方块边缘，防止子弹卡进方块
        if (hitPoint == 1) { // 上边
            y = blockY - size;
        } else if (hitPoint == 2) { // 下边
            y = blockY + blockSize;
        } else if (hitPoint == 3) { // 左边
            x = blockX - size;
        } else if (hitPoint == 4) { // 右边
            x = blockX + blockSize;
        }
    }
} 