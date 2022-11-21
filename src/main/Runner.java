package main;

import javax.swing.*;


public class Runner {
    /**
     * @author Lukas Gilch
     */
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Game");
        MainGame mainGame = new MainGame();
        window.add(mainGame);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        mainGame.startGameThread();

    }
}
