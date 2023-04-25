/*
 * MIT License
 *
 * Copyright (c) 2023 Lukas Gilch
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

package gameworld.entities.multiplayer;

import gameworld.entities.ENTITY;
import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;

public class ENT_Player2 extends ENTITY {
    private Image player2;

    public ENT_Player2(MainGame mainGame) {
        this.mg = mainGame;
        //Setting default values
        worldX = 12_500;
        worldY = 12_500;
        this.entityHeight = 48;
        this.entityWidth = 48;


        //Handlers

    }


    /**
     *
     */
    @Override
    public void playGetHitSound() {

    }

    public void draw(GraphicsContext g2) {
        g2.drawImage(player2, worldX - Player.worldX + MainGame.SCREEN_WIDTH / 2.00f - 24, worldY - Player.worldY + MainGame.SCREEN_HEIGHT / 2.00f - 24, 48, 48);
    }

    /**
     *
     */
    @Override
    public void update() {

    }
}

