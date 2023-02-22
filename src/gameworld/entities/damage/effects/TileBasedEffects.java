package gameworld.entities.damage.effects;

import gameworld.world.WorldController;
import gameworld.world.objects.drops.DRP_ChestItem;
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

    public void openChest() {
        if (activeTile == 137) {
            mg.WORLD_DROPS.add(new DRP_ChestItem(mg, mg.playerX * 48 + 24, mg.playerY * 48 + 24, WorldController.currentWorld, mg.player.level));
            mg.sound.playChestOpen();
        }
    }
}
