package com.example.criinfo.More;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.criinfo.More.MyTournamentTabs.MyTournamentInfo;
import com.example.criinfo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddMatchResult extends AppCompatActivity {

    Spinner winner;
    RelativeLayout layout;
    ArrayList<String> winnername = new ArrayList<String>();
    String scheduleId;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String Team1Id, Team2Id, winningTeamName, winTeamId, wins,resultId,winstatu;
    ImageView team2flag, team1flag;
    TextView team2name, team1name;
    Button saveresult;
    EditText team1score, team2score, team1wicket, team2wicket, winstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_match_result);

        winner = findViewById(R.id.winner);
        team1flag = findViewById(R.id.team1flag);
        team2flag = findViewById(R.id.team2flag);
        team1name = findViewById(R.id.team1name);
        team2name = findViewById(R.id.team2name);
        saveresult = findViewById(R.id.saveresult);

        team1score = findViewById(R.id.team1score);
        team2score = findViewById(R.id.team2score);
        team2wicket = findViewById(R.id.team2wicket);
        team1wicket = findViewById(R.id.team1wicket);
        winstatus = findViewById(R.id.winstatus);

        layout = findViewById(R.id.layout);


        winnername.add("Select Winner For This Match");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, winnername);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        winner.setAdapter(adapter);
        Bundle bundle = getIntent().getExtras();
        scheduleId = bundle.getString("Id");

        db.collection("result")
                .whereEqualTo("scheduleId",scheduleId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (!task.getResult().isEmpty()) {
                                    resultId = document.getId();
                                   team1score.setText(String.valueOf(document.getString("team1score")));
                                   team2score.setText(String.valueOf(document.getString("team2score")));
                                   team2wicket.setText(String.valueOf(document.getString("team2wicket")));
                                   team1wicket.setText(String.valueOf(document.getString("team1wicket")));

                                   saveresult.setText("Update Result");

                                }

                            }
                        }
                    }
                });

        db.collection("schedule").document(scheduleId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        Team1Id = task.getResult().getString("team1");
                        Team2Id = task.getResult().getString("team2");
                        db.collection("teams").document(Team1Id)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        winnername.add(task.getResult().getString("sortname"));
                                        team1name.setText(task.getResult().getString("sortname"));
                                        Glide.with(AddMatchResult.this).load(task.getResult().getString("image"))
                                                .placeholder(R.drawable.team)
                                                .into(team1flag);

                                    }
                                });

                        db.collection("teams").document(Team2Id)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        winnername.add(task.getResult().getString("sortname"));
                                        team2name.setText(task.getResult().getString("sortname"));
                                        Glide.with(AddMatchResult.this).load(task.getResult().getString("image"))
                                                .placeholder(R.drawable.team)
                                                .into(team2flag);
                                    }
                                });


                    }
                });

        saveresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (saveresult.getText().toString().equals("Save Result")) {


                    winningTeamName = winner.getSelectedItem().toString();


                    if (team1score.getText().toString().isEmpty()) {
                        team1score.setError("Enter Run for team 1");
                    } else if (team1wicket.getText().toString().isEmpty()) {
                        team1wicket.setError("Enter Wicket for team 1");
                    } else if (team2score.getText().toString().isEmpty()) {
                        team2score.setError("Enter Run for team 2");
                    } else if (team2wicket.getText().toString().isEmpty()) {
                        team2wicket.setError("Enter Wicket for team 2");
                    } else if (winstatus.getText().toString().isEmpty()) {
                        winstatus.setError("Enter Statues for this result");
                    } else if (winningTeamName.equals("Select Winner For This Match")) {
                        Snackbar.make(layout, "Plz Select team that win this match", Snackbar.LENGTH_LONG)
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();
                    } else {
                        db.collection("teams")
                                .whereEqualTo("sortname", winningTeamName)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                if (!task.getResult().isEmpty()) {
                                                    String id = document.getId();
                                                    if (id.equals(Team1Id) || id.equals(Team2Id)) {
                                                        winTeamId = String.valueOf(document.getId());
//                                                    Toast.makeText(AddMatchResult.this, winTeamId+"", Toast.LENGTH_SHORT).show();
                                                        saveResult(winTeamId);
                                                    }
                                                }

                                            }
                                        }
                                    }
                                });
                    }
                }
                else{

                    winningTeamName = winner.getSelectedItem().toString();


                    if (team1score.getText().toString().isEmpty()) {
                        team1score.setError("Enter Run for team 1");
                    } else if (team1wicket.getText().toString().isEmpty()) {
                        team1wicket.setError("Enter Wicket for team 1");
                    } else if (team2score.getText().toString().isEmpty()) {
                        team2score.setError("Enter Run for team 2");
                    } else if (team2wicket.getText().toString().isEmpty()) {
                        team2wicket.setError("Enter Wicket for team 2");
                    } else if (winstatus.getText().toString().isEmpty()) {
                        winstatus.setError("Enter Statues for this result");
                    } else if (winningTeamName.equals("Select Winner For This Match")) {
                        Snackbar.make(layout, "Plz Select team that win this match", Snackbar.LENGTH_LONG)
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();
                    } else {
                        db.collection("teams")
                                .whereEqualTo("sortname", winningTeamName)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                if (!task.getResult().isEmpty()) {
                                                    String id = document.getId();
                                                    if (id.equals(Team1Id) || id.equals(Team2Id)) {
                                                        winTeamId = String.valueOf(document.getId());
//                                                    Toast.makeText(AddMatchResult.this, winTeamId+"", Toast.LENGTH_SHORT).show();
                                                        updateResult(winTeamId);
                                                    }
                                                }

                                            }
                                        }
                                    }
                                });
                    }
                }
            }
        });


    }

    private void updateResult(String winTeamId) {

        winstatu = winningTeamName + " Win By " + winstatus.getText().toString();
        Map<String, Object> Resultupdate = new HashMap<>();
        Resultupdate.put("team1score", team1score.getText().toString());
        Resultupdate.put("team2score", team2score.getText().toString());
        Resultupdate.put("team1wicket", team1wicket.getText().toString());
        Resultupdate.put("team2wicket", team2wicket.getText().toString());
        Resultupdate.put("winning_status", winstatu);
        Resultupdate.put("scheduleId", scheduleId);
        Resultupdate.put("winteamId", winTeamId);
        db.collection("result").document(resultId)
                .update(Resultupdate)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddMatchResult.this, "Result Save Successfully", Toast.LENGTH_SHORT).show();
                        updateSchedule();
                        finish();
                        startActivity(new Intent(getApplicationContext(), MyTournamentInfo.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddMatchResult.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void saveResult(String winTeamId) {

        winstatu = winningTeamName + " Win By " + winstatus.getText().toString();
        Map<String, Object> Result = new HashMap<>();
        Result.put("team1score", team1score.getText().toString());
        Result.put("team2score", team2score.getText().toString());
        Result.put("team1wicket", team1wicket.getText().toString());
        Result.put("team2wicket", team2wicket.getText().toString());
        Result.put("winning_status", winstatu);
        Result.put("scheduleId", scheduleId);
        Result.put("winteamId", winTeamId);

        db.collection("result")
                .add(Result)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        Toast.makeText(AddMatchResult.this, "Result Save Successfully", Toast.LENGTH_SHORT).show();
                        updateSchedule();
                        finish();
                        startActivity(new Intent(getApplicationContext(), MyTournamentInfo.class));


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddMatchResult.this, "Please fill all the details", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void updateSchedule() {

        db.collection("schedule").document(scheduleId)
                .update(
                        "team1score",team1score.getText().toString()+" / "+team1wicket.getText().toString() + "",
                        "team2score",team2score.getText().toString()+" / "+team2wicket.getText().toString() + "",
                        "winner",winstatu
                ).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddMatchResult.this, "Update successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddMatchResult.this, "Update Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
}