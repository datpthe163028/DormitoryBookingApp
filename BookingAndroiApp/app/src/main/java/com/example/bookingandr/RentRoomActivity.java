package com.example.bookingandr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Adapter.ListRoom2Adapter;
import Adapter.RoomTypeAdapter;
import Api.ApiClient;
import model.BookingRoomResponseModel;
import model.GetCurrenRoomModel;
import model.GetListBuilding2ResponseModel;
import model.GetListTypeRoomResponseModel;
import model.ResponseGetListRoom2Model;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RentRoomActivity extends AppCompatActivity {

    private Spinner spinnerBuildings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ApiClient apiClient = new ApiClient();
        SharedPreferences sharedPref = this.getSharedPreferences("UserInformation", Context.MODE_PRIVATE);
        String userId = sharedPref.getString("UserId", null);



                int roomId = getIntent().getIntExtra("ROOM_ID", -1);
                ApiClient apiClient2 = new ApiClient();
                apiClient2.getApiService().GetListBuilding2().enqueue(new Callback<List<GetListBuilding2ResponseModel>>() {
                    @Override
                    public void onResponse(Call<List<GetListBuilding2ResponseModel>> call, Response<List<GetListBuilding2ResponseModel>> response) {
                        List<GetListBuilding2ResponseModel> model = response.body();
                        if (model != null) {
                            populateSpinner(model, roomId);
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(Call<List<GetListBuilding2ResponseModel>> call, Throwable throwable) {
                        Log.e("123123213213", throwable.getMessage());
                    }
                });


        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rent_room);

        ImageView back = findViewById(R.id.back_icon);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }

    private void populateSpinner(List<GetListBuilding2ResponseModel> model, int roomId) {
        spinnerBuildings = findViewById(R.id.spinnerBuildings);
        spinnerBuildings.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                Log.e("Selected Item", selectedItem);
                ApiClient apiClient = new ApiClient();
                apiClient.getApiService().getRoomsForCustomer(roomId, selectedItem).enqueue(new Callback<ResponseGetListRoom2Model>() {
                    @Override
                    public void onResponse(Call<ResponseGetListRoom2Model> call, Response<ResponseGetListRoom2Model> response) {
                        ResponseGetListRoom2Model model = response.body();

                        RecyclerView recyclerView;
                        ListRoom2Adapter listRoom2Adapter;
                        recyclerView = findViewById(R.id.recyclerListRoom2);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new GridLayoutManager(RentRoomActivity.this, 1));
                        listRoom2Adapter = new ListRoom2Adapter(RentRoomActivity.this, model.rooms);
                        recyclerView.setAdapter(listRoom2Adapter);
                    }

                    @Override
                    public void onFailure(Call<ResponseGetListRoom2Model> call, Throwable throwable) {
                        Log.e("123", throwable.getMessage());
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
        List<String> buildingNames = new ArrayList<>();
        for (GetListBuilding2ResponseModel item : model) {
            buildingNames.add(item.name); // Adjust this according to your model
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, buildingNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBuildings.setAdapter(adapter);
    }
}