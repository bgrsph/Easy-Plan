package com.example.easyplan;

import android.content.Context;
import android.graphics.Typeface;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.easyplan.ClassSearchFragment.selectedCourses;
import static com.example.easyplan.ClassSearchFragment.text1;

public class PlanAdapter extends BaseExpandableListAdapter {
    Context context;
    List<String> listPlanGroups;
    HashMap<String, List<String>> mapSchedulePlan;
//    ViewHolder viewHolder;/
    public PlanAdapter(Context context, List<String> listDataHeader,
                       HashMap<String, List<String>> listChildData) {
        this.context = context;
        this.listPlanGroups = listDataHeader;
        this.mapSchedulePlan = listChildData;
    }

 /*   private class ScheduleViewHolder {
        ImageView delete;
        TextView schedule;

    }*/


    @Override
    public int getGroupCount() {
        return this.listPlanGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.mapSchedulePlan.get(listPlanGroups.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listPlanGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.mapSchedulePlan.get(this.listPlanGroups.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String planName =  (String) this.getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.plan_group, null);
        }
        TextView planTextView = (TextView) convertView.findViewById(R.id.planGroup);
        planTextView.setTypeface(null, Typeface.BOLD_ITALIC);
        planTextView.setText(planName);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String scheduleName = (String) this.getChild(groupPosition, childPosition);
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.schedule_group, null);
        }
        /*viewHolder = new ViewHolder();
        viewHolder.delete = (ImageView)  convertView.findViewById(R.id.scheduleDeleteBtn);
        viewHolder.schedule = (TextView) convertView.findViewById(R.id.scheduleGroup);
        viewHolder.schedule.setText(scheduleName);
        viewHolder.imageViewList.add(viewHolder.delete);
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> plan = mapSchedulePlan.get(listPlanGroups.get(groupPosition));
                plan.remove(childPosition);
                notifyDataSetChanged();
            }
        });*/
        TextView schedule = convertView.findViewById(R.id.scheduleGroup);
        ImageView delete = convertView.findViewById(R.id.scheduleDeleteBtn);
        schedule.setText(scheduleName);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> plan = mapSchedulePlan.get(listPlanGroups.get(groupPosition));
                plan.remove(childPosition);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }


    /*
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View contactView = inflater.inflate(R.layout.schedule_group, parent, false);

            // Return a new holder instance
            ViewHolder viewHolder = new ViewHolder(contactView);
            return viewHolder;
        }*/
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

