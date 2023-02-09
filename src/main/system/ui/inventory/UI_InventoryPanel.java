package main.system.ui.inventory;

import gameworld.player.Player;
import gameworld.world.objects.drops.DRP_DroppedItem;
import gameworld.world.objects.items.ITEM;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.Colors;
import main.system.ui.FonT;
import main.system.ui.talentpane.TalentNode;

import java.awt.Point;
import java.awt.Rectangle;
import java.text.DecimalFormat;

public class UI_InventoryPanel {
    private static final int SLOT_SIZE = 45;
    public final Rectangle combatStatsHitBox;
    private static final int BAG_SLOTS = 15;
    private static final int CHAR_SLOTS = 10;
    public final UI_InventorySlot[] char_Slots;
    public final UI_InventorySlot[] bag_Slots;
    public final Rectangle wholeCharWindow;
    public final Rectangle wholeBagWindow;
    private final MainGame mg;
    private final DecimalFormat df = new DecimalFormat("#.##");
    private final Rectangle bagPanelCloser;
    private final Rectangle charPanelMover;
    private final Rectangle bagPanelMover;
    public final Rectangle effectsHitBox;
    public boolean showCombatStats = true;
    private final Point previousMousePosition = new Point(300, 300);
    private int charPanelX = 300, grabbedIndexChar = -1, grabbedIndexBag = -1;
    private int charPanelY = 300;
    private final Point lastCharPosition = new Point(charPanelX, charPanelY);
    private int bagPanelX = 1_400;
    private int bagPanelY = 600;
    private final Point lastBagPosition = new Point(bagPanelX, bagPanelY);
    private ITEM grabbedITEM;

    public UI_InventoryPanel(MainGame mainGame) {
        mg = mainGame;
        bag_Slots = new UI_InventorySlot[BAG_SLOTS];
        char_Slots = new UI_InventorySlot[CHAR_SLOTS];
        grabbedITEM = null;
        createCharSlots();
        createBagSlots();
        charPanelMover = new Rectangle(charPanelX - 40, charPanelY - 75, 438, 25);
        bagPanelMover = new Rectangle(bagPanelX, bagPanelY, 365, 50);
        bagPanelCloser = new Rectangle(bagPanelX, bagPanelY, 30, 30);
        wholeCharWindow = new Rectangle(charPanelX - 30, charPanelY - 15, 445, 615);
        wholeBagWindow = new Rectangle(bagPanelX, bagPanelY, 365, 410);
        combatStatsHitBox = new Rectangle(bagPanelX, bagPanelY, 107, 15);
        effectsHitBox = new Rectangle(bagPanelX, bagPanelY, 80, 15);
        hideCharCollision();
        hideBagCollision();
    }

    public void drawCharacterWindow(GraphicsContext gc) {
        gc.setFont(mg.ui.maruMonica);
        drawCharPanel(gc, charPanelX, charPanelY);
        lastCharPosition.x = charPanelX;
        lastCharPosition.y = charPanelY;
    }

    public void drawBagWindow(GraphicsContext gc) {
        gc.setFont(mg.ui.maruMonica);
        drawBagPanel(gc, bagPanelX, bagPanelY);
        lastBagPosition.x = bagPanelX;
        lastBagPosition.y = bagPanelY;
    }

    public void drawCharTooltip(GraphicsContext g2) {
        if (grabbedITEM == null && !mg.inputH.mouse1Pressed) {
            for (UI_InventorySlot invSlot : char_Slots) {
                if (invSlot.item != null && invSlot.toolTipTimer >= 40) {
                    getTooltip(g2, invSlot);
                }
                if (invSlot.boundBox.contains(mg.inputH.lastMousePosition)) {
                    invSlot.toolTipTimer++;
                    break;
                } else {
                    invSlot.toolTipTimer = 0;
                }
            }
        }
    }

