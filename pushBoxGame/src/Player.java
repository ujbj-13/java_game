public class Player {
    private static int x;
    private static int y;
    private Box box;
    private int count;

    public void setCount() {
        if (count > 0) {
            count--;
        }
    }

    public int getCount() {
        return count;
    }

    public Player() {
        this.box = new Box();
    }

    public static int getX() {
        return x;
    }

    public static void setX(int x) {

        if (x >= 0 && x < Map.ROW) {
            Player.x = x;
        }
    }

    public static int getY() {
        return y;
    }

    public static void setY(int y) {
        if (y >= 0 && y < Map.COL) {
            Player.y = y;
        }
    }

    public boolean pushBox(int px, int py, int[][] map) {
        if (map[x][y] != Map.PLAYER && map[x][y] != Map.GOAL_PLAYER) return false;


        if (map[px][py] ==  Map.GROUND || map[px][py] == Map.GOAL) { // 前面如果是 空地或目标
            map[x][y] -= Map.PLAYER;
            map[px][py] += Map.PLAYER;

            count++;
        } else if (map[px][py] == Map.BOX || map[px][py] == Map.GOAL_BOX) { // 前面是箱子 或者 箱子 + 目标
            box.setX(px);
            box.setY(py);

            int tempX = px - x;
            int tempY = py - y;

            if (box.move(px + tempX, py + tempY, map)) {
                map[x][y] -= Map.PLAYER;
            }

            count++;
        }

        return true;
    }
}
