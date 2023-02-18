package main.system;

import gameworld.entities.ENTITY;
import gameworld.entities.monsters.ENT_Grunt;
import gameworld.entities.multiplayer.ENT_Player2;
import gameworld.player.Player;
import main.MainGame;
import main.system.enums.Zone;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Multiplayer {
    private static final int portNumber = 60_069;
    private static DataOutputStream outputStream;
    private static String ipAddress;
    private static DataInputStream inputStream;
    private final MainGame mg;
    private final ENT_Player2 ENTPlayer2;
    private boolean multiplayerStarted;
    private int index = 10;

    public Multiplayer(MainGame mainGame, ENT_Player2 ENTPlayer2) {
        this.mg = mainGame;
        this.ENTPlayer2 = ENTPlayer2;
    }

    public void updateMultiplayerInput() {
        try {
            int messageLength = mg.player2Information.length();
            mg.player2Information = Multiplayer.inputStream.readUTF();
            //System.out.println(mainGame.player2Information);
            ENTPlayer2.worldX = Integer.parseInt(mg.player2Information, 0, 5, 10) - 50_000;
            ENTPlayer2.worldY = Integer.parseInt(mg.player2Information, 5, 10, 10) - 50_000;
            if (mg.player2Information.length() != messageLength) {
                MainGame.ENTITIES.clear();
                for (int i = 0; i < mg.player2Information.length() - 10; i += 15) {
                    MainGame.ENTITIES.add(new ENT_Grunt(mg, Integer.parseInt(mg.player2Information, index, index + 5, 10) - 50_000, Integer.parseInt(mg.player2Information, index + 5, index + 10, 10) - 50_000, Integer.parseInt(mg.player2Information, index + 10, index + 15, 10) - 50_000, Zone.GrassLands));
                    index += 15;
                }
            }
            index = 10;
            for (ENTITY entity : MainGame.ENTITIES) {
                entity.worldX = Integer.parseInt(mg.player2Information, index, index + 5, 10) - 50_000;
                index += 5;
                entity.worldY = Integer.parseInt(mg.player2Information, index, index + 5, 10) - 50_000;
                index += 5;
                //entity.health = Integer.parseInt(mg.player2Information, index, index + 5, 10) - 50_000;
                index += 5;
            }
            index = 10;
        } catch (IOException | IndexOutOfBoundsException ignored) {

        }
    }


    private void updateMultiplayerOutput() {
        try {
            for (ENTITY entity : MainGame.ENTITIES) {
                //outputString.append(entity.worldX + 50_000).append(entity.worldY + 50_000).append(entity.health + 50_000);
            }
            //System.out.println(outputString);
            Multiplayer.outputStream.writeUTF(String.valueOf(Player.worldX + 50_000) + (Player.worldY + 50_000)
                    //outputString.append(entity.worldX + 50_000).append(entity.worldY + 50_000).append(entity.health + 50_000);
                    //System.out.println(outputString);
            );
            index = 10;
        } catch (IOException e) {
            throw new RuntimeException("failed");
        }
    }

    public void startMultiplayerServer() {
        multiplayerStarted = true;
        if (mg.inputH.multiplayer) {
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
        mg.client = true;
        if (mg.inputH.f_pressed) {
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
