package input;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class MouseHandler implements MouseListener {
    public boolean mouse1Pressed, mouse2Pressed;
    public Point mouse1Position, mouse2Position;
    public final MotionHandler motionHandler;

    public MouseHandler(MotionHandler motionHandler) {
        this.motionHandler = motionHandler;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == 1) {
            mouse1Pressed = true;
            mouse1Position = new Point(e.getX(), e.getY());
        }
        if (e.getButton() == 3) {
            mouse2Pressed = true;
            mouse2Position = new Point(e.getX(), e.getYOnScreen());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == 1) {
            mouse1Pressed = false;
            motionHandler.mousePosition = null;
        }
        if (e.getButton() == 3) {
            mouse2Pressed = false;
            motionHandler.mousePosition = null;
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
