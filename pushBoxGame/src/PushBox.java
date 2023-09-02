import javax.swing.*;

public class PushBox extends JFrame {

    public static void main(String[] args) {
        new PushBox();
    }

    Map map = null;

    // 窗口 900 * 600
    public PushBox() {

        map = new Map();
        add(map);
        addKeyListener(map);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        setVisible(true);
    }
}