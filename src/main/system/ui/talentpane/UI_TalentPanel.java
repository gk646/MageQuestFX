package main.system.ui.talentpane;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;
import main.system.ui.Colors;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Objects;

public class UI_TalentPanel {
    private static final int TALENT_SIZE = 45;

    public final TalentNode[] talent_Nodes;
    private final int stringY = 0;
    public final Rectangle wholeTalentWindow;
    private final MainGame mg;
    private final Point previousMousePosition = new Point(300, 300);
    private final Image background = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/talents/background.png")));
    private final Image connection_red = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/talents/connection_red.png")));
    private final Image connection_orange = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/talents/connection_orange.png")));
    private final Image connection_green = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/talents/connection_green.png")));

    private int pointsToSpend;
    public int talentPanelX = 960 - 16;
    public int talentPanelY = 540 - 16;
    private final Point lastTalentPosition = new Point(talentPanelX, talentPanelY);
    AdjacencyMatrix matrix = new AdjacencyMatrix();

    public UI_TalentPanel(MainGame mg) {
        this.mg = mg;
        this.talent_Nodes = new TalentNode[25];
        createTalentNodes();
        wholeTalentWindow = new Rectangle(175, 75, 1_570, 940);
        hideTalentCollision();
    }

    public void drawTalentWindow(GraphicsContext gc) {
        drawTalentBackground(gc);
        drawConnections(gc, talentPanelX, talentPanelY);
        drawTalentNodes(gc, talentPanelX, talentPanelY);
    }


    private void drawTalentBackground(GraphicsContext gc) {
        for (int i = 0; i < 30; i++) {
            for (int b = 0; b < 19; b++) {
                gc.drawImage(background, 175 + i * 48, 75 + b * 48);
            }
        }
    }

    private void drawConnections(GraphicsContext gc, int x, int y) {
        drawLineA(x, y, gc, 2, 8);
        drawLineA(x, y, gc, 2, 9);
        drawLineA(x, y, gc, 9, 14);
        drawLineA(x, y, gc, 14, 10);
        drawLineA(x, y, gc, 10, 11);
        drawLineA(x, y, gc, 12, 11);
        drawLineA(x, y, gc, 8, 13);
        drawLineA(x, y, gc, 13, 12);
    }

    private void createTalentNodes() {
        //middle
        talent_Nodes[0] = new TalentNode(new TALENT(0, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 0, 100);
        talent_Nodes[1] = new TalentNode(new TALENT(1, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), -100, 0);
        talent_Nodes[2] = new TalentNode(new TALENT(2, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 0, -100);
        talent_Nodes[3] = new TalentNode(new TALENT(3, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 100, 0);
        //lower side
        talent_Nodes[4] = new TalentNode(new TALENT(4, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), -50, 150);
        talent_Nodes[5] = new TalentNode(new TALENT(5, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 50, 150);
        talent_Nodes[6] = new TalentNode(new TALENT(6, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 100, 200);
        talent_Nodes[7] = new TalentNode(new TALENT(7, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), -100, 200);

        // upper side
        talent_Nodes[8] = new TalentNode(new TALENT(8, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 50, -150);
        talent_Nodes[9] = new TalentNode(new TALENT(9, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), -50, -150);
        talent_Nodes[14] = new TalentNode(new TALENT(14, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), -110, -218);
        talent_Nodes[10] = new TalentNode(new TALENT(10, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), -50, -267);
        //energy sphere?s
        talent_Nodes[11] = new TalentNode(new TALENT(11, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 0, -220);
        talent_Nodes[12] = new TalentNode(new TALENT(12, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 50, -267);
        talent_Nodes[13] = new TalentNode(new TALENT(13, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 110, -218);
    }

    private void drawLineA(int x, int y, GraphicsContext gc, int node1, int node2) {
        drawLine(x, y, gc, talent_Nodes[node1].position.x, talent_Nodes[node1].position.y, talent_Nodes[node2].position.x, talent_Nodes[node2].position.y, talent_Nodes[node1], talent_Nodes[node2]);
    }

    private void drawLine(int offsetx, int offsety, GraphicsContext gc, int x0, int y0, int x1, int y1, TalentNode requirement, TalentNode nextOne) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int counterx = 0;
        int countery = 0;
        int err = dx - dy;
        int e2;
        while (true) {
            if ((counterx + countery) >= 8) {
                if (requirement.activated && nextOne.activated) {
                    gc.drawImage(connection_green, offsetx + x0 + 16 - 2, offsety + y0 + 16 - 2);
                } else if (requirement.activated) {
                    gc.drawImage(connection_orange, offsetx + x0 + 16 - 2, offsety + y0 + 16 - 2);
                } else if (!nextOne.activated) {
                    gc.drawImage(connection_red, offsetx + x0 + 16 - 2, offsety + y0 + 16 - 2);
                } else {
                    gc.drawImage(connection_orange, offsetx + x0 + 16 - 2, offsety + y0 + 16 - 2);
                }
                countery = 0;
                counterx = 0;
            }
            if (x0 == x1 && y0 == y1) break;
            e2 = 2 * err;
            if (e2 > -dy) {
                err = err - dy;
                x0 = x0 + sx;
                counterx++;
            }
            if (e2 < dx) {
                err = err + dx;
                y0 = y0 + sy;
                countery++;
            }
        }
    }

    private void drawTalentNodes(GraphicsContext gc, int startX, int startY) {
        for (TalentNode node : talent_Nodes) {
            if (node != null) {
                if (node.boundBox.contains(mg.inputH.lastMousePosition)) {
                    node.drawHoverOverEffect(gc, startX, startY);
                    drawTooltip(gc, node);
                } else {
                    node.drawNode(gc, startX, startY);
                }
            }
        }
    }

    private void drawTooltip(GraphicsContext gc, TalentNode node) {
        gc.setFill(Colors.darkBackground);
        gc.fillRoundRect(node.position.x - 50, node.position.y - 40, 40, 40, 5, 5);
    }

    public boolean checkValidTalent(TalentNode node) {
        if (node.id == 2) {
            return true;
        } else {
            for (int b = 0; b < 100; b++) {
                if (matrix.getEdge(b, node.id) == 1) {
                    System.out.println(b);
                    if (talent_Nodes[b].activated) {
                        return true;
                    }
                }
            }

            return false;
        }
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
