package gameworld.entities.npcs;

import gameworld.dialogue.Dialog;
import gameworld.dialogue.generic.Tutorial;
import gameworld.entities.ENTITY;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Objects;

public class NPC_Man extends ENTITY {
    private final Dialog dial;
    private final Point goalTile = new Point(34, 34);
    private Image player2;
    private boolean show_dialog;
    private Point playerTalkLocation;
    private int dialog_counter;
    private boolean block_dialog;

    public NPC_Man(MainGame mainGame, int dialog_type, int x, int y) {
        this.mg = mainGame;
        this.dial = new Tutorial(mg, dialog_type);
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


    public void draw(GraphicsContext g2) {
        g2.drawImage(player2, worldX - mg.player.worldX + mg.player.screenX, worldY - mg.player.worldY + mg.player.screenY, 48, 48);
        if (show_dialog) {
            dial.draw(g2, this);
        }
    }

    public void update() {
        dial.script();
        if (show_dialog) {
            show_dialog = !mg.util.player_went_away(playerTalkLocation);
            if (dial.text.equals("followNPC")) {
                onPath = true;
                dial.next_stage();
            }
            if ((mg.collisionChecker.checkEntityAgainstEntity(this, mg.player) && mg.inputH.e_typed && !onPath && !dial.block)) {
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
        if (mg.collisionChecker.checkEntityAgainstEntity(this, mg.player) && mg.inputH.e_typed) {
            mg.inputH.e_typed = false;
            show_dialog = true;
            playerTalkLocation = new Point(mg.player.worldX, mg.player.worldY);
        }
        if (onPath) {
            followPlayer(goalTile.x, goalTile.y);
        }
        mg.inputH.e_typed = false;
    }


    private void getPlayerImage() {
        player2 = setup("man01.png");
    }


    private Image setup(String imagePath) {

        return new Image((Objects.requireNonNull(getClass().getResourceAsStream("/Entitys/npc/" + imagePath))));
    }
}

