package se.cygni.cygnos.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Service;
import se.cygni.cygnos.model.Track;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class SearchProvider {

    public List<Track> search(String query) throws Exception {

        List<Track> tracks = new ArrayList<>();

        URI uri = new URIBuilder("https://api.spotify.com/v1/search")
                .addParameter("q", query)
                .addParameter("type", "track")
                .build();

        String response = Request.Get(
                uri).
                execute().
                returnContent().asString();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);

        JsonNode items = rootNode.path("tracks").path("items");

        Iterator<JsonNode> elements = items.elements();
        while(elements.hasNext()){
            JsonNode item = elements.next();
            if (item.path("type").asText().equals("track")) {
                tracks.add(new Track(
                        getValue(item, "id"),
                        getValue(item, "preview_url"),
                        getValue(item.path("artists").get(0), "name"),
                        getValue(item, "name"),
                        getValue(item, "album", "name")
                ));
            }
        }

        return tracks;
    }

    private String getValue(JsonNode node, String...elements) {
        JsonNode endNode = node;
        for (String element : elements) {
            endNode = endNode.path(element);
        }
        return endNode.asText();
    }
}
