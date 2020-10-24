package com.example.criinfo.More.TournamentsTabs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.criinfo.R;
import com.example.criinfo.Utils.Utils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.mikhaellopez.circularimageview.CircularImageView;

public class Tournaments extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences ;
    String teamId,usetId;
    FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournaments);
        recyclerView=findViewById(R.id.tournamentRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        sharedPreferences = getApplicationContext().getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        usetId = sharedPreferences.getString("userId","");
        teamId = sharedPreferences.getString("teamId","");

        final Query query = FirebaseFirestore.getInstance()
                .collection("tournaments");


        FirestoreRecyclerOptions<TournamentsPojo> options = new FirestoreRecyclerOptions.Builder<TournamentsPojo>()
                .setQuery(query, TournamentsPojo.class)
                .build();

        adapter  = new FirestoreRecyclerAdapter<TournamentsPojo, Tournaments.TournamentHolder>(options) {
            @Override
            public void onBindViewHolder(final Tournaments.TournamentHolder holder, final int position, final TournamentsPojo model) {


                Glide.with(getApplicationContext()).load(model.getImage())
                        .placeholder(R.drawable.profile)
                        .into(holder.image);
                holder.name.setText(model.getTournament());

                holder.tournament.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("tournamentId",getSnapshots().getSnapshot(position).getId());
                        editor.commit();
                        startActivity(new Intent(getApplicationContext(), TournamentsInfo.class));

                    }
                });



            }

            @Override
            public Tournaments.TournamentHolder onCreateViewHolder(ViewGroup group, int i) {
                // Using a custom layout called R.layout.message for each item, we create a new instance of the viewholder
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.mytournament_list, group, false);
                return new Tournaments.TournamentHolder(view);
            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private class TournamentHolder extends RecyclerView.ViewHolder {
        TextView name;
        CircularImageView image;
        LinearLayout tournament;
        public TournamentHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tournament_name);
            image = itemView.findViewById(R.id.tournament_img);
            tournament = itemView.findViewById(R.id.tournament);
        }

    }
}