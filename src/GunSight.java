// 瞄准线线程
public class GunSight implements Runnable {
    private boolean running = true;
    private double direction_X = 0;// 初始瞄准方向（垂直）
    private double change_value = 0.01;// 瞄准线摆动速度
    private double change_direction = 1;// 瞄准线摆动方向


    public GunSight() {}

    public GunSight(boolean _running, double _direction_X) {
        running = _running;
        direction_X = _direction_X;
    }

    @Override
    public void run() {
        while (running) {
            // direction_X 在 -1 到 1 之间来回摆动
            try {
                Thread.sleep(16); // 每0.016秒改变一次方向

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 改变瞄准方向
            direction_X += change_value * change_direction;
            // 改变摆动方向
            if (direction_X >= 1) {
                change_direction = -1;
            } else if (direction_X <= -1) {
                change_direction = 1;
            }
        }
    }

    public double getDirectionX() {
        return direction_X;
    }

    public void setRunning(boolean _running) {
        running = _running;
    }

    public void stop() {
        running = false;
    }

}
