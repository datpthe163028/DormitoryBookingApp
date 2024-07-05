package com.example.bookingandr;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import Api.ApiClient;
import model.RoomModel;
import model.RoomResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditRoomActivity extends AppCompatActivity {

    private EditText editRoomName, editCurrentPeople, editIsAvailable, editTypeId, editBuildingId, editFloor;
    private Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_room);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        editRoomName = findViewById(R.id.edit_room_name);
        editCurrentPeople = findViewById(R.id.edit_current_people);
        editIsAvailable = findViewById(R.id.edit_is_available);
        editTypeId = findViewById(R.id.edit_type_id);
        editBuildingId = findViewById(R.id.edit_building_id);
        editFloor = findViewById(R.id.edit_floor);
        updateButton = findViewById(R.id.update_button);

        // Retrieve the room data from the Intent
        RoomModel room = (RoomModel) getIntent().getSerializableExtra("room");

        if (room != null) {
            editRoomName.setText(room.getName());
            editCurrentPeople.setText(String.valueOf(room.getCurrentPeople()));
            editIsAvailable.setText(room.isAvailble() ? "Available" : "Not Available");
            editTypeId.setText(String.valueOf(room.getTypeId()));
            editBuildingId.setText(String.valueOf(room.getBuildingId()));
            editFloor.setText(String.valueOf(room.getFloor()));
        }

        // In EditRoomActivity
        updateButton.setOnClickListener(v -> {
            // Create a RoomModel object with updated values
            RoomModel updatedRoom = new RoomModel();
            updatedRoom.setId(room.getId());
            updatedRoom.setName(editRoomName.getText().toString());
            updatedRoom.setCurrentPeople(Integer.parseInt(editCurrentPeople.getText().toString()));
            updatedRoom.setAvailble(editIsAvailable.getText().toString().equalsIgnoreCase("Available"));
            updatedRoom.setTypeId(Integer.parseInt(editTypeId.getText().toString()));
            updatedRoom.setBuildingId(Integer.parseInt(editBuildingId.getText().toString()));
            updatedRoom.setFloor(Integer.parseInt(editFloor.getText().toString()));

            // Make a PUT request to update the room
            ApiClient apiClient = new ApiClient();
            apiClient.getApiService().updateRoom(updatedRoom).enqueue(new Callback<RoomResponseModel<RoomModel>>() {
                @Override
                public void onResponse(Call<RoomResponseModel<RoomModel>> call, Response<RoomResponseModel<RoomModel>> response) {
                    if (response.isSuccessful()) {
                        // Handle successful update
                        setResult(Activity.RESULT_OK);
//                        CrudRoomFragment roomFragment = new CrudRoomFragment();
//                        roomFragment.fetchRooms();
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