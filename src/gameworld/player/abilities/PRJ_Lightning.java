package gameworld.player.abilities;

import gameworld.PRJ_Control;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;
import main.system.ui.Effects;

import java.awt.Rectangle;

public class PRJ_Lightning extends PRJ_Control {

    /**
     * What happens when you press "2". Part of
     * {@link PRJ_Control}
     */
    public PRJ_Lightning(MainGame mainGame) {
        super(mainGame);

        //-------VALUES-----------
        this.projectileHeight = 92;
        this.projectileWidth = 70;
        this.collisionBox = new Rectangle(30, 30, 40, 30);

        //------POSITION-----------
        this.worldPos = new java.awt.geom.Point2D.Double(mainGame.player.worldX + mg.inputH.lastMousePosition.x - mg.HALF_WIDTH - 24, mainGame.player.worldY + mg.inputH.lastMousePosition.y - mg.HALF_HEIGHT - 24);
        getImages();
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setEffect(Effects.blueGlow);
        spriteCounter++;
        int spriteIndex = spriteCounter / 6;
        switch (spriteIndex) {
            case 0 ->
                    gc.drawImage(projectileImage1, (int) worldPos.x - mg.player.worldX + mg.HALF_WIDTH, (int) worldPos.y - mg.player.worldY + mg.HALF_HEIGHT - 50, projectileWidth, projectileHeight);
            case 1 ->
                    gc.drawImage(projectileImage2, (int) worldPos.x - mg.player.worldX + mg.HALF_WIDTH, (int) worldPos.y - mg.player.worldY + mg.HALF_HEIGHT - 50, projectileWidth, projectileHeight);
            case 2 ->
                    gc.drawImage(projectileImage2, (int) worldPos.x - mg.player.worldX + mg.HALF_WIDTH, (int) worldPos.y - mg.player.worldY + mg.HALF_HEIGHT - 50, projectileWidth, projectileHeight);
            case 4 ->
                    gc.drawImage(projectileImage3, (int) worldPos.x - mg.player.worldX + mg.HALF_WIDTH, (int) worldPos.y - mg.player.worldY + mg.HALF_HEIGHT - 50, projectileWidth, projectileHeight);
            case 5 ->
                    gc.drawImage(projectileImage4, (int) worldPos.x - mg.player.worldX + mg.HALF_WIDTH, (int) worldPos.y - mg.player.worldY + mg.HALF_HEIGHT - 50, projectileWidth, projectileHeight);
            case 6 ->
                    gc.drawImage(projectileImage5, (int) worldPos.x - mg.player.worldX + mg.HALF_WIDTH, (int) worldPos.y - mg.player.worldY + mg.HALF_HEIGHT - 50, projectileWidth, projectileHeight);
            case 7 ->
                    gc.drawImage(projectileImage6, (int) worldPos.x - mg.player.worldX + mg.HALF_WIDTH, (int) worldPos.y - mg.player.worldY + mg.HALF_HEIGHT - 50, projectileWidth, projectileHeight);
            case 8 ->
                    gc.drawImage(projectileImage7, (int) worldPos.x - mg.player.worldX + mg.HALF_WIDTH, (int) worldPos.y - mg.player.worldY + mg.HALF_HEIGHT - 50, projectileWidth, projectileHeight);
            case 9 ->
                    gc.drawImage(projectileImage8, (int) worldPos.x - mg.player.worldX + mg.HALF_WIDTH, (int) worldPos.y - mg.player.worldY + mg.HALF_HEIGHT - 50, projectileWidth, projectileHeight);
            case 10 ->
                    gc.drawImage(projectileImage9, (int) worldPos.x - mg.player.worldX + mg.HALF_WIDTH, (int) worldPos.y - mg.player.worldY + mg.HALF_HEIGHT - 50, projectileWidth, projectileHeight);
            case 11 ->
                    gc.drawImage(projectileImage10, (int) worldPos.x - mg.player.worldX + mg.HALF_WIDTH, (int) worldPos.y - mg.player.worldY + mg.HALF_HEIGHT - 50, projectileWidth, projectileHeight);
            case 12 ->
                    gc.drawImage(projectileImage10, (int) worldPos.x - mg.player.worldX + mg.HALF_WIDTH, (int) worldPos.y - mg.player.worldY + mg.HALF_HEIGHT - 50, projectileWidth, projectileHeight);
            case 13 -> dead = true;
        }
        gc.setEffect(null);
    }

    public void update() {

    }

    private void getImages() {
        projectileImage1 = mg.imageSto.Lightning1;
        projectileImage2 = mg.imageSto.Lightning2;
        projectileImage3 = mg.imageSto.Lightning3;
        projectileImage4 = mg.imageSto.Lightning4;
        projectileImage5 = mg.imageSto.Lightning5;
        projectileImage6 = mg.imageSto.Lightning6;
        projectileImage7 = mg.imageSto.Lightning7;
        projectileImage8 = mg.imageSto.Lightning8;
        projectileImage9 = mg.imageSto.Lightning9;
        projectileImage10 = mg.imageSto.Lightning10;
    }
}