    public void drawBagTooltip(GraphicsContext g2) {
        if (grabbedITEM == null && !mg.inputH.mouse1Pressed) {
            for (UI_InventorySlot bagSlot : bag_Slots) {
                if (bagSlot.item != null && bagSlot.toolTipTimer >= 40) {
                    getTooltip(g2, bagSlot);
                }
                if (bagSlot.boundBox.contains(mg.inputH.lastMousePosition)) {
                    bagSlot.toolTipTimer++;
                    break;
                } else {
                    bagSlot.toolTipTimer = 0;
                }
            }
        }
    }

    private void drawCharPanel(GraphicsContext gc, int startX, int startY) {
        drawCharacterBackground(gc, startX, startY);
        drawCharacterSlots(gc, startX, startY);
    }

    private void drawBagPanel(GraphicsContext gc, int startX, int startY) {
        drawBagBackground(gc, startX, startY);
        drawBagSlots(gc, startX, startY);
    }

    private void getTooltip(GraphicsContext gc, UI_InventorySlot invSlot) {
        //BACKGROUND
        gc.setFill(Colors.LightGrey);
        gc.fillRoundRect(mg.inputH.lastMousePosition.x - (MainGame.SCREEN_HEIGHT * 0.238), mg.inputH.lastMousePosition.y - (MainGame.SCREEN_HEIGHT * 0.314), MainGame.SCREEN_HEIGHT * 0.23, MainGame.SCREEN_HEIGHT * 0.324f, 15, 15);
        //OUTLINE
        setRarityColor(gc, invSlot);
        gc.setLineWidth(1);
        gc.strokeRoundRect(mg.inputH.lastMousePosition.x - (MainGame.SCREEN_HEIGHT * 0.235), mg.inputH.lastMousePosition.y - (MainGame.SCREEN_HEIGHT * 0.311), MainGame.SCREEN_HEIGHT * 0.224, MainGame.SCREEN_HEIGHT * 0.318f, 15, 15);
        //NAME
        setRarityColor(gc, invSlot);
        gc.setFont(FonT.minecraftRegular20);
        gc.fillText(invSlot.item.name, mg.inputH.lastMousePosition.x - MainGame.SCREEN_HEIGHT * 0.229f, mg.inputH.lastMousePosition.y - MainGame.SCREEN_HEIGHT * 0.268f);
        //Quality
        applyQualityColor(invSlot, gc);
        gc.setFont(FonT.minecraftItalic15);
        if (invSlot.item.quality < 100) {
            gc.fillText(invSlot.item.quality + "%", mg.inputH.lastMousePosition.x - MainGame.SCREEN_HEIGHT * 0.039_8f, mg.inputH.lastMousePosition.y - MainGame.SCREEN_HEIGHT * 0.299f);
        } else {
            gc.fillText(invSlot.item.quality + "%", mg.inputH.lastMousePosition.x - MainGame.SCREEN_HEIGHT * 0.047_3f, mg.inputH.lastMousePosition.y - MainGame.SCREEN_HEIGHT * 0.299f);
        }
        //STATS
        if (!invSlot.item.type.equals("M")) {
            gc.setFill(Colors.darkBackground);
            gc.setFont(FonT.minecraftItalic15);
            gc.fillText("INT: " + invSlot.item.intellect, mg.inputH.lastMousePosition.x - MainGame.SCREEN_HEIGHT * 0.230f, mg.inputH.lastMousePosition.y - MainGame.SCREEN_HEIGHT * 0.236f);
            gc.fillText("VIT: " + invSlot.item.vitality, mg.inputH.lastMousePosition.x - MainGame.SCREEN_HEIGHT * 0.230f, mg.inputH.lastMousePosition.y - MainGame.SCREEN_HEIGHT * 0.222f);
            gc.fillText("WIS: " + invSlot.item.wisdom, mg.inputH.lastMousePosition.x - MainGame.SCREEN_HEIGHT * 0.230f, mg.inputH.lastMousePosition.y - MainGame.SCREEN_HEIGHT * 0.208f);

            gc.fillText("AGI: " + invSlot.item.agility, mg.inputH.lastMousePosition.x - MainGame.SCREEN_HEIGHT * 0.160f, mg.inputH.lastMousePosition.y - MainGame.SCREEN_HEIGHT * 0.236f);
            gc.fillText("LUC: " + invSlot.item.luck, mg.inputH.lastMousePosition.x - MainGame.SCREEN_HEIGHT * 0.160f, mg.inputH.lastMousePosition.y - MainGame.SCREEN_HEIGHT * 0.222f);
            gc.fillText("CHA: " + invSlot.item.charisma, mg.inputH.lastMousePosition.x - MainGame.SCREEN_HEIGHT * 0.160f, mg.inputH.lastMousePosition.y - MainGame.SCREEN_HEIGHT * 0.208f);

            gc.fillText("END: " + invSlot.item.endurance, mg.inputH.lastMousePosition.x - MainGame.SCREEN_HEIGHT * 0.086f, mg.inputH.lastMousePosition.y - MainGame.SCREEN_HEIGHT * 0.236f);
            gc.fillText("STR: " + invSlot.item.strength, mg.inputH.lastMousePosition.x - MainGame.SCREEN_HEIGHT * 0.086f, mg.inputH.lastMousePosition.y - MainGame.SCREEN_HEIGHT * 0.222f);
            gc.fillText("FOC: " + invSlot.item.focus, mg.inputH.lastMousePosition.x - MainGame.SCREEN_HEIGHT * 0.086f, mg.inputH.lastMousePosition.y - MainGame.SCREEN_HEIGHT * 0.208f);

      /*  INT - Int
        WIS - Wis
        VIT - Vit
        AGI - Agi
        LUC - Luc
        CHA - Cha
        END - End
        STR - Str
        FOC - Foc

       */
            //EFFECTS
            gc.fillText("Effects: ", mg.inputH.lastMousePosition.x - 5 - 250 - 3 + 10, mg.inputH.lastMousePosition.y - 5 + 15 - 350 + 200);
        }
        //DESCRIPTION
        gc.setFont(FonT.minecraftItalic12);
        int stringY = mg.inputH.lastMousePosition.y - 5 + 15 - 350 + 270;
        for (String string : invSlot.item.description.split("\n")) {
            gc.fillText(string, mg.inputH.lastMousePosition.x - 5 - 250 - 3 + 10, stringY += 11);
        }

        gc.setFont(FonT.minecraftItalic14);
        //TYPE
        printItemType(gc, invSlot);


        //LEVEL
        gc.fillText("ilvl: " + invSlot.item.level, mg.inputH.lastMousePosition.x - 5 - 250 - 3 + 10, mg.inputH.lastMousePosition.y - 5 + 15 - 350 + 18);
        //Durability
        gc.fillText("D: " + invSlot.item.durability, mg.inputH.lastMousePosition.x - 5 - 250 - 3 + 7, mg.inputH.lastMousePosition.y - 5 + 15 - 7);
        //ID
        gc.fillText("ID: " + String.format("%04d", invSlot.item.i_id) + invSlot.item.type, mg.inputH.lastMousePosition.x - 5 - 73, mg.inputH.lastMousePosition.y - 5 + 15 - 7);
    }

