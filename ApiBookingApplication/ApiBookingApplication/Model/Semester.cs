using System;
using System.Collections.Generic;

namespace ApiBookingApplication.Model
{
    public partial class Semester
    {
        public Semester()
        {
            Bookings = new HashSet<Booking>();
        }

        public int Id { get; set; }
        public string? Name { get; set; }
        public DateTime? DateStart { get; set; }
        public DateTime? DateEnd { get; set; }

        public virtual ICollection<Booking> Bookings { get; set; }
    }
}
