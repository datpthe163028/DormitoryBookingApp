namespace ApiBookingApplication.Service.Account
{
    public class UserDetailResponse
    {
        public int? id { get; set; }
        public string? StudentID { get; set; }
        public string? Phone { get; set; }
        public bool? Gender { get; set; }
        public int? currentRoomID { get; set; }
        public double? Balance { get; set; }
    }
}
