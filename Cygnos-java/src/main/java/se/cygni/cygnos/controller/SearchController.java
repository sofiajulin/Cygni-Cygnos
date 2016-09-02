package se.cygni.cygnos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.cygni.cygnos.model.Track;
import se.cygni.cygnos.service.Mp3PlayerService;
import se.cygni.cygnos.service.SearchProvider;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SearchController {

    private SearchProvider searchProvider;

    private final Mp3PlayerService player;

    @Autowired
    public SearchController(SearchProvider searchProvider, Mp3PlayerService player) {
        this.searchProvider = searchProvider;
        this.player = player;
    }

    @RequestMapping("/search/{query}")
    public List<Track> searchTrack(
            @PathVariable String query) throws Exception {

        List<Track> tracks = searchProvider.search(query);

        playSong(tracks, tracks.get(0));
        return tracks;
    }

    public void playSong(List<Track> tracks, Track currentTrack) throws Exception {
        final int i = tracks.indexOf(currentTrack);
        System.out.println("Playing track: " + currentTrack.getTrackName());

        player.play(tracks.get(i), playerState -> {
            try {
                if (i < tracks.size() - 1)
                playSong(tracks, tracks.get(i + 1));
                else
                playSong(tracks, tracks.get(0));

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
