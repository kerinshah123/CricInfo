package com.example.criinfo.More.MyTeamTabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.criinfo.Info.Player;
import com.example.criinfo.More.MyTeamInfo;
import com.example.criinfo.More.Team;
import com.example.criinfo.More.TeamsActivity;
import com.example.criinfo.More.addPlayer;
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
 * Use the {@link MyTeamPlayer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyTeamPlayer extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    LinearLayout addPlayers;
    RecyclerView playerRecycler;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences ;
    String teamId,usetId;
    FirestoreRecyclerAdapter adapter;

    public MyTeamPlayer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyTeamPlayer.
     */
    // TODO: Rename and change types and number of parameters
    public static MyTeamPlayer newInstance(String param1, String param2) {
        MyTeamPlayer fragment = new MyTeamPlayer();
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

        View view = inflater.inflate(R.layout.fragment_my_team_player, container, false);
        playerRecycler = view.findViewById(R.id.playersRecycler);
        addPlayers = view.findViewById(R.id.addPlayerLayout);
        sharedPreferences = getContext().getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        usetId = sharedPreferences.getString("userId","");


        db.collection("teams")
                .whereEqualTo("managerId",usetId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(!task.getResult().isEmpty()){
                                    addPlayers.setVisibility(View.VISIBLE);
                                }
                                else {
                                    addPlayers.setVisibility(View.GONE);
                                }
                            }
                        } else {

                            addPlayers.setVisibility(View.GONE);
                        }
                    }
                });

        final Query query = FirebaseFirestore.getInstance()
                .collection("players").whereEqualTo("teamId",sharedPreferences.getString("teamId",""));

        FirestoreRecyclerOptions<Players> options = new FirestoreRecyclerOptions.Builder<Players>()
                .setQuery(query, Players.class)
                .build();

        adapter  = new FirestoreRecyclerAdapter<Players, PlayerHolder>(options) {
            @Override
            public void onBindViewHolder(PlayerHolder holder, final int position, final Players model) {
                System.out.println(model.getName());
                Glide.with(getActivity()).load(model.getImage())
                        .placeholder(R.drawable.logo)
                        .into(holder.image);
                holder.name.setText(model.getName());

            }

            @Override
            public PlayerHolder onCreateViewHolder(ViewGroup group, int i) {
                // Using a custom layout called R.layout.message for each item, we create a new instance of the viewholder
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.team_list, group, false);
                return new PlayerHolder(view);
            }
        };

        playerRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        playerRecycler.setAdapter(adapter);
        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private class PlayerHolder extends RecyclerView.ViewHolder {
        TextView name;
        CircularImageView image;
        LinearLayout team;
        public PlayerHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.team_name);
            image = itemView.findViewById(R.id.team_img);
            team = itemView.findViewById(R.id.team);
        }
    }
}