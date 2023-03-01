package main.system.ui.questpanel;


import gameworld.quest.QUEST;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
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
    public final Rectangle expandButton = new Rectangle(1_872, 343, 21, 21);
    private final MainGame mg;
    private final Image collapseImage = new Image((Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/questpanel/collapse.png"))));
    private final Image expandImage = new Image((Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/questpanel/expand.png"))));


    public UI_QuestPanel(MainGame mg) {
        this.mg = mg;
    }

    public void draw(GraphicsContext gc) {
        gc.setFont(FonT.minecraftBold20);
        if (expanded) {
            gc.setFill(Colors.lightGreyMiddleAlpha);
            gc.fillRoundRect(1_649, 335, 251, 400, 10, 10);
        }
        gc.setFill(Color.WHITE);
        gc.fillText("OBJECTIVES", 1_678, 361);
        gc.setFont(FonT.minecraftBoldItalic15);
        int y = 361;
        gc.fillText(activeQuest.name, 1_685, y += 30);
        for (int i = 0; i < 3; i++) {
            if (activeQuest.objectives[i] != null) {
                gc.fillText(activeQuest.objectives[i], 1_500, y += 30);
            }
        }


        gc.setFill(Colors.button);
        gc.fillRoundRect(1_872, 343, 21, 21, 5, 5);
        if (expanded) {
            gc.drawImage(collapseImage, 1_878, 350);
            for (QUEST quest : quests) {
                if (quest != null) {
                    gc.fillText(quest.name, 1_685, y += 30);
                }
            }
        } else {
            gc.drawImage(expandImage, 1_876, 347);
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
