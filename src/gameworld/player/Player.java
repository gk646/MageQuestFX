package gameworld.player;

import gameworld.entities.ENTITY;
import gameworld.entities.damage.effects.Buff_Effect;
import gameworld.entities.damage.effects.Effect;
import gameworld.entities.loadinghelper.GeneralResourceLoader;
import gameworld.quest.Dialog;
import gameworld.world.MapQuadrant;
import gameworld.world.WorldController;
import gameworld.world.maps.Map;
import gameworld.world.objects.DROP;
import gameworld.world.objects.drops.DRP_Coin;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;
import main.system.enums.Zone;
import main.system.ui.Colors;
import main.system.ui.FonT;
import main.system.ui.inventory.UI_InventorySlot;
import main.system.ui.talentpanel.TalentNode;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.Objects;


public class Player extends ENTITY {
    public boolean levelup;
    public Dialog dialog = new Dialog();
    public Map map;
    public boolean isMoving;
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
    public static int effectsSizeRollable = 35;
    public static int effectsSizeTotal = 50;
    public static String[] effectNames = new String[effectsSizeTotal];
    public static float[] playerEffects;
    public float DMG_Arcane_Absolute, DMG_Dark__Absolute, buffLength_Absolute, DoT_Damage_Absolute, DoT_Length_Absolute, Mana_Percent, Health_Percent;
    public float CDR_Absolute, DMG_Poison_Percent, DMG_Fire_Percent, CritDMG_Absolute;
    public GeneralResourceLoader animation = new GeneralResourceLoader("ui/levelup");
    private int levelupCounter;
    public boolean drawDialog;

    /*
    1. DMG_Arcane_Absolute
    2. DMG_Dark__Absolute
    3. buffLength_Absolute
    4. DoT_Damage_Absolute
    5. DoT_Length_Absolute
    6. Mana_Percent
    7. Health_Percent
    8. INT__Absolute
    9. WIS__Absolute
    10. VIT_Absolute
    11. AGI__Absolute
    12. LUC__Absolute
    13. CHA__Absolute
    14. STR__Absolute
    15. END__Absolute
    16. FOC_Absolute
    17. CDR_Absolute
    18. DMG_Poison_Absolute
    19. DMG_Fire_Percent
    20. Armour_Percent
    21. CritChance_Absolute
    22. CritDMG_Absolute
    23. CarryWeight_Absolute
    24. HealthRegen_Percent
    25. Mana Regen Percent
    26. Man Cost Reduction
    27. magic find flat ( 10)
    28 ice magic damage +%


    40.weakness to ice
    41.weakness to arcane
    42.weakness to dark magic
    43.weakness to poison
    44.weakness to fire
    45.MovementSpeed Absolute (eg. 0.5)
    46.maximum mana flat (20)
    47.maximum health flat (10)
    48.shield ( over health shield) once given
     */
    public Player(MainGame mainGame) {
        this.mg = mainGame;
        playerEffects = effects;
        dialog.dialogLine = null;
        //-------VALUES-----------
        this.playerMovementSpeed = 4;
        animation.loadSound("levelup", "levelup");
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
        setupEffectNames();
    }

