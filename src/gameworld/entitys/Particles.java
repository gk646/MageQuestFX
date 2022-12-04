package gameworld.entitys;

import gameworld.Entity;
import main.MainGame;

import java.awt.Color;

public class Particles extends Entity {
    Entity generator;
    Color color;
    int xd;
    int yd;
    int size;
    int speed;
    int maxLife;

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
