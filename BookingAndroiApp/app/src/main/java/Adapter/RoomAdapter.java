package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingandr.EditRoomActivity;
import com.example.bookingandr.R;

import java.util.List;

import model.RoomModel;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {
    private List<RoomModel> roomList;
    private Context context;
    private Fragment fragment;
    private static final int EDIT_ROOM_REQUEST_CODE = 1;

    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        public TextView roomName;
        public TextView currentPeople;
        public TextView isAvailable;
        public Button editButton;

        public RoomViewHolder(View itemView) {
            super(itemView);
            roomName = itemView.findViewById(R.id.room_name);
            currentPeople = itemView.findViewById(R.id.current_people);
            isAvailable = itemView.findViewById(R.id.is_available);
            editButton = itemView.findViewById(R.id.buttonEdit);
        }
    }

    public RoomAdapter(List<RoomModel> rooms, Context context, Fragment fragment) {
        roomList = rooms;
        this.context = context;
        this.fragment = fragment;
    }

    @Override
    public RoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RoomViewHolder holder, int position) {
        RoomModel currentRoom = roomList.get(position);
        String room = "Room: " + currentRoom.getName();
        String current = "Current people: " + String.valueOf(currentRoom.getCurrentPeople());
        holder.roomName.setText(room);
        holder.currentPeople.setText(current);
        holder.isAvailable.setText(currentRoom.isAvailble() ? "Available" : "Not Available");

//        holder.editButton.setOnClickListener(v -> {
//            Intent intent = new Intent(context, EditRoomActivity.class);
//            intent.putExtra("room", currentRoom);
//            context.startActivity(intent);
//        });
        // In RoomAdapter.java
        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditRoomActivity.class);
            intent.putExtra("room", currentRoom);
            fragment.startActivityForResult(intent, EDIT_ROOM_REQUEST_CODE); // Use Fragment's startActivityForResult
        });

    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }
}
