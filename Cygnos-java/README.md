#Backend Cygnos - a starter plate

This is a simple backend that exposed a couple of REST endpoints for searching tracks and playing, pausing and stopping a track. It is also possible to query the current state and volume as well as setting the volume.

##Examples

###Search for track [GET]
```
curl localhost:8080/api/search?query=helt%20sjukt
```

###Play a track [POST, body]
```
curl -H "Content-Type: application/json" -X POST -d '{ "id": "3iGbTfFPGC81wkArfwIqIn", "previewUrl": "https://p.scdn.co/mp3-preview/9e4c6d6418bf191c87a79b6b82a7b262d2d126bf", "artistName": "Nostra Love", "trackName": "Helt Sjukt", "albumName": "Helt Sjukt" }' http://localhost:8080/api/play
```

###Pause [POST] 
> Note: post pause again to unpause current state

```
curl -X POST http://localhost:8080/api/pause
```

###Stop [POST]
```
curl -X POST http://localhost:8080/api/stop
```

###Current volume [GET]
```
curl http://localhost:8080/api/volume
```

###Set volume [POST, query params] 
> Note: volume must be between 0 and 1.0

```
curl -X POST http://localhost:8080/api/volume?volume=0.6
```

###Current state [GET]
```
curl http://localhost:8080/api/state
```