package main.system.ui;


import input.KeyHandler;
import main.MainGame;

import javax.swing.*;
import java.awt.*;


public class SkilltreeWindow extends JPanel {

    public SkilltreeWindow(MainGame mainGame) {
        JPanel panel1 = new Skilltree(mainGame).skillwindow;
        panel1.setBackground(new Color(90, 105, 136, 255));
        panel1.setPreferredSize(new Dimension(2500, 2500));
        panel1.setLocation(1000, 1000);
        panel1.setVisible(true);
        KeyHandler keyHandler = new KeyHandler(mainGame);
        DragListener drag = new DragListener();
        panel1.addMouseListener(drag);
        panel1.addKeyListener(keyHandler);
        panel1.addMouseMotionListener(drag);
        setSize(1500, 800);
        add(panel1);
        setBackground(new Color(90, 105, 136, 255));
        setLocation(210, 135);
        setVisible(true);


    }

}
