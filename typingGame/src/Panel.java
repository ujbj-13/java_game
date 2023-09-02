import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class Panel extends JPanel implements Runnable {
    static final String fontName = "微软雅黑";
    static final int fontSize = 24;
    Vector<Enemy> enemies = new Vector<>();
    int enemySize = 5;


    public Panel() {
        // 初始化Enemy
        initEnemies();
    }

    private void initEnemies() {
        for (int i = 0; i < enemySize; i++) {
            Enemy enemy = new Enemy(LoadUtil.word());
            enemies.add(enemy);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // 设置背景
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, TypingGame.WINDOW_W, TypingGame.WINDOW_H);

        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            if (enemy.isLife()) {
                drawEnemy(g, enemy);
                System.out.println("绘制");
            } else {
                enemies.remove(enemy);
            }

        }
    }

    public void drawEnemy(Graphics g, Enemy enemy) {
        g.setColor(Color.WHITE);
        g.setFont(new Font(fontName, Font.BOLD, fontSize));
        g.drawString(enemy.getValue(), enemy.getX(), enemy.getY());

        // 绘制血条
        g.setColor(Color.RED);
        g.fillRect(enemy.getX(), enemy.getY() + fontSize, fontSize / 2 * enemy.getLen(), 5);
        g.setColor(Color.GREEN);
        g.fillRect(enemy.getX(), enemy.getY() + fontSize, fontSize / 2 * (enemy.getHp() - 1), 5);
    }

    @Override
    public void run() {
        while (enemies.size() > 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (int i = 0; i < enemies.size(); i++) {
                Enemy enemy = enemies.get(i);
                if (enemy.isLife()) {
                    enemy.moveEnemy();
                }
            }
            repaint();
        }
    }
}
