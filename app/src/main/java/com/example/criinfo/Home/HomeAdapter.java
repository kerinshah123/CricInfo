package com.example.criinfo.Home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.criinfo.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    Context context;
    List<Matchpojo> listdata;
    String flagone,flagtwo;


    public HomeAdapter(Context context, List<Matchpojo> listdata) {
        this.context = context;
        this.listdata = listdata;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.items, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final Matchpojo myListData = listdata.get(position);


        flagone = getFlag(myListData.getTeam1());
        flagtwo = getFlag(myListData.getTeam2());

        Picasso.get().load(flagone).into(holder.teamfirst);
        Picasso.get().load(flagtwo).into(holder.teamsecond);



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
                Intent intent = new Intent(context, MatchDetails.class);
                intent.putExtra("matchid", holder.matchid.getText().toString());
           /*
                intent.putExtra("teamone", flagone);
                intent.putExtra("teamtwo", flagtwo);


            */

                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView series, typeofmatch, matchdescription, venue, result, team1, team2, score1, score2, matchid;
        LinearLayout itemlayout;
        ImageView teamfirst,teamsecond;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            series = itemView.findViewById(R.id.series);
            typeofmatch = itemView.findViewById(R.id.typeofmatch);
            matchdescription = itemView.findViewById(R.id.matchdescription);
            venue = itemView.findViewById(R.id.venue);
            result = itemView.findViewById(R.id.result);
            team1 = itemView.findViewById(R.id.team1);
            team2 = itemView.findViewById(R.id.team2);
            score1 = itemView.findViewById(R.id.score1);
            score2 = itemView.findViewById(R.id.score2);
            matchid = itemView.findViewById(R.id.matchid);
            itemlayout = itemView.findViewById(R.id.itemlayout);
            teamfirst=itemView.findViewById(R.id.teamone);
            teamsecond=itemView.findViewById(R.id.teamtwo);

        }
    }

    public String getFlag(String country)
    {
        if(country.equalsIgnoreCase("Pakistan"))
        {return "https://flagcdn.com/w160/pk.png";
        }
        else if (country.equalsIgnoreCase("India"))
        {
            return "https://flagcdn.com/w160/in.png";
        }
        else if (country.equalsIgnoreCase("Australia"))
        {
            return "https://flagcdn.com/w160/au.png";
        }
        else if (country.equalsIgnoreCase("New Zealand"))
        {
            return "https://flagcdn.com/w160/nz.png";
        }
        else if (country.equalsIgnoreCase("Zimbabwe"))
        {
            return "https://flagcdn.com/w160/zw.png";
        }
        else if (country.equalsIgnoreCase("England"))
        {
            return "https://flagcdn.com/w160/gb-eng.png";
        }
        else if (country.equalsIgnoreCase("Afghanistan"))
        {
            return "https://flagcdn.com/w160/af.png";
        }
        else if (country.equalsIgnoreCase("Sri Lanka"))
        {
            return "https://flagcdn.com/w160/lk.png";
        }
        else if (country.equalsIgnoreCase("Bangladesh"))
        {
            return "https://flagcdn.com/w160/bd.png";
        }
        else if (country.equalsIgnoreCase("Sunrisers Hyderabad"))
        {
            return "https://png.pngitem.com/pimgs/s/127-1270226_srh-logo-ipl-2018-png-download-ipl-team.png";
        }
        else if (country.equalsIgnoreCase("Royal Challengers Bangalore"))
        {
            return "https://png.pngitem.com/pimgs/s/124-1245124_australia-s-world-cup-player-of-the-tournament.png";
        }
        else if (country.equalsIgnoreCase("Mumbai Indians"))
        {
            return "https://png.pngitem.com/pimgs/s/124-1245168_mumbai-indians-logo-png-image-free-download-searchpng.png";
        }
        else if (country.equalsIgnoreCase("Rajasthan Royals"))
        {
            return "https://png.pngitem.com/pimgs/s/160-1605910_rajasthan-royals-logo-vector-rajasthanroyals-hd-png-download.png";
        }

        else if (country.equalsIgnoreCase("Kings Xi Punjab"))
        {
            return "https://png.pngitem.com/pimgs/s/191-1919662_kings-xi-punjab-logos-png-download-kings-xi.png";
        }
        else if (country.equalsIgnoreCase("Delhi Capitals"))
        {
            return "https://png.pngitem.com/pimgs/s/127-1270122_ipl-2018-auctions-are-taking-palce-at-delhi.png";
        }
        else if (country.equalsIgnoreCase("Kolkata kNight Riders"))
        {
            return "https://png.pngitem.com/pimgs/s/332-3321485_kolkata-knight-riders-logo-ipl-kolkata-knight-riders.png";
        }
        else if (country.equalsIgnoreCase("Chennai Super Kings"))
        {
            return "https://png.pngitem.com/pimgs/s/127-1270691_chennai-super-kings-logo-png-image-free-download.png";
        }

        else{
            return "https://logos.co/1024/royalty-free-cricket-batsman-over-green-square-background-logo-by-patrimonio-2857.jpg";    }

    }


}
