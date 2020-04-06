package com.example.easyplan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PlanCourseFragment extends Fragment {

    public PlanCourseFragment() {
        // Required empty public constructor
    }

    View view;
    private Spinner spinner1, spinner2, planSpinner;
    private Button course1, course2, course3, course4, course5, course6, course7, course8;
    private ArrayList<Course> courseList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_course_plan_add, container, false);
        populateSpinners(view);
        Bundle bundle = getArguments();
        courseList = bundle.getParcelableArrayList("courseList");
        configureButtons(view);

        return view;
    }

    public void populateSpinners (View view) {

        planSpinner = view.findViewById(R.id.planSpinner);
        List<String> planList = new ArrayList<String>();
        planList.add("Plans will be shown here. ");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, planList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        planSpinner.setAdapter(dataAdapter);

        spinner1 = view.findViewById(R.id.spinner1);
        List<String> list1 = new ArrayList<String>();
        list1.add("8:30"); list1.add("10:00"); list1.add("11:30");
        list1.add("13:00"); list1.add("14:30"); list1.add("16:00");
        list1.add("17:30");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list1);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter1);

        spinner2 = view.findViewById(R.id.spinner2);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list1);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter2);
    }

    public void configureButtons(View view) {

        course1 = view.findViewById(R.id.course1); course2 = view.findViewById(R.id.course2);
        course3 = view.findViewById(R.id.course3); course4 = view.findViewById(R.id.course4);
        course5 = view.findViewById(R.id.course5); course6 = view.findViewById(R.id.course6);
        course7 = view.findViewById(R.id.course7); course8 = view.findViewById(R.id.course8);

        ArrayList<Button> buttonList = new ArrayList<Button>();
        buttonList.add(course1); buttonList.add(course2); buttonList.add(course3); buttonList.add(course4);
        buttonList.add(course5); buttonList.add(course6); buttonList.add(course7); buttonList.add(course8);

        for (int i = 0; i < 8; i++) {
            if (courseList.size() > i) {
                buttonList.get(i).setVisibility(View.VISIBLE);
                buttonList.get(i).setText(courseList.get(i).getSubject() + courseList.get(i).getCatalog());
            } else buttonList.get(i).setVisibility(View.INVISIBLE);
        }

        // HAYATIMDA YAZDIĞIM EN KÖTÜ KOD
        /*if (courseList.size() == 1) {
            course1.setText(courseList.get(0).getSubject() + courseList.get(0).getCatalog());
        } else if (courseList.size() == 2) {
            course1.setText(courseList.get(0).getSubject() + courseList.get(0).getCatalog());
            course2.setText(courseList.get(1).getSubject() + courseList.get(1).getCatalog());
        } else if (courseList.size() == 3) {
            course1.setText(courseList.get(0).getSubject() + courseList.get(0).getCatalog());
            course2.setText(courseList.get(1).getSubject() + courseList.get(1).getCatalog());
            course3.setText(courseList.get(2).getSubject() + courseList.get(2).getCatalog());
        } else if (courseList.size() == 4) {
            course1.setText(courseList.get(0).getSubject() + courseList.get(0).getCatalog());
            course2.setText(courseList.get(1).getSubject() + courseList.get(1).getCatalog());
            course3.setText(courseList.get(2).getSubject() + courseList.get(2).getCatalog());
            course4.setText(courseList.get(3).getSubject() + courseList.get(3).getCatalog());
        } else if (courseList.size() == 5) {
            course1.setText(courseList.get(0).getSubject() + courseList.get(0).getCatalog());
            course2.setText(courseList.get(1).getSubject() + courseList.get(1).getCatalog());
            course3.setText(courseList.get(2).getSubject() + courseList.get(2).getCatalog());
            course4.setText(courseList.get(3).getSubject() + courseList.get(3).getCatalog());
            course5.setText(courseList.get(4).getSubject() + courseList.get(4).getCatalog());
        } else if (courseList.size() == 6) {
            course1.setText(courseList.get(0).getSubject() + courseList.get(0).getCatalog());
            course2.setText(courseList.get(1).getSubject() + courseList.get(1).getCatalog());
            course3.setText(courseList.get(2).getSubject() + courseList.get(2).getCatalog());
            course4.setText(courseList.get(3).getSubject() + courseList.get(3).getCatalog());
            course5.setText(courseList.get(4).getSubject() + courseList.get(4).getCatalog());
            course6.setText(courseList.get(5).getSubject() + courseList.get(5).getCatalog());
        } else if (courseList.size() == 7) {
            course1.setText(courseList.get(0).getSubject() + courseList.get(0).getCatalog());
            course2.setText(courseList.get(1).getSubject() + courseList.get(1).getCatalog());
            course3.setText(courseList.get(2).getSubject() + courseList.get(2).getCatalog());
            course4.setText(courseList.get(3).getSubject() + courseList.get(3).getCatalog());
            course5.setText(courseList.get(4).getSubject() + courseList.get(4).getCatalog());
            course6.setText(courseList.get(5).getSubject() + courseList.get(5).getCatalog());
            course7.setText(courseList.get(6).getSubject() + courseList.get(6).getCatalog());
        } else if (courseList.size() == 8) {
            course1.setText(courseList.get(0).getSubject() + courseList.get(0).getCatalog());
            course2.setText(courseList.get(1).getSubject() + courseList.get(1).getCatalog());
            course3.setText(courseList.get(2).getSubject() + courseList.get(2).getCatalog());
            course4.setText(courseList.get(3).getSubject() + courseList.get(3).getCatalog());
            course5.setText(courseList.get(4).getSubject() + courseList.get(4).getCatalog());
            course6.setText(courseList.get(5).getSubject() + courseList.get(5).getCatalog());
            course7.setText(courseList.get(6).getSubject() + courseList.get(6).getCatalog());
            course8.setText(courseList.get(7).getSubject() + courseList.get(7).getCatalog());
        } */
    }

}
