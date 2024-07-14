namespace ApiBookingApplication.Service.Account
{
    public class AccountRegisterRequest
    {
        public string? email { get; set; }
        public string? password { get; set; }
        public string? StudentID { get; set; }
        public DateTime? Dob { get; set; }
        public string? Phone { get; set; }
        public bool Gender { get; set; }
    }
}
