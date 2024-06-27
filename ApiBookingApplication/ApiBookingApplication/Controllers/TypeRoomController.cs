using ApiBookingApplication.Model;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace ApiBookingApplication.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class TypeRoomController : ControllerBase
    {
        private readonly DormitoryBookingContext _bookingContext;
        public TypeRoomController(DormitoryBookingContext dormitoryBookingContext) {
            _bookingContext = dormitoryBookingContext;
        }

        [HttpGet]
        public IActionResult getList()
        {
            return Ok(_bookingContext.RoomTypes.ToList());
        }
    }
}
