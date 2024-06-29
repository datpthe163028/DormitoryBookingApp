package Adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingandr.R;

public class RoomTypeAdapterHolder extends RecyclerView.ViewHolder {
    public TextView t1;
    public ImageView t2;
    public Button t3;
    public RoomTypeAdapterHolder(@NonNull View itemView) {
        super(itemView);
        t1 = itemView.findViewById(R.id.dat123);
        t2 = itemView.findViewById(R.id.imageViewDat);
        t3 = itemView.findViewById(R.id.buttonDat);
    }
}
