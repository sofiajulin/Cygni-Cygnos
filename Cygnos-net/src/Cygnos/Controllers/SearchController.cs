using System.Collections.Generic;
using System.Linq;
using Cygnos.Model;
using Microsoft.AspNetCore.Mvc;

// For more information on enabling Web API for empty projects, visit http://go.microsoft.com/fwlink/?LinkID=397860

namespace Cygnos.Controllers
{
    [Route("api/[controller]")]
    public class SearchController : Controller
    {
        private readonly ISearchProvider provider;

        public SearchController(ISearchProvider provider)
        {
            this.provider = provider;
        }

        // GET api/values/5
        [HttpGet("{query}")]
        public IEnumerable<Track> Get(string query)
        {
            return provider.Search(query).ToList();
        }
        
    }
}
