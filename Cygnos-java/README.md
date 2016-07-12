#Backend Cygnos - a starter plate

This is a simple backend that exposed a couple of REST endpoints for searching tracks and playing, pausing and stopping a track. It is also possible to query the current state and volume as well as setting the volume.

##Prerequisites

1. Java 8 with Java FX (OpenJDK won't work)
2. curl for running the examples below

##Running
```
./mvnw spring-boot:run
```

##Web page
There is a simple web page that illustrates the search and playing of tracks here:

http://localhost:8080/index.html

##Examples

###Search for track [GET]
```
curl http://localhost:8080/api/search/helt%20sjukt
```

###Play a track [POST, body]
```
curl -H "Content-Type: application/json" -X POST -d '{ "id": "3iGbTfFPGC81wkArfwIqIn", "previewUrl": "https://p.scdn.co/mp3-preview/9e4c6d6418bf191c87a79b6b82a7b262d2d126bf", "artistName": "Nostra Love", "trackName": "Helt Sjukt", "albumName": "Helt Sjukt" }' http://localhost:8080/api/player/play
```

###Pause [POST] 
> Note: post pause again to unpause current state

```
curl -X POST http://localhost:8080/api/player/pause
```

###Stop [POST]
```
curl -X POST http://localhost:8080/api/player/stop
```

###Current volume [GET]
```
curl http://localhost:8080/api/player/volume
```

###Set volume [POST, query params] 
> Note: volume must be between 0 and 1.0

```
curl -X POST http://localhost:8080/api/player/volume?volume=0.6
```

###Current state [GET]
```
curl http://localhost:8080/api/player/state
```