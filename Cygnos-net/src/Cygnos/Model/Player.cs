using System.IO;
using System.Net;
using NAudio.Wave;

namespace Cygnos.Model
{
    public class Player : IPlayer
    {
        private WaveOut output;
        private Mp3FileReader reader;

        public void Play(string url)
        {
            output?.Stop();

            var ms = new MemoryStream();
            using (var stream = WebRequest.Create(url).GetResponse().GetResponseStream())
            {
                byte[] buffer = new byte[32768];
                int read;
                while ((read = stream.Read(buffer, 0, buffer.Length)) > 0)
                {
                    ms.Write(buffer, 0, read);
                }
                ms.Position = 0;
            }

            reader = new Mp3FileReader(ms);
            output = new WaveOut();
            output.Init(reader);
            output.Play();
        }
      
        public void Stop()
        {
            output?.Stop();
            output = null;
        }

        public PlayerState State
        {
            get
            {
                switch (output.PlaybackState)
                {
                    case PlaybackState.Paused:
                        return PlayerState.Paused;
                    case PlaybackState.Playing:
                        return PlayerState.Playing;
                    default:
                        return PlayerState.Stopped;
                }
            }
        }

        public float Volume
        {
            get { return output?.Volume ?? 0; }
            set
            {
                if (output != null)
                    output.Volume = value;
            }
        }
    }
}