package gameworld.entities.npcs.quests;

import gameworld.entities.NPC;
import gameworld.entities.loadinghelper.ResourceLoaderEntity;
import gameworld.player.Player;
import gameworld.quest.Dialog;
import gameworld.quest.QUEST;
import gameworld.quest.quests.QST_MarlaFakeNecklace;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.enums.Zone;

import java.awt.Point;
import java.awt.Rectangle;

public class NPC_Marla extends NPC {
    private boolean once;
    private int counter;
    private boolean twice;
    private boolean gotQuest;

    public NPC_Marla(MainGame mainGame, int xTile, int yTile) {
        this.dialog = new Dialog();
        this.animation = new ResourceLoaderEntity("npc/marla");
        animation.load();
        this.zone = Zone.Hillcrest;
        this.mg = mainGame;
        goalTile = new Point();
        worldX = xTile * 48;
        worldY = yTile * 48;
        this.entityHeight = 48;
        this.entityWidth = 48;
        this.movementSpeed = 2;
        this.collisionBox = new Rectangle(0, 0, 42, 42);
        direction = "updownleftright";
        spriteCounter = (int) (Math.random() * 20);
    }

    @Override
    public void update() {
        super.update();
        if (show_dialog) {
            dialogHideDelay++;
            show_dialog = !mg.wControl.player_went_away(playerTalkLocation, 800);
        }
        if (dialogHideDelay > 600) {
            show_dialog = false;
            dialogHideDelay = 0;
        }
        if (onPath) {
            moveTo(goalTile.x, goalTile.y, checkPoints);
        }
        if (!gotQuest) {
            if (!once && QUEST.playerCloseToAbsolute((int) worldX, (int) worldY, 400)) {
                dialog.loadNewLine("Hey, you. Yeah you with the bat black coat. Would you hear out a damsel in distress?");
                show_dialog = true;
                dialogHideDelay = 0;
                playerTalkLocation = new Point(activeTile.x * 48, activeTile.y * 48);
                once = true;
            } else if (!twice && !show_dialog && QUEST.playerCloseToAbsolute((int) worldX, (int) worldY, 400)) {
                dialog.loadNewLine("Hello? Dont you see iam talking to you?!");
                show_dialog = true;
                dialogHideDelay = 0;
                playerTalkLocation = new Point(activeTile.x * 48, activeTile.y * 48);
                twice = true;
            } else if (once && !QUEST.playerCloseToAbsolute((int) worldX, (int) worldY, 600)) {
                counter++;
                if (counter >= 1500) {
                    once = false;
                    twice = false;
                }
            }
            if (mg.collisionChecker.checkEntityAgainstPlayer(this, 8) && mg.inputH.e_typed && !mg.qPanel.PlayerHasQuests("The Fake Necklace")) {
                mg.inputH.e_typed = false;
                mg.qPanel.quests.add(new QST_MarlaFakeNecklace(mg, "The Fake Necklace"));
                gotQuest = true;
                dialog.dialogRenderCounter = 2000;
            }
        }
    }

    /**
     *
     */
    public void draw(GraphicsContext gc) {
        screenX = (int) (worldX - Player.worldX + Player.screenX);
        screenY = (int) (worldY - Player.worldY + Player.screenY);
        if (onPath) {
            drawWalk(gc);
        } else {
            drawIdle(gc);
        }
        if (show_dialog) {
            dialog.drawDialog(gc, this);
        }
        spriteCounter++;
        drawNPCName(gc, "Marla");
    }

    private void drawIdle(GraphicsContext gc) {
        switch (spriteCounter % 120 / 30) {
            case 0 -> gc.drawImage(animation.idle.get(0), screenX + 10, screenY - 20);
            case 1 -> gc.drawImage(animation.idle.get(1), screenX + 10, screenY - 20);
            case 2 -> gc.drawImage(animation.idle.get(2), screenX + 10, screenY - 20);
            case 3 -> gc.drawImage(animation.idle.get(3), screenX + 10, screenY - 20);
        }
    }

    private void drawWalk(GraphicsContext gc) {
        switch (spriteCounter % 180 / 30) {
            case 0 -> gc.drawImage(animation.walk.get(0), screenX + 10, screenY - 20);
            case 1 -> gc.drawImage(animation.walk.get(1), screenX + 10, screenY - 20);
            case 2 -> gc.drawImage(animation.walk.get(2), screenX + 10, screenY - 20);
            case 3 -> gc.drawImage(animation.walk.get(3), screenX + 10, screenY - 20);
            case 4 -> gc.drawImage(animation.walk.get(4), screenX + 10, screenY - 20);
            case 5 -> gc.drawImage(animation.walk.get(5), screenX + 10, screenY - 20);
        }
    }
}
