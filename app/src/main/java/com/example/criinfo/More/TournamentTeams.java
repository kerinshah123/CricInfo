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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.criinfo.More.MyTeamTabs.Players;
import com.example.criinfo.More.MyTournamentTabs.Team;
import com.example.criinfo.R;
import com.example.criinfo.Utils.Utils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.HashMap;
import java.util.Map;

public class TournamentTeams extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayout createTeam;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences;
    String tournamentId, usetId,teamId;
    FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_teams);

        recyclerView = findViewById(R.id.all_team_recycler);
        createTeam = findViewById(R.id.create_team);

        sharedPreferences = getApplicationContext().getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        usetId = sharedPreferences.getString("userId", "");
        tournamentId = sharedPreferences.getString("tournamentId", "");


        final Query query1 = FirebaseFirestore.getInstance()
                .collection("teams").whereEqualTo("managerId", sharedPreferences.getString("userId", ""));

        query1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.getResult().isEmpty()) {
                    createTeam.setVisibility(View.VISIBLE);
                } else {
                    createTeam.setVisibility(View.GONE);

                }
            }
        });


        final Query query = FirebaseFirestore.getInstance()
                .collection("teams");

        FirestoreRecyclerOptions<Team> options = new FirestoreRecyclerOptions.Builder<Team>()
                .setQuery(query, Team.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Team, TeamHolder>(options) {
            @Override
            public void onBindViewHolder(final TeamHolder holder, final int position, final Team model) {


                Glide.with(getApplicationContext()).load(model.getImage())
                        .placeholder(R.drawable.profile)
                        .into(holder.image);
                holder.name.setText(model.getName());

                holder.team.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (model.getLeagueId().contains(tournamentId)) {
                            Toast.makeText(getApplicationContext(), "Team Already in League", Toast.LENGTH_SHORT).show();
                        } else {
                            teamId = getSnapshots().getSnapshot(position).getId().toString();
                            addToTournament(getSnapshots().getSnapshot(position).getId().toString());
                        }
                    }
                });


            }

            @Override
            public TeamHolder onCreateViewHolder(ViewGroup group, int i) {
                // Using a custom layout called R.layout.message for each item, we create a new instance of the viewholder
                View view = LayoutInflater.from(group.getContext()).inflate(R.layout.team_list, group, false);
                return new TeamHolder(view);
            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);


    }

    private void addToTournament(String toString) {
        db.collection("teams").document(toString)
                .update("leagueId", FieldValue.arrayUnion(tournamentId))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Team Added Successfully", Toast.LENGTH_SHORT).show();
                        Map<String, Object> pointstable = new HashMap<>();
                        pointstable.put("LeagueId",tournamentId);
                        pointstable.put("teamId",teamId);
                        pointstable.put("point",0);
                        pointstable.put("matchPlayed",0);
                        pointstable.put("matchwin",0);
                        pointstable.put("matchloss",0);


                        db.collection("pointstable")
                                .add(pointstable)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(TournamentTeams.this, "Team Added To pointstable", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(TournamentTeams.this, "Some Error", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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

    public void createTeam(View view) {
        startActivity(new Intent(getApplicationContext(), CreateTeam.class));
    }

    private class TeamHolder extends RecyclerView.ViewHolder {
        TextView name;
        CircularImageView image;
        LinearLayout team;

        public TeamHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.team_name);
            image = itemView.findViewById(R.id.team_img);
            team = itemView.findViewById(R.id.team);
        }
    }
}