    private void setRarityColor(GraphicsContext gc, UI_InventorySlot slot) {
        if (slot.item != null) {
            if (slot.item.rarity == 1) {
                gc.setStroke(Colors.NormalGrey);
                gc.setFill(Colors.NormalGrey);
            } else if (slot.item.rarity == 2) {
                gc.setStroke(Colors.rareColor);
                gc.setFill(Colors.rareColor);
            } else if (slot.item.rarity == 3) {
                gc.setStroke(Colors.epicColor);
                gc.setFill(Colors.epicColor);
            } else if (slot.item.rarity == 4) {
                gc.setStroke(Colors.legendaryColor);
                gc.setFill(Colors.legendaryColor);
            } else if (slot.item.rarity == 5) {
                gc.setStroke(Colors.setItem);
                gc.setFill(Colors.setItem);
            } else if (slot.item.rarity == 10) {
                gc.setStroke(Colors.legendaryColor);
                gc.setFill(Colors.legendaryColor);
            }
        } else {
            gc.setStroke(Colors.darkBackground);
            gc.setFill(Colors.darkBackground);
        }
    }

    private void printItemType(GraphicsContext gc, UI_InventorySlot slot) {
        int xPosition = (int) (mg.inputH.lastMousePosition.x - MainGame.SCREEN_HEIGHT * 0.148f);
        int yPosition = (int) (mg.inputH.lastMousePosition.y + MainGame.SCREEN_HEIGHT * 0.002_7f);
        switch (slot.item.type) {
            case "H" -> gc.fillText("Helm", xPosition, yPosition);
            case "C" -> gc.fillText("Chest", xPosition, yPosition);
            case "P" -> gc.fillText("Pants", xPosition, yPosition);
            case "B" -> gc.fillText("Boots", xPosition - MainGame.SCREEN_HEIGHT * 0.004, yPosition);
            case "A" -> gc.fillText("Amulet", xPosition - MainGame.SCREEN_HEIGHT * 0.009f, yPosition);
            case "R" -> gc.fillText("Ring", xPosition, yPosition);
            case "T" -> gc.fillText("Relic", xPosition - MainGame.SCREEN_HEIGHT * 0.004, yPosition);
            case "W" -> gc.fillText("One-Handed", xPosition - MainGame.SCREEN_HEIGHT * 0.017f, yPosition);
            case "2" -> gc.fillText("Two-Handed", xPosition - MainGame.SCREEN_HEIGHT * 0.019f, yPosition);
            case "O" -> gc.fillText("Offhand", xPosition - MainGame.SCREEN_HEIGHT * 0.004, yPosition);
            default -> gc.fillText("Misc", xPosition, yPosition);
        }
    }

