    package com.example.easyplan;


    import android.os.Bundle;

    import androidx.fragment.app.Fragment;

    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.AdapterView;
    import android.widget.ArrayAdapter;
    import android.widget.Spinner;
    import android.widget.Toast;


    /**
     * A simple {@link Fragment} subclass.
     */
    public class PersonalInfo extends Fragment {
        View view;
        Spinner facultySpinner;
        Spinner deptSpiner;
        String selectedFaculty;
        String selectedDepartment;


        public PersonalInfo() {
            // Required empty public constructor
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            view =inflater.inflate(R.layout.fragment_personal_info, container, false);
            selectedFaculty = "";
            selectedDepartment = "";
            createSpinners(view);
            return view;
        }

        public void createSpinners(View view){
            //FACULTY SPINNER
            facultySpinner = view.findViewById(R.id.FacultySpinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.faculty_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            facultySpinner.setAdapter(adapter);
            facultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedFaculty = parent.getItemAtPosition(position).toString();
                    setupDepartmentSpinner();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }

        public void setupDepartmentSpinner(){
            //DEPARTMENT SPINNER

            deptSpiner = view.findViewById(R.id.DepartmentSpinner);
            ArrayAdapter<CharSequence> deptAdapter  = ArrayAdapter.createFromResource(getActivity(),R.array.empty_array, android.R.layout.simple_spinner_item);;
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
                    break;
                case "Nursing":
                    deptAdapter  = ArrayAdapter.createFromResource(getActivity(),R.array.nurse_dept, android.R.layout.simple_spinner_item);
                    break;
                case "School of Medicine":
                    deptAdapter  = ArrayAdapter.createFromResource(getActivity(),R.array.med_dept, android.R.layout.simple_spinner_item);
                    break;
                default:
                    break;
            }

            deptAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            deptSpiner.setAdapter(deptAdapter);
            deptSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedFaculty = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(),selectedDepartment, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

    }
