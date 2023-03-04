package main;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Launcher {
    public static void main(String[] args) throws FileNotFoundException {
        try {
            Runner.main(args);
        } catch (Exception e) {
            PrintWriter writer = new PrintWriter("error.log");
            e.printStackTrace(writer);
            writer.close();
            System.exit(1);
        }
    }
}
