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

package main.system.enums;

public enum Zone {
    Woodland_Edge(0), EtherRealm(0), DeadPlains(0), GrassLands(1), City1(2), Ruin_Dungeon(3), Hillcrest(4), Treasure_Cave(3), Hillcrest_Mountain_Cave(4), The_Grove(5), TestRoom(5), Goblin_Cave(3), Hillcrest_Hermit_Cave(3);
    private final int value;

    Zone(int val) {
        value = val;
    }

    public boolean isDungeon() {
        return value == 3;
    }

    public boolean isForest() {
        return value == 1 || value == 0;
    }

    @Override
    public String toString() {
        return this.name().replace("_", " ");
    }
}
