import java.util.Vector;

public class Hero extends Tank {

    Shot shot;

    Vector<Shot> shots = new Vector<>();

    int shotSize = 5;

    public Hero(int x, int y) {
        super(x, y);
    }

    public void shotEnemyTank() {
        if (shots.size() == 5) return;

        switch (getDirect()) {
            case 0 -> {
                shot = new Shot(getX() + 35, getY() - 5, 0);
            }
            case 1 -> {
                shot = new Shot(getX() + 35, getY() + 60 + 5, 1);

            }
            case 2 -> {
                shot = new Shot(getX() - 5, getY() + 35, 2);
            }
            case 3 -> {
                shot = new Shot(getX() + 60 + 5, getY() + 35, 3);
            }
        }

        new Thread(shot).start();
        shots.add(shot);
    }
}
