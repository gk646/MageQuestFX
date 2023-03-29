package gameworld.entities.boss;

import gameworld.entities.BOSS;
import gameworld.entities.damage.effects.buffs.BUF_RegenAura;
import gameworld.entities.loadinghelper.ResourceLoaderEntity;
import gameworld.entities.monsters.ENT_SkeletonArcher;
import gameworld.entities.monsters.ENT_SkeletonWarrior;
import gameworld.player.Player;
import gameworld.player.abilities.enemies.PRJ_AttackCone;
import gameworld.quest.Dialog;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;
import main.MainGame;
import main.system.enums.Zone;

import java.awt.Rectangle;

public class BOSS_Knight extends BOSS {
    private boolean active;
    public int healthPackCounter = 0;
    public boolean activate;
    private final String[] fightComments = new String[]{"Got enough yet?", "DIE!", "Got enough yet?", "You are a fool for even facing me!"};
    public boolean attack1, attack2, attack3, attack4, attack5;
    private int counter = 0;
    private boolean attack1Sound, attack4Sound, attackSound2, attack3Sound, attack5Sound;
    private boolean special;
    private boolean special_once;
    public boolean drawDialog;
    public boolean autoPilot;
    private boolean special_two = false;

    public BOSS_Knight(MainGame mg, int x, int y, int level, int health, Zone zone) {
        super(mg, x, y, level, health, zone);
        this.dialog = new Dialog();
        this.animation = new ResourceLoaderEntity("enemies/BOSSKnight");
        animation.load();
        this.collisionBox = new Rectangle(-15, -15, 63, 63);
        movementSpeed = 3;
        name = "Stone Knight";
    }

    @Override
    public void update() {
        if (!active) {
            health = maxHealth;
        }
        if (closeToPlayerAbsolute(500)) {
            activate = true;
        }
        hpBarOn = false;
        if (active) {
            super.update();
            if (!autoPilot && searchTicks % 750 == 0) {
                dialog.loadNewLine(fightComments[counter++]);
                if (counter > fightComments.length - 1) {
                    counter = (int) (Math.random() * fightComments.length);
                }
            }
            if (!autoPilot && health < 0.75 * maxHealth && !special_once) {
                this.BuffsDebuffEffects.add(new BUF_RegenAura(240, 2, 1, false, null));
                special = true;
                spriteCounter = 0;
                summonSkeletons();
                dialog.loadNewLine("Rise, my minions!");
                animation.playRandomSoundFromXToIndex(3, 3);
                healthPackCounter = 0;
                special_once = true;
            } else if (health < 0.25 * maxHealth && !special_two) {
                special_two = true;
                dialog.loadNewLine("Rise, my minions!");
                summonSkeletons();
                animation.playRandomSoundFromXToIndex(3, 3);
            } else if (!autoPilot && health < 0.5 * maxHealth) {
                if (healthPackCounter >= 600) {
                    special = true;
                    this.BuffsDebuffEffects.add(new BUF_RegenAura(240, 2, 1, false, null));
                    spriteCounter = 0;
                    healthPackCounter = 0;
                }
            }
            standardAttackScript();
            if (!autoPilot && !special && !attack2 && !attack3 && !attack1 && !attack4 && !attack5) {
                onPath = true;
                getNearestPlayer();
                searchPathBigEnemies(goalCol, goalRow, 30);
            }
            hitDelay++;
            searchTicks++;
            healthPackCounter++;
        }
    }

    private void standardAttackScript() {
        if (!autoPilot && collidingWithPlayer && !onPath && !attack2 && !attack3 && !attack1 && !attack5 && !attack4) {
            double num = Math.random();
            if (num < 0.2) {
                attack1 = true;
            } else if (num < 0.4) {
                attack2 = true;
            } else if (num < 0.6) {
                attack3 = true;
            } else if (num < 0.8) {
                attack4 = true;
            } else {
                attack5 = true;
            }
            mg.PROJECTILES.add(new PRJ_AttackCone((int) worldX, (int) worldY, 70, 64, 64, -8, -8, 3 * level));
            //animation.playGetHitSound(3);
            spriteCounter = 0;
            collidingWithPlayer = false;
        }
    }


