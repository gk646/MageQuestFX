package gameworld.player;

import gameworld.entities.loadinghelper.GeneralResourceLoader;

import java.util.ArrayList;

public class ProjectilePreloader {

    public static GeneralResourceLoader solarFlare;

    public static GeneralResourceLoader voidField;

    public static GeneralResourceLoader thunderStrike;
    public static GeneralResourceLoader voidEruption;
    public static GeneralResourceLoader thunderSplash;
    public static GeneralResourceLoader frostNova;
    public static GeneralResourceLoader iceLance;

    public static GeneralResourceLoader lightning;

    public static ArrayList<GeneralResourceLoader> projectileSounds = new ArrayList<>();

    public static void load() {
        solarFlare = new GeneralResourceLoader("projectiles/solarFlare");
        solarFlare.loadProjectilesSounds();
        projectileSounds.add(solarFlare);

        voidField = new GeneralResourceLoader("projectiles/voidField");
        voidField.loadProjectilesSounds();
        projectileSounds.add(voidField);

        thunderStrike = new GeneralResourceLoader("projectiles/thunderStrike");
        thunderStrike.loadProjectilesSounds();
        projectileSounds.add(thunderStrike);

        voidEruption = new GeneralResourceLoader("projectiles/voidEruption");
        voidEruption.loadProjectilesSounds();
        projectileSounds.add(voidEruption);

        thunderSplash = new GeneralResourceLoader("projectiles/thunderSplash");
        projectileSounds.add(thunderSplash);

        frostNova = new GeneralResourceLoader("projectiles/frostNova");
        projectileSounds.add(frostNova);

        iceLance = new GeneralResourceLoader("projectiles/iceLance");
        iceLance.loadProjectilesSounds();
        projectileSounds.add(iceLance);

        lightning = new GeneralResourceLoader("projectiles/lightning");
        lightning.loadProjectilesSounds();
        projectileSounds.add(lightning);
    }
}
