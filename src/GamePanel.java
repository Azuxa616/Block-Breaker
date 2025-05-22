import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GamePanel extends JPanel implements KeyListener {
    private ShotingSpot player;
    private List<Bullet> bullets;
    private volatile boolean running = true;
    private BulletMover bulletMover;
    private BlockGridPanel blockGridPanel;
    private BulletBlockCollisionThread bulletBlockCollisionThread;
    private Score score;
    private Timer timer;
    private boolean isStart = false;

    // 计时器线程
    Thread timerThread;

    public GamePanel() {
        setLayout(null); // 允许绝对定位
        setFocusable(true);
        setBackground(Color.black);
        setPreferredSize(new Dimension(800, 1000));
        addKeyListener(this);

        player = new ShotingSpot(400, 900);
        score = new Score();
        bullets = new CopyOnWriteArrayList<>();
        bulletMover = new BulletMover(running, bullets);
        blockGridPanel = new BlockGridPanel(8, 12, 55, 800);
        bulletBlockCollisionThread = new BulletBlockCollisionThread(bullets, blockGridPanel.getBlocks(), blockGridPanel.getPanel_X(), blockGridPanel.getPanel_Y(), score);
        // 计时器
        timer = new Timer(30);

        // 计时器线程
        timerThread = new Thread(timer);
        // 启动子弹移动线程
        new Thread(bulletMover).start();
        
        // 启动UI刷新线程（只启动一次）
        new Thread(() -> {
            while (true) {
                if (running) {
                    repaint();
                }
                try {
                    Thread.sleep(16); // 约60FPS
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        
        // 启动子弹与方块碰撞检测线程（只启动一次）
        new Thread(() -> {
            while (true) {
                if (running) {
                    bulletBlockCollisionThread.run();
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        // 启动游戏结束检测线程（只启动一次）
        new Thread(() -> {
            while (true) {
                if (running && (timer.getTime() <= 0 || score.getScore() < 0)) {
                    gameOver();
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        blockGridPanel.draw(g);
        player.draw(g);
        score.draw(g);
        timer.draw(g);
        for (Bullet b : bullets) {
            b.draw(g);
        }
    }

    
    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !isStart) {
            isStart = true;
            timer.setRunning(true);
            timerThread.start();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            bullets.add(new Bullet(player.getX(), player.getY(), player.getDirection_X(), -1));
            System.out.println("生成子弹，x方向：" + player.getDirection_X());
            bulletMover.setBullets(bullets);
            score.subScore(100);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    private void gameOver() {
        running = false;
        timer.setRunning(false);
        timerThread.interrupt();
        JOptionPane.showMessageDialog(this, "游戏结束，你的得分是：" + score.getScore());
        resetGame();
    }

    private void resetGame() {
        player = new ShotingSpot(400, 900);
        score.setScore(1000);
        bullets.clear();
        blockGridPanel = new BlockGridPanel(8, 12, 55, 800);
        bulletBlockCollisionThread.setBlocks(blockGridPanel.getBlocks());
        bulletBlockCollisionThread.setPanel_X(blockGridPanel.getPanel_X());
        bulletBlockCollisionThread.setPanel_Y(blockGridPanel.getPanel_Y());
        bulletBlockCollisionThread.setScore(score);
        timer.setTime(30);
        timer.setRunning(false);
        isStart = false;
        running = true;
        timerThread = new Thread(timer);
        bulletMover.setBullets(bullets);
        repaint();
    }
} 