package gameworld.world.objects.items;

import gameworld.player.Player;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.database.SQLite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DroppedItemTest {

    MainGame mg;

    @BeforeEach
    public void setup() {
        Canvas canvas = new Canvas();
        Group group = new Group();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        this.mg = new MainGame(1, 1, gc, group.getScene());
        mg.sqLite = new SQLite(mg);
        mg.sqLite.readItemsOnly();
        mg.player = new Player(mg);
    }

    @Test
    void ItemDropChance() {
        int nullitems = 0;
        int rarity1 = 0;
        int ratity2 = 0;
        int rarity3 = 0;
        int rarity4 = 0;
        int rarity5 = 0;
        int fail = 0;
        for (int i = 0; i < 10_00; i++) {
            DroppedItem drop = new DroppedItem(mg, 1, 1, 1);
            if (drop.item == null) {
                nullitems++;
            } else if (drop.item.rarity == 1) {
                rarity1++;
            } else if (drop.item.rarity == 2) {
                ratity2++;
            } else if (drop.item.rarity == 3) {
                rarity3++;
            } else if (drop.item.rarity == 4) {
                rarity4++;
            } else if (drop.item.rarity == 5) {
                rarity5++;
            } else {
                fail++;
            }
        }
        System.out.println("The number of nullitems is: " + nullitems);
        System.out.println("The number of items with rarity 1 is: " + rarity1);
        System.out.println("The number of items with rarity 2 is: " + ratity2);
        System.out.println("The number of items with rarity 3 is: " + rarity3);
        System.out.println("The number of items with rarity 4 is: " + rarity4);
        System.out.println("The number of items with rarity 5 is: " + rarity5);
        System.out.println("The number of failed attempts is: " + fail);
        System.out.println("-----------------------------------------------------------");
    }

    @Test
    void playerStats() {
        mg.player.level = 60;
        mg.player.intellect = 456;
        mg.player.vitality = 456;
        mg.player.agility = 60;
        mg.player.wisdom = 400;
        mg.player.luck = 56;
        mg.player.charisma = 25;
        mg.player.endurance = 56;
        mg.player.strength = 56;
        mg.player.focus = 156;
        calcPlayerStats();
        System.out.println("Max Health: " + mg.player.maxHealth);
        System.out.println("Max Mana: " + mg.player.maxMana);
        System.out.println("Mana Regeneration: " + mg.player.manaRegeneration * 60);
        System.out.println("Health Regeneration: " + mg.player.healthRegeneration * 60);
        System.out.println("Player Movement Speed: " + mg.player.playerMovementSpeed);
        System.out.println("Crit Chance: " + mg.player.critChance);
        System.out.println("Speech Skill: " + mg.player.speechSkill);
        System.out.println("Resist Chance: " + mg.player.resistChance);
        System.out.println("Carry Weight: " + mg.player.carryWeight);
        System.out.println("Buff Length Multiplier: " + mg.player.buffLengthMultiplier);
        System.out.println("Dot Damage Multiplier: " + mg.player.dotDamageMultiplier);
        System.out.println("Dot Length Multiplier: " + mg.player.dotLengthMultiplier);
        System.out.println("-----------------------------------------------------------");
    }

    private void calcPlayerStats() {
        mg.player.maxHealth = (int) ((9.0f + mg.player.vitality * 1.5f + mg.player.endurance / 2.0f) * Math.sqrt(mg.player.level));
        mg.player.maxMana = (int) ((19.0f + mg.player.intellect * 3 + mg.player.wisdom / 2.0f) * Math.sqrt(mg.player.level));
        mg.player.manaRegeneration = ((Math.round((5.0f + (mg.player.level / 10.0f) + (mg.player.wisdom * 5 + mg.player.intellect) * (1.0f - (mg.player.level / 65.0f))) * 100.0f) / 100.0f) / 60);
        mg.player.healthRegeneration = (float) (((0.05f + Math.sqrt(mg.player.endurance * 2 + mg.player.vitality)) * Math.sqrt(mg.player.level)) / 80);
        mg.player.playerMovementSpeed = (float) (4 + 0.2 * mg.player.agility);
        mg.player.critChance = Math.min((Math.round((5.0f + (mg.player.level / 10.0f) + (mg.player.luck * 2) * (1.0f - (mg.player.level / 63.0f))) * 100.0f) / 100.0f), 75);
        mg.player.speechSkill = Math.round((5.0f + (mg.player.level / 10.0f) + (1.5f * mg.player.charisma) * (1.0f - (mg.player.level / 64.0f))) * 100.0f) / 100.0f;
        mg.player.resistChance = Math.min((Math.round((5.0f + (mg.player.level / 10.0f) + (mg.player.endurance * 1.0f) * (1.0f - (mg.player.level / 64.0f))) * 100.0f) / 100.0f), 50);
        mg.player.carryWeight = Math.round((50.0f + (mg.player.level) + (mg.player.strength * 2.0f) * (1.0f - (mg.player.level / 100.0f))) * 100.0f) / 100.0f;
        mg.player.buffLengthMultiplier = Math.round(((mg.player.focus) * (1.0f - (mg.player.level / 80.0f))) * 100.0f) / 100.0f;
        mg.player.dotDamageMultiplier = Math.round(((mg.player.focus) * (1.0f - (mg.player.level / 70.0f))) * 100.0f) / 100.0f;
        mg.player.dotLengthMultiplier = mg.player.dotDamageMultiplier;
    }
}

