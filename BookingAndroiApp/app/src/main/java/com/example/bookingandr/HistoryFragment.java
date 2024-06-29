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

import java.util.List;

import Adapter.BookingHistoryAdapter;
import Api.ApiClient;
import model.BookingHistoryModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookingHistoryAdapter bhAdapter;

    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Handle fragment arguments if necessary
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("UserInformation", Context.MODE_PRIVATE);
        String userId = sharedPref.getString("UserId", null);
        recyclerView = view.findViewById(R.id.recyclerBookingHistory);
        recyclerView.setHasFixedSize(true);
       recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        Log.e("duong", "123");
        ApiClient apiClient = new ApiClient();
        apiClient.getApiService().getBookingHistoryByUserId(Integer.parseInt(userId)).enqueue(new Callback<List<BookingHistoryModel>>() {
            @Override
            public void onResponse(Call<List<BookingHistoryModel>> call, Response<List<BookingHistoryModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<BookingHistoryModel> model = response.body();
                    bhAdapter = new BookingHistoryAdapter(getContext(), model);
                   recyclerView.setAdapter(bhAdapter);
                } else {
                    Log.e("duong", "Response unsuccessful");
                }
            }

            @Override
            public void onFailure(Call<List<BookingHistoryModel>> call, Throwable throwable) {
                Log.e("duong", throwable.getMessage());
            }
        });


    }
}
