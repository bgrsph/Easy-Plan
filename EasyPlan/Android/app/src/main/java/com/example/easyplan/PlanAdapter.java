package com.example.easyplan;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.Layout;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.easyplan.ClassSearchFragment.selectedCourses;
import static com.example.easyplan.ClassSearchFragment.text1;

public class PlanAdapter extends BaseExpandableListAdapter {
    Context context;
    List<String> listPlanGroups;
    HashMap<String, List<String>> mapSchedulePlan;
    TextView deleteBtn;
    private SharedPreferenceBot bot = new SharedPreferenceBot();

    public PlanAdapter(Context context, List<String> listDataHeader,
                       HashMap<String, List<String>> listChildData) {
        this.context = context;
        this.listPlanGroups = listDataHeader;
        this.mapSchedulePlan = listChildData;
    }


    @Override
    public int getGroupCount() {
        return this.listPlanGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.mapSchedulePlan.get(this.listPlanGroups.get(groupPosition)).size();
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
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String planName = (String) getGroup(groupPosition); //returns the name
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.plan_group, null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.planGroup);
        deleteBtn = convertView.findViewById(R.id.plan_delete_btn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePlan(groupPosition);
                PlansFragment plansFrag = new PlansFragment();
                FragmentTransaction trans = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                trans.replace(R.id.fragment, plansFrag, "Plans");
                trans.commit();
            }
        });
        textView.setText(planName);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String scheduleName = (String) getChild(groupPosition, childPosition); //returns the name
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.schedule_group, null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.scheduleGroup);
        textView.setText(scheduleName);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void deletePlan(int planID){
        Gson gson = new Gson();
        Type type = new TypeToken<List<Plan>>(){}.getType();
        List<Plan> plans = gson.fromJson((String)bot.getSharedPrefC("plans", context ), type);
        plans.remove(planID);
        bot.setSharedPrefC("plans", context, plans);



    }
}

