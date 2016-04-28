package edu.westga.tamikowilliamsattendancerepairapp.UIActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.westga.tamikowilliamsattendancerepairapp.Model.Attendance;
import edu.westga.tamikowilliamsattendancerepairapp.Model.Course;
import edu.westga.tamikowilliamsattendancerepairapp.Model.Student;
import edu.westga.tamikowilliamsattendancerepairapp.R;

public class StudentStatsActivity extends AppCompatActivity {
    int student_id, course_id, month;
    ArrayList<Integer> all = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_stats);

        Bundle bundle = getIntent().getExtras();
        student_id = bundle.getInt("student_id");
        month = bundle.getInt("month");
        course_id = 0;

        TextView studentVal = (TextView) findViewById(R.id.studentVal);
        TextView courseVal = (TextView) findViewById(R.id.courseVal);
        TextView monthVal = (TextView) findViewById(R.id.monthVal);
        TextView presentVal = (TextView) findViewById(R.id.presentVal);
        TextView absentVal = (TextView) findViewById(R.id.absentVal);

        if(bundle.containsKey("course_id")) {
            course_id = bundle.getInt("course_id");
        }

        Log.e("miko", String.valueOf(student_id));
        studentVal.setText(Student.findStudentName(this, student_id));

        if(course_id == 0) {
            courseVal.setText("All");
            if (month == 0) {
                monthVal.setText("All");
                all = Attendance.getStudentStats(this, student_id);
            } else {
                monthVal.setText(Attendance.getMonthName(month));
                all = Attendance.getCourseStatsByMonth(this, student_id, month);
            }
        } else {
            courseVal.setText(Course.findCourseName(this, course_id));
            if(month == 0) {
                monthVal.setText("All");
                all = Attendance.getStudentStatsByCourse(this, student_id, course_id);
            } else {
                monthVal.setText(Attendance.getMonthName(month));
                all = Attendance.getStudentStatsByCourseAndMonth(this, student_id, course_id, month);
            }
        }

        presentVal.setText(String.valueOf(all.get(0)));
        absentVal.setText(String.valueOf(all.get(1)));
    }
}