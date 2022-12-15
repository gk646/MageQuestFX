package main.system.ui;


import input.KeyHandler;
import main.MainGame;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SkilltreeWindow extends JPanel implements ActionListener {

       public  SkilltreeWindow(MainGame mainGame){
        JPanel panel1 = new Skilltree().skillwindow;
        //Set the frame's size and make it visible
        panel1.setPreferredSize(new Dimension(2000, 2000));
        panel1.setBackground(Color.yellow);
        panel1.setVisible(true);

        setSize(1500, 800);
        this.add(panel1);
        KeyHandler keyHandler = new KeyHandler(mainGame);
        DragListener drag = new DragListener();
        panel1.addMouseListener(drag);
        panel1.addKeyListener(keyHandler);
        panel1.addMouseMotionListener(drag);
        setVisible(true);


}

        @Override
        public void actionPerformed(ActionEvent e) {

        }
}
