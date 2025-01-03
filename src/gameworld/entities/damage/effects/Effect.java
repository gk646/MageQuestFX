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

package gameworld.entities.damage.effects;

import gameworld.entities.ENTITY;
import gameworld.entities.damage.DamageType;
import gameworld.player.PROJECTILE;
import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.system.ui.Colors;

import java.util.Objects;


abstract public class Effect {
    DamageType type;
    public float amount;
    protected boolean fromPlayer = false;
    public int indexAffected;
    private float coolDownCoefficient;
    protected String name;
    protected Image icon;
    protected String description;
    public Class<? extends PROJECTILE> sourceProjectile;
    protected int ticker = 0;
    public EffectType effectType;
    protected int tickRate;// how many ticks needed to activate
    public float full_duration, rest_duration;

    protected Effect(float duration, float amount, boolean fromPlayer, Class<? extends PROJECTILE> sourceProjectile) {
        full_duration = duration;
        if (fromPlayer) {
            this.full_duration = duration + duration / 100.0f * Player.playerEffects[3];
        }
        rest_duration = full_duration;
        this.amount = amount;
        this.fromPlayer = fromPlayer;
        this.rest_duration = full_duration;
        this.sourceProjectile = sourceProjectile;
    }

    protected Effect() {

    }

    abstract public void tick(ENTITY entity);

    protected Image setupPicture(String name) {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/buffs/" + name + ".png")));
    }

    public void draw(GraphicsContext gc, int x, int y) {
        gc.drawImage(icon, x, y);
        drawCooldown(gc, x, y);
    }

    private void drawCooldown(GraphicsContext gc, int skillBarX, int skillBarY) {
        int side1;
        int side2;
        int side3;
        int side4;
        int side5;
        if (rest_duration != full_duration) {
            coolDownCoefficient = (rest_duration * (128.0f / full_duration));
            side1 = 16;
            side2 = 0;
            side3 = 0;
            side4 = 0;
            side5 = 0;
        } else {
            coolDownCoefficient = 0;
            side1 = 100;
            side2 = 110;
            side3 = 110;
            side4 = 111;
            side5 = 110;
        }
        if (coolDownCoefficient > 0) {
            side1 = (int) (side1 + coolDownCoefficient);
        }
        if (coolDownCoefficient > 16) {
            side2 = (int) (side2 + (coolDownCoefficient - 16));
        }
        if (coolDownCoefficient > 48) {
            side3 = (int) (side3 + (coolDownCoefficient - 48));
        }
        if (coolDownCoefficient > 80) {
            side4 = (int) (side4 + (coolDownCoefficient - 80));
        }
        if (coolDownCoefficient > 112) {
            side5 = (int) (side5 + (coolDownCoefficient - 112));
        }
        gc.setStroke(Colors.LightGreyLessTransp);
        for (int i = side1; i <= 32; i++) {
            gc.strokeLine(skillBarX + 16, skillBarY + 16, skillBarX + i, skillBarY);
        }
        for (int i = side2; i <= 32; i++) {
            gc.strokeLine(skillBarX + 16, skillBarY + 16, skillBarX + 32, skillBarY + i);
        }
        for (int i = side3; i <= 32; i++) {
            gc.strokeLine(skillBarX + 16, skillBarY + 16, skillBarX + 32 - i, skillBarY + 32);
        }
        for (int i = side4; i <= 32; i++) {
            gc.strokeLine(skillBarX + 16, skillBarY + 16, skillBarX, skillBarY + 32 - i);
        }
        for (int i = side5; i <= 16; i++) {
            gc.strokeLine(skillBarX + 16, skillBarY + 16, skillBarX + i, skillBarY);
        }
    }

    public void drawCooldownSmall(GraphicsContext gc, int skillBarX, int skillBarY) {
        int side1;
        int side2;
        int side3;
        int side4;
        int side5;
        if (rest_duration != full_duration) {
            coolDownCoefficient = (rest_duration * (64.0f / full_duration));
            side1 = 8;
            side2 = 0;
            side3 = 0;
            side4 = 0;
            side5 = 0;
        } else {
            coolDownCoefficient = 0;
            side1 = 100;
            side2 = 110;
            side3 = 110;
            side4 = 111;
            side5 = 110;
        }
        if (coolDownCoefficient > 0) {
            side1 = (int) (side1 + coolDownCoefficient);
        }
        if (coolDownCoefficient > 8) {
            side2 = (int) (side2 + (coolDownCoefficient - 8));
        }
        if (coolDownCoefficient > 24) {
            side3 = (int) (side3 + (coolDownCoefficient - 24));
        }
        if (coolDownCoefficient > 40) {
            side4 = (int) (side4 + (coolDownCoefficient - 48));
        }
        if (coolDownCoefficient > 56) {
            side5 = (int) (side5 + (coolDownCoefficient - 56));
        }
        gc.setStroke(Colors.LightGreyLessTransp);
        for (int i = side1; i <= 16; i++) {
            gc.strokeLine(skillBarX + 8, skillBarY + 8, skillBarX + i, skillBarY);
        }
        for (int i = side2; i <= 16; i++) {
            gc.strokeLine(skillBarX + 8, skillBarY + 8, skillBarX + 16, skillBarY + i);
        }
        for (int i = side3; i <= 16; i++) {
            gc.strokeLine(skillBarX + 8, skillBarY + 8, skillBarX + 16 - i, skillBarY + 16);
        }
        for (int i = side4; i <= 16; i++) {
            gc.strokeLine(skillBarX + 8, skillBarY + 8, skillBarX, skillBarY + 16 - i);
        }
        for (int i = side5; i <= 8; i++) {
            gc.strokeLine(skillBarX + 8, skillBarY + 8, skillBarX + i, skillBarY);
        }
    }

    @Override
    abstract public Effect clone();
}
