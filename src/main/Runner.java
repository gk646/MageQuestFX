package main;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Runner {
    public static DataOutputStream outputStream;
    public static DataInputStream inputStream;
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
        /*try {

            //ServerSocket serverSocket = new ServerSocket(2525);
            // s =  serverSocket.accept() ;
            Socket s = new Socket("192.168.2.47",2525);
            outputStream= new DataOutputStream(s.getOutputStream());
            inputStream = new DataInputStream(s.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

         */

        mainGame.startGameThread();

    }
}
