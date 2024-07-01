package Adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingandr.R;

public class ListRoom2AdapterHolder extends RecyclerView.ViewHolder{
    public Button button1, button2, button3, button4, button5;
    public ListRoom2AdapterHolder(@NonNull View itemView) {
        super(itemView);
        button1 = itemView.findViewById(R.id.abutton1);
        button2 = itemView.findViewById(R.id.abutton2);
        button3 = itemView.findViewById(R.id.abutton3);
        button4 = itemView.findViewById(R.id.abutton4);
        button5 = itemView.findViewById(R.id.abutton5);
    }
}
