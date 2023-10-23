public class Tank {
    private int x;
    private int y;
    private int direct = 0;
    private int speed = 1;
    boolean isLive = true;

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
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

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void moveUp() {
        if (y > 0 + 5) {
            y -= speed;
        }
    }

    public void moveDown() {
        if (y < 600 - 100 - 5) {
            y += speed;
        }
    }

    public void moveLeft() {
        if (x > 0 + 5) {
            x -= speed;
        }
    }

    public void moveRight() {
        if (x < 800 - 75 - 5) {
            x += speed;
        }
    }
}
