import java.io.*;
import java.util.Vector;

public class Recorder {
    private static int allDefeatTankNum;

    private static BufferedWriter bw = null;
    private static BufferedReader br = null;

    public static String path = "tankGame/record.txt";

    private static Vector<EnemyTank> enemyTanks = null;
    private static Vector<Node> nodes = new Vector<>();

    public static Vector<EnemyTank> getEnemyTanks() {
        return enemyTanks;
    }

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    public static String getPath() {
        return path;
    }

    public static Vector<Node> getNodes() {
        try {
            br = new BufferedReader(new FileReader(path));
            allDefeatTankNum = Integer.parseInt(br.readLine());

            String line = "";
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(" ");
                nodes.add(new Node(Integer.parseInt(arr[0]),
                        Integer.parseInt(arr[1]),
                        Integer.parseInt(arr[2])));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return nodes;
    }

    public static void recordFile() {
        try {
            bw = new BufferedWriter(new FileWriter(path));
            bw.write(allDefeatTankNum + "\r\n");

            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                bw.write(enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirect() + "\r\n");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static int getAllDefeatTankNum() {


        return allDefeatTankNum;
    }

    public static void setAllDefeatTankNum(int allDefeatTankNum) {
        Recorder.allDefeatTankNum = allDefeatTankNum;
    }

    public static void addAllDefeatTankNum() {
        allDefeatTankNum++;
    }
}
