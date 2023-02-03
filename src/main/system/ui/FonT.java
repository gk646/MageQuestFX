package main.system.ui;

import javafx.scene.text.Font;

public class FonT {
    //MINECRAFT STYLE

    public static Font minecraftItalic12;
    public static Font minecraftRegular20;
    public static Font minecraftRegular14;
    public static Font minecraftItalic15;
    public static Font minecraftItalic17;
    public static Font minecraftItalic30;

    public static Font minecraftBold20;

    public static Font minecraftBold18;
    public static Font minecraftBoldItalic15;

    //MARU MONICA
    public static Font maruMonica;

    public static Font maruMonica90;

    //PUBLIC PIXEL

    public static Font publicPixel20;

    public static void loadFonts() {
        minecraftRegular14 = Font.loadFont(FonT.class.getResourceAsStream("/Fonts/MinecraftRegular-Bmg3.otf"), 14);
        minecraftRegular20 = Font.loadFont(FonT.class.getResourceAsStream("/Fonts/MinecraftRegular-Bmg3.otf"), 20);
        minecraftItalic12 = Font.loadFont(FonT.class.getResourceAsStream("/Fonts/MinecraftItalic-R8Mo.otf"), 12);
        minecraftItalic15 = Font.loadFont(FonT.class.getResourceAsStream("/Fonts/MinecraftItalic-R8Mo.otf"), 15);
        minecraftItalic17 = Font.loadFont(FonT.class.getResourceAsStream("/Fonts/MinecraftItalic-R8Mo.otf"), 17);
        minecraftItalic30 = Font.loadFont(FonT.class.getResourceAsStream("/Fonts/MinecraftItalic-R8Mo.otf"), 30);
        minecraftBold20 = Font.loadFont(FonT.class.getResourceAsStream("/Fonts/MinecraftBold-nMK1.otf"), 20);
        minecraftBold18 = Font.loadFont(FonT.class.getResourceAsStream("/Fonts/MinecraftBold-nMK1.otf"), 18);
        minecraftBoldItalic15 = Font.loadFont(FonT.class.getResourceAsStream("/Fonts/MinecraftBoldItalic-1y1e.otf"), 15);
        maruMonica = Font.loadFont(FonT.class.getResourceAsStream("/Fonts/x12y16pxMaruMonica.ttf"), 20);
        maruMonica90 = Font.loadFont(FonT.class.getResourceAsStream("/Fonts/x12y16pxMaruMonica.ttf"), 90);
    }
}
