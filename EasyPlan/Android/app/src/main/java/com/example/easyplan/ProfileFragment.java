package com.example.easyplan;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    Button logout;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_profile, container, false);
        logout = (Button) v.findViewById(R.id.logoutBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getActivity().getSharedPreferences("checkbox", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.remove("remember");
                editor.commit();
//                finish();
                startActivity(new Intent(getActivity(), WelcomePage.class));
            }
        });

        return  v;
    }


    }