    public void drawDragAndDrop(GraphicsContext gc) {
        if (grabbedITEM != null) {
            gc.drawImage(grabbedITEM.icon, mg.inputH.lastMousePosition.x - SLOT_SIZE / 2.0f, mg.inputH.lastMousePosition.y - SLOT_SIZE / 2.0f, SLOT_SIZE, SLOT_SIZE);
        }
        if (grabbedITEM == null && mg.showChar) {
            for (int i = 0; i < char_Slots.length; i++) {
                if (char_Slots[i].boundBox.contains(mg.inputH.lastMousePosition) && char_Slots[i].item != null) {
                    if (mg.inputH.X_pressed) {
                        mg.WORLD_DROPS.add(new DRP_DroppedItem(mg, (int) (Player.worldX - 50), (int) Player.worldY, char_Slots[i].item));
                        char_Slots[i].item = null;
                    } else if (mg.inputH.mouse1Pressed) {
                        char_Slots[i].grabbed = true;
                        grabbedITEM = char_Slots[i].item;
                        grabbedIndexChar = i;
                        char_Slots[i].item = null;
                    }
                    mg.player.updateEquippedItems();
                }
            }
        }
        if (grabbedITEM == null && mg.showBag) {
            for (int i = 0; i < bag_Slots.length; i++) {
                if (bag_Slots[i].boundBox.contains(mg.inputH.lastMousePosition) && bag_Slots[i].item != null) {
                    if (mg.inputH.X_pressed) {
                        mg.WORLD_DROPS.add(new DRP_DroppedItem(mg, (int) (Player.worldX - 50), (int) Player.worldY, bag_Slots[i].item));
                        bag_Slots[i].item = null;
                    } else if (mg.inputH.mouse1Pressed) {
                        bag_Slots[i].grabbed = true;
                        grabbedITEM = bag_Slots[i].item;
                        grabbedIndexBag = i;
                        bag_Slots[i].item = null;
                    }
                }
            }
        }
        if (grabbedITEM != null && !mg.inputH.mouse1Pressed) {
            if (mg.showChar) {
                for (UI_InventorySlot invSlot : char_Slots) {
                    if (invSlot.boundBox.contains(mg.inputH.lastMousePosition)) {
                        if (invSlot.item != null) {
                            if (grabbedIndexChar != -1) {
                                char_Slots[grabbedIndexChar].item = invSlot.item;
                            }
                            if (grabbedIndexBag != -1) {
                                bag_Slots[grabbedIndexBag].item = invSlot.item;
                            }
                        }
                        mg.player.updateEquippedItems();
                        invSlot.item = grabbedITEM;
                        grabbedITEM = null;
                    }
                    invSlot.grabbed = false;
                }
            }
            if (mg.showBag) {
                for (UI_InventorySlot bagSlot : bag_Slots) {
                    if (bagSlot.boundBox.contains(mg.inputH.lastMousePosition)) {
                        if (grabbedIndexChar != -1) {
                            char_Slots[grabbedIndexChar].item = bagSlot.item;
                        }
                        if (grabbedIndexBag != -1) {
                            bag_Slots[grabbedIndexBag].item = bagSlot.item;
                        }
                        mg.player.updateEquippedItems();
                        bagSlot.item = grabbedITEM;
                        grabbedITEM = null;
                    }
                    bagSlot.grabbed = false;
                }
            }
            if (grabbedIndexChar != -1 && grabbedITEM != null) {
                char_Slots[grabbedIndexChar].item = grabbedITEM;
            }
            if (grabbedIndexBag != -1 && grabbedITEM != null) {
                bag_Slots[grabbedIndexBag].item = grabbedITEM;
            }
            grabbedIndexChar = -1;
            grabbedIndexBag = -1;

            if (grabbedITEM != null) {
                grabbedITEM = null;
            }
        }
    }

