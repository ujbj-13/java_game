import java.util.Vector;

public class EnemyTank extends Tank implements Runnable {
    Vector<Shot> shots = new Vector<>();

    private Vector<EnemyTank> enemyTanks = new Vector<>();


    public EnemyTank(int x, int y) {
        super(x, y);
    }

    public boolean tankCollision() {
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);

            if (this != enemyTank) {
                switch (this.getDirect()) {
                    case 0: {
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 1) {
                            if (this.getX() >= enemyTank.getX() &&
                                    this.getX() <= enemyTank.getX() + 90 &&
                                    this.getY() >= enemyTank.getY() &&
                                    this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            if (this.getX() + 90 >= enemyTank.getX() &&
                                    this.getX() + 90 <= enemyTank.getX() + 90 &&
                                    this.getY() >= enemyTank.getY() &&
                                    this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        if (enemyTank.getDirect() == 2 || enemyTank.getDirect() == 3) {
                            if (this.getX() >= enemyTank.getX() &&
                                    this.getX() <= enemyTank.getX() + 60 &&
                                    this.getY() >= enemyTank.getY() &&
                                    this.getY() <= enemyTank.getY() + 90) {
                                return true;
                            }
                            if (this.getX() + 90 >= enemyTank.getX() &&
                                    this.getX() + 90 <= enemyTank.getX() + 60 &&
                                    this.getY() >= enemyTank.getY() &&
                                    this.getY() <= enemyTank.getY() + 90) {
                                return true;
                            }
                        }
                        break;
                    }
                    case 1 : {
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 1) {
                            if (this.getX() >= enemyTank.getX() &&
                                    this.getX() <= enemyTank.getX() + 90 &&
                                    this.getY() + 60 >= enemyTank.getY() &&
                                    this.getY() + 60 <= enemyTank.getY() + 60) {
                                return true;
                            }
                            if (this.getX() + 90 >= enemyTank.getX() &&
                                    this.getX() + 90 <= enemyTank.getX() + 90 &&
                                    this.getY() + 60 >= enemyTank.getY() &&
                                    this.getY() + 60 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        if (enemyTank.getDirect() == 2 || enemyTank.getDirect() == 3) {
                            if (this.getX() >= enemyTank.getX() &&
                                    this.getX() <= enemyTank.getX() + 60 &&
                                    this.getY() + 60 >= enemyTank.getY() &&
                                    this.getY() + 60 <= enemyTank.getY() + 90) {
                                return true;
                            }
                            if (this.getX() + 90 >= enemyTank.getX() &&
                                    this.getX() + 90 <= enemyTank.getX() + 60 &&
                                    this.getY() + 60 >= enemyTank.getY() &&
                                    this.getY() + 60 <= enemyTank.getY() + 90) {
                                return true;
                            }
                        }
                        break;
                    }
                    case 2 : {
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 1) {
                            if (this.getX() >= enemyTank.getX() &&
                                    this.getX() <= enemyTank.getX() + 90 &&
                                    this.getY() >= enemyTank.getY() &&
                                    this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            if (this.getX() >= enemyTank.getX() &&
                                    this.getX() <= enemyTank.getX() + 90 &&
                                    this.getY() + 90 >= enemyTank.getY() &&
                                    this.getY() + 90 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        if (enemyTank.getDirect() == 2 || enemyTank.getDirect() == 3) {
                            if (this.getX() >= enemyTank.getX() &&
                                    this.getX() <= enemyTank.getX() + 60 &&
                                    this.getY() >= enemyTank.getY() &&
                                    this.getY() <= enemyTank.getY() + 90) {
                                return true;
                            }
                            if (this.getX() >= enemyTank.getX() &&
                                    this.getX() <= enemyTank.getX() + 60 &&
                                    this.getY() + 90 >= enemyTank.getY() &&
                                    this.getY() + 90 <= enemyTank.getY() + 90) {
                                return true;
                            }
                        }
                        break;
                    }
                    case 3 : {
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 1) {
                            if (this.getX() + 60 >= enemyTank.getX() &&
                                    this.getX() + 60 <= enemyTank.getX() + 90 &&
                                    this.getY() >= enemyTank.getY() &&
                                    this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            if (this.getX() + 60 >= enemyTank.getX() &&
                                    this.getX() + 60 <= enemyTank.getX() + 90 &&
                                    this.getY() + 90 >= enemyTank.getY() &&
                                    this.getY() + 90 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        if (enemyTank.getDirect() == 2 || enemyTank.getDirect() == 3) {
                            if (this.getX() >= enemyTank.getX() &&
                                    this.getX() <= enemyTank.getX() + 60 &&
                                    this.getY() >= enemyTank.getY() &&
                                    this.getY() <= enemyTank.getY() + 90) {
                                return true;
                            }
                            if (this.getX() >= enemyTank.getX() &&
                                    this.getX() <= enemyTank.getX() + 60 &&
                                    this.getY() + 90 >= enemyTank.getY() &&
                                    this.getY() + 90 <= enemyTank.getY() + 90) {
                                return true;
                            }
                        }
                        break;
                    }
                }
            }
        }
        return false;
    }

    public Vector<EnemyTank> getEnemyTanks() {
        return enemyTanks;
    }

    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    @Override
    public void run() {
        while (true) {
            // 坦克存活 并且子弹为0
            if (isLive && shots.size() < 3) {
                Shot shot = null;
                switch (getDirect()) {
                    case 0 : {
                        shot = new Shot(getX() + 35, getY() - 5, 0);
                        break;
                    }
                    case 1 : {
                        shot = new Shot(getX() + 35, getY() + 60 + 5, 1);
                        break;
                    }
                    case 2 : {
                        shot = new Shot(getX() - 5, getY() + 35, 2);
                        break;
                    }
                    case 3 : {
                        shot = new Shot(getX() + 60 + 5, getY() + 35, 3);
                        break;
                    }
                }

                new Thread(shot).start();
                shots.add(shot);
            }


            // 坦克移动
            switch (getDirect()) {
                case 0 : {
                    for (int i = 0; i < 30; i++) {
                        if (!tankCollision()) {
                            moveUp();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                }
                case 1 : {
                    for (int i = 0; i < 30; i++) {
                        if (!tankCollision()) {
                            moveDown();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                }
                case 2 : {
                    for (int i = 0; i < 30; i++) {
                        if (!tankCollision()) {
                            moveLeft();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                }
                case 3 : {
                    if (!tankCollision()) {
                        moveRight();
                    }
                    for (int i = 0; i < 30; i++) {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                }
            }

            setDirect((int) (Math.random() * 4));

            if (!isLive) {
                break;
            }
        }
    }
}
