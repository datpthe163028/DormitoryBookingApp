package Api;

import java.util.List;

import model.BookingHistoryModel;
import model.BookingRoomResponseModel;
import model.GetListBuilding2ResponseModel;
import model.GetListTypeRoomResponseModel;
import model.GetStatusCModel;
import model.LoginRequestModel;
import model.LoginResponseModel;
import model.ResponseGetListRoom2Model;
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
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {
    @POST("api/Account/Auth")
    Call<LoginResponseModel> loginUser(@Body LoginRequestModel loginRequest);

    @GET("api/TypeRoom")
    Call<List<GetListTypeRoomResponseModel>> GetListTypeRoom();

    @GET("api/RoomForCustomer")
    Call<ResponseGetListRoom2Model> getRoomsForCustomer(@Query("TypeRoomId") int typeRoomId, @Query("BuildingName") String buildingName);

    @GET("api/RoomForCustomer/listbuilding")
    Call<List<GetListBuilding2ResponseModel>> GetListBuilding2();

    @GET("api/RoomForCustomer/bookingRoom")
    Call<BookingRoomResponseModel> BookingRoom(@Query("userId") String userId, @Query("roomId") String roomId );

    @GET("api/RoomForCustomer/GetCurrentRoom")
    Call<BookingRoomResponseModel> GetCurrentRoom(@Query("userId") String userId);
    @GET("api/RoomForCustomer/GetCurrentDeserve")
    Call<GetStatusCModel> GetStatusc(@Query("userId") String userId);
    @GET("api/RoomForCustomer/Deserve")
    Call<GetStatusCModel> Derserve(@Query("userId") String userId);


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

