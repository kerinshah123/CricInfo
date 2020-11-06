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
import android.widget.TextView;
import android.widget.Toast;

import com.example.criinfo.More.MyTournamentTabs.TournamentPointtableFragment;
import com.example.criinfo.More.PointsTablePojo;
import com.example.criinfo.R;
import com.example.criinfo.Utils.Utils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TournamentsPointTableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TournamentsPointTableFragment extends Fragment {

    RecyclerView mypointstable;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences;
    String tournamentId;
    FirestoreRecyclerAdapter pointstableadapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;




    public TournamentsPointTableFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TournamentsPointTableFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TournamentsPointTableFragment newInstance(String param1, String param2) {
        TournamentsPointTableFragment fragment = new TournamentsPointTableFragment();
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
        View view= inflater.inflate(R.layout.fragment_tournaments_point_table, container, false);

        mypointstable=view.findViewById(R.id.mypointstable);

        sharedPreferences = getActivity().getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        tournamentId = sharedPreferences.getString("tournamentId", "");

        final Query query = FirebaseFirestore.getInstance()
                .collection("pointstable").whereEqualTo("LeagueId",tournamentId).orderBy("point", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<PointsTablePojo> options = new FirestoreRecyclerOptions.Builder<PointsTablePojo>()
                .setQuery(query, PointsTablePojo.class)
                .build();

        pointstableadapter = new FirestoreRecyclerAdapter<PointsTablePojo,Pointholder>(options) {
            @NonNull
            @Override
            public Pointholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pointstablelayout, parent, false);
                return new Pointholder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final Pointholder holder, int position, @NonNull PointsTablePojo model) {

                holder.teamwin.setText(String.valueOf(model.getMatchwin()));
                holder.teamloss.setText(String.valueOf(model.getMatchloss()));
                holder.teamplayed.setText(String.valueOf(model.getMatchPlayed()));
                holder.teampoints.setText(String.valueOf(model.getPoint()));

                db.collection("teams")
                        .document(model.getTeamId())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                holder.teamsortname.setText(String.valueOf(task.getResult().getString("sortname")));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        };
        mypointstable.setLayoutManager(new LinearLayoutManager(getActivity()));
        mypointstable.setAdapter(pointstableadapter);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        pointstableadapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        pointstableadapter.stopListening();
    }


    private class Pointholder extends RecyclerView.ViewHolder {
        TextView teamsortname,teamplayed,teamloss,teamwin,teampoints;

        public Pointholder(@NonNull View itemView) {
            super(itemView);
            teamsortname = itemView.findViewById(R.id.teamsortname);
            teamplayed = itemView.findViewById(R.id.teamplayed);
            teamloss = itemView.findViewById(R.id.teamloss);
            teamwin = itemView.findViewById(R.id.teamwin);
            teampoints = itemView.findViewById(R.id.teampoints);

        }
    }

}