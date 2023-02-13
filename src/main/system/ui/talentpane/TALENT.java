package main.system.ui.talentpane;

import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TALENT {
    public final int i_id;
    private final String name;
    private final String description;

    public String imagePath;
    private final Image icon;
    public int[] effects = new int[Player.effectsSizeTotal];
    public int intellect;
    public int vitality;
    public int wisdom;
    public int agility;
    public int luck;
    public int charisma;
    public int endurance;
    public int strength;
    public int focus;
    public int armour;

    public TALENT(int i_id, String name, String imagePath, String description, String effect) {
        this.i_id = i_id;
        this.name = name;
        getEffect(effect);
        this.icon = setup(imagePath);
        this.description = description;
    }

    private void getEffect(String effect) {
        Pattern p;
        Matcher m;
        p = Pattern.compile("\\[(\\d+)](\\d+)");
        m = p.matcher(effect);
        while (m.find()) {
            effects[Integer.parseInt(m.group(1))] = Integer.parseInt(m.group(2));
        }
    }

    public void drawIcon(GraphicsContext gc, int x, int y) {
        gc.drawImage(icon, x, y);
    }


    private Image setup(String imagePath) {
        return new Image((Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/talents/TalentIcons/" + imagePath))));
    }
}

