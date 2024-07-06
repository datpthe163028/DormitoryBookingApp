using System;
using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace ApiBookingApplication.Model
{
    public partial class Room
    {
        public Room()
        {
            Bookings = new HashSet<Booking>();
            Users = new HashSet<User>();
        }

        public int Id { get; set; }
        public string? Name { get; set; }
        public int? CurrentPeople { get; set; }
        public bool? IsAvailble { get; set; }
        public int? TypeId { get; set; }
        public int? BuildingId { get; set; }
        public int? Floor { get; set; }
        [JsonIgnore]
        public virtual Building? Building { get; set; }
        [JsonIgnore]
        public virtual RoomType? Type { get; set; }
        [JsonIgnore]
        public virtual ICollection<Booking> Bookings { get; set; }
        [JsonIgnore]
        public virtual ICollection<User> Users { get; set; }
    }
}
