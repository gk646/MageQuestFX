package main.system.ui.inventory;

import gameworld.player.Player;
import gameworld.world.objects.drops.DRP_DroppedItem;
import gameworld.world.objects.items.ITEM;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.MainGame;
import main.system.ui.Colors;
import main.system.ui.FonT;

import java.awt.Point;
import java.awt.Rectangle;
import java.text.DecimalFormat;

public class UI_InventoryPanel {
    private static final int SLOT_SIZE = 45;
    private static final int BAG_SLOTS = 15;
    private static final int CHAR_SLOTS = 10;
    public final UI_InventorySlot[] char_Slots;
    public final UI_InventorySlot[] bag_Slots;
    public final Rectangle wholeCharWindow;
    public final Rectangle wholeBagWindow;
    private final MainGame mg;
    private final DecimalFormat df = new DecimalFormat("#.##");
    private final Rectangle charPanelCloser;
    private final Rectangle bagPanelCloser;
    private final Rectangle charPanelMover;
    private final Rectangle bagPanelMover;
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
        charPanelMover = new Rectangle(charPanelX - 50, charPanelY - 65 + 2 - 10, 500, 50);
        charPanelCloser = new Rectangle(charPanelX, charPanelY, 30, 30);
        bagPanelMover = new Rectangle(bagPanelX, bagPanelY, 365, 50);
        bagPanelCloser = new Rectangle(bagPanelX, bagPanelY, 30, 30);
        wholeCharWindow = new Rectangle(charPanelX, charPanelY, 500, 650);
        wholeBagWindow = new Rectangle(bagPanelX, bagPanelY, 365, 410);
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
        gc.fillRoundRect(mg.inputH.lastMousePosition.x - 5 - 250 - 3, mg.inputH.lastMousePosition.y - 5 + 15 - 350, 250, 350, 15, 15);
        //OUTLINE
        setRarityColor(gc, invSlot);
        gc.setLineWidth(2);
        gc.strokeRoundRect(mg.inputH.lastMousePosition.x - 5 - 3 - 244 - 3, mg.inputH.lastMousePosition.y - 5 + 15 - 3 - 344, 244, 344, 15, 15);
        //NAME
        setRarityColor(gc, invSlot);
        gc.setFont(FonT.minecraftRegular20);
        gc.fillText(invSlot.item.name, mg.inputH.lastMousePosition.x - 5 - 250 - 3 + 10, mg.inputH.lastMousePosition.y - 5 + 15 - 350 + 50);
        //Quality
        applyQualityColor(invSlot, gc);
        gc.setFont(FonT.minecraftItalic15);
        if (invSlot.item.quality < 100) {
            gc.fillText(invSlot.item.quality + "%", mg.inputH.lastMousePosition.x - 5 - 38, mg.inputH.lastMousePosition.y - 5 + 15 - 350 + 17);
        } else {
            gc.fillText(invSlot.item.quality + "%", mg.inputH.lastMousePosition.x - 5 - 47, mg.inputH.lastMousePosition.y - 5 + 15 - 350 + 17);
        }
        //STATS
        if (!invSlot.item.type.equals("M")) {
            gc.setFill(Colors.darkBackground);
            gc.setFont(FonT.minecraftItalic15);
            gc.fillText("INT: " + invSlot.item.intellect, mg.inputH.lastMousePosition.x - 5 - 250 - 3 + 9, mg.inputH.lastMousePosition.y - 5 + 30 - 350 + 70);
            gc.fillText("VIT: " + invSlot.item.vitality, mg.inputH.lastMousePosition.x - 5 - 250 - 3 + 9, mg.inputH.lastMousePosition.y - 5 + 30 - 350 + 85);
            gc.fillText("WIS: " + invSlot.item.wisdom, mg.inputH.lastMousePosition.x - 5 - 250 - 3 + 9, mg.inputH.lastMousePosition.y - 5 + 30 - 350 + 100);

            gc.fillText("AGI: " + invSlot.item.agility, mg.inputH.lastMousePosition.x - 5 - 250 - 3 + 85, mg.inputH.lastMousePosition.y - 5 + 30 - 350 + 70);
            gc.fillText("LUC: " + invSlot.item.luck, mg.inputH.lastMousePosition.x - 5 - 250 - 3 + 85, mg.inputH.lastMousePosition.y - 5 + 30 - 350 + 85);
            gc.fillText("CHA: " + invSlot.item.charisma, mg.inputH.lastMousePosition.x - 5 - 250 - 3 + 85, mg.inputH.lastMousePosition.y - 5 + 30 - 350 + 100);

            gc.fillText("END: " + invSlot.item.endurance, mg.inputH.lastMousePosition.x - 5 - 250 - 3 + 165, mg.inputH.lastMousePosition.y - 5 + 30 - 350 + 70);
            gc.fillText("STR: " + invSlot.item.strength, mg.inputH.lastMousePosition.x - 5 - 250 - 3 + 165, mg.inputH.lastMousePosition.y - 5 + 30 - 350 + 85);
            gc.fillText("FOC: " + invSlot.item.focus, mg.inputH.lastMousePosition.x - 5 - 250 - 3 + 165, mg.inputH.lastMousePosition.y - 5 + 30 - 350 + 100);

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

        gc.setFont(FonT.minecraftRegular14);
        //LEVEL
        gc.fillText("ilvl: " + invSlot.item.level, mg.inputH.lastMousePosition.x - 5 - 250 - 3 + 10, mg.inputH.lastMousePosition.y - 5 + 15 - 350 + 18);
        //Durability
        gc.fillText("D: " + invSlot.item.durability, mg.inputH.lastMousePosition.x - 5 - 250 - 3 + 7, mg.inputH.lastMousePosition.y - 5 + 15 - 7);
        //ID
        gc.fillText("ID: " + String.format("%04d", invSlot.item.i_id) + invSlot.item.type, mg.inputH.lastMousePosition.x - 5 - 73, mg.inputH.lastMousePosition.y - 5 + 15 - 7);
    }

