package handlers;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.plaf.TreeUI;

public class MouseHandler implements MouseListener {
    public boolean mouse1Pressed, mouse2Pressed;
    public Point mouse1Position, mouse2Position;

    public MouseHandler() {

    }


    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouse1Pressed(MouseEvent e) {
        if (e.getButton() == 1) {
            mouse1Pressed = true;
            mouse1Position = new Point(e.getX(), e.getY());
        }if(e.getButton()==2){
            mouse2Pressed = true;
            mouse2Position = new Point(e.getX(),e.getYOnScreen());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton()==1){
        mouse1Pressed = false;
        }
        if(e.getButton()==2){
            mouse2Pressed = false;
        }
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
