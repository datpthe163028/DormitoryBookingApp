package com.example.bookingandr;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Date;

import Api.ApiClient;
import Api.ApiService;
import model.NewsModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewsActivity extends AppCompatActivity {
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_news);

        ImageView back = findViewById(R.id.back_button);
        back.setOnClickListener(view -> finish());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SharedPreferences sharedPreferences = getSharedPreferences("UserInformation", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("UserId", null);
        apiService = new ApiClient().getApiService();

        EditText headerInput = findViewById(R.id.headerInput);
        EditText contentInput = findViewById(R.id.contentInput);
        Button submitNewsButton = findViewById(R.id.submitNewsButton);

        submitNewsButton.setOnClickListener(v -> {
            String header = headerInput.getText().toString().trim();
            String content = contentInput.getText().toString().trim();

            if (header.isEmpty() || content.isEmpty()) {
                Toast.makeText(AddNewsActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                NewsModel newsModel = new NewsModel();
                newsModel.setDatePost(new Date());
                newsModel.setHeader(header);
                newsModel.setContent(content);
                newsModel.setUserId(Integer.parseInt(userId));

                addNews(newsModel);
            }
        });
    }
    private void addNews(NewsModel newsModel) {
        apiService.addNews(newsModel).enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddNewsActivity.this, "News added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddNewsActivity.this, "Failed to add news", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Toast.makeText(AddNewsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}