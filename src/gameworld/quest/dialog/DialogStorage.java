package gameworld.quest.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


abstract public class DialogStorage {

    public static String[] Tutorial;
    public static String[] TradingGeneral;


    public static void loadDialogs() {
        try {
            Tutorial = loadDialogByName("Tutorial");
            TradingGeneral = loadDialogByName("TradingGeneral");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String[] loadDialogByName(String txtName) throws IOException {
        String text;
        InputStream inputStream = DialogStorage.class.getResourceAsStream("/Dialog/" + txtName + ".txt");
        assert inputStream != null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder("d\n");
        text = bufferedReader.readLine();
        while (text != null) {
            stringBuilder.append(text);
            stringBuilder.append("\n");
            text = bufferedReader.readLine();
        }
        return stringBuilder.toString().split("\\n");
    }
}

