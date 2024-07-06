namespace ApiBookingApplication.DTOs
{
    public class NewsDTO
    {
        public int Id { get; set; }
        public DateTime? DatePost { get; set; }
        public string? Header { get; set; }
        public string? Content { get; set; }
        public int? UserId { get; set; }
    }
}
