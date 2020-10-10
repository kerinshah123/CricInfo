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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.criinfo.R;
import com.example.criinfo.Utils.Utils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.mikhaellopez.circularimageview.CircularImageView;

public class TeamsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences ;
    FirestoreRecyclerAdapter adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);
        recyclerView = findViewById(R.id.teamRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sharedPreferences = getApplicationContext().getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final Query query = FirebaseFirestore.getInstance()
                .collection("teams");
        FirestoreRecyclerOptions<Team> options = new FirestoreRecyclerOptions.Builder<Team>()
                .setQuery(query, Team.class)
                .build();

        adapter  = new FirestoreRecyclerAdapter<Team, TeamHolder>(options) {
            @Override
            public void onBindViewHolder(TeamHolder holder, final int position, final Team model) {
                Glide.with(getApplicationContext()).load(model.getImage())
                        .placeholder(R.drawable.logo)
                        .into(holder.image);
                holder.name.setText(model.getName());
                holder.team.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("teamId",getSnapshots().getSnapshot(position).getId());
                        editor.commit();
                        startActivity(new Intent(getApplicationContext(),MyTeamInfo.class));
                    }
                });

            }

            @Override
            public TeamHolder onCreateViewHolder(ViewGroup group, int i) {
                // Using a custom layout called R.layout.message for each item, we create a new instance of the viewholder
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.team_list, group, false);
                return new TeamHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);
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
        LinearLayout team;
        public TeamHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.team_name);
            image = itemView.findViewById(R.id.team_img);
            team = itemView.findViewById(R.id.team);
        }
    }
}