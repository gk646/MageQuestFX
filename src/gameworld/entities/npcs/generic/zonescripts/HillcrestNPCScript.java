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

package gameworld.entities.npcs.generic.zonescripts;

import gameworld.entities.NPC;
import gameworld.entities.npcs.generic.NPC_Generic;
import main.MainGame;
import main.system.enums.Zone;

import java.awt.Point;

public class HillcrestNPCScript extends NPCScript {

    private int upCounter;

    public HillcrestNPCScript(MainGame mg) {
        this.respawnCounter = 300;
        this.zone = Zone.Hillcrest;
        this.mg = mg;
        this.houseEntrances = new Point[]{new Point(30, 8), new Point(38, 8),
                new Point(45, 9), new Point(27, 14), new Point(33, 14), new Point(48, 26), new Point(53, 26),
                new Point(53, 32), new Point(48, 32), new Point(23, 27), new Point(26, 20), new Point(32, 20), new Point(36, 24)};
        this.entranceTaken = new boolean[houseEntrances.length];
        amountOfNPCs = 6;
    }


    /**
     *
     */
    @Override
    public void update() {
        upCounter++;
        if (upCounter >= respawnCounter) {
            upCounter = 0;
            int genericCounter = 0;
            for (NPC npc : mg.npcControl.NPC_Active) {
                if (npc instanceof NPC_Generic) {
                    genericCounter++;
                }
            }
            if (genericCounter < amountOfNPCs) {
                respawnGeneric();
            }
        }
    }
}
