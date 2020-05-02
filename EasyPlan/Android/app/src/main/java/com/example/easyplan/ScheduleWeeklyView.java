package com.example.easyplan;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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


public class ScheduleWeeklyView extends Fragment {
    TextView monday8, monday10, monday11, monday13, monday14, monday16, monday17;
    TextView tuesday8, tuesday10, tuesday11, tuesday13, tuesday14, tuesday16, tuesday17;
    TextView wednesday8, wednesday10, wednesday11, wednesday13, wednesday14, wednesday16, wednesday17;
    TextView thursday8, thursday10, thursday11, thursday13, thursday14, thursday16, thursday17;
    TextView friday8, friday10, friday11, friday13, friday14, friday16, friday17;

    CardView monday8CV, monday10CV, monday11CV, monday13CV, monday14CV, monday16CV, monday17CV;
    CardView tuesday8CV, tuesday10CV, tuesday11CV, tuesday13CV, tuesday14CV, tuesday16CV, tuesday17CV;
    CardView wednesday8CV, wednesday10CV, wednesday11CV, wednesday13CV, wednesday14CV, wednesday16CV, wednesday17CV;
    CardView thursday8CV, thursday10CV, thursday11CV, thursday13CV, thursday14CV, thursday16CV, thursday17CV;
    CardView friday8CV, friday10CV, friday11CV, friday13CV, friday14CV, friday16CV, friday17CV;

    ArrayList<ScheduleContentItem> courseList;
    ScheduleContentAdapter adapter;
    private SharedPreferenceBot bot = new SharedPreferenceBot();
    static int planID, scheduleID;

    public ScheduleWeeklyView() {
        // Required empty public constructor
    }

    public ScheduleWeeklyView(int group, int child) {
        planID = group;
        scheduleID = child;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule_weekly_view, container, false);
        monday8 = view.findViewById(R.id.weekly_monday_0830_course_name);
        monday10 = view.findViewById(R.id.weekly_monday_1000_course_name);
        monday11 = view.findViewById(R.id.weekly_monday_1130_course_name);
        monday13 = view.findViewById(R.id.weekly_monday_1300_course_name);
        monday14 = view.findViewById(R.id.weekly_monday_1430_course_name);
        monday16 = view.findViewById(R.id.weekly_monday_1600_course_name);
        monday17 = view.findViewById(R.id.weekly_monday_1730_course_name);
        tuesday8 = view.findViewById(R.id.weekly_tuesday_0830_course_name);
        tuesday10 = view.findViewById(R.id.weekly_tuesday_1000_course_name);
        tuesday11 = view.findViewById(R.id.weekly_tuesday_1130_course_name);
        tuesday13 = view.findViewById(R.id.weekly_tuesday_1300_course_name);
        tuesday14 = view.findViewById(R.id.weekly_tuesday_1430_course_name);
        tuesday16 = view.findViewById(R.id.weekly_tuesday_1600_course_name);
        tuesday17 = view.findViewById(R.id.weekly_tuesday_1730_course_name);
        wednesday8 = view.findViewById(R.id.weekly_wednesday_0830_course_name);
        wednesday10 = view.findViewById(R.id.weekly_wednesday_1000_course_name);
        wednesday11 = view.findViewById(R.id.weekly_wednesday_1130_course_name);
        wednesday13 = view.findViewById(R.id.weekly_wednesday_1300_course_name);
        wednesday14 = view.findViewById(R.id.weekly_wednesday_1430_course_name);
        wednesday16 = view.findViewById(R.id.weekly_wednesday_1600_course_name);
        wednesday17 = view.findViewById(R.id.weekly_wednesday_1730_course_name);
        thursday8 = view.findViewById(R.id.weekly_thursday_0830_course_name);
        thursday10 = view.findViewById(R.id.weekly_thursday_1000_course_name);
        thursday11 = view.findViewById(R.id.weekly_thursday_1130_course_name);
        thursday13 = view.findViewById(R.id.weekly_thursday_1300_course_name);
        thursday14 = view.findViewById(R.id.weekly_thursday_1430_course_name);
        thursday16 = view.findViewById(R.id.weekly_thursday_1600_course_name);
        thursday17 = view.findViewById(R.id.weekly_thursday_1730_course_name);
        friday8 = view.findViewById(R.id.weekly_friday_0830_course_name);
        friday10 = view.findViewById(R.id.weekly_friday_1000_course_name);
        friday11 = view.findViewById(R.id.weekly_friday_1130_course_name);
        friday13 = view.findViewById(R.id.weekly_friday_1300_course_name);
        friday14 = view.findViewById(R.id.weekly_friday_1430_course_name);
        friday16 = view.findViewById(R.id.weekly_friday_1600_course_name);
        friday17 = view.findViewById(R.id.weekly_friday_1730_course_name);

