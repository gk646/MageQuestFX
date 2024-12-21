/*
 * MIT License
 *
 * Copyright (c) 2023 gk646
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package gameworld.world.objects.drops;

import gameworld.player.Player;
import gameworld.world.objects.DROP;
import gameworld.world.objects.items.ITEM;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.system.enums.Zone;
import main.system.ui.Effects;

public class DRP_DroppedItem extends DROP {

    private final Image droppedIcon;

    /**
     * Rarity 1: 58.87% <p>
     * Rarity 2: 31.86% <p>
     * Rarity 3: 7.68% <p>
     * Rarity 4: 1.39% <p>
     * Rarity 5: 0.20% <p>
     *
     * @param worldX
     * @param worldY
     * @param item
     * @param zone
     */

    public DRP_DroppedItem(int worldX, int worldY, ITEM item, Zone zone) {
        this.zone = zone;
        this.size = 32;
        this.worldPos.x = worldX + 16;
        this.worldPos.y = worldY + 16;
        this.item = item;
        droppedIcon = item.icon;
    }

    public static ITEM cloneItem(ITEM item) {
        ITEM new_ITEM = new ITEM(item.i_id, item.name, item.rarity, item.type, item.imagePath, item.description, item.stats, item.quality, item.level, item.effects);
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
        float[] array = new float[Player.effectsSizeRollable];
        System.arraycopy(item.effects, 0, array, 0, Player.effectsSizeRollable);
        ITEM new_ITEM = new ITEM(item.i_id, item.name, item.rarity, item.type, item.imagePath, item.description, item.stats, quality, level, array);
        new_ITEM.icon = item.icon;
        return new_ITEM;
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
