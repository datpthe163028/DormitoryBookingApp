using ApiBookingApplication.Model;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Net.Mail;
using System.Net;
using System.Security.Claims;
using System.Text;

namespace ApiBookingApplication.Service.Account
{
    public interface IAccountService
    {
        Task<(string accessToken, string errorMessage, string role, string userId)> AuthAsync(AccountLoginRequest accountAuthRequest);
        Task<(string errorMessage, string email, string pwd)> Register_Client(AccountRegisterRequest accountAuthRequest);
        Task<(string errorMessage, string Email, string otpSend)> OTP(OTPRequest OTPreq);
    }

    public class AccountService : IAccountService
    {
        private readonly DormitoryBookingContext _context;
        private readonly IConfiguration _configuration;


        public AccountService(DormitoryBookingContext a,
            IConfiguration configuration)
        {
            _context = a;
            _configuration = configuration;
        }

        public async Task<(string accessToken, string errorMessage, string role, string userId)> AuthAsync(AccountLoginRequest accountAuthRequest)
        {
            string accessToken = "";
            var ac = _context.Users.Include(s => s.Role).FirstOrDefault(s => s.Email == accountAuthRequest.email && s.Password == accountAuthRequest.password);
            if (ac is null)
            {
                return ("", "Account not found", "", "");
            }

            if (ac.Active is false)
            {
                return ("", "Account doesn't active", "", "");
            }

            return (await GenerateJwtTokenTw(ac), "", ac.Role.Name, ac.Id + "");

        }

        private async Task<string> GenerateJwtTokenTw(User account)
        {
            var secretKey = _configuration["JwtSettings:SecretKey"];
            var issuer = _configuration["JwtSettings:Issuer"];

            List<Claim> claims = new()
            {
                new Claim(ClaimTypes.NameIdentifier, account.Id + ""),
                new Claim(ClaimTypes.Role, account.Role.Name + "")
            };

            var key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(secretKey));
            var creds = new SigningCredentials(key, SecurityAlgorithms.HmacSha256);

            var jwtSecurityToken = new JwtSecurityToken(
                issuer: issuer,
                audience: issuer,
                claims: claims,
                expires: DateTime.Now.AddDays(30),
                signingCredentials: creds
            );
            return new JwtSecurityTokenHandler().WriteToken(jwtSecurityToken);
        }


        public async Task<(string errorMessage, string email, string pwd)> Register_Client(AccountRegisterRequest accountAuthRequest)
        {
            var existingUser = _context.Users.FirstOrDefault(s => s.Email == accountAuthRequest.email);
            if (existingUser != null)
            {
                return ("Email already registered", "", "");
            }

            var newUser = new User
            {
                Email = accountAuthRequest.email,
                Password = accountAuthRequest.password,
                //DateOfBirth = accountAuthRequest.Dob,
                PhoneNumber = accountAuthRequest.Phone,
                StudentCode = accountAuthRequest.StudentID,
                Gender = accountAuthRequest.Gender,
                Active = true,
                Balance = 0,
                RoleId = 2 // Assuming role id 1 is for regular users
            };

            _context.Users.Add(newUser);
            await _context.SaveChangesAsync();

            return ("", newUser.Email, newUser.Password);
        }

        public async Task<(string errorMessage, string Email, string otpSend)> OTP(OTPRequest OTPreq)
        {
            try
            {
                // Send OTP via email
                var smtpClient = new SmtpClient("smtp.gmail.com", 587)
                {
                    EnableSsl = true,
                    Credentials = new NetworkCredential("dummydamailforcode@gmail.com", "navieezteekczhay"),
                };
                var mailMessage = new MailMessage
                {
                    From = new MailAddress("dummydamailforcode@gmail.com"),
                    Subject = "OTP Dorm",
                    Body = "Your code for verification is: " + OTPreq.otp,
                    IsBodyHtml = false,
                };
                mailMessage.To.Add(OTPreq.email);
                await smtpClient.SendMailAsync(mailMessage);

                return ("", OTPreq.email, OTPreq.otp);
            }
            catch (Exception ex)
            {
                // Handle exceptions
                return (ex.Message, "", "");
            }
        }
    }
}
