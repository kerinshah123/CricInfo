package com.example.criinfo.News;

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
import com.example.criinfo.network.ApiClient;
import com.example.criinfo.network.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recyclerView;

    private RequestQueue mQueue;
    private StringRequest request;



    NewsAdapter adp;
    List<NewsPojo> ar1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
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
        View view = inflater.inflate(R.layout.fragment_news, container, false);


        recyclerView=view.findViewById(R.id.recycler);
        ar1=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Calendar c = Calendar.getInstance();
        String date = sdf.format(c.getTime());

//        mQueue = Volley.newRequestQueue(getContext());
//        request = new StringRequest(Request.Method.GET, "http://newsapi.org/v2/everything?language=en&q=cricket&from=2020-11-06&sortBy=popularity&apiKey=f8513c13f26b4d81b6d7f6128291b8a2", new com.android.volley.Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                try {
//
//                    JSONObject jsona = new JSONObject(response);
//
//                    JSONArray jsonArray = jsona.getJSONArray("articles");
//
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        NewsPojo pj = new NewsPojo(jsonObject.getString("urlToImage"), jsonObject.getString("author"), jsonObject.getString("url"), jsonObject.getString("title"), jsonObject.getString("publishedAt"));
//                        ar1.add(pj);
//                    }
//
//                    adp = new NewsAdapter(getContext(), ar1);
//                    recyclerView.setAdapter(adp);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Toast.makeText(getContext(), "Catch", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
//            }
//        }){
//
//        };
//
//        mQueue.add(request);

        ApiInterface apiInterface = ApiClient.getClientNews().create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.getNewsData("f8513c13f26b4d81b6d7f6128291b8a2","en","cricket",date);
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
        } );




        return view;
    }

    private void parseData(String string) {
        try {

            JSONObject jsona = new JSONObject(string);
            if(!jsona.isNull("articles")){
                JSONArray jsonArray = jsona.getJSONArray("articles");


                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    NewsPojo pj = new NewsPojo(jsonObject.getString("urlToImage"), jsonObject.getString("author"), jsonObject.getString("url"), jsonObject.getString("title"), jsonObject.getString("publishedAt"));
                    ar1.add(pj);
                }

                adp = new NewsAdapter(getContext(), ar1);
                recyclerView.setAdapter(adp);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}