    public void interactWithWindows() {
        for (TalentNode node : mg.talentP.talent_Nodes) {
            if (node != null) {
                if (node.boundBox.contains(mg.inputH.lastMousePosition) && mg.inputH.mouse1Pressed) {
                    if (mg.talentP.checkValidTalent(node)) {
                        node.activated = true;
                    }
                }
            }
        }
        if (mg.inputH.mouse1Pressed && charPanelMover.contains(mg.inputH.lastMousePosition)) {
            charPanelX += mg.inputH.lastMousePosition.x - previousMousePosition.x;
            charPanelY += mg.inputH.lastMousePosition.y - previousMousePosition.y;
            charPanelMover.x = charPanelX - 40;
            charPanelMover.y = charPanelY - 75;
        } else if (mg.inputH.mouse1Pressed && bagPanelMover.contains(mg.inputH.lastMousePosition)) {
            bagPanelX += mg.inputH.lastMousePosition.x - previousMousePosition.x;
            bagPanelY += mg.inputH.lastMousePosition.y - previousMousePosition.y;
            bagPanelMover.x = bagPanelX;
            bagPanelMover.y = bagPanelY - 10;
        } else if (mg.inputH.mouse1Pressed && mg.talentP.wholeTalentWindow.contains(mg.inputH.lastMousePosition)) {
            mg.talentP.talentPanelX += mg.inputH.lastMousePosition.x - previousMousePosition.x;
            mg.talentP.talentPanelY += mg.inputH.lastMousePosition.y - previousMousePosition.y;
        } else if (mg.inputH.mouse2Pressed && mg.talentP.wholeTalentWindow.contains(mg.inputH.lastMousePosition)) {
            mg.talentP.talentPanelX = 960 - 16;
            mg.talentP.talentPanelY = 540 - 16;
            for (TalentNode node : mg.talentP.talent_Nodes) {
                if (node != null) {
                    node.activated = false;
                }
            }
        }
        previousMousePosition.x = mg.inputH.lastMousePosition.x;
        previousMousePosition.y = mg.inputH.lastMousePosition.y;
    }

