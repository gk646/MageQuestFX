package main.system.ui.talentpane;

import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TALENT {
    public final int i_id;
    final String name;
    final String description;

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
        this.imagePath = imagePath;
        if (effect != null) {
            getEffect(effect);
            getStats(effect);
        }
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

    private void getStats(String effect) {
        Pattern p;
        Matcher m;
        if (effect.contains("INT")) {
            p = Pattern.compile("INT(-?[0-9]+)");
            m = p.matcher(effect);
            while (m.find()) {
                intellect = Integer.parseInt(m.group(1));
            }
        }
        if (effect.contains("VIT")) {
            p = Pattern.compile("VIT(-?[0-9]+)");
            m = p.matcher(effect);
            while (m.find()) {
                vitality = Integer.parseInt(m.group(1));
            }
        }
        if (effect.contains("WIS")) {
            p = Pattern.compile("WIS(-?[0-9]+)");
            m = p.matcher(effect);
            while (m.find()) {
                wisdom = Integer.parseInt(m.group(1));
            }
        }
        if (effect.contains("AGI")) {
            p = Pattern.compile("AGI(-?[0-9]+)");
            m = p.matcher(effect);
            while (m.find()) {
                agility = Integer.parseInt(m.group(1));
            }
        }
        if (effect.contains("LUC")) {
            p = Pattern.compile("LUC(-?[0-9]+)");
            m = p.matcher(effect);
            while (m.find()) {
                luck = Integer.parseInt(m.group(1));
            }
        }
        if (effect.contains("CHA")) {
            p = Pattern.compile("CHA(-?[0-9]+)");
            m = p.matcher(effect);
            while (m.find()) {
                charisma = Integer.parseInt(m.group(1));
            }
        }
        if (effect.contains("END")) {
            p = Pattern.compile("END(-?[0-9]+)");
            m = p.matcher(effect);
            while (m.find()) {
                endurance = Integer.parseInt(m.group(1));
            }
        }
        if (effect.contains("STR")) {
            p = Pattern.compile("STR(-?[0-9]+)");
            m = p.matcher(effect);
            while (m.find()) {
                strength = Integer.parseInt(m.group(1));
            }
        }
        if (effect.contains("FOC")) {
            p = Pattern.compile("FOC(-?[0-9]+)");
            m = p.matcher(effect);
            while (m.find()) {
                focus = Integer.parseInt(m.group(1));
            }
        }
        if (effect.contains("ARM")) {
            p = Pattern.compile("ARM(-?[0-9]+)");
            m = p.matcher(effect);
            while (m.find()) {
                armour = Integer.parseInt(m.group(1));
            }
        }
    }

    public void drawIcon(GraphicsContext gc, int x, int y) {
        if (imagePath.contains("dark_magic")) {
            gc.drawImage(icon, x + 2, y);
        } else {
            gc.drawImage(icon, x, y);
        }
    }


    private Image setup(String imagePath) {
        return new Image((Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/talents/TalentIcons/" + imagePath))));
    }
}

