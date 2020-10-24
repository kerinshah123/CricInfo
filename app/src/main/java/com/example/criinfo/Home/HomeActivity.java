package com.example.criinfo.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.criinfo.Info.InfoFragment;
import com.example.criinfo.Match.MatchFragment;
import com.example.criinfo.More.MoreFragment;
import com.example.criinfo.News.NewsFragment;
import com.example.criinfo.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView main_nav;
    FrameLayout main_frame;


    HomeFragment homeFragment = new HomeFragment();
    InfoFragment infoFragment = new InfoFragment();
    MatchFragment matchFragment = new MatchFragment();
    MoreFragment moreFragment = new MoreFragment();
    NewsFragment newsFragment = new NewsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        main_frame = findViewById(R.id.main_frame);
        main_nav = findViewById(R.id.main_nav);

        setTitle("Cricinfo");
        setfragment(homeFragment);



        main_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.navigation_home :
                        setTitle("Cric Info");
                        setfragment(homeFragment);
                        return true;
                    case R.id.nav_info :
                        setfragment(infoFragment);
                        setTitle("Info");
                        return true;
                    case R.id.nav_match :
                        setfragment(matchFragment);
                        setTitle("Match");
                        return true;
                    case R.id.nav_more :
                        setfragment(moreFragment);
                        setTitle("More");
                        return true;
                    case R.id.nav_news :
                        setfragment(newsFragment);
                        setTitle("News");
                        return true;
                    default:
                        return false;

                }
            }

        });

    }

    private void setfragment(Fragment fragment) {
        FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.main_frame,fragment);
        fm.addToBackStack(null);
        fm.commit();

    }
}