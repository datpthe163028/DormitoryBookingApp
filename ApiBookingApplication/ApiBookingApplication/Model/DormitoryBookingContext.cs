using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

namespace ApiBookingApplication.Model
{
    public partial class DormitoryBookingContext : DbContext
    {
        public DormitoryBookingContext()
        {
        }

        public DormitoryBookingContext(DbContextOptions<DormitoryBookingContext> options)
            : base(options)
        {
        }

        public virtual DbSet<Booking> Bookings { get; set; } = null!;
        public virtual DbSet<Building> Buildings { get; set; } = null!;
        public virtual DbSet<IncomingRequest> IncomingRequests { get; set; } = null!;
        public virtual DbSet<News> News { get; set; } = null!;
        public virtual DbSet<Role> Roles { get; set; } = null!;
        public virtual DbSet<Room> Rooms { get; set; } = null!;
        public virtual DbSet<RoomType> RoomTypes { get; set; } = null!;
        public virtual DbSet<Semester> Semesters { get; set; } = null!;
        public virtual DbSet<SwapHistory> SwapHistories { get; set; } = null!;
        public virtual DbSet<User> Users { get; set; } = null!;

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            var config = new ConfigurationBuilder()
                    .AddJsonFile("appsettings.json")
                    .Build();

            if (!optionsBuilder.IsConfigured)
            {
                optionsBuilder.UseSqlServer(config.GetConnectionString("value"));
            }
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Booking>(entity =>
            {
                entity.ToTable("Booking");

                entity.Property(e => e.Id).HasColumnName("id");

                entity.Property(e => e.RoomId).HasColumnName("roomId");

                entity.Property(e => e.SemesterId).HasColumnName("semesterId");

                entity.Property(e => e.UserId).HasColumnName("userId");

                entity.HasOne(d => d.Room)
                    .WithMany(p => p.Bookings)
                    .HasForeignKey(d => d.RoomId)
                    .HasConstraintName("FK__Booking__roomId__4AB81AF0");

                entity.HasOne(d => d.Semester)
                    .WithMany(p => p.Bookings)
                    .HasForeignKey(d => d.SemesterId)
                    .HasConstraintName("FK__Booking__semeste__4BAC3F29");

                entity.HasOne(d => d.User)
                    .WithMany(p => p.Bookings)
                    .HasForeignKey(d => d.UserId)
                    .HasConstraintName("FK__Booking__userId__4CA06362");
            });

            modelBuilder.Entity<Building>(entity =>
            {
                entity.ToTable("Building");

                entity.Property(e => e.Id).HasColumnName("id");

                entity.Property(e => e.Name)
                    .HasMaxLength(25)
                    .HasColumnName("name");
            });

            modelBuilder.Entity<IncomingRequest>(entity =>
            {
                entity.ToTable("IncomingRequest");

                entity.Property(e => e.Id).HasColumnName("id");

                entity.Property(e => e.FromId).HasColumnName("fromId");

                entity.Property(e => e.ReceiveId).HasColumnName("receiveId");

                entity.Property(e => e.RequestDate)
                    .HasColumnType("datetime")
                    .HasColumnName("requestDate");

                entity.HasOne(d => d.From)
                    .WithMany(p => p.IncomingRequestFroms)
                    .HasForeignKey(d => d.FromId)
                    .HasConstraintName("FK__IncomingR__fromI__4D94879B");

                entity.HasOne(d => d.Receive)
                    .WithMany(p => p.IncomingRequestReceives)
                    .HasForeignKey(d => d.ReceiveId)
                    .HasConstraintName("FK__IncomingR__recei__4E88ABD4");
            });

            modelBuilder.Entity<News>(entity =>
            {
                entity.Property(e => e.Id).HasColumnName("id");

                entity.Property(e => e.Content)
                    .HasColumnType("text")
                    .HasColumnName("content");

                entity.Property(e => e.DatePost)
                    .HasColumnType("datetime")
                    .HasColumnName("datePost");

                entity.Property(e => e.Header)
                    .HasMaxLength(250)
                    .HasColumnName("header");

                entity.Property(e => e.UserId).HasColumnName("userId");

                entity.HasOne(d => d.User)
                    .WithMany(p => p.News)
                    .HasForeignKey(d => d.UserId)
                    .HasConstraintName("FK__News__userId__4F7CD00D");
            });

            modelBuilder.Entity<Role>(entity =>
            {
                entity.ToTable("Role");

                entity.Property(e => e.Id).HasColumnName("id");

                entity.Property(e => e.Name).HasMaxLength(25);
            });

