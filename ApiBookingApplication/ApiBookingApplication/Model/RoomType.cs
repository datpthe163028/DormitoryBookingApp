using System;
using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace ApiBookingApplication.Model
{
    public partial class RoomType
    {
        public RoomType()
        {
            Rooms = new HashSet<Room>();
        }

        public int Id { get; set; }
        public int? Capacity { get; set; }
        public double? Price { get; set; }
        public string? ImageUrl { get; set; }
        [JsonIgnore]
        public virtual ICollection<Room> Rooms { get; set; }
    }
}
