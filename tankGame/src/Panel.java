import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class Panel extends JPanel implements KeyListener, Runnable {
    private Hero hero = null;

    private EnemyTank enemyTank = null;

    private int enemySize = 3;

    private Vector<EnemyTank> enemyTanks = new Vector<>();


    public Panel() {
        hero = new Hero(100, 100);
        hero.setSpeed(5);

        for (int i = 0; i < enemySize; i++) {
            enemyTanks.add(new EnemyTank((i + 1) * 200, 0));
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);

        drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), hero.getType());

        for (int i = 0; i < enemyTanks.size(); i++) {
            drawTank(enemyTanks.get(i).getX(), enemyTanks.get(i).getY(), g, 1, 1);
        }
    }

    /**
     * 绘制坦克
     *
     * @param x      坦克的左上角x坐标
     * @param y      坦克的左上角y坐标
     * @param g      画笔
     * @param direct 坦克方向(上下左右)
     * @param type   坦克类型
     */
    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        // 设置坦克类型
        switch (type) {
            // 0 表示自己
            case 0 -> g.setColor(Color.CYAN);
            // 1. 表示敌人
            case 1 -> g.setColor(Color.ORANGE);
        }

        // 绘制 坦克方向
        switch (direct) {
            case 0 -> { // 向上
                g.fill3DRect(x, y, 20, 80, false);
                g.fill3DRect(x + 60, y, 20, 80, false);

                g.fill3DRect(x + 20, y + 15, 40, 50, false);

                g.fillOval(x + 20, y + 20, 40, 40);

                g.drawLine(x + 40, y - 30, x + 40, y + 20);
            }

            case 1 -> { // 向下
                g.fill3DRect(x, y, 20, 80, false);
                g.fill3DRect(x + 60, y, 20, 80, false);
                g.fill3DRect(x + 20, y + 15, 40, 50, false);

                g.fillOval(x + 20, y + 20, 40, 40);

                g.drawLine(x + 40, y + 110, x + 40, y + 60);
            }

            case 2 -> { // 向左
                g.fill3DRect(x, y, 80, 20, false);
                g.fill3DRect(x, y + 60, 80, 20, false);
                g.fill3DRect(x + 15, y + 20, 50, 40, false);

                g.fillOval(x + 20, y + 20, 40, 40);

                g.drawLine(x - 30, y + 40, x + 20, y + 40);
            }
            case 3 -> { // 向右
                g.fill3DRect(x, y, 80, 20, false);
                g.fill3DRect(x, y + 60, 80, 20, false);
                g.fill3DRect(x + 15, y + 20, 50, 40, false);

                g.fillOval(x + 20, y + 20, 40, 40);

                g.drawLine(x + 50, y + 40, x + 110, y + 40);

            }

            default -> {
                System.out.println("其他");
            }
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        System.out.println(e);

        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
            hero.setDirect(0);
            hero.moveUp();
        } else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            hero.setDirect(1);
            hero.moveDown();
        } else if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
            hero.setDirect(2);
            hero.moveLeft();
        } else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            hero.setDirect(3);
            hero.moveRight();
        }

        if (keyCode == KeyEvent.VK_J) {
            hero.ShotEnemyTank();
        }

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            repaint();
        }
    }
}
