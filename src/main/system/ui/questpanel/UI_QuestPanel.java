package main.system.ui.questpanel;


import gameworld.quest.QUEST;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;
import main.system.ui.Colors;
import main.system.ui.FonT;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Objects;

public class UI_QuestPanel {
    public boolean expanded = false;
    public ArrayList<QUEST> quests = new ArrayList<>();

    public QUEST activeQuest;
    public final Rectangle expandButton = new Rectangle(1_870, 350, 21, 21);
    private final MainGame mg;
    private final Image collapseImage = new Image((Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/questpanel/collapse.png"))));
    private final Image expandImage = new Image((Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/questpanel/expand.png"))));
    private final Image small = new Image((Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/questpanel/questpanel_small.png"))));


    public UI_QuestPanel(MainGame mg) {
        this.mg = mg;
    }

    public void draw(GraphicsContext gc) {

        if (expanded) {
            gc.setFill(Colors.lightGreyMiddleAlpha);
            gc.fillRoundRect(1_649, 335, 251, 400, 15, 15);
        } else {
            gc.setFill(Colors.lightGreyMiddleAlpha);
            gc.fillRoundRect(1_655, 340, 245, 145, 15, 15);
            gc.drawImage(small, 1649, 335);
        }
        gc.setEffect(mg.ui.shadow);
        gc.setFont(FonT.minecraftBold17);
        gc.setFill(Colors.white);
        gc.fillText("OBJECTIVES", 1_715, 361);
        gc.setFont(FonT.minecraftBold16);
        int y = 390;
        gc.setFill(Colors.questNameBeige);
        gc.fillText(activeQuest.name, 1_670, y += 30);
        gc.setFill(Colors.white);
        gc.setFont(FonT.minecraftBoldItalic14);
        y += 10;
        for (int i = 0; i < 3; i++) {
            if (activeQuest.objectives[i] != null) {
                for (String string : activeQuest.objectives[i].split("\n")) {
                    gc.fillText(string, 1_680, y += 15);
                }
                y += 20;
            }
        }
        gc.setEffect(null);

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
        for (QUEST quest : quests) {
            if (quest != null) {
                quest.update();
            }
        }
    }

    public void setQuestStage(String quest_name, int progressStage) {
        for (QUEST quest : quests) {
            if (quest.name.equals(quest_name)) {
                quest.progressStage = progressStage;
            }
        }
    }
}
