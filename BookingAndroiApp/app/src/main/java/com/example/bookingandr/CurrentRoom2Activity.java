package com.example.bookingandr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import Api.ApiClient;
import model.BookingRoomResponseModel;
import model.GetListBuilding2ResponseModel;
import model.GetStatusCModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentRoom2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_current_room2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String room = getIntent().getStringExtra("ROOM");
        String building = getIntent().getStringExtra("BUILDING");
        TextView t1 = findViewById(R.id.textView5);
        TextView t2 = findViewById(R.id.textView6);
        t1.setText("Hiện tại bạn đang ở phòng " + room);
        t2.setText(building);


        SharedPreferences sharedPref = this.getSharedPreferences("UserInformation", Context.MODE_PRIVATE);
        String userId = sharedPref.getString("UserId", null);
        ApiClient apiClient = new ApiClient();
        apiClient.getApiService().GetStatusc(userId).enqueue(new Callback<GetStatusCModel>() {
            @Override
            public void onResponse(Call<GetStatusCModel> call, Response<GetStatusCModel> response) {
                GetStatusCModel c = response.body();

                if(c.status.trim().equals("true")){
                    TextView t6 = findViewById(R.id.textView7);
                    t6.setText("");
                }else{
                    Button b = findViewById(R.id.button3);
                    b.setVisibility(View.GONE);

                }

            }

            @Override
            public void onFailure(Call<GetStatusCModel> call, Throwable throwable) {

            }
        });
        Button b = findViewById(R.id.button3);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiClient.getApiService().Derserve(userId).enqueue(new Callback<GetStatusCModel>() {
                    @Override
                    public void onResponse(Call<GetStatusCModel> call, Response<GetStatusCModel> response) {
                        SharedPreferences sharedPref = CurrentRoom2Activity.this.getSharedPreferences("UserInformation", Context.MODE_PRIVATE);
                        String userId = sharedPref.getString("UserId", null);
                        ApiClient apiClient2 = new ApiClient();
                        apiClient2.getApiService().GetCurrentRoom(userId).enqueue(new Callback<BookingRoomResponseModel>() {
                            @Override
                            public void onResponse(Call<BookingRoomResponseModel> call, Response<BookingRoomResponseModel> response) {
                                BookingRoomResponseModel model = response.body();
                                Intent intent = new Intent(CurrentRoom2Activity.this, CurrentRoom2Activity.class);
                                intent.putExtra("ROOM", model.roomName);
                                intent.putExtra("BUILDING", model.buildingName);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onFailure(Call<BookingRoomResponseModel> call, Throwable throwable) {

                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<GetStatusCModel> call, Throwable throwable) {

                    }
                });
            }
        });
        ImageView back = findViewById(R.id.back_icon);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}