#Backend Cygnos - a starter plate

This is a simple backend that exposed a couple of REST endpoints for searching tracks and playing, pausing and stopping a track. It is also possible to query the current state and volume as well as setting the volume.

##Prerequisites

1. VisualStudio 2015 with latest updates!

##Running
Open solution, hit F5

##Web page
There is a simple web page that illustrates the search and playing of tracks here:

http://localhost:58068/index.html 

##Examples

###Search for track [GET]
```
curl http://localhost:58068/api/search/helt%20sjukt
```

###Play a track [POST, body]
```
curl -H "Content-Type: application/json" -X POST -d '{ "id": "3iGbTfFPGC81wkArfwIqIn", "previewUrl": "https://p.scdn.co/mp3-preview/9e4c6d6418bf191c87a79b6b82a7b262d2d126bf", "artistName": "Nostra Love", "trackName": "Helt Sjukt", "albumName": "Helt Sjukt" }' http://localhost:58068/api/player/play
```

###Stop [POST]
```
curl -X POST http://localhost:58068/api/player/stop -d null
```

###Current volume [GET]
```
curl http://localhost:58068/api/player/volume
```

##References
[Spotify API](https://developer.spotify.com/web-api/)
[Leap Motion API](https://developer.leapmotion.com/documentation/javascript/devguide/Leap_Overview.html)
[LeapJS](https://github.com/leapmotion/leapjs)