            modelBuilder.Entity<Room>(entity =>
            {
                entity.ToTable("Room");

                entity.Property(e => e.Id).HasColumnName("id");

                entity.Property(e => e.BuildingId).HasColumnName("buildingId");

                entity.Property(e => e.CurrentPeople).HasColumnName("currentPeople");

                entity.Property(e => e.Floor).HasColumnName("floor");

                entity.Property(e => e.IsAvailble).HasColumnName("isAvailble");

                entity.Property(e => e.Name)
                    .HasMaxLength(25)
                    .HasColumnName("name");

                entity.Property(e => e.TypeId).HasColumnName("typeId");

                entity.HasOne(d => d.Building)
                    .WithMany(p => p.Rooms)
                    .HasForeignKey(d => d.BuildingId)
                    .HasConstraintName("FK__Room__buildingId__5070F446");

                entity.HasOne(d => d.Type)
                    .WithMany(p => p.Rooms)
                    .HasForeignKey(d => d.TypeId)
                    .HasConstraintName("FK__Room__typeId__5165187F");
            });

            modelBuilder.Entity<RoomType>(entity =>
            {
                entity.ToTable("RoomType");

                entity.Property(e => e.Id).HasColumnName("id");

                entity.Property(e => e.Capacity).HasColumnName("capacity");

                entity.Property(e => e.Price).HasColumnName("price");
            });

            modelBuilder.Entity<Semester>(entity =>
            {
                entity.ToTable("Semester");

                entity.Property(e => e.Id).HasColumnName("id");

                entity.Property(e => e.DateEnd)
                    .HasColumnType("datetime")
                    .HasColumnName("dateEnd");

                entity.Property(e => e.DateStart)
                    .HasColumnType("datetime")
                    .HasColumnName("dateStart");

                entity.Property(e => e.Name)
                    .HasMaxLength(50)
                    .HasColumnName("name");
            });

            modelBuilder.Entity<SwapHistory>(entity =>
            {
                entity.ToTable("Swap_History");

                entity.Property(e => e.Id).HasColumnName("id");

                entity.Property(e => e.BookingId1).HasColumnName("bookingId1");

                entity.Property(e => e.BookingId2).HasColumnName("bookingId2");

                entity.Property(e => e.DateSwap)
                    .HasColumnType("datetime")
                    .HasColumnName("dateSwap");

                entity.Property(e => e.UserId1).HasColumnName("userId1");

                entity.Property(e => e.UserId2).HasColumnName("userId2");

                entity.HasOne(d => d.BookingId1Navigation)
                    .WithMany(p => p.SwapHistoryBookingId1Navigations)
                    .HasForeignKey(d => d.BookingId1)
                    .HasConstraintName("FK__Swap_Hist__booki__52593CB8");

                entity.HasOne(d => d.BookingId2Navigation)
                    .WithMany(p => p.SwapHistoryBookingId2Navigations)
                    .HasForeignKey(d => d.BookingId2)
                    .HasConstraintName("FK__Swap_Hist__booki__534D60F1");

                entity.HasOne(d => d.UserId1Navigation)
                    .WithMany(p => p.SwapHistoryUserId1Navigations)
                    .HasForeignKey(d => d.UserId1)
                    .HasConstraintName("FK__Swap_Hist__userI__5441852A");

                entity.HasOne(d => d.UserId2Navigation)
                    .WithMany(p => p.SwapHistoryUserId2Navigations)
                    .HasForeignKey(d => d.UserId2)
                    .HasConstraintName("FK__Swap_Hist__userI__5535A963");
            });

            modelBuilder.Entity<User>(entity =>
            {
                entity.ToTable("User");

                entity.Property(e => e.Id).HasColumnName("id");

                entity.Property(e => e.Balance).HasColumnName("balance");

                entity.Property(e => e.CurrentRoomId).HasColumnName("current_room_id");

                entity.Property(e => e.DateOfBirth)
                    .HasColumnType("date")
                    .HasColumnName("dateOfBirth");

                entity.Property(e => e.Email)
                    .HasMaxLength(100)
                    .HasColumnName("email");


                entity.Property(e => e.Password)
                    .HasMaxLength(100)
                    .HasColumnName("password");

                entity.Property(e => e.PhoneNumber)
                    .HasMaxLength(20)
                    .HasColumnName("phoneNumber");

                entity.Property(e => e.RoleId).HasColumnName("roleId");


                entity.HasOne(d => d.CurrentRoom)
                    .WithMany(p => p.Users)
                    .HasForeignKey(d => d.CurrentRoomId)
                    .HasConstraintName("FK__User__current_ro__5629CD9C");

                entity.HasOne(d => d.Role)
                    .WithMany(p => p.Users)
                    .HasForeignKey(d => d.RoleId)
                    .HasConstraintName("FK__User__roleId__6E01572D");
            });

            OnModelCreatingPartial(modelBuilder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
