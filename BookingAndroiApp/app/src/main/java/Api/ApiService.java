package Api;

import java.util.List;

import model.BookingHistoryModel;
import model.GetListTypeRoomResponseModel;
import model.LoginRequestModel;
import model.LoginResponseModel;
import model.NewsModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @POST("api/Account/Auth")
    Call<LoginResponseModel> loginUser(@Body LoginRequestModel loginRequest);

    @GET("api/TypeRoom")
    Call<List<GetListTypeRoomResponseModel>> GetListTypeRoom();

    @GET("api/BookingHistory/user/{userId}")
    Call<List<BookingHistoryModel>> getBookingHistoryByUserId(@Path("userId") int userId);

    @GET("api/News")
    Call<List<NewsModel>> getNews();

    @GET("api/News/{id}")
    Call<NewsModel> getNews(@Path("id") int id);

    @POST("api/News")
    Call<NewsModel> addNews(@Body NewsModel newsModel);

    @PUT("api/News/{id}")
    Call<Void> updateNews(@Path("id") int id, @Body NewsModel newsModel);

    @DELETE("api/News/{id}")
    Call<Void> deleteNews(@Path("id") int id);

}
