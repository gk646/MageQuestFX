package main.system.sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URISyntaxException;

public class Sound {
    {
        try {
            fileName = getClass().getResource("/resources/sound/music/intromore.wav").toURI().toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    String fileName;
    Media soundMedia = new Media(fileName);

    // Create a MediaPlayer object
    public MediaPlayer mediaPlayer = new MediaPlayer(soundMedia);

    public Sound() {

    }

    // Play the sound file
}
