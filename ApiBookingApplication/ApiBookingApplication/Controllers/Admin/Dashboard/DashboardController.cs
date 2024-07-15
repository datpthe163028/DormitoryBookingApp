using ApiBookingApplication.Common;
using ApiBookingApplication.Service.Account;
using ApiBookingApplication.Service.Admin.Dashboard;
using Microsoft.AspNetCore.Mvc;

namespace ApiBookingApplication.Controllers.Admin.Dashboard
{
    [ApiController]
    [Route("api/[controller]/[action]")]
    public class DashboardController : ControllerBase
    {
        private readonly IDashboardService _dashboardService;
        
        public DashboardController(IDashboardService dashboardService)
        {
            _dashboardService = dashboardService;
        }
        [HttpGet]
        public async Task<IActionResult> GetDashboard()
        {
            (int totalUsers, int totalRooms, int totalAvailableRooms) = await _dashboardService.GetDashboard();
            
            return Ok(new ResponseBaseModel() { Data = new { users = totalUsers, rooms = totalRooms, availableRooms = totalAvailableRooms }, Message = "Success", Status = 200 });
        }

    }
}
