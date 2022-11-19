package projectile.projectiles;

import handlers.MotionHandler;
import handlers.MouseHandler;
import main.Display;
import projectile.Projectile;

public class AltFire extends Projectile{

    public AltFire(Display display, MotionHandler motionHandler, MouseHandler mouseHandler) {
        super(display, motionHandler, mouseHandler);
    
        this.projectileSpeed = 11;
        this.mousePosition = display.motionHandler.mousePosition;
        this.playerPosition = display.player.getPlayerPosition();
        
        

    

    }
}
