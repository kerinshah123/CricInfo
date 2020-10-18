package com.example.criinfo.More.MyTournamentTabs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.criinfo.More.TournamentTeams;
import com.example.criinfo.R;
import com.example.criinfo.Utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mikhaellopez.circularimageview.CircularImageView;

public class MyTournamentInfo extends AppCompatActivity {

    TabLayout tournamentTab;
    TournamentSchecduleFragment tournamentSchecduleFragment;
    TournamentTeamsFragment tournamentTeamsFragment;
    TournamentPointtableFragment tournamentPointtableFragment;
    TournamentAboutFragment tournamentAboutFragment;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences ;
    CircularImageView image;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tournament_info);

        image = findViewById(R.id.tournamentImage);
        name = findViewById(R.id.tournamentName);

        tournamentSchecduleFragment = new TournamentSchecduleFragment();
        tournamentTeamsFragment = new TournamentTeamsFragment();
        tournamentPointtableFragment= new TournamentPointtableFragment();
        tournamentAboutFragment = new TournamentAboutFragment();


        tournamentTab = findViewById(R.id.tournamentTab);
        setfragment(tournamentSchecduleFragment);

        sharedPreferences = getApplicationContext().getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);


        db.collection("tournaments")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(!task.getResult().isEmpty()){
                                    if(document.getId().equals(sharedPreferences.getString("tournamentId","")))
                                        Glide.with(getApplicationContext())
                                                .load(document.getString("image"))
                                                .placeholder(R.drawable.team)
                                                .into(image);
                                    name.setText(document.getString("tournament"));

                                }
                            }
                        } else {

                        }
                    }
                });

        tournamentTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        setfragment(tournamentSchecduleFragment);
                        break;
                    case 1:
                        setfragment(tournamentTeamsFragment);
                        break;
                    case 2:
                        setfragment(tournamentPointtableFragment);
                        break;
                    case 3:
                        setfragment(tournamentAboutFragment);
                        break;
                    default:
                        setfragment(tournamentSchecduleFragment);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void setfragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_Tournament, fragment);
        fragmentTransaction.commit();

    }


    public void addTeam(View view) {
        startActivity(new Intent(getApplicationContext(), TournamentTeams.class));

    }
}