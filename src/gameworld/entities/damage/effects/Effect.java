package gameworld.entities.damage.effects;

import gameworld.entities.damage.DamageType;
import javafx.scene.image.Image;

abstract public class Effect {
    int full_duration, rest_duration;
    DamageType type;
    int flat_damage;
    String name;
    Image icon;
    String description;
}
