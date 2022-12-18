package main.system.ui;

import gameworld.Item;
import gameworld.items.ARM_chest01;
import main.MainGame;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InventoryPanel extends JPanel {
    private static final int SLOT_SIZE = 45;
    private static final int BAG_SLOTS = 15;
    private static final int CHAR_SLOTS = 10;
    public final Color lightBackground = new Color(192, 203, 220), lightBackgroundAlpha = new Color(0xCAC0CBDC, true), darkBackground = new Color(90, 105, 136);
    private final int selectedSlot;
    private final int draggedSlot;
    private final Item[] bag_Slots;
    public MainGame mg;
    MouseMotionListener motionL;
    MouseListener mouseL;
    public Item[] char_Slots;
    private int dragOffsetX;
    private int dragOffsetY;


    public InventoryPanel(MainGame mainGame) {
        this.setSize(new Dimension(1000, 750));
        setBackground(new Color(192, 203, 220, 255));
        //todo panel inside panel
        addMouseListener(mouseL);
        addMouseMotionListener(motionL);
        setVisible(true);
        this.mg = mainGame;
        this.motionL = mg.motionHandler;
        this.mouseL = mg.mouseHandler;
        bag_Slots = new Item[BAG_SLOTS];
        char_Slots = new Item[10];
        selectedSlot = -1;
        draggedSlot = -1;
        char_Slots[1] = new ARM_chest01();

    }


    public void draw(Graphics2D g2) {
        g2.setFont(mg.ui.maruMonica);
        drawDragandDrop();
        drawInventoryWindow(g2);
        drawCharacterSlots(g2, 260, 200);

    }


    public void drawDragandDrop() {

    }

    public void drawCharacterSlots(Graphics2D g2, int startX, int startY) {
        g2.setColor(darkBackground);
        g2.drawRoundRect(startX, startY, 400, 550, 30, 30);
        g2.setColor(darkBackground);
        g2.drawRoundRect(100 + startX, startY + 90, 200, 250, 25, 25);
        g2.drawImage(mg.player.entityImage1, startX + 120, startY + 150, 150, 150, null);
        //LEFT
        g2.drawRoundRect(40 + startX, 130 + startY, SLOT_SIZE, SLOT_SIZE, 20, 20);
        g2.drawRoundRect(40 + startX, 130 + 50 + startY, SLOT_SIZE, SLOT_SIZE, 20, 20);
        g2.drawRoundRect(40 + startX, 130 + 100 + startY, SLOT_SIZE, SLOT_SIZE, 20, 20);
        g2.drawRoundRect(40 + startX, 130 + 150 + startY, SLOT_SIZE, SLOT_SIZE, 20, 20);
        //RIGHT
        g2.drawRoundRect(40 + 270 + startX, 130 + startY, SLOT_SIZE, SLOT_SIZE, 20, 20);
        g2.drawRoundRect(40 + 270 + startX, 130 + 50 + startY, SLOT_SIZE, SLOT_SIZE, 20, 20);
        g2.drawRoundRect(40 + 270 + startX, 130 + 100 + startY, SLOT_SIZE, SLOT_SIZE, 20, 20);
        g2.drawRoundRect(40 + 270 + startX, 130 + 150 + startY, SLOT_SIZE, SLOT_SIZE, 20, 20);
        //Below
        g2.drawRoundRect(150 + startX, 110 + 240 + startY, SLOT_SIZE, SLOT_SIZE, 20, 20);
        g2.drawRoundRect(50 + 150 + startX, 110 + 240 + startY, SLOT_SIZE, SLOT_SIZE, 20, 20);

        //Stats Text
        g2.setColor(darkBackground);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30f));
        g2.drawString("Mage: Level" + mg.player.level, 120 + startX, 40 + startY);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 15f));
        g2.drawString("Health:" + mg.player.maxHealth, startX + 110, startY + 420);
        //Icons
        drawCharIcons(g2, startX, startY);
    }

    public void drawCharIcons(Graphics2D g2, int startX, int startY) {
        //------------------------Left Side
        for (int i = 0; i <= 3; i++) {
            int x = startX + 40;
            int y = (i * (5 + SLOT_SIZE)) + startY + 130;
            /*
            if (i == selectedSlot) {
                g2.setColor(Color.LIGHT_GRAY);
                g2.fillRect(x, y, SLOT_SIZE, SLOT_SIZE);
            }
             */
            if (char_Slots[i] != null) {
                char_Slots[i].drawIcon(g2, x, y, SLOT_SIZE);
            }
        }
        //--------------------Right Side
        for (int i = 4; i <= 7; i++) {
            int x = 40 + 270 + startX;
            int y = ((i - 4) * (5 + SLOT_SIZE)) + 130 + startY;
            /*
            if (i == selectedSlot) {
                g2.setColor(Color.LIGHT_GRAY);
                g2.fillRect(x, y, SLOT_SIZE, SLOT_SIZE);
            }
             */
            if (char_Slots[i] != null) {
                char_Slots[i].drawIcon(g2, x, y, SLOT_SIZE);
            }
        }
        //-------------------Below
        for (int i = 8; i <= 9; i++) {
            int x = ((i - 8) * (SLOT_SIZE + 5)) + 150 + startX;
            int y = 110 + 240 + startY;
            /*
            if (i == selectedSlot) {
                g2.setColor(Color.LIGHT_GRAY);
                g2.fillRect(x, y, SLOT_SIZE, SLOT_SIZE);
            }
             */
            if (char_Slots[i] != null) {
                char_Slots[i].drawIcon(g2, x, y, SLOT_SIZE);
            }
        }
        if (draggedSlot != -1) {
            int x = draggedSlot * SLOT_SIZE;
            int y = 0;
            Item item = char_Slots[draggedSlot];
            item.drawIcon(g2, x + dragOffsetX, y + dragOffsetY, SLOT_SIZE);
        }

    }

    public void drawInventoryWindow(Graphics2D g2) {
        g2.setColor(new Color(192, 203, 220, 220));
        g2.fillRoundRect(210, 135, 1500, 800, 50, 50);
    }

    public void drawBags(Graphics2D g2) {
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
            Item item = char_Slots[draggedSlot];
            item.drawIcon(g2, x + dragOffsetX, y + dragOffsetY, SLOT_SIZE);
        }
    }
}