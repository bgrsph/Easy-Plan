package com.example.easyplan;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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
    private SharedPreferenceBot bot = new SharedPreferenceBot();

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
        for (int i = 0; i < listPlanGroups.size(); i++) {
            planExpandable.expandGroup(i);
        }
        planExpandable.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getContext(), "clickedOnPlans", Toast.LENGTH_LONG).show();
                ScheduleContents sch = new ScheduleContents();
                FragmentTransaction trans = getFragmentManager().beginTransaction();
                trans.replace(R.id.fragment, sch, "ScheduleContents");
                trans.commit();
                return true;
            }
        });

        return view;
    }

    private void initListData() {
        Gson gson = new Gson();
        Type type = new TypeToken<Plan>() {
        }.getType();
        Plan plan = gson.fromJson((String) bot.getSharedPref("plan1", getActivity()), type);

        listPlanGroups.add(plan.getPlanName());
        ArrayList<Schedule> schedules = plan.getSchedules();
        int scCount = 1;
        List<String> planArr = new ArrayList<>();
        for (int i = 0; i < schedules.size(); i++) {
            String name = "Schedule#" + (scCount);
            planArr.add(name);
            scCount++;
        }
        mapSchedulePlan.put(listPlanGroups.get(0), planArr);
        adapter.notifyDataSetChanged();
    }

}