    private void drawCharacterBackground(GraphicsContext gc, int startX, int startY) {
        //inventory background
        //big background
        wholeCharWindow.x = startX - 47;
        wholeCharWindow.y = startY - 78;
        gc.setFill(Colors.LightGrey);
        gc.fillRoundRect(startX - 50, startY - 80, 450, 620, 35, 35);
        //outline
        gc.setStroke(Colors.darkBackground);
        gc.setLineWidth(5);
        gc.strokeRoundRect(startX - 45, startY - 75, 440, 620 - 10, 30, 30);
        gc.setFill(Colors.mediumLightGrey);
        gc.fillRoundRect(startX - 42, startY - 75, 434, 22, 15, 15);
        gc.setStroke(Colors.darkBackground);
        gc.strokeRoundRect(startX - 42, startY - 75, 434, 22, 15, 15);
        gc.setFill(Colors.darkBackground);
        gc.setFont(FonT.minecraftBold13);
        gc.fillText("Character", startX - 80 + 218, startY - 78 + 17);
        gc.fillText("No Title", startX - 45 + 217 - 25, startY);
        gc.fillText("Level " + mg.player.level, 135 + startX, 20 + startY);

        //character image outline
        gc.setLineWidth(2);
        gc.strokeRoundRect(75 + startX, startY + 40, 200, 250, 25, 25);

        //game world.player image
        gc.drawImage(mg.player.entityImage1, startX + 135, startY + 135, 60, 120);
        //Stats Text
        gc.strokeRoundRect(startX - 14, startY + 375, 187, 150, 15, 15);
        gc.strokeRoundRect(startX + 177, startY + 375, 187, 150, 15, 15);
        gc.fillText("Base  Stats", startX - 5, startY + 385 - 15);

        //stats
        gc.fillText("Intelligence: " + mg.player.intellect, startX - 5, startY + 393);
        gc.fillText("Wisdom: " + mg.player.wisdom, startX - 5, startY + 393 + 15);
        gc.fillText("Vitality: " + mg.player.vitality, startX - 5, startY + 393 + 30);
        gc.fillText("Agility: " + mg.player.agility, startX - 5, startY + 393 + 30 + 15);

        gc.fillText("Luck: " + mg.player.luck, startX - 5, startY + 393 + 30 + 30);
        gc.fillText("Charisma: " + mg.player.charisma, startX - 5, startY + 393 + 30 + 30 + 15);
        gc.fillText("Endurance: " + mg.player.endurance, startX - 5, startY + 393 + 30 + 30 + 30);
        gc.fillText("Strength: " + mg.player.strength, startX - 5, startY + 393 + 30 + 30 + 30 + 15);
        gc.fillText("Focus: " + mg.player.focus, startX - 5, startY + 393 + 30 + 30 + 30 + 30);

        // second panel stats
        gc.fillText("Combat Stats", startX + 185, startY + 372);
        gc.fillText("Effects", startX + 292, startY + 372);

        gc.strokeRoundRect(startX + 180, startY + 360, 107, 15, 10, 10);
        gc.strokeRoundRect(startX + 287, startY + 360, 68, 15, 10, 10);
        combatStatsHitBox.x = startX + 180;
        combatStatsHitBox.y = startY + 360;
        effectsHitBox.x = startX + 287;
        effectsHitBox.y = startY + 360;
        if (showCombatStats) {
            drawCombatStats(gc, startX, startY);
            gc.setFill(Colors.mediumVeryLight);
            gc.fillRoundRect(startX + 180, startY + 360, 107, 15, 10, 10);
            gc.setFill(Colors.darkBackground);
            gc.strokeRoundRect(startX + 180, startY + 360, 107, 15, 10, 10);
            gc.fillText("Combat Stats", startX + 185, startY + 372);
        } else {
            drawEffects(gc, startX, startY);
            gc.setFill(Colors.mediumVeryLight);
            gc.fillRoundRect(startX + 287, startY + 360, 68, 15, 10, 10);
            gc.setFill(Colors.darkBackground);
            gc.strokeRoundRect(startX + 287, startY + 360, 68, 15, 10, 10);
            gc.fillText("Effects", startX + 292, startY + 372);
        }
    }

