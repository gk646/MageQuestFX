package main.system.ui.talentpane.talents;

import main.system.ui.talentpane.TALENT;

public class TAL_INT1 extends TALENT {

    public TAL_INT1(int i_id, String name, String imagePath, String description, int sizeX, int sizeY) {
        super(i_id, name, imagePath, description, sizeX, sizeY);
        this.description = "Increases your Intelligence by 5 points";
    }
}
