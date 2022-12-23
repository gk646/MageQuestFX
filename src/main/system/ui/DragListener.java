package main.system.ui;

import javax.swing.event.MouseInputAdapter;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;

class DragListener extends MouseInputAdapter {
    private Point location;
    private MouseEvent pressed;

    public void mousePressed(MouseEvent me) {
        pressed = me;
    }

    public void mouseDragged(MouseEvent me) {
        Component component = me.getComponent();
        location = component.getLocation(location);
        int x = Math.max(-1000, Math.min(location.x - pressed.getX() + me.getX(), 1000));
        int y = Math.max(-1000, Math.min(location.y - pressed.getY() + me.getY(), 800));
        component.setLocation(x, y);

    }
}
