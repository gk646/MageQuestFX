/*
 * MIT License
 *
 * Copyright (c) 2023 Lukas Gilch
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
    public static String[] Nietzsche;

    public static void loadDialogs() {
        try {
            Tutorial = loadDialogByNameQUEST("Tutorial");
            Trading = loadDialogByNameMISC("Trading");
            MarlaNecklace = loadDialogByNameQUEST("MarlaQuest");
            AuditionMayor = loadDialogByNameQUEST("mayor_audition");
            IntoTheGrassLands = loadDialogByNameQUEST("IntoTheGrassLands");
            NPCComments = loadDialogByNameMISC("VillagerComments");
            TheGroveSecret = loadDialogByNameQUEST("TheGroveSecret");
            Nietzsche = loadDialogByNameQUEST("Nietzsche");
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

