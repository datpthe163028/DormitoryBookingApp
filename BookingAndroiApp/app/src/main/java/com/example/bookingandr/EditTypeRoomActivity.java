package com.example.bookingandr;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;

import Api.ApiClient;
import Api.ApiService;
import model.GetListTypeRoomResponseModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTypeRoomActivity extends AppCompatActivity {

    private EditText etCapacity;
    private EditText etPrice;
    private ImageView ivSelectedImage;
    private Button btnSelectImage;
    private Button btnSubmit;

    private Uri selectedImageUri;

    private static final int REQUEST_IMAGE_PICK = 1;
    private static final int PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 100;
    private int roomTypeId;

    private static final String TAG = "EditTypeRoomActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_typeroom); // Ensure you have set the correct layout file

        etCapacity = findViewById(R.id.et_capacity);
        etPrice = findViewById(R.id.et_price);
        ivSelectedImage = findViewById(R.id.iv_selected_image);
        btnSelectImage = findViewById(R.id.btn_select_image);
        btnSubmit = findViewById(R.id.btn_submit);
        ImageView back = findViewById(R.id.back_icon);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTypeRoomActivity.this, AdminDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        roomTypeId = getIntent().getIntExtra("roomTypeId", -1);

        if (roomTypeId != -1) {
            fetchRoomTypeDetails(roomTypeId);
        } else {
            Toast.makeText(this, "Invalid Room Type ID", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndRequestPermissions();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRoomType();
            }
        });
    }

    private void fetchRoomTypeDetails(int id) {
        ApiClient apiClient = new ApiClient();
        ApiService apiService = apiClient.getApiService();
        Call<GetListTypeRoomResponseModel> call = apiService.getRoomTypeById(id);

        call.enqueue(new Callback<GetListTypeRoomResponseModel>() {
            @Override
            public void onResponse(Call<GetListTypeRoomResponseModel> call, Response<GetListTypeRoomResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GetListTypeRoomResponseModel roomType = response.body();
                    etCapacity.setText(String.valueOf(roomType.getCapacity()));
                    etPrice.setText(String.valueOf(roomType.getPrice()));
                    Glide.with(EditTypeRoomActivity.this).load(roomType.getImageUrl()).into(ivSelectedImage);
                } else {
                    Toast.makeText(EditTypeRoomActivity.this, "Failed to load room type details", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<GetListTypeRoomResponseModel> call, Throwable t) {
                Toast.makeText(EditTypeRoomActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            openImagePicker();
        }
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            ivSelectedImage.setImageURI(selectedImageUri);
        }
    }

    private void submitRoomType() {
        String capacityStr = etCapacity.getText().toString();
        String priceStr = etPrice.getText().toString();

        if (capacityStr.isEmpty() || priceStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int capacity = Integer.parseInt(capacityStr);
        double price = Double.parseDouble(priceStr);

        ApiClient apiClient = new ApiClient();
        ApiService apiService = apiClient.getApiService();

        RequestBody capacityBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(capacity));
        RequestBody priceBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(price));
        MultipartBody.Part imagePart = null;

        if (selectedImageUri != null) {
            File file = new File(getRealPathFromURI(selectedImageUri));
            if (file.exists()) {
                RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"), file);
                imagePart = MultipartBody.Part.createFormData("ImageUrl", file.getName(), imageBody);
            } else {
                Toast.makeText(this, "Selected image file not found", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Call<Void> call = apiService.editRoomType(roomTypeId, capacityBody, priceBody, imagePart);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditTypeRoomActivity.this, "Room type updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    try {
                        Log.d("EditTypeRoomActivity", "Failed to update room type: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(EditTypeRoomActivity.this, "Failed to update room type", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("EditTypeRoomActivity", "An error occurred: " + t.getMessage());
                Toast.makeText(EditTypeRoomActivity.this, "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return filePath;
        }
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImagePicker();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
