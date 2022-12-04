package main.system.ui;


import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SkilltreeWindow extends JFrame implements ActionListener {

    public SkilltreeWindow() {
        JPanel panel1 = new Skilltree().skillwindow;
        //Set the frame's size and make it visible
        panel1.setPreferredSize(new Dimension(2000, 2000));
        panel1.setBackground(Color.yellow);
        panel1.setVisible(true);
        setLayout(new FlowLayout());
        setUndecorated(true);
        setSize(1500, 800);
        this.add(panel1);

        DragListener drag = new DragListener();
        panel1.addMouseListener(drag);
        panel1.addMouseMotionListener(drag);
        setVisible(true);
        setLocationRelativeTo(null);

    }

    public static void main(String[] args) {
        new SkilltreeWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
