package com.example.criinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class aboutusAdapter extends RecyclerView.Adapter<aboutusAdapter.ViewHolder>{
Context context;
    List<pojoaboutus> ar1;

    public aboutusAdapter(Context context, List<pojoaboutus> ar1) {
    this.context=context;
    this.ar1=ar1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.itemsaboutus, parent, false);
       ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        pojoaboutus myListData = ar1.get(position);

        holder.aboutiimgs.setImageResource(myListData.getImages());


    }

    @Override
    public int getItemCount() {
        return ar1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
ImageView aboutiimgs;
LinearLayout abtimglayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            aboutiimgs=itemView.findViewById(R.id.aboutiimgs);
            abtimglayout=itemView.findViewById(R.id.abtimglayout);

        }
    }
}