    private void setRarityColor(GraphicsContext g2, UI_InventorySlot slot) {
        g2.setStroke(Colors.darkBackground);
        g2.setFill(Colors.darkBackground);
        if (slot.item.rarity == 1) {
            g2.setStroke(Colors.NormalGrey);
            g2.setFill(Colors.NormalGrey);
        } else if (slot.item.rarity == 2) {
            g2.setStroke(Colors.rareColor);
            g2.setFill(Colors.rareColor);
        } else if (slot.item.rarity == 3) {
            g2.setStroke(Colors.epicColor);
            g2.setFill(Colors.epicColor);
        } else if (slot.item.rarity == 4) {
            g2.setStroke(Colors.legendaryColor);
            g2.setFill(Colors.legendaryColor);
        } else if (slot.item.rarity == 5) {
            g2.setStroke(Colors.setItem);
            g2.setFill(Colors.setItem);
        } else if (slot.item.rarity == 10) {
            g2.setStroke(Colors.legendaryColor);
            g2.setFill(Colors.legendaryColor);
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
                        mg.player.updateEquippedItems();
                        char_Slots[i].grabbed = true;
                        grabbedITEM = char_Slots[i].item;
                        grabbedIndexChar = i;
                        char_Slots[i].item = null;
                    }
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
                        mg.player.updateEquippedItems();
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
                        invSlot.item = grabbedITEM;
                        mg.player.updateEquippedItems();
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
        if (mg.inputH.mouse1Pressed && charPanelMover.contains(mg.inputH.lastMousePosition)) {
            charPanelX += mg.inputH.lastMousePosition.x - previousMousePosition.x;
            charPanelY += mg.inputH.lastMousePosition.y - previousMousePosition.y;
            charPanelMover.x = charPanelX - 50;
            charPanelMover.y = charPanelY - 65 + 2 - 10;
        } else if (mg.inputH.mouse1Pressed && bagPanelMover.contains(mg.inputH.lastMousePosition)) {
            bagPanelX += mg.inputH.lastMousePosition.x - previousMousePosition.x;
            bagPanelY += mg.inputH.lastMousePosition.y - previousMousePosition.y;
            bagPanelMover.x = bagPanelX;
            bagPanelMover.y = bagPanelY - 10;
        } else if (mg.inputH.mouse1Pressed && charPanelCloser.contains(mg.inputH.lastMousePosition)) {
            mg.showChar = false;
            hideCharCollision();
        } else if (mg.inputH.mouse1Pressed && bagPanelCloser.contains(mg.inputH.lastMousePosition)) {
            mg.showBag = false;
            hideBagCollision();
        }
        previousMousePosition.x = mg.inputH.lastMousePosition.x;
        previousMousePosition.y = mg.inputH.lastMousePosition.y;
    }

