package main.system.ui.inventory;

import gameworld.Item;
import main.MainGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class InventoryPanel {
    private static final int SLOT_SIZE = 45;
    private static final int BAG_SLOTS = 15;
    private static final int CHAR_SLOTS = 10;
    private final Color normalColor = new Color(143, 143, 140, 255), epicColor = new Color(168, 93, 218), legendaryColor = new Color(239, 103, 3);
    private final Color rareColor = new Color(26, 111, 175), lightBackgroundAlpha = new Color(192, 203, 220, 190), darkBackground = new Color(90, 105, 136);
    private final InventorySlot[] bag_Slots;
    private final MainGame mg;

    public InventorySlot[] char_Slots;
    private final BasicStroke width2 = new BasicStroke(2);
    private final BasicStroke width5 = new BasicStroke(5);
    private final BasicStroke width1 = new BasicStroke(1);
    private final Rectangle charPanelCloser;
    private final Rectangle bagPanelCloser;
    private final Rectangle charPanelMover;
    private final Rectangle bagPanelMover;
    private final Point lastBagPosition = new Point(bagPanelX, bagPanelY);
    public Rectangle wholeCharWindow;
    public Rectangle wholeBagWindow;
    private int charPanelX = 300, charPanelY = 300, bagPanelX = 1400, bagPanelY = 600, stringY = 0;
    private final Point lastCharPosition = new Point(charPanelX, charPanelY);
    private Point previousMousePosition = new Point(300, 300);
    private Item grabbedItem;
    private InventorySlot grabbedSlot;

    public InventoryPanel(MainGame mainGame) {
        mg = mainGame;
        bag_Slots = new InventorySlot[BAG_SLOTS];
        char_Slots = new InventorySlot[CHAR_SLOTS];
        grabbedItem = null;
        createCharSlots();
        createBagSlots();
        charPanelMover = new Rectangle(charPanelX, charPanelY, 500, 50);
        charPanelCloser = new Rectangle(charPanelX, charPanelY, 30, 30);
        bagPanelMover = new Rectangle(bagPanelX, bagPanelY, 365, 50);
        bagPanelCloser = new Rectangle(bagPanelX, bagPanelY, 30, 30);
        wholeCharWindow = new Rectangle(charPanelX, charPanelY, 500, 650);
        wholeBagWindow = new Rectangle(bagPanelX, bagPanelY, 365, 410);
        hideCharCollision();
        hideBagCollision();
        bag_Slots[0].item = mg.CHEST.get(1);
        bag_Slots[1].item = mg.CHEST.get(2);
        bag_Slots[2].item = mg.CHEST.get(3);
        bag_Slots[3].item = mg.CHEST.get(4);
        bag_Slots[4].item = mg.RINGS.get(1);
        bag_Slots[5].item = mg.RINGS.get(2);
    }

    public void drawCharacterWindow(Graphics2D g2) {
        g2.setFont(mg.ui.maruMonica);
        drawCharPanel(g2, charPanelX, charPanelY);
        lastCharPosition.x = charPanelX;
        lastCharPosition.y = charPanelY;

    }

    public void drawBagWindow(Graphics2D g2) {
        g2.setFont(mg.ui.maruMonica);
        drawBagPanel(g2, bagPanelX, bagPanelY);
        lastBagPosition.x = bagPanelX;
        lastBagPosition.y = bagPanelY;
    }

    public void drawCharTooltip(Graphics2D g2) {
        if (grabbedItem == null && !mg.mouseH.mouse1Pressed) {
            for (InventorySlot invSlot : char_Slots) {
                if (invSlot.item != null && invSlot.toolTipTimer >= 40) {
                    getTooltip(g2, invSlot);
                }
                if (invSlot.boundBox.contains(mg.motionH.lastMousePosition)) {
                    invSlot.toolTipTimer++;
                    break;
                } else {
                    invSlot.toolTipTimer = 0;
                }
            }
        }

    }

    public void drawBagTooltip(Graphics2D g2) {
        if (grabbedItem == null && !mg.mouseH.mouse1Pressed) {
            for (InventorySlot bagSlot : bag_Slots) {
                if (bagSlot.item != null && bagSlot.toolTipTimer >= 40) {
                    getTooltip(g2, bagSlot);
                }
                if (bagSlot.boundBox.contains(mg.motionH.lastMousePosition)) {
                    bagSlot.toolTipTimer++;
                    break;
                } else {
                    bagSlot.toolTipTimer = 0;
                }
            }
        }
    }

    private void drawCharPanel(Graphics2D g2, int startX, int startY) {
        drawCharacterBackground(g2, startX, startY);
        drawCharacterSlots(g2, startX, startY);
    }

    private void drawBagPanel(Graphics2D g2, int startX, int startY) {
        drawBagBackground(g2, startX, startY);
        drawBagSlots(g2, startX, startY);
    }


    private void getTooltip(Graphics2D g2, InventorySlot invSlot) {
        //BACKGROUND
        g2.setColor(lightBackgroundAlpha);
        g2.fillRoundRect(mg.motionH.lastMousePosition.x, mg.motionH.lastMousePosition.y, 200, 250, 15, 15);
        //OUTLINE
        setRarityColor(g2, invSlot);
        g2.setStroke(width2);
        g2.drawRoundRect(mg.motionH.lastMousePosition.x + 3, mg.motionH.lastMousePosition.y + 3, 194, 244, 15, 15);
        g2.setStroke(width1);
        //NAME
        setRarityColor(g2, invSlot);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 23));
        g2.drawString(invSlot.item.name, mg.motionH.lastMousePosition.x + 10, mg.motionH.lastMousePosition.y + 35);
        g2.setColor(darkBackground);
        //STATS

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20));
        g2.drawString("INT: " + invSlot.item.INT, mg.motionH.lastMousePosition.x + 10, mg.motionH.lastMousePosition.y + 65);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20));
        g2.drawString("VIT: " + invSlot.item.VIT, mg.motionH.lastMousePosition.x + 10, mg.motionH.lastMousePosition.y + 85);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20));
        g2.drawString("REG: " + invSlot.item.REG, mg.motionH.lastMousePosition.x + 60, mg.motionH.lastMousePosition.y + 65);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20));
        g2.drawString("SPD: " + invSlot.item.SPD, mg.motionH.lastMousePosition.x + 60, mg.motionH.lastMousePosition.y + 85);

        //EFFECTS


        //DESCRIPTION
        g2.setFont(g2.getFont().deriveFont(Font.ITALIC, 16));
        stringY = mg.motionH.lastMousePosition.y + 150;
        for (String string : invSlot.item.description.split("\n")) {
            g2.drawString(string, mg.motionH.lastMousePosition.x + 7, stringY += g2.getFontMetrics().getHeight());
        }
        g2.setFont(mg.ui.maruMonica);
        //ID
        setRarityColor(g2, invSlot);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN + Font.ITALIC, 16));
        g2.drawString("ID: " + String.format("%04d", invSlot.item.i_id) + invSlot.item.type, mg.motionH.lastMousePosition.x + 145, mg.motionH.lastMousePosition.y + 240);
    }

    private void setRarityColor(Graphics2D g2, InventorySlot slot) {
        g2.setColor(darkBackground);
        if (slot.item.rarity == 1) {
            g2.setColor(normalColor);
        } else if (slot.item.rarity == 2) {
            g2.setColor(rareColor);
        } else if (slot.item.rarity == 3) {
            g2.setColor(epicColor);
        } else if (slot.item.rarity == 4) {
            g2.setColor(legendaryColor);
        }
    }

    public int drawDragAndDrop(Graphics2D g2) {
        if (grabbedItem != null) {
            g2.drawImage(grabbedItem.icon, mg.motionH.lastMousePosition.x - SLOT_SIZE / 2, mg.motionH.lastMousePosition.y - SLOT_SIZE / 2, SLOT_SIZE, SLOT_SIZE, null);
        }
        if (grabbedItem == null && mg.mouseH.mouse1Pressed) {
            if (mg.showChar) {
                for (InventorySlot invSlot : char_Slots) {
                    if (invSlot.boundBox.contains(mg.motionH.lastMousePosition) && invSlot.item != null) {
                        mg.player.updateEquippedItems();
                        invSlot.grabbed = true;
                        grabbedSlot = invSlot;
                        grabbedItem = invSlot.item;
                        return 1;
                    }
                }
            }
            if (mg.showBag) {
                for (InventorySlot bagSlot : bag_Slots) {
                    if (bagSlot.boundBox.contains(mg.motionH.lastMousePosition) && bagSlot.item != null) {
                        bagSlot.grabbed = true;
                        grabbedSlot = bagSlot;
                        grabbedItem = bagSlot.item;
                        return 1;
                    }
                }
            }
        }
        if (grabbedItem != null && !mg.mouseH.mouse1Pressed) {
            if (mg.showChar) {
                for (InventorySlot invSlot : char_Slots) {
                    if (invSlot.boundBox.contains(mg.motionH.lastMousePosition) && invSlot != grabbedSlot) {
                        invSlot.item = (grabbedItem);
                        mg.player.updateEquippedItems();
                        grabbedSlot.item = null;
                        grabbedSlot.grabbed = false;
                        grabbedItem = null;
                        grabbedSlot = null;
                        return 1;
                    }
                }
            }
            if (mg.showBag) {
                for (InventorySlot bagSlot : bag_Slots) {
                    if (bagSlot.boundBox.contains(mg.motionH.lastMousePosition) && bagSlot != grabbedSlot) {
                        bagSlot.item = grabbedItem;
                        grabbedSlot.item = null;
                        grabbedSlot.grabbed = false;
                        grabbedItem = null;
                        grabbedSlot = null;
                        mg.player.updateEquippedItems();
                        return 1;
                    }
                }
            }
            grabbedSlot.grabbed = false;
            grabbedItem = null;
            grabbedSlot = null;
        }
        return 0;
    }

    public void interactWithWindows() {
        if (mg.mouseH.mouse1Pressed && charPanelMover.contains(mg.motionH.lastMousePosition)) {
            charPanelX += mg.motionH.lastMousePosition.x - previousMousePosition.x;
            charPanelY += mg.motionH.lastMousePosition.y - previousMousePosition.y;
        } else if (mg.mouseH.mouse1Pressed && bagPanelMover.contains(mg.motionH.lastMousePosition)) {
            bagPanelX += mg.motionH.lastMousePosition.x - previousMousePosition.x;
            bagPanelY += mg.motionH.lastMousePosition.y - previousMousePosition.y;
        }
        if (mg.mouseH.mouse1Pressed && charPanelCloser.contains(mg.motionH.lastMousePosition)) {
            mg.showChar = false;
            hideCharCollision();
        }
        if (mg.mouseH.mouse1Pressed && bagPanelCloser.contains(mg.motionH.lastMousePosition)) {
            mg.showBag = false;
            hideBagCollision();
        }
        previousMousePosition = mg.motionH.lastMousePosition;
    }

    private void drawCharacterBackground(Graphics2D g2, int startX, int startY) {
        //inventory background
        //big background
        wholeCharWindow.x = startX - 65;
        wholeCharWindow.y = startY - 65;
        g2.setColor(lightBackgroundAlpha);
        g2.fillRoundRect(startX - 50, startY - 65, 500, 650, 35, 35);
        //outline
        g2.setColor(darkBackground);
        g2.setStroke(width5);
        g2.drawRoundRect(startX - 50 + 5, startY - 65 + 5, 500 - 10, 650 - 10, 30, 30);
        g2.setStroke(width1);
        //window mover
        g2.setColor(darkBackground);
        g2.fillRoundRect(startX - 50, startY - 65 + 2, 500, 30, 15, 15);
        charPanelMover.x = startX - 50;
        charPanelMover.y = startY - 65 + 2 - 10;
        //window close button
        g2.setColor(Color.red);
        g2.drawRoundRect(startX + 500 - 50 - 30, startY - 65 + 2, 30, 30, 5, 5);
        charPanelCloser.x = startX - 50 + 500 - 30;
        charPanelCloser.y = startY - 65 + 2;
        //character image outline
        g2.setColor(darkBackground);
        g2.drawRoundRect(100 + startX, startY + 90, 200, 250, 25, 25);
        //player image
        g2.drawImage(mg.player.entityImage1, startX + 120, startY + 150, 150, 150, null);
        //Stats Text
        g2.setColor(darkBackground);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30f));
        g2.drawString("Mage: Level" + mg.player.level, 120 + startX, 40 + startY);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 15f));
        g2.drawString("Health:" + mg.player.maxHealth, startX + 110, startY + 420);
    }

    private void drawCharacterSlots(Graphics2D g2, int startX, int startY) {
        //Character Slots
        g2.setStroke(width2);
        for (int i = 0; i <= 3; i++) {
            char_Slots[i].boundBox.x = 40 + startX;
            char_Slots[i].boundBox.y = (i * 50 + 130 + startY);
            char_Slots[i].drawSlot(g2, 40 + startX, (i * 50 + 130 + startY));
            if (char_Slots[i].item != null && char_Slots[i].grabbed) {
            } else if (char_Slots[i].item != null) {
                char_Slots[i].drawIcon(g2, 40 + startX, ((i * 50) + 130 + startY), SLOT_SIZE);
            }
        }
        for (int i = 4; i <= 7; i++) {
            char_Slots[i].boundBox.x = 40 + 270 + startX;
            char_Slots[i].boundBox.y = (((i - 4) * 50) + 130 + startY);
            char_Slots[i].drawSlot(g2, 40 + 270 + startX, (((i - 4) * 50) + 130 + startY));
            if (char_Slots[i].item != null && char_Slots[i].grabbed) {
            } else if (char_Slots[i].item != null) {
                char_Slots[i].drawIcon(g2, 40 + 270 + startX, ((i - 4) * 50) + 130 + startY, SLOT_SIZE);
            }
        }
        for (int i = 8; i <= 9; i++) {
            char_Slots[i].boundBox.x = ((i - 8) * 50) + 140 + startX;
            char_Slots[i].boundBox.y = 110 + 240 + startY;
            char_Slots[i].drawSlot(g2, ((i - 8) * 50) + 140 + startX, 110 + 240 + startY);
            if (char_Slots[i].item != null && char_Slots[i].grabbed) {
            } else if (char_Slots[i].item != null) {
                char_Slots[i].drawIcon(g2, ((i - 8) * 50) + 140 + startX, 110 + 240 + startY, SLOT_SIZE);
            }
        }

    }

    private void drawBagBackground(Graphics2D g2, int startX, int startY) {
        wholeBagWindow.x = startX;
        wholeBagWindow.y = startY;
        //big background
        g2.setColor(lightBackgroundAlpha);
        g2.fillRoundRect(startX, startY, 365, 410, 15, 15);
        //outline
        g2.setStroke(width5);
        g2.setColor(darkBackground);
        g2.drawRoundRect(startX + 5, startY + 5, 355, 400, 25, 25);
        g2.setStroke(width2);
        //feature pane
        g2.setColor(darkBackground);
        g2.drawRoundRect(startX + 5, startY + 31, 355, 20, 15, 15);
        //window mover
        g2.setColor(darkBackground);
        g2.fillRoundRect(startX, startY, 365, 30, 15, 15);
        bagPanelMover.x = startX;
        bagPanelMover.y = startY - 10;
        //window close button
        g2.setColor(Color.red);
        g2.fillRoundRect(startX + 365 - 30, startY, 30, 30, 15, 15);
        bagPanelCloser.x = startX + 365 - 30;
        bagPanelCloser.y = startY;
    }

    private void drawBagSlots(Graphics2D g2, int startX, int startY) {
        g2.setColor(darkBackground);
        for (int i = 0; i < bag_Slots.length; i++) {
            if (i <= 6) {
                bag_Slots[i].boundBox.x = i * 50 + startX + 10;
                bag_Slots[i].boundBox.y = 60 + startY;
                bag_Slots[i].drawSlot(g2, i * 50 + startX + 10, 60 + startY);
                if (bag_Slots[i].item != null && bag_Slots[i].grabbed) {
                } else if (bag_Slots[i].item != null) {
                    bag_Slots[i].drawIcon(g2, i * 50 + startX + 10, 60 + startY, SLOT_SIZE);
                }
            } else if (i <= 13) {
                bag_Slots[i].boundBox.x = (i - 7) * 50 + startX + 10;
                bag_Slots[i].boundBox.y = 110 + startY;
                bag_Slots[i].drawSlot(g2, (i - 7) * 50 + startX + 10, 110 + startY);
                if (bag_Slots[i].item != null && bag_Slots[i].grabbed) {
                } else if (bag_Slots[i].item != null) {
                    bag_Slots[i].drawIcon(g2, (i - 7) * 50 + startX + 10, 110 + startY, SLOT_SIZE);
                }
            } else if (i <= 19) {
                bag_Slots[i].boundBox.x = (i - 14) * 50 + startX + 10;
                bag_Slots[i].boundBox.y = 160 + startY;
                bag_Slots[i].drawSlot(g2, (i - 14) * 50 + startX + 10, 160 + startY);
                if (bag_Slots[i].item != null && bag_Slots[i].grabbed) {
                } else if (bag_Slots[i].item != null) {
                    bag_Slots[i].drawIcon(g2, (i - 14) * 50 + startX + 10, 160 + startY, SLOT_SIZE);
                }
            }
        }
    }

    public void hideCharCollision() {
        wholeCharWindow.x = -1000;
        wholeCharWindow.y = -1000;

    }

    public void hideBagCollision() {
        wholeBagWindow.x = -1000;
        wholeBagWindow.y = -1000;
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
            char_Slots[i] = new InventorySlot(null, 40 + 260, (i * 50) + 130 + 200);
        }
        for (int i = 4; i <= 7; i++) {
            char_Slots[i] = new InventorySlot(null, 40 + 270 + 260, ((i - 4) * 50) + 130 + 200);
        }
        for (int i = 8; i <= 9; i++) {
            char_Slots[i] = new InventorySlot(null, ((i - 8) * 50) + 140 + 260, 110 + 240 + 200);
        }
    }

    private void createBagSlots() {
        for (int i = 0; i <= 9; i++) {
            bag_Slots[i] = new InventorySlot(null, (i * 50) + 40 + 770, 130 + 200);
        }
        for (int i = 10; i < 15; i++) {
            bag_Slots[i] = new InventorySlot(null, ((i - 10) * 50) + 40 + 770, 130 + 200 + 50);

        }
    }
}