    /**
     * @param gc Graphics context
     */
    @Override
    public void draw(GraphicsContext gc) {
        screenX = (int) (worldX - Player.worldX + Player.screenX - 87);
        screenY = (int) (worldY - Player.worldY + Player.screenY - 47);
        drawBuffsAndDeBuffs(gc);
        if (!dialog.dialogLine.equals("...")) {
            drawDialog = true;
            if (dialog.dialogRenderCounter >= 2_000) {
                dialog.dialogRenderCounter++;
                if (dialog.dialogRenderCounter >= 2_500) {
                    drawDialog = false;
                }
            }
        }
        if (drawDialog) {
            dialog.drawDialog(gc, this);
        }
        if (!active) {
            drawPray(gc);
        } else if (!activate) {
            drawPray(gc);
        } else if (special) {
            drawSpecial(gc);
        } else if (dead) {
            drawDeath(gc);
        } else if (attack1) {
            drawAttack1(gc);
        } else if (attack2) {
            drawAttack2(gc);
        } else if (attack3) {
            drawAttack3(gc);
        } else if (attack4) {
            drawAttack4(gc);
        } else if (attack5) {
            drawAttack5(gc);
        } else if (onPath) {
            drawRun(gc);
        } else {
            drawIdle(gc);
        }
        if (active) {
            drawBossHealthBar(gc);
        }
        spriteCounter++;
    }

    private void drawRun(GraphicsContext gc) {
        switch (spriteCounter % 160 / 20) {
            case 0 -> gc.drawImage(animation.run.get(0), screenX, screenY);
            case 1 -> gc.drawImage(animation.run.get(1), screenX, screenY);
            case 2 -> gc.drawImage(animation.run.get(2), screenX, screenY);
            case 3 -> gc.drawImage(animation.run.get(3), screenX, screenY);
            case 4 -> gc.drawImage(animation.run.get(4), screenX, screenY);
            case 5 -> gc.drawImage(animation.run.get(5), screenX, screenY);
            case 6 -> gc.drawImage(animation.run.get(6), screenX, screenY);
            case 7 -> gc.drawImage(animation.run.get(7), screenX, screenY);
        }
    }

    private void drawIdle(GraphicsContext gc) {
        switch (spriteCounter % 200 / 25) {
            case 0 -> gc.drawImage(animation.idle.get(0), screenX, screenY);
            case 1 -> gc.drawImage(animation.idle.get(1), screenX, screenY);
            case 2 -> gc.drawImage(animation.idle.get(2), screenX, screenY);
            case 3 -> gc.drawImage(animation.idle.get(3), screenX, screenY);
            case 4 -> gc.drawImage(animation.idle.get(4), screenX, screenY);
            case 5 -> gc.drawImage(animation.idle.get(5), screenX, screenY);
            case 6 -> gc.drawImage(animation.idle.get(6), screenX, screenY);
            case 7 -> gc.drawImage(animation.idle.get(7), screenX, screenY);
        }
    }

    private void drawAttack1(GraphicsContext gc) {
        switch (spriteCounter % 160 / 20) {
            case 0 -> gc.drawImage(animation.attack1.get(0), screenX, screenY);
            case 1 -> gc.drawImage(animation.attack1.get(1), screenX, screenY);
            case 2 -> {
                if (!attack1Sound) {
                    mg.PROJECTILES.add(new PRJ_AttackCone((int) worldX, (int) worldY, 30, 64, 64, -8, 0, 3 * level));
                    animation.playRandomSoundFromXToIndex(1, 1);
                    attack1Sound = true;
                }
                gc.drawImage(animation.attack1.get(2), screenX, screenY);
            }
            case 3 -> gc.drawImage(animation.attack1.get(3), screenX, screenY);
            case 4 -> gc.drawImage(animation.attack1.get(4), screenX, screenY);
            case 5 -> gc.drawImage(animation.attack1.get(5), screenX, screenY);
            case 6 -> gc.drawImage(animation.attack1.get(6), screenX, screenY);
            case 7 -> {
                attack1 = false;
                attack1Sound = false;
            }
        }
    }

