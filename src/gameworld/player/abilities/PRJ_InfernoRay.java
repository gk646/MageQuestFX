package gameworld.player.abilities;

import gameworld.entities.damage.DamageType;
import gameworld.player.PROJECTILE;
import gameworld.player.Player;
import gameworld.player.ProjectilePreloader;
import gameworld.player.ProjectileType;
import input.InputHandler;
import javafx.scene.canvas.GraphicsContext;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Point2D;


public class PRJ_InfernoRay extends PROJECTILE {
    double RightAngle;

    public PRJ_InfernoRay(float weapon_damage_percent) {
        this.weapon_damage_percent = weapon_damage_percent;
        this.type = DamageType.Fire;
        damageDead = true;
        this.resource = ProjectilePreloader.infernoRay;
        //this.sounds[0] = new MediaPlayer(resource.sounds.get(0).getMedia());
        //this.sounds[0].setOnEndOfMedia(() -> sounds[0].dispose());
        this.worldPos = new Point2D.Double(Player.worldX, Player.worldY);
        collisionBox = new Rectangle(0, 0, 282, 48);
        Polygon polygon = new Polygon();
        //polygon.addPoint();
        this.collisionBox = polygon.getBounds();
        direction = "leftrightdownup";
        projectileType = ProjectileType.OneHitNoDMG;
        double dx = InputHandler.instance.lastMousePosition.getX() - (Player.screenX + 24);
        double dy = InputHandler.instance.lastMousePosition.getY() - (Player.screenY + 24);
        RightAngle = Math.toDegrees(Math.atan2(dy, dx));
        //playStartSound();
    }


    @Override
    public void draw(GraphicsContext gc) {
        gc.save();
        gc.translate(worldPos.x - Player.worldX + Player.screenX + 24, worldPos.y - Player.worldY + Player.screenY + 31);
        gc.rotate(RightAngle);
        switch (spriteCounter % 120 / 10) {
            case 0 -> gc.drawImage(resource.images1.get(0), 0, -31);
            case 1 -> gc.drawImage(resource.images1.get(1), 0, -31);
            case 2 -> gc.drawImage(resource.images1.get(2), 0, -31);
            case 3 -> gc.drawImage(resource.images1.get(3), 0, -31);
            case 4 -> gc.drawImage(resource.images1.get(4), 0, -31);
            case 5 -> gc.drawImage(resource.images1.get(5), 0, -31);
            case 6 -> gc.drawImage(resource.images1.get(6), 0, -31);
            case 7 -> gc.drawImage(resource.images1.get(7), 0, -31);
            case 8 -> gc.drawImage(resource.images1.get(8), 0, -31);
            case 9 -> gc.drawImage(resource.images1.get(9), 0, -31);
            case 10 -> gc.drawImage(resource.images1.get(10), 0, -31);

            case 11 -> dead = true;
        }
        gc.restore();
        gc.fillPolygon(new double[]{123, 123}, new double[]{123, 123}, 2);
        //  gc.fillRect(worldPos.x + collisionBox.x - Player.worldX + Player.screenX, worldPos.y + collisionBox.y - Player.worldY + Player.screenY, collisionBox.width, collisionBox.height);

        if (spriteCounter == 30) {
            damageDead = false;
        }
        spriteCounter++;
    }

    @Override
    public void playHitSound() {

    }

    /**
     *
     */
    @Override
    public void update() {

    }
}
