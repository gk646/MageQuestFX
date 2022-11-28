package main;

import javax.swing.*;


public class Runner {

    public static JFrame window;

    /**
     * @author Lukas Gilch
     */
    public static void main(String[] args) {
        window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setUndecorated(true);
        window.setTitle("SERVER");
        MainGame mainGame = new MainGame();
        window.add(mainGame);
        window.pack();
        window.setVisible(true);
        mainGame.startGameThread();
    }
}
