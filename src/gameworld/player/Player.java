package gameworld.player;

import gameworld.entities.ENTITY;
import gameworld.player.abilities.PRJ_AutoShot;
import gameworld.player.abilities.PRJ_Lightning;
import gameworld.world.objects.DROP;
import gameworld.world.objects.drops.DRP_Coin;
import gameworld.world.objects.drops.DRP_DroppedItem;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;
import main.system.enums.Map;
import main.system.ui.inventory.UI_InventorySlot;

import java.awt.Rectangle;
import java.util.Iterator;
import java.util.Objects;


public class Player extends ENTITY {
    private boolean isMoving;
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
    private int cooldownOneSecond;
    private int cooldownTwoSecond;
    public float experience;
    public int coins;
    public int spawnLevel;
    public float manaRegeneration = 0.02f;
    public float healthRegeneration = 0.002f;
    public float playerMovementSpeed;
    public int armour;
    private int cooldownPrimary;
    private int cdLightning;
    private int playerQuadrant;
    public int levelUpExperience = 10;
    private int quadrantTimer;
    private boolean respawnsDone, movingLeft;
    // PLAYER X AND Y ALWAYS +24
    public static float worldX, worldY;
    // screenX is half width -24
    public static int screenX, screenY;
    private Image idle1, idle2, idle3, idle4, idle5, idle6, idle7, idle8;
    private Image run1, run2, run3, run4, run5, run6, run7, run8;
    private Image runM1, runM2, runM3, runM4, runM5, runM6, runM7, runM8;


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
        this.collisionBox = new Rectangle(18, 8, 10, 32);
        this.level = 1;
        screenX = MainGame.SCREEN_WIDTH / 2 - 24;
        screenY = MainGame.SCREEN_HEIGHT / 2 - 24;
    }

    public void setPosition(int x, int y) {
        worldX = x;
        worldY = y;
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
        maxHealth = (int) ((10.0f + vitality * 1.5f + endurance / 2.0f) * Math.sqrt(Math.min(level, 50)));
        maxMana = (int) ((20.0f + intellect * 3 + wisdom) * Math.sqrt(Math.min(level, 50)));
        manaRegeneration = Math.round(1 + ((wisdom * 2 + intellect) / Math.sqrt(Math.max(10, level + 5))) / 60.0f * 100.0f) / 100.0f;
        healthRegeneration = Math.round(2 + ((endurance * 2 + vitality) / Math.sqrt(Math.max(10, level + 10))) / 110.0f * 100.0f) / 100.0f;
        playerMovementSpeed = Math.round(((4.0f + (agility * 0.4 / (float) level))) * 100.0f) / 100.0f;
        critChance = Math.min((Math.round(((5.0f + ((luck * 2) / Math.sqrt(Math.max(10, level))) * 100.0f) / 100.0f))), 75);
        speechSkill = Math.round((5.0f + (level / 10.0f) + (1.5f * charisma) * (1.0f - (level / 64.0f))) * 100.0f) / 100.0f;
        resistChance = Math.min((Math.round((5.0f + (level / 10.0f) + (endurance * 1.0f) * (1.0f - (level / 64.0f))) * 100.0f) / 100.0f), 50);
        carryWeight = Math.round((30.0f + (level) + (strength) * (1.0f - (level / 90.0f))) * 100.0f) / 100.0f;
        buffLengthMultiplier = Math.round(((focus * 1.5) * (1.0f - (level / 83.0f))) * 100.0f) / 100.0f;
        dotDamageMultiplier = Math.round(((focus * 1.5) * (1.0f - (level / 75.0f))) * 100.0f) / 100.0f;
        dotLengthMultiplier = (dotDamageMultiplier * 1.25f);
    }

    public void pickupDroppedItem() {
        synchronized (mg.WORLD_DROPS) {
            Iterator<DROP> iter = mg.WORLD_DROPS.iterator();
            while (iter.hasNext()) {
                DROP drop = iter.next();
                if (new Rectangle((int) worldX + mg.player.collisionBox.x, (int) worldY + mg.player.collisionBox.y, mg.player.collisionBox.width, mg.player.collisionBox.height).intersects(new Rectangle(drop.worldPos.x, drop.worldPos.y, drop.size, drop.size))) {
                    if (drop instanceof DRP_DroppedItem) {
                        for (UI_InventorySlot bagSlot : mg.inventP.bag_Slots) {
                            if (bagSlot.item == null && !bagSlot.grabbed) {
                                bagSlot.item = drop.item;
                                iter.remove();
                                break;
                            }
                        }
                    } else if (drop instanceof DRP_Coin) {
                        mg.player.coins += ((DRP_Coin) drop).amount;
                        iter.remove();
                    }
                }
            }
        }
    }


    public void getDurabilityDamageArmour() {
        for (UI_InventorySlot invSlot : mg.inventP.char_Slots) {
            if (invSlot.item != null && (!(invSlot.item.type == '2') && !(invSlot.item.type == 'W') && (!(invSlot.item.type == 'O')))) {
                if (Math.random() >= 0.9) {
                    invSlot.item.durability--;
                }
            }
        }
    }

    public void getDurabilityDamageWeapon() {
        for (UI_InventorySlot invSlot : mg.inventP.char_Slots) {
            if (invSlot.item != null && (!(invSlot.item.type == '2') && !(invSlot.item.type == 'W') && (!(invSlot.item.type == 'O')))) {
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
            mg.talentP.pointsToSpend++;
            updateEquippedItems();
            this.experience -= levelUpExperience;
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
            mg.talentP.pointsToSpend++;
            updateEquippedItems();
            this.experience -= levelUpExperience;
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
        if (isMoving) {
            if (movingLeft) {
                drawRunLeft(gc);
            } else {
                drawRun(gc);
            }
        } else {
            drawIdle(gc);
        }
        spriteCounter++;
    }

    public void checkPlayerIsMoving() {
        movingLeft = mg.inputH.leftPressed;
        isMoving = mg.inputH.upPressed || mg.inputH.downPressed || mg.inputH.leftPressed || mg.inputH.rightPressed;
    }

    private void drawIdle(GraphicsContext gc) {
        switch (spriteCounter % 144 / 18) {
            case 0 -> gc.drawImage(idle1, screenX, screenY);
            case 1 -> gc.drawImage(idle2, screenX, screenY);
            case 2 -> gc.drawImage(idle3, screenX, screenY);
            case 3 -> gc.drawImage(idle4, screenX, screenY);
            case 4 -> gc.drawImage(idle5, screenX, screenY);
            case 5 -> gc.drawImage(idle6, screenX, screenY);
            case 6 -> gc.drawImage(idle7, screenX, screenY);
            case 7 -> gc.drawImage(idle8, screenX, screenY);
        }
    }

    private void drawRun(GraphicsContext gc) {
        switch (spriteCounter % 136 / 17) {
            case 0 -> gc.drawImage(run1, screenX, screenY);
            case 1 -> gc.drawImage(run2, screenX, screenY);
            case 2 -> gc.drawImage(run3, screenX, screenY);
            case 3 -> gc.drawImage(run4, screenX, screenY);
            case 4 -> gc.drawImage(run5, screenX, screenY);
            case 5 -> gc.drawImage(run6, screenX, screenY);
            case 6 -> gc.drawImage(run7, screenX, screenY);
            case 7 -> gc.drawImage(run8, screenX, screenY);
        }
    }

    private void drawRunLeft(GraphicsContext gc) {
        switch (spriteCounter % 136 / 17) {
            case 0 -> gc.drawImage(runM1, screenX, screenY);
            case 1 -> gc.drawImage(runM2, screenX, screenY);
            case 2 -> gc.drawImage(runM3, screenX, screenY);
            case 3 -> gc.drawImage(runM4, screenX, screenY);
            case 4 -> gc.drawImage(runM5, screenX, screenY);
            case 5 -> gc.drawImage(runM6, screenX, screenY);
            case 6 -> gc.drawImage(runM7, screenX, screenY);
            case 7 -> gc.drawImage(runM8, screenX, screenY);
        }
    }

    private void movement() {
        collisionRight = false;
        collisionLeft = false;
        collisionDown = false;
        collisionUp = false;
        direction = "updownleftright";
        mg.collisionChecker.checkPlayerAgainstTile(this);
        mg.ob_control.checkCollisionPlayer();
        if (mg.inputH.leftPressed) {
            if (!collisionLeft && worldX > 0) {
                worldX -= playerMovementSpeed;
            }
        }
        if (mg.inputH.upPressed) {
            if (!collisionUp && worldY > 0) {
                worldY -= playerMovementSpeed;
            }
        }
        if (mg.inputH.downPressed) {
            if (!collisionDown && worldY < mg.wRender.worldSize.x * 48 - 48) {
                worldY += playerMovementSpeed;
            }
        }

        if (mg.inputH.rightPressed) {
            if (!collisionRight && worldX < mg.wRender.worldSize.x * 48 - 48) {
                worldX += playerMovementSpeed;
            }
        }
    }

    private void skills() {
        if (!mg.inventP.wholeBagWindow.contains(mg.inputH.lastMousePosition) && !mg.inventP.wholeCharWindow.contains(mg.inputH.lastMousePosition) && !mg.gameMap.mapMover.contains(mg.inputH.lastMousePosition)) {
            if (mg.inputH.mouse1Pressed && cooldownPrimary == 35) {
                mg.PROJECTILES.add(new PRJ_AutoShot(mg.inputH.lastMousePosition.x, mg.inputH.lastMousePosition.y));
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
            mg.PROJECTILES.add(new PRJ_Lightning(mg.inputH.lastMousePosition.x, mg.inputH.lastMousePosition.y));
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
        if (cooldownPrimary < 35) {
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

    private Image setupIdle(String imagePath) {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Entitys/player/idle/" + imagePath)));
    }

    private Image setupRun(String imagePath) {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Entitys/player/run/" + imagePath)));
    }

    private Image setupRunM(String imagePath) {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Entitys/player/runMirrored/" + imagePath)));
    }


    private void getPlayerImage() {
        idle1 = setupIdle("1.png");
        idle2 = setupIdle("2.png");
        idle3 = setupIdle("3.png");
        idle4 = setupIdle("4.png");
        idle5 = setupIdle("5.png");
        idle6 = setupIdle("6.png");
        idle7 = setupIdle("7.png");
        idle8 = setupIdle("8.png");
        run1 = setupRun("1.png");
        run2 = setupRun("2.png");
        run3 = setupRun("3.png");
        run4 = setupRun("4.png");
        run5 = setupRun("5.png");
        run6 = setupRun("6.png");
        run7 = setupRun("7.png");
        run8 = setupRun("8.png");
        runM1 = setupRunM("1.png");
        runM2 = setupRunM("2.png");
        runM3 = setupRunM("3.png");
        runM4 = setupRunM("4.png");
        runM5 = setupRunM("5.png");
        runM6 = setupRunM("6.png");
        runM7 = setupRunM("7.png");
        runM8 = setupRunM("8.png");
    }
}
