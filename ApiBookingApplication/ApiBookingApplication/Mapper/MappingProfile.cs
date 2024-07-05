using ApiBookingApplication.DTOs;
using ApiBookingApplication.Model;
using AutoMapper;

namespace ApiBookingApplication.Mapper
{
    public class MappingProfile : Profile
    {
        public MappingProfile() {
            CreateMap<Booking, BookingHistoryDTO>()
                    .ForMember(dest => dest.RoomName, opt => opt.MapFrom(src => src.Room.Name))
                    .ForMember(dest => dest.UserName, opt => opt.MapFrom(src => src.User.Email)) // Assuming there's a Name property in User entity
                    .ForMember(dest => dest.SemesterName, opt => opt.MapFrom(src => src.Semester.Name))
                    .ForMember(dest => dest.RoomTypeId, opt => opt.MapFrom(src => src.Room.TypeId))
                    .ForMember(dest => dest.Capacity, opt => opt.MapFrom(src => src.Room.Type.Capacity))
                    .ForMember(dest => dest.Price, opt => opt.MapFrom(src => src.Room.Type.Price))
                    .ForMember(dest => dest.ImageUrl, opt => opt.MapFrom(src => src.Room.Type.ImageUrl))
                    .ForMember(dest => dest.SemesterStartDate, opt => opt.MapFrom(src => src.Semester.DateStart))
                    .ForMember(dest => dest.SemesterEndDate, opt => opt.MapFrom(src => src.Semester.DateEnd))
                    .ForMember(dest => dest.BuildingName, opt => opt.MapFrom(src => src.Room.Building.Name));
            CreateMap<News, NewsDTO>().ReverseMap();
        }
    }
}
