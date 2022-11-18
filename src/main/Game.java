package main;

import javax.swing.*;
public class Game {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Game");
        Display display = new Display();
        window.add(display);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        display.startGameThread();

    }
}
