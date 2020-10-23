package com.example.criinfo.More;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddTournamentMatchSchedule extends AppCompatActivity {

    RecyclerView teamSelection;
    FirestoreRecyclerAdapter SelectionTeamAdapter;
    SharedPreferences sharedPreferences;
    String tournamentId, matchdate;
    TextInputEditText Matchdate;
    RadioGroup selectball, selectMatchtype;
    RadioButton ball,match;
    Button showteams;
    ArrayList<String> Teams = new ArrayList<String>();
    ArrayList<String> date = new ArrayList<String>();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
    int Count;
    SimpleDateFormat start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tournament_match_schecdule);

        showteams = findViewById(R.id.fixschedule);
        Matchdate = findViewById(R.id.Matchdate);
        teamSelection = findViewById(R.id.teamSelection);
        selectball = (RadioGroup) findViewById(R.id.selectball);
        selectMatchtype = (RadioGroup) findViewById(R.id.selectMatchtype);

        sharedPreferences = getApplicationContext().getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        tournamentId = sharedPreferences.getString("tournamentId", "");

        showteams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = selectball.getCheckedRadioButtonId();
                ball = (RadioButton) findViewById(selectedId);
                int selectedIdMatch = selectMatchtype.getCheckedRadioButtonId();
                match = findViewById(selectedIdMatch);

                matchdate = Matchdate.getText().toString();

                if(Count == 2)
                {

                    DocumentReference documentReference = db.collection("teams").document(Teams.get(0));
                    documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Team team = documentSnapshot.toObject(Team.class);
                            if(team.getMatchDate().contains(matchdate))
                            {
                                Toast.makeText(AddTournamentMatchSchedule.this, "Select Any Other Date Team is Already Playing on that day", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    DocumentReference documentReference2 = db.collection("teams").document(Teams.get(0));
                    documentReference2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Team team = documentSnapshot.toObject(Team.class);
                            if(team.getMatchDate().contains(matchdate))
                            {
                                Toast.makeText(AddTournamentMatchSchedule.this, "Please select any Other Date these Teams are already playing on thiss day", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                if(Count == 2)
                                {
                                    addToTeam(Teams.get(0));
                                    addToTeam(Teams.get(1));

                                    Map<String, Object> Schedule = new HashMap<>();
                                    Schedule.put("team1",Teams.get(0));
                                    Schedule.put("team2",Teams.get(1));
                                    Schedule.put("LeagueId",tournamentId);
                                    Schedule.put("Match Date" , matchdate);
                                    Schedule.put("Match Ball" , ball.getText().toString());
                                    Schedule.put("Match Type" , match.getText().toString());

                                    db.collection("schedule")
                                            .add(Schedule)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    finish();

                                                    Toast.makeText(AddTournamentMatchSchedule.this, "Schedule Created Successfully", Toast.LENGTH_SHORT).show();
                                                    //startActivity(new Intent(getApplicationContext(),teamManagerTeam.class));
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    System.out.println(e.getMessage());
                                                }
                                            });

                                }
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(AddTournamentMatchSchedule.this, "Plz Select 2 Teams to proceed", Toast.LENGTH_SHORT).show();
                }




//                Toast.makeText(AddTournamentMatchSchecdule.this, ball.getText().toString() + "", Toast.LENGTH_SHORT).show();
//                Toast.makeText(AddTournamentMatchSchecdule.this, Teams + "", Toast.LENGTH_SHORT).show();
            }
        });


        final Query query = FirebaseFirestore.getInstance()
                .collection("teams");
        FirestoreRecyclerOptions<Team> options = new FirestoreRecyclerOptions.Builder<Team>()
                .setQuery(query, Team.class)
                .build();

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String myFormat = "MM/dd/yy"; //In which you need put here
                start = new SimpleDateFormat(myFormat, Locale.US);
                Matchdate.setText(start.format(myCalendar.getTime()));
            }

        };

        Matchdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTournamentMatchSchedule.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                }
                datePickerDialog.show();
            }
        });

        SelectionTeamAdapter = new FirestoreRecyclerAdapter<Team, TeamHolder>(options) {
            @NonNull
            @Override
            public TeamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.selectionlist, parent, false);
                return new TeamHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final TeamHolder holder, final int position, @NonNull final Team model) {
                if (model.getLeagueId().contains(tournamentId)) {
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
                            } else {
                                String tempteam3 = getSnapshots().getSnapshot(position).getId();
                                Teams.remove(tempteam3);
                                Count--;
                            }
                        }
                    });
                }
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
    private void addToTeam(String toString) {
        db.collection("teams").document(toString)
                .update("matchDate", FieldValue.arrayUnion(matchdate))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Date Change", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}