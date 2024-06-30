using ApiBookingApplication.Model;

namespace ApiBookingApplication.Service.Admin.CRUDRoom
{
    public interface IRoomService
    {
        Task<(List<Room> list, string message)> GetAllRooms();
        Task<(List<Room>, string message)> GetAvailableRooms();
        Task<(Room room, string message)> AddRoom(Room room);
        Task<(Room room, string message)> UpdateRoom();
        Task<string> RemoveRoom(int id);

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

        public async Task<(List<Room> list, string message)> GetAllRooms()
        {
            var list = await Task.Run(() => _context.Rooms.ToList());
            if (list.Any())
                return (list, $"Found {list.Count()} rooms");
            return (null, "No rooms were found");
        }

        public Task<(List<Room>, string message)> GetAvailableRooms()
        {
            throw new NotImplementedException();
        }

        public Task<string> RemoveRoom(int id)
        {
            throw new NotImplementedException();
        }

        public Task<(Room room, string message)> UpdateRoom()
        {
            throw new NotImplementedException();
        }
    }
}
