package gameworld.player;

import gameworld.entities.loadinghelper.GeneralResourceLoader;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;


public class PlayerPrompts {

    public boolean E;
    MainGame mg;
    GeneralResourceLoader resc = new GeneralResourceLoader("hey");
    private int spriteCounter;

    public PlayerPrompts(MainGame mg) {
        this.mg = mg;
        resc.loadImages1("ui/prompts/e");
    }


    public void draw(GraphicsContext gc) {
        if (E) {
            switch (spriteCounter % 120 / 30) {
                case 0 -> gc.drawImage(resc.images1.get(0), Player.screenX, Player.screenY - 48);
                case 1 -> gc.drawImage(resc.images1.get(1), Player.screenX, Player.screenY - 48);
                case 2 -> gc.drawImage(resc.images1.get(2), Player.screenX, Player.screenY - 48);
                case 3 -> gc.drawImage(resc.images1.get(3), Player.screenX, Player.screenY - 48);
            }
        }
        spriteCounter++;
    }
}
