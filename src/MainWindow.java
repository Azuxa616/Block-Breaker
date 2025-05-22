import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        setTitle("Block Breaker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 1000);
        setLocationRelativeTo(null);
        setResizable(false);
        add(new GamePanel());
    }
}
