package com.example.easyplan;


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


/**
 * A simple {@link Fragment} subclass.
 */
public class PlansFragment extends Fragment {
    MainAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    View view;

    public PlansFragment() {

        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_plans_fragment, container, false);
        // get the listview
        expListView = (ExpandableListView) view.findViewById(R.id.plansExpandable);

        // preparing list data
        prepareListData();

        listAdapter = new MainAdapter(getActivity(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        return view;
    }

    public HashMap<String, String> getData(){

        return null;
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Plan#1");
        listDataHeader.add("Plan#2");
        listDataHeader.add("Plan#3");

        // Adding child data
        List<String> list1 = new ArrayList<String>();
        list1.add("schedule1");


        List<String> list2 = new ArrayList<String>();
        list2.add("schedule1");
        list2.add("schedule2");
        list2.add("schedule3");


        List<String> list3 = new ArrayList<String>();
        list3.add("schedule1");
        list3.add("schedule2");
        list3.add("schedule3");
        list3.add("schedule4");

        listDataChild.put(listDataHeader.get(0), list1); // Header, Child data
        listDataChild.put(listDataHeader.get(1), list2);
        listDataChild.put(listDataHeader.get(2), list3);
    }
  /*  private void initPlanData() {
        plans.add("Plan#1");
        plans.add("Plan#2");
        plans.add("Plan#3");

        String[] array = {"Schedule#1", "Schedule#2"};
        List<String> list1 = new ArrayList<>();
        for(String s : array){
            list1.add(s);
        }
        String[] array2 = {"Schedule#1", "Schedule#2", "Schedule#3"};
        List<String> list2 = new ArrayList<>();
        for(String s : array2){
            list2.add(s);
        }
        String[] array3 = {"Schedule#1", "Schedule#2"};
        List<String> list3 = new ArrayList<>();
        for(String s : array){
            list3.add(s);
        }
        planScheduleMap.put(plans.get(0), "schedule1");
        planScheduleMap.put(plans.get(1), "schedule2");
        planScheduleMap.put(plans.get(2), "schedule3");
//        adapter.notifyDataSetChanged();


    }*/


}
