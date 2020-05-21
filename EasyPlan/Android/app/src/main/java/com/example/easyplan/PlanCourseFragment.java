package com.example.easyplan;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PlanCourseFragment extends Fragment {

    public PlanCourseFragment() {
        // Required empty public constructor
    }

    View view;
    private EditText planName;
    private CheckBox MWCheckbox, TTCheckbox, FCheckbox;
    private Spinner spinner1, spinner2, spinner3, spinner4, spinner5, spinner6, planSpinner, sizeSpinner;
    private Button course1, course2, course3, course4, course5, course6, course7, course8, planButton, deleteButton;
    private ArrayList<Course> courseList, labs;
    private ArrayList<Course> filteredList;
    private ArrayList<Button> buttonList = new ArrayList<Button>();
    private SharedPreferenceBot bot = new SharedPreferenceBot();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_course_plan_add, container, false);
        planSpinner = view.findViewById(R.id.planSpinner);
        spinner1 = view.findViewById(R.id.spinner1);
        spinner2 = view.findViewById(R.id.spinner2);
        spinner3 = view.findViewById(R.id.spinner3);
        spinner4 = view.findViewById(R.id.spinner4);
        spinner5 = view.findViewById(R.id.spinner5);
        spinner6 = view.findViewById(R.id.spinner6);
        sizeSpinner = view.findViewById(R.id.sizeSpinner);
        planName = view.findViewById(R.id.planName);
        MWCheckbox = view.findViewById(R.id.MWCheckbox);
        TTCheckbox = view.findViewById(R.id.TTCheckbox);
        FCheckbox = view.findViewById(R.id.FCheckbox);
        populateSpinners(view);

        spinner1.setEnabled(false); spinner2.setEnabled(false);
        spinner3.setEnabled(false); spinner4.setEnabled(false);
        spinner5.setEnabled(false); spinner6.setEnabled(false);

        planButton = view.findViewById(R.id.planButton);
        deleteButton = view.findViewById(R.id.delete);
        course1 = view.findViewById(R.id.course1); course2 = view.findViewById(R.id.course2);
        course3 = view.findViewById(R.id.course3); course4 = view.findViewById(R.id.course4);
        course5 = view.findViewById(R.id.course5); course6 = view.findViewById(R.id.course6);
        course7 = view.findViewById(R.id.course7); course8 = view.findViewById(R.id.course8);
        buttonList.add(course1); buttonList.add(course2); buttonList.add(course3); buttonList.add(course4);
        buttonList.add(course5); buttonList.add(course6); buttonList.add(course7); buttonList.add(course8);

        String name = "";
        Gson gson = new Gson();
        Type type = new TypeToken<List<Plan>>(){}.getType();
        List<Plan> plans = gson.fromJson((String)bot.getSharedPref("plans", getActivity()), type);
        int n;
        if(plans != null){
            n = plans.size();
            name = "Plan " + (n + 1);
        } else {
            n = 0;
        }

        for(int i = 0; i<n; i++){
            if(plans.get(i).getPlanName().equalsIgnoreCase(name)){
                name = "Plan " + (++n);
            }
        }
        planName.setText(name);

        Bundle bundle = getArguments();
        courseList = bundle.getParcelableArrayList("courseList");
        labs = bundle.getParcelableArrayList("labsList");
        String key = "";
        filteredList = new ArrayList<Course>();
        for (Course x : courseList) {
            if (key.equals("")) {
                filteredList.add(x);
                key = x.getSubject() + x.getCatalog();
            } else if (!key.equals(x.getSubject() + x.getCatalog())) {
                filteredList.add(x);
                key = x.getSubject() + x.getCatalog();
            }
        }
        configureButtons();
        setOnClickListeners();

        MWCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CompoundButton) view).isChecked()){
                    spinner1.setEnabled(true); spinner2.setEnabled(true);
                } else {
                    spinner1.setEnabled(false); spinner2.setEnabled(false);
                    spinner1.setSelection(0); spinner2.setSelection(6);
                }
            }
        });

        TTCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CompoundButton) view).isChecked()){
                    spinner3.setEnabled(true); spinner4.setEnabled(true);
                } else {
                    spinner3.setEnabled(false); spinner4.setEnabled(false);
                    spinner3.setSelection(0); spinner4.setSelection(6);
                }
            }
        });

        FCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CompoundButton) view).isChecked()){
                    spinner5.setEnabled(true); spinner6.setEnabled(true);
                } else {
                    spinner5.setEnabled(false); spinner6.setEnabled(false);
                    spinner5.setSelection(0); spinner6.setSelection(6);
                }
            }
        });
        MWCheckbox.setChecked(true); TTCheckbox.setChecked(true); FCheckbox.setChecked(true);
        spinner1.setEnabled(true); spinner2.setEnabled(true);
        spinner3.setEnabled(true); spinner4.setEnabled(true);
        spinner5.setEnabled(true); spinner6.setEnabled(true);

        planButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filteredList.size() >= Integer.valueOf(sizeSpinner.getSelectedItem().toString())){
                    if (spinner1.getSelectedItemPosition() > spinner2.getSelectedItemPosition() ||
                            spinner3.getSelectedItemPosition() > spinner4.getSelectedItemPosition() ||
                            spinner5.getSelectedItemPosition() > spinner6.getSelectedItemPosition()) {
                        Toast.makeText(getActivity(), "Start time cannot be equal or later than the end time.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Plan>>(){}.getType();
                    List<Plan> plans = gson.fromJson((String)bot.getSharedPref("plans", getActivity()), type);

                    Plan plan = new Plan("");
                    if(planSpinner.getSelectedItem().toString().equals("New Plan...")) {



                        if (bot.sharedPref(getActivity()).contains("plans")) {


                            plan.setPlanName(planName.getText().toString());
                            int size = Integer.valueOf(sizeSpinner.getSelectedItem().toString());
                            ArrayList<Course> tempList = new ArrayList<Course>();
                            for (Course x : courseList) {

                                int time = changeTime(x.getMtgStart());
                                if (MWCheckbox.isChecked() && (x.getMonday().equals("Y") || x.getWednesday().equals("Y"))) {
                                    if (Integer.valueOf(spinner1.getSelectedItem().toString().substring(0, spinner1.getSelectedItem().toString().indexOf(':'))) <= time &&
                                            Integer.valueOf(spinner2.getSelectedItem().toString().substring(0, spinner2.getSelectedItem().toString().indexOf(':'))) > time) {
                                        tempList.add(x); continue;
                                    }
                                }
                                if (TTCheckbox.isChecked() && (x.getTuesday().equals("Y") || x.getThursday().equals("Y"))) {
                                    if (Integer.valueOf(spinner3.getSelectedItem().toString().substring(0, spinner3.getSelectedItem().toString().indexOf(':'))) <= time &&
                                            Integer.valueOf(spinner4.getSelectedItem().toString().substring(0, spinner4.getSelectedItem().toString().indexOf(':'))) > time) {
                                        tempList.add(x); continue;
                                    }
                                }
                                if (FCheckbox.isChecked() && x.getFriday().equals("Y")) {
                                    if (Integer.valueOf(spinner5.getSelectedItem().toString().substring(0, spinner5.getSelectedItem().toString().indexOf(':'))) <= time &&
                                            Integer.valueOf(spinner6.getSelectedItem().toString().substring(0, spinner6.getSelectedItem().toString().indexOf(':'))) > time) {
                                           tempList.add(x); continue;
                                    }
                                }
                            }
                            if (tempList.size() >= 30) {
                                Toast.makeText(getActivity(), "Please add some constraints; the possible schedule size is too big.", Toast.LENGTH_LONG).show();
                                return;
                            }
                            plan.createSchedules(tempList, tempList.size(), size, labs);
                            //plan.deleteDuplicates();
                        } else {
                            //List<Plan> plans = new ArrayList<Plan>();
                            plan.setPlanName(planName.getText().toString());
                            int size = Integer.valueOf(sizeSpinner.getSelectedItem().toString());
                            ArrayList<Course> tempList = new ArrayList<Course>();
                            for (Course x : courseList) {

                                int time = changeTime(x.getMtgStart());
                                if (MWCheckbox.isChecked() && (x.getMonday().equals("Y") || x.getWednesday().equals("Y"))) {
                                    if (Integer.valueOf(spinner1.getSelectedItem().toString().substring(0, spinner1.getSelectedItem().toString().indexOf(':'))) <= time &&
                                            Integer.valueOf(spinner2.getSelectedItem().toString().substring(0, spinner2.getSelectedItem().toString().indexOf(':'))) > time) {
                                        tempList.add(x); continue;
                                    }
                                }
                                if (TTCheckbox.isChecked() && (x.getTuesday().equals("Y") || x.getThursday().equals("Y"))) {
                                    if (Integer.valueOf(spinner3.getSelectedItem().toString().substring(0, spinner3.getSelectedItem().toString().indexOf(':'))) <=
                                            Integer.valueOf(x.getMtgStart().substring(0, x.getMtgStart().indexOf(':'))) &&
                                            Integer.valueOf(spinner4.getSelectedItem().toString().substring(0, spinner4.getSelectedItem().toString().indexOf(':'))) >
                                                    Integer.valueOf(x.getMtgStart().substring(0, x.getMtgStart().indexOf(':')))) {
                                        tempList.add(x); continue;
                                    }
                                }
                                if (FCheckbox.isChecked() && x.getFriday().equals("Y")) {
                                    if (Integer.valueOf(spinner5.getSelectedItem().toString().substring(0, spinner5.getSelectedItem().toString().indexOf(':'))) <=
                                            Integer.valueOf(x.getMtgStart().substring(0, x.getMtgStart().indexOf(':'))) &&
                                            Integer.valueOf(spinner6.getSelectedItem().toString().substring(0, spinner6.getSelectedItem().toString().indexOf(':'))) >
                                                    Integer.valueOf(x.getMtgStart().substring(0, x.getMtgStart().indexOf(':')))) {
                                        tempList.add(x); continue;
                                    }
                                }
                            }
                            if (tempList.size() >= 30) {
                                Toast.makeText(getActivity(), "Please add some constraints; the possible schedule size is too big.", Toast.LENGTH_LONG).show();
                                return;
                            }
                            plan.createSchedules(tempList, tempList.size(), size, labs);
                            //plan.deleteDuplicates();
                            plans.add(plan);
                            bot.setSharedPref("plans", getActivity(), plans);
                        }
                    } else {
                        String s = planSpinner.getSelectedItem().toString();

                        plan.setPlanName(planName.getText().toString());
                        int size = Integer.valueOf(sizeSpinner.getSelectedItem().toString());
                        ArrayList<Course> tempList = new ArrayList<Course>();
                        for (Course x : courseList) {

                            int time = changeTime(x.getMtgStart());
                            if (MWCheckbox.isChecked() && (x.getMonday().equals("Y") || x.getWednesday().equals("Y"))) {
                                if (Integer.valueOf(spinner1.getSelectedItem().toString().substring(0, spinner1.getSelectedItem().toString().indexOf(':'))) <= time &&
                                        Integer.valueOf(spinner2.getSelectedItem().toString().substring(0, spinner2.getSelectedItem().toString().indexOf(':'))) > time) {
                                    tempList.add(x); continue;
                                }
                            }
                            if (TTCheckbox.isChecked() && (x.getTuesday().equals("Y") || x.getThursday().equals("Y"))) {
                                if (Integer.valueOf(spinner3.getSelectedItem().toString().substring(0, spinner3.getSelectedItem().toString().indexOf(':'))) <=
                                        Integer.valueOf(x.getMtgStart().substring(0, x.getMtgStart().indexOf(':'))) &&
                                        Integer.valueOf(spinner4.getSelectedItem().toString().substring(0, spinner4.getSelectedItem().toString().indexOf(':'))) >
                                                Integer.valueOf(x.getMtgStart().substring(0, x.getMtgStart().indexOf(':')))) {
                                    tempList.add(x); continue;
                                }
                            }
                            if (FCheckbox.isChecked() && x.getFriday().equals("Y")) {
                                if (Integer.valueOf(spinner5.getSelectedItem().toString().substring(0, spinner5.getSelectedItem().toString().indexOf(':'))) <=
                                        Integer.valueOf(x.getMtgStart().substring(0, x.getMtgStart().indexOf(':'))) &&
                                        Integer.valueOf(spinner6.getSelectedItem().toString().substring(0, spinner6.getSelectedItem().toString().indexOf(':'))) >
                                                Integer.valueOf(x.getMtgStart().substring(0, x.getMtgStart().indexOf(':')))) {
                                    tempList.add(x); continue;
                                }
                            }
                        }
                        if (tempList.size() >= 30) {
                            Toast.makeText(getActivity(), "Please add some constraints; the possible schedule size is too big.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        plan.createSchedules(tempList, tempList.size(), size, labs);
                        //.deleteDuplicates();

                        Plan tmp = new Plan("lul");
                        for (Plan x : plans) {
                            if (x.getPlanName().equals(s)) {
                                tmp = x;
                                break;
                            }
                        }
                        plans.remove(tmp);

                    }
                    if (plan.getSchedules().size() > 0) {
                        plans.add(plan);
                        bot.setSharedPref("plans", getActivity(), plans);
                        PlansFragment plansFrag = new PlansFragment();
                        FragmentTransaction trans = getFragmentManager().beginTransaction();
                        trans.replace(R.id.fragment, plansFrag, "Plans");
                        trans.commit();
                    } else {
                        Toast.makeText(getActivity(), "No schedule can be created with these courses/constraints.", Toast.LENGTH_LONG).show();
                        return;
                    }

                } else {
                    Toast.makeText(getActivity(), "Please make sure to select more courses than the selected size.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = planSpinner.getSelectedItem().toString();
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
                populateSpinners(view);
            }
        });

        planSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    planName.setEnabled(true);
                } else planName.setEnabled(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    public void populateSpinners (View view) {

        List<String> planList = new ArrayList<String>();
        //planList.add("Plans will be shown here. ");
        planList.add("2"); planList.add("3"); planList.add("4"); planList.add("5");
        planList.add("6"); planList.add("7");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, planList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(dataAdapter);

        List<String> list1 = new ArrayList<String>();
        //list1.add("Start Time");
        list1.add("8:30"); list1.add("10:00"); list1.add("11:30");
        list1.add("13:00"); list1.add("14:30"); list1.add("16:00");
        list1.add("17:30");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list1);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter1);

        List<String> list2 = new ArrayList<String>();
        //list2.add("End Time");
        list2.add("9:45"); list2.add("11:15");
        list2.add("12:45"); list2.add("14:15"); list2.add("15:45");
        list2.add("17:15"); list2.add("18:45");
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter2);

        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list1);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(dataAdapter3);

        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list2);
        dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(dataAdapter4);

        ArrayAdapter<String> dataAdapter5 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list1);
        dataAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner5.setAdapter(dataAdapter5);

        ArrayAdapter<String> dataAdapter6 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list2);
        dataAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner6.setAdapter(dataAdapter6);

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

        ArrayAdapter<String> dataAdapter7 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list3);
        dataAdapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        planSpinner.setAdapter(dataAdapter7);
        planSpinner.setSelection(0);
        sizeSpinner.setSelection(3);
        spinner1.setSelection(0);
        spinner3.setSelection(0);
        spinner5.setSelection(0);
        spinner2.setSelection(6);
        spinner4.setSelection(6);
        spinner6.setSelection(6);
    }

    public void configureButtons() {

        for (int i = 0; i < 8; i++) {
            if (filteredList.size() > i) {
                buttonList.get(i).setVisibility(View.VISIBLE);
                buttonList.get(i).setText(filteredList.get(i).getSubject() + filteredList.get(i).getCatalog());
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

    public int changeTime(String time) {
        int a = 0;

        if (time.equals("08:30 AM")) {
            a = 8;
        }
        else if (time.equals("10:00 AM")) {
            a = 10;
        }
        else if (time.equals("11:30 AM")) {
            a = 11;
        }
        else if (time.equals("01:00 PM")) {
            a = 13;
        }
        else if (time.equals("02:30 PM")) {
            a = 14;
        }
        else if (time.equals("04:00 PM")) {
            a = 16;
        }
        else if (time.equals("05:30 PM")) {
            a = 17;
        }
        return a;
    }
}
