package main.system.ui;

import gameworld.Item;
import main.MainGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InventoryPanel extends JPanel {
    private static final int SLOT_SIZE = 45;
    private static final int BAG_SLOTS = 15;
    private static final int CHAR_SLOTS = 10;
    public final Color lightBackground = new Color(192, 203, 220), lightBackgroundAlpha = new Color(0xCAC0CBDC, true), darkBackground = new Color(90, 105, 136);
    private final InventorySlot[] bag_Slots;
    public MainGame mg;
    MouseMotionListener motionL;
    MouseListener mouseL;
    public InventorySlot[] char_Slots;
    Item grabbedItem;
    InventorySlot grabbedSlot;

    public InventoryPanel(MainGame mainGame) {
        this.setSize(new Dimension(1000, 750));
        setBackground(new Color(192, 203, 220, 255));
        this.mg = mainGame;
        this.motionL = mg.motionHandler;
        this.mouseL = mg.mouseHandler;
        addMouseListener(mouseL);
        addMouseMotionListener(motionL);
        setVisible(true);
        bag_Slots = new InventorySlot[BAG_SLOTS];
        char_Slots = new InventorySlot[CHAR_SLOTS];
        this.grabbedItem = null;
        createCharSlots(260, 200);
        createBagSlots(770, 200);
        char_Slots[1].setItem(new ARM_head02());
    }

    //todo add moveable windows and recenter button
    public void draw(Graphics2D g2) {
        g2.setFont(mg.ui.maruMonica);
        drawDragandDrop();
        drawBackground(g2, 260, 200);
        drawAllSlots(g2);
    }

    public int drawDragandDrop() {
        if (grabbedItem == null && mg.mouseHandler.mouse1Pressed) {
            for (InventorySlot invSlot : char_Slots) {
                if (invSlot.boundBox.contains(mg.mouseHandler.mouse1Position) && invSlot.item != null) {
                    invSlot.grabbed = true;
                    grabbedSlot = invSlot;
                    grabbedItem = invSlot.item;
                    return 1;
                }
            }
            for (InventorySlot bagSlot : bag_Slots) {
                if (bagSlot.boundBox.contains(mg.mouseHandler.mouse1Position) && bagSlot.item != null) {
                    bagSlot.grabbed = true;
                    grabbedSlot = bagSlot;
                    grabbedItem = bagSlot.item;
                    return 1;
                }
            }
        }
        if (grabbedItem != null && !mg.mouseHandler.mouse1Pressed) {
            for (InventorySlot invSlot : char_Slots) {
                if (invSlot.boundBox.contains(mg.motionHandler.lastMousePosition) && invSlot != grabbedSlot) {
                    invSlot.setItem(grabbedItem);
                    grabbedSlot.item = null;
                    grabbedItem = null;
                    grabbedSlot = null;
                    return 1;
                }
            }
            for (InventorySlot bagSlot : bag_Slots) {
                if (bagSlot.boundBox.contains(mg.motionHandler.lastMousePosition) && bagSlot != grabbedSlot) {
                    bagSlot.setItem(grabbedItem);
                    grabbedSlot.item = null;
                    grabbedItem = null;
                    grabbedSlot = null;
                    return 1;
                }
            }
            grabbedSlot.grabbed = false;
            grabbedItem = null;
        }
        if (mg.mouseHandler.mouse2Pressed) {
            grabbedSlot.grabbed = false;
            grabbedItem = null;
        }
        return 0;
    }


    public void drawBackground(Graphics2D g2, int startX, int startY) {
        //inventory background
        g2.setColor(new Color(192, 203, 220, 220));
        g2.fillRoundRect(210, 135, 1500, 800, 24, 25);
        //bag slots
        g2.setColor(darkBackground);
        g2.drawRoundRect(startX + 500, startY, 500, 250, 25, 25);
        g2.setColor(darkBackground);
        g2.drawRoundRect(startX, startY, 400, 550, 30, 30);
        g2.setColor(darkBackground);
        g2.drawRoundRect(100 + startX, startY + 90, 200, 250, 25, 25);
        g2.drawImage(mg.player.entityImage1, startX + 120, startY + 150, 150, 150, null);
        //Stats Text
        g2.setColor(darkBackground);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30f));
        g2.drawString("Mage: Level" + mg.player.level, 120 + startX, 40 + startY);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 15f));
        g2.drawString("Health:" + mg.player.maxHealth, startX + 110, startY + 420);
    }

    public void drawAllSlots(Graphics2D g2) {
        //Character Slots
        for (InventorySlot invSlot : char_Slots) {
            invSlot.drawSlot(g2);
            if (invSlot.item != null && invSlot.grabbed) {
                invSlot.drawIcon(g2, mg.motionHandler.lastMousePosition.x - SLOT_SIZE / 2, mg.motionHandler.lastMousePosition.y - SLOT_SIZE / 2, SLOT_SIZE);
            } else if (invSlot.item != null) {
                invSlot.drawIcon(g2, invSlot.xCo, invSlot.yCo, SLOT_SIZE);
            }
        }
        //Bag Slots
        for (InventorySlot bagSlot : bag_Slots) {
            bagSlot.drawSlot(g2);
            if (bagSlot.item != null && bagSlot.grabbed) {
                bagSlot.drawIcon(g2, mg.motionHandler.lastMousePosition.x - SLOT_SIZE / 2, mg.motionHandler.lastMousePosition.y - SLOT_SIZE / 2, SLOT_SIZE);
            } else if (bagSlot.item != null) {
                bagSlot.drawIcon(g2, bagSlot.xCo, bagSlot.yCo, SLOT_SIZE);
            }
        }
    }

    private void createCharSlots(int startX, int startY) {
        for (int i = 0; i <= 3; i++) {
            char_Slots[i] = new InventorySlot(null, 40 + startX, (i * 50) + 130 + startY);
        }
        for (int i = 4; i <= 7; i++) {
            char_Slots[i] = new InventorySlot(null, 40 + 270 + startX, ((i - 4) * 50) + 130 + startY);
        }
        for (int i = 8; i <= 9; i++) {
            char_Slots[i] = new InventorySlot(null, ((i - 8) * 50) + 140 + startX, 110 + 240 + startY);
        }
    }

    private void createBagSlots(int startX, int startY) {
        for (int i = 0; i <= 9; i++) {
            bag_Slots[i] = new InventorySlot(null, (i * 50) + 40 + startX, 130 + startY);
        }
        for (int i = 10; i < 15; i++) {
            bag_Slots[i] = new InventorySlot(null, ((i - 10) * 50) + 40 + startX, 130 + startY + 50);

        }
    }
}