package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingandr.R;

import java.util.List;

import model.BookingHistoryModel;
import com.bumptech.glide.Glide;
public class BookingHistoryAdapter  extends  RecyclerView.Adapter<BookingHistoryAdapter.BookingHistoryViewHolder>{

    private List<BookingHistoryModel> bhlist;
    private Context context;
    public BookingHistoryAdapter(Context context,List<BookingHistoryModel> bhlist) {
        this.context = context;
        this.bhlist = bhlist;

    }

    @NonNull
    @Override
    public BookingHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bookinghistory_items,parent,false);
        return new BookingHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingHistoryViewHolder holder, int position) {
     BookingHistoryModel bh = bhlist.get(position);
     if(bh == null)
         return;
     holder.tv1.setText("Số Phòng "+bh.getRoomName());
        Glide.with(context)
                .load(bhlist.get(position).getImageUrl())
                .into(holder.img);
        holder.tv6.setText("Đã đăng ký phòng vào kỳ "+bh.getSemesterName());
        holder.tv2.setText("Loại phòng "+bh.getCapacity()+" người");
        holder.tv3.setText("Mức giá "+bh.getPrice()+"đ/tháng");
        holder.tv4.setText("Tòa Nhà "+bh.getBuildingName());

    }

    @Override
    public int getItemCount() {
        return bhlist.size();
    }

    public class BookingHistoryViewHolder extends RecyclerView.ViewHolder{
     private ImageView img;
     private TextView tv1,tv2,tv3,tv4,tv5,tv6;
        public BookingHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            tv1 = itemView.findViewById(R.id.roomName);
            tv2 = itemView.findViewById(R.id.roomType);
            tv3 = itemView.findViewById(R.id.price);
            tv4 = itemView.findViewById(R.id.buildingName);
            tv6 = itemView.findViewById(R.id.semesterName);

        }
    }
}
