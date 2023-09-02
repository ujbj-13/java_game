import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

public class TankGame extends JFrame {
    public static void main(String[] args) {
        new TankGame();
    }

    Scanner sc = new Scanner(System.in);
    Panel panel = null;

    public TankGame() {
        String key = sc.next();

        panel = new Panel(key);

        // 启动线程
        new Thread(panel).start();

        this.add(panel);
        this.addKeyListener(panel);

        this.setSize(1000, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // 使窗口居中
        this.setResizable(false); // 无法调整框架的大小。
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.recordFile();
                System.exit(0);
            }
        });
    }
}
