using ApiBookingApplication.Model;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace ApiBookingApplication.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class RoomForCustomerController : ControllerBase
    {
        private readonly DormitoryBookingContext ct;

        public RoomForCustomerController(DormitoryBookingContext _ct)
        {
            this.ct = _ct;

        }

        [HttpGet]
        public IActionResult getListRoom(int TypeRoomId, string BuildingName)
        {
            var x = ct.Buildings.FirstOrDefault(x => x.Name == BuildingName);

            var b = ct.Buildings.Include(c => c.Rooms).FirstOrDefault(c => c.Id == x.Id);

            b.Rooms = b.Rooms.Where(r => r.TypeId == TypeRoomId).OrderBy(r => r.Name).ToList();

            return Ok(b);
        }

        [HttpGet("listbuilding")]
        public IActionResult getListRoom2()
        {
            var b = ct.Buildings.Include(c => c.Rooms).Select(c => new
            {
                id = c.Id,
                name = c.Name,
            }

                ).ToList();


            return Ok(b);
        }

        [HttpGet("bookingRoom")]
        public IActionResult bookingRoom(string userId, string roomId)
        {
            try
            {
                int soNguyen1 = int.Parse(userId);
                int soNguyen2 = Convert.ToInt32(roomId);


                var u = ct.Users.FirstOrDefault(c => c.Id == soNguyen1);
                var r = ct.Rooms.Include(c => c.Type).FirstOrDefault(c => c.Id == soNguyen2);

                if (u == null || r == null)
                    return BadRequest();

                r.CurrentPeople = r.CurrentPeople++;
                if (r.Type.Capacity == r.CurrentPeople)
                    r.IsAvailble = false;

                u.CurrentRoomId = soNguyen2;
                var semesters = ct.Semesters.ToList();
                DateTime currentDate = DateTime.Now;

                foreach (var s in semesters)
                {
                    if (currentDate >= s.DateStart && currentDate <= s.DateEnd)
                    {
                        ct.Bookings.Add(new Booking() { RoomId = soNguyen2, UserId = soNguyen1, SemesterId = s.Id });
                        break;
                    }
                }
                u.Balance = u.Balance - r.Type.Price;

                ct.SaveChanges();

                return Ok(new { status = "success" });

            }
            catch (Exception)
            {
                return BadRequest();
            }
        }

        [HttpGet("GetCurrentRoom")]
        public IActionResult GetRoomCurrent(string userId)
        {
            try
            {
                int soNguyen1 = int.Parse(userId);
                var u = ct.Users.Include(c => c.CurrentRoom).ThenInclude(c => c.Building).FirstOrDefault(c => c.Id == soNguyen1);

                if (u.CurrentRoomId == null)
                {
                    return BadRequest();
                }
                else
                {
                    return Ok(new { status = "true", roomName = u.CurrentRoom.Name, buildingName = u.CurrentRoom.Building.Name });
                }
            }
            catch (Exception)
            {
                return BadRequest();
            }
        }

        [HttpGet("GetCurrentDeserve")]
        public IActionResult GetRoomCurrent2(string userId)
        {
            try
            {
                int soNguyen1 = int.Parse(userId);
                var u = ct.Users.Include(c => c.CurrentRoom).ThenInclude(c => c.Building).FirstOrDefault(c => c.Id == soNguyen1);
                if (u.Active == true)
                {
                    return Ok(new { status = "true" });
                }
                else
                {
                    return Ok(new { status = "false" });
                }
            }
            catch (Exception)
            {
                return BadRequest();
            }
        }

        [HttpGet("Deserve")]
        public IActionResult GetRoomCurrent3(string userId)
        {
            try
            {
                int soNguyen1 = int.Parse(userId);
                var u = ct.Users.Include(c => c.CurrentRoom).ThenInclude(c => c.Building).Include(c => c.CurrentRoom).ThenInclude(c => c.Type).FirstOrDefault(c => c.Id == soNguyen1);
                u.Active = false;
                u.Balance = u.Balance - u.CurrentRoom.Type.Price;
                ct.SaveChanges();
                return Ok(new { status = "true" });

            }
            catch (Exception)
            {
                return BadRequest();
            }
        }
    }
}
