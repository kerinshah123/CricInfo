package com.example.criinfo.More;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.criinfo.Info.Player;
import com.example.criinfo.More.MyTeamTabs.MyTeamPlayer;
import com.example.criinfo.More.MyTeamTabs.Players;
import com.example.criinfo.Utils.Utils;
import com.example.criinfo.PojoPlayer;

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

import com.example.criinfo.Playerlistadapter;
import com.example.criinfo.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TeamPlayers extends AppCompatActivity {

    RecyclerView recyclerView;
    Button addPlayer;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences ;
    String teamId,usetId;
    FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_players);
        recyclerView=findViewById(R.id.playerRecycler);
        addPlayer = findViewById(R.id.addLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        sharedPreferences = getApplicationContext().getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        usetId = sharedPreferences.getString("userId","");
        teamId = sharedPreferences.getString("teamId","");

        final Query query = FirebaseFirestore.getInstance()
                .collection("players");

        FirestoreRecyclerOptions<Players> options = new FirestoreRecyclerOptions.Builder<Players>()
                .setQuery(query, Players.class)
                .build();

        adapter  = new FirestoreRecyclerAdapter<Players, PlayerHolder>(options) {
            @Override
            public void onBindViewHolder(final PlayerHolder holder, final int position, final Players model) {


                    Glide.with(getApplicationContext()).load(model.getImage())
                            .placeholder(R.drawable.profile)
                            .into(holder.image);
                    holder.name.setText(model.getName());

                    holder.team.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           if(model.getTeamId().contains(teamId)){
                               Toast.makeText(TeamPlayers.this, "Player Already In Team", Toast.LENGTH_SHORT).show();
                           }
                           else {
                               addToSquard(getSnapshots().getSnapshot(position).getId().toString());
                           }
                        }
                    });



            }

            @Override
            public PlayerHolder onCreateViewHolder(ViewGroup group, int i) {
                // Using a custom layout called R.layout.message for each item, we create a new instance of the viewholder
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.team_list, group, false);
                return new PlayerHolder(view);
            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

    }

    private void addToSquard(final String toString) {
        db.collection("players").document(toString)
                .update("teamId", FieldValue.arrayUnion(teamId))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Player Addes Successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TeamPlayers.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private class PlayerHolder extends RecyclerView.ViewHolder {
        TextView name;
        CircularImageView image;
        LinearLayout team;
        public PlayerHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.team_name);
            image = itemView.findViewById(R.id.team_img);
            team = itemView.findViewById(R.id.team);
        }
    }


    public void createPlayer(View view) {
        startActivity(new Intent(getApplicationContext(),AddPlayer.class));
    }
}