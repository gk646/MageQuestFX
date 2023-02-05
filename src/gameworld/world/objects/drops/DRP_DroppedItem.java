package gameworld.world.objects.drops;

import gameworld.player.Player;
import gameworld.world.objects.DROP;
import gameworld.world.objects.items.ITEM;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;

public class DRP_DroppedItem extends DROP {
    private final MainGame mg;
    public Image droppedIcon;

    /**
     * Rarity 1: 803/10000 (8%) <p>
     * Rarity 2: 1621/10000 (16%) <p>
     * Rarity 3: 359/10000 (3.6%) <p>
     * Rarity 4: 58/10000 (0.6%) <p>
     * Rarity 5: 18/10000 (0.2%) <p>
     * Nullitems: 7141/10000 (71.4%) <p>
     *
     * @param mg     MainGame
     * @param worldX DropLocation X
     * @param worldY DropLocation Y
     * @param level  Level of the dropped item
     */
    public DRP_DroppedItem(MainGame mg, int worldX, int worldY, int level) {
        this.mg = mg;
        this.size = 32;
        this.worldPos.x = worldX + 24;
        this.worldPos.y = worldY + 24;
        item = rollForItem(level);
        if (item != null) {
            droppedIcon = item.icon;
        }
    }

    public DRP_DroppedItem(MainGame mg, int worldX, int worldY, ITEM item) {
        this.mg = mg;
        this.size = 32;
        this.worldPos.x = worldX + 24;
        this.worldPos.y = worldY + 24;
        this.item = item;
        droppedIcon = item.icon;
    }

    public static ITEM cloneItem(ITEM item) {
        ITEM new_ITEM = new ITEM(item.i_id, item.name, item.rarity, item.type, item.imagePath, item.description, item.stats, item.quality, item.level);
        new_ITEM.icon = item.icon;
        return new_ITEM;
    }

    /**
     * @param item    the item to clone
     * @param quality the quality of the item to clone
     * @param level   the level of the item to clone
     * @return a new instance of a base item with the given quality and level
     */
    public static ITEM cloneItemWithLevelQuality(ITEM item, int quality, int level) {
        ITEM new_ITEM = new ITEM(item.i_id, item.name, item.rarity, item.type, item.imagePath, item.description, item.stats, quality, level);
        new_ITEM.icon = item.icon;
        return new_ITEM;
    }

    @Override
    public void draw(GraphicsContext g2) {
        g2.drawImage(droppedIcon, worldPos.x - Player.worldX + Player.screenX, worldPos.y - Player.worldY + Player.screenY, 32, 32);
    }

    /**
     *
     */
    @Override
    public void update() {

    }

    private ITEM rollForItem(int level) {
        ITEM item;
        while (true) {
            item = goThroughArrays(mg.random.nextInt(0, 10));
            assert item != null;
            item.level = level;
            item.rollQuality();
            if (item.rarity <= 5) {
                if (mg.random.nextInt(0, 40) == 25) {
                    if (item.rarity == 1) {
                        return cloneItem(item);
                    }
                    if (item.rarity == 2 && mg.random.nextInt(0, 3) == 1) {
                        return cloneItem(item);
                    }
                    if (item.rarity == 3 && mg.random.nextInt(0, 7) == 4) {
                        return cloneItem(item);
                    }
                    if (item.rarity == 4 && mg.random.nextInt(0, 20) == 10) {
                        return cloneItem(item);
                    }
                    if (item.rarity == 5 && mg.random.nextInt(0, 25) == 10) {
                        return cloneItem(item);
                    }
                    return null;
                }
            }
        }
    }

    /**
     * @param listIndex the category to search in
     * @return A base item from any of the item category arrays
     */
    private ITEM goThroughArrays(int listIndex) {
        if (listIndex == 0) {
            return mg.AMULET.get(mg.random.nextInt(1, mg.AMULET.size()));
        } else if (listIndex == 1) {
            return mg.BOOTS.get(mg.random.nextInt(1, mg.BOOTS.size()));
        } else if (listIndex == 2) {
            return mg.CHEST.get(mg.random.nextInt(1, mg.CHEST.size()));
        } else if (listIndex == 3) {
            return mg.HEAD.get(mg.random.nextInt(1, mg.HEAD.size()));
        } else if (listIndex == 4) {
            return mg.OFFHAND.get(mg.random.nextInt(1, mg.OFFHAND.size()));
        } else if (listIndex == 5) {
            return mg.ONEHAND.get(mg.random.nextInt(1, mg.ONEHAND.size()));
        } else if (listIndex == 6) {
            return mg.PANTS.get(mg.random.nextInt(1, mg.PANTS.size()));
        } else if (listIndex == 7) {
            return mg.RELICS.get(mg.random.nextInt(1, mg.RELICS.size()));
        } else if (listIndex == 8) {
            return mg.RINGS.get(mg.random.nextInt(1, mg.RINGS.size()));
        } else if (listIndex == 9) {
            return mg.TWOHANDS.get(mg.random.nextInt(1, mg.TWOHANDS.size()));
        }
        return null;
    }

    private void debugItems() {
        System.out.println(DRP_DroppedItem.cloneItemWithLevelQuality(mg.CHEST.get(8), 80, 60).vitality);
        System.out.println(mg.CHEST.get(8).vitality);
        mg.CHEST.get(8).rollQuality(80);
        System.out.println(mg.CHEST.get(8).vitality);
        mg.CHEST.get(8).applyRarity();
        System.out.println(mg.CHEST.get(8).vitality);
        mg.CHEST.get(8).applyLevel();
        System.out.println(mg.CHEST.get(8).vitality);
    }
}
