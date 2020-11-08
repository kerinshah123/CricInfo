package com.example.criinfo.Info;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.criinfo.R;
import com.example.criinfo.Utils.Utils;
import com.example.criinfo.network.ApiClient;
import com.example.criinfo.network.ApiInterface;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class PlayerInfo extends AppCompatActivity {
    int id;
    CircularImageView imageView;
    TextView name, country, profileLabel, profileDetails,full_name,born,age,role,batting,bowling,teams;
    TextView tbtinn,tbtmatch,tbtrun,tbtavg,tbths,tbtft,tbthd,tbwk,tbec,tbbest,tbfw;
    TextView odibtinn,odibtmatch,odibtrun,odibtavg,odibths,odibtft,odibthd,odibwk,odibec,odibbest,odibfw;
    TextView testbtinn,testbtmatch,testbtrun,testbtavg,testbths,testbtft,testbthd,testbwk,testbec,testbbest,testbfw;
    CardView profile,personal,battingCard;
    LinearLayout personalDetails,battindStats;
    boolean profileVisible = false;
    boolean personalVisible = false;
    boolean battingVisible = false;
    private RequestQueue mQueue;
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_info);

        imageView = findViewById(R.id.playerImage);
        name = findViewById(R.id.playerName);
        country = findViewById(R.id.playerCountry);
        profile = findViewById(R.id.profile);
        personal = findViewById(R.id.personal);
        profileLabel = findViewById(R.id.profileLabel);
        profileDetails = findViewById(R.id.profileDetails);
        personalDetails = findViewById(R.id.personalDetails);
        full_name = findViewById(R.id.full_name);
        born = findViewById(R.id.born);
        age = findViewById(R.id.age);
        role = findViewById(R.id.role);
        batting = findViewById(R.id.batting);
        bowling = findViewById(R.id.bowling);
        teams = findViewById(R.id.teams);

        battindStats = findViewById(R.id.battinStats);
        battingCard = findViewById(R.id.battinStatsCard);

        tbtinn = findViewById(R.id.inningT20);
        tbtmatch = findViewById(R.id.matchT20);
        tbtrun = findViewById(R.id.runsT20);
        tbtavg = findViewById(R.id.avgT20);
        tbths = findViewById(R.id.hsT20);
        tbtft = findViewById(R.id.ftT20);
        tbthd = findViewById(R.id.hdT20);
        tbwk = findViewById(R.id.wkT20);
        tbec = findViewById(R.id.ecT20);
        tbbest = findViewById(R.id.bstT20);
        tbfw = findViewById(R.id.fwT20);

        odibtinn = findViewById(R.id.inningODI);
        odibtmatch = findViewById(R.id.matchODI);
        odibtrun = findViewById(R.id.runsODI);
        odibtavg = findViewById(R.id.avgODI);
        odibths = findViewById(R.id.hsODI);
        odibtft = findViewById(R.id.ftODI);
        odibthd = findViewById(R.id.hdODI);
        odibwk = findViewById(R.id.wkODI);
        odibec = findViewById(R.id.ecODI);
        odibbest = findViewById(R.id.bstODI);
        odibfw = findViewById(R.id.fwODI);

        testbtinn = findViewById(R.id.inningTest);
        testbtmatch = findViewById(R.id.matchTest);
        testbtrun = findViewById(R.id.runsTest);
        testbtavg = findViewById(R.id.avgTest);
        testbths = findViewById(R.id.hsTest);
        testbtft = findViewById(R.id.ftTest);
        testbthd = findViewById(R.id.hdTest);
        testbwk = findViewById(R.id.wkTest);
        testbec = findViewById(R.id.ecTest);
        testbbest = findViewById(R.id.bstTest);
        testbfw = findViewById(R.id.fwTest);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileVisible) {

                    profileDetails.setVisibility(View.GONE);
                    profileVisible = false;
                } else {
                    profileDetails.setVisibility(View.VISIBLE);
                    profileVisible = true;
                }

            }
        });
        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (personalVisible) {

                    personalDetails.setVisibility(View.GONE);
                    personalVisible = false;
                } else {
                    personalDetails.setVisibility(View.VISIBLE);
                    personalVisible = true;
                }

            }
        });

        battingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (battingVisible) {

                    battindStats.setVisibility(View.GONE);
                    battingVisible = false;
                } else {
                    battindStats.setVisibility(View.VISIBLE);
                    battingVisible = true;
                }

            }
        });

        id = getIntent().getIntExtra("id", 0);
        callApi();
    }

    private void callApi() {
        mQueue = Volley.newRequestQueue(getApplicationContext());
        request = new StringRequest(Request.Method.GET, "https://cricapi.com/api/playerStats/?apikey="+Utils.apikey+"&pid="+id, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseData(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });

        mQueue.add(request);


//        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        Call<ResponseBody> call = apiInterface.getPlayerInfo(Utils.apikey, id);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    parseData(response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
    }

    private void parseData(String string) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(string);
            Glide.with(this).load(jsonObject.getString("imageURL"))
                    .placeholder(R.drawable.profile)
                    .into(imageView);
            name.setText(jsonObject.getString("name"));
            country.setText(jsonObject.getString("country"));
            profileDetails.setText(jsonObject.getString("profile"));
            full_name.setText(jsonObject.getString("fullName"));
            born.setText(jsonObject.getString("born"));
            batting.setText(jsonObject.getString("battingStyle"));
            bowling.setText(jsonObject.getString("bowlingStyle"));
            teams.setText(jsonObject.getString("majorTeams"));
            age.setText(jsonObject.getString("currentAge"));
            role.setText(jsonObject.getString("playingRole"));

            JSONObject batting = jsonObject.getJSONObject("data").getJSONObject("batting");
            JSONObject battingTwenty = batting.getJSONObject("T20Is");
            JSONObject battingODI = batting.getJSONObject("ODIs");
            JSONObject battingTest = batting.getJSONObject("tests");

            tbtinn.setText(battingTwenty.getString("Inns"));
            tbtmatch.setText(battingTwenty.getString("Mat"));
            tbtrun.setText(battingTwenty.getString("Runs"));
            tbths.setText(battingTwenty.getString("HS"));
            tbtavg.setText(battingTwenty.getString("Ave"));
            tbthd.setText(battingTwenty.getString("100"));
            tbtft.setText(battingTwenty.getString("50"));

            odibtinn.setText(battingODI.getString("Inns"));
            odibtmatch.setText(battingODI.getString("Mat"));
            odibtrun.setText(battingODI.getString("Runs"));
            odibths.setText(battingODI.getString("HS"));
            odibtavg.setText(battingODI.getString("Ave"));
            odibthd.setText(battingODI.getString("100"));
            odibtft.setText(battingODI.getString("50"));

            testbtinn.setText(battingTest.getString("Inns"));
            testbtmatch.setText(battingTest.getString("Mat"));
            testbtrun.setText(battingTest.getString("Runs"));
            testbths.setText(battingTest.getString("HS"));
            testbtavg.setText(battingTest.getString("Ave"));
            testbthd.setText(battingTest.getString("100"));
            testbtft.setText(battingTest.getString("50"));

            JSONObject bowling = jsonObject.getJSONObject("data").getJSONObject("bowling");
            JSONObject bowlingTwenty = bowling.getJSONObject("T20Is");
            JSONObject bowlingODI = bowling.getJSONObject("ODIs");
            JSONObject bowlingTest = bowling.getJSONObject("tests");


            tbwk.setText(bowlingTwenty.getString("Wkts"));
            tbec.setText(bowlingTwenty.getString("Econ"));
            tbbest.setText(bowlingTwenty.getString("BBI"));
            tbfw.setText(bowlingTwenty.getString("5w"));

            odibwk.setText(bowlingODI.getString("Wkts"));
            odibec.setText(bowlingODI.getString("Econ"));
            odibbest.setText(bowlingODI.getString("BBI"));
            odibfw.setText(bowlingODI.getString("5w"));

            testbwk.setText(bowlingTest.getString("Wkts"));
            testbec.setText(bowlingTest.getString("Econ"));
            testbbest.setText(bowlingTest.getString("BBI"));
            testbfw.setText(bowlingTest.getString("5w"));



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}