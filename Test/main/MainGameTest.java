package main;

import gameworld.player.Player;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import main.system.database.SQLite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.Random;

class MainGameTest {
    MainGame mg;

    @BeforeEach
    public void setup() {
        Canvas canvas = new Canvas();
        Group group = new Group();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        this.mg = new MainGame(1, 1, gc, group.getScene());
        mg.sqLite = new SQLite(mg);
        mg.sqLite.readItemsOnly();
        mg.player = new Player(mg);
        SecureRandom secureRandom = new SecureRandom();
        long seed = secureRandom.nextLong();
        mg.random = new Random(seed);
    }

    @Test
    void run() {

    }
}