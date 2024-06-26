﻿using ApiBookingApplication.Model;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;

namespace ApiBookingApplication.Service.Account
{
    public interface IAccountService
    {
        Task<(string accessToken, string errorMessage, string role)> AuthAsync(AccountLoginRequest accountAuthRequest);
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

        public async Task<(string accessToken, string errorMessage, string role)> AuthAsync(AccountLoginRequest accountAuthRequest)
        {
            string accessToken = "";
            var ac = _context.Users.Include(s => s.Role).FirstOrDefault(s => s.Email == accountAuthRequest.email && s.Password == accountAuthRequest.password);
            if (ac is null)
            {
                return ("", "Account not found", "");
            }

            if (ac.Active is false)
            {
                return ("", "Account doesn't active", "");
            }

            return (await GenerateJwtTokenTw(ac), "", ac.Role.Name);

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
    }
}