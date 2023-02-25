package gameworld.world.objects.drops;

import gameworld.player.Player;
import gameworld.world.objects.DROP;
import gameworld.world.objects.items.ITEM;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;
import main.system.enums.Zone;
import main.system.ui.Effects;

public class DRP_DroppedItem extends DROP {
    private final MainGame mg;
    private Image droppedIcon;

    public DRP_DroppedItem(MainGame mg) {
        this.mg = mg;
    }

    public DRP_DroppedItem(MainGame mg, int worldX, int worldY, ITEM item, Zone zone) {
        this.mg = mg;
        this.zone = zone;
        this.size = 32;
        this.worldPos.x = worldX + 16;
        this.worldPos.y = worldY + 16;
        this.item = item;
        droppedIcon = item.icon;
    }

    private static ITEM cloneItem(ITEM item) {
        if (item != null) {
            ITEM new_ITEM = new ITEM(item.i_id, item.name, item.rarity, item.type, item.imagePath, item.description, item.stats, item.quality, item.level);
            new_ITEM.icon = item.icon;
            return new_ITEM;
        }
        return null;
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


    public void dropItem(MainGame mg, int worldX, int worldY, int level, Zone zone) {
        ITEM item = cloneAndRollEffects(rollForItem(level));
        if (item != null) {
            mg.WORLD_DROPS.add(new DRP_DroppedItem(mg, worldX, worldY, item, zone));
        }
    }

    public ITEM getFinishedRandomItem(int level) {
        ITEM item = cloneAndRollEffects(rollForItem(level));
        while (item == null) {
            item = cloneAndRollEffects(rollForItem(level));
        }
        return item;
    }

    public void dropRareItem(MainGame mg, int worldX, int worldY, int level, Zone zone) {
        ITEM item = cloneAndRollEffects(getRandomRare(level));
        if (item != null) {
            mg.WORLD_DROPS.add(new DRP_DroppedItem(mg, worldX, worldY, item, zone));
        }
    }

    public ITEM cloneAndRollEffects(ITEM item) {
        return rollEffect(cloneItem(item));
    }

    /**
     * Gets item with desired level
     *
     * @param level level the item should have
     * @return a random item with the given level
     */
    private ITEM rollForItem(int level) {
        ITEM item;
        item = goThroughArrays(mg.random.nextInt(0, 11));
        assert item != null;
        item.level = level;
        item.rollQuality();
        return getItemWithDropChance(item);
    }


    private ITEM getItemWithDropChance(ITEM item) {
        if (mg.random.nextInt(0, 2) == mg.random.nextInt(0, 2)) {
            if (item.rarity == 1) {
                return cloneItem(item);
            } else if (item.rarity == 2 && mg.random.nextInt(0, 3) == mg.random.nextInt(0, 3)) {
                return cloneAndRollEffects(item);
            } else if (item.rarity == 3 && mg.random.nextInt(0, 7) == mg.random.nextInt(0, 6)) {
                return cloneAndRollEffects(item);
            } else if (item.rarity == 4 && mg.random.nextInt(0, 20) == mg.random.nextInt(0, 18)) {
                return cloneItem(item);
            } else if (item.rarity == 5 && mg.random.nextInt(0, 25) == mg.random.nextInt(0, 20)) {
                return cloneItem(item);
            }
        }
        return null;
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
        } else if (listIndex == 10) {
            return mg.BAGS.get(mg.random.nextInt(1, mg.BAGS.size()));
        }
        return null;
    }

