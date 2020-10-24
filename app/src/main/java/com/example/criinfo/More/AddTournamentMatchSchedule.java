package com.example.criinfo.More;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.criinfo.More.MyTournamentTabs.Team;
import com.example.criinfo.R;
import com.example.criinfo.Utils.Utils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddTournamentMatchSchedule extends AppCompatActivity {

    RecyclerView teamSelection;
    FirestoreRecyclerAdapter SelectionTeamAdapter;
    SharedPreferences sharedPreferences;
    String tournamentId, matchdate;
    Button showteams;
    ArrayList<String> Teams = new ArrayList<String>();
    //ArrayList<String> date = new ArrayList<String>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    int Count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tournament_match_schecdule);

        showteams = findViewById(R.id.fixschedule1);
        teamSelection = findViewById(R.id.teamSelection);


        sharedPreferences = getApplicationContext().getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        tournamentId = sharedPreferences.getString("tournamentId", "");


        showteams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int selectedId = selectball.getCheckedRadioButtonId();
//                ball = (RadioButton) findViewById(selectedId);
//                int selectedIdMatch = selectMatchtype.getCheckedRadioButtonId();
//                match = findViewById(selectedIdMatch);

                Toast.makeText(AddTournamentMatchSchedule.this,Count+"", Toast.LENGTH_SHORT).show();

                if (Count == 2) {
                    Intent i = new Intent(AddTournamentMatchSchedule.this,MatchTypeSelection.class);
                    i.putExtra("Team1" , Teams.get(0));
                    i.putExtra("Team2" , Teams.get(1));
                    startActivity(i);
                } else {
                    Toast.makeText(AddTournamentMatchSchedule.this, "Plz Select 2 Teams to proceed", Toast.LENGTH_SHORT).show();
                }

//
            }
        });


        final Query query = FirebaseFirestore.getInstance()
                .collection("teams").whereArrayContains("leagueId",tournamentId);
        FirestoreRecyclerOptions<Team> options = new FirestoreRecyclerOptions.Builder<Team>()
                .setQuery(query, Team.class)
                .build();


        SelectionTeamAdapter = new FirestoreRecyclerAdapter<Team, TeamHolder>(options) {
            @NonNull
            @Override
            public TeamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.selectionlist, parent, false);
                return new TeamHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final TeamHolder holder, final int position, @NonNull final Team model) {



                    Glide.with(getApplicationContext()).load(model.getImage())
                            .placeholder(R.drawable.profile)
                            .into(holder.image);
                    holder.name.setText(model.getName());
                    holder.selected_Team.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (holder.selected_Team.isChecked()) {
                                Count++;
                                String tempteam2 = getSnapshots().getSnapshot(position).getId();
                                Teams.add(tempteam2);
                                Toast.makeText(AddTournamentMatchSchedule.this,Count+"", Toast.LENGTH_SHORT).show();
                            } else {
                                String tempteam3 = getSnapshots().getSnapshot(position).getId();
                                Teams.remove(tempteam3);
                                Count--;
                                Toast.makeText(AddTournamentMatchSchedule.this,Count+"", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        };
        teamSelection.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        teamSelection.setAdapter(SelectionTeamAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        SelectionTeamAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        SelectionTeamAdapter.stopListening();
    }

    private class TeamHolder extends RecyclerView.ViewHolder {
        CheckBox selected_Team;
        TextView name;
        CircularImageView image;
        LinearLayout team;

        public TeamHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.team_name);
            image = itemView.findViewById(R.id.team_img);
            team = itemView.findViewById(R.id.team);
            selected_Team = itemView.findViewById(R.id.selectedTeam);
        }
    }

//    private void addToTeam(String toString) {
//        db.collection("teams").document(toString)
//                .update("matchDate", FieldValue.arrayUnion(matchdate))
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(getApplicationContext(), "Date Change", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
}