using System;
using System.Collections.Generic;

namespace ApiBookingApplication.Model
{
    public partial class IncomingRequest
    {
        public int Id { get; set; }
        public int? FromId { get; set; }
        public int? ReceiveId { get; set; }
        public DateTime? RequestDate { get; set; }

        public virtual User? From { get; set; }
        public virtual User? Receive { get; set; }
    }
}
