package main;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.util.Objects;


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
        try {
            Image image = ImageIO.read((Objects.requireNonNull(Runner.class.getResourceAsStream("/resources/icon/icon.png"))));
            window.setIconImage(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
