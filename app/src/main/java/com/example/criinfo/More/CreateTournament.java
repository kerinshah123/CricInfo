package com.example.criinfo.More;

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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.criinfo.R;
import com.example.criinfo.Utils.Utils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.mikhaellopez.circularimageview.CircularImageView;

public class CreateTournament extends AppCompatActivity {

    TextView noteam;
    SharedPreferences sharedPreferences ;
    FirestoreRecyclerAdapter adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView recyclerView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tournament);
        noteam = findViewById(R.id.noteamLayout);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = findViewById(R.id.progress);


        sharedPreferences = getApplicationContext().getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final Query query = FirebaseFirestore.getInstance()
                .collection("tournaments").whereEqualTo("leaguemanager",sharedPreferences.getString("userId",""));



        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.getResult().isEmpty()){
                    progressBar.setVisibility(View.GONE);
                    noteam.setVisibility(View.VISIBLE);
                }
                else {

                    progressBar.setVisibility(View.GONE);
                    noteam.setVisibility(View.GONE);
                }
            }
        });

        FirestoreRecyclerOptions<Tournament> options = new FirestoreRecyclerOptions.Builder<Tournament>()
                .setQuery(query, Tournament.class)
                .build();




        adapter  = new FirestoreRecyclerAdapter<Tournament, CreateTournament.TeamHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull TeamHolder holder, final int position, @NonNull Tournament model) {
                Glide.with(getApplicationContext()).load(model.getImage())
                        .placeholder(R.drawable.team)
                        .into(holder.image);
                holder.name.setText(model.getTournament());
                holder.tournament.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("tournamentId",getSnapshots().getSnapshot(position).getId());
                        editor.commit();
                        startActivity(new Intent(getApplicationContext(),MyTeamInfo.class));
                    }
                });

            }



            @Override
            public CreateTournament.TeamHolder onCreateViewHolder(ViewGroup group, int i) {
                // Using a custom layout called R.layout.message for each item, we create a new instance of the viewholder
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.mytournament_list, group, false);
                return new CreateTournament.TeamHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);


    }

    public void createtournament(View view) {
        Intent intent=new Intent(getApplicationContext(), TournamentRegistration.class);
        startActivity(intent);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }@Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private class TeamHolder extends RecyclerView.ViewHolder {
        TextView name;
        CircularImageView image;
        LinearLayout tournament;
        public TeamHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tournament_name);
            image = itemView.findViewById(R.id.tournament_img);
            tournament = itemView.findViewById(R.id.tournament);
        }
    }
}