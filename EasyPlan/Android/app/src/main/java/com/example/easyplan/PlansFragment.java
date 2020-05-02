package com.example.easyplan;


import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
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
                if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE) {
                    ScheduleWeeklyView sch = new ScheduleWeeklyView(groupPosition, childPosition);
                    FragmentTransaction trans = getFragmentManager().beginTransaction();
                    trans.replace(R.id.fragment, sch, "Weekly");
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                    trans.commit();
                } else if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT) {
                    ScheduleContents sch = new ScheduleContents(groupPosition, childPosition);
                    FragmentTransaction trans = getFragmentManager().beginTransaction();
                    trans.replace(R.id.fragment, sch, "ScheduleContents");
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                    trans.commit();

                }

                return true;
            }
        });

        return view;
    }

    private void initListData() {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Plan>>(){}.getType();
        List<Plan> plans = gson.fromJson((String) bot.getSharedPref("plans", getActivity()), type);
        if (plans != null) {
            for (int i = 0; i < plans.size(); i++) {
                Plan p = plans.get(i);
                if(p.getPlanName().equals("")){
                    p.setPlanName("Plan #" + (i+1));
                }
                listPlanGroups.add(p.getPlanName());
                List<String> planArr = new ArrayList<>();
                ArrayList<Schedule> schedules = p.getSchedules();
                    for (int j = 0; j < schedules.size(); j++) {
                        String name = "Schedule#" + (j + 1);
                        planArr.add(name);
                    }
                mapSchedulePlan.put(listPlanGroups.get(i), planArr);
            }
        }
        adapter.notifyDataSetChanged();
    }

}

