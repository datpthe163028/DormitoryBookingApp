package com.example.bookingandr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import Adapter.RoomAdapter;
import Api.ApiClient;
import Api.ApiService;
import model.RoomResponseModel;
import model.RoomModel;
import java.util.List;

import model.RoomModel;
import model.RoomResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrudRoomFragment extends Fragment {

    private RecyclerView roomRecyclerView;
    private RoomAdapter roomAdapter;
    private static final int EDIT_ROOM_REQUEST_CODE = 1;
    private static final int ADD_ROOM_REQUEST_CODE = 2;
    private Button buttonAddRoom;

    public CrudRoomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crud_room, container, false);
        roomRecyclerView = view.findViewById(R.id.roomRecyclerView);
        roomRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        buttonAddRoom = view.findViewById(R.id.buttonAddRoom);
        buttonAddRoom.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddRoomActivity.class);
            startActivityForResult(intent, ADD_ROOM_REQUEST_CODE);
        });
        fetchRooms();
        return view;
    }

    public void fetchRooms() {
        ApiClient apiClient = new ApiClient();
        apiClient.getApiService().getAllRooms().enqueue(new Callback<RoomResponseModel<List<RoomModel>>>() {
            @Override
            public void onResponse(Call<RoomResponseModel<List<RoomModel>>> call, Response<RoomResponseModel<List<RoomModel>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<RoomModel> roomList = response.body().getData();
                    roomAdapter = new RoomAdapter(roomList, getContext(), CrudRoomFragment.this);
                    roomRecyclerView.setAdapter(roomAdapter);
                }
            }

            @Override
            public void onFailure(Call<RoomResponseModel<List<RoomModel>>> call, Throwable t) {
                Log.e("failure", "failure");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == EDIT_ROOM_REQUEST_CODE || requestCode == ADD_ROOM_REQUEST_CODE) && resultCode == Activity.RESULT_OK) {
            fetchRooms(); // Refresh the list
        }
    }
}
