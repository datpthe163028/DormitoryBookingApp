using ApiBookingApplication.Common;
using ApiBookingApplication.Model;
using ApiBookingApplication.Service.Admin.CRUDRoom;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System.Drawing;
using System.Xml.Linq;
using System;

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
            (List<Room> listRooms, string message) = await _roomService.GetAllRooms();
            List<RoomDTO> listDtos = new List<RoomDTO>();
            if (listRooms.Any())
            {
                foreach (Room room in listRooms)
                {
                    listDtos.Add(new RoomDTO
                    {
                        Id = room.Id,
                        Name = room.Name,
                        CurrentPeople = room.CurrentPeople,
                        IsAvailble = room.IsAvailble,
                        TypeId = room.TypeId,
                        BuildingId = room.BuildingId,
                        Floor = room.Floor
                    });
                }
            }

            return Ok(new ResponseBaseModel() 
            { 
                Data = new { listDtos },
                Message = message,
                Status = 200 
            });
        }
        [HttpGet("{id}")]
        public async Task<IActionResult> GetRoomById([FromRoute] int id)
        {
            Room r = await _roomService.GetRoomById(id);

            return Ok(new ResponseBaseModel()
            {
                Data = new RoomDTO
                {
                    Id = r.Id,
                    Name = r.Name,
                    CurrentPeople = r.CurrentPeople,
                    IsAvailble = r.IsAvailble,
                    TypeId = r.TypeId,
                    BuildingId = r.BuildingId,
                    Floor = r.Floor
                },
                Message = "",
                Status = 200
            });
        }
        [HttpGet("AllBuildings")]
        public async Task<IActionResult> GetAllBuildings()
        {
            (List<Building> list, string message) = await _roomService.GetAllBuildings();
            return Ok(new ResponseBaseModel()
            {
                Data = new { buildings = list },
                Message = message,
                Status = 200
            });
        }
        [HttpGet("AllRoomTypes")]
        public async Task<IActionResult> GetAllRoomTypes()
        {
            (List<RoomType> list, string message) = await _roomService.GetAllRoomTypes();
            return Ok(new ResponseBaseModel()
            {
                Data = new { roomtypes = list },
                Message = message,
                Status = 200
            });
        }
        [HttpPost]
        public async Task<IActionResult> AddRoom([FromBody] RoomDTO dto)
        {
            var building = await _roomService.GetBuildingById(dto.BuildingId ?? 0);
            var type = await _roomService.GetRoomTypeById(dto.TypeId ?? 0);

            if (building == null) return NotFound("Building does not exist");
            if (type == null) return NotFound("Room type does not exist");
            Room room = new Room
            {
                Name = dto.Name,
                CurrentPeople = dto.CurrentPeople,
                IsAvailble = dto.IsAvailble,
                TypeId = dto.TypeId,
                BuildingId = dto.BuildingId,
                Floor = dto.Floor,
                Building = building,
                Type = type
            };

            (Room r, string message) = await _roomService.AddRoom(room);
            if (r == null) return BadRequest(message);

            return Ok(new ResponseBaseModel()
            {
                Data = new RoomDTO
                {
                    Id = r.Id,
                    Name = r.Name,
                    CurrentPeople = r.CurrentPeople,
                    IsAvailble = r.IsAvailble,
                    TypeId = r.TypeId,
                    BuildingId = r.BuildingId,
                    Floor = r.Floor
                },
                Message = message,
                Status = 200
            });
        }
        [HttpPut]
        public async Task<IActionResult> UpdateRoom([FromBody] RoomDTO dto)
        {
            var room = await _roomService.GetRoomById(dto.Id ?? 0);
            if (room == null) return NotFound($"Room with id {dto.Id} does not exist");

            var building = await _roomService.GetBuildingById(dto.BuildingId ?? 0);
            if (building == null) return NotFound("Building does not exist");
            var type = await _roomService.GetRoomTypeById(dto.TypeId ?? 0);
            if (type == null) return NotFound("Room type does not exist");
            
            room.Name = dto.Name;
            room.CurrentPeople = dto.CurrentPeople;
            room.IsAvailble = dto.IsAvailble;
            room.TypeId = dto.TypeId;
            room.BuildingId = dto.BuildingId;
            room.Floor = dto.Floor;
            room.Building = building;
            room.Type = type;

            (Room r, string message) = await _roomService.UpdateRoom(room);
            if (r == null) return BadRequest(message);

            return Ok(new ResponseBaseModel()
            {
                Data = new { room = r },
                Message = message,
                Status = 200
            });
        }

    }
}
