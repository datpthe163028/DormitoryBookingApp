package Adapter;

import static android.app.PendingIntent.getActivity;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookingandr.NewsDetailActivity;
import com.example.bookingandr.R;
import com.example.bookingandr.RentRoomActivity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import model.NewsModel;

public class NewsforuserAdapter extends  RecyclerView.Adapter<NewsforuserAdapter.NewsViewHolder> {

    private Context context;
    private List<NewsModel> newslist;


    public NewsforuserAdapter( Context context,List<NewsModel> newslist) {
        this.context = context;
        this.newslist = newslist;

    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item,parent,false);
        return new NewsforuserAdapter.NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsModel nl = newslist.get(position);
        if (nl == null)
            return;
        holder.tv1.setText(nl.getHeader());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String formattedDate = dateFormat.format(nl.getDatePost());
        holder.tv2.setText(formattedDate);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Example: Start RentRoomActivity with an intent
                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra("newsId", nl.getId()); // Pass any necessary data to the activity
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
     return newslist.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{
        private TextView tv1,tv2;
        private CardView cardView;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            tv1 = itemView.findViewById(R.id.tvHeader);
            tv2 = itemView.findViewById(R.id.tvDate);
            cardView = itemView.findViewById(R.id.news_container);
        }
    }
}
