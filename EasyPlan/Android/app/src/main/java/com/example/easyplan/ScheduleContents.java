package com.example.easyplan;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class ScheduleContents extends Fragment {
    RecyclerView courseRecycler;
    TextView delete, scheduleName;
    ImageView favorite_star;
    ArrayList<ScheduleContentItem> courseList;
    ScheduleContentAdapter adapter;
    Button prev, next;
    static boolean isFavorite;
    static int planID, scheduleID;
    static String spCode;
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
        View view = inflater.inflate(R.layout.fragment_schedule_contents, container, false);
        courseRecycler = view.findViewById(R.id.schedule_courses_recyler);
        delete = view.findViewById(R.id.schedule_delete_btn);
        favorite_star = view.findViewById(R.id.favorite_schedule_content);
        prev = view.findViewById(R.id.schedule_prev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPrevSchedule();
            }
        });
        next = view.findViewById(R.id.schedule_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNextSchedule();
            }
        });
        favorite_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addScheduleToFavorites(spCode);
            }
        });
        scheduleName = view.findViewById(R.id.schedule_header_name);
        courseRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        courseList = new ArrayList<>();
        adapter = new ScheduleContentAdapter(view.getContext(), courseList);
        courseRecycler.setAdapter(adapter);
        initScheduleData();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSchedule();
            }
        });
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ScheduleWeeklyView sch = new ScheduleWeeklyView(planID, scheduleID);
            FragmentTransaction trans = getFragmentManager().beginTransaction();
            trans.replace(R.id.fragment, sch, "Weekly");
            trans.commit();
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

        }
        adapter.notifyDataSetChanged();
        return view;
    }
    private void openPrevSchedule() {
        courseList = new ArrayList<>();
        adapter = new ScheduleContentAdapter(getContext(), courseList);
        courseRecycler.setAdapter(adapter);
        Gson gson = new Gson();
        Type type = new TypeToken<List<Plan>>() {
        }.getType();
        List<Plan> plans = gson.fromJson((String) bot.getSharedPref("plans", getActivity()), type);
        Plan plan = plans.get(planID);
        if(scheduleID == 0){
            scheduleID = plan.getSchedules().size() - 1;
        }else {
            scheduleID--;
        }
        Schedule currSchedule = plan.getSchedules().get(scheduleID);
        spCode = plan.getPlanName() + "-" + scheduleID;
        SharedPreferences sp = getActivity().getSharedPreferences("favSchedules", Context.MODE_PRIVATE);
        Boolean isFavorite = sp.getBoolean(spCode, false);
        if (isFavorite) {
            favorite_star.setImageResource(R.drawable.favorite_filled36);
        } else {
            favorite_star.setImageResource(R.drawable.favorite_empty36);
        }
        ArrayList<Course> courses = currSchedule.getCourseList();
        scheduleName.setText(plan.getPlanName() + " Schedule #" + (scheduleID + 1));
        for (Course c : courses) {
            String courseName = c.getSubject() + " " + c.getCatalog() + " - " + c.getSection();
            String instructorName = c.getProf();
            String meetingTime = c.getMeetingDays() + " " + c.getMtgStart() + " - " + c.getMtgEnd();
            courseList.add(new ScheduleContentItem(courseName, instructorName, meetingTime));
        }

        adapter.notifyDataSetChanged();
    }

    private void openNextSchedule() {
        courseList = new ArrayList<>();
        adapter = new ScheduleContentAdapter(getContext(), courseList);
        courseRecycler.setAdapter(adapter);
        Gson gson = new Gson();
        Type type = new TypeToken<List<Plan>>() {
        }.getType();
        List<Plan> plans = gson.fromJson((String) bot.getSharedPref("plans", getActivity()), type);
        Plan plan = plans.get(planID);
        if(scheduleID + 1 != plan.getSchedules().size()){
            scheduleID++;
        }else{
            scheduleID = 0;
        }
        Schedule currSchedule = plan.getSchedules().get(scheduleID);
        spCode = plan.getPlanName() + "-" + scheduleID;
        SharedPreferences sp = getActivity().getSharedPreferences("favSchedules", Context.MODE_PRIVATE);
        Boolean isFavorite = sp.getBoolean(spCode, false);
        if (isFavorite) {
            favorite_star.setImageResource(R.drawable.favorite_filled36);
        } else {
            favorite_star.setImageResource(R.drawable.favorite_empty36);
        }
        ArrayList<Course> courses = currSchedule.getCourseList();
        scheduleName.setText(plan.getPlanName() + " Schedule #" + (scheduleID + 1));
        for (Course c : courses) {
            String courseName = c.getSubject() + " " + c.getCatalog() + " - " + c.getSection();
            String instructorName = c.getProf();
            String meetingTime = c.getMeetingDays() + " " + c.getMtgStart() + " - " + c.getMtgEnd();
            courseList.add(new ScheduleContentItem(courseName, instructorName, meetingTime));
        }

        adapter.notifyDataSetChanged();
    }

    private void addScheduleToFavorites(String spCode) {
        SharedPreferences sp = getActivity().getSharedPreferences("favSchedules", Context.MODE_PRIVATE);
        Boolean isFavorite = sp.getBoolean(spCode, false);
        SharedPreferences.Editor editor = sp.edit();
        if (!isFavorite) {
            favorite_star.setImageResource(R.drawable.favorite_filled36);
            editor.putBoolean(spCode, true);

        } else {
            favorite_star.setImageResource(R.drawable.favorite_empty36);
            editor.putBoolean(spCode, false);
        }
        editor.commit();
    }

    private void initScheduleData() {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Plan>>() {
        }.getType();
        List<Plan> plans = gson.fromJson((String) bot.getSharedPref("plans", getActivity()), type);
        Plan plan = plans.get(planID);
        Schedule currSchedule = plan.getSchedules().get(scheduleID);
        spCode = plan.getPlanName() + "-" + scheduleID;
        SharedPreferences sp = getActivity().getSharedPreferences("favSchedules", Context.MODE_PRIVATE);
        Boolean isFavorite = sp.getBoolean(spCode, false);
        if (isFavorite) {
            favorite_star.setImageResource(R.drawable.favorite_filled36);
        } else {
            favorite_star.setImageResource(R.drawable.favorite_empty36);
        }
        ArrayList<Course> courses = currSchedule.getCourseList();
        scheduleName.setText(plan.getPlanName() + " Schedule #" + (scheduleID + 1));
        for (Course c : courses) {
            String courseName = c.getSubject() + " " + c.getCatalog() + " - " + c.getSection();
            String instructorName = c.getProf();
            String meetingTime = c.getMeetingDays() + " " + c.getMtgStart() + " - " + c.getMtgEnd();
            courseList.add(new ScheduleContentItem(courseName, instructorName, meetingTime));
        }
    }

    private void deleteSchedule() {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Plan>>() {
        }.getType();
        List<Plan> plans = gson.fromJson((String) bot.getSharedPref("plans", getActivity()), type);
        Plan plan = plans.get(planID);
        Plan tmp = plans.get(planID);
        for (int i = scheduleID; i < plan.getSchedules().size(); i++) {
            String spCode = plan.getPlanName() + "-" + i;
            SharedPreferences sp = getActivity().getSharedPreferences("favSchedules", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            Boolean isFavorite = sp.getBoolean(spCode, false);
            if (isFavorite && i == scheduleID) {
                editor.remove(spCode);
            } else if (isFavorite) {
                int difference = i - 1;
                String newSpCode = plan.getPlanName() + "-" + difference;
                editor.remove(spCode);
                editor.putBoolean(newSpCode, true);
            }
            editor.commit();

        }
        Schedule currSchedule = plan.getSchedules().get(scheduleID);
        plan.getSchedules().remove(currSchedule);
        plans.remove(tmp);
        if (plan.getSchedules().size() > 0) {
            plans.add(plan);
        }
        bot.setSharedPref("plans", getActivity(), plans);

        PlansFragment plansFrag = new PlansFragment();
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        trans.replace(R.id.fragment, plansFrag, "Plans");
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        trans.commit();
    }


}
