package Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookingandr.EditTypeRoomActivity;
import com.example.bookingandr.R;

import java.util.List;

import Api.ApiClient;
import Api.ApiService;
import model.GetListTypeRoomResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;




import android.widget.Toast;

public class AdminRoomTypeAdapter extends RecyclerView.Adapter<AdminRoomTypeAdapter.AdminRoomTypeAdapterHolder> {
    private Context context;
    private List<GetListTypeRoomResponseModel> list;

    public AdminRoomTypeAdapter(Context context, List<GetListTypeRoomResponseModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public AdminRoomTypeAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.for_list_typeroom_admin, parent, false);
        return new AdminRoomTypeAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminRoomTypeAdapterHolder holder, int position) {
        GetListTypeRoomResponseModel roomType = list.get(position);
        holder.t1.setText("Loại phòng " + roomType.getCapacity() + " người");
        Glide.with(context)
                .load(roomType.getImageUrl())
                .into(holder.t2);

        // Set click listeners for the edit and delete buttons
        holder.buttonEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditTypeRoomActivity.class);
            intent.putExtra("roomTypeId", roomType.getId());
            context.startActivity(intent);
        });

        holder.buttonDelete.setOnClickListener(v -> {

                showDeleteConfirmationDialog(roomType.getId());

        });
    }
    private void showDeleteConfirmationDialog(int roomId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirm Delete");
        builder.setMessage("Are you sure you want to delete this room type?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteRoomType(roomId);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
    private void deleteRoomType(int roomId) {
        ApiClient apiClient = new ApiClient();
        ApiService apiService = apiClient.getApiService();

        Call<Void> call = apiService.deleteRoomType(roomId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Room type deleted successfully", Toast.LENGTH_SHORT).show();
                    removeItem(roomId);
                } else {
                    Toast.makeText(context, "Failed to delete room type", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeItem(int roomId) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == roomId) {
                list.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class AdminRoomTypeAdapterHolder extends RecyclerView.ViewHolder {
        public TextView t1;
        public ImageView t2;
        public Button buttonEdit;
        public Button buttonDelete;

        public AdminRoomTypeAdapterHolder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.dat123);
            t2 = itemView.findViewById(R.id.imageViewDat);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
