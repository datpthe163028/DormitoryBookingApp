using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace ApiBookingApplication.Common
{
    public class ResponseBaseModel 
    {
        public int Status { get; set; }
        public string? Message { get; set; }
        public Object? Data { get; set; }
    }
}
