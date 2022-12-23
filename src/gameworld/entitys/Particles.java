package gameworld.entitys;

import gameworld.Entity;
import main.MainGame;

import java.awt.Color;

class Particles extends Entity {
    private final Entity generator;
    private final Color color;
    private final int xd;
    private final int yd;
    private final int size;
    private final int speed;
    private final int maxLife;

    public Particles(MainGame mainGame, Entity generator, Color color, int size, int speed, int maxLife, int xd, int yd) {
        super(mainGame);
        this.generator = generator;
        this.color = color;
        this.size = size;
        this.speed = speed;
        this.maxLife = maxLife;
        this.xd = xd;
        this.yd = yd;


    }
}
