package main;

import gameworld.entitys.Player2;

import java.io.IOException;

public class Multiplayer {
    private final MainGame mainGame;
    private final Player2 player2;
    public Multiplayer(MainGame mainGame, Player2 player2) {
        this.mainGame = mainGame;
        this.player2 = player2;
    }

    public void update() {
        try {

            Runner.outputStream.writeUTF((mainGame.player.worldX +50000)+"" +(mainGame.player.worldY+50000));
            mainGame.player2Information = Runner.inputStream.readUTF();
            player2.worldX= Integer.parseInt(mainGame.player2Information,0,5,10)-50000;
            player2.worldY= Integer.parseInt(mainGame.player2Information,5,10,10)-50000;
            player2.screenX = player2.worldX-1700-24;
            player2.screenY = player2.worldY-1950-24;



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
