package com.example.bookingandr;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

import Api.ApiClient;
import Api.ApiService;
import model.NewsModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateNewsActivity extends AppCompatActivity {

    private EditText headerInput, contentInput;
    private Button submitNewsButton;
    private ApiService apiService;
    private int newsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_news);
        ImageView back = findViewById(R.id.back_button);
        back.setOnClickListener(view -> finish());
        // Initialize views
        headerInput = findViewById(R.id.headerInput);
        contentInput = findViewById(R.id.contentInput);
        submitNewsButton = findViewById(R.id.submitNewsButton);

        // Initialize Retrofit ApiService
        apiService = new ApiClient().getApiService();

        // Retrieve the news ID from the intent
        newsId = getIntent().getIntExtra("newsId", -1);
        if (newsId == -1) {
            Toast.makeText(this, "Invalid News ID", Toast.LENGTH_SHORT).show();
            finish(); // Close activity if news ID is not valid
        }

        // Fetch news details from API and populate EditTexts
        fetchNewsDetails();

        // Handle submit button click to update news
        submitNewsButton.setOnClickListener(v -> updateNews());
    }

    private void fetchNewsDetails() {
        // Call API to fetch news details
        Call<NewsModel> call = apiService.getNews(newsId);
        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Populate EditTexts with current news details
                    NewsModel news = response.body();
                    headerInput.setText(news.getHeader());
                    contentInput.setText(news.getContent());
                } else {
                    Toast.makeText(UpdateNewsActivity.this, "Failed to fetch news details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Toast.makeText(UpdateNewsActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateNews() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserInformation", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("UserId", null);
        String header = headerInput.getText().toString().trim();
        String content = contentInput.getText().toString().trim();

        if (header.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create NewsModel object with updated details
        NewsModel updatedNews = new NewsModel();
        updatedNews.setId(newsId); // Set the news ID
        updatedNews.setHeader(header);
        updatedNews.setContent(content);
        updatedNews.setDatePost(new Date());
        updatedNews.setUserId(Integer.parseInt(userId));

        // Call API to update news
        Call<Void> call = apiService.updateNews(newsId, updatedNews);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UpdateNewsActivity.this, "News updated successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Close activity after successful update
                } else {
                    Toast.makeText(UpdateNewsActivity.this, "Failed to update news", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(UpdateNewsActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
