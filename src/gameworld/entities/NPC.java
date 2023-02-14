package gameworld.entities;

import gameworld.quest.Dialog;

import java.awt.Point;

abstract public class NPC extends ENTITY {
    public Dialog dial;
    public boolean blockInteraction;
    public boolean show_dialog;
    protected Point playerTalkLocation;
    protected int dialog_counter;
    public Point goalTile;
    public int stuckCounter;
}
