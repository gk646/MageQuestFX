package main;

import gameworld.Entity;
import gameworld.entitys.Player2;

import java.io.IOException;

public class Multiplayer {
    private final MainGame mainGame;
    private final Player2 player2;
    private String outputString;
    private Entity entity;
    private int index = 10;

    public Multiplayer(MainGame mainGame, Player2 player2, Entity entity) {
        this.mainGame = mainGame;
        this.player2 = player2;
        this.entity = entity;

    }

    public void update() {
        try {
            outputString = "";
            outputString += (mainGame.player.worldX + 50000) + "" + (mainGame.player.worldY + 50000);
            for (Entity entity1 : entity.entities) {
                if (entity1 != null) {
                    outputString += (entity1.worldX + 50000) + "" + (entity1.worldY + 50000) + "" + (entity1.health + 50000);

                }

            }
            String debug = "";
            Runner.outputStream.writeUTF(outputString);
            mainGame.player2Information = Runner.inputStream.readUTF();
            player2.worldX = Integer.parseInt(mainGame.player2Information, 0, 5, 10) - 50000;
            player2.worldY = Integer.parseInt(mainGame.player2Information, 5, 10, 10) - 50000;
            for (Entity entity1 : entity.entities) {
                if(entity1!=null) {
                    entity1.worldX = Integer.parseInt(mainGame.player2Information, index, index + 5, 10) - 50000;
                    index+=5;
                    entity1.worldY = Integer.parseInt(mainGame.player2Information, index, index + 5, 10) - 50000;
                    index+=5;
                    entity1.health = Integer.parseInt(mainGame.player2Information, index, index + 5, 10) - 50000;
                    index+=5;
                    debug+= entity1.health;
                }

            }
            System.out.println(debug);
            index = 10;
            player2.screenX = player2.worldX - 1700 - 24;
            player2.screenY = player2.worldY - 1950 - 24;


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
