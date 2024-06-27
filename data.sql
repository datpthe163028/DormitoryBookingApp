USE [master]
GO
/****** Object:  Database [DormitoryBooking]    Script Date: 6/26/2024 3:05:32 PM ******/
CREATE DATABASE [DormitoryBooking]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'DormitoryBooking', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\DormitoryBooking.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'DormitoryBooking_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\DormitoryBooking_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [DormitoryBooking] SET COMPATIBILITY_LEVEL = 150
GO
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
/****** Object:  Table [dbo].[Booking]    Script Date: 6/26/2024 3:05:33 PM ******/
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
/****** Object:  Table [dbo].[Building]    Script Date: 6/26/2024 3:05:33 PM ******/
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
/****** Object:  Table [dbo].[IncomingRequest]    Script Date: 6/26/2024 3:05:33 PM ******/
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
/****** Object:  Table [dbo].[News]    Script Date: 6/26/2024 3:05:33 PM ******/
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
/****** Object:  Table [dbo].[Role]    Script Date: 6/26/2024 3:05:33 PM ******/
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
/****** Object:  Table [dbo].[Room]    Script Date: 6/26/2024 3:05:33 PM ******/
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
/****** Object:  Table [dbo].[RoomType]    Script Date: 6/26/2024 3:05:33 PM ******/
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
/****** Object:  Table [dbo].[Semester]    Script Date: 6/26/2024 3:05:33 PM ******/
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
/****** Object:  Table [dbo].[Swap_History]    Script Date: 6/26/2024 3:05:33 PM ******/
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
/****** Object:  Table [dbo].[User]    Script Date: 6/26/2024 3:05:33 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[User](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[email] [nvarchar](100) NULL,
	[password] [nvarchar](100) NULL,
	[dateOfBirth] [date] NULL,
	[phoneNumber] [nvarchar](20) NULL,
	[current_room_id] [int] NULL,
	[balance] [float] NULL,
	[roleId] [int] NULL,
	[active] [bit] NULL,
	[studentCode] [nvarchar](20) NULL,
	[gender] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Building] ON 

INSERT [dbo].[Building] ([id], [name]) VALUES (1, N'DomA')
INSERT [dbo].[Building] ([id], [name]) VALUES (2, N'DomB')
INSERT [dbo].[Building] ([id], [name]) VALUES (3, N'DomC')
SET IDENTITY_INSERT [dbo].[Building] OFF
GO
SET IDENTITY_INSERT [dbo].[Role] ON 

INSERT [dbo].[Role] ([id], [Name]) VALUES (1, N'Admin')
INSERT [dbo].[Role] ([id], [Name]) VALUES (2, N'Customer')
SET IDENTITY_INSERT [dbo].[Role] OFF
GO
SET IDENTITY_INSERT [dbo].[Room] ON 

INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (1, N'101', 0, 1, 4, 1, 1)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (2, N'102', 0, 1, 2, 1, 1)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (3, N'103', 0, 1, 2, 1, 1)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (4, N'104', 0, 1, 2, 1, 1)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (5, N'105', 0, 1, 3, 1, 1)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (6, N'106', 0, 1, 3, 1, 1)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (7, N'107', 0, 1, 3, 1, 1)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (8, N'108', 0, 1, 3, 1, 1)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (9, N'109', 0, 1, 3, 1, 1)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (10, N'110', 0, 1, 3, 1, 1)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (11, N'111', 0, 1, 4, 1, 1)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (12, N'112', 0, 1, 4, 1, 1)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (13, N'113', 0, 1, 4, 1, 1)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (14, N'114', 0, 1, 4, 1, 1)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (15, N'201', 0, 1, 4, 1, 2)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (16, N'202', 0, 1, 4, 1, 2)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (17, N'203', 0, 1, 4, 1, 2)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (18, N'204', 0, 1, 1, 1, 2)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (19, N'205', 0, 1, 1, 1, 2)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (20, N'206', 0, 1, 1, 1, 2)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (21, N'207', 0, 1, 1, 1, 2)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (22, N'208', 0, 1, 1, 1, 2)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (23, N'209', 0, 1, 1, 1, 2)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (24, N'210', 0, 1, 1, 1, 2)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (25, N'211', 0, 1, 1, 1, 2)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (26, N'212', 0, 1, 1, 1, 2)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (27, N'301', 0, 1, 1, 1, 3)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (28, N'302', 0, 1, 1, 1, 3)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (29, N'303', 0, 1, 1, 1, 3)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (30, N'304', 0, 1, 2, 1, 3)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (31, N'305', 0, 1, 2, 1, 3)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (32, N'306', 0, 1, 2, 1, 3)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (33, N'307', 0, 1, 2, 1, 3)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (34, N'308', 0, 1, 2, 1, 3)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (35, N'309', 0, 1, 2, 1, 3)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (36, N'310', 0, 1, 2, 1, 3)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (37, N'101', 0, 1, 2, 2, 1)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (38, N'102', 0, 1, 1, 2, 1)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (39, N'103', 0, 1, 2, 2, 1)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (40, N'104', 0, 1, 2, 2, 1)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (41, N'105', 0, 1, 1, 2, 1)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (42, N'106', 0, 1, 1, 2, 1)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (43, N'107', 0, 1, 1, 2, 1)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (44, N'108', 0, 1, 3, 2, 1)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (45, N'201', 0, 1, 3, 2, 2)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (46, N'202', 0, 1, 3, 2, 2)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (47, N'203', 0, 1, 2, 2, 2)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (48, N'204', 0, 1, 2, 2, 2)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (49, N'205', 0, 1, 2, 2, 2)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (50, N'301', 0, 1, 2, 2, 3)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (51, N'302', 0, 1, 2, 2, 3)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (52, N'303', 0, 1, 2, 2, 3)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (53, N'304', 0, 1, 1, 2, 3)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (54, N'305', 0, 1, 1, 2, 3)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (55, N'401', 0, 1, 1, 2, 4)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (56, N'402', 0, 1, 1, 2, 4)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (57, N'403', 0, 1, 4, 2, 4)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (58, N'404', 0, 1, 4, 2, 4)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (59, N'405', 0, 1, 4, 2, 4)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (60, N'103', 0, 1, 1, 3, 1)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (61, N'102', 0, 1, 1, 3, 1)
INSERT [dbo].[Room] ([id], [name], [currentPeople], [isAvailble], [typeId], [buildingId], [floor]) VALUES (62, N'101', 0, 1, 2, 3, 1)
SET IDENTITY_INSERT [dbo].[Room] OFF
GO
SET IDENTITY_INSERT [dbo].[RoomType] ON 

INSERT [dbo].[RoomType] ([id], [capacity], [price]) VALUES (1, 2, 1000000)
INSERT [dbo].[RoomType] ([id], [capacity], [price]) VALUES (2, 4, 700000)
INSERT [dbo].[RoomType] ([id], [capacity], [price]) VALUES (3, 6, 500000)
INSERT [dbo].[RoomType] ([id], [capacity], [price]) VALUES (4, 1, 1500000)
SET IDENTITY_INSERT [dbo].[RoomType] OFF
GO
SET IDENTITY_INSERT [dbo].[User] ON 

INSERT [dbo].[User] ([id], [email], [password], [dateOfBirth], [phoneNumber], [current_room_id], [balance], [roleId], [active], [studentCode], [gender]) VALUES (5, N'dat@gmail.com', N'dat123456', CAST(N'2024-06-25' AS Date), N'0947048768', NULL, 5000000, 2, 1, N'He163029', 1)
INSERT [dbo].[User] ([id], [email], [password], [dateOfBirth], [phoneNumber], [current_room_id], [balance], [roleId], [active], [studentCode], [gender]) VALUES (6, N'admin@gmail.com', N'123456', CAST(N'2024-06-25' AS Date), N'0947048768', NULL, 1, 1, 1, NULL, 1)
INSERT [dbo].[User] ([id], [email], [password], [dateOfBirth], [phoneNumber], [current_room_id], [balance], [roleId], [active], [studentCode], [gender]) VALUES (7, N'dat2@gmail.com', N'dat123456', CAST(N'2024-06-25' AS Date), N'0947048768', NULL, 1, 2, 0, N'He163022', 0)
SET IDENTITY_INSERT [dbo].[User] OFF
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
ALTER TABLE [dbo].[User]  WITH CHECK ADD FOREIGN KEY([roleId])
REFERENCES [dbo].[Role] ([id])
GO
USE [master]
GO
ALTER DATABASE [DormitoryBooking] SET  READ_WRITE 
GO
