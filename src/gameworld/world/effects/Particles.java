package gameworld.world.effects;

import gameworld.ENT_Control;

import java.awt.Color;

class Particles {
    private final ENT_Control generator;
    private final Color color;
    private final int xd;
    private final int yd;
    private final int size;
    private final int speed;
    private final int maxLife;

    public Particles(ENT_Control generator, Color color, int size, int speed, int maxLife, int xd, int yd) {
        this.generator = generator;
        this.color = color;
        this.size = size;
        this.speed = speed;
        this.maxLife = maxLife;
        this.xd = xd;
        this.yd = yd;
    }
}
