package Api;

import model.LoginRequestModel;
import model.LoginResponseModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiService {
    @POST("api/Account/Auth")
    Call<LoginResponseModel> loginUser(@Body LoginRequestModel loginRequest);
}