package com.example.criinfo.Home;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.criinfo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MatchDetails extends AppCompatActivity {
    TextView versus,result,teamone,teamtwo,scoreone,scoretwo,momname,resultmatch,batsmanone,batsmantwo,batsmanonescore,batsmantwoscore,bowlername,bowlerovers,commentary;
String matchid,mom,batonename,batonescore,battwoname,battwoscore;
    String matchcode,state;
    String status,type,team1,team2,score1,score2,matchdescription,sub,bwlname,bwlovers,batsman1name,batsman2name,batsman1score,batsman2score ;
    private RequestQueue mQueue;
    private StringRequest request,request1;
    String url = "http://mapps.cricbuzz.com/cbzios/match/livematches";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchopen);

        versus=findViewById(R.id.versus);
        result=findViewById(R.id.result);
        teamone=findViewById(R.id.team1);
        teamtwo=findViewById(R.id.team2);
        scoreone=findViewById(R.id.score1);
        scoretwo=findViewById(R.id.score2);
        momname=findViewById(R.id.momname);
        resultmatch=findViewById(R.id.resultmatch);
        batsmanone=findViewById(R.id.batsman1);
        batsmantwo=findViewById(R.id.batsman2);
        batsmanonescore=findViewById(R.id.batsman1score);
        batsmantwoscore=findViewById(R.id.batsman2score);
        bowlername=findViewById(R.id.bowlername);
        bowlerovers=findViewById(R.id.bowlerovers);
        commentary=findViewById(R.id.commentary);


        matchcode=  getIntent().getStringExtra("matchid");



        mQueue = Volley.newRequestQueue(getApplicationContext());


        request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsona = new JSONObject(response);
                   JSONArray jsonarray = jsona.getJSONArray("matches");


              for (int i=0;i<jsonarray.length();i++)
              {

                 JSONObject jsonobject= jsonarray.getJSONObject(i);
                  matchid= jsonobject.getString("match_id");

                  if (!matchid.equals(matchcode))
                  {
                 continue;
                  }


                   JSONObject jsonobject2=   jsonobject.getJSONObject("header");
                  state=jsonobject2.getString("state");
                 status=  jsonobject2.getString("status");
                 result.setText(status);

                       type = jsonobject2.getString("type");
                     sub = status.substring(0, 6);

                          JSONObject jsonobject8 = jsonobject.getJSONObject("team1");
                          team1 = jsonobject8.getString("name");
                          JSONObject jsonobject9 = jsonobject.getJSONObject("team2");
                           team2 = jsonobject9.getString("name");



                           if (state.equals("preview"))
                           {

                               versus.setText(team1 + " versus " + team2);
                               result.setText("Match will "+status);
                               teamone.setText(team1);
                               teamtwo.setText(team2);
                               scoreone.setText("00/0");
                               scoretwo.setText("00/0");
                               momname.setText("To be Declared");
                               resultmatch.setText(type+" - Format Match");
                               batsmanone.setVisibility(View.GONE);
                               batsmantwo.setVisibility(View.GONE);
                               batsmanonescore.setVisibility(View.GONE);
                               batsmantwoscore.setVisibility(View.GONE);
                               bowlername.setVisibility(View.GONE);
                               bowlerovers.setVisibility(View.GONE);
                           }

                          else if (state.equals("innings break"))
                           {
                              matchdescription = jsonobject2.getString("toss") + ", " + jsonobject2.getString("match_desc");


                               JSONObject jsonobject4 = jsonobject.getJSONObject("bat_team");
                               JSONArray jsonArray1 = jsonobject4.getJSONArray("innings");
                               JSONObject jsonobject5 = jsonArray1.getJSONObject(0);
                          score1 = jsonobject5.getString("score") + "/" + jsonobject5.getString("wkts") + " in " + jsonobject5.getString("overs");


                               JSONArray batsmanarray = jsonobject.getJSONArray("batsman");

                               for (int ii = 0; ii < batsmanarray.length(); ii++) {

                                   if (ii == 0) {
                                       JSONObject batsmanoneobject = batsmanarray.getJSONObject(ii);


                                   batonename=    batsmanoneobject.getString("name");
                                      batonescore= batsmanoneobject.getString("r") + "(" + batsmanoneobject.getString("b") + "), 4's-" + batsmanoneobject.getString("4s") + ", 6's-" + batsmanoneobject.getString("6s");
                                   } else {
                                       JSONObject batsmantwoobject = batsmanarray.getJSONObject(ii);

                                       battwoname= batsmantwoobject.getString("name");
                                       battwoscore= batsmantwoobject.getString("r") + "(" + batsmantwoobject.getString("b") + "), 4's-" + batsmantwoobject.getString("4s") + ", 6's-" + batsmantwoobject.getString("6s");

                                   }


                               }

                               versus.setText(team1 + " versus " + team2);
                               result.setText(status);
                               teamone.setText(team1);
                               teamtwo.setText(team2);
                               scoreone.setText(score1);
                               scoretwo.setText("00/0");
                               momname.setText("To be Declared");
                               resultmatch.setText("Result - " + status);
                               bowlername.setVisibility(View.GONE);
                               bowlerovers.setVisibility(View.GONE);

                               if (batsmanarray.length()==1)
                               {
                                   batsmanone.setText(batonename);
                                   batsmanonescore.setText(batonescore);
                               }
                               else {
                                   batsmanone.setText(batonename);
                                   batsmanonescore.setText(batonescore);
                                   batsmantwo.setText(battwoname);
                                   batsmantwoscore.setText(battwoscore);
                               }
                               updation();
                           }
                          else if (state.equals("mom")) {
                               matchdescription = jsonobject2.getString("toss") + ", " + jsonobject2.getString("match_desc");

                               if (jsonobject2.has("momNames")) {
                                   JSONArray momarray = jsonobject2.getJSONArray("momNames");


                                   mom = momarray.getString(0);
                               } else {
                                   mom = "To be Declared";
                               }


                               JSONObject jsonobject4 = jsonobject.getJSONObject("bat_team");
                               JSONArray jsonArray1 = jsonobject4.getJSONArray("innings");
                               JSONObject jsonobject5 = jsonArray1.getJSONObject(0);
                               score1 = jsonobject5.getString("score") + "/" + jsonobject5.getString("wkts") + " in " + jsonobject5.getString("overs");

                               JSONObject jsonobject6 = jsonobject.getJSONObject("bow_team");
                               JSONArray jsonArray2 = jsonobject6.getJSONArray("innings");
                               JSONObject jsonobject7 = jsonArray2.getJSONObject(0);
                               score2 = jsonobject7.getString("score") + "/" + jsonobject7.getString("wkts") + " in " + jsonobject7.getString("overs");

                               JSONArray bowlerarray = jsonobject.getJSONArray("bowler");
                               JSONObject bowlerobject = bowlerarray.getJSONObject(0);
                               bwlname = bowlerobject.getString("name");
                               bwlovers = bowlerobject.getString("o") + "-" + bowlerobject.getString("m") + "-" + bowlerobject.getString("r") + "-" + bowlerobject.getString("w");


                               JSONArray batsmanarray = jsonobject.getJSONArray("batsman");

                               for (int ii = 0; ii < batsmanarray.length(); ii++) {

                                   if (ii == 0) {
                                       JSONObject batsmanoneobject = batsmanarray.getJSONObject(ii);

                                       batsman1name = batsmanoneobject.getString("name");
                                       batsman1score = batsmanoneobject.getString("r") + "(" + batsmanoneobject.getString("b") + "), { 4's-" + batsmanoneobject.getString("4s") + ", 6's-" + batsmanoneobject.getString("6s") + " }";

                                   } else {
                                       JSONObject batsmantwoobject = batsmanarray.getJSONObject(ii);
                                       batsman2name = batsmantwoobject.getString("name");
                                       batsman2score = batsmantwoobject.getString("r") + "(" + batsmantwoobject.getString("b") + "), { 4's-" + batsmantwoobject.getString("4s") + ", 6's-" + batsmantwoobject.getString("6s") + " }";

                                   }


                               }

                               versus.setText(team1 + " versus " + team2);
                               result.setText(status);
                               teamone.setText(team1);
                               teamtwo.setText(team2);
                               scoreone.setText(score1);
                               scoretwo.setText(score2);
                               momname.setText(mom);
                               resultmatch.setText(type + " - Format Match");
                               bowlername.setText(bwlname);
                               batsmanone.setText(batsman1name);
                               batsmantwo.setText(batsman2name);
                               batsmanonescore.setText(batsman1score);
                               batsmantwoscore.setText(batsman2score);
                               bowlerovers.setText(bwlovers);

                               updation();

                           }
                          else if (state.equals("toss"))
                           {

                               versus.setText(team1 + " versus " + team2);
                               result.setText(jsonobject2.getString("toss") );
                               teamone.setText(team1);
                               teamtwo.setText(team2);
                               scoreone.setText("Starts Soon");
                               scoretwo.setText("Starts soon");
                               momname.setText("To be Declared");
                               resultmatch.setText(type + " - Format Match");
                               bowlername.setVisibility(View.GONE);
                               batsmanone.setVisibility(View.GONE);
                               batsmantwo.setVisibility(View.GONE);
                               batsmanonescore.setVisibility(View.GONE);
                               batsmantwoscore.setVisibility(View.GONE);
                               bowlerovers.setVisibility(View.GONE);

                           }
                          else if (state.equals("inprogress"))
                              {

                                  matchdescription = jsonobject2.getString("toss") + ", " + jsonobject2.getString("match_desc");




                                  JSONObject jsonobject4 = jsonobject.getJSONObject("bat_team");
                                  JSONArray jsonArray1 = jsonobject4.getJSONArray("innings");
                                  if (jsonArray1.length()==1) {
                                      JSONObject jsonobject5 = jsonArray1.getJSONObject(0);
                                      score1 = jsonobject5.getString("score") + "/" + jsonobject5.getString("wkts") + " in " + jsonobject5.getString("overs");
                                  }
                                  else {
                                      score1="00/0";
                                  }
                                  JSONObject jsonobject6 = jsonobject.getJSONObject("bow_team");
                                  JSONArray jsonArray2 = jsonobject6.getJSONArray("innings");
                                  if (jsonArray2.length()==1) {
                                      JSONObject jsonobject7 = jsonArray2.getJSONObject(0);
                                      score2 = jsonobject7.getString("score") + "/" + jsonobject7.getString("wkts") + " in " + jsonobject7.getString("overs");
                                  }
                                  else {
                                      score2="00/0";
                                  }
                                  JSONArray bowlerarray = jsonobject.getJSONArray("bowler");
                                  JSONObject bowlerobject = bowlerarray.getJSONObject(0);
                                  bwlname = bowlerobject.getString("name");
                                  bwlovers = bowlerobject.getString("o") + "-" + bowlerobject.getString("m") + "-" + bowlerobject.getString("r") + "-" + bowlerobject.getString("w");


                                  JSONArray batsmanarray = jsonobject.getJSONArray("batsman");

                                  for (int ii = 0; ii < batsmanarray.length(); ii++) {

                                      if (ii == 0) {
                                          JSONObject batsmanoneobject = batsmanarray.getJSONObject(ii);

                                          batsman1name = batsmanoneobject.getString("name");
                                          batsman1score = batsmanoneobject.getString("r") + "(" + batsmanoneobject.getString("b") + "), { 4's-" + batsmanoneobject.getString("4s") + ", 6's-" + batsmanoneobject.getString("6s") + " }";

                                      } else {
                                          JSONObject batsmantwoobject = batsmanarray.getJSONObject(ii);
                                          batsman2name = batsmantwoobject.getString("name");
                                          batsman2score = batsmantwoobject.getString("r") + "(" + batsmantwoobject.getString("b") + "), { 4's-" + batsmantwoobject.getString("4s") + ", 6's-" + batsmantwoobject.getString("6s") + " }";

                                      }


                                  }


                                  versus.setText(team1 + " versus " + team2);
                                  result.setText(status);
                                  teamone.setText(team1);
                                  teamtwo.setText(team2);
                                  scoreone.setText(score1);
                                  scoretwo.setText(score2);
                                  momname.setText("To be Declared");
                                  resultmatch.setText(type + " - Format Match");
                                  bowlername.setText(bwlname);
                                  batsmanone.setText(batsman1name);
                                  batsmantwo.setText(batsman2name);
                                  batsmanonescore.setText(batsman1score);
                                  batsmantwoscore.setText(batsman2score);
                                  bowlerovers.setText(bwlovers);

                                  updation();


                              }
                          else {
return;
                           }


              }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"catch",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();

            }
        }){

        };

        mQueue.add(request);


    }


    public void updation() {
        request1=new StringRequest(Request.Method.GET, "http://mapps.cricbuzz.com/cbzios/match/"+matchcode+"/commentary", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject commobject=new JSONObject(response);
                   JSONArray commarray= commobject.getJSONArray("comm_lines");

                   for (int i=0;i<commarray.length();i++)
                   {
                      JSONObject insideobject= commarray.getJSONObject(i);
                      if (!insideobject.has("comm"))
                      {
                       continue;
                      }

                      else {
                          commentary.append("\n");
                          commentary.append(insideobject.getString("comm"));
                          commentary.append("\n");
                          commentary.append("\n");
                          commentary.append("\n");
                      }
                   }






                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Catch :  ",Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error :  ",Toast.LENGTH_LONG).show();
            }
        })
        {


        };
        mQueue.add(request1);






    }


}