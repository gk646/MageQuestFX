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
        switch (spellName) {
            case "Solar Flare" -> mg.skillPanel.addSKill(new SKL_SolarFlare(mg));
            case "Energy Sphere" -> mg.skillPanel.addSKill(new SKL_EnergySphere(mg));
            case "Thunder Strike" -> mg.skillPanel.addSKill(new SKL_ThunderStrike(mg));
            case "Ring Salvo" -> mg.skillPanel.addSKill(new SKL_FireBurst(mg));
            case "Frost Nova" -> mg.skillPanel.addSKill(new SKL_FrostNova(mg));
            case "Thunder Splash" -> mg.skillPanel.addSKill(new SKL_ThunderSplash(mg));
            case "Void Eruption" -> mg.skillPanel.addSKill(new SKL_VoidEruption(mg));
            case "Void Field" -> mg.skillPanel.addSKill(new SKL_VoidField(mg));
            case "Lightning Strike" -> mg.skillPanel.addSKill(new SKL_Lightning(mg));
            case "Regenerative Aura" -> mg.skillPanel.addSKill(new SKL_RegenAura(mg));
            case "Auto Shot" -> mg.skillPanel.addSKill(new SKL_AutoShot(mg));
            case "Energy Sphere 2" -> mg.skillPanel.addSKill(new SKL_EnergySphere_2(mg));
            case "Filler" -> mg.skillPanel.addSKill(new SKL_Filler(mg));
            case "Ice Lance" -> mg.skillPanel.addSKill(new SKL_IceLance(mg));
            case "Magic Shield" -> mg.skillPanel.addSKill(new SKL_MagicShield(mg));
            case "Mana Shield" -> mg.skillPanel.addSKill(new SKL_ManaShield(mg));
            case "Pyro Blast" -> mg.skillPanel.addSKill(new SKL_PyroBlast(mg));
            case "Power Surge" -> mg.skillPanel.addSKill(new SKL_PowerSurge(mg));
            default -> System.out.println("Unknown spell: " + spellName);
        }
    }
}
