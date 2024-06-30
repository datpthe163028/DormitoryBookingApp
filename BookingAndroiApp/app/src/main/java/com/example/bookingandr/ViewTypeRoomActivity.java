package com.example.bookingandr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Api.ApiClient;
import Api.ApiService;
import Adapter.AdminRoomTypeAdapter;
import model.GetListTypeRoomResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewTypeRoomActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTypeRoom;
    private AdminRoomTypeAdapter adminRoomTypeAdapter;
    private List<GetListTypeRoomResponseModel> roomTypeList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_type_room);

        recyclerViewTypeRoom = findViewById(R.id.recyclerViewTypeRoom);
        recyclerViewTypeRoom.setLayoutManager(new LinearLayoutManager(this));

        loadRoomTypes();
        ImageView back = findViewById(R.id.back_icon);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewTypeRoomActivity.this, AdminDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadRoomTypes() {
        ApiClient apiClient = new ApiClient();
        ApiService apiService = apiClient.getApiService();
        Call<List<GetListTypeRoomResponseModel>> call = apiService.GetListTypeRoom();

        call.enqueue(new Callback<List<GetListTypeRoomResponseModel>>() {
            @Override
            public void onResponse(Call<List<GetListTypeRoomResponseModel>> call, Response<List<GetListTypeRoomResponseModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    roomTypeList = response.body();
                    adminRoomTypeAdapter = new AdminRoomTypeAdapter(ViewTypeRoomActivity.this, roomTypeList);
                    recyclerViewTypeRoom.setAdapter(adminRoomTypeAdapter);
                } else {
                    Toast.makeText(ViewTypeRoomActivity.this, "Failed to load room types", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<GetListTypeRoomResponseModel>> call, Throwable t) {
                Toast.makeText(ViewTypeRoomActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
