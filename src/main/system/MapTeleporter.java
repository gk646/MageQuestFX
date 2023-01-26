package main.system;

import main.MainGame;

public class MapTeleporter {
    // WORLD CODES
    // 0 = Tutorial
    // 1 = Grass Lands
    // 2 = City 1
    // 3 =
    //


    private final MainGame mg;

    public MapTeleporter(MainGame mg) {
        this.mg = mg;
    }

    public void checkTeleports() {
        if (mg.wControl.currentWorld == 0) {
            if (mg.playerX == 43 && mg.playerY == 34) {
                mg.wControl.load_city1(5, 5);
                mg.player.spawnLevel = 1;
            }
        }
        if (mg.wControl.currentWorld == 1) {
            if (mg.playerX == 499 && mg.playerY == 499) {
                mg.wControl.load_city1(5, 5);
            }
        }
        if (mg.wControl.currentWorld == 2) {
            if (mg.playerX == 32 && mg.playerY == 0 ||
                    mg.playerX == 33 && mg.playerY == 0 ||
                    mg.playerX == 34 && mg.playerY == 0 ||
                    mg.playerX == 35 && mg.playerY == 0 ||
                    mg.playerX == 36 && mg.playerY == 0 ||
                    mg.playerX == 37 && mg.playerY == 0) {
                mg.wControl.load_OverWorldMap(495, 495);
            }
        }
    }

    public void loadSpawnLevel() {
        if (mg.player.spawnLevel == 0) {
            mg.wControl.load_tutorial(4, 4);
        } else if (mg.player.spawnLevel == 1) {
            mg.wControl.load_city1(11, 11);
        }
    }
}
