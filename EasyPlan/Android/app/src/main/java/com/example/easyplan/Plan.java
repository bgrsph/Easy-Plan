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

    public void deleteDuplicates() {
        ArrayList<Schedule> newList = new ArrayList<>();
        for (Schedule x : schedules) {
            if (!newList.contains(x)) newList.add(x);
        }
        this.schedules = newList;
    }

    private void recursiveScheduleCreating(ArrayList<Course> input, Course list[], int start, int end, int index, int size) {
        if (index == size)
        {
            Schedule schedule = new Schedule();
            for (Course x : list) {
                boolean add = true;
                /*for (Course y : schedule.getCourseList()) {
                    if (y.getSubject().equals(x.getSubject()) && y.getCatalog().equals(x.getCatalog())) {
                        add = false;
                        break;
                    }
                } */
                if (add) schedule.addToSchedule(x);
            }
            this.schedules.add(schedule);
            return;
        }

        for (int i = start; i <= end && end - i + 1 >= size - index; i++)
        {
            list[index] = input.get(i);
            recursiveScheduleCreating(input, list, i + 1, end, index + 1, size);
        }
    }

    public void createSchedules(ArrayList<Course> input, int n, int size, ArrayList<Course> labs) {
        //Create ALL the schedules!
        Course data[] = new Course[size];
        /*for (int i = 1; i < n; i++) {
            data.add(new Course());
        }*/
        recursiveScheduleCreating(input, data, 0, n-1, 0, size);
        obliterateConflicts(labs);
    }

    private void obliterateConflicts(ArrayList<Course> labs) {
        ArrayList<Schedule> newList = new ArrayList<>();
        for (Schedule x : schedules) {
            boolean add = true;
            for (Course y : x.getCourseList()) {
                for (Course z : x.getCourseList()) {
                    add = true;
                    if (y.equals(z)) continue;
                    if (y.getSubject().equals(z.getSubject()) && y.getCatalog().equals(z.getCatalog())) {
                        add = false;
                        break;
                    }
                    if (y.getMtgStart().equals(z.getMtgStart()) &&
                            ((y.getMonday().equals(z.getMonday()) && y.getMonday().equals("Y")) ||
                                    (y.getTuesday().equals(z.getTuesday()) && y.getTuesday().equals("Y")) ||
                                    (y.getWednesday().equals(z.getWednesday()) && y.getWednesday().equals("Y")) ||
                                    (y.getThursday().equals(z.getThursday()) && y.getThursday().equals("Y")) ||
                                    (y.getFriday().equals(z.getFriday()) && y.getFriday().equals("Y")))) {
                        add = false;
                        break;
                    }
                }
                if (!add) break;
            }
            if (!newList.contains(x) && add) {
                newList.add(x);
            }
        }
        scheduleLabs(labs);
        this.schedules = newList;
    }

    private void scheduleLabs (ArrayList<Course> labs) {
        ArrayList<ArrayList<Course>> a = new ArrayList<>();
        a = addLabs(a, a, 0, new ArrayList<Course>());
    }

    private ArrayList<ArrayList<Course>> addLabs(ArrayList<ArrayList<Course>> labLists, ArrayList<ArrayList<Course>> result, int depth, ArrayList<Course> current) {
        if (depth == labLists.size()) {
            result.add(current);
            return result;
        }
        for (int i = 0; i < labLists.get(depth).size(); i++) {
            current.add(labLists.get(depth).get(i));
            addLabs(labLists, result, depth + 1, current);
        }
        return result;
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
