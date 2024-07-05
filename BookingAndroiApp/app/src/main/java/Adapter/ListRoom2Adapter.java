package Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookingandr.CurrentRoom2Activity;
import com.example.bookingandr.R;
import com.example.bookingandr.RentRoomActivity;

import java.util.List;

import Api.ApiClient;
import model.BookingRoomResponseModel;
import model.GetListTypeRoomResponseModel;
import model.ResponseGetListRoom2Data;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListRoom2Adapter extends RecyclerView.Adapter<ListRoom2AdapterHolder>{
    private Context context;
    private List<ResponseGetListRoom2Data> list;

    public ListRoom2Adapter(Context context, List<ResponseGetListRoom2Data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ListRoom2AdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListRoom2AdapterHolder(LayoutInflater.from(context).inflate(R.layout.layout_for_list_room2, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ListRoom2AdapterHolder holder, int position) {
        holder.button1.setVisibility(View.GONE);
        holder.button2.setVisibility(View.GONE);
        holder.button3.setVisibility(View.GONE);
        holder.button4.setVisibility(View.GONE);
        holder.button5.setVisibility(View.GONE);
        SharedPreferences sharedPref = context.getSharedPreferences("UserInformation", Context.MODE_PRIVATE);
        String userId = sharedPref.getString("UserId", null);

        // Calculate start index and end index for the current row
        int startIndex = position * 5;
        int endIndex = Math.min(startIndex + 5, list.size());

        // Get sublist for the current row
        List<ResponseGetListRoom2Data> sublist = list.subList(startIndex, endIndex);

        // Set visibility and text for each button in the row
        for (int i = 0; i < sublist.size(); i++) {
            String buttonText = sublist.get(i).name;
            int buttonId = sublist.get(i).id;
            Boolean check = sublist.get(i).isAvailble;
            switch (i) {
                case 0:
                    holder.button1.setVisibility(View.VISIBLE);
                    holder.button1.setText(buttonText);
                    holder.button1.setId(buttonId);
                    if(!check)
                        holder.button1.setBackgroundColor(Color.RED);
                    else{
                        holder.button1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                builder.setMessage("Are you want to sell it?")
                                        .setTitle("Confirmation");

                                // Thêm nút "Yes"
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        ApiClient apiClient = new ApiClient();
                                        apiClient.getApiService().BookingRoom( userId , holder.button1.getId() + "").enqueue(new Callback<BookingRoomResponseModel>() {
                                            @Override
                                            public void onResponse(Call<BookingRoomResponseModel> call, Response<BookingRoomResponseModel> response) {
                                                BookingRoomResponseModel model = response.body();
                                                if(model.status.equals("success")){
                                                    Intent intent = new Intent(context, CurrentRoom2Activity.class);
                                                    intent.putExtra("ROOM", model.roomName);
                                                    intent.putExtra("BUILDING", model.buildingName);
                                                    context.startActivity(intent);
                                                    ((Activity) context).finish();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<BookingRoomResponseModel> call, Throwable throwable) {

                                            }
                                        });
                                    }
                                });

                                // Thêm nút "No"
                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Hành động khi người dùng nhấn "No"
                                        // Bạn có thể thêm mã ở đây nếu cần
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        });
                    }
                    break;
                case 1:
                    holder.button2.setVisibility(View.VISIBLE);
                    holder.button2.setText(buttonText);
                    holder.button2.setId(buttonId);
                    if(!check)
                        holder.button2.setBackgroundColor(Color.RED);
                    else{
                        holder.button2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                builder.setMessage("Are you want to sell it?" + holder.button2.getId())
                                        .setTitle("Confirmation");

                                // Thêm nút "Yes"
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                ApiClient apiClient = new ApiClient();
                                                apiClient.getApiService().BookingRoom( userId , holder.button2.getId() + "").enqueue(new Callback<BookingRoomResponseModel>() {
                                                    @Override
                                                    public void onResponse(Call<BookingRoomResponseModel> call, Response<BookingRoomResponseModel> response) {

                                                    }

                                                    @Override
                                                    public void onFailure(Call<BookingRoomResponseModel> call, Throwable throwable) {

                                                    }
                                                });
                                            }
                                        });
                                    }
                                });

                                // Thêm nút "No"
                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Hành động khi người dùng nhấn "No"
                                        // Bạn có thể thêm mã ở đây nếu cần
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        });
                    }
                    break;
                case 2:
                    holder.button3.setVisibility(View.VISIBLE);
                    holder.button3.setText(buttonText);
                    holder.button3.setId(buttonId);
                    if(!check)
                        holder.button3.setBackgroundColor(Color.RED);
                    else{
                        holder.button3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                builder.setMessage("Are you want to sell it?" + holder.button3.getId())
                                        .setTitle("Confirmation");

                                // Thêm nút "Yes"
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                ApiClient apiClient = new ApiClient();
                                                apiClient.getApiService().BookingRoom( userId , holder.button3.getId() + "").enqueue(new Callback<BookingRoomResponseModel>() {
                                                    @Override
                                                    public void onResponse(Call<BookingRoomResponseModel> call, Response<BookingRoomResponseModel> response) {

                                                    }

                                                    @Override
                                                    public void onFailure(Call<BookingRoomResponseModel> call, Throwable throwable) {

                                                    }
                                                });
                                            }
                                        });
                                    }
                                });

                                // Thêm nút "No"
                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Hành động khi người dùng nhấn "No"
                                        // Bạn có thể thêm mã ở đây nếu cần
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        });
                    }
                    break;
                case 3:
                    holder.button4.setVisibility(View.VISIBLE);
                    holder.button4.setText(buttonText);
                    holder.button4.setId(buttonId);
                    if(!check)
                        holder.button4.setBackgroundColor(Color.RED);
                    else{
                        holder.button4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                builder.setMessage("Are you want to sell it?" + holder.button4.getId())
                                        .setTitle("Confirmation");

                                // Thêm nút "Yes"
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                ApiClient apiClient = new ApiClient();
                                                apiClient.getApiService().BookingRoom( userId , holder.button4.getId() + "").enqueue(new Callback<BookingRoomResponseModel>() {
                                                    @Override
                                                    public void onResponse(Call<BookingRoomResponseModel> call, Response<BookingRoomResponseModel> response) {

                                                    }

                                                    @Override
                                                    public void onFailure(Call<BookingRoomResponseModel> call, Throwable throwable) {

                                                    }
                                                });
                                            }
                                        });
                                    }
                                });

                                // Thêm nút "No"
                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Hành động khi người dùng nhấn "No"
                                        // Bạn có thể thêm mã ở đây nếu cần
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        });
                    }
                    break;
                case 4:
                    holder.button5.setVisibility(View.VISIBLE);
                    holder.button5.setText(buttonText);
                    holder.button5.setId(buttonId);
                    if(!check)
                        holder.button5.setBackgroundColor(Color.RED);
                    else{
                        holder.button5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                builder.setMessage("Are you want to sell it?" +  holder.button5.getId())
                                        .setTitle("Confirmation");

                                // Thêm nút "Yes"
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                ApiClient apiClient = new ApiClient();
                                                apiClient.getApiService().BookingRoom( userId , holder.button5.getId() + "").enqueue(new Callback<BookingRoomResponseModel>() {
                                                    @Override
                                                    public void onResponse(Call<BookingRoomResponseModel> call, Response<BookingRoomResponseModel> response) {

                                                    }

                                                    @Override
                                                    public void onFailure(Call<BookingRoomResponseModel> call, Throwable throwable) {

                                                    }
                                                });
                                            }
                                        });
                                    }
                                });

                                // Thêm nút "No"
                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        });
                    }
                    break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return (int) Math.ceil((double) list.size() / 5);
    }
}
