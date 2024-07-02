package com.example.bookingandr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

<<<<<<< Updated upstream
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

=======
>>>>>>> Stashed changes
import Api.ApiClient;
import model.LoginRequestModel;
import model.LoginResponseModel;
import model.OTPRequestModel;
import model.OTPResponseModel;
import model.RegisterRequestModel;
import model.RegisterResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPActivity extends AppCompatActivity
{

    public String hiddenOTP;
    public String emailBuffer;
    public String pwdBuffer;
    public String studentIDBuffer;
    public String phoneBuffer;
<<<<<<< Updated upstream
    public Date dobBuffer;
=======
>>>>>>> Stashed changes
    public boolean genderBuffer;
    public String dobString;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otp);

        // Retrieve the values passed from RegisterActivity
        Intent intent = getIntent();
        if (intent != null) {
            emailBuffer = intent.getStringExtra("email");
            pwdBuffer = intent.getStringExtra("password");
            studentIDBuffer = intent.getStringExtra("StudentID");
            phoneBuffer = intent.getStringExtra("Phone");
            hiddenOTP = intent.getStringExtra("hiddenOTP");

            // Convert genderString to boolean
            String genderString = intent.getStringExtra("Gender");
            genderBuffer = Boolean.parseBoolean(genderString);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Button newOTPBtn = findViewById(R.id.newOTPBtn);
        newOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendOTP(emailBuffer);
            }
        });

        Button verifyBtn = findViewById(R.id.verifyBtn);
<<<<<<< Updated upstream
=======
        Button BackBtn = findViewById(R.id.BackBtn);
>>>>>>> Stashed changes
        EditText otpText = findViewById(R.id.otpText);


        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputOTP = otpText.getText().toString();

                if (inputOTP.equals(hiddenOTP)) {

<<<<<<< Updated upstream
                    doRegister(emailBuffer, pwdBuffer, studentIDBuffer, dobBuffer, phoneBuffer, genderBuffer);
=======
                    doRegister(emailBuffer, pwdBuffer, studentIDBuffer, phoneBuffer, genderBuffer);
>>>>>>> Stashed changes
                }
                else
                {
                    TextView OTPDev = findViewById(R.id.er);
                    OTPDev.setText("incorrect: " + hiddenOTP + " " + genderBuffer + " " + dobString);
                }
            }
        });

<<<<<<< Updated upstream
=======
        BackBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OTPActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
>>>>>>> Stashed changes
    }


    public void sendOTP(String email)
    {
        // Generate OTP
        Random rand = new Random();
        int otp = rand.nextInt(1255650);
        String otpString = String.valueOf(otp);

        hiddenOTP = otpString;

        System.out.println("Generated: " + hiddenOTP);
        System.out.println("Sending to: " + email);
        // Call the sendMess method
        sendMess(email, otpString);
    }
    void sendMess(String mail, String otp)
    {
        ApiClient apiClient = new ApiClient();
        apiClient.getApiService().SendOTP(new OTPRequestModel(mail, otp))
                .enqueue(new Callback<OTPResponseModel>() {
                    @Override
                    public void onResponse(Call<OTPResponseModel> call, Response<OTPResponseModel> response) {
                        OTPResponseModel model = response.body();
                        if(model.status == 200)
                        {
<<<<<<< Updated upstream
                            Intent intent = new Intent(OTPActivity.this, LoginActivity.class);
                            startActivity(intent);
=======
                            TextView OTPDev = findViewById(R.id.er);
                            OTPDev.setText("Sent a new OTP, try it");
>>>>>>> Stashed changes
                        }
                        else
                        {
                            TextView emailError = findViewById(R.id.er);
                            emailError.setText(model.message);
                        }
                    }
                    @Override
                    public void onFailure(Call<OTPResponseModel> call, Throwable throwable) {
                        TextView emailError = findViewById(R.id.er);
                        emailError.setText("API Error");
                    }
                });
    }


<<<<<<< Updated upstream
    public void doRegister(String emailInput, String passwordInput, String studentID, Date dob, String phone, boolean gender)
    {
        TextView OTPDev = findViewById(R.id.er);

        RegisterRequestModel newUser = new RegisterRequestModel(emailInput, passwordInput, studentID, dob, phone, gender);
=======
    public void doRegister(String emailInput, String passwordInput, String studentID, String phone, boolean gender)
    {
        TextView OTPDev = findViewById(R.id.er);

        RegisterRequestModel newUser = new RegisterRequestModel(emailInput, passwordInput, studentID, phone, gender);
>>>>>>> Stashed changes

        ApiClient apiClient = new ApiClient();
        apiClient.getApiService().RegisterUser(newUser)
                .enqueue(new Callback<RegisterResponseModel>() {
                    @Override
                    public void onResponse(Call<RegisterResponseModel> call, Response<RegisterResponseModel> response) {
                        RegisterResponseModel model = response.body();
                        if(model.status == 200)
                        {
<<<<<<< Updated upstream
                            OTPDev.setText("success: ");
                            Intent intent = new Intent(OTPActivity.this, LoginActivity.class);
=======
                            OTPDev.setText("success!!!");
                            Intent intent = new Intent(OTPActivity.this, LoginActivity.class);
                            intent.putExtra("Notif", "Register successfully");
>>>>>>> Stashed changes
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            OTPDev.setText(model.message);
                        }
                    }
                    @Override
                    public void onFailure(Call<RegisterResponseModel> call, Throwable throwable) {
                        TextView emailError = findViewById(R.id.er);
                        emailError.setText("API Error");
                    }
                });


    }
}