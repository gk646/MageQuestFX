package gameworld.entities.npcs;

import gameworld.dialogue.generic.Tutorial;
import gameworld.entities.NPC;
import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Objects;

public class NPC_Man extends NPC {


    public NPC_Man(MainGame mainGame, int dialog_type, int x, int y) {
        this.mg = mainGame;
        this.dial = new Tutorial(mg, dialog_type, this);
        goalTile = new Point(34, 34);
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
        g2.drawImage(player2, worldX - Player.worldX + Player.screenX, worldY - Player.worldY + Player.screenY, 48, 48);
        if (show_dialog) {
            dial.draw(g2, this);
        }
    }

    public void update() {
        dial.script(this);

        if (show_dialog) {
            show_dialog = !mg.util.player_went_away(playerTalkLocation);
            if ((mg.collisionChecker.checkEntityAgainstPlayer(this, mg.player) && mg.inputH.e_typed && !onPath && !dial.block)) {
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
        if (mg.collisionChecker.checkEntityAgainstPlayer(this, mg.player) && mg.inputH.e_typed) {
            this.show_dialog = true;
            playerTalkLocation = new Point((int) Player.worldX, (int) Player.worldY);
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

