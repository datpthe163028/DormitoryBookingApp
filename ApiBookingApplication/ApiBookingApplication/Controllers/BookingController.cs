using ApiBookingApplication.Common;
using ApiBookingApplication.Service.Account;
using ApiBookingApplication.Service.Booking;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace ApiBookingApplication.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class BookingController : ControllerBase
    {
        private readonly IBookingService _bookingService;

        public BookingController(IBookingService bookingService)
        {
            _bookingService = bookingService;
        }

        [HttpPost("Add")]
        public async Task<IActionResult> Add([FromBody] BookingRequest bookingRequest)
        {
            (string errorMessage, string id, string BookingID) = await _bookingService.Add(bookingRequest);
            if (!string.IsNullOrEmpty(errorMessage))
            {
                return Ok(new ResponseBaseModel() { Data = null, Message = errorMessage, Status = 400 });
            }
            return Ok(new ResponseBaseModel() { Data = new { id = id, BookingID = BookingID }, Message = "Success", Status = 200 });
        }

        [HttpPost("Swap Request")]
        public async Task<IActionResult> SwapReq([FromBody] SwapBookingRequest SwapBookingRequest)
        {
            (string errorMessage, string UserIDReq, string UserIDTarget) = await _bookingService.SwapReq(SwapBookingRequest);
            if (!string.IsNullOrEmpty(errorMessage))
            {
                return Ok(new ResponseBaseModel() { Data = null, Message = errorMessage, Status = 400 });
            }
            return Ok(new ResponseBaseModel() { Data = new { UserIDReq = UserIDReq, UserIDTarget = UserIDTarget }, Message = "Success", Status = 200 });
        }

        [HttpPost("Swap")]
        public async Task<IActionResult> Swap([FromBody] SwapBooking SwapBookingRequest)
        {
            (string errorMessage, int? UserIDReq, int? UserIDTarget) = await _bookingService.Swap(SwapBookingRequest);
            if (!string.IsNullOrEmpty(errorMessage))
            {
                return Ok(new ResponseBaseModel() { Data = null, Message = errorMessage, Status = 400 });
            }
            return Ok(new ResponseBaseModel() { Data = new { UserIDReq = UserIDReq, UserIDTarget = UserIDTarget }, Message = "Success", Status = 200 });
        }
    }
}
