package main.system.ui.talentpane;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.MainGame;
import main.system.ui.Colors;
import main.system.ui.FonT;

import java.awt.Rectangle;
import java.util.Objects;

public class UI_TalentPanel {
    private final MainGame mg;
    private Image background;
    private Image connection_red;
    private Image connection_orange;
    private Image connection_green;
    private Image talentnode_green;
    private Image talentnode_purple;
    private Image talentnode_orange;
    private Image talentnode_big;
    private Image talentnode_mid;
    private Image talentnode_mid_green;
    private Image talentnode_mid_purple;
    private Image talentnode_big_green;
    private Image talentnode_big_purple;
    public int pointsToSpend = 15;
    private int pointsSpent;
    public int talentPanelX = 960 - 16;
    public int talentPanelY = 540 - 16;
    public final TalentNode[] talent_Nodes;
    public final Rectangle wholeTalentWindow;

    private final AdjacencyMatrix matrix = new AdjacencyMatrix();

    public UI_TalentPanel(MainGame mg) {
        this.mg = mg;
        this.talent_Nodes = new TalentNode[200];
        wholeTalentWindow = new Rectangle(0, 0, MainGame.SCREEN_WIDTH, MainGame.SCREEN_HEIGHT);
        hideTalentCollision();
        getImages();
    }


    public void spendTalentPoint() {
        pointsSpent++;
        pointsToSpend--;
    }

