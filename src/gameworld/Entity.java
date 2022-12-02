package gameworld;

import gameworld.entitys.Enemy;
import main.MainGame;

import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * Only used for handling Enemies atm.
 * Main inheritable class for all game world entity's
 */
public class Entity {
    public int worldY, worldX, entityWidth, entityHeight, screenX, screenY;

    public int movementSpeed, health, maxHealth, hpBarCounter, entityHealthBarLength;
    public MainGame mainGame;
    public BufferedImage up1;

    public String direction;
    public Rectangle collisionBox;
    public boolean initializeEnemies, hpBarOn;
    public boolean collisionUp, collisionDown, collisionLeft, collisionRight, dead;

    public Entity(MainGame mainGame) {

        this.mainGame = mainGame;

    }

    /**
     * Only really updates enemy position
     */
    public void update() {

        if (!initializeEnemies) {
            spawnEnemies();
            initializeEnemies = true;
        }
        for (Entity entity : mainGame.ENTITIES) {
            entity.update();
            if (entity.hpBarCounter >= 600) {
                entity.hpBarOn = false;
                entity.hpBarCounter = 0;
            }
            if(entity.hpBarOn){
                entity.hpBarCounter++;
            }
        }
        mainGame.ENTITIES.removeIf(entity -> entity.dead);

    }

    /**
     * Used for drawing health bars
     */
    public void draw(Graphics2D g2) {
        for (Entity entity1 : mainGame.ENTITIES) {
            entity1.draw(g2);
            if (entity1.hpBarOn) {
                g2.setColor(new Color(0xFF0044));
                g2.fillRect(entity1.screenX, entity1.screenY - 10,  (int) (((float) entity1.health / (float) entity1.maxHealth) * entity1.entityWidth), 8);
                g2.setColor(new Color(0xFFFFFF));
                g2.setFont(mainGame.ui.maruMonica);
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20f));
                g2.drawString(entity1.health + "", entity1.screenX + 14, entity1.screenY);
            }
        }
    }

    public void spawnEnemies() {
        mainGame.ENTITIES.add(new Enemy(mainGame, 2300, 2400, 1));
        mainGame.ENTITIES.add(new Enemy(mainGame, 2550, 2400, 99));
        mainGame.ENTITIES.add(new Enemy(mainGame, 2650, 2400, 99));
        mainGame.ENTITIES.add(new Enemy(mainGame, 2760, 2500, 99));
        mainGame.ENTITIES.add(new Enemy(mainGame, 2800, 2550, 99));
        mainGame.ENTITIES.add(new Enemy(mainGame, 2900, 2600, 99));
    }

}
