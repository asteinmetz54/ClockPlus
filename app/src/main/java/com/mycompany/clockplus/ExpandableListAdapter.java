package com.mycompany.clockplus;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.security.acl.Group;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Andrew on 4/29/2017.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private final SparseArray<AlarmGroup> groups;
    public LayoutInflater inflater;
    private Activity activity;

    public ExpandableListAdapter(Activity activity, SparseArray<AlarmGroup> groups){
        this.activity = activity;
        this.groups = groups;
        inflater = activity.getLayoutInflater();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).children.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        Alarm alarm = (Alarm) getChild(groupPosition, childPosition);
        TextView alarmName = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_alarm, null);
        }

        alarmName = (TextView)convertView.findViewById(R.id.alarm_name);
        alarmName.setText(alarm.getName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, "Alarm Name was pressed",
                        Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).children.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.alarm_group, null);
        }
        AlarmGroup alarmGroup = (AlarmGroup) getGroup(groupPosition);
        ((CheckedTextView) convertView).setText(alarmGroup.alarmTime);
        ((CheckedTextView) convertView).setChecked(isExpanded);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
