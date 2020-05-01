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
public class PlansFragment extends Fragment {
    View view;
    List<String> listPlanGroups;
    HashMap<String, List<String>> mapSchedulePlan;
    ExpandableListView planExpandable;
    PlanAdapter adapter;
    public PlansFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_plans_fragment, container, false);
        listPlanGroups = new ArrayList<>();
        mapSchedulePlan = new HashMap<>();
        planExpandable = view.findViewById(R.id.plansExpandable);
        adapter = new PlanAdapter(view.getContext(), listPlanGroups, mapSchedulePlan);
        planExpandable.setAdapter(adapter);
        initListData();
        for(int i = 0; i<listPlanGroups.size(); i++){
            planExpandable.expandGroup(i);
        }

        return view;
    }

    private void initListData() {
        listPlanGroups.add("Plan1");
        listPlanGroups.add("Plan2");
        listPlanGroups.add("Plan3");
        listPlanGroups.add("Plan4");
        listPlanGroups.add("Plan5");

        String[] arr1 = {"Sc1", "Sc2", "Sc3"};
        String[] arr2 = {"Sc1", "Sc2", "Sc3"};
        String[] arr3 = {"Sc1", "Sc2"};
        String[] arr4 = {"Sc1", "Sc2"};
        String[] arr5 = {"Sc1"};
        List<String> list1 = new ArrayList<>();
        for(String i : arr1){
            list1.add(i);
        }

        List<String> list2 = new ArrayList<>();
        for(String i : arr2){
            list2.add(i);
        }
        List<String> list3 = new ArrayList<>();
        for(String i : arr3){
            list3.add(i);
        }
        List<String> list4 = new ArrayList<>();
        for(String i : arr4){
            list4.add(i);
        }
        List<String> list5 = new ArrayList<>();
        for(String i : arr5){
            list5.add(i);
        }
        mapSchedulePlan.put(listPlanGroups.get(0), list1);
        mapSchedulePlan.put(listPlanGroups.get(1), list2);
        mapSchedulePlan.put(listPlanGroups.get(2), list3);
        mapSchedulePlan.put(listPlanGroups.get(3), list4);
        mapSchedulePlan.put(listPlanGroups.get(4), list5);
        adapter.notifyDataSetChanged();
    }

}

