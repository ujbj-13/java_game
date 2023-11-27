import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;

// 地图 500 * 500
public class Map extends JPanel implements KeyListener {

    public static final int GROUND = 0;
    public static final int WALL = 1;
    public static final int GOAL = 2;
    public static final int BOX = 3;
    public static final int PLAYER = 4;
    public static final int GOAL_BOX = GOAL + BOX;
    public static final int GOAL_PLAYER = GOAL + PLAYER;

    public static final int ROW = 10;
    public static final int COL = 10;

    Image groundImg;
    Image wallImg;
    Image goalImg;
    Image boxImg;
    Image[] playerImg;
    Image goalBoxImg;
    Image[] goalPlayerImg;

    int mapX;
    int mapY;
    public int[][] map;

    private ArrayList<Integer[][]> recordsMap;

    Player player = null;

    // 加载图片
    {
        boxImg = new ImageIcon("pushBoxGame/images/box.png").getImage();
        goalImg = new ImageIcon("pushBoxGame/images/goal.png").getImage();
        goalBoxImg = new ImageIcon("pushBoxGame/images/goalBox.png").getImage();
        groundImg = new ImageIcon("pushBoxGame/images/ground.png").getImage();
        playerImg = new Image[]{
                new ImageIcon("pushBoxGame/images/player_up.png").getImage(),
                new ImageIcon("pushBoxGame/images/player_down.png").getImage(),
                new ImageIcon("pushBoxGame/images/player_left.png").getImage(),
                new ImageIcon("pushBoxGame/images/player_right.png").getImage(),
        };
        wallImg = new ImageIcon("pushBoxGame/images/wall.png").getImage();

        goalPlayerImg = playerImg;
    }

    // 初始化 地图
    {
        System.out.println("初始化数组");
        map = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 0, 2, 0, 2, 2, 2, 1, 0},
                {0, 1, 3, 1, 0, 2, 2, 2, 1, 0},
                {0, 1, 0, 0, 0, 0, 1, 1, 1, 0},
                {0, 1, 0, 3, 3, 3, 0, 0, 1, 0},
                {1, 1, 0, 3, 0, 1, 0, 0, 1, 0},
                {1, 0, 0, 0, 3, 0, 3, 0, 1, 0},
                {1, 0, 0, 0, 1, 0, 0, 4, 1, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 0}
        };
    }

    // 控制方向
    private int direction = 1;

    public Map() {
        mapX = 200;
        mapY = 30;

        player = new Player();

        recordsMap = new ArrayList<>();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);


        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 900, 600);

        drawMap(g);

        System.out.println("重绘");

        g.setColor(Color.BLACK);
        g.setFont(new Font("方正喵呜体", Font.BOLD, 20));
        g.drawString("步数: " + player.getCount(), 20, 50);
    }

    public void drawMap(Graphics g) {
        Integer[][] arr = new Integer[10][10];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                arr[i][j] = map[i][j];
                Image tempImg = null;

                switch (map[i][j]) {
                    case WALL : {
                        tempImg = wallImg;
                        break;
                    }
                    case GOAL : {
                        tempImg = goalImg;
                        break;
                    }
                    case BOX : {
                        tempImg = boxImg;
                        break;
                    }
                    case PLAYER : {
                        tempImg = playerImg[direction];

                        Player.setX(i);
                        Player.setY(j);
                        break;
                    }
                    case GOAL_BOX : {
                        tempImg = goalBoxImg;
                        break;
                    }
                    case GOAL_PLAYER : {
                        tempImg = goalPlayerImg[direction];

                        Player.setX(i);
                        Player.setY(j);
                        break;
                    }
                }
                g.drawImage(tempImg, i * 50 + mapX, j * 50 + mapY, 50, 50, this);
            }
        }

        recordsMap.add(arr);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        System.out.println("x:" + Player.getX() + "\t" + "y: " + Player.getY());

        // 移动
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
            direction = 0;
            player.pushBox(Player.getX(), Player.getY() - 1, map);
        } else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            direction = 1;
            player.pushBox(Player.getX(), Player.getY() + 1, map);
        } else if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
            direction = 2;
            player.pushBox(Player.getX() - 1, Player.getY(), map);
        } else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            direction = 3;
            player.pushBox(Player.getX() + 1, Player.getY(), map);
        }

        if (keyCode == KeyEvent.VK_ESCAPE) {
            copyOf(recordsMap.get(player.getCount()));

            player.setCount();
        }

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void copyOf(Integer[][] arr) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = arr[i][j];
            }
        }
    }
}
