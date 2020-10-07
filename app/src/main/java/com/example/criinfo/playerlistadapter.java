package com.example.criinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class playerlistadapter extends RecyclerView.Adapter<playerlistadapter.ViewHolder> {

    Context context;
    List<pojoplayer> ar1;

    public playerlistadapter(Context context, List<pojoplayer> ar1) {
        this.context=context;
        this.ar1=ar1;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.itemsplayers, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        pojoplayer myListData = ar1.get(position);

        holder.playername.setText(myListData.getPlayername());
        holder.playerage.setText(myListData.getPlayerage());
        holder.playertype.setText(myListData.getPlayertype());
        holder.playerimg.setImageResource(myListData.getPlayerimage());



    }

    @Override
    public int getItemCount() {
        return ar1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

TextView playername,playerage,playertype;
ImageView playerimg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            playerimg=itemView.findViewById(R.id.playerpics);
            playername=itemView.findViewById(R.id.playernm);
            playerage=itemView.findViewById(R.id.playerag);
            playertype=itemView.findViewById(R.id.playertyp);
        }
    }
}
