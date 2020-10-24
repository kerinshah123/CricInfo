package com.example.criinfo.More.TournamentsTabs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.criinfo.More.MyTournamentTabs.Team;
import com.example.criinfo.More.MyTournamentTabs.TournamentTeamsFragment;
import com.example.criinfo.R;
import com.example.criinfo.Utils.Utils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mikhaellopez.circularimageview.CircularImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TournamentsTeamsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TournamentsTeamsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences;
    String teamId, usetId, tournamentId;
    FirestoreRecyclerAdapter adapter;

    public TournamentsTeamsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TournamentsTeamsFreagment.
     */
    // TODO: Rename and change types and number of parameters
    public static TournamentsTeamsFragment newInstance(String param1, String param2) {
        TournamentsTeamsFragment fragment = new TournamentsTeamsFragment();
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
        View view = inflater.inflate(R.layout.fragment_tournaments_teams_freagment, container, false);
        recyclerView = view.findViewById(R.id.tournamnetTeamRecycleruser);



        sharedPreferences = getContext().getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //usetId = sharedPreferences.getString("userId", "");
        teamId = sharedPreferences.getString("teamId", "");
        tournamentId = sharedPreferences.getString("tournamentId", "");

//        db.collection("tournaments")
//                .whereEqualTo("leaguemanager", usetId)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                if (!task.getResult().isEmpty()) {
//                                    //addTeam.setVisibility(View.VISIBLE);
//                                } else {
//                                    //addTeam.setVisibility(View.GONE);
//                                }
//                            }
//                        } else {
//
//                            //addTeam.setVisibility(View.GONE);
//                        }
//                    }
//                });


        final Query query = FirebaseFirestore.getInstance()
                .collection("teams").whereArrayContains("leagueId", tournamentId);

        FirestoreRecyclerOptions<Team> options = new FirestoreRecyclerOptions.Builder<Team>()
                .setQuery(query, Team.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Team, TournamentsTeamsFragment.TeamHolder>(options) {
            @Override
            public void onBindViewHolder(TournamentsTeamsFragment.TeamHolder holder, final int position, final Team model) {

                Glide.with(getActivity()).load(model.getImage())
                        .placeholder(R.drawable.profile)
                        .into(holder.image);
                holder.name.setText(model.getName());

            }

            @Override
            public TeamHolder onCreateViewHolder(ViewGroup group, int i) {
                // Using a custom layout called R.layout.message for each item, we create a new instance of the viewholder
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.team_list, group, false);
                return new TeamHolder(view);
            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onResume() {
        super.onResume();
//        db.collection("tournaments")
//                .whereEqualTo("leaguemanager", usetId)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                if (!task.getResult().isEmpty()) {
//                                    //addTeam.setVisibility(View.VISIBLE);
//                                } else {
//                                   // addTeam.setVisibility(View.GONE);
//                                }
//                            }
//                        } else {
//
//                            //addTeam.setVisibility(View.GONE);
//                        }
//                    }
//                });
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private class TeamHolder extends RecyclerView.ViewHolder {
        TextView name;
        CircularImageView image;
        LinearLayout team;

        public TeamHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.team_name);
            image = itemView.findViewById(R.id.team_img);
            team = itemView.findViewById(R.id.team);
        }
    }
}