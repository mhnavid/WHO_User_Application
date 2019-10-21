package com.example.whouserapplication;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class DayTimeExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<DayTime>> expandableListDetail;

    public DayTimeExpandableListAdapter(Context context, List<String> expandableListTitle,
                                        HashMap<String, List<DayTime>> expandableListDetail){
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return this.expandableListDetail.get(this.expandableListTitle.get(i))
                .size();
    }

    @Override
    public Object getGroup(int i) {
        return this.expandableListTitle.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return this.expandableListDetail.get(this.expandableListTitle.get(i))
                .get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View convertView, ViewGroup viewGroup) {
        String listTitle = (String) getGroup(i);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.daytime_list_group, null);
        }
        TextView listDayTitleTextView = (TextView) convertView
                .findViewById(R.id.dayTimeListTitle);
        listDayTitleTextView.setTypeface(null, Typeface.BOLD);
        listDayTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View convertView, ViewGroup viewGroup) {
        final DayTime dayTimeList = (DayTime) getChild(i, i1);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.daytime_list_item, null);
        }
        TextView expandedDayListTextView = (TextView) convertView
                .findViewById(R.id.dayListItem);
        expandedDayListTextView.setText(dayTimeList.getDay());
        TextView expandedTimeListTextView = (TextView) convertView
                .findViewById(R.id.timeListItem);
        expandedTimeListTextView.setText(dayTimeList.getTime());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
