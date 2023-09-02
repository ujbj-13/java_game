import javax.swing.*;
import java.awt.*;

public class Shot extends JPanel implements Runnable {
    public boolean getWhetherSurvival() {
        return whetherSurvival;
    }

    public void setWhetherSurvival(boolean whetherSurvival) {
        this.whetherSurvival = whetherSurvival;
    }

    private int x;
    private int y;
    private int direction;
    private int speed = 2;

    private boolean whetherSurvival = true;

    public Shot(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            switch (direction) {
                case 0 -> {
                    y -= speed;
                }
                case 1 -> {
                    y += speed;
                }
                case 2 -> {
                    x -= speed;
                }
                case 3 -> {
                    x += speed;
                }
            }

            System.out.println("x = " + x + "\ty = " + y);

            if (x > 1000 || x < 0 || y > 750 || y < 0) {
                whetherSurvival = false;
                break;
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.ORANGE);
        g.fillOval(x, y, 2, 2);
    }
}
