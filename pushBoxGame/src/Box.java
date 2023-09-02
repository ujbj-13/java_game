public class Box {
    private int x;
    private int y;

    public Box() {
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

    public boolean move(int bx, int by, int[][] map) {
        if (map[x][y] != Map.BOX && map[x][y] != Map.GOAL_BOX) {
            System.out.println("box: " + map[x][y]);
            System.out.println();
            return false;
        }

        // 箱子前面是空地 或 目标
        if (map[bx][by] == Map.GROUND || map[bx][by] == Map.GOAL) {

            map[x][y] = map[x][y] + Map.PLAYER - Map.BOX;
            map[bx][by] += Map.BOX;
            return true;
        }

        return false;
    }
}
