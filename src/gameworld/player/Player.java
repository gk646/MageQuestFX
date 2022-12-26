package gameworld.player;

import gameworld.Entity;
import gameworld.player.abilities.Ability1;
import gameworld.player.abilities.Lightning;
import gameworld.player.abilities.PrimaryFire;
import gameworld.player.abilities.SecondaryFire;
import gameworld.world.DroppedItem;
import input.KeyHandler;
import input.MotionHandler;
import input.MouseHandler;
import main.MainGame;
import main.system.ui.inventory.InventorySlot;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.Objects;


public class Player extends Entity {
    public static Point startingPoint;
    private final MotionHandler motionHandler;
    private final KeyHandler keyH;
    private final MouseHandler mouseH;
    public int maxMana;
    public float mana;
    public float health;
    //STATS
    public int INT;
    public int VIT;
    public int REG;
    public int SPD;
    private int cooldownOneSecond;
    private int cooldownTwoSecond;
    private int cooldownPrimary;
    private int cdLightning;
    private int experience;
    private int levelUpExperience;
    private int playerQuadrant;
    private int quadrantTimer;
    public float manaRegeneration = 0.02f;
    public float healthRegeneration = 0.002f;
    private boolean respawnsDone;


    public Player(MainGame mainGame, KeyHandler keyH, MouseHandler mouseH, MotionHandler motionHandler) {
        super(mainGame);
        this.motionHandler = motionHandler;
        //-------VALUES-----------
        movementSpeed = 4;
        this.maxHealth = 10;
        this.health = 10;
        this.maxMana = 20;
        this.mana = maxMana;
        this.entityHeight = 48;
        this.entityWidth = 48;
        worldX = 23500;
        worldY = 23500;
        direction = "";
        getPlayerImage();
        this.collisionBox = new Rectangle(8, 8, 32, 32);
        this.level = 1;

        //Handlers
        this.mg = mainGame;
        this.keyH = keyH;
        this.mouseH = mouseH;
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
    }

    public void pickupDroppedItem() {
        try {
            for (DroppedItem drop : mg.droppedItems) {
                if (new Rectangle(mg.player.worldX - 25, mg.player.worldY - 14, mg.player.collisionBox.width + 10, mg.player.collisionBox.height + 17).contains(drop.worldPos)) {
                    for (InventorySlot invSlot : mg.inventP.bag_Slots) {
                        if (invSlot.item == null) {
                            invSlot.item = drop.item;
                            mg.droppedItems.remove(drop);
                            break;
                        }
                    }
                }
                if (drop.item.name.contains("FILLER")) {
                    mg.droppedItems.remove(drop);
                }
            }
        } catch (ConcurrentModificationException ignored) {
        }
    }

    public void getDurabilityDamageArmour() {
        for (InventorySlot invSlot : mg.inventP.char_Slots) {
            if (invSlot.item != null && (!invSlot.item.type.contains("2") && !invSlot.item.type.contains("W") && !invSlot.item.type.contains("O"))) {
                if (Math.random() >= 0.9) {
                    invSlot.item.durability--;
                }
            }
        }
    }

    public void getDurabilityDamageWeapon() {
        for (InventorySlot invSlot : mg.inventP.char_Slots) {
            if (invSlot.item != null && (invSlot.item.type.contains("2") || invSlot.item.type.contains("W") || invSlot.item.type.contains("O"))) {
                if (Math.random() >= 0.95) {
                    invSlot.item.durability--;
                }
            }
        }
    }

    public void update() {
        movement();
        skills();
        if (quadrantTimer >= 100) {
            dynamicSpawns();
            if (respawnsDone) {
                quadrantTimer = 0;
                respawnsDone = false;
            }
        }

        quadrantTimer++;
    }

    public void getExperience(Entity entity) {
        experience += entity.level;
        if (experience >= levelUpExperience) {
            level++;
            updateEquippedItems();
            levelUpExperience = 0;
            for (int i = 1; i <= level; i++) {
                levelUpExperience += (i + i - 1) * (10 + i - 1);
            }
        }
    }

    public void dynamicSpawns() {
        getPlayerQuadrant();
        respawnCloseQuadrants();
    }

