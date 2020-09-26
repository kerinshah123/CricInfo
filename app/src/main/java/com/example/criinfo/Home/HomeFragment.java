package com.example.criinfo.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {


    RecyclerView recyclerView;

    private RequestQueue mQueue;
    private StringRequest request;
    HomeAdapter adp;
    String url = "http://mapps.cricbuzz.com/cbzios/match/livematches";
    List<Matchpojo> ar1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view= inflater.inflate(R.layout.fragment_home2, container, false);
        recyclerView=view.findViewById(R.id.recycler);
        ar1=new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mQueue = Volley.newRequestQueue(getContext());


        request = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
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

                        String status = jsonobject2.getString("status");
                        String sub = status.substring(0, 6);

                        if (sub.equals("Starts")) {
                            JSONObject jsonobject3 = jsonobject.getJSONObject("venue");
                            String location = jsonobject3.getString("name") + ", " + jsonobject3.getString("location");


                            JSONObject jsonobject8 = jsonobject.getJSONObject("team1");
                            String team1 = jsonobject8.getString("name");
                            JSONObject jsonobject9 = jsonobject.getJSONObject("team2");
                            String team2 = jsonobject9.getString("name");


                            Matchpojo pj = new Matchpojo(seriesname, type, jsonobject2.getString("match_desc"), location, status, team1, team2, "", "", matchid);
                            ar1.add(pj);
                        }

                        else if (sub.equals("Inning"))
                        {
                            String matchdescription = jsonobject2.getString("toss") + ", " + jsonobject2.getString("match_desc");

                            JSONObject jsonobject3 = jsonobject.getJSONObject("venue");
                            String location = jsonobject3.getString("name") + ", " + jsonobject3.getString("location");

                            JSONObject jsonobject4 = jsonobject.getJSONObject("bat_team");
                            JSONArray jsonArray1 = jsonobject4.getJSONArray("innings");
                            JSONObject jsonobject5 = jsonArray1.getJSONObject(0);
                            String score1 = jsonobject5.getString("score") + "/" + jsonobject5.getString("wkts") + " in " + jsonobject5.getString("overs");



                            JSONObject jsonobject8 = jsonobject.getJSONObject("team1");
                            String team1 = jsonobject8.getString("name");
                            JSONObject jsonobject9 = jsonobject.getJSONObject("team2");
                            String team2 = jsonobject9.getString("name");


                            Matchpojo pj = new Matchpojo(seriesname, type, jsonobject2.getString("match_desc"), location, status, team1, team2, score1, "00/0", matchid);
                            ar1.add(pj);

                        }


                        else {
                            String matchdescription = jsonobject2.getString("toss") + ", " + jsonobject2.getString("match_desc");

                            JSONObject jsonobject3 = jsonobject.getJSONObject("venue");
                            String location = jsonobject3.getString("name") + ", " + jsonobject3.getString("location");

                            JSONObject jsonobject4 = jsonobject.getJSONObject("bat_team");
                            JSONArray jsonArray1 = jsonobject4.getJSONArray("innings");
                            JSONObject jsonobject5 = jsonArray1.getJSONObject(0);
                            String score1 = jsonobject5.getString("score") + "/" + jsonobject5.getString("wkts") + " in " + jsonobject5.getString("overs");

                            JSONObject jsonobject6 = jsonobject.getJSONObject("bow_team");
                            JSONArray jsonArray2 = jsonobject6.getJSONArray("innings");
                            JSONObject jsonobject7 = jsonArray2.getJSONObject(0);
                            String score2 = jsonobject7.getString("score") + "/" + jsonobject7.getString("wkts") + " in " + jsonobject7.getString("overs");

                            JSONObject jsonobject8 = jsonobject.getJSONObject("team1");
                            String team1 = jsonobject8.getString("name");
                            JSONObject jsonobject9 = jsonobject.getJSONObject("team2");
                            String team2 = jsonobject9.getString("name");


                            Matchpojo pj = new Matchpojo(seriesname, type, jsonobject2.getString("match_desc"), location, status, team1, team2, score1, score2, matchid);
                            ar1.add(pj);

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

                Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        }){

        };

        mQueue.add(request);






        return view;
    }
}