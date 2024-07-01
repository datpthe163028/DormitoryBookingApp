using ApiBookingApplication.Common;
using ApiBookingApplication.Model;
using ApiBookingApplication.Service.Account;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System.Security.Principal;

namespace ApiBookingApplication.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AccountController : ControllerBase
    {
        private readonly IAccountService _accountService;

        public AccountController(IAccountService accountService)
        {
            _accountService = accountService;
        }

        [HttpPost("Auth")]
        public async Task<IActionResult> Login([FromBody] AccountLoginRequest account)
        {
            (string accessToken, string errorMessage, string role, string userId) = await _accountService.AuthAsync(account);
            if (!string.IsNullOrEmpty(errorMessage))
            {
                return Ok(new ResponseBaseModel() { Data = null, Message = errorMessage, Status = 400 });
            }
            return Ok(new ResponseBaseModel() { Data = new {token = accessToken, role = role, userId = userId }, Message = "Success", Status = 200} );
        }

        [HttpPost("Register")]
        public async Task<IActionResult> Register([FromBody] AccountRegisterRequest account)
        {
            (string errorMessage, string email, string pwd) = await _accountService.Register_Client(account);
            if (!string.IsNullOrEmpty(errorMessage))
            {
                return Ok(new ResponseBaseModel() { Data = null, Message = errorMessage, Status = 400 });
            }
            return Ok(new ResponseBaseModel() { Data = new { mail = email, password = pwd }, Message = "Success", Status = 200 });
        }

        [HttpPost("OTP")]
        public async Task<IActionResult> OTP([FromBody] OTPRequest otpReq)
        {
            (string errorMessage, string Email, string otpSend) = await _accountService.OTP(otpReq);

            if (!string.IsNullOrEmpty(errorMessage))
            {
                return Ok(new ResponseBaseModel() { Data = null, Message = "Failed to send OTP", Status = 400 });
            }
            return Ok(new ResponseBaseModel() { Data = new { email = otpReq.email, otp = otpReq.otp }, Message = "OTP sent: " + Email, Status = 200 });
        }

    }
}
