package Api;

import java.util.List;

import model.BookingHistoryModel;
import model.GetListTypeRoomResponseModel;
import model.LoginRequestModel;
import model.LoginResponseModel;
import model.RoomTypePostModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    @POST("api/Account/Auth")
    Call<LoginResponseModel> loginUser(@Body LoginRequestModel loginRequest);

    @GET("api/TypeRoom")
    Call<List<GetListTypeRoomResponseModel>> GetListTypeRoom();

    @GET("api/BookingHistory/user/{userId}")
    Call<List<BookingHistoryModel>> getBookingHistoryByUserId(@Path("userId") int userId);
    @Multipart
    @POST("api/TypeRoom")
    Call<Void> createRoomType(
            @Part("capacity") RequestBody capacity,
            @Part("price") RequestBody price,
            @Part MultipartBody.Part image
    );
    @Multipart
    @PUT ("api/TypeRoom/{id}")
    Call<Void> editRoomType(
            @Path("id") int id,
            @Part("capacity") RequestBody capacity,
            @Part("price") RequestBody price,
            @Part MultipartBody.Part image
    );
    @DELETE("api/TypeRoom/{id}")
    Call<Void> deleteRoomType(@Path("id") int id);
    @GET("api/TypeRoom/{id}")
    Call<GetListTypeRoomResponseModel> getRoomTypeById(@Path("id") int id);
}

