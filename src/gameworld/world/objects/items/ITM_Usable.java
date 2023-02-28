package gameworld.world.objects.items;

public class ITM_Usable extends ITEM {


    ITM_Usable(String name, int rarity, String description, String imagePath) {
        this.name = name;
        this.rarity = rarity;
        this.description = description;
        this.icon = setup(imagePath);
    }

    public void activate() {
        if (name.equals("Spell Book: Void Field")) {

        }
    }
}
