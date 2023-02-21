package gameworld.entities.multiplayer;

import gameworld.entities.ENTITY;
import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;

public class ENT_Player2 extends ENTITY {
    private Image player2;

    public ENT_Player2(MainGame mainGame) {
        this.mg = mainGame;
        //Setting default values
        worldX = 12_500;
        worldY = 12_500;
        this.entityHeight = 48;
        this.entityWidth = 48;


        //Handlers

    }


    /**
     *
     */
    @Override
    public void playGetHitSound() {

    }

    public void draw(GraphicsContext g2) {
        g2.drawImage(player2, worldX - Player.worldX + MainGame.SCREEN_WIDTH / 2.00f - 24, worldY - Player.worldY + MainGame.SCREEN_HEIGHT / 2.00f - 24, 48, 48);
    }

    /**
     *
     */
    @Override
    public void update() {

    }
}

