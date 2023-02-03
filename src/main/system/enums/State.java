package main.system.enums;

public enum State {
    START(-2), TITLE_OPTION(-1), TITLE(0), PLAY(1), OPTION(2), TALENT(3), GAMEOVER(4);

    public final int value;

    State(int value) {
        this.value = value;
    }
}
