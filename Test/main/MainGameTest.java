package main;

import gameworld.NPC_Control;
import gameworld.entities.ENTITY;
import gameworld.entities.monsters.ENT_SkeletonWarrior;
import gameworld.player.Player;
import gameworld.world.WorldController;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import main.system.ai.PathFinder;
import main.system.database.SQLite;
import main.system.enums.Zone;
import main.system.rendering.WorldRender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

class MainGameTest {

    MainGame mg;

    @BeforeEach
    public void setup() {
        Canvas canvas = new Canvas();
        Group group = new Group();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        this.mg = new MainGame(1, 1, gc);
        mg.sqLite = new SQLite(mg);
        mg.sqLite.readItemsOnly();
        mg.player = new Player(mg);
        mg.pathF = new PathFinder(mg);
        mg.wRender = new WorldRender(mg);
        mg.wRender.worldSize = new Point(500, 500);
        mg.pathF.instantiateNodes();
        mg.wControl = new WorldController(mg);
        mg.npcControl = new NPC_Control(mg);
        mg.wControl.loadWorldData();
        mg.wControl.loadMap(Zone.Tutorial, 15, 15);
        SecureRandom secureRandom = new SecureRandom();
        long seed = secureRandom.nextLong();
        mg.random = new Random(seed);
    }

    @Test
    void run() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        ENTITY[] entities = new ENTITY[500];
        WorldController.currentWorld = Zone.Tutorial;
        Player.worldX = 4500;
        Player.worldY = 4500;
        for (int i = 0; i < 200; i++) {
            entities[i] = new ENT_SkeletonWarrior(mg, 5000, 5000, 10, Zone.Tutorial);
        }
        for (int i = 200; i < 400; i++) {
            entities[i] = new ENT_SkeletonWarrior(mg, 5000, 5000, 10, Zone.GrassLands);
        }
        for (int i = 200; i < 400; i++) {
            entities[i] = new ENT_SkeletonWarrior(mg, 1000, 1000, 10, Zone.City1);
        }
        for (int i = 400; i < 500; i++) {
            entities[i] = new ENT_SkeletonWarrior(mg, 1200, 120, 10, Zone.City1);
        }
        long time = System.nanoTime();
        for (ENTITY entity : entities) {
            if (entity.zone != WorldController.currentWorld || Math.abs(entity.worldX - Player.worldX) + Math.abs(entity.worldY - Player.worldY) > 1_500) {
                continue;
            }
            arrayList.add(1);
            entity.update();
        }

        System.out.println((System.nanoTime() - time) / 1_000 + " my way");
        System.out.println(arrayList.size());
        time = System.nanoTime();
        for (ENTITY entity : entities) {
            if (entity.zone == WorldController.currentWorld && Math.abs(entity.worldX - Player.worldX) + Math.abs(entity.worldY - Player.worldY) < 1_800) {
                entity.update();
                arrayList.add(2);
            }
        }
        System.out.println((System.nanoTime() - time) / 1_000 + " ki way");
        System.out.println(arrayList.size());
        time = System.nanoTime();

        for (ENTITY entity : entities) {
            if ((entity.worldX - Player.worldX) * (entity.worldX - Player.worldX) + (entity.worldY - Player.worldY) * (entity.worldY - Player.worldY) < 2_250_000) {
                arrayList.add(1);
            }
        }

        System.out.println((System.nanoTime() - time) / 1_000 + "pythagors");
        System.out.println(arrayList.size());

        time = System.nanoTime();

        for (ENTITY entity : entities) {
            if (Math.abs(entity.worldX - Player.worldX) + Math.abs(entity.worldY - Player.worldY) < 1500) {
                arrayList.add(1);
            }
        }

        System.out.println((System.nanoTime() - time) / 1_000 + " my way");
        System.out.println(arrayList.size());
    }
}