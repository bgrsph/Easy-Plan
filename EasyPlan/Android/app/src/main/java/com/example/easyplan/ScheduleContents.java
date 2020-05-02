package com.example.easyplan;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class ScheduleContents extends Fragment{
    RecyclerView courseRecycler;
    TextView delete, scheduleName;
    ArrayList<ScheduleContentItem> courseList;
    ScheduleContentAdapter adapter;
    static int  planID, scheduleID;
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
        delete = view.findViewById(R.id.schedule_delete_btn);
        scheduleName = view.findViewById(R.id.schedule_header_name);
        courseRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        courseList = new ArrayList<>();
        adapter = new ScheduleContentAdapter(view.getContext(), courseList);
        courseRecycler.setAdapter(adapter);
        initScheduleData();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSchedule();
            }
        });
        if(this.getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE) {
            ScheduleWeeklyView sch = new ScheduleWeeklyView(planID, scheduleID);
            FragmentTransaction trans = getFragmentManager().beginTransaction();
            trans.replace(R.id.fragment, sch, "Weekly");
            trans.commit();
        } else if(this.getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT) {

        }
        adapter.notifyDataSetChanged();
        return view;
    }

    private void initScheduleData() {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Plan>>(){}.getType();
        List<Plan> plans = gson.fromJson((String) bot.getSharedPref("plans", getActivity()), type);
        Plan plan = plans.get(planID);
        Schedule currSchedule = plan.getSchedules().get(scheduleID);
        ArrayList<Course> courses = currSchedule.getCourseList();
        scheduleName.setText(plan.getPlanName() + " Schedule #" + (scheduleID +1) );
        for(Course c : courses){
            String courseName = c.getSubject()+ " " + c.getCatalog() + " - " + c.getSection();
            String instructorName = c.getProf();
            String meetingTime = c.getMeetingDays()+ " " + c.getMtgStart() + " - " + c.getMtgEnd();
            courseList.add(new ScheduleContentItem(courseName, instructorName, meetingTime));
        }
    }

    private void deleteSchedule(){
        Gson gson = new Gson();
        Type type = new TypeToken<List<Plan>>(){}.getType();
        List<Plan> plans = gson.fromJson((String)bot.getSharedPref("plans", getActivity()), type);
        Plan plan = plans.get(planID);
        Plan tmp = plans.get(planID);
        Schedule currSchedule = plan.getSchedules().get(scheduleID);
        plan.getSchedules().remove(currSchedule);
        plans.remove(tmp);
        if(plan.getSchedules().size() > 0){
            plans.add(plan);
        }
        bot.setSharedPref("plans", getActivity(), plans);

        PlansFragment plansFrag = new PlansFragment();
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        trans.replace(R.id.fragment, plansFrag, "Plans");
        trans.commit();
    }



}
