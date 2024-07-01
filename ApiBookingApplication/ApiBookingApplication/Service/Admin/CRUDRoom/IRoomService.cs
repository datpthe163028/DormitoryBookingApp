using ApiBookingApplication.Model;

namespace ApiBookingApplication.Service.Admin.CRUDRoom
{
    public interface IRoomService
    {
        Task<(List<Room> list, string message)> GetAllRooms();
        Task<(List<Room> list, string message)> GetAvailableRooms();
        Task<(Room room, string message)> AddRoom(Room room);
        Task<(Room room, string message)> UpdateRoom(Room room);
        Task<string> RemoveRoom(int id);
        Task<(List<Building> list, string message)> GetAllBuildings();
        Task<(List<RoomType> list, string message)> GetAllRoomTypes();
        Task<Building> GetBuildingById(int buildingId);
        Task<RoomType> GetRoomTypeById(int typeId);
        Task<Room> GetRoomById(int id);
    }
    public class RoomService : IRoomService
    {
        private readonly DormitoryBookingContext _context;
        private readonly IConfiguration _configuration;

        public RoomService(DormitoryBookingContext a,
            IConfiguration configuration)
        {
            _context = a;
            _configuration = configuration;
        }

        public async Task<(Room room, string message)> AddRoom(Room room)
        {
            try
            {
                _context.Rooms.Add(room);
                await _context.SaveChangesAsync();
                return (room, "Room added successfully.");
            }
            catch (Exception ex)
            {
                return (null, $"Error adding room: {ex.Message}");
            }
        }

        public async Task<(List<Building> list, string message)> GetAllBuildings()
        {
            var list = await Task.Run(() => _context.Buildings.ToList());
            if (list.Any())
                return (list, $"Found {list.Count()} buildings");
            return (null, "No buildings were found");
        }

        public async Task<(List<Room> list, string message)> GetAllRooms()
        {
            var list = await Task.Run(() => _context.Rooms.ToList());
            if (list.Any())
                return (list, $"Found {list.Count()} rooms");
            return (null, "No rooms were found");
        }

        public async Task<(List<RoomType> list, string message)> GetAllRoomTypes()
        {
            var list = await Task.Run(() => _context.RoomTypes.ToList());
            if (list.Any())
                return (list, $"Found {list.Count()} room types");
            return (null, "No room types were found");
        }

        public Task<(List<Room> list, string message)> GetAvailableRooms()
        {
            throw new NotImplementedException();
        }

        public async Task<Building> GetBuildingById(int buildingId)
        {
            var building = await Task.Run(() => _context.Buildings.FirstOrDefault(b => b.Id == buildingId));
            return building;
        }

        public async Task<RoomType> GetRoomTypeById(int typeId)
        {
            var type = await Task.Run(() => _context.RoomTypes.FirstOrDefault(t => t.Id == typeId));
            return type;
        }
        public async Task<Room> GetRoomById(int id)
        {
            var room = await Task.Run(() => _context.Rooms.FirstOrDefault(r => r.Id == id));
            return room;
        }
        public Task<string> RemoveRoom(int id)
        {
            throw new NotImplementedException();
        }

        public async Task<(Room room, string message)> UpdateRoom(Room room)
        {
            try
            {
                _context.Rooms.Update(room);
                await _context.SaveChangesAsync();
                return (room, "Room updated successfully.");
            }
            catch (Exception ex)
            {
                return (null, $"Error updating room: {ex.Message}");
            }
        }
    }
}
