using ApiBookingApplication.Common;
using ApiBookingApplication.Service.Account;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

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
            (string accessToken, string errorMessage, string role) = await _accountService.AuthAsync(account);
            if (!string.IsNullOrEmpty(errorMessage))
            {
                return Ok(new ResponseBaseModel() { Data = null, Message = errorMessage, Status = 400 });
            }
            return Ok(new ResponseBaseModel() { Data = new {token = accessToken, role = role}, Message = "Success", Status = 200} );
        }

    }
}
