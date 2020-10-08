package com.example.criinfo.More;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.criinfo.LoginSignUpActivity;
import com.example.criinfo.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoreFragment extends Fragment {
    Button teams;
    LinearLayout myteam,logoutlayout,aboutuslayout,contactuslayout;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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

        myteam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),teamManagerTeam.class);
                startActivity(intent);
            }
        });
//
//        teams=view.findViewById(R.id.teams);
//
//        teams.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getContext(),teamManagerTeam.class);
//                startActivity(intent);
//
//            }
//        });

        logoutlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setMessage("Are you sure?")
                            .setPositiveButton("Logout", new DialogInterface.OnClickListener()                 {

                                public void onClick(DialogInterface dialog, int which) {

                                    Intent intent=new Intent(getContext(), LoginSignUpActivity.class);
                                    startActivity(intent);
                                }
                            }).setNegativeButton("Cancel", null);

                    AlertDialog alert1 = alert.create();
                    alert1.show();


            }
        });

        aboutuslayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),aboutUs.class);
                startActivity(intent);
            }
        });
        contactuslayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),contactUs.class);
                startActivity(intent);
            }
        });

        return view;
    }
}