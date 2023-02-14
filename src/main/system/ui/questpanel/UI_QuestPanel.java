package main.system.ui.questpanel;


import gameworld.quest.QUEST;
import gameworld.quest.quests.QST_Tutorial;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import main.MainGame;
import main.system.ui.Colors;

import java.awt.Rectangle;
import java.util.Objects;

public class UI_QuestPanel {
    public boolean expanded = false;
    public QUEST[] quests = new QUEST[10];
    public final Rectangle expandButton = new Rectangle(1_872, 343, 21, 21);
    private final MainGame mg;
    private final Image collapseImage = new Image((Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/questpanel/collapse.png"))));
    private final Image expandImage = new Image((Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/questpanel/expand.png"))));


    public UI_QuestPanel(MainGame mg) {
        this.mg = mg;
        quests[0] = new QST_Tutorial(mg, "An old man's tale");
    }

    public void draw(GraphicsContext gc) {
        if (expanded) {
            gc.setFill(Colors.lightGreyMiddleAlpha);
            gc.fillRoundRect(1_649, 335, 251, 400, 10, 10);
        }
        gc.setFill(Color.WHITE);
        gc.fillText("OBJECTIVES", 1_678, 361);
        gc.setFill(Colors.button);
        gc.fillRoundRect(1_872, 343, 21, 21, 5, 5);
        if (expanded) {
            gc.drawImage(collapseImage, 1_878, 350);
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
}
