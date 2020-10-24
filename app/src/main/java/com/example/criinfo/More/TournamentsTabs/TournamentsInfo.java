package com.example.criinfo.More.TournamentsTabs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.criinfo.More.TournamentsTabs.*;
import com.example.criinfo.R;
import com.example.criinfo.Utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mikhaellopez.circularimageview.CircularImageView;

public class TournamentsInfo extends AppCompatActivity {
    TabLayout tourTab;
    TournamentsAboutFragment tournamentsAboutFragment;
    TournamentsTeamsFragment tournamentsTeamsFragment;
    TournamentsPointTableFragment tournamentsPointTableFragment;
    TournamentsScheduleFragment tournamentsSchecduleFragment;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences;
    CircularImageView image;
    TextView name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournaments_info);
        image = findViewById(R.id.tourImage);
        name = findViewById(R.id.tourName);

        tournamentsSchecduleFragment = new TournamentsScheduleFragment();
        tournamentsTeamsFragment = new TournamentsTeamsFragment();
        tournamentsPointTableFragment = new TournamentsPointTableFragment();
        tournamentsAboutFragment = new TournamentsAboutFragment();


        tourTab = findViewById(R.id.tourTab);
        setfragment(tournamentsSchecduleFragment);

        sharedPreferences = getApplicationContext().getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);


        db.collection("tournaments")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (!task.getResult().isEmpty()) {
                                    if (document.getId().equals(sharedPreferences.getString("tournamentId", ""))) {
                                        Glide.with(getApplicationContext())
                                                .load(document.getString("image"))
                                                .placeholder(R.drawable.team)
                                                .into(image);
                                        name.setText(document.getString("tournament"));
                                    }

                                }
                            }
                        } else {

                        }
                    }
                });

        tourTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        setfragment(tournamentsSchecduleFragment);
                        break;
                    case 1:
                        setfragment(tournamentsTeamsFragment);
                        break;
                    case 2:
                        setfragment(tournamentsPointTableFragment);
                        break;
                    case 3:
                        setfragment(tournamentsAboutFragment);
                        break;
                    default:
                        setfragment(tournamentsSchecduleFragment);
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


}
