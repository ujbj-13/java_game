public class Hero extends Tank {

    public Shot getShot() {
        return shot;
    }

    public void setShot(Shot shot) {
        this.shot = shot;
    }

    private Shot shot = null;

    public Hero(int x, int y) {
        super(x, y);
    }

    public void ShotEnemyTank() {


        switch (getDirect()) {
            case 0 -> {
                shot = new Shot(getX() + 40, getY() - 30, getDirect());
            }
            case 1 -> {
                shot = new Shot(getX() + 40, getY() + 110, getDirect());
            }
            case 2 -> {
                shot = new Shot(getX() - 30, getY() + 40, getDirect());
            }
            case 3 -> {
                shot = new Shot(getX() + 110, getY() + 40, getDirect());

            }
        }
        new Thread(shot).start();
    }
}
