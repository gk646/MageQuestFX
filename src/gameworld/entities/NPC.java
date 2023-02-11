package gameworld.entities;

import gameworld.dialogue.Dialog;
import javafx.scene.image.Image;

import java.awt.Point;

abstract public class NPC extends ENTITY {
    public Dialog dial;

    protected Image player2;
    public boolean show_dialog;
    protected Point playerTalkLocation;
    protected int dialog_counter;
    public Point goalTile;
}
