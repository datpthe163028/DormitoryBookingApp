package com.example.bookingandr;

import android.content.Context;
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

import com.google.gson.Gson;

import java.util.List;

import Adapter.BookingHistoryAdapter;
import Adapter.NewsforuserAdapter;
import Api.ApiClient;
import model.BookingHistoryModel;
import model.NewsModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {
    private RecyclerView recyclerView;
    private NewsforuserAdapter bhAdapter;

    public NewsFragment() {
        // Required empty public constructor
    }

    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
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
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerNews);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        ApiClient apiClient = new ApiClient();
        apiClient.getApiService().getNews().enqueue(new Callback<List<NewsModel>>() {
            @Override
            public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<NewsModel> model = response.body();

                    // Log the list size
                    Log.d("duong", "Received " + model.size() + " news items.");

                    // Log each item in the list
                    for (NewsModel news : model) {
                        Log.d("duong", "News item: " + news.toString());
                        // Or you can log specific fields
                         Log.d("duong", "Title: " + news.getHeader());
                        Log.d("duong", "JSON Response: " + new Gson().toJson(response.body()));

                        // Log.d("NewsFragment", "Description: " + news.getDescription());
                        // etc.
                    }

                    bhAdapter = new NewsforuserAdapter(getContext(), model);
                    recyclerView.setAdapter(bhAdapter);
                } else {
                    Log.e("duong", "Response unsuccessful");
                }
            }


            @Override
            public void onFailure(Call<List<NewsModel>> call, Throwable throwable) {
                Log.e("duong", "loi j vay");
            }
        });


    }
}