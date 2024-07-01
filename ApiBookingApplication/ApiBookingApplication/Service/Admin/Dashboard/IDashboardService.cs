using ApiBookingApplication.Model;
using ApiBookingApplication.Service.Account;

namespace ApiBookingApplication.Service.Admin.Dashboard
{
    public interface IDashboardService
    {
        Task<(int totalUsers, int totalRooms, int totalAvailableRooms)> GetDashboard();
    }
    public class DashboardService : IDashboardService
    {
        private readonly DormitoryBookingContext _context;
        private readonly IConfiguration _configuration;

        public DashboardService(DormitoryBookingContext a,
            IConfiguration configuration)
        {
            _context = a;
            _configuration = configuration;
        }

        public async Task<(int totalUsers, int totalRooms, int totalAvailableRooms)> GetDashboard()
        {
            var users = await Task.Run(() => _context.Users.Count(u => u.RoleId == 2));
            var rooms = await Task.Run(() => _context.Rooms.Count());
            var availableRooms = await Task.Run(() => _context.Rooms.Count(r => r.IsAvailble == true));

            return (users, rooms, availableRooms);
        }
    }
}
