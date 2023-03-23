package gameworld.world.objects.drops;

import gameworld.player.Player;
import gameworld.world.objects.DROP;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.system.enums.Zone;

public class DRP_EtherMark extends DROP {
    private final Image etherMark = new Image(getClass().getResourceAsStream("/resources/ui/ether_mark.png"));
    public int amount;

    public DRP_EtherMark(int x, int y, int amount, Zone zone) {
        this.zone = zone;
        this.amount = amount;
        this.size = 30;
        this.worldPos.x = x + 5;
        this.worldPos.y = y + 5;
    }

    /**
     * @param gc
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(etherMark, worldPos.x - Player.worldX + Player.screenX, worldPos.y - Player.worldY + Player.screenY);
    }

    /**
     *
     */
    @Override
    public void update() {

    }
}
