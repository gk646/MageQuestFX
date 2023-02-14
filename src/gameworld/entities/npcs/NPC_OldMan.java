package gameworld.entities.npcs;

import gameworld.entities.NPC;
import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Objects;

public class NPC_OldMan extends NPC {
    private Image idle1, idle2, idle3, idle4;


    public NPC_OldMan(MainGame mainGame, int x, int y) {
        this.mg = mainGame;
        goalTile = new Point(34, 34);
        //Setting default values
        getPlayerImage();
        worldX = x;
        worldY = y;
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
            dial.draw(gc, this);
        }
        spriteCounter++;
    }

    @Override
    public void update() {
        if (show_dialog) {
            show_dialog = !mg.map_utils.player_went_away(playerTalkLocation);
            if ((mg.collisionChecker.checkEntityAgainstPlayer(this, 0) && mg.inputH.e_typed && !onPath && !blockInteraction)) {
                dial.next_stage();
            }
        }
        if (onPath) {
            dialog_counter++;
            followPlayer(goalTile.x, goalTile.y);
            if (dialog_counter > 140) {
                show_dialog = false;
                dialog_counter = 0;
            }
        }
        if (mg.collisionChecker.checkEntityAgainstPlayer(this, 0) && mg.inputH.e_typed) {
            this.show_dialog = true;
            playerTalkLocation = new Point((int) Player.worldX, (int) Player.worldY);
        }
        mg.inputH.e_typed = false;
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

