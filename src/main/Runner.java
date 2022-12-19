package main;

import main.system.ui.InventoryPanel;
import main.system.ui.SkilltreeWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Runner {
    public static JFrame window;
    public static JTextField textField;
    public static JSlider slider;
    public static JPanel skillTree;
    public static InventoryPanel inventP;

    /**
     * @author Lukas Gilch
     */
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setTitle("Mage Quest_2D");
        //Fullscreen
        GraphicsEnvironment gE = GraphicsEnvironment.getLocalGraphicsEnvironment();
        MainGame mainGame = new MainGame(gE.getDefaultScreenDevice().getDisplayMode().getWidth(), gE.getDefaultScreenDevice().getDisplayMode().getHeight());
        window.setSize(gE.getDefaultScreenDevice().getDisplayMode().getWidth(), gE.getDefaultScreenDevice().getDisplayMode().getHeight());
        //window.addKeyListener(new KeyHandler(mainGame));
        window.setCursor(new Cursor(Cursor.HAND_CURSOR));
        try {
            Image image = ImageIO.read((Objects.requireNonNull(Runner.class.getResourceAsStream("/resources/icon/icon.png"))));
            window.setIconImage(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Main Game
        mainGame.setDoubleBuffered(true);
        //InventoryPanel
        InventoryPanel inventP = new InventoryPanel(mainGame);
        inventP.setVisible(false);
        inventP.setLocation(150, 150);
        //Skill tree
        SkilltreeWindow skilltreeWindow = new SkilltreeWindow(mainGame);
        skilltreeWindow.setVisible(false);
        //TEXT FIELD
        JTextField jTextField = new JTextField();
        jTextField.setVisible(false);
        jTextField.setSize(new Dimension(300, 100));
        //SLIDER
        JSlider jSlider = new JSlider(60, 240, 120);
        jSlider.setVisible(false);
        jSlider.setSize(new Dimension(200, 100));
        Runner.slider = jSlider;
        Runner.textField = jTextField;
        Runner.skillTree = skilltreeWindow;
        Runner.inventP = inventP;
        window.add(jTextField);
        window.add(jSlider);
        window.add(skilltreeWindow);
        window.add(inventP);
        window.add(mainGame);
        window.setUndecorated(true);
        window.pack();
        window.setVisible(true);
        mainGame.startGameThread();
    }
    /*
       TODO: 11.12.2022  item drops / level ups / skill pane / more ui / description
     */

}
