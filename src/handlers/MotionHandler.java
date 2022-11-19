package handlers;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MotionHandler implements MouseMotionListener {
    public Point mousePosition;
    public boolean mousePressed;

    public MotionHandler() {
        mousePressed = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mousePosition = new Point(e.getX(), e.getY());
        mousePressed=true;

    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
