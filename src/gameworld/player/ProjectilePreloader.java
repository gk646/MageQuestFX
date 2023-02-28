package gameworld.player;

import gameworld.entities.loadinghelper.GeneralResourceLoader;

public class ProjectilePreloader {

    public static GeneralResourceLoader SolarFlare;

    public static GeneralResourceLoader voidField;

    public static GeneralResourceLoader thunderStrike;
    public static GeneralResourceLoader voidEruption;
    public static GeneralResourceLoader thunderSplash;
    public static GeneralResourceLoader frostNova;

    public static void load() {
        SolarFlare = new GeneralResourceLoader("projectiles/solarFlare");
        voidField = new GeneralResourceLoader("projectiles/voidField");
        voidField.loadProjectilesSounds();
        thunderStrike = new GeneralResourceLoader("projectiles/thunderStrike");
        voidEruption = new GeneralResourceLoader("projectiles/voidEruption");
        voidEruption.loadProjectilesSounds();
        thunderSplash = new GeneralResourceLoader("projectiles/thunderSplash");
        frostNova = new GeneralResourceLoader("projectiles/frostNova");
    }
}
