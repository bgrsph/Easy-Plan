package com.example.easyplan;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
        /*ArrayList<Schedule> newList = new ArrayList<>();
        for (Schedule x : schedules) {
            if (!newList.contains(x)) newList.add(x);
        }
        this.schedules = newList;*/
    }

    private void recursiveScheduleCreating(ArrayList<Course> input, Course list[], int start, int end, int index, int size) {
        //if(schedules.size() == 10000) return;
        if (index == size)
        {
            if (Arrays.asList(list).contains(null)) return;
            Schedule schedule = new Schedule();
            for (Course x : list) {
                schedule.addToSchedule(x);
            }

            boolean add = true;
            /*for (Schedule x : schedules) {
                if (x.getCourseList().containsAll(schedule.getCourseList())) {
                    add = false;
                    break;
                }
            }*/
            if (add) this.schedules.add(schedule);
            return;
        }

        for (int i = start; i <= end && end - i + 1 >= size - index; i++)
        {
            /*
            if (list[0] != null) {
                boolean add = true;
                for (Course y : list) {
                    if (y == null) continue;
                    Course z = input.get(i);
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
                if (add) list[index] = input.get(i);
            } else list[index] = input.get(i);
            */
            list[index] = input.get(i);
            recursiveScheduleCreating(input, list, i + 1, end, index + 1, size);
        }
    }

    public void createSchedules(ArrayList<Course> input, int n, int size, ArrayList<Course> labs) {
        //Create ALL the schedules!
        Course data[] = new Course[size];
        recursiveScheduleCreating(input, data, 0, n-1, 0, size);
        if (labs.size() > 0) addLabs(labs);
        obliterateConflicts();
    }

    private void obliterateConflicts() {
        ArrayList<Schedule> newList = new ArrayList<>();
        for (Schedule x : schedules) {
            boolean add = true;
            for (Course y : x.getCourseList()) {
                for (Course z : x.getCourseList()) {
                    add = true;
                    if (y.equals(z)) continue;
                    if (y.getSubject().equals(z.getSubject()) && y.getCatalog().equals(z.getCatalog()) &&
                            !(y.getSection().startsWith("PS") || y.getSection().startsWith("DS") || y.getSection().startsWith("LAB")) &&
                                    !(z.getSection().startsWith("PS") || z.getSection().startsWith("DS") || z.getSection().startsWith("LAB"))) {
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
        Random rng = new Random();
        ArrayList<Schedule> tmpList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Schedule tmp = newList.get(rng.nextInt(newList.size()));
            tmp.setName("Schedule #" + Integer.toString(i + 1));
            tmpList.add(tmp);
        }
        this.schedules = tmpList;
    }

    private List<List<Course>> scheduleLabs (ArrayList<Course> labs, ArrayList<ArrayList<Course>> a) {
        ArrayList<ArrayList<Course>> b = new ArrayList<>();
        String s = "";
        ArrayList<Course> temp = new ArrayList<Course>();
        for (Course x : labs) {
            if (s.equals("")) {
                s = x.getSubject() + x.getCatalog() + x.getSection().substring(0, 2);
                temp.add(x);
            } else if (!(s.equals(x.getSubject() + x.getCatalog() + x.getSection().substring(0, 2)))) {
                a.add(new ArrayList<Course>(temp));
                temp.clear();
                s = x.getSubject() + x.getCatalog()+ x.getSection().substring(0, 2);
                temp.add(x);
            }
            else {
                temp.add(x);
            }
        }
        a.add(temp);
        return Lists.cartesianProduct(a);
    }

    private void addLabs(ArrayList<Course> labs) {
        ArrayList<Schedule> newList = new ArrayList<>();
        List<List<Course>> offf = scheduleLabs(labs, new ArrayList<ArrayList<Course>>());
        for (Schedule x : schedules) {
            ArrayList<String> yeterLan = new ArrayList<String>();
            for (Course y : x.getCourseList()) {
                yeterLan.add(y.getSubject() + y.getCatalog());
            }
            for (List<Course> y : offf) {
                Schedule LAN = new Schedule();
                ArrayList<Course> zzzz = new ArrayList<>(x.getCourseList());
                LAN.setCourseList(zzzz);
                boolean add = true;
                for (Course z : y) {
                    if (!(yeterLan.contains(z.getSubject() + z.getCatalog()))) {
                        add = false;
                        break;
                    }
                    LAN.addToSchedule(z);
                }
                if (add) newList.add(LAN);
            }
        }
        this.schedules = newList;
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
