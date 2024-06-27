package com.example.bookingandr;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import Api.ApiClient;
import model.LoginRequestModel;
import model.LoginResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        SharedPreferences sharedPreferences = getSharedPreferences("UserInformation", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("UserId", null);

        if(userId != null && !userId.isEmpty()){
            String role = sharedPreferences.getString("Role", null);

            if(role.equals("Admin")){
                Intent intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
                startActivity(intent);
                finish();
            }else{
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }

        TextView register = findViewById(R.id.regis);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        Button loginBtn = findViewById(R.id.lgButton);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                int check = 0;
                EditText email = findViewById(R.id.emailText);
                EditText password = findViewById(R.id.passwordText);
                TextView emailError = findViewById(R.id.er1);
                TextView passwordError = findViewById(R.id.er2);

                String emailInput = email.getText().toString();
                String passwordInput = password.getText().toString();
                emailError.setText("");
                passwordError.setText("");
                if (emailInput.isEmpty() || !emailInput.contains("@")) {
                    check = 1;
                    emailError.setText("Enter email");
                }

                if (passwordInput.isEmpty()) {
                    check = 1;
                    passwordError.setText("Enter password");
                }

                if(check == 0){
                    ApiClient apiClient = new ApiClient();
                    apiClient.getApiService().loginUser(new LoginRequestModel(emailInput, passwordInput))
                            .enqueue(new Callback<LoginResponseModel>() {
                                @Override
                                public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                                    LoginResponseModel model = response.body();
                                    if(model.status == 200){


                                        SharedPreferences sharedPref = getSharedPreferences("UserInformation", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPref.edit();
                                        editor.putString("UserId", model.data.userId);
                                        editor.putString("Role", model.data.role);
                                        editor.putString("accessToken", model.data.token);
                                        editor.apply();

                                        if(model.data.role.equals("Admin")){
                                            Intent intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }else{
                                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }else {
                                        passwordError.setText(model.message);
                                    }
                                }
                                @Override
                                public void onFailure(Call<LoginResponseModel> call, Throwable throwable) {
                                    passwordError.setText("Api error");
                                }
                            });
                }
            }
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}