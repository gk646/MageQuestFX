package gameworld.entities;

import gameworld.dialogue.Dialog;
import javafx.scene.image.Image;

import java.awt.Point;

abstract public class NPC extends ENTITY {
    public Dialog dial;

    public Image player2;
    public boolean show_dialog;
    public Point playerTalkLocation;
    public int dialog_counter;
    public Point goalTile;
}
