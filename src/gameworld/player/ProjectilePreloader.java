package gameworld.player;

import gameworld.entities.loadinghelper.GeneralResourceLoader;

public class ProjectilePreloader {

    public static GeneralResourceLoader SolarFlare;

    public static GeneralResourceLoader voidField;

    public static GeneralResourceLoader thunderStrike;

    public static void load() {
        SolarFlare = new GeneralResourceLoader("projectiles/solarFlare");
        voidField = new GeneralResourceLoader("projectiles/voidField");
        thunderStrike = new GeneralResourceLoader("projectiles/thunderStrike");
    }
}
