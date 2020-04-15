package com.example.easyplan;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlansFragment extends Fragment implements AdapterView.OnItemClickListener {
    View view;
    List<String> listPlanGroups;
    HashMap<String, List<String>> mapSchedulePlan;
    ExpandableListView planExpandable;
    PlanAdapter planAdapter;
    int count;

    public PlansFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        count = 0;
        view = inflater.inflate(R.layout.fragment_plans_fragment, container, false);
        listPlanGroups = new ArrayList<>();
        mapSchedulePlan = new HashMap<>();
        planExpandable = view.findViewById(R.id.plansExpandable);

        planAdapter = new PlanAdapter(getContext(), listPlanGroups, mapSchedulePlan);
        createAPlan(2);
        createAPlan(3);
        createAPlan(4);
        createAPlan(1);
        planExpandable.setAdapter(planAdapter);
        for(int i = 0; i < planAdapter.getGroupCount(); i++)
            planExpandable.expandGroup(i);
        return view;
    }



    private void createAPlan(int sch){
        listPlanGroups.add("Plan#"+(count+1));
        List<String> schedules = new ArrayList<String>();
        if(sch > 0 ){
            for (int i = 1; i <= sch; i++){
                schedules.add("Schedule#" + i);
            }
        }
        mapSchedulePlan.put(listPlanGroups.get(count),schedules);
        count++;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getContext(), "mellow", Toast.LENGTH_SHORT);
    }
}

