using ApiBookingApplication.Common;
using ApiBookingApplication.Model;
using ApiBookingApplication.Service.Admin.CRUDRoom;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace ApiBookingApplication.Controllers.Admin.CRUDRoom
{
    [ApiController]
    [Route("api/[controller]")]
    public class RoomController : ControllerBase
    {
        private readonly IRoomService _roomService;
        public RoomController(IRoomService roomService)
        {
            _roomService = roomService;
        }
        [HttpGet]
        public async Task<IActionResult> GetAllRooms()
        {
            (List<Room> list, string message) = await _roomService.GetAllRooms();

            return Ok(new ResponseBaseModel() 
            { 
                Data = new { listRooms = list },
                Message = message,
                Status = 200 
            });
        }
        [HttpPost]
        public async Task<IActionResult> AddRoom([FromBody] RoomDTO dto)
        {
            //var building = _context.Buildings.FirstOrDefault(x => x.Id == dto.BuildingId);
            //var type = _context.RoomTypes.FirstOrDefault(x => x.Id == dto.TypeId);

            //if (building != null)
            //    room.Building = building;
            //else
            //    return NotFound("Building does not exist");
            //if (type != null)
            //    room.Type = type;
            //else
            //    return NotFound("Room type does not exist");
            Room room = new Room
            {
                Id = dto.Id,
                Name = dto.Name,
                CurrentPeople = dto.CurrentPeople,
                IsAvailble = dto.IsAvailble,
                TypeId = dto.TypeId,
                BuildingId = dto.BuildingId,
                Floor = dto.Floor
            };

            (Room r, string message) = await _roomService.AddRoom(room);
            if (r == null) return BadRequest(message);

            return Ok(new ResponseBaseModel()
            {
                Data = r,
                Message = message,
                Status = 200
            });
        }

    }
}
