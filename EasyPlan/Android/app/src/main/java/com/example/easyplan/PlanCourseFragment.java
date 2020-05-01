package com.example.easyplan;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PlanCourseFragment extends Fragment {

    public PlanCourseFragment() {
        // Required empty public constructor
    }

    View view;
    private EditText planName;
    private Spinner spinner1, spinner2, spinner3, planSpinner;
    private Button course1, course2, course3, course4, course5, course6, course7, course8, planButton, deleteButton;
    private ArrayList<Course> courseList;
    private ArrayList<Button> buttonList = new ArrayList<Button>();
    private SharedPreferenceBot bot = new SharedPreferenceBot();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_course_plan_add, container, false);
        planSpinner = view.findViewById(R.id.planSpinner);
        spinner1 = view.findViewById(R.id.spinner1);
        spinner2 = view.findViewById(R.id.spinner2);
        spinner3 = view.findViewById(R.id.spinner3);
        planName = view.findViewById(R.id.planName);
        populateSpinners(view, spinner1, spinner2, spinner3, planSpinner);

        /*SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity().getApplicationContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("MyObject", "");
        Type type = new TypeToken<List<Plan>>(){}.getType();
        List<Plan> students = gson.fromJson(json, type);*/


        planButton = view.findViewById(R.id.planButton);
        deleteButton = view.findViewById(R.id.delete);
        course1 = view.findViewById(R.id.course1); course2 = view.findViewById(R.id.course2);
        course3 = view.findViewById(R.id.course3); course4 = view.findViewById(R.id.course4);
        course5 = view.findViewById(R.id.course5); course6 = view.findViewById(R.id.course6);
        course7 = view.findViewById(R.id.course7); course8 = view.findViewById(R.id.course8);
        buttonList.add(course1); buttonList.add(course2); buttonList.add(course3); buttonList.add(course4);
        buttonList.add(course5); buttonList.add(course6); buttonList.add(course7); buttonList.add(course8);

        Bundle bundle = getArguments();
        courseList = bundle.getParcelableArrayList("courseList");
        configureButtons();
        setOnClickListeners();

        planButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinner3.getSelectedItem().toString().equals("New Plan...")) {
                    if (bot.sharedPref(getActivity()).contains("plans")) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Plan>>(){}.getType();
                        List<Plan> plans = gson.fromJson((String)bot.getSharedPref("plans", getActivity()), type);

                        Plan plan = new Plan(planName.getText().toString());
                        int size = Integer.valueOf(planSpinner.getSelectedItem().toString());
                        plan.createSchedules(courseList, courseList.size(), size);
                        plan.deleteDuplicates();
                        plans.add(plan);
                        bot.setSharedPref("plans", getActivity(), plans);
                    } else {
                        List<Plan> plans = new ArrayList<Plan>();
                        Plan plan = new Plan(planName.getText().toString());
                        int size = Integer.valueOf(planSpinner.getSelectedItem().toString());
                        plan.createSchedules(courseList, courseList.size(), size);
                        plan.deleteDuplicates();
                        plans.add(plan);
                        bot.setSharedPref("plans", getActivity(), plans);
                    }
                } else {
                    String s = spinner3.getSelectedItem().toString();
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Plan>>(){}.getType();
                    List<Plan> plans = gson.fromJson((String)bot.getSharedPref("plans", getActivity()), type);

                    Plan plan = new Plan(planName.getText().toString());
                    int size = Integer.valueOf(planSpinner.getSelectedItem().toString());
                    plan.createSchedules(courseList, courseList.size(), size);
                    plan.deleteDuplicates();

                    Plan tmp = new Plan("lul");
                    for (Plan x : plans) {
                        if (x.getPlanName().equals(s)) {
                            tmp = x;
                            break;
                        }
                    }
                    plans.remove(tmp);
                    plans.add(plan);
                    bot.setSharedPref("plans", getActivity(), plans);
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = spinner3.getSelectedItem().toString();
                Gson gson = new Gson();
                Type type = new TypeToken<List<Plan>>(){}.getType();
                List<Plan> plans = gson.fromJson((String)bot.getSharedPref("plans", getActivity()), type);

                Plan tmp = new Plan("lul");
                for (Plan x : plans) {
                    if (x.getPlanName().equals(s)) {
                        tmp = x;
                        break;
                    }
                }
                plans.remove(tmp);
                bot.setSharedPref("plans", getActivity(), plans);
                populateSpinners(view, spinner1, spinner2, spinner3, planSpinner);

            }
        });
        return view;
    }

    public void populateSpinners (View view, Spinner spinner1, Spinner spinner2, Spinner spinner3, Spinner planSpinner) {

        List<String> planList = new ArrayList<String>();
        //planList.add("Plans will be shown here. ");
        planList.add("2"); planList.add("3"); planList.add("4"); planList.add("5");
        planList.add("6"); planList.add("7");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, planList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        planSpinner.setAdapter(dataAdapter);

        List<String> list1 = new ArrayList<String>();
        list1.add("8:30"); list1.add("10:00"); list1.add("11:30");
        list1.add("13:00"); list1.add("14:30"); list1.add("16:00");
        list1.add("17:30");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list1);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter1);

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list1);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter2);

        List<String> list3 = new ArrayList<String>();
        list3.add("New Plan...");

        if (bot.sharedPref(getActivity()).contains("plans")) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Plan>>(){}.getType();
            ArrayList<Plan> plans = gson.fromJson((String)bot.getSharedPref("plans", getActivity()), type);
            for (Plan x : plans) {
                list3.add(x.getPlanName());
            }
        }

        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list3);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(dataAdapter3);
        spinner3.setSelection(0);
    }

    public void configureButtons() {

        for (int i = 0; i < 8; i++) {
            if (courseList.size() > i) {
                buttonList.get(i).setVisibility(View.VISIBLE);
                buttonList.get(i).setText(courseList.get(i).getSubject() + courseList.get(i).getCatalog());
            } else buttonList.get(i).setVisibility(View.INVISIBLE);
        }
    }

    public void setOnClickListeners() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag() == "unselected") {
                    v.setBackgroundResource(R.drawable.border3);
                    v.setTag("selected");
                } else {
                    v.setBackgroundResource(R.drawable.border2);
                    v.setTag("unselected");
                }
            }};

        for (Button x : buttonList) {
            x.setTag("unselected");
            x.setOnClickListener(listener);
        }
    }
}
