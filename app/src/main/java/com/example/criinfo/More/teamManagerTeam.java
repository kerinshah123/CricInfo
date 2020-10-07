package com.example.criinfo.More;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.criinfo.R;

public class teamManagerTeam extends AppCompatActivity {
LinearLayout teamlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_manager_team);
        teamlayout=findViewById(R.id.teamlayout);

        teamlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),teamPlayers.class);
                startActivity(intent);
            }
        });
    }

    public void createteam(View view) {
        Intent intent=new Intent(getApplicationContext(),createTeam.class);
        startActivity(intent);
    }
}