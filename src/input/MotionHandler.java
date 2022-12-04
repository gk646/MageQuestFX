package input;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Can detect mouse dragging and moving
 *
 * @Methods mouseDragged();
 * mouseMoved();
 */
public class MotionHandler implements MouseMotionListener {
    /**
     * Mouse position as Point
     */
    public Point mousePosition;

    public MotionHandler() {
    }

    /**
     * @param e the event to be processed
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        mousePosition = new Point(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