    private void drawAttack2(GraphicsContext gc) {
        switch (spriteCounter % 180 / 30) {
            case 0 -> gc.drawImage(animation.attack2.get(0), screenX, screenY);
            case 1 -> gc.drawImage(animation.attack2.get(1), screenX, screenY);
            case 2 -> {
                if (!attackSound2) {
                    animation.playRandomSoundFromXToIndex(1, 1);
                    mg.PROJECTILES.add(new PRJ_AttackCone((int) worldX, (int) worldY, 30, 64, 64, 0, 0, 3 * level));
                    attackSound2 = true;
                }
                gc.drawImage(animation.attack2.get(2), screenX, screenY);
            }
            case 3 -> gc.drawImage(animation.attack2.get(3), screenX, screenY);
            case 4 -> gc.drawImage(animation.attack2.get(4), screenX, screenY);
            case 5 -> {
                attack2 = false;
                attackSound2 = false;
            }
        }
    }

    private void drawAttack3(GraphicsContext gc) {
        switch (spriteCounter % 150 / 30) {
            case 0 -> {
                if (!attack3Sound) {
                    animation.playRandomSoundFromXToIndex(1, 1);
                    mg.PROJECTILES.add(new PRJ_AttackCone((int) worldX, (int) worldY, 30, 60, 20, -20, 0, 2 * level));
                    attack3Sound = true;
                }
                gc.drawImage(animation.attack3.get(0), screenX, screenY);
            }
            case 1 -> gc.drawImage(animation.attack3.get(1), screenX, screenY);
            case 2 -> gc.drawImage(animation.attack3.get(2), screenX, screenY);
            case 3 -> gc.drawImage(animation.attack3.get(3), screenX, screenY);
            case 4 -> {
                attack3 = false;
                attack3Sound = false;
            }
        }
    }

    private void drawAttack4(GraphicsContext gc) {
        switch (spriteCounter % 160 / 20) {
            case 0 -> gc.drawImage(animation.attack4.get(0), screenX, screenY);
            case 1 -> {
                if (!attack4Sound) {
                    animation.playRandomSoundFromXToIndex(0, 0);
                    mg.PROJECTILES.add(new PRJ_AttackCone((int) worldX, (int) worldY, 30, 80, 40, -20, +10, 6 * level));
                    attack4Sound = true;
                }
                gc.drawImage(animation.attack4.get(1), screenX, screenY);
            }
            case 2 -> gc.drawImage(animation.attack4.get(2), screenX, screenY);
            case 3 -> gc.drawImage(animation.attack4.get(3), screenX, screenY);
            case 4 -> gc.drawImage(animation.attack4.get(4), screenX, screenY);
            case 5 -> gc.drawImage(animation.attack4.get(5), screenX, screenY);
            case 6 -> gc.drawImage(animation.attack4.get(6), screenX, screenY);
            case 7 -> {
                attack4Sound = false;
                attack4 = false;
            }
        }
    }

