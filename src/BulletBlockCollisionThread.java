import java.util.List;

public class BulletBlockCollisionThread extends Thread {
    private List<Bullet> bullets;
    private List<Block> blocks;
    private int panel_X;
    private int panel_Y;
    private Score score;

    public BulletBlockCollisionThread(List<Bullet> bullets, List<Block> blocks, int panel_X, int panel_Y, Score score) {
        this.bullets = bullets;
        this.blocks = blocks;
        this.panel_X = panel_X;
        this.panel_Y = panel_Y;
        this.score = score;
    }

    @Override
    public void run() {
        while (true) {
            for (Bullet bullet : bullets) {
                if (!bullet.isActive()) continue;
                for (Block block : blocks) {
                    if (!block.isDestroyed() && isCollide(bullet, block, panel_X, panel_Y) != 0) {
                        int hitPoint = isCollide(bullet, block, panel_X, panel_Y);
                        bullet.hitBlock(hitPoint);
                        bullet.correctPosition(hitPoint, block, panel_X, panel_Y);

                        // 碰撞一次加10分
                        score.addScore(10);

                        // 判断是否摧毁
                        int hp = block.HitHappen();
                        if (hp <= 0) {
                            // 摧毁方块加200分
                            score.addScore(200);
                        }
                        break;
                    }
                }
            }
            try {
                Thread.sleep(1); // 检测间隔
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private int isCollide(Bullet bullet, Block block, int panel_X, int panel_Y) {
        double bx = bullet.getX();
        double by = bullet.getY();
        double bsize = bullet.getSize();

        double blockX = block.getCol() * block.getSize() + panel_X;
        double blockY = block.getRow() * block.getSize() + panel_Y;
        double blockSize = block.getSize();

        // 判断是否碰撞
        if (bx + bsize > blockX && bx < blockX + blockSize &&
            by + bsize > blockY && by < blockY + blockSize) {

            double topDist = Math.abs(by + bsize - blockY);
            double bottomDist = Math.abs(by - (blockY + blockSize));
            double leftDist = Math.abs(bx + bsize - blockX);
            double rightDist = Math.abs(bx - (blockX + blockSize));

            double minDist = Math.min(Math.min(topDist, bottomDist), Math.min(leftDist, rightDist));
            
            bullet.hitHappen();
            
            if (minDist == topDist) {
                return 1; // 上边
            } else if (minDist == bottomDist) {
                return 2; // 下边
            } else if (minDist == leftDist) {
                return 3; // 左边
            } else {
                return 4; // 右边
            }
        }
        return 0; // 无碰撞
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

    public void setPanel_X(int panel_X) {
        this.panel_X = panel_X;
    }

    public void setPanel_Y(int panel_Y) {
        this.panel_Y = panel_Y;
    }

    public void setScore(Score score) {
        this.score = score;
    }
}
