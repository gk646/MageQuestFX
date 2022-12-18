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
    private final int selectedSlot;
    private final int draggedSlot;
    private final InventorySlot[] bag_Slots;
    public MainGame mg;
    MouseMotionListener motionL;
    MouseListener mouseL;
    public InventorySlot[] char_Slots;
    Item grabbedItem;
    InventorySlot grabbedSlot;
    private int dragOffsetX;
    private int dragOffsetY;


    public InventoryPanel(MainGame mainGame) {
        this.setSize(new Dimension(1000, 750));
        setBackground(new Color(192, 203, 220, 255));
        //todo panel inside panel
        this.mg = mainGame;
        this.motionL = mg.motionHandler;
        this.mouseL = mg.mouseHandler;
        addMouseListener(mouseL);
        addMouseMotionListener(motionL);
        setVisible(true);

        bag_Slots = new InventorySlot[BAG_SLOTS];
        char_Slots = new InventorySlot[10];
        selectedSlot = -1;
        draggedSlot = -1;
        this.grabbedItem = null;
        createCharSlots(260, 200);
        char_Slots[1].setItem(new ARM_head02());
    }


    public void draw(Graphics2D g2) {
        g2.setFont(mg.ui.maruMonica);
        drawInventoryWindow(g2);
        drawDragandDrop();
        drawCharacterSlots(g2, 260, 200);
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
            grabbedSlot.grabbed = false;
            grabbedItem = null;
        }
        if (mg.mouseHandler.mouse2Pressed) {
            grabbedSlot.grabbed = false;
            grabbedItem = null;
        }
        return 0;
    }


    public void drawCharacterSlots(Graphics2D g2, int startX, int startY) {
        g2.setColor(darkBackground);
        g2.drawRoundRect(startX, startY, 400, 550, 30, 30);
        g2.setColor(darkBackground);
        g2.drawRoundRect(100 + startX, startY + 90, 200, 250, 25, 25);
        g2.drawImage(mg.player.entityImage1, startX + 120, startY + 150, 150, 150, null);
        for (InventorySlot invSlot : char_Slots) {
            invSlot.drawSlot(g2);
            if (invSlot.item != null && invSlot.grabbed) {
                invSlot.drawIcon(g2, mg.motionHandler.lastMousePosition.x - SLOT_SIZE / 2, mg.motionHandler.lastMousePosition.y - SLOT_SIZE / 2, SLOT_SIZE);
            } else if (invSlot.item != null) {
                invSlot.drawIcon(g2, invSlot.xCo, invSlot.yCo, SLOT_SIZE);
            }
        }
        /*for (int i = 0; i < CHAR_SLOTS; i++) {
            if (char_Slots[i].item != null) {
                System.out.println(i);
            }
        }

         */
        //Stats Text
        g2.setColor(darkBackground);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30f));
        g2.drawString("Mage: Level" + mg.player.level, 120 + startX, 40 + startY);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 15f));
        g2.drawString("Health:" + mg.player.maxHealth, startX + 110, startY + 420);
    }

    public void drawInventoryWindow(Graphics2D g2) {
        g2.setColor(new Color(192, 203, 220, 220));
        g2.fillRoundRect(210, 135, 1500, 800, 50, 50);
    }

    public void drawBag(Graphics2D g2) {
        for (int i = 0; i < BAG_SLOTS; i++) {
            int x = i * SLOT_SIZE + 250;
            int y = 250;
            if (i == selectedSlot) {
                g2.setColor(Color.LIGHT_GRAY);
                g2.fillRect(x, y, SLOT_SIZE, SLOT_SIZE);
            }
            if (char_Slots[i] != null) {
                char_Slots[i].drawIcon(g2, x, y, SLOT_SIZE);
            }
        }
        if (draggedSlot != -1) {
            int x = draggedSlot * SLOT_SIZE;
            int y = 0;
            // Item item = char_Slots[draggedSlot];
            // item.drawIcon(g2, x + dragOffsetX, y + dragOffsetY, SLOT_SIZE);
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
            char_Slots[i] = new InventorySlot(null, ((i - 8) * 50) + 40 + startX, 110 + 240 + startY);
        }
    }
}