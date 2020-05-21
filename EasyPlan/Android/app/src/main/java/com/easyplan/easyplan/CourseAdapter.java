package com.easyplan.easyplan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.easyplan.easyplan.ClassSearchFragment.noDupList;
import static com.easyplan.easyplan.ClassSearchFragment.selectedCourses;
import static com.easyplan.easyplan.ClassSearchFragment.selectedLabs;
import static com.easyplan.easyplan.ClassSearchFragment.text1;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    Context context;
    private ArrayList<Course> courseList1 = new ArrayList<Course>();
    private ArrayList<Course> filterList = new ArrayList<Course>();
    private int size = 0;

    public CourseAdapter(Context context, ArrayList<Course> courses) {
        this.context = context;
        courseList1 = courses;
        filterList = courses;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public ImageView imageView;
        public Button infoBtn;
        public LinearLayout layout;

        public ViewHolder(final View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.courseName);
            imageView = itemView.findViewById(R.id.image);
            infoBtn = itemView.findViewById(R.id.class_info_btn);
            layout = itemView.findViewById(R.id.layout);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imageView.getTag() == "plus") {
                        imageView.setImageResource(R.drawable.done_icon);
                        imageView.setTag("check");
                        for (Course x : ClassSearchFragment.allSections) {
                            if ((x.getSubject() + x.getCatalog()).equals(nameTextView.getTag())) {
                                selectedCourses.add(x);
                            }
                        }
                        for (Course x : ClassSearchFragment.labList) {
                            if ((x.getSubject() + x.getCatalog()).equals(nameTextView.getTag())) {
                                selectedLabs.add(x);
                            }
                        }
                    } else {
                        imageView.setImageResource(R.drawable.add_icon);
                        imageView.setTag("plus");
                        List<Course> courseDelete = new ArrayList<Course>();
                        for (Course x : selectedCourses) {
                            if ((x.getSubject() + x.getCatalog()).equals(nameTextView.getTag())) {
                                courseDelete.add(x);
                            }
                        }
                        for (Course x : ClassSearchFragment.labList) {
                            if ((x.getSubject() + x.getCatalog()).equals(nameTextView.getTag())) {
                                courseDelete.add(x);
                            }
                        }
                        for (Course x : courseDelete) {
                            if (selectedCourses.contains(x)) selectedCourses.remove(x);
                        }

                        for (Course x : courseDelete) {
                            if (selectedLabs.contains(x)) selectedLabs.remove(x);
                        }
                    }

                    String key = "";
                    for (Course x : selectedCourses) {
                        if (key.equals("")) {
                            noDupList.add(x);
                            key = x.getSubject() + x.getCatalog();
                        } else if (!key.equals(x.getSubject() + x.getCatalog())) {
                            noDupList.add(x);
                            key = x.getSubject() + x.getCatalog();
                        }
                    }
                    if (ClassSearchFragment.noDupList.size() == 0 || ClassSearchFragment.noDupList.size() == 1) {
                        text1.setText(ClassSearchFragment.noDupList.size() + " course selected.");
                    } else {
                        text1.setText(ClassSearchFragment.noDupList.size() + " courses selected.");
                    }

                    ClassSearchFragment.noDupList.clear();

                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.class_list_item, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Course course = courseList1.get(position);

        // Set item views based on your views and data model
        TextView textView1 = holder.nameTextView;
        textView1.setText(course.getSubject() + course.getCatalog());
        textView1.setTag(course.getSubject() + course.getCatalog());
        ImageView image = holder.imageView;
        Button infoBtn = holder.infoBtn;
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = course.getSubject()+ "- " + course.getCatalog();;
                final SharedPreferenceBot bot = new SharedPreferenceBot();
                final ArrayList<CourseInfoItemHelper> allSections = new ArrayList<>();
                bot.setSharedPrefC("courseInfoList", context, allSections);
                FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
                DatabaseReference mReference = mDatabase.getReference().child("ugradCourses");
                mReference.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            Course course = dataSnapshot1.getValue(Course.class);
                            String courseName = course.getSubject() + "- " + course.getCatalog();
                            if (courseName.equalsIgnoreCase(name)) {
                                String courseProf = course.getProf() + "";
                                String meetingTime = course.getMeetingDays() + " " + course.getMtgStart() + " - " + course.getMtgEnd();
                                String section = course.getSection();
                                allSections.add(new CourseInfoItemHelper(courseName, courseProf, meetingTime, section));
                            }
                        }
                        bot.setSharedPrefC("courseInfoList", context, allSections);
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                context.startActivity(new

                        Intent(context, CourseInformationPopup.class));
            }
        });
        for (
                Course x : ClassSearchFragment.selectedCourses) {
            if ((x.getSubject() + x.getCatalog()).equals(textView1.getTag())) {
                image.setImageResource(R.drawable.done_icon);
                image.setTag("check");
            }
        }
        if (image.getTag() != "check") {
            image.setImageResource(R.drawable.add_icon);
            image.setTag("plus");
        }
    }

    @Override
    public int getItemCount() {
        return courseList1.size();
    }

    public void filterList(ArrayList<Course> filteredList) {
        courseList1.clear();
        courseList1 = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
