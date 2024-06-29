using ApiBookingApplication.Model;
using ApiBookingApplication.DTOs;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using AutoMapper;
using System.Collections.Generic;
using System.Linq;

namespace ApiBookingApplication.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class BookingHistoryController : ControllerBase
    {
        private readonly DormitoryBookingContext _bookingContext;
        private readonly IMapper _mapper;

        public BookingHistoryController(DormitoryBookingContext dormitoryBookingContext, IMapper mapper)
        {
            _bookingContext = dormitoryBookingContext;
            _mapper = mapper;
        }

        // GET: api/BookingHistory/user/{userId}
        [HttpGet("user/{userId}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public ActionResult<IEnumerable<BookingHistoryDTO>> GetBookingHistoryByUserId(int userId)
        {
            // Query bookings related to the user ID
            var bookings = _bookingContext.Bookings
                .Include(b => b.Room)
                    .ThenInclude(r => r.Type)
                .Include(b => b.Semester)
                .Include(b => b.User)
                .Include(b => b.Room.Building) // Include building for DTO mapping
                .Where(b => b.UserId == userId)
                .ToList();

            if (bookings == null || bookings.Count == 0)
            {
                return NotFound();
            }

            // Map Booking entities to BookingHistoryDTO using AutoMapper
            var bookingHistoryDTOs = _mapper.Map<List<BookingHistoryDTO>>(bookings);

            return Ok(bookingHistoryDTOs);
        }
    }
}
