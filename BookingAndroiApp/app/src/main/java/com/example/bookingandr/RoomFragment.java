package com.example.bookingandr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import Adapter.RoomTypeAdapter;
import Api.ApiClient;
import model.GetCurrenRoomModel;
import model.GetListTypeRoomResponseModel;
import model.LoginRequestModel;
import model.LoginResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RoomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoomFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RoomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RoomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RoomFragment newInstance(String param1, String param2) {
        RoomFragment fragment = new RoomFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_room, container, false);
    }
    RecyclerView recyclerView;
    RoomTypeAdapter roomTypeAdapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ApiClient apiClient = new ApiClient();



        apiClient.getApiService().GetListTypeRoom().enqueue(new Callback<List<GetListTypeRoomResponseModel>>() {
            @Override
            public void onResponse(Call<List<GetListTypeRoomResponseModel>> call, Response<List<GetListTypeRoomResponseModel>> response) {
                List<GetListTypeRoomResponseModel> model = response.body();

                Log.e("kkkkkkkkkkkk", "b");

                for (GetListTypeRoomResponseModel item : model) {

                }
                Log.e("kkkkkkkkkkkk", "dat");

                recyclerView = view.findViewById(R.id.recyclerRoomType);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
                roomTypeAdapter = new RoomTypeAdapter(getContext(), model);
                recyclerView.setAdapter(roomTypeAdapter);
            }

            @Override
            public void onFailure(Call<List<GetListTypeRoomResponseModel>> call, Throwable throwable) {
                Log.e("kkkkkkkkkkkk", throwable.getMessage());
            }
        });



    }
}