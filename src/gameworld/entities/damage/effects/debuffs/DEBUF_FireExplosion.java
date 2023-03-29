package gameworld.entities.damage.effects.debuffs;

import gameworld.entities.ENTITY;
import gameworld.entities.damage.effects.Effect;
import gameworld.player.PROJECTILE;
import gameworld.player.abilities.FIRE.PRJ_BurnSource;
import gameworld.player.abilities.FIRE.PRJ_FireExplosion;

import java.util.List;

public class DEBUF_FireExplosion extends Effect {
    private final List<PROJECTILE> projectiles;

    public DEBUF_FireExplosion(List<PROJECTILE> projectiles) {
        this.projectiles = projectiles;
        this.rest_duration = 0;
    }

    /**
     * @param entity
     */
    @Override
    public void tick(ENTITY entity) {
        for (Effect effect : entity.BuffsDebuffEffects) {
            if (effect.sourceProjectile == PRJ_BurnSource.class) {
                effect.rest_duration = 0;
                projectiles.add(new PRJ_FireExplosion(110, (int) entity.worldX, (int) entity.worldY));
                break;
            }
        }
        rest_duration = 0;
    }

    /**
     * @return
     */
    @Override
    public Effect clone() {
        return this;
    }
}
