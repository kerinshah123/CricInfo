package com.example.criinfo.Home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.criinfo.R;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    Context context;
    List<Matchpojo>  listdata;


    public HomeAdapter(Context context, List<Matchpojo> listdata) {
        this.context=context;
        this.listdata=listdata;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout. items, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final Matchpojo myListData = listdata.get(position);

        holder.series.setText(myListData.getSeries());
        holder.typeofmatch.setText(myListData.getTypeofmatch());
        holder.matchdescription.setText(myListData.matchdescription);
        holder.venue.setText(myListData.getVenue());
        holder.result.setText(myListData.getResult());
        holder.team1.setText(myListData.getTeam1());
        holder.team2.setText(myListData.getTeam2());
        holder.score1.setText(myListData.getScore1());
        holder.score2.setText(myListData.getScore2());
        holder.matchid.setText(myListData.getMatchid());

        holder.itemlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Intent intent=new Intent(context, MatchDetails.class);
intent.putExtra("matchid",holder.matchid.getText().toString());
context.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView series,typeofmatch,matchdescription,venue,result,team1,team2,score1,score2,matchid;
        LinearLayout itemlayout;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            series=itemView.findViewById(R.id.series);
            typeofmatch=itemView.findViewById(R.id.typeofmatch);
            matchdescription=itemView.findViewById(R.id.matchdescription);
            venue=itemView.findViewById(R.id.venue);
            result=itemView.findViewById(R.id.result);
            team1=itemView.findViewById(R.id.team1);
            team2=itemView.findViewById(R.id.team2);
            score1=itemView.findViewById(R.id.score1);
            score2=itemView.findViewById(R.id.score2);
            matchid=itemView.findViewById(R.id.matchid);
            itemlayout=itemView.findViewById(R.id.itemlayout);

        }
    }
}
