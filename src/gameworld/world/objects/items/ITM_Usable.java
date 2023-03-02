package gameworld.world.objects.items;

import main.MainGame;

public class ITM_Usable extends ITEM {


    ITM_Usable(String name, int rarity, String description, String imagePath) {
        this.name = name;
        this.rarity = rarity;
        this.description = description;
    }

    public void activate(MainGame mg) {

    }
}
