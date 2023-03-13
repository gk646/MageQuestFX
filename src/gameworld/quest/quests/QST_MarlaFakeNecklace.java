package gameworld.quest.quests;

import gameworld.entities.ENTITY;
import gameworld.entities.NPC;
import gameworld.entities.boss.BOSS_Knight;
import gameworld.entities.monsters.ENT_Mushroom;
import gameworld.entities.monsters.ENT_Snake;
import gameworld.entities.monsters.ENT_Wolf;
import gameworld.entities.npcs.quests.NPC_Aria;
import gameworld.entities.npcs.quests.NPC_Marla;
import gameworld.entities.props.DeadWolf;
import gameworld.quest.QUEST;
import gameworld.quest.QUEST_NAME;
import gameworld.quest.dialog.DialogStorage;
import gameworld.world.WorldController;
import javafx.util.Duration;
import main.MainGame;
import main.system.enums.State;
import main.system.enums.Zone;
import main.system.rendering.WorldRender;

import java.awt.Point;

public class QST_MarlaFakeNecklace extends QUEST {
    private int enemiesKilled;
    private boolean KilledAria;
    boolean once;
    private boolean cutscenefinish;

    public QST_MarlaFakeNecklace(MainGame mg, boolean completed) {
        super(mg);
        logicName = QUEST_NAME.TheFakeNecklace;
        quest_id = logicName.val;
        name = "The Fake Necklace";
        if (!completed) {
            mg.sqLite.setQuestActive(quest_id);
        } else {
            mg.sqLite.finishQuest(quest_id);
        }
    }

