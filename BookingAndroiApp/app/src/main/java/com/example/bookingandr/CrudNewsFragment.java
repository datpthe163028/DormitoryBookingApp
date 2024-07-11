package com.example.bookingandr;

import static Adapter.NewsForAdminAdapter.EDIT_News_REQUEST_CODE;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.ImageView;

import com.google.gson.Gson;

import java.util.List;

import Adapter.NewsForAdminAdapter;
import Api.ApiClient;
import model.NewsModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrudNewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrudNewsFragment extends Fragment {
    private RecyclerView recyclerView;
    private NewsForAdminAdapter bhAdapter;
    public static final int ADD_NEWS_REQUEST_CODE = 2;

    public CrudNewsFragment() {
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crud_news, container, false);

        ImageView addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddNewsActivity.class);
            startActivityForResult(intent, ADD_NEWS_REQUEST_CODE);
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerCRUDNews);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        refreshNewsList();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == EDIT_News_REQUEST_CODE || requestCode == ADD_NEWS_REQUEST_CODE) && resultCode == Activity.RESULT_OK) {
            // Refresh the news list
            refreshNewsList();
        }
    }

    private void refreshNewsList() {
        ApiClient apiClient = new ApiClient();
        apiClient.getApiService().getNews().enqueue(new Callback<List<NewsModel>>() {
            @Override
            public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<NewsModel> model = response.body();
                    bhAdapter = new NewsForAdminAdapter(getContext(), model, CrudNewsFragment.this);
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
