package com.example.easyplan;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easyplan.ui.ScheduleContentAdapter;
import com.example.easyplan.ui.ScheduleContentItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class ScheduleContents extends Fragment {
    RecyclerView courseRecycler;
    ArrayList<ScheduleContentItem> courseList;
    ScheduleContentAdapter adapter;
    int planID, scheduleID;
    private SharedPreferenceBot bot = new SharedPreferenceBot();

    public ScheduleContents() {
        // Required empty public constructor
    }

    public ScheduleContents(int group, int child) {
        planID = group;
        scheduleID = child;
    }







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  =inflater.inflate(R.layout.fragment_schedule_contents, container, false);
        courseRecycler = view.findViewById(R.id.schedule_courses_recyler);
        courseRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        courseList = new ArrayList<>();
        adapter = new ScheduleContentAdapter(view.getContext(), courseList);
        courseRecycler.setAdapter(adapter);
        initScheduleData();
        adapter.notifyDataSetChanged();
        return view;
    }

    private void initScheduleData() {
        Gson gson = new Gson();
        Type type = new TypeToken<Plan>() {
        }.getType();
        Plan plan = gson.fromJson((String) bot.getSharedPref("plan1", getActivity()), type);
        Schedule currSchedule = plan.getSchedules().get(scheduleID);
        ArrayList<Course> courses = currSchedule.getCourseList();
        for(Course c : courses){
            courseList.add(new ScheduleContentItem(c.getCatalog() + " " + c.getSubject()));
        }

    }


}
