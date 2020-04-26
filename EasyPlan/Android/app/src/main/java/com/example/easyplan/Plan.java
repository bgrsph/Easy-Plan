package com.example.easyplan;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Plan implements Parcelable {

    private ArrayList<Schedule> schedules = new ArrayList<>();
    private String planName;

    public Plan(String name) {
        planName = name;
    }

    /*public void addToPlan(Schedule schedule) {
        schedules.add(schedule);
    } */

    protected Plan(Parcel in) {
        planName = in.readString();
    }

    public static final Creator<Plan> CREATOR = new Creator<Plan>() {
        @Override
        public Plan createFromParcel(Parcel in) {
            return new Plan(in);
        }

        @Override
        public Plan[] newArray(int size) {
            return new Plan[size];
        }
    };

    public void planSchedules(ArrayList<Course> courseList) {
        //plan ALL the schedules.
        for (int i = 0; i < courseList.size(); i++) {
            Schedule schedule = new Schedule();
            ArrayList<Course> scheduleCourses = new ArrayList<>();
            scheduleCourses.add(courseList.get(i));
            ArrayList<Course> a = new ArrayList<>();
            for (int k = 0; k < courseList.size(); k++) {

                if (i == k) continue;
                if (a.size() == 4) break;

                for (Course x : scheduleCourses) {

                    if (x.getMtg_start() == courseList.get(k).getMtg_start() &&
                            (x.getMonday() == courseList.get(k).getMonday() ||
                                    x.getTuesday() == courseList.get(k).getTuesday() ||
                                    x.getWednesday() == courseList.get(k).getWednesday() ||
                                    x.getThursday() == courseList.get(k).getThursday() ||
                                    x.getFriday() == courseList.get(k).getFriday())) {
                        continue;
                    } else a.add(courseList.get(k));
                }

            }
            for (Course x : a) {
                scheduleCourses.add(x);
            }
            a.clear();
            schedule.setCourseList(scheduleCourses);
            this.schedules.add(schedule);
        }
    }

    public void countAllCombinations (Schedule input, int idx, ArrayList<Course> courseList) {
        for(int i = idx ; i < courseList.size(); i++) {
            if (input.getCourseList().size() > 4) return;
            input.addToSchedule(courseList.get(i));
            this.schedules.add(input);
            countAllCombinations(input, ++idx, courseList);
        }
    }

    public void deleteDuplicates() {
        ArrayList<Schedule> newList = new ArrayList<>();
        for (Schedule x : schedules) {
            if (!newList.contains(x)) newList.add(x);
        }
        this.schedules = newList;
    }

    /* arr[]  ---> Input Array
    data[] ---> Temporary array to store current combination
    start & end ---> Staring and Ending indexes in arr[]
    index  ---> Current index in data[]
    r ---> Size of a combination to be printed */
    public void combinationUtil(ArrayList<Course> arr, ArrayList<Course> data, int start,
                                int end, int index, int r)
    {
        // Current combination is ready to be printed, print it
        if (index == r)
        {
            Schedule schedule = new Schedule();
            for (Course x : data) {
                schedule.addToSchedule(x);
            }
            this.schedules.add(schedule);
            return;
        }

        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i = start; i <= end && end - i + 1 >= r - index; i++)
        {
            //data[index] = arr[i];
            data.set(index, arr.get(i));
            combinationUtil(arr, data, i + 1, end, index + 1, r);
        }
    }

    // The main function that prints all combinations of size r
    // in arr[] of size n. This function mainly uses combinationUtil()
    public void printCombination(ArrayList<Course> arr, int n, int r)
    {
        // A temporary array to store all combination one by one
        ArrayList<Course> data = new ArrayList<Course>();
        for (int i = 0; i < n; i++) {
            data.add(new Course());
        }
        // Print all combination using temprary array 'data[]'
        combinationUtil(arr, data, 0, n-1, 0, r);
    }

    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(ArrayList<Schedule> schedules) {
        this.schedules = schedules;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(planName);
    }
}
