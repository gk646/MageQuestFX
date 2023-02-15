package gameworld.world.objects.items;

import gameworld.player.Player;
import gameworld.world.WorldController;
import gameworld.world.objects.drops.DRP_DroppedItem;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.database.SQLite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.Random;

class DRPDroppedItemTest {

    MainGame mg;

    @BeforeEach
    public void setup() {
        Canvas canvas = new Canvas();
        Group group = new Group();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        this.mg = new MainGame(1, 1, gc);
        mg.sqLite = new SQLite(mg);
        mg.sqLite.readItemsOnly();
        mg.player = new Player(mg);

        SecureRandom secureRandom = new SecureRandom();
        long seed = secureRandom.nextLong();
        mg.random = new Random(seed);
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
        for (int i = 0; i < 10_000; i++) {
            DRP_DroppedItem drop = new DRP_DroppedItem(mg, 1, 1, 1, WorldController.currentWorld);
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
    void playerStatsLow() {
        mg.player.level = 12;
        mg.player.intellect = 34;
        mg.player.vitality = 34;
        mg.player.agility = 34;
        mg.player.wisdom = 34;
        mg.player.luck = 34;
        mg.player.charisma = 25;
        mg.player.endurance = 34;
        mg.player.strength = 34;
        mg.player.focus = 34;
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

    @Test
    void playerStatsHigh() {

        mg.player.level = 60;
        mg.player.intellect = 400;
        mg.player.vitality = 400;
        mg.player.agility = 155;
        mg.player.wisdom = 344;
        mg.player.luck = 50;
        mg.player.charisma = 25;
        mg.player.endurance = 100;
        mg.player.strength = 76;
        mg.player.focus = 250;

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
        mg.player.maxHealth = (int) ((9.0f + mg.player.vitality * 1.5f + mg.player.endurance / 2.0f) * Math.sqrt(Math.min(mg.player.level, 50)));
        mg.player.maxMana = (int) ((19.0f + mg.player.intellect * 3 + mg.player.wisdom) * Math.sqrt(Math.min(mg.player.level, 50)));
        mg.player.manaRegeneration = Math.round(1 + ((mg.player.wisdom * 2 + mg.player.intellect) / Math.sqrt(Math.max(10, mg.player.level + 5))) / 60.0f * 100.0f) / 100.0f;
        mg.player.healthRegeneration = Math.round(2 + ((mg.player.endurance * 2 + mg.player.vitality) / Math.sqrt(Math.max(10, mg.player.level + 10))) / 110.0f * 100.0f) / 100.0f;
        mg.player.playerMovementSpeed = Math.round(((4.0f + (mg.player.agility * 0.4 / (float) mg.player.level))) * 100.0f) / 100.0f;
        mg.player.critChance = Math.min((Math.round(((5.0f + ((mg.player.luck * 2) / Math.sqrt(Math.max(10, mg.player.level))) * 100.0f) / 100.0f))), 75);
        mg.player.speechSkill = Math.round((5.0f + (mg.player.level / 10.0f) + (1.5f * mg.player.charisma) * (1.0f - (mg.player.level / 64.0f))) * 100.0f) / 100.0f;
        mg.player.resistChance = Math.min((Math.round((5.0f + (mg.player.level / 10.0f) + (mg.player.endurance * 1.0f) * (1.0f - (mg.player.level / 64.0f))) * 100.0f) / 100.0f), 50);
        mg.player.carryWeight = Math.round((30.0f + (mg.player.level) + (mg.player.strength) * (1.0f - (mg.player.level / 90.0f))) * 100.0f) / 100.0f;
        mg.player.buffLengthMultiplier = Math.round(((mg.player.focus * 1.5) * (1.0f - (mg.player.level / 83.0f))) * 100.0f) / 100.0f;
        mg.player.dotDamageMultiplier = Math.round(((mg.player.focus * 1.5) * (1.0f - (mg.player.level / 75.0f))) * 100.0f) / 100.0f;
        mg.player.dotLengthMultiplier = (mg.player.dotDamageMultiplier * 1.25f);
    }
}

