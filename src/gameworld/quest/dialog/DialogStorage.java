package gameworld.quest.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


abstract public class DialogStorage {

    public static String[] Tutorial;
    public static String[] Trading;
    public static String[] MarlaNecklace;

    public static String[] AuditionMayor;
    public static String[] IntoTheGrassLands;
    public static String[] NPCComments;
    public static String[] TheGroveSecret;
    public static void loadDialogs() {
        try {
            Tutorial = loadDialogByNameQUEST("Tutorial");
            Trading = loadDialogByNameMISC("Trading");
            MarlaNecklace = loadDialogByNameQUEST("MarlaQuest");
            AuditionMayor = loadDialogByNameQUEST("mayor_audition");
            IntoTheGrassLands = loadDialogByNameQUEST("IntoTheGrassLands");
            NPCComments = loadDialogByNameMISC("VillagerComments");
            TheGroveSecret = loadDialogByNameQUEST("TheGroveSecret");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String[] loadDialogByNameQUEST(String txtName) throws IOException {
        String text;
        InputStream inputStream = DialogStorage.class.getResourceAsStream("/Dialog/Quests/" + txtName + ".txt");
        assert inputStream != null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder("\n");
        text = bufferedReader.readLine();
        while (text != null) {
            stringBuilder.append(text);
            stringBuilder.append("\n");
            text = bufferedReader.readLine();
        }
        return stringBuilder.toString().split("\\n");
    }

    private static String[] loadDialogByNameMISC(String txtName) throws IOException {
        String text;
        InputStream inputStream = DialogStorage.class.getResourceAsStream("/Dialog/misc/" + txtName + ".txt");
        assert inputStream != null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder("\n");
        text = bufferedReader.readLine();
        while (text != null) {
            stringBuilder.append(text);
            stringBuilder.append("\n");
            text = bufferedReader.readLine();
        }
        return stringBuilder.toString().split("\\n");
    }
}

