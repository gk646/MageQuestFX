package main.system.sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;

public class Sound {
    public static ArrayList<MediaPlayer> ambientTracks = new ArrayList<>();

    public MediaPlayer INTRO;

    public Sound() {
    }

    public void loadSounds() {
        INTRO = new MediaPlayer(new Media(getClass().getResource("/resources/sound/music/intro_new.wav").toString()));
        loadDungeonAmbience();
    }

    private void loadDungeonAmbience() {
        MediaPlayer ambientTrack1 = new MediaPlayer(new Media(getClass().getResource("/resources/sound/music/dungeonAmbience/ambience03.wav").toString()));
        MediaPlayer ambientTrack2 = new MediaPlayer(new Media(getClass().getResource("/resources/sound/music/dungeonAmbience/ambience04.wav").toString()));
        ambientTracks.add(ambientTrack1);
        ambientTracks.add(ambientTrack2);
    }
}
