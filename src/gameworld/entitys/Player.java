package gameworld.entitys;

import gameworld.Entity;
import gameworld.projectiles.Ability1;
import gameworld.projectiles.PrimaryFire;
import gameworld.projectiles.SecondaryFire;
import input.KeyHandler;
import input.MouseHandler;
import main.MainGame;
import main.system.Utilities;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;


public class Player extends Entity {
    public static Point startingPoint;
    public float mana, health;
    public final int maxMana;
    public int cooldownOneSecond;
    public int cooldownTwoSecond;
    public int cooldownPrimary;
    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;

    public Player(MainGame mainGame, KeyHandler keyHandler, MouseHandler mouseHandler) {
        super(mainGame);
        //-------VALUES-----------
        movementSpeed = 4;
        this.maxHealth = 10;
        this.health = 7;
        this.maxMana = 20;
        this.mana = maxMana;
        this.entityHeight = 48;
        this.entityWidth = 48;
        worldX = startingPoint.x;
        worldY = startingPoint.y;
        direction = "up";
        getPlayerImage();
        this.collisionBox = new Rectangle(8, 16, 31, 31);

        //Handlers
        this.mainGame = mainGame;
        this.keyHandler = keyHandler;
        this.mouseHandler = mouseHandler;
        screenX = MainGame.SCREEN_WIDTH / 2 - 24;
        screenY = MainGame.SCREEN_HEIGHT / 2 - 24;
    }


    public void update() {
        direction = "";
        if (keyHandler.leftPressed) {
            direction += "left";
        }
        if (keyHandler.upPressed) {
            direction += "up";
        }
        if (keyHandler.downPressed) {
            direction += "down";
        }

        if (keyHandler.rightPressed) {
            direction += "right";
        }
        //check tile collision
        collisionRight = false;
        collisionLeft = false;
        collisionDown = false;
        collisionUp = false;

        mainGame.collisionChecker.checkEntityAgainstTile(this);

        if (direction.contains("left")) {
            if (!collisionLeft) {
                worldX -= movementSpeed;
            }
        }

        if (direction.contains("up")) {
            if (!collisionUp) {
                worldY -= movementSpeed;
            }
        }

        if (direction.contains("down")) {
            if (!collisionDown) {
                worldY += movementSpeed;
            }
        }
        if (direction.contains("right")) {

            if (!collisionRight) {
                worldX += movementSpeed;
            }
        }
        if (this.mouseHandler.mouse1Pressed && cooldownPrimary == 10) {
            mainGame.PROJECTILES.add(new PrimaryFire(mainGame, mouseHandler));
            cooldownPrimary = 0;
        }
        if (this.mouseHandler.mouse2Pressed && cooldownOneSecond == 60 && this.mana >= 10) {
            mainGame.PROJECTILES.add(new SecondaryFire(mainGame, mouseHandler));
            mana -= 10;
            cooldownOneSecond = 0;
        }
        if (this.keyHandler.OnePressed && cooldownTwoSecond == 120 && this.mana >= 10) {
            for (int i = 0; i <= 7; i++) {
                mainGame.PROJECTILES.add(new Ability1(mainGame, mouseHandler, i));
            }
            mana -= 10;
            cooldownTwoSecond = 0;
        }
        if (mana < maxMana) {
            mana += 0.05;
        }
        if (health < maxHealth) {
            health += 0.002;
        }
        if(cooldownPrimary <10){
            cooldownPrimary++;
        }
        if (cooldownOneSecond < 60) {
            cooldownOneSecond++;
        }
        if (cooldownTwoSecond < 120) {
            cooldownTwoSecond++;
        }
    }


    public void draw(Graphics2D g2) {
        g2.drawImage(up1, screenX, screenY, 48, 48, null);

    }

    public void getPlayerImage() {
        up1 = setup("Mage_down01.png");


    }

    private BufferedImage setup(String imagePath) {
        Utilities utilities = new Utilities();
        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/" + imagePath))));
            scaledImage = utilities.scaleImage(scaledImage, 48, 48);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaledImage;
    }

}
