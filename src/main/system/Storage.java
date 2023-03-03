package main.system;


import javafx.scene.image.Image;

import java.awt.Rectangle;
import java.util.Objects;

public class Storage {
    public final static Rectangle box_primaryFire = new Rectangle(0, 0, 16, 16);
    public static final Rectangle box_secondaryFire = new Rectangle(-7, -7, 46, 46);
    public static final Rectangle acidBreathBox = new Rectangle(0, 0, 10, 8);
    public static Image Lightning1, Lightning2, Lightning3, Lightning4, Lightning5, Lightning6, Lightning7, Lightning8, Lightning9, Lightning10;
    public static Image secondaryFire1, secondaryFire2, secondaryFire3, secondaryFire4, secondaryFire5, secondaryFire6;
    public static Image BigSLimewalk1;
    public static Image primaryFire1;

    public static Image acidBreath;
    public static Image shooterImage1;
    public static Image coin1, coin2, coin3, coin4;

    public Storage() {
    }

    public void loadImages() {
        loadAbilityImages();
        loadDropImages();
    }

    private void loadDropImages() {
        loadCoinImages();
    }


    private void loadCoinImages() {
        coin1 = setup("/items/drops/coin1.png");
        coin2 = setup("/items/drops/coin2.png");
        coin3 = setup("/items/drops/coin3.png");
        coin4 = setup("/items/drops/coin4.png");
    }

    private void loadAbilityImages() {
        getLightningImages();
        getEnergySphereImages();
        getPrimaryFireImages();
    }


    private void getEnergySphereImages() {
        secondaryFire1 = setupEnergySphere("SecondaryFire01.png");
        secondaryFire2 = setupEnergySphere("SecondaryFire02.png");
        secondaryFire3 = setupEnergySphere("SecondaryFire03.png");
        secondaryFire4 = setupEnergySphere("SecondaryFire04.png");
        secondaryFire5 = setupEnergySphere("SecondaryFire05.png");
        secondaryFire6 = setupEnergySphere("SecondaryFire06.png");
    }

    private void getLightningImages() {
        Lightning1 = setupLightning("lightn01.png");
        Lightning2 = setupLightning("lightn02.png");
        Lightning3 = setupLightning("lightn03.png");
        Lightning4 = setupLightning("lightn04.png");
        Lightning5 = setupLightning("lightn05.png");
        Lightning6 = setupLightning("lightn06.png");
        Lightning7 = setupLightning("lightn07.png");
        Lightning8 = setupLightning("lightn08.png");
        Lightning9 = setupLightning("lightn09.png");
        Lightning10 = setupLightning("lightn10.png");
    }

    private void getPrimaryFireImages() {
        primaryFire1 = setup("/projectiles/PrimaryFire/PrimaryFire01.png");
        acidBreath = setup("/projectiles/acidBreath/0.png");
    }

    private Image setup(String imagePath) {
        return new Image((Objects.requireNonNull(getClass().getResourceAsStream("/resources" + imagePath))));
    }

    private Image setupLightning(String imagePath) {
        return new Image((Objects.requireNonNull(getClass().getResourceAsStream("/projectiles/Lightning/" + imagePath))));
    }

    private Image setupEnergySphere(String imagePath) {
        return new Image((Objects.requireNonNull(getClass().getResourceAsStream("/projectiles/EnergySphere/" + imagePath))));
    }


    private Image setupRun(String imagePath) {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Entitys/enemies/" + imagePath)));
    }
}
