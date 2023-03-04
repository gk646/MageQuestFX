package gameworld.entities.npcs;

import gameworld.entities.NPC;
import gameworld.player.Player;
import gameworld.quest.Dialog;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;
import main.system.enums.Zone;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Objects;

public class NPC_OldMan extends NPC {
    private Image idle1, idle2, idle3, idle4;


    public NPC_OldMan(MainGame mainGame, int xTile, int yTile, Zone zone) {
        this.dialog = new Dialog();
        this.zone = zone;
        this.mg = mainGame;
        goalTile = new Point(34, 34);
        //Setting default values
        getPlayerImage();
        worldX = xTile * 48;
        worldY = yTile * 48;
        this.entityHeight = 48;
        this.entityWidth = 48;
        this.movementSpeed = 2;
        this.collisionBox = new Rectangle(0, 0, 42, 42);
        direction = "updownleftright";
    }

    @Override
    public void draw(GraphicsContext gc) {
        screenX = (int) (worldX - Player.worldX + Player.screenX);
        screenY = (int) (worldY - Player.worldY + Player.screenY);
        if (onPath) {
            switch (spriteCounter % 180 / 30) {
                case 0 -> gc.drawImage(entityImage1, screenX, screenY);
                case 1 -> gc.drawImage(entityImage2, screenX, screenY);
                case 2 -> gc.drawImage(entityImage3, screenX, screenY);
                case 3 -> gc.drawImage(entityImage4, screenX, screenY);
                case 4 -> gc.drawImage(entityImage5, screenX, screenY);
                case 5 -> gc.drawImage(entityImage6, screenX, screenY);
            }
        } else {
            switch (spriteCounter % 120 / 30) {
                case 0 -> gc.drawImage(idle1, screenX, screenY);
                case 1 -> gc.drawImage(idle2, screenX, screenY);
                case 2 -> gc.drawImage(idle3, screenX, screenY);
                case 3 -> gc.drawImage(idle4, screenX, screenY);
            }
        }
        if (show_dialog) {
            dialog.drawDialog(gc, this);
        }
        spriteCounter++;
    }

    @Override
    public void update() {
        super.update();
        if (show_dialog) {
            dialogHideDelay++;
            show_dialog = !mg.wControl.player_went_away(playerTalkLocation, 500);
        }
        if (dialogHideDelay > 600) {
            show_dialog = false;
            dialogHideDelay = 0;
        }
        if (onPath) {
            moveTo(goalTile.x, goalTile.y, checkPoints);
        }
    }


    private void getPlayerImage() {
        this.entityImage1 = setup("walk/1.png");
        this.entityImage2 = setup("walk/2.png");
        this.entityImage3 = setup("walk/3.png");
        this.entityImage4 = setup("walk/4.png");
        this.entityImage5 = setup("walk/5.png");
        this.entityImage6 = setup("walk/6.png");
        this.idle1 = setup("idle/1.png");
        this.idle2 = setup("idle/2.png");
        this.idle3 = setup("idle/3.png");
        this.idle4 = setup("idle/4.png");
    }

    private Image setup(String imagePath) {
        return new Image((Objects.requireNonNull(getClass().getResourceAsStream("/Entitys/npc/oldman/" + imagePath))));
    }
}

