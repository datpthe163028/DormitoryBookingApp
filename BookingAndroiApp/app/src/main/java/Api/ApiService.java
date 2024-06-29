package Api;

import java.util.List;

import model.BookingHistoryModel;
import model.GetListTypeRoomResponseModel;
import model.LoginRequestModel;
import model.LoginResponseModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("api/Account/Auth")
    Call<LoginResponseModel> loginUser(@Body LoginRequestModel loginRequest);

    @GET("api/TypeRoom")
    Call<List<GetListTypeRoomResponseModel>> GetListTypeRoom();

    @GET("api/BookingHistory/user/{userId}")
    Call<List<BookingHistoryModel>> getBookingHistoryByUserId(@Path("userId") int userId);
}
