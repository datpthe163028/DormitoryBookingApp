package com.example.bookingandr;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CrudTypeRoomFragment extends Fragment {

    private Button btnAddTypeRoom;
    private Button btnViewTypeRoom;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crud_type_room, container, false);

        btnAddTypeRoom = view.findViewById(R.id.btn_add_typeroom);

        btnAddTypeRoom.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddTypeRoomActivity.class);
            startActivity(intent);
        });
        btnViewTypeRoom = view.findViewById(R.id.btn_view_typeroom);

        btnViewTypeRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewTypeRoomActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
