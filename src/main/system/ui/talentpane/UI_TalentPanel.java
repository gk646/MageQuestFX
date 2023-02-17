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

    public static String insertNewLine(String str) {
        StringBuilder sb = new StringBuilder();
        String[] words = str.split("\\s+");
        int count = 0;
        for (String word : words) {
            if (count + word.length() > 58) {
                sb.append("\n");
                count = 0;
            }
            count += word.length();
            sb.append(word);
            sb.append(" ");
            count++;
        }
        return sb.toString();
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
        int count = 1;
        for (String line : lines) {
            if (count > 1) {
                gc.setFont(FonT.minecraftItalic11);
                gc.setFill(Colors.LightGrey);
                gc.fillText(line, startX - MainGame.SCREEN_HEIGHT * 0.329f, startY - MainGame.SCREEN_HEIGHT * (y -= 0.009f));
            } else {
                gc.fillText(line, startX - MainGame.SCREEN_HEIGHT * 0.329f, startY - MainGame.SCREEN_HEIGHT * (y -= 0.012f));
            }
            count++;
        }
    }

    private void drawLine(int offsetX, int offsetY, GraphicsContext gc, TalentNode requirement, TalentNode nextOne) {
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
        int e2, drawX, drawY;
        while (true) {
            if (counter % 8 == 0) {
                drawX = x0 + offsetX + 14;
                drawY = y0 + offsetY + 14;
                gc.drawImage(requirement.activated && nextOne.activated ? connection_green : requirement.activated ? connection_orange : !nextOne.activated ? connection_red : connection_orange, drawX, drawY);
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
       /*
        talent_Nodes[128] = new TalentNode(new TALENT(89, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), 0, -840);
        talent_Nodes[129] = new TalentNode(new TALENT(80, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), 1115, -824);
        talent_Nodes[130] = new TalentNode(new TALENT(81, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), 220, -477);
        talent_Nodes[131] = new TalentNode(new TALENT(82, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), 48, -924);
        talent_Nodes[132] = new TalentNode(new TALENT(83, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), -48, -35);
        talent_Nodes[133] = new TalentNode(new TALENT(84, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), 0, 1024);
        talent_Nodes[134] = new TalentNode(new TALENT(85, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), -44, -764);
        talent_Nodes[135] = new TalentNode(new TALENT(86, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), -44, -692);
        talent_Nodes[136] = new TalentNode(new TALENT(87, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), 44, -764);
        talent_Nodes[137] = new TalentNode(new TALENT(88, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), 44, -692);
        talent_Nodes[138] = new TalentNode(new TALENT(89, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), 0, -635)
        talent_Nodes[139] = new TalentNode(new TALENT(81, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), 182, -632);
        talent_Nodes[140] = new TalentNode(new TALENT(82, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), 182, -704);
        talent_Nodes[141] = new TalentNode(new TALENT(82, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), 259, -704);
        talent_Nodes[142] = new TalentNode(new TALENT(83, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), 259, -632);
        talent_Nodes[143] = new TalentNode(new TALENT(84, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), 271, 816);
        talent_Nodes[144] = new TalentNode(new TALENT(85, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), 202, -854);
        talent_Nodes[145] = new TalentNode(new TALENT(86, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), 291, -889);
        talent_Nodes[146] = new TalentNode(new TALENT(87, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), 199, -943);
        talent_Nodes[147] = new TalentNode(new TALENT(88, "Increase maximum mana", "mana.png", "Increases maximum mana by 5%", "w"), 243, -994);
        */
    }

    public void assignDescriptions() {
        String magicFind = """
                "Legend has it that the great wizard Merlin once went on a treasure hunt, and his magic find was so powerful that he accidentally uncovered the Holy Grail while searching for his car keys."
                "Rumor has it that the infamous pirate Blackbeard was always on the lookout for magical artifacts, but he never realized that the real treasure was the friends he made along the way... until he got a magic find boost and suddenly found a chest full of gold and riches."
                "Some say that the reason the philosopher's stone was so hard to find is that it was hidden in plain sight the whole time - all anyone needed was a magic find talent and a sharp eye for shiny things. Unfortunately, most alchemists were too busy trying to turn lead into gold to notice."
                "Back in the day, the great adventurer Jones was known for his ability to find ancient relics and artifacts..."
                "There's an old tale about a wealthy merchant who hired a team of mages to enchant his entire mansion with magic find spells, hoping to discover hidden riches that had been lost for centuries. Unfortunately, all they found were a bunch of dusty old cobwebs and a few silver spoons - but hey, at least the mansion is enchanted now.""";
        addDescription("magic_find", magicFind);

        String intelligence = """
                "Rumor has it that the great philosopher Aristotle once stumbled upon a hidden library filled with ancient texts and scrolls. His intelligence was so high that he immediately recognized the value of the priceless manuscripts - but he accidentally left them at the market on the way home."
                "Legend has it that the famous mage Merlin was known for his incredible intelligence, which he used to discover hidden truths and ancient knowledge. But one day, he got so lost in thought that he accidentally walked into a wall - proof that even the smartest among us can have a momentary lapse of judgment."
                "There's an old story about a group of mages who were tasked with finding a rare spell that could unlock untold power. They scoured the land for months, using every trick in the book to increase their intelligence find - but it wasn't until they stumbled upon a local pub and started swapping stories that they finally uncovered the secret they were looking for."
                "It is said that the great scientist Isaac Newton once stumbled upon a hidden room filled with ancient tomes and esoteric knowledge. His intelligence was so great that he could understand every word on the page - but when he tried to explain his discoveries to his cat, the feline just stared at him with a look of disdain, reminding him that intelligence isn't everything."
                  """;
        addDescription("book", intelligence);
    }

    private void addDescription(String name, String text) {
        for (TalentNode node : talent_Nodes) {
            if (node != null) {
                if (node.talent.imagePath.contains(name)) {
                    node.talent.description += "\n\n" + insertNewLine(text.split("\n")[mg.random.nextInt(0, text.split("\n").length)]);
                }
            }
        }
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
            for (int b = 0; b < matrix.length; b++) {
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
        wholeTalentWindow.x = 0;
        wholeTalentWindow.y = 0;
    }

    public void hideTalentCollision() {
        wholeTalentWindow.x = -2_000;
        wholeTalentWindow.y = -2_000;
    }
}
