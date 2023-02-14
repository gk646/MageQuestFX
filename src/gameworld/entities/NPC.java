package gameworld.entities;

import java.awt.Point;

abstract public class NPC extends ENTITY {
    public boolean blockInteraction;
    public boolean show_dialog;
    public Point playerTalkLocation;
    public int dialogHideDelay;

    public Point goalTile;
    public int stuckCounter;
}
