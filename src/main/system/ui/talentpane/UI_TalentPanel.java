package main.system.ui.talentpane;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;
import main.system.ui.talentpane.talents.TAL_INT1;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Objects;

public class UI_TalentPanel {
    private static final int TALENT_SIZE = 45;

    private final TalentNode[] talent_Nodes;
    private final int stringY = 0;
    public final Rectangle wholeTalentWindow;
    private final MainGame mg;
    private final Point previousMousePosition = new Point(300, 300);
    private final Image background = new Image(Objects.requireNonNull(getClass().getResourceAsStream("resources/ui/talents/background.png")));

    private int pointsToSpend;
    public int talentPanelX = 960 - 22;
    public int talentPanelY = 540 - 22;
    private final Point lastTalentPosition = new Point(talentPanelX, talentPanelY);

    public UI_TalentPanel(MainGame mg) {
        this.mg = mg;
        this.talent_Nodes = new TalentNode[25];
        createTalentNodes();
        wholeTalentWindow = new Rectangle(175, 75, 1_570, 940);
        hideTalentCollision();
    }

    public void drawTalentWindow(GraphicsContext gc) {
        drawTalentBackground(gc);
        drawTalentNodes(gc, talentPanelX, talentPanelY);
    }

    public void drawTooltip(Graphics2D g2) {
        if (!mg.inputH.mouse1Pressed) {
            for (TalentNode talentN : talent_Nodes) {
                if (talentN.toolTipTimer >= 40) {
                    getToolTip(g2, talentN);
                }
                if (talentN.boundBox.contains(mg.inputH.lastMousePosition)) {
                    talentN.toolTipTimer++;
                    break;
                } else {
                    talentN.toolTipTimer = 0;
                }
            }
        }
    }

    private void drawTalentBackground(GraphicsContext gc) {

        for (int i = 0; i < 40; i++) {
            for (int b = 0; b < 23; b++) {
                gc.drawImage(background, i * 48, b * 48);
            }
        }
    }

    private void drawTalentNodes(GraphicsContext gc, int startX, int startY) {
        for (TalentNode node : talent_Nodes) {
            if (node != null) {
                node.drawNode(gc, startX, startY, TALENT_SIZE);
            }
        }
    }


    private void getToolTip(Graphics2D g2, TalentNode talentNode) {

    }


    private void createTalentNodes() {
        talent_Nodes[0] = new TalentNode(new TAL_INT1(1, "Increase maximum mana", "ManaTalentIcon.png", "Increases maximum mana by 5%", 23, 21), 300, 300);
        talent_Nodes[1] = new TalentNode(new TAL_INT1(1, "Increase maximum mana", "ManaTalentIcon.png", "Increases maximum mana by 5%", 23, 21), 300, 300);
        talent_Nodes[2] = new TalentNode(new TAL_INT1(1, "Increase maximum mana", "ManaTalentIcon.png", "Increases maximum mana by 5%", 23, 21), 300, 300);
        talent_Nodes[3] = new TalentNode(new TAL_INT1(1, "Increase maximum mana", "ManaTalentIcon.png", "Increases maximum mana by 5%", 23, 21), 300, 300);
    }

    public void resetTalentCollision() {
        wholeTalentWindow.x = 175;
        wholeTalentWindow.y = 75;
    }

    public void hideTalentCollision() {
        wholeTalentWindow.x = -1_000;
        wholeTalentWindow.y = -1_000;
    }
}
