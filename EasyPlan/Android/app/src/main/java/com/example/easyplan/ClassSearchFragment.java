package com.example.easyplan;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClassSearchFragment extends Fragment {


    TextView text;
    private String accEmail;
    public ClassSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        accEmail = this.getArguments().getString("accEmail");
        View view = inflater.inflate(R.layout.fragment_class_search, container, false);
        return inflater.inflate(R.layout.fragment_class_search, container, false);
    }

}