    public void drawTalentWindow(GraphicsContext gc) {
        drawTalentBackground(gc);
        drawConnections(gc, talentPanelX, talentPanelY);
        drawTalentNodes(gc, talentPanelX, talentPanelY);
        drawTooltip(gc);
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
                if (node.size == 0) {
                    if (node.activated) {
                        node.drawNode(gc, startX, startY, talentnode_green);
                    } else if (checkValidTalent(node)) {
                        node.drawNode(gc, startX, startY, talentnode_purple);
                    } else {
                        node.drawNode(gc, startX, startY, talentnode_orange);
                    }
                } else if (node.size == 1) {
                    if (node.activated) {
                        node.drawNode(gc, startX - 7, startY - 7, talentnode_big_green);
                    } else if (checkValidTalent(node)) {
                        node.drawNode(gc, startX - 7, startY - 7, talentnode_big_purple);
                    } else {
                        node.drawNode(gc, startX - 7, startY - 7, talentnode_big);
                    }
                } else if (node.size == 2) {
                    if (node.activated) {
                        node.drawNode(gc, startX - 3, startY - 3, talentnode_mid_green);
                    } else if (checkValidTalent(node)) {
                        node.drawNode(gc, startX - 3, startY - 3, talentnode_mid_purple);
                    } else {
                        node.drawNode(gc, startX - 3, startY - 3, talentnode_mid);
                    }
                }
            }
        }
    }


    public void drawTooltip(GraphicsContext gc) {
        for (TalentNode node : talent_Nodes) {
            if (node != null) {
                if (node.boundBox.contains(mg.inputH.lastMousePosition)) {
                    getTooltip(gc, node, mg.inputH.lastMousePosition.x, mg.inputH.lastMousePosition.y);
                }
            }
        }
    }

    private void getTooltip(GraphicsContext gc, TalentNode node, int startX, int startY) {
        //BACKGROUND
        gc.setFill(Colors.black_transparent);
        gc.fillRoundRect(startX - (MainGame.SCREEN_HEIGHT * 0.338), startY - (MainGame.SCREEN_HEIGHT * 0.114), MainGame.SCREEN_HEIGHT * 0.33, MainGame.SCREEN_HEIGHT * 0.124f, 15, 15);
        //OUTLINE
        gc.setStroke(Colors.purple_dark);
        gc.setLineWidth(2);
        gc.strokeRoundRect(startX - (MainGame.SCREEN_HEIGHT * 0.335), startY - (MainGame.SCREEN_HEIGHT * 0.111), MainGame.SCREEN_HEIGHT * 0.324, MainGame.SCREEN_HEIGHT * 0.118f, 15, 15);
        //NAME
        gc.setFill(Colors.white);
        gc.setFont(FonT.minecraftBoldItalic15);
        gc.fillText(node.talent.name, startX - MainGame.SCREEN_HEIGHT * 0.329f, startY - MainGame.SCREEN_HEIGHT * 0.098f);
        //description
        gc.setFont(FonT.minecraftItalic14);
        String[] lines = node.talent.description.split("\\n");
        float y = 0.088f;
        for (String line : lines) {
            gc.fillText(line, startX - MainGame.SCREEN_HEIGHT * 0.329f, startY - MainGame.SCREEN_HEIGHT * (y -= 0.015f));
        }
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

    public void createTalentNodes() {
        talent_Nodes[81] = new TalentNode(new TALENT(81, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), -296, -323);
        talent_Nodes[82] = new TalentNode(new TALENT(82, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), -239, -362);
        talent_Nodes[83] = new TalentNode(new TALENT(83, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), -419, -349);
        talent_Nodes[84] = new TalentNode(new TALENT(84, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), -478, -417);
        talent_Nodes[85] = new TalentNode(new TALENT(85, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), -539, -480);
        talent_Nodes[86] = new TalentNode(new TALENT(86, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), -481, -520);
        talent_Nodes[87] = new TalentNode(new TALENT(87, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), -426, -566);
        talent_Nodes[88] = new TalentNode(new TALENT(88, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), -366, -500);
        talent_Nodes[89] = new TalentNode(new TALENT(89, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), -306, -432);
        talent_Nodes[90] = new TalentNode(new TALENT(80, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), -175, -415);
        talent_Nodes[91] = new TalentNode(new TALENT(81, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), -117, -476);
        talent_Nodes[92] = new TalentNode(new TALENT(82, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), -45, -443);
        talent_Nodes[93] = new TalentNode(new TALENT(83, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), 45, -443);
        talent_Nodes[94] = new TalentNode(new TALENT(84, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), 113, -477);
        talent_Nodes[95] = new TalentNode(new TALENT(85, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), 170, -435);
        talent_Nodes[96] = new TalentNode(new TALENT(86, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), 0, -482);
        talent_Nodes[97] = new TalentNode(new TALENT(87, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), -45, -515);
        talent_Nodes[98] = new TalentNode(new TALENT(88, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), 45, -515);
        talent_Nodes[99] = new TalentNode(new TALENT(89, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), 112, -574);
        talent_Nodes[100] = new TalentNode(new TALENT(81, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), -117, -574);
        talent_Nodes[101] = new TalentNode(new TALENT(82, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), -191, -626);
        talent_Nodes[102] = new TalentNode(new TALENT(83, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), -268, -626);
        talent_Nodes[103] = new TalentNode(new TALENT(84, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), -191, -700);
        talent_Nodes[104] = new TalentNode(new TALENT(85, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), -268, -700);
        talent_Nodes[105] = new TalentNode(new TALENT(86, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), -176, -290);
        talent_Nodes[106] = new TalentNode(new TALENT(87, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), -230, -783);
        talent_Nodes[107] = new TalentNode(new TALENT(88, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), -122, -823);
        talent_Nodes[108] = new TalentNode(new TALENT(89, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), 0, -840);
        talent_Nodes[109] = new TalentNode(new TALENT(80, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), 1115, -824);
        talent_Nodes[110] = new TalentNode(new TALENT(81, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), 220, -477);
        talent_Nodes[111] = new TalentNode(new TALENT(82, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), 48, -924);
        talent_Nodes[112] = new TalentNode(new TALENT(83, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), -48, -35);
        talent_Nodes[113] = new TalentNode(new TALENT(84, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), 0, 1024);
        talent_Nodes[114] = new TalentNode(new TALENT(85, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), -44, -764);
        talent_Nodes[115] = new TalentNode(new TALENT(86, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), -44, -692);
        talent_Nodes[116] = new TalentNode(new TALENT(87, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), 44, -764);
        talent_Nodes[117] = new TalentNode(new TALENT(88, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), 44, -692);
        talent_Nodes[118] = new TalentNode(new TALENT(89, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), 0, -635);
    }

    private void getImages() {
        background = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/talents/background.png")));
        connection_red = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/talents/connection_red.png")));
        connection_orange = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/talents/connection_orange.png")));
        connection_green = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/talents/connection_green.png")));
        talentnode_green = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/talents/node_green.png")));
        talentnode_purple = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/talents/node_purple.png")));
        talentnode_orange = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/talents/node_orange.png")));
        talentnode_big = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/talents/talent_big.png")));
        talentnode_mid = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/talents/talent_mid.png")));
        talentnode_mid_green = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/talents/talent_mid_green.png")));
        talentnode_mid_purple = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/talents/talent_mid_purple.png")));
        talentnode_big_green = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/talents/talent_big_green.png")));
        talentnode_big_purple = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/ui/talents/talent_big_purple.png")));
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
