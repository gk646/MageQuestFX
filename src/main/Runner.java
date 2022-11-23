package main;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


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
