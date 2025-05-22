public class GunSight implements Runnable {
    private boolean running = true;
    private double direction_X = 0;
    private double change_value = 0.01;
    private double change_direction = 1;


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
            direction_X += change_value * change_direction;
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
