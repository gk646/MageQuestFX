package input;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class MouseHandler implements MouseListener {
    public boolean mouse1Pressed, mouse2Pressed;
    public Point mouse1Position, mouse2Position, mouse1Released;
    public final MotionHandler motionHandler;
    public static MouseEvent pressed;

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
            mouse1Position = e.getLocationOnScreen();

        }
        if (e.getButton() == 3) {
            mouse2Pressed = true;
            mouse2Position = e.getLocationOnScreen();
        }
        pressed = e;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == 1) {
            mouse1Pressed = false;
            motionHandler.mousePosition = null;
            mouse1Released = e.getLocationOnScreen();
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
