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
import com.example.criinfo.More.MyTournamentTabs.MyTournamentInfo;
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
import com.mikhaellopez.circularimageview.CircularImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MatchTypeSelection extends AppCompatActivity {

    RecyclerView teamSelection;
    FirestoreRecyclerAdapter SelectionTeamAdapter;
    SharedPreferences sharedPreferences;
    String tournamentId, matchdate;
    TextView Matchdate;
    RadioGroup selectball, selectMatchtype;
    RadioButton ball, match;
    Button showteams;
    ArrayList<String> Teams = new ArrayList<String>();
    ArrayList<String> date = new ArrayList<String>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    int Count;
    SimpleDateFormat start;
    String startDate, endDate, Team1, Team2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_type_selection);

        Bundle extras = getIntent().getExtras();

        Team1 = extras.getString("Team1");
        Team2 = extras.getString("Team2");

        showteams = findViewById(R.id.fixschedule2);
        Matchdate = findViewById(R.id.Matchdate);
        teamSelection = findViewById(R.id.teamSelection);
        selectball = (RadioGroup) findViewById(R.id.selectball);
        selectMatchtype = (RadioGroup) findViewById(R.id.selectMatchtype);

        sharedPreferences = getApplicationContext().getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        tournamentId = sharedPreferences.getString("tournamentId", "");

        db.collection("tournaments").document(tournamentId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        startDate = task.getResult().getString("startdate");
                        endDate = task.getResult().getString("enddate");
                    }
                });


        showteams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = selectball.getCheckedRadioButtonId();
                ball = (RadioButton) findViewById(selectedId);
                int selectedIdMatch = selectMatchtype.getCheckedRadioButtonId();
                match = findViewById(selectedIdMatch);
                matchdate = Matchdate.getText().toString();

                if (selectball.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(MatchTypeSelection.this, "Plzz Select Ball Types", Toast.LENGTH_SHORT).show();
                } else if (selectMatchtype.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(MatchTypeSelection.this, "Plzz Select Match Types", Toast.LENGTH_SHORT).show();
                } else if (matchdate.isEmpty()) {
                    Toast.makeText(MatchTypeSelection.this, "Plzz Select Date", Toast.LENGTH_SHORT).show();

                } else {
                    DocumentReference documentReference = db.collection("teams").document(Team1);
                    documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Team team = documentSnapshot.toObject(Team.class);
                            if (team.getMatchDate().contains(matchdate)) {
                                Toast.makeText(MatchTypeSelection.this, "Select Any Other Date Team is Already Playing on that day", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    DocumentReference documentReference2 = db.collection("teams").document(Team2);
                    documentReference2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Team team = documentSnapshot.toObject(Team.class);
                            if (team.getMatchDate().contains(matchdate)) {
                                Toast.makeText(MatchTypeSelection.this, "Please select any Other Date these Teams are already playing on this day", Toast.LENGTH_SHORT).show();
                            } else {

                                    addToTeam(Team1);
                                    addToTeam(Team2);

                                    Map<String, Object> Schedule = new HashMap<>();
                                    Schedule.put("team1", Team1);
                                    Schedule.put("team2", Team2);
                                    Schedule.put("LeagueId", tournamentId);
                                    Schedule.put("Match Date", matchdate);
                                    Schedule.put("Match Ball", ball.getText().toString());
                                    Schedule.put("Match Type", match.getText().toString());

                                    db.collection("schedule")
                                            .add(Schedule)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    finish();

                                                    Toast.makeText(MatchTypeSelection.this, "Schedule Created Successfully", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(getApplicationContext(), MyTournamentInfo.class));


                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(MatchTypeSelection.this, "Please fill all the details", Toast.LENGTH_SHORT).show();

                                                    //System.out.println(e.getMessage());
                                                }
                                            });
                            }
                        }
                    });
                }
            }
        });


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
                DatePickerDialog datePickerDialog = new DatePickerDialog(MatchTypeSelection.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    String myFormat = "MM/dd/yy"; //In which you need put here
                    start = new SimpleDateFormat(myFormat, Locale.US);
                    Matchdate.setText(start.format(myCalendar.getTime()));

                    try {
                        Date date1 = new SimpleDateFormat("MM/dd/yy").parse(startDate);
                        Date date2 = new SimpleDateFormat("MM/dd/yy").parse(endDate);
                        datePickerDialog.getDatePicker().setMaxDate(date2.getTime());
                        datePickerDialog.getDatePicker().setMinDate(date1.getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
                datePickerDialog.show();
            }
        });


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