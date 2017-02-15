public class Stats {

    private static boolean isPlay = true;
    private static boolean isPause = false;
    private static boolean isMenu = false;
    private static boolean isEnd = false;

    public static int score  = 0;
    public static int lives  = 2;
    public static int level  = 1;
    public static int count = 0;
    public static int display = 0;

    public static boolean isPlay(){
        return isPlay;
    }

    public static boolean isPause(){return isPause;}

    public static boolean isMenu(){
        return isMenu;
    }

    public static boolean isEnd(){
        return isEnd;
    }

    public static void startPlay(){
        isPlay = true;
        isMenu = false;
    }

    public static void stopMenu(){
        isMenu = false;
    }

    public static void endGame() {
        isEnd = true;
        isPlay = false;
    }

    public static void startGame(){
        isEnd = false;
        isPlay = true;
    }

    public static void togglePause(){
        if(isPause){
            isPause = false;
        }
        else
            isPause = true;
    }
    public static void togglePlay(){
        if(isPlay){
            isPlay = false;
        }
        else
            isPlay = true;
    }

    public static void toggleMenu(){
        if(isMenu){
            isMenu = false;
        }
        else
            isMenu = true;
    }
}