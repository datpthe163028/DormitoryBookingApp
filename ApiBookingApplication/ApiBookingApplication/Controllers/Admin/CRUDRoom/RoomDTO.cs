namespace ApiBookingApplication.Controllers.Admin.CRUDRoom
{
    public class RoomDTO
    {
        public int Id { get; set; }
        public string? Name { get; set; }
        public int? CurrentPeople { get; set; }
        public bool? IsAvailble { get; set; }
        public int? TypeId { get; set; }
        public int? BuildingId { get; set; }
        public int? Floor { get; set; }
    }
}
