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

package gameworld.entities.npcs.generic.zonescripts;

import gameworld.entities.npcs.generic.NPC_GenericVillagerBoy;
import gameworld.entities.npcs.generic.NPC_GenericVillagerBoy1;
import gameworld.entities.npcs.generic.NPC_GenericVillagerWoman;
import main.MainGame;
import main.system.enums.Zone;

import java.awt.Point;

abstract public class NPCScript {
    MainGame mg;
    int respawnCounter;
    int amountOfNPCs;
    public Zone zone;
    public Point[] houseEntrances;
    public boolean[] entranceTaken;

    abstract public void update();

    void respawnGeneric() {
        double num = Math.random();
        Point point = houseEntrances[(int) (Math.random() * houseEntrances.length)];
        if (num < 0.15) {
            mg.npcControl.NPC_Active.add(new NPC_GenericVillagerBoy(mg, point.x, point.y, zone));
        } else if (num < 0.3) {
            mg.npcControl.NPC_Active.add(new NPC_GenericVillagerBoy1(mg, point.x, point.y, zone));
        } else if (num < 1) {
            mg.npcControl.NPC_Active.add(new NPC_GenericVillagerWoman(mg, point.x, point.y, zone));
        }
    }
}
