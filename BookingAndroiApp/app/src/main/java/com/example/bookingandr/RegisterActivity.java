package com.example.bookingandr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
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

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import Api.ApiClient;
import model.LoginRequestModel;
import model.LoginResponseModel;
import model.OTPRequestModel;
import model.OTPResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity
{

    public String hiddenOTP;
    public String emailBuffer;
    public String pwdBuffer;
    public String StudentIDBuffer;
    public String phoneBuffer;
    public Date DobBuffer;
    public boolean genderBuffer;
    public String dobInput;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Button registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int check = 0;
                TextView emailError = findViewById(R.id.er);

                EditText email = findViewById(R.id.emailText);
                EditText password = findViewById(R.id.passwordText);
                EditText studentID = findViewById(R.id.StudentIDText);
                EditText phone = findViewById(R.id.PhoneText);
                EditText dob = findViewById(R.id.DOBText);
                RadioGroup gender = findViewById(R.id.genderRadioGroup);

                emailBuffer = email.getText().toString();
                pwdBuffer = password.getText().toString();
                StudentIDBuffer = studentID.getText().toString();
                phoneBuffer = phone.getText().toString();
                genderBuffer = gender.getCheckedRadioButtonId() == R.id.maleRadioButton;

                if (emailBuffer.isEmpty() || !emailBuffer.contains("@")) {
                    check = 1;
                    emailError.setText("Enter email");
                }
                if (pwdBuffer.isEmpty()) {
                    check = 1;
                    emailError.setText("Enter password");
                }
                if (StudentIDBuffer.isEmpty()) {
                    check = 1;
                    emailError.setText("Enter Student ID");
                }
                if (phoneBuffer.isEmpty()) {
                    check = 1;
                    emailError.setText("Enter phone");
                }

                dobInput = dob.getText().toString();


                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    DobBuffer = dateFormat.parse(dobInput);
                    if (!dobInput.equals(dateFormat.format(DobBuffer))) {
                        check = 1;
                        emailError.setText("Enter date of birth in format dd/MM/yyyy");
                    }
                } catch (ParseException e) {
                    check = 1;
                    emailError.setText("Enter date of birth in format dd/MM/yyyy");
                }


                if(check == 0)
                {
                    sendOTP(emailBuffer);
                }

            }
        });

    }


    public void sendOTP(String email)
    {
        // Generate OTP
        Random rand = new Random();
        int otp = rand.nextInt(1255650);
        String otpString = String.valueOf(otp);

        hiddenOTP = otpString;

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
                            Intent intent = new Intent(RegisterActivity.this, OTPActivity.class);
                            intent.putExtra("email", emailBuffer);
                            intent.putExtra("password", pwdBuffer);
                            intent.putExtra("StudentID", StudentIDBuffer);
                            intent.putExtra("Phone", phoneBuffer);
                            intent.putExtra("Gender", String.valueOf(genderBuffer));

                            intent.putExtra("hiddenOTP", hiddenOTP);
                            intent.putExtra("DOB", dobInput);
                            startActivity(intent);
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

}