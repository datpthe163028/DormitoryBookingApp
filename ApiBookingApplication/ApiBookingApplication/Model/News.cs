using System;
using System.Collections.Generic;

namespace ApiBookingApplication.Model
{
    public partial class News
    {
        public int Id { get; set; }
        public DateTime? DatePost { get; set; }
        public string? Header { get; set; }
        public string? Content { get; set; }
        public int? UserId { get; set; }

        public virtual User? User { get; set; }
    }
}
