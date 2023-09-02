public class Bullet implements Runnable {
    private int x;
    private int y;
    private int speed;
    private boolean isLife;

    public Bullet() {
        x = TypingGame.WINDOW_W / 2;
        y = TypingGame.WINDOW_H;
        speed = 10;
        isLife = true;
    }

    @Override
    public void run() {
    }

    public void moveBullet(Enemy enemy) {
        if (!enemy.isLife()) return;

        int speedX = (enemy.getX() - x) / (enemy.getY() / speed);

        if (isLife) {
            if (x < enemy.getX() && y < enemy.getY() ) {
                x += speedX;
                y += speed;
            }
        }else {
            isLife = false;
        }
    }
}