    private void drawAttack5(GraphicsContext gc) {
        switch (spriteCounter % 140 / 20) {
            case 0 -> gc.drawImage(animation.attack5.get(0), screenX, screenY);
            case 1 -> {
                if (!attack5Sound) {
                    attack5Sound = true;
                    mg.PROJECTILES.add(new PRJ_AttackCone((int) worldX, (int) worldY, 30, 80, 40, -20, +10, 5 * level));
                    animation.playRandomSoundFromXToIndex(2, 2);
                }
                gc.drawImage(animation.attack5.get(1), screenX, screenY);
            }
            case 2 -> gc.drawImage(animation.attack5.get(2), screenX, screenY);
            case 3 -> gc.drawImage(animation.attack5.get(3), screenX, screenY);
            case 4 -> gc.drawImage(animation.attack5.get(4), screenX, screenY);
            case 5 -> gc.drawImage(animation.attack5.get(5), screenX, screenY);
            case 6 -> {
                attack5 = false;
                attack5Sound = false;
            }
        }
    }

    private void drawDeath(GraphicsContext gc) {
        mg.sound.fadeOut(mg.sound.BossMusic1, 1, 5);
        switch (spriteCounter % 150 / 30) {
            case 0 -> gc.drawImage(animation.dead.get(0), screenX, screenY);
            case 1 -> gc.drawImage(animation.dead.get(1), screenX, screenY);
            case 2 -> gc.drawImage(animation.dead.get(2), screenX, screenY);
            case 3 -> gc.drawImage(animation.dead.get(3), screenX, screenY);
            case 4 -> AfterAnimationDead = true;
        }
    }

    private void drawPray(GraphicsContext gc) {
        if (activate) {
            switch (spriteCounter % 120 / 30) {
                case 0 -> {
                    gc.drawImage(animation.idle2.get(9), screenX, screenY);
                    return;
                }
                case 1 -> {
                    gc.drawImage(animation.idle2.get(10), screenX, screenY);
                    return;
                }
                case 2 -> {
                    gc.drawImage(animation.idle2.get(11), screenX, screenY);
                    return;
                }
                case 3 -> {
                    mg.sound.BossMusic1.seek(Duration.ZERO);
                    mg.sound.BossMusic1.play();
                    active = true;
                    return;
                }
            }
        }
        switch (spriteCounter % 210 / 30) {
            case 0 -> gc.drawImage(animation.idle2.get(2), screenX, screenY);
            case 1 -> gc.drawImage(animation.idle2.get(3), screenX, screenY);
            case 2 -> gc.drawImage(animation.idle2.get(4), screenX, screenY);
            case 3 -> gc.drawImage(animation.idle2.get(5), screenX, screenY);
            case 4 -> gc.drawImage(animation.idle2.get(6), screenX, screenY);
            case 5 -> gc.drawImage(animation.idle2.get(7), screenX, screenY);
            case 6 -> gc.drawImage(animation.idle2.get(8), screenX, screenY);
        }
    }

    private void drawSpecial(GraphicsContext gc) {
        switch (spriteCounter % 270 / 30) {
            case 0 -> gc.drawImage(animation.special.get(0), screenX, screenY);
            case 1 -> gc.drawImage(animation.special.get(1), screenX, screenY);
            case 2 -> gc.drawImage(animation.special.get(2), screenX, screenY);
            case 3 -> gc.drawImage(animation.special.get(3), screenX, screenY);
            case 4 -> gc.drawImage(animation.special.get(4), screenX, screenY);
            case 5 -> gc.drawImage(animation.special.get(5), screenX, screenY);
            case 6 -> gc.drawImage(animation.special.get(6), screenX, screenY);
            case 7 -> gc.drawImage(animation.special.get(7), screenX, screenY);
            case 8 -> {
                special = false;
                healthPackCounter = 0;
            }
        }
    }

    private void summonSkeletons() {
        for (int j = 89; j <= 94; j += 5) {
            for (int i = 51; i <= 61; i += 10) {
                if (Math.random() > 0.5) {
                    mg.ent_control.addToEntities.add(new ENT_SkeletonArcher(mg, i * 48, j * 48, 3, Zone.Hillcrest));
                } else {
                    mg.ent_control.addToEntities.add(new ENT_SkeletonWarrior(mg, i * 48, j * 48, 3, Zone.Hillcrest));
                }
            }
        }
    }
}
