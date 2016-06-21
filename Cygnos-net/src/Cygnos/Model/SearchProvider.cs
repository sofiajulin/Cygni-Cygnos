using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text.Encodings.Web;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace Cygnos.Model
{
    public class Track
    {
        public Track(string id, string previewUrl, string artistName, string trackName)
        {
            Id = id;
            PreviewUrl = previewUrl;
            ArtistName = artistName;
            TrackName = trackName;
        }

        [JsonProperty("id")]
        public string Id { get; }
        [JsonProperty("previewUrl")]
        public string PreviewUrl { get; }
        [JsonProperty("artistName")]
        public string ArtistName { get; }
        [JsonProperty("trackName")]
        public string TrackName { get; }
    }

    public interface ISearchProvider
    {
        IEnumerable<Track> Search(string query);
    }

    public class SearchProvider : ISearchProvider
    {
        public IEnumerable<Track> Search(string query)
        {
            var client = new HttpClient();
            client.BaseAddress = new Uri("https://api.spotify.com/v1/search");
            client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

            // List data response.
            HttpResponseMessage response = client.GetAsync($"?q={UrlEncoder.Create().Encode(query)}&type=track").Result;
            if (response.IsSuccessStatusCode)
            {
                var responseBody = response.Content.ReadAsStringAsync().Result;
                var jobject = JObject.Parse(responseBody);
                foreach (var track in jobject["tracks"]["items"])
                {                    
                    yield return new Track(track["id"].ToString(), track["preview_url"].ToString(), track["artists"].First()["name"].ToString(), track["name"].ToString());
                }
            }
        }

    }
}
