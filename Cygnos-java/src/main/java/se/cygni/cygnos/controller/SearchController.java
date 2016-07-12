package se.cygni.cygnos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.cygni.cygnos.model.Track;
import se.cygni.cygnos.service.SearchProvider;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SearchController {

    private SearchProvider searchProvider;

    @Autowired
    public SearchController(SearchProvider searchProvider) {
        this.searchProvider = searchProvider;
    }

    @RequestMapping("/search/{query}")
    public List<Track> searchTrack(
            @PathVariable String query) throws Exception {

        return searchProvider.search(query);
    }
}
