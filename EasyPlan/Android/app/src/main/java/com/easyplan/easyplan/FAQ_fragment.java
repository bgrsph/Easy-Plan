package com.easyplan.easyplan;

import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FAQ_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, String> expandableListDetail;
    View view;

    public FAQ_fragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_faq_fragment, container, false);
        expandableListView = (ExpandableListView) view.findViewById(R.id.faqExpandible);
        expandableListDetail = getData();
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);




        return view;
    }

    public HashMap<String, String> getData(){
        Resources res = getResources();
        String[] questions = res.getStringArray(R.array.question_list);
        String[] answers = res.getStringArray(R.array.answer_list);
        HashMap<String, String> expandableListDetail = new HashMap<>();
        for(int i = 0; i<questions.length; i++){
            expandableListDetail.put(questions[i], answers[i]);
        }
        return expandableListDetail;
    }

}
