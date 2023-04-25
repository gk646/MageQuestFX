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

package gameworld.entities.loadinghelper;

public class EntityPreloader {
    public static ResourceLoaderEntity mushroom;
    public static ResourceLoaderEntity skeletonWarrior;
    public static ResourceLoaderEntity skeletonArcher;
    public static ResourceLoaderEntity skeletonSpear;
    public static ResourceLoaderEntity snake;
    public static ResourceLoaderEntity wolf;

    public static ResourceLoaderEntity skeletonShield;
    private static ResourceLoaderEntity deathBringer;
    public static ResourceLoaderEntity goblin;
    private static ResourceLoaderEntity stoneKnight;
    public static ResourceLoaderEntity realmKeeper;
    public static ResourceLoaderEntity bigBloated;

    public static void load() {
        mushroom = new ResourceLoaderEntity("enemies/mushroom");
        skeletonWarrior = new ResourceLoaderEntity("enemies/skeletonWarrior");
        skeletonArcher = new ResourceLoaderEntity("enemies/skeletonArcher");
        skeletonSpear = new ResourceLoaderEntity("enemies/skeletonSpear");
        snake = new ResourceLoaderEntity("enemies/snake");
        wolf = new ResourceLoaderEntity("enemies/wolf");
        goblin = new ResourceLoaderEntity("enemies/goblin");
        deathBringer = new ResourceLoaderEntity("enemies/BOSSDeathBringer");
        skeletonShield = new ResourceLoaderEntity("enemies/skeletonShield");
        stoneKnight = new ResourceLoaderEntity("enemies/BOSSKnight");
        bigBloated = new ResourceLoaderEntity("enemies/BossSlime");
        realmKeeper = new ResourceLoaderEntity("npc/realmKeeper");
    }
}
