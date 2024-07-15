package com.example.bookingandr;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import Api.ApiClient;
import model.RoomModel;
import model.RoomResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRoomActivity extends AppCompatActivity {

    private EditText editRoomName, editCurrentPeople, editIsAvailable, editTypeId, editBuildingId, editFloor;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        editRoomName = findViewById(R.id.edit_room_name);
        editCurrentPeople = findViewById(R.id.edit_current_people);
        editIsAvailable = findViewById(R.id.edit_is_available);
        editTypeId = findViewById(R.id.edit_type_id);
        editBuildingId = findViewById(R.id.edit_building_id);
        editFloor = findViewById(R.id.edit_floor);
        addButton = findViewById(R.id.add_button);

        addButton.setOnClickListener(v -> {
            RoomModel newRoom = new RoomModel();
            newRoom.setName(editRoomName.getText().toString());
            newRoom.setCurrentPeople(Integer.parseInt(editCurrentPeople.getText().toString()));
            newRoom.setAvailble(editIsAvailable.getText().toString().equalsIgnoreCase("Available"));
            newRoom.setTypeId(Integer.parseInt(editTypeId.getText().toString()));
            newRoom.setBuildingId(Integer.parseInt(editBuildingId.getText().toString()));
            newRoom.setFloor(Integer.parseInt(editFloor.getText().toString()));

            ApiClient apiClient = new ApiClient();
            apiClient.getApiService().addRoom(newRoom).enqueue(new Callback<RoomResponseModel<RoomModel>>() {
                @Override
                public void onResponse(Call<RoomResponseModel<RoomModel>> call, Response<RoomResponseModel<RoomModel>> response) {
                    if (response.isSuccessful()) {
                        setResult(Activity.RESULT_OK);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<RoomResponseModel<RoomModel>> call, Throwable t) {
                    // Handle failure
                }
            });
        });
    }
}
