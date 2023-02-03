package main.system.ui.talentpane;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

public abstract class TALENT {

    public final int i_id;
    public final String name;
    public String description;

    public String imagePath;
    public Image icon;

    public TALENT(int i_id, String name, String imagePath, String description, int sizeX, int sizeY) {
        this.i_id = i_id;
        this.name = name;
        this.icon = setup(imagePath, sizeX, sizeY);
        this.description = description;
    }


    public void drawIcon(GraphicsContext gc, int x, int y, int slotSize) {
        gc.drawImage(icon, x, y, slotSize, slotSize);
    }


    private Image setup(String imagePath, int sizeX, int sizeY) {
        return new Image((Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/talents/TalentIcons/" + imagePath))));
    }
}

