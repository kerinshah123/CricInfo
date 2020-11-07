package com.example.criinfo.Match;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.criinfo.Home.Matchpojo;
import com.example.criinfo.Info.Player;
import com.example.criinfo.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.annotation.MatchesPattern;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Matchpojo> playerArrayList;
    String flagone,flagtwo;


    public MatchAdapter(Context context, ArrayList<Matchpojo> playerArrayList) {
        this.context = context;
        this.playerArrayList = playerArrayList;
    }

    @NonNull
    @Override
    public MatchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.match_list,parent,false);
        return new MatchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchAdapter.ViewHolder holder, int position) {
        Matchpojo matches = playerArrayList.get(position);

        flagone = getFlag(matches.getTeam1());
        flagtwo = getFlag(matches.getTeam2());

        Picasso.get().load(flagone).into(holder.teamone);
        Picasso.get().load(flagtwo).into(holder.teamtwo);

        holder.team1.setText(matches.getTeam1());
        holder.team2.setText(matches.getTeam2());
        holder.matchdescription.setText(matches.getMatchdescription());
        holder.typeofmatch.setText(matches.getTypeofmatch());
       // holder.toss.setText(matches.getToss());

    }

    @Override
    public int getItemCount() {
        return playerArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView  typeofmatch, matchdescription, team1, team2;
        ImageView teamone,teamtwo;


        public ViewHolder(View view) {
            super(view);
            team1 = view.findViewById(R.id.team1);
            team2 = view.findViewById(R.id.team2);
            typeofmatch = view.findViewById(R.id.typeofmatch);
            matchdescription = view.findViewById(R.id.matchdescription);
            teamone=view.findViewById(R.id.team_one_image);
            teamtwo=view.findViewById(R.id.team_two_image);

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
            return "https://logos.co/1024/royalty-free-cricket-batsman-over-green-square-background-logo-by-patrimonio-2857.jpg";
        }

    }
}

