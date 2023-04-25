/*
 * MIT License
 *
 * Copyright (c) 2023 Lukas Gilch
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package main.system.ui.questpanel;


import gameworld.quest.Dialog;
import gameworld.quest.HiddenQUEST;
import gameworld.quest.QUEST;
import gameworld.quest.QUEST_NAME;
import gameworld.world.WorldController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;
import main.system.enums.Zone;
import main.system.ui.Colors;
import main.system.ui.FonT;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class UI_QuestPanel {
    public final List<QUEST> quests = Collections.synchronizedList(new ArrayList<>());
    public final ArrayList<HiddenQUEST> hiddenQuests = new ArrayList<>();
    public final ArrayList<QUEST> finishedQuests = new ArrayList<>();
    public QUEST activeQuest;
    private final MainGame mg;
    public final Rectangle wholeJournalWindow = new Rectangle(480, 260, 960, 563);
    public final Rectangle leftSide = new Rectangle(508 + 480, 260 + 11, 414, 510);
    private final Image journal = new Image((Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/questpanel/journal.png"))));
    private final Image trackBox = new Image((Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/questpanel/trackBox.png"))));
    private final Image trackBoxHover = new Image((Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/questpanel/trackBoxHover.png"))));
    private final Image sideBar = new Image((Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/questpanel/sideBar.png"))));
    private final Image etherBar = new Image((Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/questpanel/etherBar.png"))));
    public double scroll = 280;
    private final Rectangle[] trackBoxes = new Rectangle[10];


    public UI_QuestPanel(MainGame mg) {
        this.mg = mg;
        for (int i = 0; i < 10; i++) {
            trackBoxes[i] = new Rectangle(24, 27);
        }
        hideJournalCollision();
    }


    public void draw(GraphicsContext gc) {
        drawQuestBar(gc);
        if (mg.showJournal) {
            drawJournal(gc);
        }
        if (WorldController.currentWorld == Zone.EtherRealm) {
            drawEtherRealm(gc);
        }
    }

    private void drawEtherRealm(GraphicsContext gc) {
        gc.setFill(Colors.dark_magic_purple);
        gc.fillRect(1683, 570, ((mg.generator.etherRealmProgress / 100.0f) * 88.0f), 25);
        gc.drawImage(etherBar, 1649, 560);
    }

    private void drawJournal(GraphicsContext gc) {
        gc.drawImage(journal, 480, 260);
        gc.setFont(FonT.antParty20);
        gc.setFill(Colors.journalBrown);
        gc.fillText("Tracked Quest:", 545, 300);
        int y = 330;
        int y2;
        if (activeQuest != null) {
            gc.fillText(activeQuest.name, 560, 325);
            for (String string : activeQuest.objectives) {
                if (string != null) {
                    y += 15;
                    gc.fillText(string, 560 + 25, y);
                }
            }
            gc.setFont(FonT.antParty16);
            y2 = (int) scroll;
            for (String string : activeQuest.questRecap) {
                y2 += 10;
                if (string != null) {
                    for (String str : string.split("\n")) {
                        y2 += 15;
                        if (y2 < 290 || y2 > 710) {
                            continue;
                        }
                        gc.fillText(str, 1030, y2);
                    }
                }
            }
        }
        y += 75;
        int x = 560;
        gc.setFont(FonT.antParty20);
        gc.fillText("All Quests: " + quests.size() + "/ 10", 545, y);
        for (int i = 0; i < quests.size(); i++) {
            if (activeQuest == null || quests.get(i).name.equals(activeQuest.name)) {
                continue;
            }
            trackBoxes[i].x = 850;
            trackBoxes[i].y = y;
            if (trackBoxes[i].contains(mg.inputH.lastMousePosition)) {
                gc.drawImage(trackBoxHover, 850, y);
                if (mg.inputH.mouse1Pressed) {
                    mg.inputH.mouse1Pressed = false;
                    activeQuest = quests.get(i);
                }
            } else {
                gc.drawImage(trackBox, 850, y);
            }
            gc.fillText(quests.get(i).name, x, y += 25);
            for (String string : quests.get(i).objectives) {
                if (string != null) {
                    gc.fillText(string, x + 25, y += 20);
                }
            }
            y += 35;
        }
    }


    private void drawQuestBar(GraphicsContext gc) {
        gc.drawImage(sideBar, 1649, 350);
        gc.setFont(FonT.antParty20);
        gc.setFill(Colors.white);
        gc.fillText("OBJECTIVES", 1_680, 390);
        gc.setFont(FonT.antParty18);
        int y = 430;
        gc.setFill(Colors.questNameBeige);
        if (activeQuest != null) {
            gc.fillText(activeQuest.name, 1_673, y);
            gc.setFill(Colors.white);
            gc.setFont(FonT.antParty16);
            y += 25;
            for (int i = 0; i < 3; i++) {
                if (activeQuest.objectives[i] != null) {
                    gc.setFont(FonT.antParty15);
                    for (String string : activeQuest.objectives[i].split("\n")) {
                        gc.fillText(string, 1_680, y);
                        if (activeQuest.objectives[i].contains("\n")) {
                            y += 20;
                        }
                    }

                    y += 20;
                }
            }
        } else {
            gc.fillText("Find new quests!", 1_670, y += 30);
        }
    }

    public void update() {
        synchronized (quests) {
            synchronized (hiddenQuests) {
                for (int i = 0; i < quests.size(); i++) {
                    if (activeQuest == null) {
                        if (quests.get(i) != null) {
                            activeQuest = quests.get(i);
                        }
                    }
                    quests.get(i).update();
                    if (quests.get(i).completed) {
                        mg.statusMessage.setQuestCompleteTrue();
                        mg.player.lastQuest = quests.get(i).name;
                        mg.sound.playEffectSound(18);
                        finishedQuests.add(quests.get(i));
                        if (activeQuest == quests.get(i)) {
                            activeQuest = null;
                        }
                    }
                }
                quests.removeIf(quest -> quest.completed);
                Iterator<HiddenQUEST> hidden = hiddenQuests.iterator();
                while (hidden.hasNext()) {
                    HiddenQUEST quest = hidden.next();
                    quest.update();
                    if (quest.activated) {
                        quests.add(quest);
                        hidden.remove();
                    }
                }
            }
        }
    }

    public QUEST getQuest(QUEST_NAME name) {
        for (QUEST quest : quests) {
            if (quest.logicName.equals(name)) {
                return quest;
            }
        }
        for (QUEST quest : hiddenQuests) {
            if (quest.logicName.equals(name)) {
                return quest;
            }
        }
        return null;
    }

    public void setQuestStageAndObjective(QUEST_NAME quest_name, int progressStage, String... objective) {
        for (QUEST quest : quests) {
            if (quest.logicName.equals(quest_name)) {
                quest.progressStage = progressStage;
                if (objective.length > 0) {
                    String string;
                    for (int i = 0; i < objective.length; i++) {
                        string = objective[i];
                        if (string != null) {
                            quest.objectives[i] = Dialog.insertNewLine(string, 28);
                        }
                    }
                }
            }
        }
    }

    public void hideJournalCollision() {
        wholeJournalWindow.y = -1_000;
    }

    public void resetJournalCollision() {
        wholeJournalWindow.y = 260;
    }

    public boolean PlayerHasQuests(QUEST_NAME name) {
        for (QUEST quest : quests) {
            if (quest.logicName.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean questIsFinished(QUEST_NAME name) {
        for (QUEST quest : finishedQuests) {
            if (quest.logicName.equals(name)) {
                return true;
            }
        }
        return false;
    }
}
