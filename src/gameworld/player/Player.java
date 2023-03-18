package gameworld.player;

import gameworld.entities.BOSS;
import gameworld.entities.ENTITY;
import gameworld.entities.damage.effects.Effect;
import gameworld.entities.damage.effects.EffectType;
import gameworld.entities.damage.effects.arraybased.Effect_ArrayBased;
import gameworld.entities.loadinghelper.GeneralResourceLoader;
import gameworld.entities.loadinghelper.ResourceLoaderEntity;
import gameworld.quest.Dialog;
import gameworld.world.maps.Map;
import gameworld.world.objects.DROP;
import gameworld.world.objects.drops.DRP_Coin;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.Colors;
import main.system.ui.FonT;
import main.system.ui.inventory.UI_InventorySlot;
import main.system.ui.talentpanel.TalentNode;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Iterator;


public class Player extends ENTITY {
    public boolean levelup;

    public static final int effectsSizeRollable = 35;
    public Map map;
    public boolean isMoving;
    public float manaBarrier;
    public int maxMana;
    public static boolean interactingWithNPC;
    private float mana;
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
    public float experience;
    public int coins;
    public int spawnLevel;
    public float manaRegeneration = 0.02f;
    public float healthRegeneration = 0.002f;
    public float playerMovementSpeed;
    public int armour;
    public int levelUpExperience = 10;
    public float weaponDamageUpper, weaponDamageLower;
    // PLAYER X AND Y ALWAYS +24
    public static float worldX, worldY;
    // screenX is half width -24
    public static int screenX, screenY;
    public static final int effectsSizeTotal = 50;
    public final Dialog dialog = new Dialog();
    public static String[] effectNames = new String[effectsSizeTotal];
    public static float[] playerEffects;
    public float DMG_Arcane_Absolute, DMG_Dark__Absolute, buffLength_Absolute, DoT_Damage_Absolute, DoT_Length_Absolute, Mana_Percent, Health_Percent;
    public float CDR_Absolute, DMG_Poison_Percent, DMG_Fire_Percent, CritDMG_Absolute;
    public final GeneralResourceLoader animation = new GeneralResourceLoader("ui/levelup");
    public final ResourceLoaderEntity resource = new ResourceLoaderEntity("player");
    private int levelupCounter;
    public boolean drawDialog;
    public boolean attack1;
    public boolean attack2;
    public boolean attack3;
    public boolean questCompleted;
    public boolean movingLeft;
    public String lastQuest;
    private final Rectangle player = new Rectangle(40, 40);
    public int questCompleteCounter;

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
        this.playerMovementSpeed = 3;
        animation.loadSound("levelup", "levelup");
        this.movementSpeed = 3;
        this.maxHealth = 10;
        this.maxMana = 20;
        this.health = maxHealth;
        this.mana = maxMana;
        this.entityHeight = 48;
        this.entityWidth = 48;
        resource.load();
        manaBarrier = maxMana;
        worldX = 24;
        worldY = 24;
        direction = "";
        this.collisionBox = new Rectangle(18, 8, 10, 32);
        this.level = 1;
        screenX = MainGame.SCREEN_WIDTH / 2 - 24;
        screenY = MainGame.SCREEN_HEIGHT / 2 - 24;
        setupEffectNames();
    }

    private void movement() {
        int worldSize = (mg.wRender.worldSize.x - 1) * 48;
        if (!stunned && mg.inputH.leftPressed && worldX > 0 + playerMovementSpeed && !mg.collisionChecker.checkPlayerLeft()) {
            worldX -= playerMovementSpeed;
        }
        if (!stunned && mg.inputH.upPressed && worldY > 0 + playerMovementSpeed && !mg.collisionChecker.checkPlayerUp()) {
            worldY -= playerMovementSpeed;
        }
        if (!stunned && mg.inputH.downPressed && worldY < worldSize && !mg.collisionChecker.checkPlayerDown()) {
            worldY += playerMovementSpeed;
        }
        if (!stunned && mg.inputH.rightPressed && worldX < worldSize && !mg.collisionChecker.checkPlayerRight()) {
            worldX += playerMovementSpeed;
        }
    }

    public void playCastAnimation(int index) {
        if (!attack1 && !attack2 && !attack3) {
            spriteCounter = 0;
            switch (index) {
                case 1 -> attack1 = true;
                case 2 -> attack2 = true;
                case 3 -> attack3 = true;
            }
        }
    }

    private void skills() {
        if (mg.inputH.OnePressed && !stunned) {
            getDurabilityDamageWeapon();
            mg.sBar.skills[0].activate();
        }
        if (mg.inputH.TwoPressed && !stunned) {
            mg.sBar.skills[1].activate();
            getDurabilityDamageWeapon();
        }
        if (mg.inputH.ThreePressed && !stunned) {
            getDurabilityDamageWeapon();
            mg.sBar.skills[2].activate();
        }
        if (mg.inputH.FourPressed && !stunned) {
            getDurabilityDamageWeapon();
            mg.sBar.skills[3].activate();
        }
        if (mg.inputH.FivePressed && !stunned) {
            getDurabilityDamageWeapon();
            mg.sBar.skills[4].activate();
        }
        if (mg.inputH.mouse1Pressed) {
            Point mousePos = mg.inputH.lastMousePosition;
            if (mg.inventP.grabbedITEM == null && mg.inventP.activeTradingNPC == null && mg.skillPanel.draggedSKILL == null && !stunned && !mg.qPanel.wholeJournalWindow.contains(mousePos) && !mg.sBar.wholeSkillBar.contains(mousePos) && !mg.inventP.wholeBagWindow.contains(mousePos) && !mg.skillPanel.wholeSkillWindow.contains(mousePos) && !mg.inventP.wholeCharWindow.contains(mousePos) && !mg.showMap && !mg.showTalents) {
                getDurabilityDamageWeapon();
                mg.sBar.skills[5].activate();
            }
        }
        if (mg.inputH.mouse2Pressed && mg.inventP.activeTradingNPC == null && !stunned) {
            Point mousePos = mg.inputH.lastMousePosition;
            if (!mg.sBar.wholeSkillBar.contains(mousePos) && !mg.inventP.wholeBagWindow.contains(mousePos) && !mg.inventP.wholeCharWindow.contains(mousePos) && !mg.showMap && !mg.showTalents) {
                getDurabilityDamageWeapon();
                mg.sBar.skills[6].activate();
            }
        }
        if (mg.inputH.q_pressed && !interactingWithNPC && !stunned) {
            mg.sBar.skills[7].activate();
        }
        if (mana < manaBarrier) {
            mana += manaRegeneration;
        } else if (mana > manaBarrier) {
            mana = manaBarrier;
        }
        if (health < maxHealth) {
            health += healthRegeneration;
        } else if (health > maxHealth) {
            health = maxHealth;
        }
        interactingWithNPC = false;
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
        if (mg.inventP.char_Slots[8].item != null && mg.inventP.char_Slots[8].item.type == '2') {
            weaponDamageLower = (float) (mg.inventP.char_Slots[8].item.weapon_damage * 0.94);
            weaponDamageUpper = (float) (mg.inventP.char_Slots[8].item.weapon_damage * 1.05);
        } else if (mg.inventP.char_Slots[8].item != null && mg.inventP.char_Slots[8].item.type == 'W') {
            weaponDamageUpper = (float) (mg.inventP.char_Slots[8].item.weapon_damage * 1.05);
            weaponDamageLower = (float) (mg.inventP.char_Slots[8].item.weapon_damage * 0.94);
            if (mg.inventP.char_Slots[9].item != null) {
                weaponDamageUpper += (float) (mg.inventP.char_Slots[9].item.weapon_damage * 1.05);
                weaponDamageLower += (float) (mg.inventP.char_Slots[9].item.weapon_damage * 0.94);
            }
        } else if (mg.inventP.char_Slots[9].item != null) {
            weaponDamageUpper = (float) (mg.inventP.char_Slots[9].item.weapon_damage * 1.05);
            weaponDamageLower = (float) (mg.inventP.char_Slots[9].item.weapon_damage * 0.94);
        } else {
            weaponDamageUpper = 0.755f;
            weaponDamageLower = 0.75f;
        }

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
            effects[effect.indexAffected] += effect.amount;
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
        playerMovementSpeed = Math.round(((3.0f + (agility * 0.4 / (float) Math.max(10, level)))) * 100.0f) / 100.0f;
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
        effects[45] = playerMovementSpeed;
        maxMana += effects[46];


        manaBarrier = maxMana;
    }

    public void setPosition(int x, int y) {
        worldX = x * 48;
        worldY = y * 48;
    }


    public void pickupDroppedItem() {
        player.x = (int) worldX;
        player.y = (int) worldY;
        synchronized (mg.WORLD_DROPS) {
            Iterator<DROP> iter = mg.WORLD_DROPS.iterator();
            while (iter.hasNext()) {
                DROP drop = iter.next();
                if (Math.abs(drop.worldPos.x - worldX) + Math.abs(drop.worldPos.y - worldY) < 200) {
                    if (player.intersects(new Rectangle(drop.worldPos.x, drop.worldPos.y, drop.size, drop.size))) {
                        if (drop instanceof DRP_Coin) {
                            mg.player.coins += ((DRP_Coin) drop).amount;
                            mg.sound.playEffectSound(9);
                            iter.remove();
                        } else if (!drop.blockPickup) {
                            for (UI_InventorySlot bagSlot : mg.inventP.bag_Slots) {
                                if (bagSlot.item == null && !bagSlot.grabbed) {
                                    bagSlot.item = drop.item;
                                    mg.sBar.showNoticeBag = true;
                                    mg.sound.playEffectSound(9);
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

        /*
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

         */
    }

    protected void tickEffects() {
        Iterator<Effect> iter = BuffsDebuffEffects.iterator();
        while (iter.hasNext()) {
            Effect effect = iter.next();
            effect.tick(this);
            if (effect.rest_duration <= 0) {
                iter.remove();
                if (effect instanceof Effect_ArrayBased) {
                    updateEquippedItems();
                }
            }
        }
    }

    public void getExperience(ENTITY entity) {
        if (entity instanceof BOSS) {
            experience += entity.level * 10;
        } else {
            experience += entity.level;
        }
        if (experience >= levelUpExperience) {
            level++;
            levelup = true;
            mg.sBar.showNoticeChar = true;
            animation.playSound(0);
            mg.talentP.pointsToSpend++;
            updateEquippedItems();
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
            levelUpExperience = 0;
            for (int i = 1; i <= level; i++) {
                levelUpExperience += (i + i - 1) * (10 + i - 1);
            }
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
        if (levelup) {
            drawLevelUp(gc);
            levelupCounter++;
            if (levelupCounter >= 1_440) {
                levelup = false;
                levelupCounter = 0;
            }
        }
        if (questCompleted) {
            drawQuestComplete(gc);
            questCompleteCounter++;
            if (questCompleteCounter >= 480) {
                questCompleted = false;
                questCompleteCounter = 0;
            }
        }
        if (attack1) {
            drawAttack1(gc);
        } else if (attack2) {
            drawAttack2(gc);
        } else if (attack3) {
            drawAttack3(gc);
        } else {
            if (isMoving) {
                if (movingLeft) {
                    drawRunLeft(gc);
                } else {
                    drawRun(gc);
                }
            } else {
                drawIdle(gc);
            }
        }

        spriteCounter++;
    }

    @Override
    public void drawBuffsAndDeBuffs(GraphicsContext gc) {
        if (BuffsDebuffEffects.size() > 0) {
            int x = 600;
            int y = 923;
            gc.setLineWidth(2);
            for (Effect effect : BuffsDebuffEffects) {
                if (effect.effectType == EffectType.BUFF && effect.rest_duration < 50000) {
                    effect.draw(gc, x, y);
                    gc.setStroke(Colors.map_green);
                    gc.strokeRoundRect(x, y, 32, 32, 6, 6);
                    x += 40;
                }
            }
            x += 32;
            for (Effect effect : BuffsDebuffEffects) {
                if (effect.effectType == EffectType.DEBUFF && effect.rest_duration < 50000) {
                    gc.setStroke(Colors.Red);
                    effect.draw(gc, x, y);
                    gc.strokeRoundRect(x, y, 32, 32, 6, 6);
                    x += 40;
                }
            }
        }
    }


    public void checkPlayerIsMoving() {
        movingLeft = mg.inputH.leftPressed;
        isMoving = mg.inputH.upPressed || mg.inputH.downPressed || mg.inputH.leftPressed || mg.inputH.rightPressed;
        if (isMoving) {
            attack3 = false;
            attack2 = false;
            attack1 = false;
        }
    }

    private void drawIdle(GraphicsContext gc) {
        switch (spriteCounter % 200 / 25) {
            case 0 -> gc.drawImage(resource.idle.get(0), screenX - 24, screenY - 48);
            case 1 -> gc.drawImage(resource.idle.get(1), screenX - 24, screenY - 48);
            case 2 -> gc.drawImage(resource.idle.get(2), screenX - 24, screenY - 48);
            case 3 -> gc.drawImage(resource.idle.get(3), screenX - 24, screenY - 48);
            case 4 -> gc.drawImage(resource.idle.get(4), screenX - 24, screenY - 48);
            case 5 -> gc.drawImage(resource.idle.get(5), screenX - 24, screenY - 48);
            case 6 -> gc.drawImage(resource.idle.get(6), screenX - 24, screenY - 48);
            case 7 -> gc.drawImage(resource.idle.get(7), screenX - 24, screenY - 48);
        }
    }

    private void drawRun(GraphicsContext gc) {
        switch (spriteCounter % 136 / 17) {
            case 0 -> gc.drawImage(resource.run.get(0), screenX - 24, screenY - 48);
            case 1 -> gc.drawImage(resource.run.get(1), screenX - 24, screenY - 48);
            case 2 -> gc.drawImage(resource.run.get(2), screenX - 24, screenY - 48);
            case 3 -> gc.drawImage(resource.run.get(3), screenX - 24, screenY - 48);
            case 4 -> gc.drawImage(resource.run.get(4), screenX - 24, screenY - 48);
            case 5 -> gc.drawImage(resource.run.get(5), screenX - 24, screenY - 48);
            case 6 -> gc.drawImage(resource.run.get(6), screenX - 24, screenY - 48);
            case 7 -> gc.drawImage(resource.run.get(7), screenX - 24, screenY - 48);
        }
    }

    private void drawRunLeft(GraphicsContext gc) {
        switch (spriteCounter % 136 / 17) {
            case 0 -> gc.drawImage(resource.runMirror.get(7), screenX, screenY - 48);
            case 1 -> gc.drawImage(resource.runMirror.get(6), screenX, screenY - 48);
            case 2 -> gc.drawImage(resource.runMirror.get(5), screenX, screenY - 48);
            case 3 -> gc.drawImage(resource.runMirror.get(4), screenX, screenY - 48);
            case 4 -> gc.drawImage(resource.runMirror.get(3), screenX, screenY - 48);
            case 5 -> gc.drawImage(resource.runMirror.get(2), screenX, screenY - 48);
            case 6 -> gc.drawImage(resource.runMirror.get(1), screenX, screenY - 48);
            case 7 -> gc.drawImage(resource.runMirror.get(0), screenX, screenY - 48);
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

    private void drawAttack1(GraphicsContext gc) {
        switch (spriteCounter % 80 / 10) {
            case 0 -> gc.drawImage(resource.attack1.get(0), screenX - 24, screenY - 48);
            case 1 -> gc.drawImage(resource.attack1.get(1), screenX - 24, screenY - 48);
            case 2 -> gc.drawImage(resource.attack1.get(2), screenX - 24, screenY - 48);
            case 3 -> gc.drawImage(resource.attack1.get(3), screenX - 24, screenY - 48);
            case 4 -> gc.drawImage(resource.attack1.get(4), screenX - 24, screenY - 48);
            case 5 -> gc.drawImage(resource.attack1.get(5), screenX - 24, screenY - 48);
            case 6 -> gc.drawImage(resource.attack1.get(6), screenX - 24, screenY - 48);
            case 7 -> attack1 = false;
        }
    }

    private void drawAttack2(GraphicsContext gc) {
        switch (spriteCounter % 100 / 10) {
            case 0 -> gc.drawImage(resource.attack2.get(0), screenX - 24, screenY - 48);
            case 1 -> gc.drawImage(resource.attack2.get(1), screenX - 24, screenY - 48);
            case 2 -> gc.drawImage(resource.attack2.get(2), screenX - 24, screenY - 48);
            case 3 -> gc.drawImage(resource.attack2.get(3), screenX - 24, screenY - 48);
            case 4 -> gc.drawImage(resource.attack2.get(4), screenX - 24, screenY - 48);
            case 5 -> gc.drawImage(resource.attack2.get(5), screenX - 24, screenY - 48);
            case 6 -> gc.drawImage(resource.attack2.get(6), screenX - 24, screenY - 48);
            case 7 -> gc.drawImage(resource.attack2.get(7), screenX - 24, screenY - 48);
            case 8 -> gc.drawImage(resource.attack2.get(8), screenX - 24, screenY - 48);
            case 9 -> attack2 = false;
        }
    }

    private void drawAttack3(GraphicsContext gc) {
        switch (spriteCounter % 170 / 10) {
            case 0 -> gc.drawImage(resource.attack3.get(0), screenX - 24, screenY - 48);
            case 1 -> gc.drawImage(resource.attack3.get(1), screenX - 24, screenY - 48);
            case 2 -> gc.drawImage(resource.attack3.get(2), screenX - 24, screenY - 48);
            case 3 -> gc.drawImage(resource.attack3.get(3), screenX - 24, screenY - 48);
            case 4 -> gc.drawImage(resource.attack3.get(4), screenX - 24, screenY - 48);
            case 5 -> gc.drawImage(resource.attack3.get(5), screenX - 24, screenY - 48);
            case 6 -> gc.drawImage(resource.attack3.get(6), screenX - 24, screenY - 48);
            case 7 -> gc.drawImage(resource.attack3.get(7), screenX - 24, screenY - 48);
            case 8 -> gc.drawImage(resource.attack3.get(8), screenX - 24, screenY - 48);
            case 9 -> gc.drawImage(resource.attack3.get(9), screenX - 24, screenY - 48);
            case 10 -> gc.drawImage(resource.attack3.get(10), screenX - 24, screenY - 48);
            case 11 -> gc.drawImage(resource.attack3.get(11), screenX - 24, screenY - 48);
            case 12 -> gc.drawImage(resource.attack3.get(12), screenX - 24, screenY - 48);
            case 13 -> gc.drawImage(resource.attack3.get(13), screenX - 24, screenY - 48);
            case 14 -> gc.drawImage(resource.attack3.get(14), screenX - 24, screenY - 48);
            case 15 -> gc.drawImage(resource.attack3.get(15), screenX - 24, screenY - 48);
            case 16 -> attack3 = false;
        }
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

    public float getMana() {
        return mana;
    }

    public void setMana(float mana) {
        this.mana = mana;
    }

    public void loseMana(float baseCost) {
        baseCost *= Math.sqrt(level);
        mana -= baseCost - ((baseCost / 100.0f) * effects[26]);
    }


    public float getManaCost(int baseCost) {
        baseCost *= Math.sqrt(level);
        return baseCost - ((baseCost / 100.0f) * effects[26]);
    }

    private void drawQuestComplete(GraphicsContext gc) {
        gc.setFont(FonT.antParty30);
        gc.setFill(Colors.questNameBeige);
        String text = "Quest Completed:";
        mg.ui.drawCenteredText(gc, text, MainGame.SCREEN_HEIGHT / 2.0f - 420);
        gc.setFont(FonT.antParty20);
        text = lastQuest;
        mg.ui.drawCenteredText(gc, text, MainGame.SCREEN_HEIGHT / 2.0f - 380);
    }

    public void drawCutscene(GraphicsContext gc, int centerX, int centerY) {
        switch (spriteCounter % 200 / 25) {
            case 0 -> gc.drawImage(resource.idle.get(0), Player.worldX - centerX + 960 - 24, Player.worldY - centerY + 540 + 48);
            case 1 -> gc.drawImage(resource.idle.get(1), Player.worldX - centerX + 960 - 24, Player.worldY - centerY + 540 + 48);
            case 2 -> gc.drawImage(resource.idle.get(2), Player.worldX - centerX + 960 - 24, Player.worldY - centerY + 540 + 48);
            case 3 -> gc.drawImage(resource.idle.get(3), Player.worldX - centerX + 960 - 24, Player.worldY - centerY + 540 + 48);
            case 4 -> gc.drawImage(resource.idle.get(4), Player.worldX - centerX + 960 - 24, Player.worldY - centerY + 540 + 48);
            case 5 -> gc.drawImage(resource.idle.get(5), Player.worldX - centerX + 960 - 24, Player.worldY - centerY + 540 + 48);
            case 6 -> gc.drawImage(resource.idle.get(6), Player.worldX - centerX + 960 - 24, Player.worldY - centerY + 540 + 48);
            case 7 -> gc.drawImage(resource.idle.get(7), Player.worldX - centerX + 960 - 24, Player.worldY - centerY + 540 + 48);
        }
        spriteCounter++;
    }
}
