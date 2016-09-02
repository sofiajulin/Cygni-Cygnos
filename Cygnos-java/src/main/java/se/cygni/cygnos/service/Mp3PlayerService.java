package se.cygni.cygnos.service;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.springframework.stereotype.Service;
import se.cygni.cygnos.model.PlayerState;
import se.cygni.cygnos.model.Track;

import java.io.File;
import java.net.URL;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
public class Mp3PlayerService {

    private MediaPlayer mediaPlayer;
    private double volume = 0.5;
    private PlayerState state = PlayerState.Stopped;
    private final String tmpDir = System.getProperty("java.io.tmpdir");


    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            switch (state) {
                case Paused:
                    mediaPlayer.play();
                    this.state = PlayerState.Playing;
                    break;
                case Playing:
                    mediaPlayer.pause();
                    this.state = PlayerState.Paused;
                    break;
            }

        } else {
            this.state = PlayerState.Stopped;
        }
    }

    public void play(Track track, Consumer<PlayerState> callback) throws Exception {

        // MediaPlayer can't fetch resources over https
        // Store the file temporarily on disk...
        Downloader downloader = new Downloader();
        String fileName = tmpDir + track.getId() + ".mp3";
        downloader.download(new URL(track.getPreviewUrl()), new File(fileName));

        if (mediaPlayer != null) {
            stop();
        }

        // Now read
        mediaPlayer = new MediaPlayer(new Media(
                new File(fileName).toURI().toString()));
        mediaPlayer.setVolume(volume);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                state = PlayerState.Stopped;
                callback.accept(state);
            }
        });
        mediaPlayer.play();

        this.state = PlayerState.Playing;
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        mediaPlayer = null;
        this.state = PlayerState.Stopped;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }

    public PlayerState getState() {
        return state;
    }
}
