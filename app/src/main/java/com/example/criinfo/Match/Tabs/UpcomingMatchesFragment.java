package com.example.criinfo.Match.Tabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.criinfo.Home.HomeAdapter;
import com.example.criinfo.Home.Matchpojo;
import com.example.criinfo.Match.MatchAdapter;
import com.example.criinfo.R;
import com.example.criinfo.Utils.Utils;
import com.example.criinfo.network.ApiClient;
import com.example.criinfo.network.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpcomingMatchesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpcomingMatchesFragment extends Fragment {

    RecyclerView recyclerView;

    private RequestQueue mQueue;
    private StringRequest request;
    MatchAdapter adp;
    String score1, score2;
    ProgressBar progressBar;

    String url = "http://mapps.cricbuzz.com/cbzios/match/livematches";
    ArrayList<Matchpojo> ar1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpcomingMatchesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpcomingMatchesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpcomingMatchesFragment newInstance(String param1, String param2) {
        UpcomingMatchesFragment fragment = new UpcomingMatchesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_upcoming_matches, container, false);

        recyclerView=view.findViewById(R.id.recycler);
        progressBar = view.findViewById(R.id.progrssbarUpcoming);
        ar1=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        progressBar.setVisibility(View.VISIBLE);

        mQueue = Volley.newRequestQueue(getContext());
        request = new StringRequest(Request.Method.GET, "https://cricapi.com/api/matches/?apikey="+Utils.apikey, new Response.Listener<String>() {
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

//       ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//       Call<ResponseBody> call = apiInterface.getMatches(Utils.apikey);
//       call.enqueue(new Callback<ResponseBody>() {
//           @Override
//           public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
//               try {
//                   parseData(response.body().string());
//               } catch (IOException e) {
//                   e.printStackTrace();
//               }
//           }
//
//           @Override
//           public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//           }
//       });


   /*     request = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsona = new JSONObject(response);
                    JSONArray jsonarray =   jsona.getJSONArray("matches");

                    int a=jsonarray.length();


                    for (int i=0;i<a;i++) {

                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        String matchid = jsonobject.getString("match_id");
                        String seriesname = jsonobject.getString("series_name");

                        JSONObject jsonobject2 = jsonobject.getJSONObject("header");
                        String type = jsonobject2.getString("type");

                        String status=jsonobject2.getString("status");
                        String state = jsonobject2.getString("state");

                        if (state.equals("preview"))
                        {
                            String matchdescription = jsonobject2.getString("match_desc");

                            JSONObject jsonobject3 = jsonobject.getJSONObject("venue");
                            String location = jsonobject3.getString("name") + ", " + jsonobject3.getString("location");


                            JSONObject jsonobject8 = jsonobject.getJSONObject("team1");
                            String team1 = jsonobject8.getString("name");
                            JSONObject jsonobject9 = jsonobject.getJSONObject("team2");
                            String team2 = jsonobject9.getString("name");


                            Matchpojo pj = new Matchpojo(seriesname, type, jsonobject2.getString("match_desc"), location, status, team1, team2, "00/0", "00/0", matchid);
                            ar1.add(pj);

                        }

                        else {

                        }



                    }
                    adp=new HomeAdapter(getContext(),ar1);
                    recyclerView.setAdapter(adp);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),"Catch",Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println(error.getMessage());
                Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        }){

        };

        mQueue.add(request);*/




        return  view;
    }

    private void parseData(String string) {
        try {
            JSONObject jsonObject = new JSONObject(string);
            System.out.println(jsonObject);
            JSONArray jsonArray = jsonObject.getJSONArray("matches");

            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                Matchpojo matchpojo = new Matchpojo();
                matchpojo.setMatchid(String.valueOf(jsonObject1.get("unique_id")));
                String date = String.valueOf(jsonObject1.get("date"));
                matchpojo.setMatchdescription(date.substring(0,10));
                matchpojo.setTeam1(String.valueOf(jsonObject1.get("team-1")));
                matchpojo.setTeam2(String.valueOf(jsonObject1.get("team-2")));
                matchpojo.setTypeofmatch(String.valueOf(jsonObject1.get("type")));

                if(!jsonObject1.getBoolean("matchStarted")){
                    ar1.add(matchpojo);
                }


            }

            adp=new MatchAdapter(getContext(),ar1);
            recyclerView.setAdapter(adp);
            progressBar.setVisibility(View.GONE);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}