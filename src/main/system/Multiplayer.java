package main.system;

import gameworld.Entity;
import gameworld.entitys.Grunt;
import gameworld.entitys.Player2;
import main.MainGame;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Multiplayer {
    private final MainGame mainGame;
    private final Player2 player2;
    private int index = 10;
    public static DataOutputStream outputStream;
    public static DataInputStream inputStream;
    public boolean multiplayerStarted;
    public static String ipAddress;
    public static final int portNumber = 60069;

    public Multiplayer(MainGame mainGame, Player2 player2) {
        this.mainGame = mainGame;
        this.player2 = player2;
    }

    public void updateMultiplayerInput() {
        try {
            int messageLength = mainGame.player2Information.length();
            mainGame.player2Information = Multiplayer.inputStream.readUTF();
            //System.out.println(mainGame.player2Information);
            player2.worldX = Integer.parseInt(mainGame.player2Information, 0, 5, 10) - 50000;
            player2.worldY = Integer.parseInt(mainGame.player2Information, 5, 10, 10) - 50000;
            if (mainGame.player2Information.length() != messageLength) {
                mainGame.ENTITIES.clear();
                for (int i = 0; i < mainGame.player2Information.length() - 10; i += 15) {
                    mainGame.ENTITIES.add(new Grunt(mainGame, Integer.parseInt(mainGame.player2Information, index, index + 5, 10) - 50000,
                            Integer.parseInt(mainGame.player2Information, index + 5, index + 10, 10) - 50000,
                            Integer.parseInt(mainGame.player2Information, index + 10, index + 15, 10) - 50000));
                    index += 15;
                }
            }
            index = 10;
            for (Entity entity1 : mainGame.ENTITIES) {
                entity1.worldX = Integer.parseInt(mainGame.player2Information, index, index + 5, 10) - 50000;
                index += 5;
                entity1.worldY = Integer.parseInt(mainGame.player2Information, index, index + 5, 10) - 50000;
                index += 5;
                entity1.health = Integer.parseInt(mainGame.player2Information, index, index + 5, 10) - 50000;
                index += 5;
            }
            index = 10;

        } catch (IOException | IndexOutOfBoundsException ignored) {

        }
    }


    public void updateMultiplayerOutput() {
        try {
            StringBuilder outputString = new StringBuilder();
            outputString.append(mainGame.player.worldX + 50000).append(mainGame.player.worldY + 50000);
            for (Entity entity1 : mainGame.ENTITIES) {
                outputString.append(entity1.worldX + 50000).append(entity1.worldY + 50000).append(entity1.health + 50000);
            }
            //System.out.println(outputString);
            Multiplayer.outputStream.writeUTF(outputString.toString());
            index = 10;


        } catch (IOException e) {
            throw new RuntimeException("failed");
        }
    }

    public void startMultiplayerServer() {
        multiplayerStarted = true;
        if (mainGame.keyHandler.multiplayer) {
            try {
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket s = serverSocket.accept();
                // s.setSoTimeout(5);
                outputStream = new DataOutputStream(s.getOutputStream());
                inputStream = new DataInputStream(s.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void startMultiplayerClient() {
        multiplayerStarted = true;
        if (mainGame.keyHandler.fpressed) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                Socket s = new Socket(ipAddress, portNumber);
                //  s.setSoTimeout(20);
                outputStream = new DataOutputStream(s.getOutputStream());
                inputStream = new DataInputStream(s.getInputStream());
                updateMultiplayerOutput();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
