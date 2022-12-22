package gameworld.entitys;

import gameworld.Entity;
import gameworld.projectiles.Ability1;
import gameworld.projectiles.Lightning;
import gameworld.projectiles.PrimaryFire;
import gameworld.projectiles.SecondaryFire;
import input.KeyHandler;
import input.MotionHandler;
import input.MouseHandler;
import main.MainGame;
import main.system.Utilities;
import main.system.ui.InventorySlot;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;


public class Player extends Entity {
    public static Point startingPoint;
    //STATS
    public int INT, VIT, REG, SPD;
    public int maxMana, cooldownOneSecond, cooldownTwoSecond, cooldownPrimary, cdLightning, experience, levelUpExperience;
    public float mana, health, manaRegeneration = 0.02f, healthRegeneration = 0.002f;

    public final MotionHandler motionHandler;
    private final KeyHandler keyHandler;
    private final MouseHandler mouseHandler;


    public Player(MainGame mainGame, KeyHandler keyHandler, MouseHandler mouseHandler, MotionHandler motionHandler) {
        super(mainGame);
        this.motionHandler = motionHandler;
        //-------VALUES-----------
        movementSpeed = 4;
        this.maxHealth = 10;
        this.health = 7;
        this.maxMana = 20;
        this.mana = maxMana;
        this.entityHeight = 48;
        this.entityWidth = 48;
        worldX = 12000;
        worldY = 12000;
        direction = "up";
        getPlayerImage();
        this.collisionBox = new Rectangle(8, 16, 31, 31);
        this.level = 1;

        //Handlers
        this.mg = mainGame;
        this.keyHandler = keyHandler;
        this.mouseHandler = mouseHandler;
        screenX = MainGame.SCREEN_WIDTH / 2 - 24;
        screenY = MainGame.SCREEN_HEIGHT / 2 - 24;

    }

    public void updateEquippedItems() {
        INT = 0;
        VIT = 0;
        SPD = 0;
        REG = 0;
        for (InventorySlot invSlot : mg.inventP.char_Slots) {
            if (invSlot.item != null) {
                INT += invSlot.item.INT;
                VIT += invSlot.item.VIT;
                SPD += invSlot.item.SPD;
                REG += invSlot.item.REG;
            }
        }
        maxHealth = (int) (9f + ((10f + VIT) / (10f) + VIT) * (level + (0.3f * VIT)));
        maxMana = (int) (19f + ((10f + INT) / (10f) + INT) * (level + (0.5 * INT)));
        manaRegeneration = ((0.3f + INT / 10f) * (level + level - 1)) / 60;
        healthRegeneration = (0.05f * level) / 60;
        movementSpeed = 4 + SPD;
        //System.out.println(maxHealth + " " + VIT + " " + maxMana + " " + INT);
    }

    public void update() {
        movement();
        skills();
    }

    public void getExperience(Entity entity) {
        experience += entity.level;
        levelUpExperience = 0;
        for (int i = 1; i <= level; i++) {
            levelUpExperience += (i + i - 1) * (10 + i - 1);
        }
        if (experience >= levelUpExperience) {
            level++;
            updateEquippedItems();
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(entityImage1, screenX, screenY, 48, 48, null);
    }

    public void getPlayerImage() {
        entityImage1 = setup("Mage_down01.png");
    }

    private void movement() {
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

        mg.collisionChecker.checkEntityAgainstTile(this);

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
    }

    private void skills() {
        if (this.mouseHandler.mouse1Pressed && cooldownPrimary == 10 && !mg.inventP.wholeBagWindow.contains(mg.motionH.lastMousePosition) && !mg.inventP.wholeCharWindow.contains(mg.motionH.lastMousePosition)) {
            mg.PROJECTILES.add(new PrimaryFire(mg, mouseHandler));
            cooldownPrimary = 0;
        }
        if (this.mouseHandler.mouse2Pressed && cooldownOneSecond == 60 && this.mana >= 10 && !mg.inventP.wholeBagWindow.contains(mg.motionH.lastMousePosition) && !mg.inventP.wholeCharWindow.contains(mg.motionH.lastMousePosition)) {
            mg.PROJECTILES.add(new SecondaryFire(mg, mouseHandler));
            mana -= 10;
            cooldownOneSecond = 0;
        }
        if (this.keyHandler.OnePressed && cooldownTwoSecond == 120 && this.mana >= 10) {
            for (int i = 0; i <= 7; i++) {
                mg.PROJECTILES.add(new Ability1(mg, mouseHandler, i));
            }
            mana -= 10;
            cooldownTwoSecond = 0;
        }
        if (keyHandler.TwoPressed && mana >= 20 && cdLightning == 20) {
            mg.PROJECTILES.add(new Lightning(mg, mouseHandler, motionHandler));
            mana -= 20;
            cdLightning = 0;
        }
        if (mana < maxMana) {
            mana += manaRegeneration;
        } else if (mana > maxMana) {
            mana = maxMana;
        }
        if (health < maxHealth) {
            health += healthRegeneration;
        } else if (health > maxHealth) {
            health = maxHealth;
        }
        if (cooldownPrimary < 10) {
            cooldownPrimary++;
        }
        if (cooldownOneSecond < 60) {
            cooldownOneSecond++;
        }
        if (cooldownTwoSecond < 120) {
            cooldownTwoSecond++;
        }
        if (cdLightning < 20) {
            cdLightning++;
        }
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
