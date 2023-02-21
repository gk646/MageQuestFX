package gameworld.entities.damage.effects;

import main.MainGame;
import main.system.rendering.WorldRender;

public class TileBasedEffects {
    public static int activeTile = 0;
    private int previousTile = -1;
    MainGame mg;

    public TileBasedEffects(MainGame mg) {
        this.mg = mg;
    }


    public void update() {
        activeTile = WorldRender.worldData[mg.playerX][mg.playerY];
        if (activeTile != previousTile) {
            previousTile = activeTile;
            checkForTileEffects();
        }
    }

    private void checkForTileEffects() {
        if (activeTile == 225) {
            mg.player.health -= (mg.player.maxHealth * 0.4f);
        }
    }
}
