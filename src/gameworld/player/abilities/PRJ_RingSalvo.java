package gameworld.player.abilities;

import gameworld.PRJ_Control;
import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.MainGame;

import java.awt.Point;
import java.awt.Rectangle;

public class PRJ_RingSalvo extends PRJ_Control {

    private final int version;

    /**
     * What happens when you press (1). Part of
     * {@link PRJ_Control}
     */
    public PRJ_RingSalvo(MainGame mainGame, int version) {
        super(mainGame);

        //-------VALUES-----------
        this.movementSpeed = 5;
        this.projectileHeight = 25;
        this.projectileWidth = 25;
        this.collisionBox = new Rectangle(0, 0, 25, 25);
        this.version = version;

        //------POSITION-----------
        this.worldPos = new java.awt.geom.Point2D.Double(Player.worldX, Player.worldY);
        this.endPos = new Point((int) (worldPos.x + 650), (int) (worldPos.y + 650));
        this.direction = "downleftrightup";
        this.updateVector = new java.awt.geom.Point2D.Double(1, 1);
        getUpdateVector();
    }

    @Override
    public void draw(GraphicsContext g2) {
        g2.setStroke(Color.RED);
        g2.strokeRect((int) worldPos.x - Player.worldX + Player.screenX + 12, (int) worldPos.y - Player.worldY + Player.screenY + 12, projectileWidth, projectileHeight);
    }

    @Override
    public void update() {
        outOfBounds();
        tileCollision();
        worldPos.x += updateVector.x;
        worldPos.y += updateVector.y;
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