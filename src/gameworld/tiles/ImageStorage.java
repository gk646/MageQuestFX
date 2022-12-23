package gameworld.tiles;

import main.MainGame;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ImageStorage {
    public BufferedImage Lightning1, Lightning2, Lightning3, Lightning4, Lightning5, Lightning6, Lightning7, Lightning8, Lightning9, Lightning10;
    public BufferedImage secondaryFire1, secondaryFire2, secondaryFire3, secondaryFire4, secondaryFire5, secondaryFire6;
    public BufferedImage primaryFire1;
    public BufferedImage gruntImage1;
    MainGame mg;

    public ImageStorage(MainGame mg) {
        this.mg = mg;
    }

    public void loadImages() {
        loadAbilityImages();
        loadEntityImages();
    }

    private void loadEntityImages() {
        getGruntImages();
    }

    private void loadAbilityImages() {
        getLightningImages();
        getEnergySphereImages();
        getPrimaryFireImages();
    }

    private void getGruntImages() {
        gruntImage1 = setup("/Entitys/enemies/enemy01.png");
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
    }

    private BufferedImage setup(String imagePath) {

        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/resources" + imagePath))));
            scaledImage = mg.utilities.scaleImage(scaledImage, 70, 92);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaledImage;
    }

    private BufferedImage setupLightning(String imagePath) {

        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/projectiles/Lightning/" + imagePath))));
            scaledImage = mg.utilities.scaleImage(scaledImage, 70, 92);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaledImage;
    }

    private BufferedImage setupEnergySphere(String imagePath) {
        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read((Objects.requireNonNull(getClass().getResourceAsStream("/projectiles/EnergySphere/" + imagePath))));
            scaledImage = mg.utilities.scaleImage(scaledImage, 70, 92);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scaledImage;
    }
}
