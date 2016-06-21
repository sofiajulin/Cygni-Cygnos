namespace Cygnos.Model
{
    public enum PlayerState
    {
        Paused,
        Playing,
        Stopped
    }

    public interface IPlayer
    {
        PlayerState State { get; }       
        void Play(string url);   
        void Stop();
        float Volume { get; set; }
    }
}