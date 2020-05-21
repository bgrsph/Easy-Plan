package com.easyplan.easyplan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CourseInformationPopup extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<CourseInfoItemHelper> classes;
    CourseInfoAdapter adapter;
    TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_information_popup);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        recyclerView = findViewById(R.id.course_info_recyler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        title = findViewById(R.id.info_header_name);
        classes = new ArrayList<>();
        initClassSections();
        adapter = new CourseInfoAdapter(getBaseContext(), classes);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void initClassSections() {
        SharedPreferenceBot bot = new SharedPreferenceBot();
        Gson gson = new Gson();
        Type type = new TypeToken<List<CourseInfoItemHelper>>() {
        }.getType();
        List<CourseInfoItemHelper> allSections = gson.fromJson((String) bot.getSharedPref("courseInfoList", this), type);

        for (CourseInfoItemHelper info : allSections) {
            classes.add(info);

        }
        if (allSections.size() > 0)
            title.setText(allSections.get(0).courseName);

    }
}
