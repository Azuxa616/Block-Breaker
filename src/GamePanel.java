import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GamePanel extends JPanel implements KeyListener {
    private ShotingSpot player; //玩家
    private List<Bullet> bullets; //子弹列表
    private volatile boolean running = true; // UI刷新状态
    private BulletMover bulletMover; //子弹移动类对象
    private BlockGridPanel blockGridPanel; //方块网格对象
    private BulletBlockCollisionThread bulletBlockCollisionThread; //子弹与方块碰撞检测线程对象
    private Score score; //分数
    private Timer timer; //计时器
    private boolean isStart = false; //游戏运行状态

    // 计时器线程
    Thread timerThread;

    public GamePanel() {
        setLayout(null); // 允许绝对定位
        setFocusable(true); 
        setBackground(Color.black);
        setPreferredSize(new Dimension(800, 1000));
        addKeyListener(this);//添加键盘监听器

        player = new ShotingSpot(400, 900); // 玩家（射击点）
        score = new Score(); // 分数
        bullets = new CopyOnWriteArrayList<>(); // 子弹
        bulletMover = new BulletMover(running, bullets); // 子弹移动类对象
        blockGridPanel = new BlockGridPanel(8, 12, 55, 800); // 方块网格对象
        bulletBlockCollisionThread = new BulletBlockCollisionThread(bullets, blockGridPanel.getBlocks(), blockGridPanel.getPanel_X(), blockGridPanel.getPanel_Y(), score); // 子弹与方块碰撞检测线程对象
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

    // 重写paintComponent方法，绘制游戏界面
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

    // 重写keyPressed方法，处理键盘按下事件
    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !isStart) {
            isStart = true;
            timer.setRunning(true);
            timerThread.start();
        }
    }

    // 重写keyReleased方法，处理键盘释放事件
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

    // 重写keyTyped方法，处理键盘输入事件(接口实现)
    @Override
    public void keyTyped(KeyEvent e) {}

    // 游戏结束方法（时间归零或分数归零）
    private void gameOver() {
        running = false;
        timer.setRunning(false);
        timerThread.interrupt();
        JOptionPane.showMessageDialog(this, "游戏结束，你的得分是：" + score.getScore());
        resetGame();
    }

    // 重置游戏方法
    private void resetGame() {
        player = new ShotingSpot(400, 900); // 玩家
        score.setScore(1000); // 分数
        bullets.clear(); // 子弹
        
        // 重置方块网格
        blockGridPanel = new BlockGridPanel(8, 12, 55, 800); 
        
        // 将重置后的方块网格数据传给碰撞检测线程
        bulletBlockCollisionThread.setBlocks(blockGridPanel.getBlocks()); 
        bulletBlockCollisionThread.setPanel_X(blockGridPanel.getPanel_X());
        bulletBlockCollisionThread.setPanel_Y(blockGridPanel.getPanel_Y());
        bulletBlockCollisionThread.setScore(score);
        
        // 重置计时器
        timer.setTime(30);
        timer.setRunning(false);
        isStart = false;
        running = true;
        // 重置计时器线程
        timerThread = new Thread(timer);
        bulletMover.setBullets(bullets);
        repaint();
    }
} 