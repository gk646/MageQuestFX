package ui;

import main.MainGame;

import java.awt.*;

public class UI {
    MainGame mg;
    Font arial_40, arial_80b;
    public UI(MainGame mainGame){
        this.mg = mainGame;
        arial_40= new Font("Arial", Font.PLAIN,40);
        arial_80b = new Font("Arial",Font.BOLD, 80);

    }

}
