using ApiBookingApplication.Model;

namespace ApiBookingApplication.DTOs
{
    public class BookingHistoryDTO
    {
        public int Id { get; set; }
        public int? RoomId { get; set; }
        public string RoomName { get; set; } // Assuming you want to include room name in DTO
        public int? UserId { get; set; }
        public string UserName { get; set; } // Assuming you want to include user name in DTO
        public int? SemesterId { get; set; }
        public string SemesterName { get; set; } // Assuming you want to include semester name in DTO

        // Additional properties from RoomType, if needed
        public int? RoomTypeId { get; set; }
        public int? Capacity { get; set; }
        public double? Price { get; set; }
       public string? ImageUrl { get; set; }

        // Additional properties from Semester, if needed
        public DateTime? SemesterStartDate { get; set; }
        public DateTime? SemesterEndDate { get; set; }

        // Building information
        public string BuildingName { get; set; }
    }
}
