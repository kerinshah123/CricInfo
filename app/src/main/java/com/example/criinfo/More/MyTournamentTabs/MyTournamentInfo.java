package com.example.criinfo.More.MyTournamentTabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.criinfo.R;
import com.google.android.material.tabs.TabLayout;

public class MyTournamentInfo extends AppCompatActivity {

    TabLayout tournamentTab;
    TournamentSchecduleFragment tournamentSchecduleFragment;
    TournamentTeamsFragment tournamentTeamsFragment;
    TournamentPointtableFragment tournamentPointtableFragment;
    TournamentAboutFragment tournamentAboutFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tournament_info);

        tournamentSchecduleFragment = new TournamentSchecduleFragment();
        tournamentTeamsFragment = new TournamentTeamsFragment();
        tournamentPointtableFragment= new TournamentPointtableFragment();
        tournamentAboutFragment = new TournamentAboutFragment();

        tournamentTab = findViewById(R.id.tournamentTab);
        setfragment(tournamentSchecduleFragment);

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
}