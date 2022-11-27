package gameworld;

import gameworld.entitys.Enemy;
import main.MainGame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Only used for handling Enemies atm.
 * Main inheritable class for all game world entity's
 */
public class Entity {
    public int worldY, worldX, entityWidth, entityHeight, screenX, screenY;
    public final ArrayList<Entity> entities;
    public int movementSpeed, health;
    public MainGame mainGame;
    public BufferedImage up1;

    public String direction;
    public Rectangle collisionBox;
    private boolean initializeEnemies = true;
    public boolean collisionUp, collisionDown, collisionLeft, collisionRight, dead;

    public Entity(MainGame mainGame) {
        this.entities = new ArrayList<>();
        this.mainGame = mainGame;

    }

    /**
     * Only really updates enemy position
     */
    public void update() {
        if (initializeEnemies) {
            spawnEnemies();
            initializeEnemies = false;
        }
        for (Entity entity : entities) {
            if (entity != null) {
                entity.update();

            }
        }
    }

    /**
     * Used for drawing health bars
     */
    public void draw(Graphics2D g2) {
        for (Entity entity1 : entities) {
            entity1.draw(g2);
            g2.setColor(Color.black);
            g2.drawString(entity1.health + "", entity1.screenX, entity1.screenY);
        }
    }

    public void spawnEnemies() {
        entities.add(new Enemy(mainGame, 2300, 2400, 50));
        entities.add(new Enemy(mainGame, 2550, 2400, 99));
        entities.add(new Enemy(mainGame, 2650, 2400, 99));
        entities.add(new Enemy(mainGame, 2760, 2500, 99));
        entities.add(new Enemy(mainGame, 2800, 2550, 99));
        entities.add(new Enemy(mainGame, 2900, 2600, 99));
    }

}
