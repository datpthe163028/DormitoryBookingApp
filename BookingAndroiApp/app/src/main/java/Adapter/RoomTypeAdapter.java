package Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookingandr.CurrentRoom2Activity;
import com.example.bookingandr.R;
import com.example.bookingandr.RentRoomActivity;

import java.util.List;

import Api.ApiClient;
import model.BookingRoomResponseModel;
import model.GetListBuilding2ResponseModel;
import model.GetListTypeRoomResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomTypeAdapter extends RecyclerView.Adapter<RoomTypeAdapterHolder> {
    private Context context;
    private List<GetListTypeRoomResponseModel> list;

    public RoomTypeAdapter(Context context, List<GetListTypeRoomResponseModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RoomTypeAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RoomTypeAdapterHolder(LayoutInflater.from(context).inflate(R.layout.for_list_typeroom, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RoomTypeAdapterHolder holder, int position) {
        holder.t1.setText(list.get(position).getCapacity() + " people");
        Glide.with(context)
                .load(list.get(position).getImageUrl())
                .transform(new CenterCrop(),new RoundedCorners(50))
                .into(holder.t2);

        holder.t3.setId(list.get(position).getId());
        holder.t4.setText(list.get(position).getPrice() + "$");
        int d = holder.t3.getId();
        holder.t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int buttonId = v.getId();

                ApiClient apiClient = new ApiClient();
                SharedPreferences sharedPref =  context.getSharedPreferences("UserInformation", Context.MODE_PRIVATE);
                String userId = sharedPref.getString("UserId", null);

                apiClient.getApiService().GetCurrentRoom(userId).enqueue(new Callback<BookingRoomResponseModel>() {
                    @Override
                    public void onResponse(Call<BookingRoomResponseModel> call, Response<BookingRoomResponseModel> response) {
                        BookingRoomResponseModel model = response.body();

                        if (model.status.equals("true")){
                            Intent intent = new Intent(context, CurrentRoom2Activity.class);
                            intent.putExtra("ROOM", model.roomName);
                            intent.putExtra("BUILDING", model.buildingName);
                            context.startActivity(intent);
                        }else{
                            Intent intent = new Intent(context, RentRoomActivity.class);
                            intent.putExtra("ROOM_ID", buttonId);
                            context.startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<BookingRoomResponseModel> call, Throwable throwable) {

                    }
                }) ;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
