package gameworld.entities;

import java.awt.Point;

abstract public class NPC extends ENTITY {
    public boolean blockInteraction;
    public boolean show_dialog;
    public Point playerTalkLocation;
    public int dialogHideDelay;
    public int checkpointIndex;
    public Point[] checkPoints;

    public Point goalTile;
    public int stuckCounter;

    public void moveTo(int goalX, int goalY, Point... checkpoints) {
        if (checkpoints != null && checkpoints.length > 0) {

            if (checkpointIndex < checkpoints.length) {

                if (checkpoints[checkpointIndex].equals(activeTile)) {
                    checkpointIndex++;
                    System.out.println(checkpointIndex);
                } else {
                    moveToTileSuperVised(checkpoints[checkpointIndex].x, checkpoints[checkpointIndex].y);
                }
            } else if (!(new Point(goalX, goalY).equals(activeTile))) {

                moveToTileSuperVised(goalX, goalY);
            } else if (new Point(goalX, goalY).equals(activeTile)) {
                onPath = false;
                checkpointIndex = 0;
            } else {

            }
        } else if (!new Point(goalX, goalY).equals(activeTile)) {
            moveToTileSuperVised(goalX, goalY);
        } else {
            onPath = false;

            checkpointIndex = 0;
        }
    }

    @Override
    public void update() {
        activeTile.x = (int) ((worldX) / 48);
        activeTile.y = (int) ((worldY) / 48);
    }

    protected boolean collidingWithPlayer() {
        return activeTile.x == mg.playerX && activeTile.y == mg.playerY;
    }
}
