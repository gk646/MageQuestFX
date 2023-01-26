package gameworld.dialogue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Dialog {
    //Type
    //0 = tutorial npc
    //1 = trader
    //2 =
    private final int stage;
    private final int type;
    public String text;
    private int textCounter;

    public Dialog(int type) {
        this.type = type;
        stage = 1;
        try {
            load_text();
        } catch (IOException e) {
            text = "Seems like i dont have anything to say...";
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void load_text() throws IOException {
        if (type == 0) {
            InputStream inputStream = Dialog.class.getResourceAsStream("/Dialog/traders.txt");
            assert inputStream != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            text = readLine(stage, bufferedReader);
        }
    }

    private String readLine(int line, BufferedReader breader) throws IOException {
        String line_text;
        int lineNumber = line;
        int currentLine = 0;
        while ((line_text = breader.readLine()) != null) {
            if (currentLine == lineNumber) {
                return line_text;
            }
            currentLine++;
        }
        breader.close();
        return line_text;
    }
}
