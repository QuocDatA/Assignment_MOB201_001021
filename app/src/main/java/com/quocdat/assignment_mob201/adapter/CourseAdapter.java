package com.quocdat.assignment_mob201.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quocdat.assignment_mob201.R;
import com.quocdat.assignment_mob201.activities.CourseDetailActivity;
import com.quocdat.assignment_mob201.models.Course;

import java.util.List;

public class CourseAdapter extends BaseAdapter {

    private Context ctx;
    private List<Course> data;
    private boolean joined;

    public CourseAdapter(Context ctx, List<Course> data, boolean joined) {
        this.ctx = ctx;
        this.data = data;
        this.joined = joined;
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
            view = View.inflate(parent.getContext(), R.layout.layout_courses_item, null);
            TextView tvCourseCode = view.findViewById(R.id.tvCourseCode);
            TextView tvCourseTeacher = view.findViewById(R.id.tvCourseTeacher);
            TextView tvCourseName = view.findViewById(R.id.tvCourseName);
            TextView tvJoined = view.findViewById(R.id.tvJoined);

            ViewHolder holder = new ViewHolder(tvCourseCode, tvCourseTeacher, tvCourseName, tvJoined);
            view.setTag(holder);
        }

        ViewHolder holder = (ViewHolder) view.getTag();

        Course course = (Course) getItem(position);
        holder.tvCourseCode.setText(course.getCode());
        holder.tvCourseTeacher.setText(course.getTeacher());
        holder.tvCourseName.setText(course.getName());
        holder.tvDetail.setText("Chi tiết");

        holder.tvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, CourseDetailActivity.class);
                intent.putExtra("course",(Parcelable) course);
                intent.putExtra("joined",joined);
                ctx.startActivity(intent);
            }
        });

        return view;
    }

    private static class ViewHolder{
        final TextView tvCourseCode, tvCourseTeacher, tvCourseName, tvDetail;

        public ViewHolder(TextView tvCourseCode, TextView tvCourseTeacher, TextView tvCourseName, TextView tvDetail) {
            this.tvCourseCode = tvCourseCode;
            this.tvCourseTeacher = tvCourseTeacher;
            this.tvCourseName = tvCourseName;
            this.tvDetail = tvDetail;
        }
    }
}
