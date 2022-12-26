package gameworld.world;

import gameworld.Item;
import main.MainGame;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class DroppedItem {
    public BufferedImage droppedIcon;
    MainGame mg;
    public Item item;
    public Point worldPos = new Point();

    public DroppedItem(MainGame mg, int worldX, int worldY) {
        this.mg = mg;
        this.worldPos.x = worldX;
        this.worldPos.y = worldY;
        item = rollForItem();
        droppedIcon = item.icon;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(droppedIcon, worldPos.x - mg.player.worldX + mg.HALF_WIDTH, worldPos.y - mg.player.worldY + mg.HALF_HEIGHT, 32, 32, null);
    }

    private Item rollForItem() {
        Item item1;
        while (true) {
            item1 = goThroughArrays(mg.random.nextInt(0, 10));
            if (!item1.name.equals("FILLER")) {
                if (mg.random.nextInt(0, 15) == 5) {
                    if (item1.rarity == 1) {
                        return cloneItem(item1);
                    }
                    if (item1.rarity == 2 && mg.random.nextInt(0, 2) == 1) {
                        return cloneItem(item1);
                    }

                    if (item1.rarity == 3 && mg.random.nextInt(0, 5) == 4) {
                        return cloneItem(item1);
                    }

                    if (item1.rarity == 4 && mg.random.nextInt(0, 11) == 10) {
                        item1.rollQuality();
                        return cloneItem(item1);
                    }
                    return mg.CHEST.get(0);
                }
            }
        }
    }

    public static Item cloneItem(Item item) {
        Item new_item = new Item(item.i_id, item.name, item.rarity, item.type, item.imagePath, item.description, item.stats);
        new_item.icon = item.icon;
        return new_item;
    }

    private Item goThroughArrays(int listIndex) {
        if (listIndex == 0) {
            return mg.AMULET.get(mg.random.nextInt(0, mg.AMULET.size()));
        } else if (listIndex == 1) {
            return mg.BOOTS.get(mg.random.nextInt(0, mg.BOOTS.size()));
        } else if (listIndex == 2) {
            return mg.CHEST.get(mg.random.nextInt(0, mg.CHEST.size()));
        } else if (listIndex == 3) {
            return mg.HEAD.get(mg.random.nextInt(0, mg.HEAD.size()));
        } else if (listIndex == 4) {
            return mg.OFFHAND.get(mg.random.nextInt(0, mg.OFFHAND.size()));
        } else if (listIndex == 5) {
            return mg.ONEHAND.get(mg.random.nextInt(0, mg.ONEHAND.size()));
        } else if (listIndex == 6) {
            return mg.PANTS.get(mg.random.nextInt(0, mg.PANTS.size()));
        } else if (listIndex == 7) {
            return mg.RELICS.get(mg.random.nextInt(0, mg.RELICS.size()));
        } else if (listIndex == 8) {
            return mg.RINGS.get(mg.random.nextInt(0, mg.RINGS.size()));
        } else if (listIndex == 9) {
            return mg.TWOHANDS.get(mg.random.nextInt(0, mg.TWOHANDS.size()));
        }
        return mg.CHEST.get(0);
    }
}
