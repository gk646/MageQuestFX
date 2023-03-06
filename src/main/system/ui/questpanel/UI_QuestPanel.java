package main.system.ui.questpanel;


import gameworld.quest.HiddenQUEST;
import gameworld.quest.QUEST;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;
import main.system.ui.Colors;
import main.system.ui.FonT;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class UI_QuestPanel {
    public boolean expanded = false;
    public final List<QUEST> quests = Collections.synchronizedList(new ArrayList<>());
    public final ArrayList<HiddenQUEST> hiddenQuests = new ArrayList<>();
    public final ArrayList<QUEST> finishedQuests = new ArrayList<>();

    public QUEST activeQuest;
    public final Rectangle expandButton = new Rectangle(1_870, 350, 21, 21);
    private final MainGame mg;
    private final Image collapseImage = new Image((Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/questpanel/collapse.png"))));
    private final Image expandImage = new Image((Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/questpanel/expand.png"))));
    private final Image small = new Image((Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/questpanel/questpanel_small.png"))));
    public final Rectangle wholeJournalWindow = new Rectangle(480, 260, 960, 563);
    public final Rectangle leftSide = new Rectangle(508 + 480, 260 + 11, 414, 510);
    private final Image journal = new Image((Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/questpanel/journal.png"))));
    private final Image trackBox = new Image((Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/questpanel/trackBox.png"))));
    private final Image trackBoxHover = new Image((Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/questpanel/trackBoxHover.png"))));
    public double scroll = 280;
    Rectangle[] trackBoxes = new Rectangle[10];
    private int lastY;


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
        gc.fillText("All Quests: " + numberOfQuests() + "/ 10", 545, y);
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
        }
    }


    private int numberOfQuests() {
        int counter = 0;
        for (QUEST quest : quests) {
            if (quest != null) {
                counter++;
            }
        }
        return counter;
    }

    private void drawQuestBar(GraphicsContext gc) {
        if (expanded) {
            gc.setFill(Colors.lightGreyMiddleAlpha);
            gc.fillRoundRect(1_649, 335, 251, 400, 15, 15);
        } else {
            gc.setFill(Colors.lightGreyMiddleAlpha);
            gc.fillRoundRect(1_655, 340, 245, 145, 15, 15);
            gc.drawImage(small, 1_649, 335);
        }
        gc.setFont(FonT.antParty20);
        gc.setFill(Colors.white);
        gc.fillText("OBJECTIVES", 1_715, 361);
        gc.setFont(FonT.antParty18);
        int y = 390;
        gc.setFill(Colors.questNameBeige);
        if (activeQuest != null) {
            gc.fillText(activeQuest.name, 1_670, y += 30);
            gc.setFill(Colors.white);
            gc.setFont(FonT.antParty16);
            y += 10;
            for (int i = 0; i < 3; i++) {
                if (activeQuest.objectives[i] != null) {
                    for (String string : activeQuest.objectives[i].split("\n")) {
                        gc.fillText(string, 1_680, y += 15);
                    }
                    y += 20;
                }
            }
        } else {
            gc.fillText("Find new quests!", 1_670, y += 30);
        }
        if (expanded) {
            gc.drawImage(collapseImage, 1_870, 350);
            for (QUEST quest : quests) {
                if (quest != null) {
                    gc.fillText(quest.name, 1_685, y += 30);
                }
            }
        } else {
            gc.drawImage(expandImage, 1_870, 350);
        }
    }

    public void update() {
        synchronized (quests) {
            synchronized (hiddenQuests) {
                Iterator<QUEST> iterator = quests.iterator();
                while (iterator.hasNext()) {
                    QUEST quest = iterator.next();
                    quest.update();
                    if (quest.completed) {
                        if (activeQuest == quest) {
                            activeQuest = null;
                        }
                        iterator.remove();
                    }
                }
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

    public QUEST getQuest(String name) {
        for (QUEST quest : quests) {
            if (quest.name.equals(name)) {
                return quest;
            }
        }
        return null;
    }

    public void setQuestStage(String quest_name, int progressStage) {
        for (QUEST quest : quests) {
            if (quest.name.equals(quest_name)) {
                quest.progressStage = progressStage;
            }
        }
    }

    public void hideJournalCollision() {
        wholeJournalWindow.y = -1_000;
    }

    public void resetJournalCollision() {
        wholeJournalWindow.y = 260;
    }

    public boolean PlayerHasQuests(String name) {
        for (QUEST quest : quests) {
            if (quest.name.equals(name)) {
                return true;
            }
        }
        return false;
    }
}
