package gameworld.quest;

abstract public class QUEST {

    public String name;
    public String objective;
    public int progressStage = 1;
    public String progressStageName;


    public QUEST(String name) {
        this.name = name;
    }

    abstract public void update();
}