        monday8CV = view.findViewById(R.id.weekly_monday_0830_card);
        monday10CV = view.findViewById(R.id.weekly_monday_1000_card);
        monday11CV = view.findViewById(R.id.weekly_monday_1130_card);
        monday13CV = view.findViewById(R.id.weekly_monday_1300_card);
        monday14CV = view.findViewById(R.id.weekly_monday_1430_card);
        monday16CV = view.findViewById(R.id.weekly_monday_1600_card);
        monday17CV = view.findViewById(R.id.weekly_monday_1730_card);

        tuesday8CV = view.findViewById(R.id.weekly_tuesday_0830_card);
        tuesday10CV = view.findViewById(R.id.weekly_tuesday_1000_card);
        tuesday11CV = view.findViewById(R.id.weekly_tuesday_1130_card);
        tuesday13CV = view.findViewById(R.id.weekly_tuesday_1300_card);
        tuesday14CV = view.findViewById(R.id.weekly_tuesday_1430_card);
        tuesday16CV = view.findViewById(R.id.weekly_tuesday_1600_card);
        tuesday17CV = view.findViewById(R.id.weekly_tuesday_1730_card);

        wednesday8CV = view.findViewById(R.id.weekly_wednesday_0830_card);
        wednesday10CV = view.findViewById(R.id.weekly_wednesday_1000_card);
        wednesday11CV = view.findViewById(R.id.weekly_wednesday_1130_card);
        wednesday13CV = view.findViewById(R.id.weekly_wednesday_1300_card);
        wednesday14CV = view.findViewById(R.id.weekly_wednesday_1430_card);
        wednesday16CV = view.findViewById(R.id.weekly_wednesday_1600_card);
        wednesday17CV = view.findViewById(R.id.weekly_wednesday_1730_card);

        thursday8CV = view.findViewById(R.id.weekly_thursday_0830_card);
        thursday10CV = view.findViewById(R.id.weekly_thursday_1000_card);
        thursday11CV = view.findViewById(R.id.weekly_thursday_1130_card);
        thursday13CV = view.findViewById(R.id.weekly_thursday_1300_card);
        thursday14CV = view.findViewById(R.id.weekly_thursday_1430_card);
        thursday16CV = view.findViewById(R.id.weekly_thursday_1600_card);
        thursday17CV = view.findViewById(R.id.weekly_thursday_1730_card);

