package gameworld.world.objects.items;

import gameworld.entities.damage.DamageType;
import gameworld.quest.Dialog;
import javafx.scene.image.Image;
import main.MainGame;
import main.system.ui.skillbar.skills.*;

import java.util.Objects;

public class ITM_SpellBook extends ITM_Usable {
    private final String spellName;

    public ITM_SpellBook(String name, int rarity, String description, String imagePath, DamageType type) {
        super(name, rarity, description, imagePath);
        if (type == DamageType.Fire) {
            icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/items/usables/fire.png")));
        } else if (type == DamageType.Arcane) {
            icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/items/usables/arcane.png")));
        } else if (type == DamageType.DarkMagic) {
            icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/items/usables/dark.png")));
        } else if (type == DamageType.Poison) {
            icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/items/usables/poison.png")));
        } else if (type == DamageType.Ice) {
            icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/items/usables/ice.png")));
        }
        this.rarity = 2;
        cost = 50;
        this.name = "Spell Book: " + name;
        spellName = name;
        this.type = 'X';
        this.description = Dialog.insertNewLine("A book containing knowledge about the" + type.toString() + "spell " + name + ". Press E to learn it!", 35);
    }

    @Override
    public void activate(MainGame mg) {
        if (spellName.equals("Solar Flare")) {
            mg.skillPanel.addSKill(new SKL_SolarFlare(mg));
        } else if (spellName.equals("Energy Sphere")) {
            mg.skillPanel.addSKill(new SKL_EnergySphere(mg));
        } else if (spellName.equals("Thunder Strike")) {
            mg.skillPanel.addSKill(new SKL_ThunderStrike(mg));
        } else if (spellName.equals("Ring Salvo")) {
            mg.skillPanel.addSKill(new SKL_RingSalvo(mg));
        } else if (spellName.equals("Frost Nova")) {
            mg.skillPanel.addSKill(new SKL_FrostNova(mg));
        } else if (spellName.equals("Thunder Splash")) {
            mg.skillPanel.addSKill(new SKL_ThunderSplash(mg));
        } else if (spellName.equals("Void Eruption")) {
            mg.skillPanel.addSKill(new SKL_VoidEruption(mg));
        } else if (spellName.equals("Void Field")) {
            mg.skillPanel.addSKill(new SKL_VoidField(mg));
        } else if (spellName.equals("Lightning Strike")) {
            mg.skillPanel.addSKill(new SKL_Lightning(mg));
        } else if (spellName.equals("Regenerative Aura")) {
            mg.skillPanel.addSKill(new SKL_RegenAura(mg));
        }
    }
}
