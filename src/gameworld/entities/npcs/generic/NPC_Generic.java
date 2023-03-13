package gameworld.entities.npcs.generic;

import gameworld.entities.NPC;
import gameworld.world.WorldController;
import main.system.rendering.WorldRender;

import java.awt.Point;

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
                        goalTile = WorldController.currentScript.houseEntrances[i];
                        return;
                    }
                }
            } else if (!goalTile.equals(activeTile)) {
                onPath = true;
                if (checkpointIndex == 0) {
                    counter1++;
                }
                if (counter1 >= 350) {

                }
            } else {
                if (!finished) {
                    for (int i = 0; i < WorldController.currentScript.houseEntrances.length; i++) {
                        if (goalTile.equals(WorldController.currentScript.houseEntrances[i])) {
                            WorldController.currentScript.entranceTaken[i] = false;
                            finished = true;
                            break;
                        }
                    }
                }
                counter2++;
                if (counter2 >= 300) {
                    despawn = true;
                }
            }
        }
    }

    private boolean getRandomCheckpoint(int num) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (num == i * 6 + j) {
                    if (!mg.wRender.tileStorage[WorldRender.worldData[activeTile.x + i - 3][activeTile.y + j - 3]].collision && WorldRender.worldData1[activeTile.x + i - 3][activeTile.y + j - 3] != -1 && !mg.wRender.tileStorage[WorldRender.worldData1[activeTile.x + i - 3][activeTile.y + j - 3]].collision) {
                        checkPoints = new Point[]{new Point(activeTile.x + i - 3, activeTile.y + j - 3)};
                        counter1 = 0;
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