    private void getPlayerQuadrant() {
        for (int i = 99; i >= 0; i--) {
            if (worldX / mg.tileSize > mg.wControl.overworldMapQuadrants[i].startTileX && worldY / mg.tileSize > mg.wControl.overworldMapQuadrants[i].startTileY && worldX / mg.tileSize < mg.wControl.overworldMapQuadrants[i].startTileX + 50 && worldY / mg.tileSize < mg.wControl.overworldMapQuadrants[i].startTileY + 50) {
                playerQuadrant = i;
                break;
            }
        }
    }


    private void respawnCloseQuadrants() {
        mg.wControl.overworldMapQuadrants[playerQuadrant].spawnEnemies();
        if (quadrantTimer < 210) {
            mg.wControl.overworldMapQuadrants[Math.min(playerQuadrant + 1, 99)].spawnEnemies();
        } else if (quadrantTimer < 220) {
            mg.wControl.overworldMapQuadrants[Math.max(playerQuadrant - 1, 0)].spawnEnemies();
        } else if (quadrantTimer < 230) {
            mg.wControl.overworldMapQuadrants[Math.min(playerQuadrant + 9, 99)].spawnEnemies();
        } else if (quadrantTimer < 240) {
            mg.wControl.overworldMapQuadrants[Math.min(playerQuadrant + 10, 99)].spawnEnemies();
        } else if (quadrantTimer < 250) {
            mg.wControl.overworldMapQuadrants[Math.min(playerQuadrant + 11, 99)].spawnEnemies();
        } else if (quadrantTimer < 260) {
            mg.wControl.overworldMapQuadrants[Math.max(playerQuadrant - 9, 0)].spawnEnemies();
        } else if (quadrantTimer < 270) {
            mg.wControl.overworldMapQuadrants[Math.max(playerQuadrant - 10, 0)].spawnEnemies();
        } else if (quadrantTimer < 280) {
            mg.wControl.overworldMapQuadrants[Math.max(playerQuadrant - 11, 0)].spawnEnemies();
            respawnsDone = true;
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(entityImage1, screenX, screenY, 48, 48, null);
    }

    private void getPlayerImage() {
        entityImage1 = setup("Mage_down01.png");
    }

    private void movement() {
        collisionRight = false;
        collisionLeft = false;
        collisionDown = false;
        collisionUp = false;
        direction = "updownleftright";
        mg.collisionChecker.checkEntityAgainstTile(this);

        if (keyH.leftPressed) {
            if (!collisionLeft) {
                worldX -= movementSpeed;
            }
        }
        if (keyH.upPressed) {
            if (!collisionUp) {
                worldY -= movementSpeed;
            }
        }
        if (keyH.downPressed) {
            if (!collisionDown) {
                worldY += movementSpeed;
            }
        }

        if (keyH.rightPressed) {
            if (!collisionRight) {
                worldX += movementSpeed;
            }
        }
    }

    private void skills() {
        if (mouseH.mouse1Pressed && cooldownPrimary == 10 && !mg.inventP.wholeBagWindow.contains(mg.motionH.lastMousePosition) && !mg.inventP.wholeCharWindow.contains(mg.motionH.lastMousePosition)) {
            mg.PROJECTILES.add(new PrimaryFire(mg, mouseH));
            cooldownPrimary = 0;
            getDurabilityDamageWeapon();
        }
        if (mouseH.mouse2Pressed && cooldownOneSecond == 60 && this.mana >= 10 && !mg.inventP.wholeBagWindow.contains(mg.motionH.lastMousePosition) && !mg.inventP.wholeCharWindow.contains(mg.motionH.lastMousePosition)) {
            mg.PROJECTILES.add(new SecondaryFire(mg, mouseH));
            mana -= 10;
            cooldownOneSecond = 0;
            getDurabilityDamageWeapon();
        }
        if (keyH.OnePressed && cooldownTwoSecond == 120 && this.mana >= 10) {
            for (int i = 0; i <= 7; i++) {
                mg.PROJECTILES.add(new Ability1(mg, mouseH, i));
            }
            mana -= 10;
            cooldownTwoSecond = 0;
            getDurabilityDamageWeapon();
        }
        if (keyH.TwoPressed && mana >= 20 && cdLightning == 20) {
            mg.PROJECTILES.add(new Lightning(mg, mouseH, motionHandler));
            mana -= 20;
            cdLightning = 0;
            getDurabilityDamageWeapon();
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
        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/Entitys/player/" + imagePath))));
            scaledImage = mg.utilities.scaleImage(scaledImage, 48, 48);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaledImage;
    }
}
