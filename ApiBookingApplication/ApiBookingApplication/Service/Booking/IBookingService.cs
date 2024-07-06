using ApiBookingApplication.Model;
using ApiBookingApplication.Service.Booking;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;

namespace ApiBookingApplication.Service.Account
{
    public interface IBookingService
    {
        Task<(string errorMessage, string id, string BookingID)> Add(BookingRequest bookingRequest);
        Task<(string errorMessage, string? UserIDReq, string? UserIDTarget)> SwapReq(SwapBookingRequest bookingRequest);
        Task<(string errorMessage, int? UserIDReq, int? UserIDTarget)> Swap(SwapBooking bookingRequest);
    }

    public class BookingService : IBookingService
    {
        private readonly DormitoryBookingContext _context;
        private readonly IConfiguration _configuration;


        public BookingService(DormitoryBookingContext a,
            IConfiguration configuration)
        {
            _context = a;
            _configuration = configuration;
        }

        public async Task<(string errorMessage, string id, string BookingID)> Add(BookingRequest bookingRequest)
        {
            //De san cho roi nha, thich nha :))
            return ("", bookingRequest.ID, bookingRequest.roomID);
        }

        public async Task<(string errorMessage, string? UserIDReq, string? UserIDTarget)> SwapReq(SwapBookingRequest bookingRequest)
        {
            var userReq = _context.Users.FirstOrDefault(u => u.StudentCode == bookingRequest.UserIDReq);
            var userTarget = _context.Users.FirstOrDefault(u => u.StudentCode == bookingRequest.UserIDTarget);

            var newRequest = new IncomingRequest
            {
                FromId = userReq.Id,
                ReceiveId = userReq.Id,
                RequestDate = DateTime.Now
            };

            //Validate
            if (newRequest.FromId == newRequest.ReceiveId)
                return ("Wait what ? (Request)", "", "");
            if(newRequest.FromId == null || newRequest.ReceiveId == null)
                return ("Cant find anyone like that, sad (Request)", "", "");
            if (userReq.CurrentRoomId == -1 || userTarget.CurrentRoomId == -1)
                return ("Have no current room (Request)", null, null);

            _context.IncomingRequests.Add(newRequest);
            await _context.SaveChangesAsync();

            return ("", bookingRequest.UserIDReq, bookingRequest.UserIDTarget);
        }

        public async Task<(string errorMessage, int? UserIDReq, int? UserIDTarget)> Swap(SwapBooking bookingRequest)
        {
            var userReq = _context.Users.FirstOrDefault(u => u.Id == bookingRequest.UserIDReq);
            var userTarget = _context.Users.FirstOrDefault(u => u.Id == bookingRequest.UserIDTarget);

            if(userReq == userTarget)
                return ("Wait what ? (SwapID)", null, null);

            if (userReq == null || userTarget == null)
                return ("Invalid User ID (SwapID)", null, null);

            if (userReq.CurrentRoomId == -1 || userTarget.CurrentRoomId == -1)
                return ("Have no current room (SwapID)", null, null);


            if (userReq.StudentCode == userTarget.StudentCode)
            {
                //Swap
                var tempRoomId = userReq.CurrentRoomId;
                userReq.CurrentRoomId = userTarget.CurrentRoomId;
                userTarget.CurrentRoomId = tempRoomId;

                // Delete incoming requests related to userReq and userTarget
                var incomingRequestsUserReq = _context.IncomingRequests.Where(ir => ir.FromId == userReq.Id || ir.ReceiveId == userReq.Id).ToList();
                _context.IncomingRequests.RemoveRange(incomingRequestsUserReq);

                var incomingRequestsUserTarget = _context.IncomingRequests.Where(ir => ir.FromId == userTarget.Id || ir.ReceiveId == userTarget.Id).ToList();
                _context.IncomingRequests.RemoveRange(incomingRequestsUserTarget);

                _context.SaveChanges();

                return ("Room swapped successfully", userReq.Id, userTarget.Id);
            }

            return ("Student codes do not match", null, null);
        }
    }
}