    private void drawCharacterBackground(GraphicsContext gc, int startX, int startY) {
        //inventory background
        //big background
        wholeCharWindow.x = startX - 65;
        wholeCharWindow.y = startY - 65;
        gc.setFill(Colors.LightGrey);
        gc.fillRoundRect(startX - 50, startY - 65, 500, 650, 35, 35);
        //outline
        gc.setStroke(Colors.darkBackground);
        gc.setLineWidth(5);
        gc.strokeRoundRect(startX - 50 + 5, startY - 65 + 5, 500 - 10, 650 - 10, 30, 30);
        gc.setLineWidth(1);
        //window mover
        gc.setFill(Colors.darkBackground);
        gc.fillRoundRect(startX - 50, startY - 65 + 2, 500, 30, 15, 15);
        //window close button
        gc.setStroke(Color.RED);
        gc.strokeRoundRect(startX + 500 - 50 - 30, startY - 65 + 2, 30, 30, 5, 5);
        charPanelCloser.x = startX - 50 + 500 - 30;
        charPanelCloser.y = startY - 65 + 2;
        //character image outline
        gc.setStroke(Colors.darkBackground);
        gc.strokeRoundRect(100 + startX, startY + 90, 200, 250, 25, 25);
        //game world.player image
        gc.drawImage(mg.player.entityImage1, startX + 150, startY + 150, 120, 120);
        //Stats Text
        gc.setFill(Colors.darkBackground);
        gc.setFont(mg.ui.maruMonica30);
        gc.fillText("No Title", 120 + startX, 20 + startY);
        gc.fillText("Mage: Level" + mg.player.level, 120 + startX, 50 + startY);


        gc.fillText("INT: " + mg.player.intellect, startX + 110, startY + 420);
        gc.fillText("VIT: " + mg.player.vitality, startX + 110, startY + 438);
        gc.fillText("WIS: " + mg.player.wisdom, startX + 110, startY + 456);
        gc.fillText("SPD: " + mg.player.agility, startX + 110, startY + 474);
        gc.fillText("Health: " + mg.player.maxHealth, startX + 170, startY + 420);
        gc.fillText("Mana: " + mg.player.maxMana, startX + 170, startY + 440);
        gc.fillText("MvmtSPD: " + mg.player.movementSpeed, startX + 170, startY + 460);
        gc.fillText("ManaREG: " + df.format(mg.player.manaRegeneration * 60) + "/s", startX + 170, startY + 480);
        gc.fillText("HealthREG: " + df.format(mg.player.healthRegeneration * 60) + "/s", startX + 170, startY + 500);
    }

    private void drawCharacterSlots(GraphicsContext gc, int startX, int startY) {
        //Character Slots
        gc.setLineWidth(2);
        for (int i = 0; i <= 3; i++) {
            char_Slots[i].boundBox.x = 40 + startX;
            char_Slots[i].boundBox.y = (i * 50 + 110 + startY);
            char_Slots[i].drawSlot(gc, 40 + startX, (i * 50 + 110 + startY));
            if (char_Slots[i].item != null && !char_Slots[i].grabbed) {
                char_Slots[i].drawIcon(gc, 40 + startX, ((i * 50) + 110 + startY), SLOT_SIZE);
            }
        }
        for (int i = 4; i <= 7; i++) {
            char_Slots[i].boundBox.x = 40 + 270 + startX;
            char_Slots[i].boundBox.y = (((i - 4) * 50) + 110 + startY);
            char_Slots[i].drawSlot(gc, 40 + 270 + startX, (((i - 4) * 50) + 110 + startY));
            if (char_Slots[i].item != null && !char_Slots[i].grabbed) {
                char_Slots[i].drawIcon(gc, 40 + 270 + startX, ((i - 4) * 50) + 110 + startY, SLOT_SIZE);
            }
        }
        for (int i = 8; i <= 9; i++) {
            char_Slots[i].boundBox.x = ((i - 8) * 50) + 140 + startX;
            char_Slots[i].boundBox.y = 110 + 240 + startY;
            char_Slots[i].drawSlot(gc, ((i - 8) * 50) + 145 + startX, 110 + 240 + startY);
            if (char_Slots[i].item != null && !char_Slots[i].grabbed) {
                char_Slots[i].drawIcon(gc, ((i - 8) * 50) + 145 + startX, 110 + 240 + startY, SLOT_SIZE);
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

        //window close button
        g2.setFill(Color.RED);
        g2.fillRoundRect(startX + 365 - 30, startY, 30, 30, 15, 15);
        bagPanelCloser.x = startX + 365 - 30;
        bagPanelCloser.y = startY;
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