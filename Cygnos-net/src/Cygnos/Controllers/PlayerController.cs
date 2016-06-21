using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Cygnos.Model;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json.Linq;

namespace Cygnos.Controllers
{
    public class foo
    {
        public string a { get; set; }
    }

    [Route("api/[controller]")]
    public class PlayerController : Controller
    {
        private readonly IPlayer player;

        public PlayerController(IPlayer player)
        {
            this.player = player;
        }

        // GET api/values
        [HttpGet]
        public PlayerState Get()
        {
            return player.State;
        }

        [HttpPost("play")]
        public void PostTrack([FromBody] Track t)
        {
            if (t?.PreviewUrl != null)
            {
                player.Play(t.PreviewUrl);
            }
        }


        [HttpPost("stop")]
        public void PostStop()
        {
            player.Stop();
        }


        [HttpGet("volume")]
        public float GetVolume()
        {
            return player.Volume;
        }

        [HttpPost("volume")]
        public void PostVolume([FromBody] float value)
        {
            player.Volume = value;
        }
    }
}