    private void drawCombatStats(GraphicsContext gc, int startX, int startY) {
        gc.setFill(Colors.darkBackground);
        gc.fillText("Max-Health: " + mg.player.maxHealth, startX + 182, startY + 393);
        gc.fillText("Max-Mana: " + mg.player.maxMana, startX + 182, startY + 408);
        gc.fillText("ManaREG: " + df.format(mg.player.manaRegeneration * 60) + "/s", startX + 182, startY + 423);
        gc.fillText("HealthREG: " + df.format(mg.player.healthRegeneration * 60) + "/s", startX + 182, startY + 438);
        gc.fillText("Armour: " + mg.player.armour, startX + 182, startY + 453);
        gc.fillText("Critchance: " + mg.player.critChance, startX + 182, startY + 468);
        gc.fillText("Resist %: " + mg.player.resistChance, startX + 182, startY + 483);
        gc.fillText("Carry-weight: " + mg.player.carryWeight, startX + 182, startY + 498);
        gc.fillText("MovementSpeed: " + mg.player.playerMovementSpeed, startX + 182, startY + 512);
    }

    private void drawEffects(GraphicsContext gc, int startX, int startY) {
        gc.fillText("hello", startX + 182, startY + 420);
    }

    private void drawCharacterSlots(GraphicsContext gc, int startX, int startY) {
        gc.setLineWidth(2);
        for (int i = 0; i <= 3; i++) {
            char_Slots[i].boundBox.x = 21 + startX;
            char_Slots[i].boundBox.y = (i * 50 + 65 + startY);
            gc.setFill(Colors.mediumVeryLight);
            gc.fillRoundRect(21 + startX, i * 50 + 65 + startY, 45, 45, 20, 20);
            setRarityColor(gc, char_Slots[i]);
            char_Slots[i].drawSlot(gc, 21 + startX, (i * 50 + 65 + startY));
            if (char_Slots[i].item != null && !char_Slots[i].grabbed) {
                char_Slots[i].drawIcon(gc, 21 + startX, ((i * 50) + 65 + startY), SLOT_SIZE);
            }
        }
        for (int i = 4; i <= 7; i++) {
            char_Slots[i].boundBox.x = 18 + 265 + startX;
            char_Slots[i].boundBox.y = (((i - 4) * 50) + 65 + startY);
            gc.setFill(Colors.mediumVeryLight);
            gc.fillRoundRect(18 + 265 + startX, ((i - 4) * 50) + 65 + startY, 45, 45, 20, 20);
            setRarityColor(gc, char_Slots[i]);
            char_Slots[i].drawSlot(gc, 18 + 265 + startX, (((i - 4) * 50) + 65 + startY));
            if (char_Slots[i].item != null && !char_Slots[i].grabbed) {
                char_Slots[i].drawIcon(gc, 18 + 265 + startX, ((i - 4) * 50) + 65 + startY, SLOT_SIZE);
            }
        }
        for (int i = 8; i <= 9; i++) {
            char_Slots[i].boundBox.x = ((i - 8) * 57) + 128 + startX;
            char_Slots[i].boundBox.y = 80 + 217 + startY;
            gc.setFill(Colors.mediumVeryLight);
            gc.fillRoundRect(((i - 8) * 57) + 128 + startX, 80 + 217 + startY, 45, 45, 20, 20);
            setRarityColor(gc, char_Slots[i]);
            char_Slots[i].drawSlot(gc, ((i - 8) * 57) + 128 + startX, 80 + 217 + startY);
            if (char_Slots[i].item != null && !char_Slots[i].grabbed) {
                char_Slots[i].drawIcon(gc, ((i - 8) * 57) + 128 + startX, 80 + 217 + startY, SLOT_SIZE);
            }
        }
    }

    private void drawBagBackground(GraphicsContext g2, int startX, int startY) {
        wholeBagWindow.x = startX;
        wholeBagWindow.y = startY;
        //big background
        g2.setFill(Colors.LightGrey);
        g2.fillRoundRect(startX, startY, 365, 410, 15, 15);
        //outline
        g2.setLineWidth(5);
        g2.setStroke(Colors.darkBackground);
        g2.strokeRoundRect(startX + 5, startY + 5, 355, 400, 25, 25);
        g2.setLineWidth(2);
        //feature pane
        g2.setStroke(Colors.darkBackground);
        g2.strokeRoundRect(startX + 5, startY + 31, 355, 20, 15, 15);
        //window mover
        g2.setFill(Colors.darkBackground);
        g2.fillRoundRect(startX, startY, 365, 30, 15, 15);
    }

