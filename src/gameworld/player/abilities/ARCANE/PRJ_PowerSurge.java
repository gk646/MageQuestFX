package gameworld.player.abilities.ARCANE;

import gameworld.player.PROJECTILE;
import gameworld.player.Player;
import gameworld.player.ProjectilePreloader;
import javafx.scene.canvas.GraphicsContext;

public class PRJ_PowerSurge extends PROJECTILE {

    PRJ_PowerSurge() {
        resource = ProjectilePreloader.powerSurge;
    }

    /**
     * @param gc
     */
    @Override
    public void draw(GraphicsContext gc) {
        switch (spriteCounter % 120 / 15) {
            case 0 -> gc.drawImage(resource.images1.get(0), Player.screenX - 12, Player.screenY - 12);
            case 1 -> gc.drawImage(resource.images1.get(1), Player.screenX - 12, Player.screenY - 12);
            case 2 -> gc.drawImage(resource.images1.get(2), Player.screenX - 12, Player.screenY - 12);
            case 3 -> gc.drawImage(resource.images1.get(3), Player.screenX - 12, Player.screenY - 12);
            case 4 -> gc.drawImage(resource.images1.get(4), Player.screenX - 12, Player.screenY - 12);
            case 5 -> gc.drawImage(resource.images1.get(5), Player.screenX - 12, Player.screenY - 12);
            case 6 -> gc.drawImage(resource.images1.get(6), Player.screenX - 12, Player.screenY - 12);
            case 7 -> gc.drawImage(resource.images1.get(7), Player.screenX - 12, Player.screenY - 12);
        }
        spriteCounter++;
    }

    /**
     *
     */
    @Override
    public void update() {

    }
}
