package gameworld;

import gameworld.entitys.Enemy;
import main.MainGame;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Main inheritable class for all gameworld.entity's
 */
public class Entity {
    public int worldY, worldX, entityWidth, entityHeight;
    Entity  [] entities;
    public int movementSpeed;
    public MainGame mainGame;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2,player2;
    public String direction;
    public Rectangle collisionBox;
    private boolean initializeEnemies=true;
    public boolean collisionup, collisiondown, collisionleft, collisionright, dead;
    public Entity(MainGame mainGame){
        this.entities = new Entity[100];
        this.mainGame = mainGame;

    }
    public void update(){
        if(initializeEnemies){
            spawnEnemies();
            initializeEnemies =false;
        }
        for(Entity entity : entities){
            if(entity !=null) {
                entity.update();
            }
        }
    }
    public void draw(Graphics2D g2){
        for (Entity entity : entities){
            if(entity !=null) {
                entity.draw(g2);
            }
        }
    }
    public void spawnEnemies(){
        entities[0]= new Enemy(mainGame,2400,2400,50,new Point(2400,2400));
    }

}
