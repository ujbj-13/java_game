public class Shot implements Runnable {
    private int x;
    private int y;
    private int direct;
    private int speed = 2;
    private boolean isLive = true;

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
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

    public boolean getIsLive() {
        return isLive;
    }

    public void setIsLive(boolean live) {
        isLive = live;
    }

    @Override
    public void run() {
        System.out.println("子弹线程开启...");

        System.out.println("x = " + x + "\t" + "y = " + y);


        while (true) {

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            switch (direct) {
                case 0:
                    y -= speed;
                    break;
                case 1:
                    y += speed;
                    break;
                case 2:
                    x -= speed;
                    break;
                case 3:
                    x += speed;
                    break;
            }

            System.out.println("x = " + x + "\t" + "y = " + y);

            if (!(x >= 0 && x <= 800 && y >= 0 && y <= 600 && isLive)) {
                isLive = false;
                break;
            }
        }
        System.out.println("子弹线程结束...");
    }
}
