package com.quocdat.assignment_mob201.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quocdat.assignment_mob201.R;
import com.quocdat.assignment_mob201.models.Schedule;

import java.util.List;

public class ScheduleAdapter extends BaseAdapter {

    private Context ctx;
    private List<Schedule> data;

    public ScheduleAdapter(Context ctx, List<Schedule> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            view = View.inflate(parent.getContext(), R.layout.layout_schedule_item, null);
            TextView tvDateSchedule = view.findViewById(R.id.tvDateSchedule);
            TextView tvTimeSchedule = view.findViewById(R.id.tvTimeSchedule);
            TextView tvRoomSchedule = view.findViewById(R.id.tvRoomSchedule);
            TextView tvMeetSchedule = view.findViewById(R.id.tvMeetSchedule);

            ViewHolder holder = new ViewHolder(tvDateSchedule, tvTimeSchedule, tvRoomSchedule, tvMeetSchedule);
            view.setTag(holder);
        }

        ViewHolder holder = (ViewHolder) view.getTag();

        Schedule schedule = (Schedule) getItem(position);
        holder.tvDateSchedule.setText(schedule.getDate());
        holder.tvTimeSchedule.setText(schedule.getTime());
        holder.tvRoomSchedule.setText(schedule.getAddress());
        holder.tvMeetSchedule.setText(schedule.getMeet());

        return view;
    }

    private static class ViewHolder{
        final TextView tvDateSchedule, tvTimeSchedule, tvRoomSchedule, tvMeetSchedule;

        public ViewHolder(TextView tvDateSchedule, TextView tvTimeSchedule, TextView tvRoomSchedule, TextView tvMeetSchedule) {
            this.tvDateSchedule = tvDateSchedule;
            this.tvTimeSchedule = tvTimeSchedule;
            this.tvRoomSchedule = tvRoomSchedule;
            this.tvMeetSchedule = tvMeetSchedule;
        }
    }
}
