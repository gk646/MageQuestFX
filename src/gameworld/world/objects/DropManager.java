package gameworld.world.objects;

import gameworld.player.Player;
import gameworld.world.objects.drops.DRP_ChestItem;
import gameworld.world.objects.drops.DRP_DroppedItem;
import gameworld.world.objects.items.ITEM;
import main.MainGame;
import main.system.enums.Zone;

public class DropManager {
    MainGame mg;

    public DropManager(MainGame mg) {
        this.mg = mg;
    }

    private ITEM getItemWithDropChance(ITEM item) {
        if (mg.random.nextInt(0, 2) == mg.random.nextInt(0, 2)) {
            if (item.rarity == 1) {
                return item;
            } else if (item.rarity == 2 && mg.random.nextInt(0, 3) == mg.random.nextInt(0, 3)) {
                return item;
            } else if (item.rarity == 3 && mg.random.nextInt(0, 7) == mg.random.nextInt(0, 6)) {
                return item;
            } else if (item.rarity == 4 && mg.random.nextInt(0, 20) == mg.random.nextInt(0, 18)) {
                return item;
            } else if (item.rarity == 5 && mg.random.nextInt(0, 25) == mg.random.nextInt(0, 20)) {
                return item;
            }
        }
        return null;
    }

    /**
     * @param listIndex the category to search in
     * @return A base item from any of the item category arrays
     */
    private ITEM goThroughArrays(int listIndex, int level) {
        if (listIndex == 0) {
            return DRP_DroppedItem.cloneItemWithLevelQuality(mg.AMULET.get(mg.random.nextInt(1, mg.AMULET.size())), mg.random.nextInt(73, 101), level);
        } else if (listIndex == 1) {
            return DRP_DroppedItem.cloneItemWithLevelQuality(mg.BOOTS.get(mg.random.nextInt(1, mg.BOOTS.size())), mg.random.nextInt(73, 101), level);
        } else if (listIndex == 2) {
            return DRP_DroppedItem.cloneItemWithLevelQuality(mg.CHEST.get(mg.random.nextInt(1, mg.CHEST.size())), mg.random.nextInt(73, 101), level);
        } else if (listIndex == 3) {
            return DRP_DroppedItem.cloneItemWithLevelQuality(mg.HEAD.get(mg.random.nextInt(1, mg.HEAD.size())), mg.random.nextInt(73, 101), level);
        } else if (listIndex == 4) {
            return DRP_DroppedItem.cloneItemWithLevelQuality(mg.OFFHAND.get(mg.random.nextInt(1, mg.OFFHAND.size())), mg.random.nextInt(73, 101), level);
        } else if (listIndex == 5) {
            return DRP_DroppedItem.cloneItemWithLevelQuality(mg.ONEHAND.get(mg.random.nextInt(1, mg.ONEHAND.size())), mg.random.nextInt(73, 101), level);
        } else if (listIndex == 6) {
            return DRP_DroppedItem.cloneItemWithLevelQuality(mg.PANTS.get(mg.random.nextInt(1, mg.PANTS.size())), mg.random.nextInt(73, 101), level);
        } else if (listIndex == 7) {
            return DRP_DroppedItem.cloneItemWithLevelQuality(mg.RELICS.get(mg.random.nextInt(1, mg.RELICS.size())), mg.random.nextInt(73, 101), level);
        } else if (listIndex == 8) {
            return DRP_DroppedItem.cloneItemWithLevelQuality(mg.RINGS.get(mg.random.nextInt(1, mg.RINGS.size())), mg.random.nextInt(73, 101), level);
        } else if (listIndex == 9) {
            return DRP_DroppedItem.cloneItemWithLevelQuality(mg.TWOHANDS.get(mg.random.nextInt(1, mg.TWOHANDS.size())), mg.random.nextInt(73, 101), level);
        } else if (listIndex == 10) {
            return DRP_DroppedItem.cloneItemWithLevelQuality(mg.BAGS.get(mg.random.nextInt(1, mg.BAGS.size())), mg.random.nextInt(73, 101), level);
        }
        return null;
    }

    private ITEM rollEffect(ITEM item) {
        int usedEffects = 28;
        if (item != null) {
            if (item.rarity == 2) {
                int number = mg.random.nextInt(1, Player.effectsSizeRollable);
                for (int i = 0; i < Player.effectsSizeRollable; i++) {
                    if (item.effects[i] != 0) {
                        if (i > usedEffects) {
                            return item;
                        } else {
                            item.effects[i] = 0;
                            break;
                        }
                    }
                }
                return getEffectWithIndex(item, number);
            } else if (item.rarity == 3) {
                int counter = 0;
                int number1 = mg.random.nextInt(1, Player.effectsSizeRollable);
                int number2 = mg.random.nextInt(1, Player.effectsSizeRollable);
                while (number1 == number2) {
                    number2 = mg.random.nextInt(1, Player.effectsSizeRollable);
                }
                for (int i = 0; i < Player.effectsSizeRollable; i++) {
                    if (item.effects[i] != 0) {
                        counter++;
                        if (counter == 1) {
                            if (number1 > 28) {
                                continue;
                            } else {
                                item.effects[i] = 0;
                                item = getEffectWithIndex(item, number1);
                            }
                        } else {
                            if (number2 <= 28) {
                                item = getEffectWithIndex(item, number2);
                            }
                            return item;
                        }
                    }
                }
                for (int i = 0; i < 2; i++) {
                    if (i == 1) {
                        item = getEffectWithIndex(item, number1);
                    } else {
                        item = getEffectWithIndex(item, number2);
                    }
                }
            }
            return item;
        } else {
            return null;
        }
    }

    private ITEM getEffectWithIndex(ITEM item, int number) {
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
        return item;
    }

    public void useDropChance(int x, int y, int level, Zone zone) {
        ITEM item = goThroughArrays(mg.random.nextInt(0, 11), level);
        item = getItemWithDropChance(rollEffect(item));
        if (item != null) {
            mg.WORLD_DROPS.add(new DRP_DroppedItem(x, y, item, zone));
        }
    }

    public ITEM getGuaranteedRandomItem(int level) {
        ITEM item = getItemWithDropChance(goThroughArrays(mg.random.nextInt(0, 11), level));
        while (item == null) {
            item = getItemWithDropChance(goThroughArrays(mg.random.nextInt(0, 11), level));
        }
        return rollEffect(item);
    }

    public void bossDieEvent(int x, int y, Zone zone, int level) {
        ITEM item = getGuaranteedRandomItem(level);
        while (item.rarity != 2) {
            item = getGuaranteedRandomItem(level);
        }
        mg.WORLD_DROPS.add(new DRP_ChestItem(mg, x, y, zone, true, item));
        mg.WORLD_DROPS.add(new DRP_ChestItem(mg, x, y, zone, level, false));
        mg.WORLD_DROPS.add(new DRP_ChestItem(mg, x, y, zone, level, false));
    }

    public void epicChestEvent(int x, int y, Zone zone, int level) {
        ITEM item = getGuaranteedRandomItem(level);
        while (item.rarity != 3) {
            item = getGuaranteedRandomItem(level);
        }
        mg.WORLD_DROPS.add(new DRP_ChestItem(mg, x, y, zone, true, item));
        mg.WORLD_DROPS.add(new DRP_ChestItem(mg, x, y, zone, level, false));
    }
}
