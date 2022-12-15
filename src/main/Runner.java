package main;

import input.KeyHandler;
import main.system.ui.SkilltreeWindow;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;


public class Runner implements ActionListener {
    public static JFrame window;
    public static JTextField textField;
    public static JSlider slider;
    public static JPanel skillTree;

    /**
     * @author Lukas Gilch
     */
    public static void main(String[] args) {
      //System.setProperty("sun.java2d.opengl", "true");
        window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setTitle("Mage Quest_2D");

        //Fullscreen
        GraphicsEnvironment gE = GraphicsEnvironment.getLocalGraphicsEnvironment();
        MainGame mainGame = new MainGame(gE.getDefaultScreenDevice().getDisplayMode().getWidth(), gE.getDefaultScreenDevice().getDisplayMode().getHeight());
        window.setSize(gE.getDefaultScreenDevice().getDisplayMode().getWidth(), gE.getDefaultScreenDevice().getDisplayMode().getHeight());

        window.addKeyListener(new KeyHandler(mainGame));
        //gE.getDefaultScreenDevice().setFullScreenWindow(window);
        SkilltreeWindow skilltreeWindow = new SkilltreeWindow(mainGame);
        //window.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        try {
            Image image = ImageIO.read((Objects.requireNonNull(Runner.class.getResourceAsStream("/resources/icon/icon.png"))));
            window.setIconImage(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //maingame
        mainGame.setDoubleBuffered(true);
        //Skill tree
        skilltreeWindow.setLocation(250, 250);
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
        window.add(jTextField);
        window.add(jSlider);
        window.add(skilltreeWindow);
        window.add(mainGame);
        window.setUndecorated(true);
        window.pack();
        window.setVisible(true);
        mainGame.startGameThread();
    }

    /* TODO: 11.12.2022  make pathfinding respect collision
       TODO: 11.12.2022  item drops / level ups / skill pane / more ui / description
     */
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
