
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [DormitoryBooking].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [DormitoryBooking] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [DormitoryBooking] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [DormitoryBooking] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [DormitoryBooking] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [DormitoryBooking] SET ARITHABORT OFF 
GO
ALTER DATABASE [DormitoryBooking] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [DormitoryBooking] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [DormitoryBooking] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [DormitoryBooking] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [DormitoryBooking] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [DormitoryBooking] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [DormitoryBooking] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [DormitoryBooking] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [DormitoryBooking] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [DormitoryBooking] SET  ENABLE_BROKER 
GO
ALTER DATABASE [DormitoryBooking] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [DormitoryBooking] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [DormitoryBooking] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [DormitoryBooking] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [DormitoryBooking] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [DormitoryBooking] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [DormitoryBooking] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [DormitoryBooking] SET RECOVERY FULL 
GO
ALTER DATABASE [DormitoryBooking] SET  MULTI_USER 
GO
ALTER DATABASE [DormitoryBooking] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [DormitoryBooking] SET DB_CHAINING OFF 
GO
ALTER DATABASE [DormitoryBooking] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [DormitoryBooking] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [DormitoryBooking] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [DormitoryBooking] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'DormitoryBooking', N'ON'
GO
ALTER DATABASE [DormitoryBooking] SET QUERY_STORE = OFF
GO
USE [DormitoryBooking]
GO
/****** Object:  Table [dbo].[Booking]    Script Date: 6/22/2024 8:13:55 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Booking](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[roomId] [int] NULL,
	[userId] [int] NULL,
	[semesterId] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Building]    Script Date: 6/22/2024 8:13:55 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Building](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](25) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[IncomingRequest]    Script Date: 6/22/2024 8:13:55 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[IncomingRequest](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[fromId] [int] NULL,
	[receiveId] [int] NULL,
	[requestDate] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[News]    Script Date: 6/22/2024 8:13:55 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[News](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[datePost] [datetime] NULL,
	[header] [nvarchar](250) NULL,
	[content] [text] NULL,
	[userId] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Role]    Script Date: 6/22/2024 8:13:55 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Role](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](25) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Room]    Script Date: 6/22/2024 8:13:55 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Room](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](25) NULL,
	[currentPeople] [int] NULL,
	[isAvailble] [bit] NULL,
	[typeId] [int] NULL,
	[buildingId] [int] NULL,
	[floor] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[RoomType]    Script Date: 6/22/2024 8:13:55 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[RoomType](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[capacity] [int] NULL,
	[price] [float] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Semester]    Script Date: 6/22/2024 8:13:55 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Semester](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](50) NULL,
	[dateStart] [datetime] NULL,
	[dateEnd] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Swap_History]    Script Date: 6/22/2024 8:13:55 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Swap_History](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[userId1] [int] NULL,
	[userId2] [int] NULL,
	[bookingId1] [int] NULL,
	[bookingId2] [int] NULL,
	[dateSwap] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[User]    Script Date: 6/22/2024 8:13:55 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[User](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[email] [nvarchar](100) NULL,
	[password] [nvarchar](100) NULL,
	[studentCode] [nvarchar](1) NULL,
	[gender] [nvarchar](1) NULL,
	[dateOfBirth] [date] NULL,
	[phoneNumber] [nvarchar](20) NULL,
	[current_room_id] [int] NULL,
	[balance] [float] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[UserRole]    Script Date: 6/22/2024 8:13:55 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UserRole](
	[roleId] [int] NOT NULL,
	[userId] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[roleId] ASC,
	[userId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Booking]  WITH CHECK ADD FOREIGN KEY([roomId])
REFERENCES [dbo].[Room] ([id])
GO
ALTER TABLE [dbo].[Booking]  WITH CHECK ADD FOREIGN KEY([semesterId])
REFERENCES [dbo].[Semester] ([id])
GO
ALTER TABLE [dbo].[Booking]  WITH CHECK ADD FOREIGN KEY([userId])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[IncomingRequest]  WITH CHECK ADD FOREIGN KEY([fromId])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[IncomingRequest]  WITH CHECK ADD FOREIGN KEY([receiveId])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[News]  WITH CHECK ADD FOREIGN KEY([userId])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[Room]  WITH CHECK ADD FOREIGN KEY([buildingId])
REFERENCES [dbo].[Building] ([id])
GO
ALTER TABLE [dbo].[Room]  WITH CHECK ADD FOREIGN KEY([typeId])
REFERENCES [dbo].[RoomType] ([id])
GO
ALTER TABLE [dbo].[Swap_History]  WITH CHECK ADD FOREIGN KEY([bookingId1])
REFERENCES [dbo].[Booking] ([id])
GO
ALTER TABLE [dbo].[Swap_History]  WITH CHECK ADD FOREIGN KEY([bookingId2])
REFERENCES [dbo].[Booking] ([id])
GO
ALTER TABLE [dbo].[Swap_History]  WITH CHECK ADD FOREIGN KEY([userId1])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[Swap_History]  WITH CHECK ADD FOREIGN KEY([userId2])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[User]  WITH CHECK ADD FOREIGN KEY([current_room_id])
REFERENCES [dbo].[Room] ([id])
GO
ALTER TABLE [dbo].[UserRole]  WITH CHECK ADD FOREIGN KEY([roleId])
REFERENCES [dbo].[Role] ([id])
GO
ALTER TABLE [dbo].[UserRole]  WITH CHECK ADD FOREIGN KEY([userId])
REFERENCES [dbo].[User] ([id])
GO
USE [master]
GO
ALTER DATABASE [DormitoryBooking] SET  READ_WRITE 
GO
