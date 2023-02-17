package gameworld.player.abilities;

import gameworld.PRJ_Control;
import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import main.MainGame;
import main.system.Storage;
import main.system.sound.Sound;
import main.system.ui.Effects;

import java.awt.Point;
import java.awt.geom.Point2D;

public class PRJ_EnergySphere extends PRJ_Control {
    MediaPlayer activation;
    MediaPlayer hit;

    /**
     * Energy Sphere projectile
     */
    public PRJ_EnergySphere(MainGame mainGame) {
        super(mainGame);
        activation = new MediaPlayer(Sound.energySphereBeginning);
        activation.setVolume(0.12);
        hit = new MediaPlayer(Sound.energySphereHit);
        hit.setVolume(0.2);
        activation.play();
        //-------VALUES-----------
        this.movementSpeed = 3;
        this.projectileHeight = 32;
        this.projectileWidth = 32;
        this.collisionBox = mg.imageSto.box_secondaryFire;
        this.direction = "downleftrightup";

        //------POSITION-----------
        this.worldPos = new java.awt.geom.Point2D.Double(Player.worldX + 24 - projectileWidth / 2.0f, Player.worldY + 24 - projectileHeight / 2.0f);
        this.endPos = new Point((int) (worldPos.x + 650), (int) (worldPos.y + 650));
        this.updateVector = getTrajectory(mainGame.inputH.lastMousePosition);
        getPlayerImage();
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setEffect(Effects.blueGlow);
        switch (spriteCounter % 60 / 10) {
            case 0 ->
                    gc.drawImage(projectileImage1, (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY, projectileWidth, projectileHeight);
            case 1 ->
                    gc.drawImage(projectileImage2, (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY, projectileWidth, projectileHeight);
            case 2 ->
                    gc.drawImage(projectileImage3, (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY, projectileWidth, projectileHeight);
            case 3 ->
                    gc.drawImage(projectileImage4, (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY, projectileWidth, projectileHeight);
            case 4 ->
                    gc.drawImage(projectileImage5, (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY, projectileWidth, projectileHeight);
            case 5 ->
                    gc.drawImage(projectileImage6, (int) worldPos.x - Player.worldX + Player.screenX, (int) worldPos.y - Player.worldY + Player.screenY, projectileWidth, projectileHeight);
        }
        spriteCounter++;
        gc.setEffect(null);
    }

    @Override
    public void update() {
        outOfBounds();
        tileCollision();
        worldPos.x += updateVector.x * movementSpeed;
        worldPos.y += updateVector.y * movementSpeed;
        if (dead) {
            playHitSound();
            activation.stop();
        }
    }

    @Override
    public void playHitSound() {
        if (System.currentTimeMillis() - lastHitTime >= 100) {
            hit.seek(Duration.ZERO);
            hit.play();
            lastHitTime = System.currentTimeMillis();
        }
    }

    private Point2D.Double getTrajectory(Point mousePosition) {
        double angle = Math.atan2(mousePosition.y - Player.screenY - 24, mousePosition.x - Player.screenX - 24);
        return new Point2D.Double(Math.cos(angle), Math.sin(angle));
    }

    private void getPlayerImage() {
        projectileImage1 = Storage.secondaryFire1;
        projectileImage2 = Storage.secondaryFire2;
        projectileImage3 = Storage.secondaryFire3;
        projectileImage4 = Storage.secondaryFire4;
        projectileImage5 = Storage.secondaryFire5;
        projectileImage6 = Storage.secondaryFire6;
    }

    private void controlSound() {
        sound.play();
        // When a projectile is fired


        sound.seek(Duration.ZERO); // Go back to the beginning of the sound
        sound.setCycleCount(MediaPlayer.INDEFINITE); // Loop the middle section
        sound.setStartTime(Duration.millis(720)); // Start looping from the second of the sound

        // When the projectile hits something
        sound.stop(); // Stop looping the middle section
        sound.setStartTime(Duration.seconds(5)); // Go to the ending of the sound
        sound.setCycleCount(1); // Play the ending once
        sound.play();
    }
}
