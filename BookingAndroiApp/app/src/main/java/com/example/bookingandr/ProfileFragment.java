package com.example.bookingandr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import Api.ApiClient;
import model.UserDetailResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    private TextView textViewUserId, textViewStudentID, textViewPhone, textViewGender, textViewCurrentRoomID, textViewBalance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        // Take ID for it
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserInformation", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("UserId", null);
        takeDetail(userId, rootView);
        // Find the "Logout" button and set an OnClickListener
        Button logoutButton = rootView.findViewById(R.id.lgOut);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear shared preferences
                SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserInformation", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                // Navigate back to LoginActivity
                Intent intent = new Intent(requireContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                requireActivity().finish();
            }
        });
        return rootView;
    }

    void takeDetail(String userID, View rootView) {
        // Initialize UI elements
        textViewUserId = rootView.findViewById(R.id.textViewUserId);
        textViewStudentID = rootView.findViewById(R.id.textViewStudentID);
        textViewPhone = rootView.findViewById(R.id.textViewPhone);
        textViewGender = rootView.findViewById(R.id.textViewGender);
        textViewCurrentRoomID = rootView.findViewById(R.id.textViewCurrentRoomID);
        textViewBalance = rootView.findViewById(R.id.textViewBalance);

        ApiClient apiClient = new ApiClient();
        apiClient.getApiService().userDetail(userID)
                .enqueue(new Callback<UserDetailResponseModel>() {
                    @Override
                    public void onResponse(Call<UserDetailResponseModel> call, Response<UserDetailResponseModel> response) {
                        UserDetailResponseModel model = response.body();
                        if (model.status == 200) {
                            UserDetailResponseModel userDetail = response.body();
                            // Populate the UI with user details
                            textViewUserId.setText("User ID: " + userID);
                            textViewStudentID.setText("Student ID: " + userDetail.data.studentID);
                            textViewPhone.setText("Phone: " + userDetail.data.phone);
                            textViewGender.setText("Gender: " + (userDetail.data.gender ? "Male" : "Female"));
                            textViewCurrentRoomID.setText("Current Room ID: " + userDetail.data.currentRoomID);
                            textViewBalance.setText("Balance: " + userDetail.data.balance);
                        } else {
                            // Handle API response status other than 200
                        }
                    }

                    @Override
                    public void onFailure(Call<UserDetailResponseModel> call, Throwable t) {
                        TextView emailError = rootView.findViewById(R.id.er);
                        emailError.setText("API Error");
                    }
                });
    }

}