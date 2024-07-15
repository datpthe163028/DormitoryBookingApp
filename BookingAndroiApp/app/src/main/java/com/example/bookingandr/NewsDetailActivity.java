package com.example.bookingandr;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import Api.ApiClient;
import Api.ApiService;
import model.NewsModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDetailActivity extends AppCompatActivity {
    private TextView newsHeader;
    private TextView newsDate;
    private TextView newsContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_news_detail);

        newsHeader = findViewById(R.id.newsHeader);
        newsDate = findViewById(R.id.newsDate);
        newsContent = findViewById(R.id.newsContent);
        ImageView back = findViewById(R.id.back_button);

        back.setOnClickListener(view -> finish());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get newsId from intent extras
        if (getIntent() != null && getIntent().hasExtra("newsId")) {
            int newsId = getIntent().getIntExtra("newsId", -1);
            if (newsId != -1) {
                fetchNewsDetails(newsId);
            }
        }
    }

    private void fetchNewsDetails(int newsId) {
        ApiClient apiClient = new ApiClient();
        ApiService apiService = apiClient.getApiService();

        Call<NewsModel> call = apiService.getNews(newsId);
        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    NewsModel news = response.body();
                    displayNewsDetails(news);
                } else {
                    Toast.makeText(NewsDetailActivity.this, "Failed to load news", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Toast.makeText(NewsDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void displayNewsDetails(NewsModel news) {
        newsHeader.setText(news.getHeader());
        newsDate.setText(news.getDatePost().toString());
        newsContent.setText(news.getContent());
    }
}
