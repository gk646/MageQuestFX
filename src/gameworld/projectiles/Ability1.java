package gameworld.projectiles;

import gameworld.Projectile;
import gameworld.entitys.Player;
import input.KeyHandler;
import input.MotionHandler;
import input.MouseHandler;
import main.MainGame;

import java.awt.*;

public class Ability1 extends Projectile {

    private final int version;

    /**
     * What happens when you press (1). Part of
     * {@link Projectile}
     */
    public Ability1(MainGame mainGame, MotionHandler motionHandler, MouseHandler mouseHandler, KeyHandler keyHandler, int version) {
        super(mainGame, motionHandler, mouseHandler, keyHandler);

        //-------VALUES-----------
        this.movementSpeed = 8;
        this.entityHeight = 25;
        this.entityWidth = 25;
        this.collisionBox = new Rectangle(0, 0, 25, 25);
        this.version = version;

        //------POSITION-----------
        this.screenPosition = new Point(MainGame.SCREEN_WIDTH / 2 + mainGame.player.worldX - Player.startingPoint.x, MainGame.SCREEN_HEIGHT / 2 + mainGame.player.worldY - Player.startingPoint.y);
        this.worldX = screenPosition.x + 1700;
        this.worldY = screenPosition.y + 1950;
        this.direction = "downleftrightup";
        this.updateVector = new Point(1, 1);
        getUpdateVector();
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.red);
        g2.drawRect(screenPosition.x - mainGame.player.worldX + Player.startingPoint.x, screenPosition.y - mainGame.player.worldY + Player.startingPoint.y, entityWidth, entityHeight);
    }

    @Override
    public void update() {
        mainGame.collisionChecker.checkEntityAgainstTile(this);
        if (collisionUp || collisionDown || collisionLeft || collisionRight) {
            this.dead = true;
        }

        screenPosition.x += updateVector.x;
        screenPosition.y += updateVector.y;
        worldX = screenPosition.x + Player.startingPoint.x - MainGame.SCREEN_WIDTH / 2 + 24;
        worldY = screenPosition.y + Player.startingPoint.y - MainGame.SCREEN_HEIGHT / 2 + 24;
    }

    private void getUpdateVector() {
        if (version == 0) {
            this.updateVector.x = movementSpeed;
            this.updateVector.y = movementSpeed;
        }
        if (version == 1) {
            this.updateVector.x = -1 * movementSpeed;
            this.updateVector.y = -1 * movementSpeed;
        }
        if (version == 2) {
            this.updateVector.x = -1 * movementSpeed;
            this.updateVector.y = movementSpeed;
        }
        if (version == 3) {
            this.updateVector.x = movementSpeed;
            this.updateVector.y = -1 * movementSpeed;
        }
        if (version == 4) {
            this.updateVector.x = 0;
            this.updateVector.y = movementSpeed;
        }
        if (version == 5) {
            this.updateVector.x = -1 * movementSpeed;
            this.updateVector.y = 0;
        }
        if (version == 6) {
            this.updateVector.x = movementSpeed;
            this.updateVector.y = 0;
        }
        if (version == 7) {
            this.updateVector.x = 0;
            this.updateVector.y = -1 * movementSpeed;
        }
    }

}