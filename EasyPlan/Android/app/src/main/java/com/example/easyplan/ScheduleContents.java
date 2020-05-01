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

import java.util.ArrayList;


public class ScheduleContents extends Fragment {
    RecyclerView courseRecycler;
    ArrayList<ScheduleContentItem> courseList;
    ScheduleContentAdapter adapter;


    public ScheduleContents() {
        // Required empty public constructor
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
        courseList.add(new ScheduleContentItem("Course name 1"));
        courseList.add(new ScheduleContentItem("Course name 2"));
        courseList.add(new ScheduleContentItem("Course name 3"));
        courseList.add(new ScheduleContentItem("Course name 4"));
        adapter.notifyDataSetChanged();
        return view;
    }


}
