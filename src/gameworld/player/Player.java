package gameworld.player;

import gameworld.entities.ENTITY;
import gameworld.player.abilities.PRJ_AutoShot;
import gameworld.player.abilities.PRJ_Lightning;
import gameworld.world.objects.DROP;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import main.MainGame;
import main.system.enums.Map;
import main.system.ui.inventory.UI_InventorySlot;

import java.awt.Rectangle;
import java.util.ConcurrentModificationException;
import java.util.Objects;


public class Player extends ENTITY {
    public int maxMana;
    public float mana;
    public float health;
    //STATS
    public int intellect;
    public int vitality;
    public int wisdom;
    public int agility;
    public int luck;
    public int charisma;
    public int endurance;
    public int strength;
    public int focus;
    public float critChance, dotDamageMultiplier, dotLengthMultiplier, buffLengthMultiplier, speechSkill, resistChance, carryWeight;
    public int cooldownOneSecond;
    public int cooldownTwoSecond;
    public int experience;
    public int coins;
    public int spawnLevel;
    public float manaRegeneration = 0.02f;
    public float healthRegeneration = 0.002f;
    public float playerMovementSpeed;
    private int cooldownPrimary;
    private int cdLightning;
    private int playerQuadrant;
    private int levelUpExperience = 10;
    private int quadrantTimer;
    private boolean respawnsDone;
    // PLAYER X AND Y ALWAYS +24
    public static float worldX, worldY;
    // screenx is half width -24
    public static int screenX, screenY;

    public Player(MainGame mainGame) {
        this.mg = mainGame;
        //-------VALUES-----------
        this.playerMovementSpeed = 4;
        this.movementSpeed = 4;
        this.maxHealth = 10;
        this.maxMana = 20;
        this.health = maxHealth;
        this.mana = maxMana;
        this.entityHeight = 48;
        this.entityWidth = 48;
        worldX = 24;
        worldY = 24;
        direction = "";
        getPlayerImage();
        this.collisionBox = new Rectangle(8, 8, 32, 32);
        this.level = 1;
        screenX = MainGame.SCREEN_WIDTH / 2 - 24;
        screenY = MainGame.SCREEN_HEIGHT / 2 - 24;
    }

    public void setPosition(int x, int y) {
        worldX = x + 24;
        worldY = y + 24;
    }

    public void updateEquippedItems() {
        intellect = 0;
        vitality = 0;
        agility = 0;
        wisdom = 0;
        luck = 0;
        charisma = 0;
        endurance = 0;
        strength = 0;
        focus = 0;
        for (UI_InventorySlot invSlot : mg.inventP.char_Slots) {
            if (invSlot.item != null) {
                intellect += invSlot.item.intellect;
                vitality += invSlot.item.vitality;
                agility += invSlot.item.agility;
                wisdom += invSlot.item.wisdom;
                luck += invSlot.item.luck;
                charisma += invSlot.item.charisma;
                endurance += invSlot.item.endurance;
                strength += invSlot.item.strength;
                focus += invSlot.item.focus;
            }
        }
        mg.player.maxHealth = (int) ((9.0f + mg.player.vitality * 1.5f + mg.player.endurance / 2.0f) * Math.sqrt(mg.player.level));
        mg.player.maxMana = (int) ((19.0f + mg.player.intellect * 3 + mg.player.wisdom / 2.0f) * Math.sqrt(mg.player.level));
        mg.player.manaRegeneration = ((Math.round((5.0f + (mg.player.level / 10.0f) + (mg.player.wisdom * 5 + mg.player.intellect) * (1.0f - (mg.player.level / 65.0f))) * 100.0f) / 100.0f) / 60);
        mg.player.healthRegeneration = (float) (((0.05f + Math.sqrt(mg.player.endurance * 2 + mg.player.vitality)) * Math.sqrt(mg.player.level)) / 80);
        mg.player.playerMovementSpeed = (float) (4 + 0.2 * mg.player.agility);
        mg.player.critChance = Math.min((Math.round((5.0f + (mg.player.level / 10.0f) + (mg.player.luck * 2) * (1.0f - (mg.player.level / 63.0f))) * 100.0f) / 100.0f), 75);
        mg.player.speechSkill = Math.round((5.0f + (mg.player.level / 10.0f) + (1.5f * mg.player.charisma) * (1.0f - (mg.player.level / 64.0f))) * 100.0f) / 100.0f;
        mg.player.resistChance = Math.min((Math.round((5.0f + (mg.player.level / 10.0f) + (mg.player.endurance * 1.0f) * (1.0f - (mg.player.level / 64.0f))) * 100.0f) / 100.0f), 50);
        mg.player.carryWeight = Math.round((50.0f + (mg.player.level) + (mg.player.strength * 2.0f) * (1.0f - (mg.player.level / 100.0f))) * 100.0f) / 100.0f;
        mg.player.buffLengthMultiplier = Math.round(((mg.player.focus) * (1.0f - (mg.player.level / 80.0f))) * 100.0f) / 100.0f;
        mg.player.dotDamageMultiplier = Math.round(((mg.player.focus) * (1.0f - (mg.player.level / 70.0f))) * 100.0f) / 100.0f;
        mg.player.dotLengthMultiplier = mg.player.dotDamageMultiplier;
    }

