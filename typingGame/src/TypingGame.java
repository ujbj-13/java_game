import javax.swing.*;
import java.awt.*;

public class TypingGame extends JFrame {
    public static final int WINDOW_W = 900;
    public static final int WINDOW_H = 600;


    public static void main(String[] args) {
        new TypingGame();
    }

    Panel panel = null;
    Player player = null;

    public TypingGame() {
        panel = new Panel();
        player = new Player();

        this.setTitle("标题");
        // 设置图标
        ImageIcon icon = new ImageIcon("typingGame/images/icon.jpeg");
        this.setIconImage(icon.getImage());

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WINDOW_W, WINDOW_H);
        this.setLocationRelativeTo(null);

        // 添加组件 开始
        this.add(panel);
        this.addKeyListener(player);
        // 添加组件 结束

        this.setResizable(false);
        this.setVisible(true);

        // 执行线程
        new Thread(panel).start();
    }
}
