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
    public static GeneralResourceLoader pyroBlast;
    public static GeneralResourceLoader fireBurst;
    public static GeneralResourceLoader powerSurge;
    public static GeneralResourceLoader blastHammer;
    public static GeneralResourceLoader gateTeleporter;
    public static GeneralResourceLoader gateTeleporter1;
    public static GeneralResourceLoader infernoRay;
    public static GeneralResourceLoader fireSword;

    public static ArrayList<GeneralResourceLoader> projectileSounds = new ArrayList<>();
    public static GeneralResourceLoader fireExplosion;

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
        thunderSplash.loadProjectilesSounds();
        projectileSounds.add(thunderSplash);

        frostNova = new GeneralResourceLoader("projectiles/frostNova");
        frostNova.loadProjectilesSounds();
        projectileSounds.add(frostNova);

        iceLance = new GeneralResourceLoader("projectiles/iceLance");
        iceLance.loadProjectilesSounds();
        projectileSounds.add(iceLance);

        lightning = new GeneralResourceLoader("projectiles/lightning");
        lightning.loadProjectilesSounds();
        projectileSounds.add(lightning);

        pyroBlast = new GeneralResourceLoader("projectiles/pyroBlast");
        pyroBlast.loadProjectilesSounds();
        projectileSounds.add(pyroBlast);

        fireBurst = new GeneralResourceLoader("projectiles/fireBurst");
        fireBurst.loadProjectilesSounds();
        projectileSounds.add(fireBurst);

        powerSurge = new GeneralResourceLoader("projectiles/powerSurge");
        powerSurge.loadProjectilesSounds();
        projectileSounds.add(powerSurge);

        blastHammer = new GeneralResourceLoader("projectiles/blastHammer");
        blastHammer.loadProjectilesSounds();
        projectileSounds.add(blastHammer);


        fireSword = new GeneralResourceLoader("projectiles/fireSword");
        fireSword.loadProjectilesSounds();
        projectileSounds.add(fireSword);

        infernoRay = new GeneralResourceLoader("projectiles/infernoRay");
        infernoRay.loadProjectilesSounds();
        projectileSounds.add(infernoRay);

        gateTeleporter = new GeneralResourceLoader("projectiles/gate1");
        //gateTeleporter.loadProjectilesSounds();
        //projectileSounds.add(gateTeleporter);
        gateTeleporter1 = new GeneralResourceLoader("projectiles/gate2");
        //TODO SOUNDS

        fireExplosion = new GeneralResourceLoader("projectiles/fireExplosion");
        //fireExplosion.loadProjectilesSounds();
        //fireExplosion.add(fireExplosion);
    }
}
