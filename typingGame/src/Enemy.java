import javax.swing.*;
import java.awt.*;

public class Enemy {
    private double x;
    private double y;
    private boolean isLife;
    private String value;
    private int len;
    private int off;
    private int speed;
    private int hp;
    private Image bg;

    public Enemy(String value) {
        this.value = value;

        x = Math.random() * TypingGame.WINDOW_W;
        y = Math.random() * -100;
        isLife = true;
        speed = 5;
        len = value.length();

        bg = new ImageIcon().getImage();
    }

    public int getX() {
        return (int) Math.ceil(x);
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getOff() {
        return off;
    }

    public void setOff() {
        if (isLife) {
            off++;
            if (off > len) isLife = false;
        }
    }

    public int getY() {
        return (int) Math.ceil(y);
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isLife() {
        return isLife;
    }

    public String getValue() {
        return value;
    }

    public int getLen() {
        return len;
    }

    public int getHp() {
        return len - off;
    }

    public void setLife(boolean life) {
        isLife = life;
    }

    public Image getBg() {
        return bg;
    }

    public void moveEnemy() {
        if (isLife) {
            x = x + (TypingGame.WINDOW_W / 2 - x - len / 2 * Panel.fontSize) / ((TypingGame.WINDOW_H - y) / speed);
            y += speed;
            if (y >= TypingGame.WINDOW_H) {
                isLife = false;
            }
        }
    }
}