    public void pickupDroppedItem() {
        try {
            for (DROP drop : mg.WORLD_DROPS) {
                if (new Rectangle((int) (worldX - 25), (int) (worldY - 14), mg.player.collisionBox.width + 10, mg.player.collisionBox.height + 17).contains(drop.worldPos)) {
                    for (UI_InventorySlot bagSlot : mg.inventP.bag_Slots) {
                        if (bagSlot.item == null && !bagSlot.grabbed) {
                            bagSlot.item = drop.item;
                            mg.WORLD_DROPS.remove(drop);
                            break;
                        }
                    }
                }
            }
        } catch (ConcurrentModificationException ignored) {
        }
    }

    public void getDurabilityDamageArmour() {
        for (UI_InventorySlot invSlot : mg.inventP.char_Slots) {
            if (invSlot.item != null && (!invSlot.item.type.contains("2") && !invSlot.item.type.contains("W") && !invSlot.item.type.contains("O"))) {
                if (Math.random() >= 0.9) {
                    invSlot.item.durability--;
                }
            }
        }
    }

    public void getDurabilityDamageWeapon() {
        for (UI_InventorySlot invSlot : mg.inventP.char_Slots) {
            if (invSlot.item != null && (invSlot.item.type.contains("2") || invSlot.item.type.contains("W") || invSlot.item.type.contains("O"))) {
                if (Math.random() >= 0.95) {
                    invSlot.item.durability--;
                }
            }
        }
    }

    @Override
    public void update() {
        movement();
        skills();
        if (mg.wControl.currentWorld == Map.GrassLands) {
            if (quadrantTimer >= 100) {
                dynamicSpawns();
                if (respawnsDone) {
                    quadrantTimer = 0;
                    respawnsDone = false;
                }
            }
            quadrantTimer++;
        }
    }

    public void getExperience(ENTITY entity) {
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

    public void setLevel(int experience) {
        this.experience = experience;
        while (experience >= levelUpExperience) {
            level++;
            updateEquippedItems();
            levelUpExperience = 0;
            for (int i = 1; i <= level; i++) {
                levelUpExperience += (i + i - 1) * (10 + i - 1);
            }
        }
    }

    private void dynamicSpawns() {
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

    @Override
    public void draw(GraphicsContext gc) {
        //gc.drawImage(entityImage1, screenX, screenY, 48, 48);
        gc.setStroke(Color.RED);
        gc.setLineWidth(1);
        gc.strokeRect(960 - 24, 540 - 24, 48, 48);
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
        mg.collisionChecker.checkPlayerAgainstTile(this);
        if (mg.inputH.leftPressed) {
            if (!collisionLeft && worldX > 0) {
                worldX -= movementSpeed;
            }
        }
        if (mg.inputH.upPressed) {
            if (!collisionUp && worldY > 0) {
                worldY -= movementSpeed;
            }
        }
        if (mg.inputH.downPressed) {
            if (!collisionDown && worldY < mg.wRender.worldSize.x * 48 - 48) {
                worldY += movementSpeed;
            }
        }

        if (mg.inputH.rightPressed) {
            if (!collisionRight && worldX < mg.wRender.worldSize.x * 48 - 48) {
                worldX += movementSpeed;
            }
        }
    }

    private void skills() {
        if (!mg.inventP.wholeBagWindow.contains(mg.inputH.lastMousePosition) && !mg.inventP.wholeCharWindow.contains(mg.inputH.lastMousePosition) && !mg.gameMap.mapMover.contains(mg.inputH.lastMousePosition)) {
            if (mg.inputH.mouse1Pressed && cooldownPrimary == 20) {
                mg.PRJControls.add(new PRJ_AutoShot(mg.inputH.lastMousePosition.x, mg.inputH.lastMousePosition.y));
                cooldownPrimary = 0;
                getDurabilityDamageWeapon();
            }
            if (mg.inputH.mouse2Pressed) {
                mg.sBar.skills[1].activate();
            }
        }
        if (mg.inputH.OnePressed) {
            mg.sBar.skills[0].activate();
        }
        if (mg.inputH.TwoPressed && mana >= 20 && cdLightning == 20) {
            mg.PRJControls.add(new PRJ_Lightning(mg.inputH.lastMousePosition.x, mg.inputH.lastMousePosition.y));
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
        if (cooldownPrimary < 20) {
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

    private Image setup(String imagePath) {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Entitys/player/" + imagePath)));
    }
}
