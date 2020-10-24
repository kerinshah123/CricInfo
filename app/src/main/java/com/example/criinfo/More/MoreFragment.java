package com.example.criinfo.More;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.criinfo.Home.HomeActivity;
import com.example.criinfo.LoginSignUpActivity;
import com.example.criinfo.More.TournamentsTabs.Tournaments;
import com.example.criinfo.R;
import com.example.criinfo.Utils.Utils;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoreFragment extends Fragment {
    Button teams;
  public   LinearLayout mytournament,tournamnet,myprofile,myteam,logoutlayout,aboutuslayout,contactuslayout;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor;
    String type ;
    CardView teamsCard,tournaments;

    public MoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MoreFragment newInstance(String param1, String param2) {
        MoreFragment fragment = new MoreFragment();
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
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        myteam=view.findViewById(R.id.myteam);
        logoutlayout=view.findViewById(R.id.logoutlayout);
        aboutuslayout=view.findViewById(R.id.aboutuslayout);
        contactuslayout=view.findViewById(R.id.contactuslayout);
        teamsCard = view.findViewById(R.id.teamsCard);
        mytournament= view.findViewById(R.id.mytournamentlayout);
        tournamnet=view.findViewById(R.id.tournamentlayout);
        myprofile=view.findViewById(R.id.myprofilelayout);
        tournaments=view.findViewById(R.id.tournaments);

        sharedPreferences = getContext().getSharedPreferences(Utils.SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        type=sharedPreferences.getString("type","");
        if (HomeActivity.usertype.equals("skip"))
        {
            logoutlayout.setVisibility(View.GONE);
        }

        myteam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("Team Manager") || type.equals("League Manager")){

                    Intent intent=new Intent(getActivity(), TeamManagerTeam.class);
                    startActivity(intent);
                }

                else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setTitle("Please Login")
                    .setMessage("To access this,you must have team manager account")
                            .setPositiveButton("Login", new DialogInterface.OnClickListener()                 {

                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.clear();
                                    editor.commit();
                                    Intent intent=new Intent(getContext(), LoginSignUpActivity.class);
                                    startActivity(intent);
                                }
                            }).setNegativeButton("Cancel", null);

                    AlertDialog alert1 = alert.create();
                    alert1.show();
                }

            }
        });

        mytournament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("League Manager")){

                    Intent intent=new Intent(getActivity(),CreateTournament.class);
                    startActivity(intent);
                }

                else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setTitle("Please Login")
                            .setMessage("To access this,you must have League Manager account")
                            .setPositiveButton("Login", new DialogInterface.OnClickListener()                 {

                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.clear();
                                    editor.commit();
                                    Intent intent=new Intent(getContext(), LoginSignUpActivity.class);
                                    startActivity(intent);
                                }
                            }).setNegativeButton("Cancel", null);

                    AlertDialog alert1 = alert.create();
                    alert1.show();
                }

            }
        });


        logoutlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setMessage("Are you sure?")
                            .setPositiveButton("Logout", new DialogInterface.OnClickListener()                 {

                                public void onClick(DialogInterface dialog, int which) {

                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.clear();
                                    editor.commit();

                                    Intent intent=new Intent(getContext(), LoginSignUpActivity.class);
                                    startActivity(intent);
                                }
                            }).setNegativeButton("Cancel", null);

                    AlertDialog alert1 = alert.create();
                    alert1.show();


            }
        });

        tournaments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Tournaments.class));
            }
        });
        teamsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),TeamsActivity.class));
            }
        });

        aboutuslayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), AboutUs.class);
                startActivity(intent);
            }
        });
        contactuslayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ContactUs.class);
                startActivity(intent);
            }
        });

        myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("Team Manager") || type.equals("League Manager")){

                    Intent intent=new Intent(getActivity(), MyProfile.class);
                    startActivity(intent);
                }

                else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setTitle("Please Login")
                            .setMessage("To access this,you must be login")
                            .setPositiveButton("Login", new DialogInterface.OnClickListener()                 {

                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.clear();
                                    editor.commit();
                                    Intent intent=new Intent(getContext(), LoginSignUpActivity.class);
                                    startActivity(intent);
                                }
                            }).setNegativeButton("Cancel", null);

                    AlertDialog alert1 = alert.create();
                    alert1.show();
                }

            }
        });



        return view;
    }


    }
