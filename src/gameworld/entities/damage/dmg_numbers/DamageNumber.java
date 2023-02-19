package gameworld.entities.damage.dmg_numbers;

import gameworld.entities.ENTITY;
import gameworld.entities.damage.DamageType;
import gameworld.player.Player;
import javafx.scene.canvas.GraphicsContext;
import main.system.ui.Colors;

public class DamageNumber {

    private final int damage;
    private final DamageType type;
    private final int offSetX;
    public float offSetY = 35;

    public DamageNumber(int damage, DamageType type) {
        this.damage = damage;
        this.type = type;
        if (Math.random() >= 0.5) {
            offSetX = 45;
        } else {
            offSetX = -25;
        }
    }

    public void draw(GraphicsContext gc, ENTITY entity) {
        switch (type) {
            case DarkDMG -> gc.setFill(Colors.dark_magic_purple);
            case FireDMG -> gc.setFill(Colors.fire_red);
            case ArcaneDMG -> gc.setFill(Colors.arcane_blue);
            case PoisonDMG -> gc.setFill(Colors.poison_green);
            case PhysicalDMG -> gc.setFill(Colors.physical_grey);
        }
        gc.fillText(String.valueOf(damage), entity.worldX + offSetX - Player.worldX + Player.screenX, entity.worldY + offSetY - Player.worldY + Player.screenY);
        offSetY -= 0.6;
    }
}
