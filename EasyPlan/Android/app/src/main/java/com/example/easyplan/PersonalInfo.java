package com.example.easyplan;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalInfo extends Fragment {
    FirebaseAuth mAuth;
    View view;
    Spinner facultySpinner, deptSpiner, academicSpinner, semesterSpinner;
    TextView save;
    String selectedFaculty, selectedDepartment, selectedAcademic, selectedSemester;
    ArrayAdapter<CharSequence> adapter;


    public PersonalInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_personal_info, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        mAuth = FirebaseAuth.getInstance();
        save = view.findViewById(R.id.SavePersonalInfo);
        selectedFaculty = "";
        selectedDepartment = "";
        selectedAcademic = "";
        selectedSemester = "";
        createSpinners(view);
        savePersonalInfo();
        return view;
    }

    public void createSpinners(View view){
        //FACULTY SPINNER

        facultySpinner = view.findViewById(R.id.FacultySpinner);
        final ArrayAdapter<CharSequence>  deptAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.empty_array, android.R.layout.simple_spinner_item);
       adapter = ArrayAdapter.createFromResource(getActivity(),R.array.faculty_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        facultySpinner.setAdapter(adapter);
        SharedPreferences sp = getContext().getSharedPreferences("userPref", Context.MODE_PRIVATE);
        String shownFaculty = sp.getString("savedFaculty", "");
        if(shownFaculty.length() > 0){
            int pos = adapter.getPosition(shownFaculty);
            facultySpinner.setSelection(pos);
        }
        facultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    selectedFaculty = parent.getItemAtPosition(position).toString();
                    setupDepartmentSpinner(deptAdapter);
                    setUpAcademicSemesterLevelSpinner();
                    setUpSemesterSpinner();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    public void setupDepartmentSpinner( ArrayAdapter deptAdapter){
        //DEPARTMENT SPINNER
        deptAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.empty_array, android.R.layout.simple_spinner_item);
        deptSpiner = view.findViewById(R.id.DepartmentSpinner);
        deptAdapter.setNotifyOnChange(true);
        switch (selectedFaculty){
            case "College of Administrative Science and Economics":
                deptAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.case_dept, android.R.layout.simple_spinner_item);
                break;
            case "College of Engineering":
                deptAdapter  = ArrayAdapter.createFromResource(getActivity(),R.array.eng_dept, android.R.layout.simple_spinner_item);
                break;
            case "College of Science":
                deptAdapter  = ArrayAdapter.createFromResource(getActivity(),R.array.scie_dept, android.R.layout.simple_spinner_item);
                break;
            case "College of Social Sciences and Humanities":
                deptAdapter  = ArrayAdapter.createFromResource(getActivity(),R.array.cssh_dept, android.R.layout.simple_spinner_item);
                break;
            case "Law School":
                deptAdapter  = ArrayAdapter.createFromResource(getActivity(),R.array.law_dept, android.R.layout.simple_spinner_item);
                selectedDepartment = deptAdapter.getItem(0).toString();
                break;
            case "Nursing":
                deptAdapter  = ArrayAdapter.createFromResource(getActivity(),R.array.nurse_dept, android.R.layout.simple_spinner_item);
                selectedDepartment = deptAdapter.getItem(0).toString();
                break;
            case "School of Medicine":
                deptAdapter  = ArrayAdapter.createFromResource(getActivity(),R.array.med_dept, android.R.layout.simple_spinner_item);
                selectedDepartment = deptAdapter.getItem(0).toString();
                break;
                default:
                    deptAdapter  =    ArrayAdapter.createFromResource(getActivity(),R.array.empty_array, android.R.layout.simple_spinner_item);
        }

        deptAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deptSpiner.setAdapter(deptAdapter);
        SharedPreferences sp = getContext().getSharedPreferences("userPref", Context.MODE_PRIVATE);
        String saved = sp.getString("savedDept", "");
        if(saved.length() > 0){
            int pos = deptAdapter.getPosition(saved);
            deptSpiner.setSelection(pos);
        }
        deptSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    selectedDepartment = parent.getItemAtPosition(position).toString();
                    setUpAcademicSemesterLevelSpinner();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setUpAcademicSemesterLevelSpinner(){
        academicSpinner = view.findViewById(R.id.AcademicLeveLSpinner);
        ArrayAdapter<CharSequence> academicAdapter  = ArrayAdapter.createFromResource(getActivity(),R.array.levels, android.R.layout.simple_spinner_item);

        switch (selectedFaculty){
            case "School of Medicine":
                academicAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.levels_som, android.R.layout.simple_spinner_item);
                break;
                default:
                    academicAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.levels, android.R.layout.simple_spinner_item);
        }
        academicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        academicSpinner.setAdapter(academicAdapter);
        SharedPreferences sp = getContext().getSharedPreferences("userPref", Context.MODE_PRIVATE);
        String saved = sp.getString("savedLevel", "");
        if(saved.length() > 0){
            int pos = academicAdapter.getPosition(saved);
            academicSpinner.setSelection(pos);
        }
        academicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    selectedAcademic =  parent.getItemAtPosition(position).toString();
                    setUpSemesterSpinner();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }
    public void setUpSemesterSpinner(){
        ArrayAdapter<CharSequence> semesterAdapter  = ArrayAdapter.createFromResource(getActivity(),R.array.semester, android.R.layout.simple_spinner_item);
        semesterSpinner =  view.findViewById(R.id.SemesterSpinner);
        switch (selectedFaculty){
            case "School of Medicine":
                semesterAdapter  = ArrayAdapter.createFromResource(getActivity(),R.array.empty_array, android.R.layout.simple_spinner_item);
                break;
        }
        semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semesterSpinner.setAdapter(semesterAdapter);
        SharedPreferences sp = getContext().getSharedPreferences("userPref", Context.MODE_PRIVATE);
        String saved = sp.getString("savedSemester", "");
        if(saved.length() > 0){
            int pos = semesterAdapter.getPosition(saved);
            semesterSpinner.setSelection(pos);
        }
        semesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    selectedSemester =  parent.getItemAtPosition(position).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void savePersonalInfo(){
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getContext().getSharedPreferences("userPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                String user_id = sp.getString("userID", "");
                DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("userInfo").child(user_id);
                Map userInfo = new HashMap();
                userInfo.put("faculty", selectedFaculty);
                userInfo.put("dept", selectedDepartment);
                userInfo.put("level", selectedAcademic);
                userInfo.put("semester", selectedSemester);
                current_user_db.setValue(userInfo);
               
                editor.putString("savedFaculty", selectedFaculty);
                editor.putString("savedDept", selectedDepartment);
                editor.putString("savedLevel", selectedAcademic);
                editor.putString("savedSemester", selectedSemester);
                editor.apply();

                ProfileFragment pf = new ProfileFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment, pf, "Profile");
                ft.commit();
            }
        });

    }

}

