using System;
using System.Collections.Generic;

namespace ApiBookingApplication.Model
{
    public partial class SwapHistory
    {
        public int Id { get; set; }
        public int? UserId1 { get; set; }
        public int? UserId2 { get; set; }
        public int? BookingId1 { get; set; }
        public int? BookingId2 { get; set; }
        public DateTime? DateSwap { get; set; }

        public virtual Booking? BookingId1Navigation { get; set; }
        public virtual Booking? BookingId2Navigation { get; set; }
        public virtual User? UserId1Navigation { get; set; }
        public virtual User? UserId2Navigation { get; set; }
    }
}
