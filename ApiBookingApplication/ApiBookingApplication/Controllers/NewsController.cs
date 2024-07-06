using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ApiBookingApplication.DTOs;
using ApiBookingApplication.Model;
using AutoMapper;

namespace ApiBookingApplication.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class NewsController : ControllerBase
    {
        private readonly DormitoryBookingContext _bookingContext;
        private readonly IMapper _mapper;

        public NewsController(DormitoryBookingContext dormitoryBookingContext, IMapper mapper)
        {
            _bookingContext = dormitoryBookingContext;
            _mapper = mapper;
        }

        // GET: api/News
        [HttpGet]
        public async Task<ActionResult<IEnumerable<NewsDTO>>> GetNews()
        {
            var newsList = await _bookingContext.News.ToListAsync();
            return Ok(_mapper.Map<IEnumerable<NewsDTO>>(newsList));
        }

        // GET: api/News/5
        [HttpGet("{id}")]
        public async Task<ActionResult<NewsDTO>> GetNews(int id)
        {
            var news = await _bookingContext.News.FindAsync(id);
            if (news == null)
            {
                return NotFound();
            }

            return Ok(_mapper.Map<NewsDTO>(news));
        }

        [HttpPost]
        public async Task<ActionResult<NewsDTO>> PostNews([FromBody] NewsDTO newsDto)
        {
        

        // Ensure the Id is not set
        newsDto.Id = 0;

            var news = _mapper.Map<News>(newsDto);
            _bookingContext.News.Add(news);
            await _bookingContext.SaveChangesAsync();

            return CreatedAtAction(nameof(GetNews), new { id = news.Id }, _mapper.Map<NewsDTO>(news));
        }

        // PUT: api/News/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutNews(int id, NewsDTO newsDto)
        {
            if (id != newsDto.Id)
            {
                return BadRequest();
            }

            var news = _mapper.Map<News>(newsDto);
            _bookingContext.Entry(news).State = EntityState.Modified;

            try
            {
                await _bookingContext.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!NewsExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        // DELETE: api/News/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteNews(int id)
        {
            var news = await _bookingContext.News.FindAsync(id);
            if (news == null)
            {
                return NotFound();
            }

            _bookingContext.News.Remove(news);
            await _bookingContext.SaveChangesAsync();

            return NoContent();
        }

        private bool NewsExists(int id)
        {
            return _bookingContext.News.Any(e => e.Id == id);
        }
    }
}
