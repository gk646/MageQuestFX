package gameworld.player;

import gameworld.entities.loadinghelper.GeneralResourceLoader;
import javafx.scene.canvas.GraphicsContext;
import main.MainGame;


public class PlayerPrompts {

    public boolean F;
    final MainGame mg;
    final GeneralResourceLoader resc = new GeneralResourceLoader("ui/prompts/e");
    private int spriteCounter;
    int Ecounter = 0;

    public PlayerPrompts(MainGame mg) {
        this.mg = mg;
    }


    public void draw(GraphicsContext gc) {
        if (Ecounter < 60) {
            Ecounter++;
            spriteCounter++;
            switch (spriteCounter % 120 / 30) {
                case 0 -> gc.drawImage(resc.images1.get(0), Player.screenX, Player.screenY - 48);
                case 1 -> gc.drawImage(resc.images1.get(1), Player.screenX, Player.screenY - 48);
                case 2 -> gc.drawImage(resc.images1.get(2), Player.screenX, Player.screenY - 48);
                case 3 -> gc.drawImage(resc.images1.get(3), Player.screenX, Player.screenY - 48);
            }
        } else if (F) {

        }
    }

    public void setETrue() {
        Ecounter = 0;
    }
}
