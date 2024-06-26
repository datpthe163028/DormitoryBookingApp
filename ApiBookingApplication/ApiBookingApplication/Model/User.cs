using System;
using System.Collections.Generic;

namespace ApiBookingApplication.Model
{
    public partial class User
    {
        public User()
        {
            Bookings = new HashSet<Booking>();
            IncomingRequestFroms = new HashSet<IncomingRequest>();
            IncomingRequestReceives = new HashSet<IncomingRequest>();
            News = new HashSet<News>();
            SwapHistoryUserId1Navigations = new HashSet<SwapHistory>();
            SwapHistoryUserId2Navigations = new HashSet<SwapHistory>();
        }

        public int Id { get; set; }
        public string? Email { get; set; }
        public bool Active { get; set; }
        public string? Password { get; set; }
        public string? StudentCode { get; set; }
        public bool Gender { get; set; }
        public DateTime? DateOfBirth { get; set; }
        public string? PhoneNumber { get; set; }
        public int? CurrentRoomId { get; set; }
        public double? Balance { get; set; }
        public int? RoleId { get; set; }

        public virtual Room? CurrentRoom { get; set; }
        public virtual Role? Role { get; set; }
        public virtual ICollection<Booking> Bookings { get; set; }
        public virtual ICollection<IncomingRequest> IncomingRequestFroms { get; set; }
        public virtual ICollection<IncomingRequest> IncomingRequestReceives { get; set; }
        public virtual ICollection<News> News { get; set; }
        public virtual ICollection<SwapHistory> SwapHistoryUserId1Navigations { get; set; }
        public virtual ICollection<SwapHistory> SwapHistoryUserId2Navigations { get; set; }
    }
}
