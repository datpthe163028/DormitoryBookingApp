package Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingandr.NewsDetailActivity;
import com.example.bookingandr.R;
import com.example.bookingandr.UpdateNewsActivity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import Api.ApiClient;
import Api.ApiService;
import model.NewsModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsForAdminAdapter extends  RecyclerView.Adapter<NewsForAdminAdapter.NewsViewHolder> {

    private Context context;
    private List<NewsModel> newslist;


    public NewsForAdminAdapter( Context context,List<NewsModel> newslist) {
        this.context = context;
        this.newslist = newslist;

    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.crud_news_item,parent,false);
        return new NewsForAdminAdapter.NewsViewHolder(view);
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
        holder.tv3.setText(nl.getContent());
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Example: Start RentRoomActivity with an intent
//                Intent intent = new Intent(context, UpdateNewsActivity.class);
//                intent.putExtra("newsId", nl.getId()); // Pass any necessary data to the activity
//                context.startActivity(intent);
//            }
//        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start UpdateNewsActivity with an intent
                Intent intent = new Intent(context, UpdateNewsActivity.class);
                intent.putExtra("newsId", nl.getId()); // Pass the news ID to the activity
                context.startActivity(intent);
            }
        });

        // Handle delete button click
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog(nl.getId());
            }
        });

    }
    private void showDeleteConfirmationDialog(int newsId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirm Delete");
        builder.setMessage("Are you sure you want to delete this news item?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteNewsItem(newsId);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void deleteNewsItem(int newsId) {
        ApiService apiService = new ApiClient().getApiService();
        Call<Void> call = apiService.deleteNews(newsId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Remove the item from the list and notify the adapter
                    removeNewsItem(newsId);
                    Toast.makeText(context, "News item deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Failed to delete news item", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Error deleting news item: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void removeNewsItem(int newsId) {
        for (NewsModel news : newslist) {
            if (news.getId() == newsId) {
                newslist.remove(news);
                notifyDataSetChanged();
                break;
            }
        }
    }
    @Override
    public int getItemCount() {
        return newslist.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{
        private TextView tv1,tv2,tv3;
        private ImageView editButton, deleteButton;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            tv1 = itemView.findViewById(R.id.tvHeader);
            tv2 = itemView.findViewById(R.id.tvDate);
            tv3 = itemView.findViewById(R.id.tvContent);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}