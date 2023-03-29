package gameworld.world.objects.items;

import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ITEM {
    public int i_id;
    public int cost;
    public String name;
    public int rarity;
    public String stats;
    public char type;
    public float weapon_damage;
    /* H - Head slot
       C - chest
       P - pants
       B - boots

       A - amulet
       R - ring
       R - ring
       T - relic / talisman

       W - one hand
       2 - two hand
       O - offhand

       G - Bag
     */
    public String imagePath;
    public int quality;
    public int durability = 100;
    public int level;
    public float[] effects = new float[Player.effectsSizeRollable];

    /*
        INT intellect + max mana / more damage on abilities
        VIT vitality + max health / more health regen /
        WIS wisdom mana regen + mana regeneration
        -Luck: Affects the likelihood of critical strikes and the chance of certain events occurring
        -Agility: Affects the character's movement speed, evasion, and accuracy
        -Charisma: Affects the character's ability to influence the behavior of non-player characters
        -Strength: Affects the character's physical damage output and the amount of weight they can carry
        Endurance:  and resist status effects / mental strain / environment effects resist /
        -Focus: longer dots, higher dots damage, longer buffs

        INT - Int
        WIS - Wis
        VIT - Vit
        AGI - Agi
        LUC - Luc
        CHA - Cha
        END - End
        STR - Str
        FOC - Foc

     */
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
    public String description;
    public Image icon;

    public ITEM(int i_id, String name, int rarity, char type, String imagePath, String description, String stats, String effect) {
        this.i_id = i_id;
        this.name = name;
        this.rarity = rarity;
        getEffects(effect);
        this.type = type;
        this.imagePath = imagePath;
        this.description = description;
        this.stats = stats;
        this.level = 1;
        this.icon = setup(imagePath);
        getStats();
        getWeaponDamage();
    }

    ITEM() {

    }

    public ITEM(int i_id, String name, int rarity, char type, String imagePath, String description, String stats, int quality, int level, float[] effects) {
        this(i_id, name, rarity, type, imagePath, "", stats, "");
        this.effects = effects;
        this.level = level;
        this.description = description;
        this.icon = setup(imagePath);
        this.quality = quality;
        this.cost = level * 25 + rarity * 25;
        applyMultipliers();
    }


    public void drawIcon(GraphicsContext gc, int x, int y, int slotSize) {
        gc.drawImage(icon, x, y, slotSize, slotSize);
    }

    private void getWeaponDamage() {
        switch (type) {
            case 'W', 'O' -> weapon_damage = (float) ((quality / 100.0f) * Math.sqrt(level + level / 2.0f)) * Math.min(4, rarity);
            case '2' -> weapon_damage = ((float) ((quality / 100.0f) * Math.sqrt(level + level / 2.0f)) * Math.min(4, rarity)) * 2;
        }
    }

    private void getStats() {
        if (stats == null) {
            return;
        }
        Pattern p;
        Matcher m;
        if (stats.contains("INT")) {
            p = Pattern.compile("INT(-?[0-9]+)");
            m = p.matcher(stats);
            while (m.find()) {
                intellect = Integer.parseInt(m.group(1));
            }
        }
        if (stats.contains("VIT")) {
            p = Pattern.compile("VIT(-?[0-9]+)");
            m = p.matcher(stats);
            while (m.find()) {
                vitality = Integer.parseInt(m.group(1));
            }
        }
        if (stats.contains("WIS")) {
            p = Pattern.compile("WIS(-?[0-9]+)");
            m = p.matcher(stats);
            while (m.find()) {
                wisdom = Integer.parseInt(m.group(1));
            }
        }
        if (stats.contains("AGI")) {
            p = Pattern.compile("AGI(-?[0-9]+)");
            m = p.matcher(stats);
            while (m.find()) {
                agility = Integer.parseInt(m.group(1));
            }
        }
        if (stats.contains("LUC")) {
            p = Pattern.compile("LUC(-?[0-9]+)");
            m = p.matcher(stats);
            while (m.find()) {
                luck = Integer.parseInt(m.group(1));
            }
        }
        if (stats.contains("CHA")) {
            p = Pattern.compile("CHA(-?[0-9]+)");
            m = p.matcher(stats);
            while (m.find()) {
                charisma = Integer.parseInt(m.group(1));
            }
        }
        if (stats.contains("END")) {
            p = Pattern.compile("END(-?[0-9]+)");
            m = p.matcher(stats);
            while (m.find()) {
                endurance = Integer.parseInt(m.group(1));
            }
        }
        if (stats.contains("STR")) {
            p = Pattern.compile("STR(-?[0-9]+)");
            m = p.matcher(stats);
            while (m.find()) {
                strength = Integer.parseInt(m.group(1));
            }
        }
        if (stats.contains("FOC")) {
            p = Pattern.compile("FOC(-?[0-9]+)");
            m = p.matcher(stats);
            while (m.find()) {
                focus = Integer.parseInt(m.group(1));
            }
        }
        if (stats.contains("ARM")) {
            p = Pattern.compile("ARM(-?[0-9]+)");
            m = p.matcher(stats);
            while (m.find()) {
                armour = Integer.parseInt(m.group(1));
            }
        }
        /*
         INT - Int
        WIS - Wis
        VIT - Vit
        AGI - Agi
        LUC - Luc
        CHA - Cha
        END - End
        STR - Str
        FOC - Foc
         */
    }

    public void getEffects(String effect) {
        if (effect == null) {
            return;
        }
        Pattern p;
        Matcher m;
        p = Pattern.compile("\\[(\\d+)]([\\d,.]+)");
        m = p.matcher(effect);
        while (m.find()) {
            effects[Integer.parseInt(m.group(1))] = Float.parseFloat(m.group(2));
        }
    }

    private void applyMultipliers() {
        if (rarity <= 4) {
            if (quality == 100) {
                vitality = (int) ((vitality + vitality / 10.0f) * Math.sqrt(level) * rarity);
                intellect = (int) ((intellect + intellect / 10.0f) * Math.sqrt(level) * rarity);
                wisdom = (int) ((wisdom + wisdom / 10.0f) * Math.sqrt(level) * rarity);
                endurance = (int) (((endurance + endurance / 10.0f)) * Math.sqrt(level) * rarity);
                strength = (int) (((strength + strength / 10.0f)) * Math.sqrt(level) * rarity);
                focus = (int) (((focus + focus / 10.0f)) * Math.sqrt(level) * rarity);
                agility = (int) ((((agility + agility / 10.0f)) * Math.sqrt(level) * rarity));
                luck = (int) (((luck + luck / 10.0f)) * Math.sqrt(level) * rarity);
                charisma = (int) (((charisma + charisma / 10.0f)) * Math.sqrt(level) * rarity);
            } else {
                vitality = (int) ((((quality / 100.0f) * vitality)) * Math.sqrt(level) * rarity);
                intellect = (int) ((((quality / 100.0f) * intellect)) * Math.sqrt(level) * rarity);
                wisdom = (int) ((((quality / 100.0f) * wisdom)) * Math.sqrt(level) * rarity);
                endurance = (int) ((((quality / 100.0f) * endurance)) * Math.sqrt(level) * rarity);
                strength = (int) ((((quality / 100.0f) * strength)) * Math.sqrt(level) * rarity);
                focus = (int) ((((quality / 100.0f) * focus)) * Math.sqrt(level) * rarity);
                agility = (int) ((((quality / 100.0f) * agility)) * Math.sqrt(level) * rarity);
                luck = (int) ((((quality / 100.0f) * luck)) * Math.sqrt(level) * rarity);
                charisma = (int) ((((quality / 100.0f) * charisma)) * Math.sqrt(level) * rarity);
            }
        } else if (rarity == 5 || rarity == 10) {
            if (quality == 100) {
                vitality = (int) ((vitality + vitality / 10.0f) * Math.sqrt(level) * 4);
                intellect = (int) ((intellect + intellect / 10.0f) * Math.sqrt(level) * 4);
                wisdom = (int) ((wisdom + wisdom / 10.0f) * Math.sqrt(level) * 4);
                endurance = (int) (((endurance + endurance / 10.0f)) * Math.sqrt(level) * 4);
                strength = (int) (((strength + strength / 10.0f)) * Math.sqrt(level) * 4);
                focus = (int) (((focus + focus / 10.0f)) * Math.sqrt(level) * 4);
                agility = (int) ((((agility + agility / 10.0f)) * Math.sqrt(level) * 4));
                luck = (int) (((luck + luck / 10.0f)) * Math.sqrt(level) * 4);
                charisma = (int) (((charisma + charisma / 10.0f)) * Math.sqrt(level) * 4);
            } else {
                vitality = (int) ((((quality / 100.0f) * vitality)) * Math.sqrt(level) * 4);
                intellect = (int) ((((quality / 100.0f) * intellect)) * Math.sqrt(level) * 4);
                wisdom = (int) ((((quality / 100.0f) * wisdom)) * Math.sqrt(level) * 4);
                endurance = (int) ((((quality / 100.0f) * endurance)) * Math.sqrt(level) * 4);
                strength = (int) ((((quality / 100.0f) * strength)) * Math.sqrt(level) * 4);
                focus = (int) ((((quality / 100.0f) * focus)) * Math.sqrt(level) * 4);
                agility = (int) ((((quality / 100.0f) * agility)) * Math.sqrt(level) * 4);
                luck = (int) ((((quality / 100.0f) * luck)) * Math.sqrt(level) * 4);
                charisma = (int) ((((quality / 100.0f) * charisma)) * Math.sqrt(level) * 4);
            }
        }
    }

    public void applyRarity() {
        if (rarity <= 4) {
            intellect = (intellect * rarity);
            vitality = (vitality * rarity);
            wisdom = (wisdom * rarity);
            agility = (agility * rarity);
            luck = luck * rarity;
            charisma = charisma * rarity;
            endurance = endurance * rarity;
            strength = strength * rarity;
            focus = focus * rarity;
        }
        if (rarity == 5 || rarity == 10) {
            intellect = (intellect * 4);
            vitality = (vitality * 4);
            wisdom = (wisdom * 4);
            agility = (agility * 4);
            luck = luck * 4;
            charisma = charisma * 4;
            endurance = endurance * 4;
            strength = strength * 4;
            focus = focus * 4;
        }
    }

    public void applyLevel() {
        intellect = (int) (intellect * Math.sqrt(level) * rarity);
        vitality = (int) (vitality * Math.sqrt(level) * rarity);
        wisdom = (int) (wisdom * Math.sqrt(level) * rarity);
        agility = (int) (agility * Math.sqrt(level) * rarity);
        luck = (int) (luck * Math.sqrt(level) * rarity);
        charisma = (int) (charisma * Math.sqrt(level) * rarity);
        endurance = (int) (endurance * Math.sqrt(level) * rarity);
        strength = (int) (strength * Math.sqrt(level) * rarity);
        focus = (int) (focus * Math.sqrt(level) * rarity);
    }


    public void rollQuality(int quality) {
        this.quality = quality;
        if (quality == 100) {
            vitality = (int) ((vitality + vitality / 10.0f));
            intellect = (int) ((intellect + intellect / 10.0f));
            wisdom = (int) ((wisdom + wisdom / 10.0f));
            endurance = (int) ((endurance + endurance / 10.0f));
            strength = (int) ((strength + strength / 10.0f));
            focus = (int) ((focus + focus / 10.0f));
            agility = (int) ((agility + agility / 10.0f));
            luck = (int) ((luck + luck / 10.0f));
            charisma = (int) ((charisma + charisma / 10.0f));
        } else {
            vitality = (int) (quality / 100.0f) * vitality;
            intellect = (int) (quality / 100.0f) * intellect;
            wisdom = (int) (quality / 100.0f) * wisdom;
            endurance = (int) (quality / 100.0f) * endurance;
            strength = (int) (quality / 100.0f) * strength;
            focus = (int) (quality / 100.0f) * focus;
            agility = (int) (quality / 100.0f) * agility;
            luck = (int) (quality / 100.0f) * luck;
            charisma = (int) (quality / 100.0f) * charisma;
        }
    }

    public void rollQuality() {
        this.quality = 71 + (int) (Math.random() * 30);
        if (quality == 100) {
            vitality = (int) ((vitality + vitality / 10.0f));
            intellect = (int) ((intellect + intellect / 10.0f));
            wisdom = (int) ((wisdom + wisdom / 10.0f));
            endurance = (int) ((endurance + endurance / 10.0f));
            strength = (int) ((strength + strength / 10.0f));
            focus = (int) ((focus + focus / 10.0f));
            agility = (int) ((agility + agility / 10.0f));
            luck = (int) ((luck + luck / 10.0f));
            charisma = (int) ((charisma + charisma / 10.0f));
        } else {
            vitality = (int) (quality / 100.0f) * vitality;
            intellect = (int) (quality / 100.0f) * intellect;
            wisdom = (int) (quality / 100.0f) * wisdom;
            endurance = (int) (quality / 100.0f) * endurance;
            strength = (int) (quality / 100.0f) * strength;
            focus = (int) (quality / 100.0f) * focus;
            agility = (int) (quality / 100.0f) * agility;
            luck = (int) (quality / 100.0f) * luck;
            charisma = (int) (quality / 100.0f) * charisma;
        }
    }


    public Image setup(String imagePath) {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
    }
}
