package com.example.criinfo.More;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.criinfo.aboutusAdapter;
import com.example.criinfo.pojoaboutus;
import android.os.Bundle;
import com.example.criinfo.R;
import com.example.criinfo.playerlistadapter;
import com.example.criinfo.pojoplayer;

import java.util.ArrayList;
import java.util.List;

public class AboutUs extends AppCompatActivity {

    RecyclerView recyclerView;
    List<pojoaboutus> ar1;
    aboutusAdapter adapter;
    pojoaboutus pj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        recyclerView=findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ar1=new ArrayList<>();


        for (int i=1;i<=5;i++)
        {
          if (i==1)
          {
             pj=new pojoaboutus(R.drawable.firstabout);
          }
          else if (i==2)
          {
              pj=new pojoaboutus(R.drawable.secondabout);
          }
          else if (i==3)
          {
              pj=new pojoaboutus(R.drawable.thirdabout);
          }
          else if (i==4)
          {
              pj=new pojoaboutus(R.drawable.fourthabout);
          }
          else
          {
              pj=new pojoaboutus(R.drawable.fifthabout);
          }

            ar1.add(pj);
        }


        adapter=new aboutusAdapter(getApplicationContext(),ar1);
        recyclerView.setAdapter(adapter);



    }
}