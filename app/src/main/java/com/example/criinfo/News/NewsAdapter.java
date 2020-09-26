package com.example.criinfo.News;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.criinfo.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    Context context;
    List<NewsPojo> ar1;

    public NewsAdapter(Context context,List<NewsPojo> ar1) {

        this.context = context;
        this.ar1=ar1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.itemsnews, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        NewsPojo myListData = ar1.get(position);


        holder.authorname.setText(myListData.getAuthorname());
        holder.newstitle.setText(myListData.getNewstitle());
        holder.newsdate.setText(myListData.getNewsdate());
        holder.newslink.setText(myListData.getNewsurl());
        Picasso.get().load(myListData.getImgurl()).into(holder.imgnws);
        holder.readfullnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = holder.newslink.getText().toString();

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ar1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        ImageView imgnws;
        TextView authorname,newslink,newstitle,newsdate;
        Button readfullnews;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            imgnws=itemView.findViewById(R.id.imgnews);
            authorname=itemView.findViewById(R.id.author);
            newslink=itemView.findViewById(R.id.link);
            newstitle=itemView.findViewById(R.id.news);
            newsdate=itemView.findViewById(R.id.publishedate);
            readfullnews=itemView.findViewById(R.id.readfull);
        }
    }
}
