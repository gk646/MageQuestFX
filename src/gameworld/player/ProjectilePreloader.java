package gameworld.player;

import gameworld.entities.loadinghelper.GeneralResourceLoader;

public class ProjectilePreloader {

    public static GeneralResourceLoader solarFlare;

    public static GeneralResourceLoader voidField;

    public static GeneralResourceLoader thunderStrike;
    public static GeneralResourceLoader voidEruption;
    public static GeneralResourceLoader thunderSplash;
    public static GeneralResourceLoader frostNova;

    public static void load() {
        solarFlare = new GeneralResourceLoader("projectiles/solarFlare");
        solarFlare.loadProjectilesSounds();
        voidField = new GeneralResourceLoader("projectiles/voidField");
        voidField.loadProjectilesSounds();
        thunderStrike = new GeneralResourceLoader("projectiles/thunderStrike");
        voidEruption = new GeneralResourceLoader("projectiles/voidEruption");
        voidEruption.loadProjectilesSounds();
        thunderSplash = new GeneralResourceLoader("projectiles/thunderSplash");
        frostNova = new GeneralResourceLoader("projectiles/frostNova");
    }
}
