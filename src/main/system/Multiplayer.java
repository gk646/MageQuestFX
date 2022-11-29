package main.system;

import gameworld.Entity;
import gameworld.entitys.Player2;
import main.MainGame;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static main.MainGame.ENTITIES;


public class Multiplayer {
    private final MainGame mainGame;
    private final Player2 player2;
    private int index = 10;
    public static DataOutputStream outputStream;
    public static DataInputStream inputStream;
    public boolean multiplayerStarted;
    public static String ipAdress;
    public static int portNumber;

    public Multiplayer(MainGame mainGame, Player2 player2) {
        this.mainGame = mainGame;
        this.player2 = player2;
    }

    public void updateMultiInput() {
        try {
            mainGame.player2Information = Multiplayer.inputStream.readUTF();
            player2.worldX = Integer.parseInt(mainGame.player2Information, 0, 5, 10) - 50000;
            player2.worldY = Integer.parseInt(mainGame.player2Information, 5, 10, 10) - 50000;
            for (Entity entity1 : ENTITIES) {
                entity1.worldX = Integer.parseInt(mainGame.player2Information, index, index + 5, 10) - 50000;
                index += 5;
                entity1.worldY = Integer.parseInt(mainGame.player2Information, index, index + 5, 10) - 50000;
                index += 5;
                entity1.health = Integer.parseInt(mainGame.player2Information, index, index + 5, 10) - 50000;
                index += 5;
            }
        } catch (IOException e) {
            mainGame.player2Information = mainGame.player2Information;
        }
    }


    public void updateOutput() {
        try {
            StringBuilder outputString = new StringBuilder();
            outputString.append(mainGame.player.worldX + 50000).append(mainGame.player.worldY + 50000);
            for (Entity entity1 : ENTITIES) {
                outputString.append(entity1.worldX + 50000).append(entity1.worldY + 50000).append(entity1.health + 50000);
            }
            //System.out.println(outputString);
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
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket s = serverSocket.accept();
                s.setSoTimeout(5);
                outputStream = new DataOutputStream(s.getOutputStream());
                inputStream = new DataInputStream(s.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
