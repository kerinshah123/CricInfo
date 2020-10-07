package com.example.criinfo.More;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.criinfo.pojoplayer;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.criinfo.playerlistadapter;
import com.example.criinfo.R;

import java.util.ArrayList;
import java.util.List;

public class teamPlayers extends AppCompatActivity {

    RecyclerView recyclerView;
    List<pojoplayer> ar1;
    playerlistadapter adapter;
    pojoplayer pj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_players);
        recyclerView=findViewById(R.id.recy);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ar1=new ArrayList<>();

        pj=new pojoplayer();

        for (int i=0;i<10;i++)
        {
            pj=new pojoplayer("steven smith","28","Batsman",R.drawable.steve);
            ar1.add(pj);
        }


        adapter=new playerlistadapter(getApplicationContext(),ar1);
        recyclerView.setAdapter(adapter);

    }

    public void addnewplayer(View view) {

        Intent intent=new Intent(getApplicationContext(),addPlayer.class);
        startActivity(intent);
    }
}