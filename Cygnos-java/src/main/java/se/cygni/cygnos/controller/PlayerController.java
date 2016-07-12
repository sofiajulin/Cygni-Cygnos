package se.cygni.cygnos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import se.cygni.cygnos.model.PlayerState;
import se.cygni.cygnos.model.Track;
import se.cygni.cygnos.service.Mp3PlayerService;

import java.net.URL;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

    private final Mp3PlayerService player;

    @Autowired
    public PlayerController(Mp3PlayerService player) {
        this.player = player;
    }

    @RequestMapping(path = "/volume",
            method = RequestMethod.GET)
    public double volume() {
        return player.getVolume();
    }

    @RequestMapping(path = "/volume",
            method = RequestMethod.POST)
    public void volume(@RequestParam(value = "volume") double volume) throws Exception {

        if (volume < 0 || volume > 1.0) {
            throw new Exception("Bad value for volume, must be between 0 and 1.0");
        }

        player.setVolume(volume);
    }

    @RequestMapping("/state")
    public PlayerState state() {
        return player.getState();
    }

    @RequestMapping(path = "/pause",
            method = RequestMethod.POST)
    public void pause() {
        player.pause();
    }

    @RequestMapping(value = "/play",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void play(@RequestBody Track track) throws Exception {

        // Verify correct url
        new URL(track.getPreviewUrl());

        player.play(track);
    }

    @RequestMapping(path = "/stop",
            method = RequestMethod.POST)
    public void stop() {
        player.stop();
    }
}
