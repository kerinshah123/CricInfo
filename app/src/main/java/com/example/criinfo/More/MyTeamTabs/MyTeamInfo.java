package com.example.criinfo.More.MyTeamTabs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.criinfo.More.AddPlayer;
import com.example.criinfo.More.MyTeamTabs.MyTeamAbout;
import com.example.criinfo.More.MyTeamTabs.MyTeamMatch;
import com.example.criinfo.More.MyTeamTabs.MyTeamPlayer;
import com.example.criinfo.More.TeamPlayers;
import com.example.criinfo.R;
import com.example.criinfo.Utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mikhaellopez.circularimageview.CircularImageView;

public class MyTeamInfo extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences ;
    CircularImageView image;
    TextView name;
    TabLayout myteamTab;
    FrameLayout frame;
    MyTeamAbout myTeamAbout;
    MyTeamMatch myTeamMatch;
    MyTeamPlayer myTeamPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_team_info);
        image = findViewById(R.id.teamImage);
        name = findViewById(R.id.myteamName);
        myteamTab = findViewById(R.id.myTeamTab);
        frame = findViewById(R.id.frame_myTeam);

        myTeamAbout = new MyTeamAbout();
        myTeamMatch = new MyTeamMatch();
        myTeamPlayer = new MyTeamPlayer();

        setfragment(myTeamAbout);

        sharedPreferences = getApplicationContext().getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        db.collection("teams")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                               if(!task.getResult().isEmpty()){
                                   if(document.getId().equals(sharedPreferences.getString("teamId","")))
                                   Glide.with(getApplicationContext())
                                           .load(document.getString("image"))
                                           .placeholder(R.drawable.team)
                                           .into(image);
                                   name.setText(document.getString("name"));

                               }
                            }
                        } else {

                        }
                    }
                });

        myteamTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        setfragment(myTeamAbout);
                        break;
                    case 1:
                        setfragment(myTeamMatch);
                        break;
                    case 2:
                        setfragment(myTeamPlayer);
                        break;
                    default:
                        setfragment(myTeamAbout);
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
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_myTeam, fragment);
        fragmentTransaction.commit();

    }

    public void addPlayer(View view) {
        startActivity(new Intent(getApplicationContext(), TeamPlayers.class));
    }
}