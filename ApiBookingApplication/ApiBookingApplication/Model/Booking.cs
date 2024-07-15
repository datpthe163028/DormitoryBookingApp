using System;
using System.Collections.Generic;

namespace ApiBookingApplication.Model
{
    public partial class Booking
    {
        public Booking()
        {
            SwapHistoryBookingId1Navigations = new HashSet<SwapHistory>();
            SwapHistoryBookingId2Navigations = new HashSet<SwapHistory>();
        }

        public int Id { get; set; }
        public int? RoomId { get; set; }
        public int? UserId { get; set; }
        public int? SemesterId { get; set; }

        public virtual Room? Room { get; set; }
        public virtual Semester? Semester { get; set; }
        public virtual User? User { get; set; }
        public virtual ICollection<SwapHistory> SwapHistoryBookingId1Navigations { get; set; }
        public virtual ICollection<SwapHistory> SwapHistoryBookingId2Navigations { get; set; }
    }
}
