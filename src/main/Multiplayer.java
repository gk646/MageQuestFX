package main;

import gameworld.Entity;
import gameworld.entitys.Player2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Multiplayer {
    private final MainGame mainGame;
    private final Player2 player2;
    private String outputString;
    private Entity entity;
    private int index = 10;
    public static DataOutputStream outputStream;
    public static DataInputStream inputStream;
    public boolean multiplayerstarted;


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
                if (entity1 != null) {
                    entity1.worldX = Integer.parseInt(mainGame.player2Information, index, index + 5, 10) - 50000;
                    index += 5;
                    entity1.worldY = Integer.parseInt(mainGame.player2Information, index, index + 5, 10) - 50000;
                    index += 5;
                    entity1.health = Integer.parseInt(mainGame.player2Information, index, index + 5, 10) - 50000;
                    index += 5;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void updateOutput() {
        try {
            outputString = "";
            outputString += (mainGame.player.worldX + 50000) + "" + (mainGame.player.worldY + 50000);
            for (Entity entity1 : entity.entities) {
                if (entity1 != null) {
                    outputString += (entity1.worldX + 50000) + "" + (entity1.worldY + 50000) + "" + (entity1.health + 50000);
                }
            }

            Multiplayer.outputStream.writeUTF(outputString);

            index = 10;
            player2.screenX = player2.worldX - 1440 - 24;
            player2.screenY = player2.worldY - 1860 -24;


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startMultiplayer() {
        multiplayerstarted= true;
        if (mainGame.keyHandler.multiplayer) {
            try {
                ServerSocket serverSocket = new ServerSocket(2555);
                Socket s = serverSocket.accept();
                outputStream = new DataOutputStream(s.getOutputStream());
                inputStream = new DataInputStream(s.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
