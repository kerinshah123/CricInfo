package com.example.criinfo.Match.Tabs;

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
 * Use the {@link PastMatchesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PastMatchesFragment extends Fragment {

    RecyclerView recyclerView;

    MatchAdapter adp;
    ArrayList<Matchpojo> ar1 ;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PastMatchesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PastMatchesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PastMatchesFragment newInstance(String param1, String param2) {
        PastMatchesFragment fragment = new PastMatchesFragment();
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
        View v = inflater.inflate(R.layout.fragment_past_matches, container, false);

        recyclerView = v.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.getPastMatches(Utils.apikey);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    parseData(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });



        return v;
    }

    private void parseData(String string) {
        try {
            JSONObject jsonObject = new JSONObject(string);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            ar1 = new ArrayList<>();

            for(int i =0;i<jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                callApi(Integer.parseInt(object.getString("unique_id")));

            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void callApi(int unique_id) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.getPastMatchesData(Utils.apikey,unique_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    parseMAtchData(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private void parseMAtchData(String string) {

        try {
            JSONObject jsonObject = new JSONObject(string);
            Matchpojo matchpojo = new Matchpojo();
            System.out.println(jsonObject);
            matchpojo.setTypeofmatch(jsonObject.getString("description"));
            matchpojo.setTeam1(jsonObject.getString("team-1"));
            matchpojo.setTeam2(jsonObject.getString("team-2"));
            matchpojo.setMatchdescription(jsonObject.getJSONObject("provider").getString("pubDate").substring(0,10));

            System.out.println(jsonObject.getString("description"));

            if(!jsonObject.getString("stat").equals("") || !jsonObject.getString("stat").equals("Live")){
                ar1.add(matchpojo);
                adp=new MatchAdapter(getContext(),ar1);
                recyclerView.setAdapter(adp);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}