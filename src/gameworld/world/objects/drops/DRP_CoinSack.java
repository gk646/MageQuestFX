package gameworld.world.objects.drops;

import gameworld.player.Player;
import gameworld.world.objects.DROP;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.system.enums.Zone;

import java.util.Objects;

public class DRP_CoinSack extends DROP {
    private final Image etherMark = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/coin_sack.png")));
    public int amount;

    public DRP_CoinSack(int x, int y, int playerLevel, Zone zone) {
        this.zone = zone;
        this.amount = (int) (Math.random() * 15 * playerLevel);
        this.size = 30;
        this.worldPos.x = x + 5;
        this.worldPos.y = y + 5;
    }


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
