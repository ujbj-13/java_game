import javax.swing.*;

public class TankGame extends JFrame {

    private Panel panel = null;

    public static void main(String[] args) {
        TankGame tankGame = new TankGame();
    }

    public TankGame() {
        panel = new Panel();

        new Thread(panel).start();

        add(panel);

        addKeyListener(panel);

        setTitle("坦克大战v1.0");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 750);
        setVisible(true);
    }
}
