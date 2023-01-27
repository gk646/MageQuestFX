package gameworld.entities.NPC;

import gameworld.Entity;
import gameworld.dialogue.Dialog;
import main.MainGame;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class NPC_Man extends Entity {
    private BufferedImage player2;
    private final Dialog dial;
    private final Point goalTile = new Point(34, 34);
    private boolean show_dialog;
    private Point playerTalkLocation;
    private boolean block_dialog, follow;
    private int dialog_counter;

    public NPC_Man(MainGame mainGame, int dialog_type, int x, int y) {
        this.mg = mainGame;
        this.dial = new Dialog(mg, dialog_type);
        //Setting default values
        getPlayerImage();
        worldX = x;
        worldY = y;
        this.entityHeight = 48;
        this.entityWidth = 48;
        this.movementSpeed = 4;
        this.collisionBox = new Rectangle(0, 0, 42, 42);
        direction = "updownleftright";
    }


    public void draw(Graphics2D g2) {
        g2.drawImage(player2, worldX - mg.player.worldX + mg.player.screenX, worldY - mg.player.worldY + mg.player.screenY, 48, 48, null);
        if (show_dialog) {
            dial.draw(g2, this);
        }
    }

    public void update() {
        if (show_dialog) {
            if ((mg.collisionChecker.checkEntityAgainstEntity(this, mg.player) && mg.keyHandler.e_typed && !onPath)) {
                dial.next_stage();
            }
            show_dialog = !mg.util.player_went_away(playerTalkLocation);
            if (dial.text.equals("followNPC")) {
                onPath = true;
                dial.next_stage();
            }
        }
        if (onPath) {
            dialog_counter++;
            if (dialog_counter > 140) {
                show_dialog = false;
                dialog_counter = 0;
            }
        }
        if (mg.collisionChecker.checkEntityAgainstEntity(this, mg.player) && mg.keyHandler.e_typed) {
            mg.keyHandler.e_typed = false;
            show_dialog = true;
            playerTalkLocation = new Point(mg.player.worldX, mg.player.worldY);
        }
        if (onPath) {
            moveToTile(goalTile.x, goalTile.y);
        }
        mg.keyHandler.e_typed = false;
    }


    private void getPlayerImage() {
        player2 = setup("man01.png");
    }

    private void owlyMovement() {
        if (onPath && searchTicks >= Math.random() * 45) {
            getNearestPlayer();
            searchPath(goalCol, goalRow, 100);
            searchTicks = 0;
        } else if (onPath) {
            trackPath(goalCol, goalRow);
        }
    }

    private BufferedImage setup(String imagePath) {
        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/Entitys/npc/" + imagePath))));
            scaledImage = mg.imageSetup.scaleImage(scaledImage, 48, 48);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaledImage;
    }
}

