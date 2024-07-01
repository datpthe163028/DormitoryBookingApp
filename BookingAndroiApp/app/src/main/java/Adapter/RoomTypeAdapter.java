package Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookingandr.R;

import java.util.List;

import model.GetListTypeRoomResponseModel;

public class RoomTypeAdapter extends RecyclerView.Adapter<RoomTypeAdapterHolder> {
    private Context context;
    private List<GetListTypeRoomResponseModel> list;

    public RoomTypeAdapter(Context context, List<GetListTypeRoomResponseModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RoomTypeAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RoomTypeAdapterHolder(LayoutInflater.from(context).inflate(R.layout.for_list_typeroom, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RoomTypeAdapterHolder holder, int position) {
        holder.t1.setText("Loại phòng " + list.get(position).getCapacity() + "người");
        Glide.with(context)
                .load(list.get(position).getImageUrl())
                .into(holder.t2);

        holder.t3.setId(list.get(position).getId());

    }

    @Override
    public int getItemCount() {
        Log.e("síaaa", list.size() + "");
        return list.size();
    }
}
