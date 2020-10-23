package com.example.criinfo.More.TournamentsTabs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.criinfo.R;
import com.example.criinfo.Utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TournamentsAboutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TournamentsAboutFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences ;

    TextView tournamentname,leaugemanager,location,country,startdate,enddate;

    public TournamentsAboutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TournamentsAboutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TournamentsAboutFragment newInstance(String param1, String param2) {
        TournamentsAboutFragment fragment = new TournamentsAboutFragment();
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
        View view = inflater.inflate(R.layout.fragment_tournaments_about, container, false);
        tournamentname = view.findViewById(R.id.tournamentname);
        leaugemanager = view.findViewById(R.id.leaguemanager);
        location = view.findViewById(R.id.loaction);
        country = view.findViewById(R.id.country);
        startdate = view.findViewById(R.id.startdate);
        enddate = view.findViewById(R.id.enddate);

        sharedPreferences = getActivity().getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        db.collection("tournaments")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (!task.getResult().isEmpty()) {
                                    if (document.getId().equals(sharedPreferences.getString("tournamentId", ""))) {
                                        tournamentname.setText(String.valueOf(document.get("tournament")));
                                        location.setText(String.valueOf(document.get("location")));
                                        country.setText(String.valueOf(document.get("country")));
                                        startdate.setText(String.valueOf(document.get("startdate")));
                                        enddate.setText(String.valueOf(document.get("enddate")));

                                        db.collection("user")
                                                .document(String.valueOf(document.get("leaguemanager")))
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        leaugemanager.setText(String.valueOf(task.getResult().get("name")));
                                                    }
                                                });


                                    }
                                } else {
                                }
                            }
                        }   }});




        return  view;
    }
}