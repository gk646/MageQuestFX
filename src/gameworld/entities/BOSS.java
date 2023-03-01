package gameworld.entities;

import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import main.MainGame;
import main.system.enums.Zone;
import main.system.ui.Colors;
import main.system.ui.FonT;

import java.util.Objects;


abstract public class BOSS extends ENTITY {
    protected String name;
    protected Image bossBar = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/skillbar/ui/bossbar.png")));

    public BOSS(MainGame mg, int x, int y, int level, int health, Zone zone) {
        this.mg = mg;
        this.zone = zone;
        this.worldX = x;
        this.worldY = y;
        this.level = level;
        this.maxHealth = ((9 + level) * (level + level - 1)) * 15;
        movementSpeed = 2;
        this.health = maxHealth;
        this.direction = "leftrightdownup";
    }

    /**
     * @param gc Graphics context
     */
    @Override
    abstract public void draw(GraphicsContext gc);


    /**
     *
     */
    @Override
    public void update() {
        tickEffects();
        activeTile.x = (int) ((worldX + 24) / 48);
        activeTile.y = (int) ((worldY + 24) / 48);
        if (health <= 0) {
            dead = true;
            playGetHitSound();
        }
        if (hpBarCounter >= 600) {
            hpBarOn = false;
            hpBarCounter = 0;
        } else if (hpBarOn) {
            hpBarCounter++;
        }
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")

    public boolean playerTooFarAbsoluteBoss(int x) {
        return Math.abs(worldX - Player.worldX) >= x || Math.abs(worldY - Player.worldY) >= x;
    }

    protected boolean isOnPlayer() {
        return (int) (worldX + 24) / 48 == mg.playerX && (int) (worldY + 24) / 48 == mg.playerY;
    }

    protected void drawBossHealthBar(GraphicsContext gc) {
        gc.setEffect(mg.ui.shadow);
        gc.setFill(Colors.bossNamePurple);
        gc.setFont(FonT.editUndo22);
        drawCenteredText(gc, name, MainGame.SCREEN_HEIGHT * 0.051f);
        gc.setEffect(null);
        gc.setFill(Colors.Red);
        gc.fillRect(MainGame.SCREEN_HEIGHT * 0.736f, MainGame.SCREEN_HEIGHT * 0.061f, (health / (float) maxHealth) * 322, 15);
        gc.drawImage(bossBar, MainGame.SCREEN_HEIGHT * 0.723f, MainGame.SCREEN_HEIGHT * 0.047_5f);
    }

    private void drawCenteredText(GraphicsContext gc, String text, float y) {
        Text textNode = new Text(text);
        textNode.setFont(gc.getFont());
        double textWidth = textNode.getLayoutBounds().getWidth();
        double x = (gc.getCanvas().getWidth() - textWidth) / 2;
        gc.fillText(text, x, y);
    }
}
