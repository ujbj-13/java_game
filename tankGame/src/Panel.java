import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

public class Panel extends JPanel implements KeyListener, Runnable {
    // 我方坦克
    private Hero hero;

    // 敌方坦克
    private Vector<EnemyTank> enemyTanks = new Vector<>();
    private Vector<Node> nodes = null;

    private Vector<Bomb> bombs = new Vector<>();
    private int enemyTankSize = 5;

    Image image1;
    Image image2;
    Image image3;

    public Panel(String key) {

        File file = new File(Recorder.path);
        if (file.exists()) {
            nodes = Recorder.getNodes();
        } else {
            key = "1";
        }

        Recorder.setEnemyTanks(enemyTanks);
        // 初始化我方坦克
        hero = new Hero(10, 100);

        switch (key) {
            case "1" -> {
                // 初始化敌方坦克
                for (int i = 0; i < enemyTankSize; i++) {
                    EnemyTank enemyTank = new EnemyTank((i + 1) * 100, 10);
                    enemyTank.setDirect(1);
                    enemyTank.setEnemyTanks(enemyTanks);
                    Shot shot = new Shot(enemyTank.getX() + 35, enemyTank.getY() + 60 + 5, 1);
                    enemyTank.shots.add(shot);
                    new Thread(shot).start();
                    new Thread(enemyTank).start();

                    enemyTanks.add(enemyTank);
                }
            }
            case "2" -> {
                // 初始化敌方坦克
                for (int i = 0; i < nodes.size(); i++) {
                    EnemyTank enemyTank = new EnemyTank(nodes.get(i).getX(), nodes.get(i).getY());
                    enemyTank.setDirect(nodes.get(i).getDirect());
                    enemyTank.setEnemyTanks(enemyTanks);
                    Shot shot = new Shot(enemyTank.getX() + 35, enemyTank.getY() + 60 + 5, 1);
                    enemyTank.shots.add(shot);
                    new Thread(shot).start();
                    new Thread(enemyTank).start();

                    enemyTanks.add(enemyTank);
                }
            }
            default -> {
                System.out.println("输入错误");
            }
        }


        image1 = new ImageIcon("tankGame/images/bomb_1.png").getImage();
        image2 = new ImageIcon("tankGame/images/bomb_2.png").getImage();
        image3 = new ImageIcon("tankGame/images/bomb_3.png").getImage();
    }

    public void showInfo(Graphics g) {
        g.setFont(new Font("微软雅黑", Font.BOLD, 16));
        g.drawString("你累积击败坦克", 820, 50);
        g.drawString(Recorder.getAllDefeatTankNum() + "", 900, 110);
        drawTank(820, 80, g, 0, 0);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 背景色
        g.fillRect(0, 0, 800, 600);

        showInfo(g);

        // 绘制我方坦克
        if (hero != null && hero.isLive) {
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
        }

        // 绘制敌方坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            if (enemyTank.isLive) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);
                // 绘制敌方坦克子弹
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    Shot shot = enemyTank.shots.get(j);
                    if (shot.getIsLive()) {
                        g.fillOval(shot.getX(), shot.getY(), 2, 2);
                    } else {
                        enemyTank.shots.remove(shot);
                    }
                }
            } else {
                enemyTanks.remove(enemyTank);
            }
        }

        // 绘制我方发射的子弹
        g.setColor(Color.GREEN);
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            g.fillOval(shot.getX(), shot.getY(), 2, 2);

            if (!shot.getIsLive()) {
                hero.shots.remove(shot);
            }
        }

        // 绘制炸弹
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);

            if (bomb.life > 6) {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 3) {
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
            } else {
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
            }

            bomb.lifeDown();
            if (bomb.life == 0) {
                bombs.remove(bomb);
            }
        }
    }

    /**
     * 画坦克
     *
     * @param x      坐标x
     * @param y      坐标y
     * @param g      画笔
     * @param direct 方向
     * @param type   类型
     */
    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        // 通过坦克类型设置坦克颜色
        switch (type) {
            case 0 -> g.setColor(Color.CYAN); // 我方
            case 1 -> g.setColor(Color.GREEN); // 敌方
        }

        // 绘制坦克 通过方向 上下左右
        switch (direct) {
            case 0 -> {
                g.fill3DRect(x, y, 20, 60, false);
                g.fill3DRect(x + 50, y, 20, 60, false);

                g.fill3DRect(x + 20, y + 10, 30, 40, false);
                g.fillOval(x + 20, y + 15, 30, 30);

                g.drawLine(x + 35, y + 15, x + 35, y - 5);
            }
            case 1 -> {
                g.fill3DRect(x, y, 20, 60, false);
                g.fill3DRect(x + 50, y, 20, 60, false);

                g.fill3DRect(x + 20, y + 10, 30, 40, false);
                g.fillOval(x + 20, y + 15, 30, 30);

                g.drawLine(x + 35, y + 60 - 15, x + 35, y + 60 + 5);
            }
            case 2 -> {
                g.fill3DRect(x, y, 60, 20, false);
                g.fill3DRect(x, y + 50, 60, 20, false);

                g.fill3DRect(x + 10, y + 20, 40, 30, false);
                g.fillOval(x + 15, y + 20, 30, 30);

                g.drawLine(x + 15, y + 35, x - 5, y + 35);
            }
            case 3 -> {
                g.fill3DRect(x, y, 60, 20, false);
                g.fill3DRect(x, y + 50, 60, 20, false);

                g.fill3DRect(x + 10, y + 20, 40, 30, false);
                g.fillOval(x + 15, y + 20, 30, 30);

                g.drawLine(x + 60 - 15, y + 35, x + 60 + 5, y + 35);
            }
        }
    }

    // 判断子弹是否命中坦克
    public void hitTank(Shot shot, Tank tank) {
        switch (tank.getDirect()) {
            case 0:
            case 1:
                if (!tank.isLive) return;
                if (shot.getX() > tank.getX() && shot.getX() < tank.getX() + 70
                        && shot.getY() > tank.getY() && shot.getY() < tank.getY() + 60) {
                    shot.setIsLive(false);
                    tank.isLive = false;

                    System.out.println("子弹命中坦克了");

                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);

                    if (tank instanceof EnemyTank) Recorder.addAllDefeatTankNum();
                }
                break;
            case 2:
            case 3:
                if (!tank.isLive) return;
                if (shot.getX() > tank.getX() && shot.getX() < tank.getX() + 60
                        && shot.getY() > tank.getY() && shot.getY() < tank.getY() + 70) {
                    shot.setIsLive(false);
                    tank.isLive = false;

                    enemyTanks.remove(tank);
                    System.out.println("子弹命中坦克了");

                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);

                    if (tank instanceof EnemyTank) Recorder.addAllDefeatTankNum();
                }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
            hero.setDirect(0);
            hero.moveUp();
        } else if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
            hero.setDirect(1);
            hero.moveDown();
        } else if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
            hero.setDirect(2);
            hero.moveLeft();
        } else if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
            hero.setDirect(3);
            hero.moveRight();
        }

        // 按j进行射击
        if (hero.isLive && code == KeyEvent.VK_J) {
            hero.shotEnemyTank();
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
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

            // 检查我方子弹是否命中
            if (hero.shot != null && hero.shot.getIsLive()) {
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);

                    for (int j = 0; j < hero.shots.size(); j++) {
                        hitTank(hero.shots.get(j), enemyTank);
                    }
                }
            }

            // 检查敌方子弹是否命中
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);

                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    hitTank(enemyTank.shots.get(j), hero);
                }
            }

            repaint();
        }
    }
}
