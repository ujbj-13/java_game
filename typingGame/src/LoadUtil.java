import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class LoadUtil {
    private LoadUtil() {
    }
    private static  final String path = "typingGame/word.txt";
    private static Vector<String> words = new Vector<>();

    static {
        BufferedReader br = null;
        String str = "";
        try {
            br = new BufferedReader(new FileReader(path));
            while ((str = br.readLine()) != null) {
                words.add(str);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static Vector<String> getWords() {
        return words;
    }

    private static int off = 0;
    public static String word() {
        if (off >= words.size())  off = 0;
        return words.get(off++);
    }
}