    private void drawBagSlots(GraphicsContext gc, int startX, int startY) {
        gc.setStroke(Colors.darkBackground);
        for (int i = 0; i < bag_Slots.length; i++) {
            if (i <= 6) {
                bag_Slots[i].boundBox.x = i * 50 + startX + 10;
                bag_Slots[i].boundBox.y = 60 + startY;
                bag_Slots[i].drawSlot(gc, i * 50 + startX + 10, 60 + startY);
                if (bag_Slots[i].item != null && !bag_Slots[i].grabbed) {
                    bag_Slots[i].drawIcon(gc, i * 50 + startX + 10, 60 + startY, SLOT_SIZE);
                }
            } else if (i <= 13) {
                bag_Slots[i].boundBox.x = (i - 7) * 50 + startX + 10;
                bag_Slots[i].boundBox.y = 110 + startY;
                bag_Slots[i].drawSlot(gc, (i - 7) * 50 + startX + 10, 110 + startY);
                if (bag_Slots[i].item != null && !bag_Slots[i].grabbed) {
                    bag_Slots[i].drawIcon(gc, (i - 7) * 50 + startX + 10, 110 + startY, SLOT_SIZE);
                }
            } else if (i <= 19) {
                bag_Slots[i].boundBox.x = (i - 14) * 50 + startX + 10;
                bag_Slots[i].boundBox.y = 160 + startY;
                bag_Slots[i].drawSlot(gc, (i - 14) * 50 + startX + 10, 160 + startY);
                if (bag_Slots[i].item != null && !bag_Slots[i].grabbed) {
                    bag_Slots[i].drawIcon(gc, (i - 14) * 50 + startX + 10, 160 + startY, SLOT_SIZE);
                }
            }
        }
    }

    public void hideCharCollision() {
        wholeCharWindow.x = -1_000;
        wholeCharWindow.y = -1_000;
    }

    private void applyQualityColor(UI_InventorySlot invSlot, GraphicsContext gc) {
        if (invSlot.item.quality < 90) {
            gc.setFill(Colors.NormalGrey);
        }
        if (invSlot.item.quality >= 90) {
            gc.setFill(Colors.mediumQuality);
        }
        if (invSlot.item.quality == 100) {
            gc.setFill(Colors.highQuality);
        }
    }

    public void hideBagCollision() {
        wholeBagWindow.x = -1_000;
        wholeBagWindow.y = -1_000;
    }

    public void resetCharCollision() {
        wholeCharWindow.x = lastCharPosition.x;
        wholeCharWindow.y = lastCharPosition.y;
    }

    public void resetBagCollision() {

        wholeBagWindow.x = lastBagPosition.x;
        wholeBagWindow.y = lastBagPosition.y;
    }

    private void createCharSlots() {
        for (int i = 0; i <= 3; i++) {
            char_Slots[i] = new UI_InventorySlot(null, 40 + 260, (i * 50) + 110 + 200);
        }
        for (int i = 4; i <= 7; i++) {
            char_Slots[i] = new UI_InventorySlot(null, 40 + 270 + 260, ((i - 4) * 50) + 110 + 200);
        }
        for (int i = 8; i <= 9; i++) {
            char_Slots[i] = new UI_InventorySlot(null, ((i - 8) * 50) + 160 + 260, 110 + 240 + 200);
        }
    }

    private void createBagSlots() {
        for (int i = 0; i <= 9; i++) {
            bag_Slots[i] = new UI_InventorySlot(null, (i * 50) + 40 + 770, 130 + 200);
        }
        for (int i = 10; i < 15; i++) {
            bag_Slots[i] = new UI_InventorySlot(null, ((i - 10) * 50) + 40 + 770, 130 + 200 + 50);
        }
    }
}