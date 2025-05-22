import java.util.List;
// 弹幕移动和碰撞检测线程
class BulletMover implements Runnable {
    private boolean running = false;
    private List<Bullet> bullets;
    public BulletMover(boolean _running, List<Bullet> _bullets){
        running = _running;
        bullets = _bullets;
    }
    public void setRunning(boolean _running){
        running = _running;
    }
    public void run() {
        while (running) {
            for (Bullet b : bullets) {

                b.update();
                if (b.isOutOfBounds()) {
                    bullets.remove(b);
                }
            }

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void setBullets(List<Bullet> _bullets){
        bullets = _bullets;
    }
}
