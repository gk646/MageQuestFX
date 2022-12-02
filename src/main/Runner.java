package main;

import javax.swing.*;
import java.awt.*;


public class Runner {

    public static JFrame window;
    public static JTextField textField;
    public static JSlider slider;

    /**
     * @author Lukas Gilch
     */
    public static void main(String[] args) {
        window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setUndecorated(true);
        window.setTitle("SERVER");
        MainGame mainGame = new MainGame();
        JTextField jTextField = new JTextField();
        jTextField.setVisible(false);
        jTextField.setSize(new Dimension(300, 100));
        JSlider jSlider = new JSlider(60, 240, 120);
        jSlider.setVisible(false);
        jSlider.setSize(new Dimension(200, 100));
        Runner.slider = jSlider;
        Runner.textField = jTextField;
        window.add(jTextField);
        window.add(jSlider);
        window.add(mainGame);
        window.pack();
        window.setVisible(true);
        mainGame.startGameThread();
    }
}
