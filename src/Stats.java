public class Stats {

    private static boolean isPlay = true;
    private static boolean isEnd = false;

    public static int score  = 0;
    public static int lives  = 2;
    public static int level  = 1;
    public static int count = 0;
    public static int display = 0;
    public static int radius = 20;

    public static boolean isPlay(){
        return isPlay;
    }

    public static boolean isEnd(){
        return isEnd;
    }

    public static void endGame() {
        isEnd = true;
        isPlay = false;
    }
}