    private void movement() {
        collisionRight = false;
        collisionLeft = false;
        collisionDown = false;
        collisionUp = false;
        mg.collisionChecker.checkPlayerAgainstTile(this);
        //TODO rewrite collison
        int worldSize = mg.wRender.worldSize.x * 48;
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
            if (!collisionDown && worldY < worldSize) {
                worldY += playerMovementSpeed;
            }
        }
        if (mg.inputH.rightPressed) {
            if (!collisionRight && worldX < worldSize) {
                worldX += playerMovementSpeed;
            }
        }
    }

    private void skills() {
        Point mousePos = mg.inputH.lastMousePosition;
        if (mg.inputH.OnePressed) {
            getDurabilityDamageWeapon();
            mg.sBar.skills[0].activate();
        }
        if (mg.inputH.TwoPressed) {
            mg.sBar.skills[1].activate();
            getDurabilityDamageWeapon();
        }
        if (mg.inputH.ThreePressed) {
            getDurabilityDamageWeapon();
            mg.sBar.skills[2].activate();
        }
        if (mg.inputH.FourPressed) {
            getDurabilityDamageWeapon();
            mg.sBar.skills[3].activate();
        }
        if (mg.inputH.FivePressed) {
            getDurabilityDamageWeapon();
            mg.sBar.skills[4].activate();
        }
        if (mg.inputH.mouse1Pressed && mg.inventP.grabbedITEM == null && mg.inventP.activeTradingNPC == null) {
            if (!mg.sBar.wholeSkillBar.contains(mousePos) && !mg.inventP.wholeBagWindow.contains(mousePos) && !mg.inventP.wholeCharWindow.contains(mousePos) && !mg.showMap && !mg.showTalents) {
                getDurabilityDamageWeapon();
                mg.sBar.skills[5].activate();
            }
        }
        if (mg.inputH.mouse2Pressed && mg.inventP.activeTradingNPC == null) {
            if (!mg.sBar.wholeSkillBar.contains(mousePos) && !mg.inventP.wholeBagWindow.contains(mousePos) && !mg.inventP.wholeCharWindow.contains(mousePos) && !mg.showMap && !mg.showTalents) {
                getDurabilityDamageWeapon();
                mg.sBar.skills[6].activate();
            }
        }
        if (mg.inputH.q_pressed) {
            getDurabilityDamageWeapon();
            mg.sBar.skills[7].activate();
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
    }

    @Override
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
        armour = 0;
        for (int i = 1; i < effectsSizeTotal; i++) {
            effects[i] = 0;
        }
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
                armour += invSlot.item.armour;

                //EFFECTS
                for (int i = 1; i < effectsSizeRollable; i++) {
                    effects[i] += invSlot.item.effects[i];
                }
            }
        }
        for (Effect effect : BuffsDebuffEffects) {
            if (effect instanceof Buff_Effect buffEffect) {
                effects[buffEffect.effectIndexAffected] += buffEffect.amount;
            }
        }
        for (TalentNode node : mg.talentP.talent_Nodes) {
            if (node != null && node.talent != null && node.activated) {
                for (int i = 1; i < effectsSizeTotal; i++) {
                    effects[i] += node.talent.effects[i];
                }
                intellect += node.talent.intellect;
                vitality += node.talent.vitality;
                agility += node.talent.agility;
                wisdom += node.talent.wisdom;
                luck += node.talent.luck;
                charisma += node.talent.charisma;
                endurance += node.talent.endurance;
                strength += node.talent.strength;
                focus += node.talent.focus;
                armour += node.talent.armour;
            }
        }

        intellect = (int) (intellect + (intellect / 100.0f) * effects[8]);
        wisdom = (int) (wisdom + (wisdom / 100.0f) * effects[9]);
        vitality = (int) (vitality + (vitality / 100.0f) * effects[10]);
        agility = (int) (agility + (agility / 100.0f) * effects[11]);
        luck = (int) (luck + (luck / 100.0f) * effects[12]);
        charisma = (int) (charisma + (charisma / 100.0f) * effects[13]);
        strength = (int) (strength + (strength / 100.0f) * effects[14]);
        endurance = (int) (endurance + (endurance / 100.0f) * effects[15]);
        focus = (int) (focus + (focus / 100.0f) * effects[16]);
        armour = (int) (armour + (armour / 100.0f) * effects[20]);

        maxHealth = (int) ((10.0f + vitality * 1.5f + endurance / 2.0f) * Math.sqrt(Math.min(level, 50)));
        maxMana = (int) ((20.0f + intellect * 3 + wisdom) * Math.sqrt(Math.min(level, 50)));
        manaRegeneration = Math.round(1 + ((wisdom * 2 + intellect) / Math.sqrt(Math.max(10, level + 5))) / 60.0f * 100.0f) / 100.0f;
        healthRegeneration = Math.round(2 + ((endurance * 2 + vitality) / Math.sqrt(Math.max(10, level + 10))) / 110.0f * 100.0f) / 100.0f;
        playerMovementSpeed = Math.round(((4.0f + (agility * 0.4 / (float) Math.max(10, level)))) * 100.0f) / 100.0f;
        critChance = Math.min((Math.round(((5.0f + ((luck * 2) / Math.sqrt(Math.max(10, level))) * 100.0f) / 100.0f))), 75);
        speechSkill = Math.round((5.0f + (level / 10.0f) + (1.5f * charisma) * (1.0f - (level / 64.0f))) * 100.0f) / 100.0f;
        resistChance = Math.min((Math.round((5.0f + (level / 10.0f) + (endurance * 1.0f) * (1.0f - (level / 64.0f))) * 100.0f) / 100.0f), 50);
        carryWeight = Math.round((30.0f + (level) + (strength) * (1.0f - (level / 90.0f))) * 100.0f) / 100.0f;
        buffLengthMultiplier = (float) ((focus * 1.5) * (1.0f - (level / 83.0f)));
        dotDamageMultiplier = Math.round(((focus * 1.5) * (1.0f - (level / 75.0f))) * 100.0f) / 100.0f;
        dotLengthMultiplier = (dotDamageMultiplier * 1.25f);


        DMG_Arcane_Absolute = effects[1];
        DMG_Dark__Absolute = effects[2];
        effects[3] += buffLengthMultiplier;
        buffLengthMultiplier = effects[3];
        effects[4] += dotDamageMultiplier;
        dotDamageMultiplier = effects[4];
        effects[5] += dotLengthMultiplier;
        dotLengthMultiplier = effects[5];
        maxMana = (int) (maxMana + (maxMana / 100.0f) * effects[6]);
        maxHealth = (int) (maxHealth + (maxHealth / 100.0f) * effects[7]);
        CDR_Absolute = effects[17];
        DMG_Poison_Percent = effects[18];
        DMG_Fire_Percent = effects[19];
        effects[21] += critChance;
        critChance = effects[21];
        effects[22] += 50;
        effects[23] += carryWeight;
        carryWeight = effects[23];
        healthRegeneration = healthRegeneration + (healthRegeneration / 100.0f) * effects[24];
        manaRegeneration = manaRegeneration + (manaRegeneration / 100.0f) * effects[25];


        playerMovementSpeed += effects[45];
        maxMana += effects[46];
        maxMana = 2000;
    }

    public void setPosition(int x, int y) {
        worldX = x * 48;
        worldY = y * 48;
    }


    public void pickupDroppedItem() {
        int x = (int) worldX;
        int y = (int) worldY;
        synchronized (mg.WORLD_DROPS) {
            Iterator<DROP> iter = mg.WORLD_DROPS.iterator();
            while (iter.hasNext()) {
                DROP drop = iter.next();
                if (Math.abs(drop.worldPos.x - x) + Math.abs(drop.worldPos.y - y) < 200) {
                    if (new Rectangle(x + mg.player.collisionBox.x, y + mg.player.collisionBox.y, mg.player.collisionBox.width, mg.player.collisionBox.height).intersects(new Rectangle(drop.worldPos.x, drop.worldPos.y, drop.size, drop.size))) {
                        if (drop instanceof DRP_Coin) {
                            mg.player.coins += ((DRP_Coin) drop).amount;
                            iter.remove();
                        } else if (!drop.blockPickup) {
                            for (UI_InventorySlot bagSlot : mg.inventP.bag_Slots) {
                                if (bagSlot.item == null && !bagSlot.grabbed) {
                                    bagSlot.item = drop.item;
                                    iter.remove();
                                    break;
                                }
                            }
                        }
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
        tickEffects();
        if (WorldController.currentWorld == Zone.GrassLands) {
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

    protected void tickEffects() {
        Iterator<Effect> iter = BuffsDebuffEffects.iterator();
        while (iter.hasNext()) {
            Effect effect = iter.next();
            effect.tick(this);
            if (effect.rest_duration <= 0) {
                iter.remove();
                if (effect instanceof Buff_Effect) {
                    updateEquippedItems();
                }
            }
        }
    }

    public void getExperience(ENTITY entity) {
        experience += entity.level;
        if (experience >= levelUpExperience) {
            level++;
            levelup = true;
            animation.playSound(0);
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
        MapQuadrant[] mapQuadrants = map.mapQuadrants;
        int[][] mapData = map.mapDataBackGround;
        getPlayerQuadrant(mapQuadrants);
        respawnCloseQuadrants(mapData, mapQuadrants);
    }

    private void getPlayerQuadrant(MapQuadrant[] mapQuadrants) {
        for (int i = 99; i >= 0; i--) {
            if (worldX / mg.tileSize > mapQuadrants[i].startTileX && worldY / mg.tileSize > mapQuadrants[i].startTileY && worldX / mg.tileSize < mapQuadrants[i].startTileX + 50 && worldY / mg.tileSize < mapQuadrants[i].startTileY + 50) {
                playerQuadrant = i;
                break;
            }
        }
    }


    private void respawnCloseQuadrants(int[][] mapData, MapQuadrant[] mapQuadrants) {
        mapQuadrants[playerQuadrant].spawnEnemies(mapData);
        if (quadrantTimer < 210) {
            mapQuadrants[Math.min(playerQuadrant + 1, 99)].spawnEnemies(mapData);
        } else if (quadrantTimer < 220) {
            mapQuadrants[Math.max(playerQuadrant - 1, 0)].spawnEnemies(mapData);
        } else if (quadrantTimer < 230) {
            mapQuadrants[Math.min(playerQuadrant + 9, 99)].spawnEnemies(mapData);
        } else if (quadrantTimer < 240) {
            mapQuadrants[Math.min(playerQuadrant + 10, 99)].spawnEnemies(mapData);
        } else if (quadrantTimer < 250) {
            mapQuadrants[Math.min(playerQuadrant + 11, 99)].spawnEnemies(mapData);
        } else if (quadrantTimer < 260) {
            mapQuadrants[Math.max(playerQuadrant - 9, 0)].spawnEnemies(mapData);
        } else if (quadrantTimer < 270) {
            mapQuadrants[Math.max(playerQuadrant - 10, 0)].spawnEnemies(mapData);
        } else if (quadrantTimer < 280) {
            mapQuadrants[Math.max(playerQuadrant - 11, 0)].spawnEnemies(mapData);
            respawnsDone = true;
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (dialog.dialogLine != null) {
            drawDialog = true;
            if (dialog.dialogRenderCounter >= 2_000) {
                dialog.dialogRenderCounter++;
                if (dialog.dialogRenderCounter >= 2_500) {
                    drawDialog = false;
                }
            }
        }
        if (isMoving) {
            if (movingLeft) {
                drawRunLeft(gc);
            } else {
                drawRun(gc);
            }
        } else {
            drawIdle(gc);
        }
        if (levelup) {
            drawLevelUp(gc);
            levelupCounter++;
            if (levelupCounter >= 1_440) {
                levelup = false;
                levelupCounter = 0;
            }
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

    private void drawLevelUp(GraphicsContext gc) {
        gc.setFont(FonT.minecraftBold30);
        gc.setFill(Colors.darkBackground);
        gc.fillText("LEVEL UP", screenX - 60, screenY - 150);
        switch (Math.abs(spriteCounter / 12 % (2 * 6 - 2) - 6 + 1)) {
            case 0 -> gc.drawImage(animation.images1.get(0), screenX - 118 + 24, screenY - 150);
            case 1 -> gc.drawImage(animation.images1.get(1), screenX - 118 + 24, screenY - 150);
            case 2 -> gc.drawImage(animation.images1.get(2), screenX - 118 + 24, screenY - 150);
            case 3 -> gc.drawImage(animation.images1.get(3), screenX - 118 + 24, screenY - 150);
            case 4 -> gc.drawImage(animation.images1.get(4), screenX - 118 + 24, screenY - 150);
            case 5 -> gc.drawImage(animation.images1.get(5), screenX - 118 + 24, screenY - 150);
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

    private void setupEffectNames() {
        effectNames = new String[]{
                "filler",
                "Arcane Damage: +",
                "Dark Magic Damage: +",
                "Buff Length: +",
                "DoT Damage: +",
                "DoT Length: +",
                "Mana: +",
                "Health: +",
                "INT: +",
                "WIS: +",
                "VIT: +",
                "AGI: +",
                "LUC: +",
                "CHA: +",
                "STR: +",
                "END: +",
                "FOC: +",
                "Cooldown Reduction: +",
                "Poison Damage: +",
                "Fire Damage: +",
                "Armour: +",
                "Crit Chance: +",
                "CritDamage: +",
                "Carry Weight: +",
                "Health Regeneration: +",
                "Mana Regeneration: +",
                "Mana Cost Reduction: +",
                "Magic Find: +",
                "Ice Damage: +",
        };
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
