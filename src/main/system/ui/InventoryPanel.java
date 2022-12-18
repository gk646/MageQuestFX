package main.system.ui;

import gameworld.Item;
import gameworld.items.ARM_chest01;
import main.MainGame;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InventoryPanel extends JPanel {
    private static final int SLOT_SIZE = 45;
    private static final int NUM_SLOTS = 10;

    private final Item[] items;
    private final int selectedSlot;
    private final int draggedSlot;
    public MainGame mg;
    MouseMotionListener motionL;
    MouseListener mouseL;
    JButton jButton;
    private int dragOffsetX;
    private int dragOffsetY;
    public final Color lightBackground = new Color(192, 203, 220), lightBackgroundAlpha = new Color(0xCAC0CBDC, true), darkBackground = new Color(90, 105, 136);


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
        items = new Item[NUM_SLOTS];
        selectedSlot = -1;
        draggedSlot = -1;
        items[0] = new ARM_chest01();
        jButton = new JButton();
        add(jButton);

    }


    public void draw(Graphics2D g2) {
        g2.setFont(mg.ui.maruMonica);
        drawInventoryWindow(g2);
        drawCharacterSlots(g2, 260, 200);
        for (int i = 0; i < NUM_SLOTS; i++) {
            int x = i * SLOT_SIZE + 250;
            int y = 250;
            if (i == selectedSlot) {
                g2.setColor(Color.LIGHT_GRAY);
                g2.fillRect(x, y, SLOT_SIZE, SLOT_SIZE);
            }
            if (items[i] != null) {
                items[i].drawIcon(g2, x, y, SLOT_SIZE);
            }
        }
        if (draggedSlot != -1) {
            int x = draggedSlot * SLOT_SIZE;
            int y = 0;
            Item item = items[draggedSlot];
            item.drawIcon(g2, x + dragOffsetX, y + dragOffsetY, SLOT_SIZE);
        }
    }

    public void drawCharacterSlots(Graphics2D g2, int startx, int starty) {
        g2.setColor(darkBackground);
        g2.drawRoundRect(startx, starty, 400, 550, 30, 30);
        g2.setColor(darkBackground);
        g2.drawRoundRect(100 + startx, starty + 90, 200, 250, 25, 25);
        g2.drawImage(mg.player.entityImage1, startx + 120, starty + 150, 150, 150, null);
        //LEFT
        g2.drawRoundRect(40 + startx, 130 + starty, SLOT_SIZE, SLOT_SIZE, 20, 20);
        g2.drawRoundRect(40 + startx, 130 + 50 + starty, SLOT_SIZE, SLOT_SIZE, 20, 20);
        g2.drawRoundRect(40 + startx, 130 + 100 + starty, SLOT_SIZE, SLOT_SIZE, 20, 20);
        g2.drawRoundRect(40 + startx, 130 + 150 + starty, SLOT_SIZE, SLOT_SIZE, 20, 20);
        //RIGHT
        g2.drawRoundRect(40 + 270 + startx, 130 + starty, SLOT_SIZE, SLOT_SIZE, 20, 20);
        g2.drawRoundRect(40 + 270 + startx, 130 + 50 + starty, SLOT_SIZE, SLOT_SIZE, 20, 20);
        g2.drawRoundRect(40 + 270 + startx, 130 + 100 + starty, SLOT_SIZE, SLOT_SIZE, 20, 20);
        g2.drawRoundRect(40 + 270 + startx, 130 + 150 + starty, SLOT_SIZE, SLOT_SIZE, 20, 20);
        //Below
        g2.drawRoundRect(140 + startx, 110 + 250 + starty, SLOT_SIZE, SLOT_SIZE, 20, 20);
        g2.drawRoundRect(50 + 140 + startx, 110 + 250 + starty, SLOT_SIZE, SLOT_SIZE, 20, 20);

        //Stats Text
        g2.setColor(darkBackground);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30f));
        g2.drawString("Mage: Level" + mg.player.level, 110 + startx, 40 + starty);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 15f));
        g2.drawString("Health:" + mg.player.maxHealth, startx + 110, starty + 420);

    }

    public void drawInventoryWindow(Graphics2D g2) {
        g2.setColor(new Color(192, 203, 220, 220));
        g2.fillRoundRect(210, 135, 1500, 800, 50, 50);
    }

    public void drawBags() {

    }
}