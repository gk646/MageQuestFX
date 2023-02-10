package main.system.ui.talentpane;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.MainGame;
import main.system.ui.Colors;
import main.system.ui.FonT;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Objects;

public class UI_TalentPanel {
    private static final int TALENT_SIZE = 45;

    private final Image backgrounbig = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/talents/background_big.png")));
    private final int stringY = 0;
    private final ImageView backgrounclip = new ImageView(backgrounbig);
    private final MainGame mg;
    private final Point previousMousePosition = new Point(300, 300);
    private final Image background = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/talents/background.png")));
    private final Image connection_red = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/talents/connection_red.png")));
    private final Image connection_orange = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/talents/connection_orange.png")));
    private final Image connection_green = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/talents/connection_green.png")));
    private final Image talentnode = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/talents/talentnode.png")));
    private final Image talentnode_green = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/talents/node_green.png")));
    private final Image talentnode_purple = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/talents/node_purple.png")));
    private final Image talentnode_orange = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/talents/node_orange.png")));

    public int pointsToSpend = 5, pointsSpent;
    public int talentPanelX = 960 - 16;
    public int talentPanelY = 540 - 16;
    private final Point lastTalentPosition = new Point(talentPanelX, talentPanelY);
    public TalentNode[] talent_Nodes;
    public Rectangle wholeTalentWindow;
    javafx.scene.shape.Rectangle ceil = new javafx.scene.shape.Rectangle(175, 75, 1_568, 938);

    AdjacencyMatrix matrix = new AdjacencyMatrix();

    public UI_TalentPanel(MainGame mg) {
        this.mg = mg;
        this.talent_Nodes = new TalentNode[100];
        createTalentNodes();
        wholeTalentWindow = new Rectangle(176, 76, 1_568, 938);
        hideTalentCollision();
        backgrounclip.setClip(ceil);
    }

    public void getTalentEffects() {
        for (TalentNode node : talent_Nodes) {

        }
    }

    public void spendTalentPoint() {
        pointsSpent++;
        pointsToSpend--;
    }

    public void drawTalentWindow(GraphicsContext gc) {
        drawTalentBackground(gc);
        drawConnections(gc, talentPanelX, talentPanelY);
        drawTalentNodes(gc, talentPanelX, talentPanelY);
        drawLegend(gc);
    }