        friday8CV = view.findViewById(R.id.weekly_friday_0830_card);
        friday10CV = view.findViewById(R.id.weekly_friday_1000_card);
        friday11CV = view.findViewById(R.id.weekly_friday_1130_card);
        friday13CV = view.findViewById(R.id.weekly_friday_1300_card);
        friday14CV = view.findViewById(R.id.weekly_friday_1430_card);
        friday16CV = view.findViewById(R.id.weekly_friday_1600_card);
        friday17CV = view.findViewById(R.id.weekly_friday_1730_card);
        initScheduleData();
        if(this.getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE) {

        } else if(this.getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT) {
            ScheduleContents sch = new ScheduleContents(planID, scheduleID);
            FragmentTransaction trans = getFragmentManager().beginTransaction();
            trans.replace(R.id.fragment, sch, "Weekly");
            trans.commit();
        }
        return view;
    }

    private void initScheduleData() {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Plan>>() {
        }.getType();
        List<Plan> plans = gson.fromJson((String) bot.getSharedPref("plans", getActivity()), type);
        Plan plan = plans.get(planID);
        Schedule currSchedule = plan.getSchedules().get(scheduleID);
        ArrayList<Course> courses = currSchedule.getCourseList();
        for (Course c : courses) {
            String courseName = c.getSubject() + " " + c.getCatalog() + " - " + c.getSection();
            String meetingTime = c.getMeetingDays() + " " + c.getMtgStart() + " - " + c.getMtgEnd();
            if (meetingTime.contains("Mon") && meetingTime.contains("08")) {
                monday8.setText(courseName);
                monday8CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }
            if (meetingTime.contains("Mon") && meetingTime.contains("10")) {
                monday10.setText(courseName);
                monday10CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }
            if (meetingTime.contains("Mon") && meetingTime.contains("11")) {
                monday11.setText(courseName);
                monday11CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }
            if (meetingTime.contains("Mon") && meetingTime.contains("13")) {
                monday13.setText(courseName);
                monday13CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }
            if (meetingTime.contains("Mon") && meetingTime.contains("02")) {
                monday14.setText(courseName);
                monday14CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }
            if (meetingTime.contains("Mon") && meetingTime.contains("16")) {
                monday16.setText(courseName);
                monday16CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }
            if (meetingTime.contains("Mon") && meetingTime.contains("05")) {
                monday17.setText(courseName);
                monday17CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }

            if (meetingTime.contains("Tue") && meetingTime.contains("08")) {
                tuesday8.setText(courseName);
                tuesday8CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }
            if (meetingTime.contains("Tue") && meetingTime.contains("10")) {
                tuesday10.setText(courseName);
                tuesday10CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }
            if (meetingTime.contains("Tue") && meetingTime.contains("11")) {
                tuesday11.setText(courseName);
                tuesday11CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }
            if (meetingTime.contains("Tue") && meetingTime.contains("13")) {
                tuesday13.setText(courseName);
                tuesday13CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }
            if (meetingTime.contains("Tue") && meetingTime.contains("02")) {
                tuesday14.setText(courseName);
                tuesday14CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }
            if (meetingTime.contains("Tue") && meetingTime.contains("16")) {
                tuesday16.setText(courseName);
                tuesday16CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }
            if (meetingTime.contains("Tue") && meetingTime.contains("05")) {
                tuesday17.setText(courseName);
                tuesday17CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }

            if (meetingTime.contains("Wed") && meetingTime.contains("08")) {
                wednesday8.setText(courseName);
                wednesday8CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }
            if (meetingTime.contains("Wed") && meetingTime.contains("10")) {
                wednesday10.setText(courseName);
                wednesday10CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }
            if (meetingTime.contains("Wed") && meetingTime.contains("11")) {
                wednesday11.setText(courseName);
                wednesday11CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }
            if (meetingTime.contains("Wed") && meetingTime.contains("01")) {
                wednesday13.setText(courseName);
                wednesday13CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }
            if (meetingTime.contains("Wed") && meetingTime.contains("02")) {
                wednesday14.setText(courseName);
                wednesday14CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }
            if (meetingTime.contains("Wed") && meetingTime.contains("16")) {
                wednesday16.setText(courseName);
                wednesday16CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }
            if (meetingTime.contains("Wed") && meetingTime.contains("05")) {
                wednesday17.setText(courseName);
                wednesday17CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }

            if (meetingTime.contains("Thu") && meetingTime.contains("08")) {
                thursday8.setText(courseName);
                thursday8CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }
            if (meetingTime.contains("Thu") && meetingTime.contains("10")) {
                thursday10.setText(courseName);
                thursday10CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }
            if (meetingTime.contains("Thu") && meetingTime.contains("11")) {
                thursday11.setText(courseName);
                thursday11CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }
            if (meetingTime.contains("Thu") && meetingTime.contains("01")) {
                thursday13.setText(courseName);
                thursday13CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }
            if (meetingTime.contains("Thu") && meetingTime.contains("02")) {
                thursday14.setText(courseName);
                thursday14CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }
            if (meetingTime.contains("Thu") && meetingTime.contains("04")) {
                thursday16.setText(courseName);
                thursday16CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }
            if (meetingTime.contains("Thu") && meetingTime.contains("05")) {
                thursday17.setText(courseName);
                thursday17CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }

            if (meetingTime.contains("Fri") && meetingTime.contains("08")) {
                friday8.setText(courseName);
                friday8CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }
            if (meetingTime.contains("Fri") && meetingTime.contains("10")) {
                friday10.setText(courseName);
                friday10CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }
            if (meetingTime.contains("Fri") && meetingTime.contains("11")) {
                friday11.setText(courseName);
                friday11CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }
            if (meetingTime.contains("Fri") && meetingTime.contains("01")) {
                friday13.setText(courseName);
                friday13CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }
            if (meetingTime.contains("Fri") && meetingTime.contains("02")) {
                friday14.setText(courseName);
                friday14CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }
            if (meetingTime.contains("Fri") && meetingTime.contains("04")) {
                friday16.setText(courseName);
                friday16CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }
            if (meetingTime.contains("Fri") && meetingTime.contains("05")) {
                friday17.setText(courseName);
                friday17CV.setCardBackgroundColor(getResources().getColor(R.color.silversand));
            }


        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            try {
                ScheduleContents sch = new ScheduleContents(planID, scheduleID);
                FragmentTransaction trans = getFragmentManager().beginTransaction();
                trans.replace(R.id.fragment, sch, "Weekly");
                trans.commit();
                if (Build.VERSION.SDK_INT >= 26) {
                    trans.setReorderingAllowed(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
