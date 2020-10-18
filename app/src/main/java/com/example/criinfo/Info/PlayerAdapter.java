package com.example.criinfo.Info;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.criinfo.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Player> playerArrayList;

    public PlayerAdapter(Context context, ArrayList<Player> playerArrayList) {
        this.context = context;
        this.playerArrayList = playerArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.player_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Player player = playerArrayList.get(position);
        holder.name.setText(player.getName());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return playerArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout layout;
        CircularImageView imageView;
        TextView name, country;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            country = view.findViewById(R.id.country);
            imageView = view.findViewById(R.id.image);
            layout = view.findViewById(R.id.list);

        }
    }
}
