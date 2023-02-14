package main.system.sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URISyntaxException;
import java.util.Objects;

public class Sound {
    {
        try {
            fileName = Objects.requireNonNull(getClass().getResource("/resources/sound/music/intro_new.wav")).toURI().toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private final String fileName;
    private final Media soundMedia = new Media(fileName);

    // Create a MediaPlayer object
    public MediaPlayer mediaPlayer = new MediaPlayer(soundMedia);

    public Sound() {

    }

    // Play the sound file
}