    private void drawTalentBackground(GraphicsContext gc) {
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 23; j++) {
                gc.drawImage(background, i * 48, j * 48);
            }
        }
    }


    private void drawConnections(GraphicsContext gc, int x, int y) {
        for (int[] array : AdjacencyMatrix.edge_list) {
            drawLine(x, y, gc, talent_Nodes[array[0]], talent_Nodes[array[1]]);
        }
    }

    private void drawTalentNodes(GraphicsContext gc, int startX, int startY) {
        for (TalentNode node : talent_Nodes) {
            if (node != null) {
                if (node.boundBox.contains(mg.inputH.lastMousePosition)) {
                    drawTooltip(gc, node, startX, startY);
                }
                if (node.activated) {
                    node.drawNode(gc, startX, startY, talentnode_green);
                } else if (checkValidTalent(node)) {
                    node.drawNode(gc, startX, startY, talentnode_purple);
                } else {
                    node.drawNode(gc, startX, startY, talentnode_orange);
                }
            }
        }
    }


    private void drawTooltip(GraphicsContext gc, TalentNode node, int startX, int startY) {
        gc.setFill(Colors.darkBackground);
        gc.fillRoundRect(startX + node.position.x - 50, startY + node.position.y - 7, 40, 40, 5, 5);
    }

    private void drawLine(int offsetx, int offsety, GraphicsContext gc, TalentNode requirement, TalentNode nextOne) {
        int x0 = requirement.position.x;
        int x1 = nextOne.position.x;
        int y0 = requirement.position.y;
        int y1 = nextOne.position.y;
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int counter = 0;
        int err = dx - dy;
        int e2, drawx, drawy;
        while (true) {
            if (counter % 8 == 0) {
                drawx = x0 + offsetx + 14;
                drawy = y0 + offsety + 14;
                gc.drawImage(requirement.activated && nextOne.activated ? connection_green : requirement.activated ? connection_orange : !nextOne.activated ? connection_red : connection_orange, drawx, drawy);
            }
            if (x0 == x1 && y0 == y1) break;
            e2 = 2 * err;
            if (e2 > -dy) {
                err = err - dy;
                x0 = x0 + sx;
            }
            if (e2 < dx) {
                err = err + dx;
                y0 = y0 + sy;
            }
            counter++;
        }
    }

    private void drawLegend(GraphicsContext gc) {
        gc.setFont(FonT.minecraftBold14);
        gc.setFill(Colors.mediumVeryLight);
        gc.fillRoundRect(900, 0, 129, 20, 5, 5);
        gc.fillRoundRect(750, 30, 150, 20, 5, 5);
        gc.fillRoundRect(1_030, 30, 150, 20, 5, 5);
        gc.fillRect(1_650, 80, 150, 65);
        gc.setLineWidth(2);
        gc.setStroke(Colors.darkBackground);
        gc.strokeRoundRect(900, 0, 129, 20, 5, 5);
        gc.strokeRoundRect(750, 30, 150, 20, 7, 7);
        gc.strokeRoundRect(1_030, 30, 150, 20, 7, 7);
        gc.strokeRoundRect(1_650, 80, 150, 65, 3, 3);
        gc.setLineWidth(1);
        gc.setFill(Colors.darkBackground);
        gc.fillText("Skill Tree", 925, 15);
        gc.fillText("Points spent:   " + pointsSpent, 757, 45);
        gc.fillText("Talentpoints:   " + pointsToSpend, 1_037, 45);

        gc.fillText("SPACE: Recenter", 1_653, 95);
        gc.fillText("Green: Active", 1_653, 110);
        gc.fillText("Purple: Available", 1_653, 125);
        gc.fillText("Orange: Locked", 1_653, 140);
    }

    private void createTalentNodes() {
        //left side
        talent_Nodes[1] = new TalentNode(new TALENT(1, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), -100, 0);


        //lower side
        talent_Nodes[0] = new TalentNode(new TALENT(0, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 0, 100);
        talent_Nodes[4] = new TalentNode(new TALENT(4, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), -50, 150);
        talent_Nodes[5] = new TalentNode(new TALENT(5, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 50, 150);
        talent_Nodes[6] = new TalentNode(new TALENT(6, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 100, 200);
        talent_Nodes[7] = new TalentNode(new TALENT(7, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), -100, 200);

        // upper side
        talent_Nodes[2] = new TalentNode(new TALENT(2, "Increase maximum mana", "health.png", "Increases maximum mana by 5%"), 0, -100);
        talent_Nodes[8] = new TalentNode(new TALENT(8, "Increase maximum mana", "glowing_health.png", "Increases maximum mana by 5%"), 50, -150);
        talent_Nodes[9] = new TalentNode(new TALENT(9, "Increase maximum mana", "glowing_health2.png", "Increases maximum mana by 5%"), -50, -150);
        talent_Nodes[14] = new TalentNode(new TALENT(14, "Increase maximum mana", "glowing_health2.png", "Increases maximum mana by 5%"), -110, -218);
        talent_Nodes[10] = new TalentNode(new TALENT(10, "Increase maximum mana", "glowing_health.png", "Increases maximum mana by 5%"), -50, -267);
        //energy sphere?s
        talent_Nodes[11] = new TalentNode(new TALENT(11, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 0, -220);
        talent_Nodes[12] = new TalentNode(new TALENT(12, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 50, -267);
        talent_Nodes[13] = new TalentNode(new TALENT(13, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 110, -218);
        talent_Nodes[15] = new TalentNode(new TALENT(15, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 0, -325);

        //right side
        talent_Nodes[3] = new TalentNode(new TALENT(3, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 100, 0);
        talent_Nodes[16] = new TalentNode(new TALENT(16, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 158, -62);
        talent_Nodes[17] = new TalentNode(new TALENT(17, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 224, -134);
        talent_Nodes[18] = new TalentNode(new TALENT(18, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 288, -204);
        talent_Nodes[19] = new TalentNode(new TALENT(19, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 231, -246);
        talent_Nodes[20] = new TalentNode(new TALENT(20, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 170, -284);
        talent_Nodes[21] = new TalentNode(new TALENT(21, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 234, -354);
        talent_Nodes[22] = new TalentNode(new TALENT(22, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 298, -422);

        talent_Nodes[23] = new TalentNode(new TALENT(23, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 185, 301);
        talent_Nodes[24] = new TalentNode(new TALENT(24, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 243, 258);
        talent_Nodes[25] = new TalentNode(new TALENT(25, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 295, 211);
        talent_Nodes[26] = new TalentNode(new TALENT(26, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 228, 138);
        talent_Nodes[27] = new TalentNode(new TALENT(27, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 160, 69);
        talent_Nodes[28] = new TalentNode(new TALENT(28, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 350, 163);
        talent_Nodes[29] = new TalentNode(new TALENT(29, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 350, 78);
        talent_Nodes[30] = new TalentNode(new TALENT(30, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 298, 0);

        talent_Nodes[31] = new TalentNode(new TALENT(31, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 230, 0);
        talent_Nodes[32] = new TalentNode(new TALENT(32, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 430, 37);
        talent_Nodes[33] = new TalentNode(new TALENT(33, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 430, -37);
        talent_Nodes[34] = new TalentNode(new TALENT(34, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 350, -78);
        talent_Nodes[35] = new TalentNode(new TALENT(35, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 350, -164);
        talent_Nodes[36] = new TalentNode(new TALENT(36, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 454, -164);
        talent_Nodes[37] = new TalentNode(new TALENT(37, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 558, -164);
        talent_Nodes[38] = new TalentNode(new TALENT(38, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 664, -164);
        talent_Nodes[39] = new TalentNode(new TALENT(39, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 765, -164);
        talent_Nodes[40] = new TalentNode(new TALENT(40, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 870, -164);
        talent_Nodes[41] = new TalentNode(new TALENT(41, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 454, 163);
        talent_Nodes[42] = new TalentNode(new TALENT(42, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 558, 163);
        talent_Nodes[43] = new TalentNode(new TALENT(43, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 661, 163);
        talent_Nodes[44] = new TalentNode(new TALENT(44, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 765, 163);
        talent_Nodes[45] = new TalentNode(new TALENT(45, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 870, 163);
        talent_Nodes[46] = new TalentNode(new TALENT(46, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 765, 90);
        talent_Nodes[47] = new TalentNode(new TALENT(47, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 836, 90);
        talent_Nodes[48] = new TalentNode(new TALENT(48, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 907, 90);
        talent_Nodes[49] = new TalentNode(new TALENT(49, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 608, 103);
        talent_Nodes[50] = new TalentNode(new TALENT(50, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 560, 35);
        talent_Nodes[51] = new TalentNode(new TALENT(51, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 560, -35);
        talent_Nodes[52] = new TalentNode(new TALENT(52, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 608, -97);
        talent_Nodes[53] = new TalentNode(new TALENT(53, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 658, -35);
        talent_Nodes[54] = new TalentNode(new TALENT(54, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 658, 35);

        talent_Nodes[55] = new TalentNode(new TALENT(55, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 816, -111);
        talent_Nodes[56] = new TalentNode(new TALENT(56, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 757, -52);
        talent_Nodes[57] = new TalentNode(new TALENT(57, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 816, 6);
        talent_Nodes[58] = new TalentNode(new TALENT(58, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 875, -52);
        talent_Nodes[59] = new TalentNode(new TALENT(59, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%"), 816, -52);
    }

    public boolean checkValidTalent(TalentNode node) {
        if (node.id == 0 || node.id == 1 || node.id == 2 || node.id == 3) {
            return true;
        } else {
            for (int b = 0; b < 100; b++) {
                if (matrix.getEdge(b, node.id) == 1) {
                    if (talent_Nodes[b].activated) {
                        return true;
                    }
                } else if (matrix.getEdge(node.id, b) == 1) {
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
