package gameworld.entities.npcs.generic;

import gameworld.entities.NPC;
import gameworld.world.WorldController;

abstract public class NPC_Generic extends NPC {
    public boolean despawn;
    public boolean finished;
    int counter1, counter2;

    protected void scriptMovement() {
        if (WorldController.currentScript != null) {
            if (goalTile == null) {
                for (int i = 0; i < WorldController.currentScript.entranceTaken.length; i++) {
                    if (!WorldController.currentScript.entranceTaken[i]) {
                        WorldController.currentScript.entranceTaken[i] = true;
                        this.goalTile = WorldController.currentScript.houseEntrances[i];
                    }
                }
            } else if (!goalTile.equals(activeTile)) {
                onPath = true;
            } else {
                if (!finished) {
                    for (int i = 0; i < WorldController.currentScript.houseEntrances.length; i++) {
                        if (goalTile.equals(WorldController.currentScript.houseEntrances[i])) {
                            WorldController.currentScript.entranceTaken[i] = false;
                            finished = true;
                        }
                    }
                }
                counter1++;
                if (counter1 >= 300) {
                    despawn = true;
                }
            }
        }
    }
}
