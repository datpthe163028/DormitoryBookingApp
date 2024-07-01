using System;
using System.Collections.Generic;

namespace ApiBookingApplication.Model
{
    public partial class Building
    {
        public Building()
        {
            Rooms = new HashSet<Room>();
        }

        public int Id { get; set; }
        public string? Name { get; set; }

        public virtual ICollection<Room> Rooms { get; set; }
    }
}
