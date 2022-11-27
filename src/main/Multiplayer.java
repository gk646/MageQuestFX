package main;

import gameworld.Entity;
import gameworld.entitys.Player2;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Multiplayer {
    private final MainGame mainGame;
    private final Player2 player2;
    private final Entity entity;
    private int index = 10;
    public static DataOutputStream outputStream;
    public static DataInputStream inputStream;
    private static byte[] bytearr = null;
    public boolean multiplayerStarted;

    public Multiplayer(MainGame mainGame, Player2 player2, Entity entity) {
        this.mainGame = mainGame;
        this.player2 = player2;
        this.entity = entity;

    }

    public void updateMultiInput() {
        try {
            mainGame.player2Information = Multiplayer.inputStream.readUTF();
            player2.worldX = Integer.parseInt(mainGame.player2Information, 0, 5, 10) - 50000;
            player2.worldY = Integer.parseInt(mainGame.player2Information, 5, 10, 10) - 50000;
            for (Entity entity1 : entity.entities) {
                entity1.worldX = Integer.parseInt(mainGame.player2Information, index, index + 5, 10) - 50000;
                index += 5;
                entity1.worldY = Integer.parseInt(mainGame.player2Information, index, index + 5, 10) - 50000;
                index += 5;
                entity1.health = Integer.parseInt(mainGame.player2Information, index, index + 5, 10) - 50000;
                index += 5;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void updateOutput() {
        try {
            StringBuilder outputString = new StringBuilder();
            outputString.append(mainGame.player.worldX + 50000).append(mainGame.player.worldY + 50000);
            for (Entity entity1 : entity.entities) {
                outputString.append(entity1.worldX + 50000).append(entity1.worldY + 50000).append(entity1.health + 50000);
            }
            System.out.println(outputString);
            Multiplayer.outputStream.writeUTF(outputString.toString());

            index = 10;
            player2.screenX = player2.worldX - 1440 - 24;
            player2.screenY = player2.worldY - 1860 - 24;


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startMultiplayer() {
        multiplayerStarted = true;
        if (mainGame.keyHandler.multiplayer) {
            try {
                ServerSocket serverSocket = new ServerSocket(2555);
                Socket s = serverSocket.accept();
                //s.setSoTimeout(5);
                outputStream = new DataOutputStream(s.getOutputStream());
                inputStream = new DataInputStream(s.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static int writeUTF(String str, DataOutput out) throws IOException {
        final int strlen = str.length();
        int utflen = strlen; // optimized for ASCII

        for (int i = 0; i < strlen; i++) {
            int c = str.charAt(i);
            if (c >= 0x80 || c == 0)
                utflen += (c >= 0x800) ? 2 : 1;
        }

        if (utflen > 120000 || /* overflow */ utflen < strlen)
            throw new IllegalMonitorStateException();

        final byte[] bytearr;
        if (out instanceof DataOutputStream dos) {
            if (Multiplayer.bytearr == null || (Multiplayer.bytearr.length < (utflen + 2)))
                Multiplayer.bytearr = new byte[(utflen * 2) + 2];
            bytearr = Multiplayer.bytearr;
        } else {
            bytearr = new byte[utflen + 2];
        }

        int count = 0;
        bytearr[count++] = (byte) ((utflen >>> 8) & 0xFF);
        bytearr[count++] = (byte) ((utflen >>> 0) & 0xFF);

        int i = 0;
        for (i = 0; i < strlen; i++) { // optimized for initial run of ASCII
            int c = str.charAt(i);
            if (c >= 0x80 || c == 0) break;
            bytearr[count++] = (byte) c;
        }

        for (; i < strlen; i++) {
            int c = str.charAt(i);
            if (c < 0x80 && c != 0) {
                bytearr[count++] = (byte) c;
            } else if (c >= 0x800) {
                bytearr[count++] = (byte) (0xE0 | ((c >> 12) & 0x0F));
                bytearr[count++] = (byte) (0x80 | ((c >> 6) & 0x3F));
                bytearr[count++] = (byte) (0x80 | ((c >> 0) & 0x3F));
            } else {
                bytearr[count++] = (byte) (0xC0 | ((c >> 6) & 0x1F));
                bytearr[count++] = (byte) (0x80 | ((c >> 0) & 0x3F));
            }
        }
        out.write(bytearr, 0, utflen + 2);
        return utflen + 2;
    }
}