    private ITEM rollEffect(ITEM item) {
        if (item != null) {
            if (item.rarity == 2) {
                int number = mg.random.nextInt(1, Player.effectsSizeRollable);
                if (number == 1 || number == 2 || number == 18 || number == 19 || number == 28) {
                    item.effects[number] = mg.random.nextInt(0, 11);
                } else if (number == 3) {
                    item.effects[number] = mg.random.nextInt(0, 26);
                } else if (number == 4) {
                    item.effects[number] = mg.random.nextInt(0, 16);
                } else if (number == 5) {
                    item.effects[number] = mg.random.nextInt(0, 26);
                } else if (number == 6 || number == 7) {
                    item.effects[number] = mg.random.nextInt(0, 6);
                } else if (number >= 8 & number <= 16) {
                    item.effects[number] = mg.random.nextInt(0, 6);
                } else if (number == 17) {
                    item.effects[number] = mg.random.nextInt(0, 16);
                } else if (number == 20) {
                    item.effects[number] = mg.random.nextInt(0, 16);
                } else if (number == 21) {
                    item.effects[number] = mg.random.nextInt(0, 6);
                } else if (number == 22) {
                    item.effects[number] = mg.random.nextInt(0, 16);
                } else if (number == 23) {
                    item.effects[number] = mg.random.nextInt(0, 16);
                } else if (number == 24 || number == 25) {
                    item.effects[number] = mg.random.nextInt(0, 11);
                } else if (number == 26) {
                    item.effects[number] = mg.random.nextInt(0, 6);
                } else if (number == 27) {
                    item.effects[number] = mg.random.nextInt(0, 11);
                }
            }
            if (item.rarity == 3) {
                int number1 = mg.random.nextInt(1, Player.effectsSizeRollable);
                int number2 = mg.random.nextInt(1, Player.effectsSizeRollable);
                while (number1 == number2) {
                    number2 = mg.random.nextInt(1, Player.effectsSizeRollable);
                }
                int number;
                for (int i = 0; i < 2; i++) {
                    if (i == 1) {
                        number = number1;
                    } else {
                        number = number2;
                    }
                    if (number == 1 || number == 2 || number == 18 || number == 19 || number == 28) {
                        item.effects[number] = mg.random.nextInt(0, 11);
                    } else if (number == 3) {
                        item.effects[number] = mg.random.nextInt(0, 26);
                    } else if (number == 4) {
                        item.effects[number] = mg.random.nextInt(0, 11);
                    } else if (number == 5) {
                        item.effects[number] = mg.random.nextInt(0, 26);
                    } else if (number == 6 || number == 7) {
                        item.effects[number] = mg.random.nextInt(0, 6);
                    } else if (number >= 8 & number <= 16) {
                        item.effects[number] = mg.random.nextInt(0, 6);
                    } else if (number == 17) {
                        item.effects[number] = mg.random.nextInt(0, 16);
                    } else if (number == 20) {
                        item.effects[number] = mg.random.nextInt(0, 16);
                    } else if (number == 21) {
                        item.effects[number] = mg.random.nextInt(0, 6);
                    } else if (number == 22) {
                        item.effects[number] = mg.random.nextInt(0, 16);
                    } else if (number == 23) {
                        item.effects[number] = mg.random.nextInt(0, 16);
                    } else if (number == 24 || number == 25) {
                        item.effects[number] = mg.random.nextInt(0, 11);
                    } else if (number == 26) {
                        item.effects[number] = mg.random.nextInt(0, 6);
                    } else if (number == 27) {
                        item.effects[number] = mg.random.nextInt(0, 11);
                    }
                }
            }
            return item;
        } else {
            return null;
        }
    }

    public ITEM getRandomCommon(int level) {
        ITEM item;
        item = goThroughArrays(mg.random.nextInt(0, 11));
        while (true) {
            assert item != null;
            if (item.rarity == 1) break;
            item = goThroughArrays(mg.random.nextInt(0, 11));
        }
        item.rollQuality();
        item.level = level;
        return cloneItem(item);
    }

    public ITEM getRandomRare(int level) {
        ITEM item;
        item = goThroughArrays(mg.random.nextInt(0, 11));
        while (true) {
            assert item != null;
            if (item.rarity == 2) break;
            item = goThroughArrays(mg.random.nextInt(0, 11));
        }
        item.level = level;
        item.rollQuality();
        return cloneAndRollEffects(item);
    }


    public ITEM getRandomEpic(int level) {
        ITEM item;
        item = goThroughArrays(mg.random.nextInt(0, 11));
        while (true) {
            assert item != null;
            if (item.rarity == 3) break;
            item = goThroughArrays(mg.random.nextInt(0, 11));
        }
        item.level = level;
        item.rollQuality();

        return cloneAndRollEffects(item);
    }

    public ITEM getRandomLegendary(int level) {
        ITEM item;
        item = goThroughArrays(mg.random.nextInt(0, 11));
        while (true) {
            assert item != null;
            if (item.rarity == 4) break;
            item = goThroughArrays(mg.random.nextInt(0, 11));
        }
        item.rollQuality();
        item.level = level;
        return cloneItem(item);
    }

    public ITEM getRandomSetItem(int level) {
        ITEM item;
        item = goThroughArrays(mg.random.nextInt(0, 11));
        while (true) {
            assert item != null;
            if (item.rarity == 5) break;
            item = goThroughArrays(mg.random.nextInt(0, 11));
        }
        item.rollQuality();
        item.level = level;
        return cloneItem(item);
    }

    private void setRarityEffect(GraphicsContext gc) {
        if (item.rarity == 1) {
            gc.setEffect(Effects.rarity_1glow);
        } else if (item.rarity == 2) {
            gc.setEffect(Effects.rarity_2glow);
        } else if (item.rarity == 3) {
            gc.setEffect(Effects.rarity_3glow);
        } else if (item.rarity == 4 || item.rarity == 10) {
            gc.setEffect(Effects.rarity_4glow);
        } else if (item.rarity == 5) {
            gc.setEffect(Effects.rarity_5glow);
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        setRarityEffect(gc);
        gc.drawImage(droppedIcon, worldPos.x - Player.worldX + Player.screenX, worldPos.y - Player.worldY + Player.screenY, 32, 32);
        gc.setEffect(null);
    }

    /**
     *
     */
    @Override
    public void update() {

    }
}
