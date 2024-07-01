using ApiBookingApplication.Model;
using CloudinaryDotNet;
using CloudinaryDotNet.Actions;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Options;
using static System.Net.Mime.MediaTypeNames;

namespace ApiBookingApplication.Controllers
{
    public class CloudinarySettings
    {
        public string CloudName { get; set; }
        public string ApiKey { get; set; }
        public string ApiSecret { get; set; }
    }
    public class TypeRoomPostDto
    {
        public int? Capacity { get; set; }
        public double? Price { get; set; }
        public IFormFile? ImageUrl { get; set; }
    }
    public class TypeRoomEditDto
    {
        public int? Capacity { get; set; }
        public double? Price { get; set; }
        public IFormFile? ImageUrl { get; set; }
    }

    [Route("api/[controller]")]
    [ApiController]
    public class TypeRoomController : ControllerBase
    {
        private readonly DormitoryBookingContext _bookingContext;
        private readonly Cloudinary _cloudinary;
        public TypeRoomController(DormitoryBookingContext dormitoryBookingContext, IOptions<CloudinarySettings> config) {
            _bookingContext = dormitoryBookingContext;
            var account = new Account(
                config.Value.CloudName,
                config.Value.ApiKey,
                config.Value.ApiSecret);

            _cloudinary = new Cloudinary(account);
        }

        [HttpGet]
        public IActionResult GetList()
        {
            return Ok(_bookingContext.RoomTypes.ToList());
        }
        [HttpGet("{id}")]
        public async Task<ActionResult> GetList(int id)
        {
            return Ok(await _bookingContext.RoomTypes.FirstOrDefaultAsync(x=>x.Id==id));
        }
        [HttpDelete("{id}")]
        public async Task<ActionResult> DeleteTypeRoom(int id)
        {
            var r = _bookingContext.RoomTypes.FirstOrDefault(x => x.Id == id);
            if (r != null)
            {
                List<Room> rooms = await _bookingContext.Rooms.Where(x => x.TypeId == id).ToListAsync();
                if (rooms.Count > 0)
                {
                    foreach (var room in rooms)
                    {
                        List<Booking> bookings = await _bookingContext.Bookings.Where(x => x.RoomId == room.Id).ToListAsync();
                        foreach (var booking in bookings)
                        {
                            List<SwapHistory> swaps = await _bookingContext.SwapHistories.Where(x => x.BookingId1 == booking.Id || x.BookingId2 == booking.Id).ToListAsync();
                            _bookingContext.SwapHistories.RemoveRange(swaps);
                        }
                        _bookingContext.Bookings.RemoveRange(bookings);
                    }
                    _bookingContext.Rooms.RemoveRange(rooms);
                }
                try
                {
                    _bookingContext.RoomTypes.Remove(r);
                    await _bookingContext.SaveChangesAsync();
                    return Ok(r);
                }
                catch(Exception ex)
                {
                    return BadRequest(ex.Message);
                }
                
            }
            else
            {
                return BadRequest("Room type not exists");
            }
            
            
        }
        [HttpPut("{id}")]
        public async Task<ActionResult> EditRoomType(int id, [FromForm] TypeRoomEditDto editDto)
        {
            var existingRoomType = await _bookingContext.RoomTypes.FindAsync(id);

            if (existingRoomType == null)
            {
                return NotFound("RoomType not found.");
            }

            if (editDto.ImageUrl != null && editDto.ImageUrl.Length > 0)
            {
                var uploadResult = new ImageUploadResult();
                await using var stream = editDto.ImageUrl.OpenReadStream();
                var uploadParams = new ImageUploadParams
                {
                    File = new FileDescription(editDto.ImageUrl.FileName, stream),
                    Folder = "TestPRM"
                };
                uploadResult = _cloudinary.Upload(uploadParams);

                if (uploadResult.Error != null)
                {
                    return BadRequest(uploadResult.Error.Message);
                }

                existingRoomType.ImageUrl = uploadResult.SecureUrl.ToString();
            }

            if (editDto.Capacity.HasValue)
            {
                existingRoomType.Capacity = editDto.Capacity.Value;
            }

            if (editDto.Price.HasValue)
            {
                existingRoomType.Price = editDto.Price.Value;
            }

            _bookingContext.Entry(existingRoomType).State = EntityState.Modified;
            await _bookingContext.SaveChangesAsync();

            return Ok(existingRoomType);
        }

        [HttpPost]
        public async Task<ActionResult> PostRoomType([FromForm] TypeRoomPostDto post)
        {
            var r = post.ImageUrl;
            if (r == null || r.Length == 0)
                return BadRequest("No file uploaded.");

            var uploadResult = new ImageUploadResult();

            if (r.Length > 0)
            {
                await using var stream = r.OpenReadStream();
                var uploadParams = new ImageUploadParams
                {
                    File = new FileDescription(r.FileName, stream),
                    Folder = "TestPRM"
                };
                uploadResult = _cloudinary.Upload(uploadParams);
            }

            if (uploadResult.Error != null)
                return BadRequest(uploadResult.Error.Message);

            var imageUrl = uploadResult.SecureUrl.ToString();

            var type = new RoomType
            {
                Capacity = post.Capacity,
                ImageUrl = imageUrl,
                Price = post.Price,
            };

            await _bookingContext.RoomTypes.AddAsync(type);
            await _bookingContext.SaveChangesAsync();

            return Ok(new { imageUrl });
        }


    }
}
