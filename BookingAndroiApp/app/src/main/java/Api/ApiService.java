package Api;

import java.util.List;

import model.GetListTypeRoomResponseModel;
import model.LoginRequestModel;
import model.LoginResponseModel;
import model.OTPRequestModel;
import model.OTPResponseModel;
import model.RegisterRequestModel;
import model.RegisterResponseModel;
<<<<<<< Updated upstream
=======
import model.ResponseGetListRoom2Model;
import model.RoomTypePostModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import model.NewsModel;
>>>>>>> Stashed changes
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiService {

    //LDA
    @POST("api/Account/Register")
    Call<RegisterResponseModel> RegisterUser(@Body RegisterRequestModel loginRequest);

    @POST("api/Account/OTP")
    Call<OTPResponseModel> SendOTP(@Body OTPRequestModel OTPReq);


    @POST("api/Account/Auth")
    Call<LoginResponseModel> loginUser(@Body LoginRequestModel loginRequest);

    @POST("api/Account/Register")
    Call<RegisterResponseModel> RegisterUser(@Body RegisterRequestModel loginRequest);

    @POST("api/Account/OTP")
    Call<OTPResponseModel> SendOTP(@Body OTPRequestModel OTPReq);

    @GET("api/TypeRoom")
    Call<List<GetListTypeRoomResponseModel>> GetListTypeRoom();
}
