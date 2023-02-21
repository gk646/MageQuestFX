package gameworld.entities.damage.effects;

import main.MainGame;
import main.system.rendering.WorldRender;

public class TileBasedEffects {
    public static int activeTile;
    MainGame mg;

    public TileBasedEffects(MainGame mg) {
        this.mg = mg;
    }


    public void update() {
        System.out.println(activeTile);
        activeTile = WorldRender.worldData[mg.playerX][mg.playerY];
        if (activeTile == 225) {
            mg.player.health = (mg.player.health * 0.6f);
        }
    }
}
