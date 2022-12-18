package main.system.ui;

import gameworld.Item;
import gameworld.items.ARM_chest01;
import main.MainGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InventoryPanel extends JPanel {
    private static final int SLOT_SIZE = 32;
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
        drawCharacterSlots();
        for (int i = 0; i < NUM_SLOTS; i++) {
            int x = i * SLOT_SIZE;
            int y = 0;
            if (i == selectedSlot) {
                g2.setColor(Color.LIGHT_GRAY);
                g2.fillRect(x, y, SLOT_SIZE + 1, SLOT_SIZE + 1);
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

    public void drawCharacterSlots() {


    }
}