    /**
     *
     */
    @Override
    public void update() {
        for (int i = 0; i < mg.npcControl.NPC_Active.size(); i++) {
            NPC npc = mg.npcControl.NPC_Active.get(i);
            if (npc instanceof NPC_Marla) {
                interactWithNpc(npc, DialogStorage.MarlaNecklace);
                if (progressStage == 1 && objective1Progress == 0) {
                    updateObjective("Talk to Marla", 0);
                    npc.dialog.loadNewLine(DialogStorage.MarlaNecklace[0]);
                    objective1Progress++;
                } else if (progressStage == 8) {
                    objective1Progress = 0;
                    int num = npc.dialog.drawChoice("Sure, ill help you out", "Maybe later", null, null);
                    if (num == 10) {
                        progressStage = 10;
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 10);
                    } else if (num == 20) {
                        nextStage();
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 9);
                    }
                } else if (progressStage == 9) {
                    npc.blockInteraction = true;
                    updateObjective("Come back if you change your mind", 0);
                    if (!playerCloseToAbsolute((int) npc.worldX, (int) npc.worldY, 600)) {
                        npc.show_dialog = false;
                        progressStage = 8;
                        npc.blockInteraction = false;
                    }
                } else if (progressStage == 11) {
                    npc.blockInteraction = true;
                    if (objective3Progress == 0) {
                        mg.ENTITIES.add(new DeadWolf(82, 50, Zone.Hillcrest));
                        mg.ENTITIES.add(new DeadWolf(84, 51, Zone.Hillcrest));
                        mg.ENTITIES.add(new DeadWolf(82, 50, Zone.Hillcrest));
                        mg.ENTITIES.add(new DeadWolf(82, 50, Zone.Hillcrest));
                        mg.npcControl.NPC_Active.add(new NPC_Aria(mg, 59, 55, Zone.Hillcrest));
                        objective3Progress++;
                    }
                    updateObjective("Head east and pickup the mysterious adventurers trail!", 0);
                    addQuestMarker(quest_id + "" + progressStage, 83, 41, Zone.Hillcrest);
                    if (playerInsideRectangle(new Point(81, 40), new Point(85, 45))) {
                        progressStage = 13;
                        updateObjective("Look around", 0);
                        removeQuestMarker(quest_id + "" + 11);
                        mg.sqLite.updateQuestFacts(quest_id, 1, 1);
                        objective3Progress = 0;
                    }
                } else if (progressStage == 75) {
                    npc.blockInteraction = true;
                } else if (progressStage == 77) {
                    npc.blockInteraction = true;
                } else if (progressStage == 79) {
                    if (objective1Progress == 0) {
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 79);
                    }
                    objective1Progress++;
                    if (objective1Progress >= 450) {
                        nextStage();
                        objective1Progress = 0;
                    }
                } else if (progressStage == 82) {
                    if (objective1Progress == 0) {
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 82);
                        objective1Progress = 1;
                    }
                    int num = npc.dialog.drawChoice("*Pay the 50 coins*", "*Do nothing*", null, null);
                    if (num == 10) {
                        if (mg.player.coins >= 50) {
                            progressStage = 100;
                            mg.player.coins -= 50;
                            mg.sound.playEffectSound(5);
                            objective1Progress = 0;
                        } else {

                        }
                    } else if (num == 20) {
                        nextStage();
                        objective1Progress = 0;
                    }
                }
            }
            if (npc instanceof NPC_Aria) {
                interactWithNpc(npc, DialogStorage.MarlaNecklace);
                if (progressStage >= 50 && progressStage <= 53) {
                    if (playerInsideRectangle(new Point(51, 88), new Point(61, 93))) {
                        progressStage = 54;
                        objective1Progress = 0;
                        objective2Progress = 0;
                        objective3Progress = 0;
                    }
                }
                if (progressStage == 13) {
                    if (objective3Progress == 0 && playerCloseToAbsolute((int) npc.worldX, (int) npc.worldY, 500)) {
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 13);
                        objective3Progress++;
                    }
                    int num = npc.dialog.drawChoice("Beat you to what?", null, null, null);
                    if (num == 10) {
                        nextStage();
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 14);
                    }
                } else if (progressStage == 14) {
                    objective3Progress = 0;
                    npc.blockInteraction = true;
                    if (npc.dialog.dialogRenderCounter == 2000) {
                        nextStage();
                        updateObjective("Help Aria through the ambush", 0);
                        enemiesKilled = mg.prj_control.ENEMIES_KILLED;
                        mg.ENTITIES.add(new ENT_Wolf(mg, 52, 52, 2, Zone.Hillcrest));
                        mg.ENTITIES.add(new ENT_Mushroom(mg, 49, 58, 2, Zone.Hillcrest));
                        mg.ENTITIES.add(new ENT_Mushroom(mg, 60, 63, 2, Zone.Hillcrest));
                        mg.ENTITIES.add(new ENT_Mushroom(mg, 53, 61, 2, Zone.Hillcrest));
                        mg.ENTITIES.add(new ENT_Wolf(mg, 57, 50, 2, Zone.Hillcrest));
                        mg.ENTITIES.add(new ENT_Snake(mg, 48, 62, 2, Zone.Hillcrest));
                    }
                } else if (progressStage == 15) {
                    if (mg.prj_control.ENEMIES_KILLED >= enemiesKilled + 6) {
                        npc.blockInteraction = false;
                        if (objective3Progress == 0) {
                            loadDialogStage(npc, DialogStorage.MarlaNecklace, 15);
                            objective3Progress = 1;
                        }
                        int num = npc.dialog.drawChoice("You couldn't have done it without me!", null, null, null);
                        if (num == 10) {
                            nextStage();
                            loadDialogStage(npc, DialogStorage.MarlaNecklace, 16);
                        }
                    }
                } else if (progressStage == 16) {
                    int num = npc.dialog.drawChoice("Ill come along!", null, null, null);
                    if (num == 10) {
                        nextStage();
                        updateObjective("Accompany Aria", 0);
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, progressStage);
                    }
                } else if (progressStage == 17) {
                    moveToTile(npc, 38, 61, new Point(53, 59));
                } else if (progressStage == 18) {
                    //TODO add usable item and lock q slot
                } else if (progressStage == 19) {
                    int num = npc.dialog.drawChoice("Yep, lets go", "The necklace is fake!", null, null);
                    if (num == 10) {
                        nextStage();
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 20);
                    } else if (num == 20) {
                        progressStage = 75;
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 75);
                        updateObjective("Meet Aria in town to confront Marla", 0);
                        mg.sqLite.updateQuestFacts(quest_id, 1, -1);
                    }
                } else if (progressStage == 20) {
                    moveToTile(npc, 68, 73, new Point(49, 75));
                } else if (progressStage == 22) {
                    if (moveToTile(npc, 76, 71)) {
                        nextStage();
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 23);
                    }
                } else if (progressStage == 23) {
                    if (mg.qPanel.questIsFinished(QUEST_NAME.HillcrestPuzzle)) {
                        progressStage = 24;
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 24);
                    }
                    int num = npc.dialog.drawChoice("Iam ready", null, null, null);
                    if (num == 10) {
                        progressStage = 25;
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 25);
                    }
                } else if (progressStage == 24) {
                    int num = npc.dialog.drawChoice("Iam ready", null, null, null);
                    if (num == 10) {
                        nextStage();
                        updateObjective("Rest and talk with Aria", 0);
                        addQuestMarker("rest", 90, 56, Zone.Hillcrest);
                        mg.sqLite.updateQuestFacts(quest_id, 1, 3);
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 25);
                    }
                } else if (progressStage == 25) {
                    if (moveToTile(npc, 90, 57, new Point(86, 62))) {
                        nextStage();
                    }
                } else if (progressStage == 28) {
                    int num = npc.dialog.drawChoice("Danger is my second name!", "Loot, loot, LOOT!", "For the friends", null);
                    if (num == 10) {
                        progressStage = 29;
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 29);
                    } else if (num == 20) {
                        progressStage = 30;
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 30);
                    } else if (num == 30) {
                        progressStage = 31;
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 31);
                    }
                } else if (progressStage == 29 || progressStage == 30 || progressStage == 31) {
                    progressStage = 32;
                } else if (progressStage == 32) {
                    int num = npc.dialog.drawChoice("Tell me something about you", null, null, null);
                    if (num == 10) {
                        nextStage();
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 33);
                    }
                } else if (progressStage == 38) {
                    removeQuestMarker("rest");
                    moveToTile(npc, 97, 76);
                } else if (progressStage == 39) {
                    WorldRender.worldData1[97][77] = -1;
                    moveToTile(npc, 91, 96);
                } else if (progressStage == 40) {
                    if (WorldController.currentWorld == Zone.Hillcrest) {
                        WorldRender.worldData1[97][77] = -1;
                    }
                    int num = npc.dialog.drawChoice("The necklace is fake!", "Its just you! *wink*", null, null);
                    if (num == 10) {
                        progressStage = 75;
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 75);
                        mg.sqLite.updateQuestFacts(quest_id, 1, -1);
                        updateObjective("Meet Aria back in town", 0);
                        addQuestMarker("cave", 41, 29, Zone.Hillcrest);
                    } else if (num == 20) {
                        nextStage();
                        updateObjective("Find a way through the cave", 0);
                        addQuestMarker("cave", 20, 9, Zone.Hillcrest_Mountain_Cave);
                        if (WorldController.currentWorld != Zone.Hillcrest) {
                            npc.update();
                        }
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 41);
                        mg.sqLite.updateQuestFacts(quest_id, 1, 4);
                    }
                } else if (progressStage == 41) {
                    npc.blockInteraction = true;
                    objective1Progress++;
                    if (objective1Progress >= 200) {
                        if (moveToTile(npc, 87, 94)) {
                            npc.zone = Zone.Hillcrest_Mountain_Cave;
                            npc.setPosition(56, 58);
                            nextStage();
                            objective1Progress = 0;
                        }
                    }
                } else if (progressStage == 42) {
                    if (WorldController.currentWorld == Zone.Hillcrest_Mountain_Cave) {
                        moveToTile(npc, 45, 54);
                        if (objective1Progress == 0) {
                            loadDialogStage(npc, DialogStorage.MarlaNecklace, 42);
                            npc.blockInteraction = false;
                            objective1Progress++;
                        }
                    }
                } else if (progressStage == 43) {
                    if (moveToTile(npc, 10, 53, new Point(36, 50))) {
                        if (objective3Progress == 0) {
                            loadDialogStage(npc, DialogStorage.MarlaNecklace, 44);
                            objective3Progress++;
                        }
                        if (npc.dialog.dialogRenderCounter == 2000) {
                            objective3Progress++;
                            if (objective3Progress >= 160) {
                                WorldRender.worldData1[9][53] = -1;
                                mg.sound.playEffectSound(8);
                                nextStage();
                            }
                        }
                    }
                } else if (progressStage == 44) {
                    moveToTile(npc, 22, 30, new Point(11, 45));
                } else if (progressStage == 45) {
                    npc.blockInteraction = true;
                    int num = npc.dialog.drawChoice("Sure thing!", "I hereby give you full rights to go first", null, null);
                    if (num == 10) {
                        nextStage();
                        objective1Progress = 0;
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 46);
                    } else if (num == 20) {
                        progressStage = 47;
                        npc.blockInteraction = false;
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 47);
                    }
                } else if (progressStage == 46) {
                    npc.blockInteraction = false;
                    if (playerInsideRectangle(new Point(33, 27), new Point(36, 31))) {
                        objective1Progress = 1;
                    }
                    if (objective1Progress == 1) {
                        moveToTile(npc, 35, 28);
                        progressStage = 48;
                    }
                } else if (progressStage == 47) {
                    moveToTile(npc, 35, 28);
                } else if (progressStage == 48) {
                    moveToTile(npc, 20, 9, new Point(39, 22), new Point(29, 9));
                } else if (progressStage == 49) {
                    npc.update();
                    if (moveToTile(npc, 0, 14)) {
                        npc.zone = Zone.Hillcrest;
                        npc.setPosition(75, 85);
                        nextStage();
                        objective1Progress = 0;
                        objective2Progress = 0;
                        objective3Progress = 0;
                    }
                } else if (progressStage == 50) {
                    if (WorldController.currentWorld == Zone.Hillcrest && objective2Progress == 0) {
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 50);
                        objective2Progress++;
                    }
                    if (objective1Progress == 0) {
                        mg.ENTITIES.add(new BOSS_Knight(mg, 56 * 48, 91 * 48, 3, 3, Zone.Hillcrest));
                        objective1Progress++;
                    }
                } else if (progressStage == 51) {
                    moveToTile(npc, 69, 92);
                } else if (progressStage == 52) {
                    int num = npc.dialog.drawChoice("Into battle!", "Give me one more second", null, null);
                    if (num == 10) {
                        progressStage = 54;
                        objective1Progress = 0;
                        objective2Progress = 0;
                        objective3Progress = 0;
                    } else if (num == 20) {
                        progressStage = 53;
                        objective1Progress = 0;
                        objective2Progress = 0;
                        objective3Progress = 0;
                    }
                } else if (progressStage == 53) {
                    loadDialogStage(npc, DialogStorage.MarlaNecklace, 53);
                    progressStage = 52;
                } else if (progressStage == 54) {
                    npc.blockInteraction = true;
                    if (objective1Progress == 0) {
                        updateObjective("Kill the Stone Knight!", 0);
                        enemiesKilled = mg.prj_control.stoneKnightKilled;
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 54);
                        for (ENTITY entity : mg.ENTITIES) {
                            if (entity instanceof BOSS_Knight) {
                                objective1Progress = 1;
                                ((BOSS_Knight) entity).activate = true;
                            }
                        }
                    }
                    synchronized (mg.ENTITIES) {
                        for (ENTITY entity : mg.ENTITIES) {
                            if (entity instanceof BOSS_Knight) {
                                if (!cutscenefinish && (entity.getHealth() <= entity.maxHealth * 0.5)) {
                                    entity.BuffsDebuffEffects.clear();
                                    ((BOSS_Knight) entity).autoPilot = true;
                                    ((BOSS_Knight) entity).healthPackCounter = 0;
                                    if (objective1Progress == 1) {
                                        entity.animation.playRandomSoundFromXToIndex(3, 3);
                                        objective1Progress = 2;
                                        ((BOSS_Knight) entity).attack5 = false;
                                        ((BOSS_Knight) entity).attack4 = false;
                                        ((BOSS_Knight) entity).attack3 = false;
                                        ((BOSS_Knight) entity).attack2 = false;
                                        ((BOSS_Knight) entity).attack1 = false;
                                    }
                                    mg.gameState = State.CUT_SCENE;
                                    mg.cutSceneX = (int) entity.worldX;
                                    mg.cutSceneY = (int) entity.worldY;
                                    for (ENTITY entity1 : mg.ENTITIES) {
                                        entity1.stunned = true;
                                    }
                                    mg.player.stunned = true;
                                    if (!entity.activeTile.equals(npc.activeTile)) {
                                        entity.searchPathUncapped(npc.activeTile.x, npc.activeTile.y, 30);
                                    } else {
                                        objective3Progress++;
                                        if (objective3Progress == 80) {
                                            npc.dialogHideDelay = 600;
                                            entity.dialog.loadNewLine("Puny human! You will die!");
                                        } else if (objective3Progress == 190) {
                                            ((BOSS_Knight) entity).drawDialog = false;
                                            entity.dialog.dialogRenderCounter = 2500;
                                            loadDialogStage(npc, DialogStorage.MarlaNecklace, 55);
                                        }
                                        if (objective3Progress > 290) {
                                            if (KilledAria && !((BOSS_Knight) entity).attack5 && objective3Progress == 1100) {
                                                for (ENTITY entity1 : mg.ENTITIES) {
                                                    entity1.stunned = false;
                                                }
                                                mg.player.stunned = false;
                                                mg.gameState = State.PLAY;
                                                npc.show_dialog = false;
                                                entity.dialog.loadNewLine("Now, back to you!");
                                                cutscenefinish = true;
                                                ((BOSS_Knight) entity).autoPilot = false;
                                            } else if (!KilledAria) {
                                                entity.animation.playRandomSoundFromXToIndex(1, 1);
                                                entity.spriteCounter = 0;
                                                ((BOSS_Knight) entity).attack5 = true;
                                                npc.dead = true;
                                                loadDialogStage(npc, DialogStorage.MarlaNecklace, 56);
                                                KilledAria = true;
                                                objective3Progress = 1000;
                                            }
                                        }
                                    }
                                } else if (moveToTile(npc, entity.activeTile.x, entity.activeTile.y)) {
                                    ((NPC_Aria) npc).attack1 = true;
                                }
                            }
                        }
                    }
                    if (enemiesKilled + 1 == mg.prj_control.stoneKnightKilled) {
                        npc.dialog.loadNewLine("   ");
                        DontConsumeETyped = true;
                        updateObjective("Look at Aria's body", 0);
                        addQuestMarker("body", npc.activeTile.x, npc.activeTile.y, Zone.Hillcrest);
                        npc.show_dialog = false;
                        nextStage();
                    }
                } else if (progressStage == 55) {
                    if (mg.collisionChecker.checkEntityAgainstPlayer(npc, 9)) {
                        mg.playerPrompts.E = true;
                        if (mg.inputH.e_typed) {
                            npc.show_dialog = false;
                            mg.inputH.e_typed = false;
                            nextStage();
                            DontConsumeETyped = false;
                            objective1Progress = 0;
                        }
                    }
                } else if (progressStage == 56) {
                    if (objective1Progress == 0) {
                        mg.sound.Sonata.setVolume(0.3);
                        mg.sound.Sonata.seek(Duration.ZERO);
                        mg.sound.Sonata.play();
                    }
                    mg.player.dialog.dialogRenderCounter = 2001;
                    objective1Progress++;
                    if (objective1Progress == 1) {
                        mg.player.dialog.loadNewLine("I won, but at what cost");
                    } else if (objective1Progress == 320) {
                        mg.player.dialog.loadNewLine("Was it worth it in the end? Did I make the right decisions?");
                    } else if (objective1Progress == 700) {
                        mg.player.dialog.loadNewLine("Well, now its too late anyway. I have to move on...");
                    } else if (objective1Progress == 960) {
                        nextStage();
                    }
                } else if (progressStage == 57) {
                    int num = npc.dialog.drawChoice("*Go back to town*", "*Stay here bit longer to think*", null, null);
                    if (num == 10) {
                        mg.sound.fadeOut(mg.sound.Sonata, 0.3, 6);
                        mg.player.setPosition(20, 20);
                        nextStage();
                        removeQuestMarker("body");
                        updateObjective("Report back to Marla", 0);
                    } else if (num == 20) {

                    }
                } else if (progressStage == 77) {
                    if (moveToTile(npc, 43, 31, new Point(40, 60), new Point(59, 59), new Point(70, 49), new Point(70, 56), new Point(83, 37), new Point(43, 34)) && npc.show_dialog) {
                        nextStage();
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 78);
                    }
                } else if (progressStage == 78) {
                    npc.blockInteraction = true;
                    objective1Progress++;
                    if (objective1Progress >= 360) {
                        nextStage();
                        objective1Progress = 0;
                    }
                } else if (progressStage == 80) {
                    if (objective1Progress == 0) {
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 80);
                    }
                    objective1Progress++;
                    if (objective1Progress >= 450) {
                        nextStage();
                        objective1Progress = 0;
                    }
                } else if (progressStage == 81) {
                    if (objective1Progress == 0) {
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 81);
                    }
                    objective1Progress++;
                    if (objective1Progress >= 450) {
                        nextStage();
                        objective1Progress = 0;
                    }
                } else if (progressStage == 83) {
                    if (objective1Progress == 0) {
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 83);
                    }
                    objective1Progress++;
                    if (objective1Progress >= 450) {
                        nextStage();
                        objective1Progress = 0;
                    }
                } else if (progressStage == 84) {
                    if (objective1Progress == 0) {
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 84);
                    }
                    objective1Progress++;
                    if (objective1Progress >= 500) {
                        nextStage();
                        updateObjective("Everyone can't be happy!", 0);
                        objective1Progress = 0;
                    }
                } else if (progressStage == 85) {
                    if (moveToTile(npc, 0, 99, new Point(31, 26), new Point(23, 29), new Point(18, 34), new Point(18, 83))) {
                        mg.sqLite.updateQuestFacts(quest_id, 1, -5);
                        npc.zone = Zone.Woodland_Edge;
                        mg.sqLite.finishQuest(quest_id);
                        this.completed = true;
                    }
                } else if (progressStage == 100) {
                    if (objective1Progress == 0) {
                        loadDialogStage(npc, DialogStorage.MarlaNecklace, 100);
                    }
                    if (objective1Progress >= 600) {
                        nextStage();
                        updateObjective("Everyone can't be happy!", 0);
                        objective1Progress = 0;
                    }
                    objective1Progress++;
                } else if (progressStage == 101) {
                    if (moveToTile(npc, 0, 99, new Point(31, 26), new Point(23, 29), new Point(18, 34), new Point(18, 83))) {
                        npc.zone = Zone.Woodland_Edge;
                        mg.sqLite.updateQuestFacts(quest_id, 1, 10);
                        mg.sqLite.finishQuest(quest_id);
                        this.completed = true;
                    }
                }
            }
        }
        if (progressStage >= 75 || mg.sqLite.readQuestFacts(quest_id, 1) == -1) {
            if (!once && WorldController.currentWorld == Zone.Hillcrest) {
                WorldRender.worldData1[97][77] = -1;
                once = true;
            }
        }
    }
}




