package com.example.bookingandr;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import Api.ApiClient;
import model.DashboardResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashBoardAdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashBoardAdminFragment extends Fragment {

    private TextView txtUsers;
    private TextView txtRooms;
    private TextView txtAvailableRooms;
    private PieChart pieChart;

    public DashBoardAdminFragment() {
        // Required empty public constructor
    }

    public static DashBoardAdminFragment newInstance(String param1, String param2) {
        DashBoardAdminFragment fragment = new DashBoardAdminFragment();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dash_board_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtUsers = view.findViewById(R.id.usersTextView);
        txtRooms = view.findViewById(R.id.roomsTextView);
        txtAvailableRooms = view.findViewById(R.id.availableRoomsTextView);
        pieChart = view.findViewById(R.id.pieChart);
        ApiClient apiClient = new ApiClient();
        apiClient.getApiService().getDashboard().enqueue(new Callback<DashboardResponseModel>() {
            @Override
            public void onResponse(Call<DashboardResponseModel> call, Response<DashboardResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DashboardResponseModel model = response.body();
                    Log.d("status", "Status: " + model.status);
                    Log.d("message", "Message: " + model.message);

                    ArrayList<PieEntry> entries = new ArrayList<>();
                    entries.add(new PieEntry(model.data.availableRooms, "Available Rooms"));
                    entries.add(new PieEntry((model.data.rooms - model.data.availableRooms), "Unavailable Rooms"));

                    PieDataSet dataSet = new PieDataSet(entries, "");
                    dataSet.setColors(ColorTemplate.MATERIAL_COLORS); // set colors for each entry
                    PieData pieData = new PieData(dataSet);
                    pieChart.setData(pieData); // set data

                    pieChart.getDescription().setEnabled(false); // set description

                    pieChart.animateY(1000); // animate the chart
                    pieChart.invalidate(); // refresh chart

                    String users = "Total Users: " + model.data.users;
                    String rooms = "Total Rooms: " + model.data.rooms;
                    String availableRooms = "Available Rooms: " + model.data.availableRooms
                            + "/" + model.data.rooms;
                    txtUsers.setText(users);
                    txtRooms.setText(rooms);
                    txtAvailableRooms.setText(availableRooms);
                } else {
                    Log.e("cant cook this", "Response unsuccessful");
                }
            }

            @Override
            public void onFailure(Call<DashboardResponseModel> call, Throwable throwable) {
                Log.e("engineering failure", throwable.getMessage());
            }
        });
    }
}