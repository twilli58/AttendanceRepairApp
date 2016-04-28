package edu.westga.tamikowilliamsattendancerepairapp.UIActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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


public class CourseStatsActivity extends AppCompatActivity {

    int student_id, course_id, month;
    ArrayList<Integer> presents = new ArrayList<>();
    ArrayList<Integer> absents = new ArrayList<>();
    ArrayList<String> students = null;
    ArrayList<Integer> all = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_stats);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView studentVal = (TextView) findViewById(R.id.studentVal);
        TextView courseVal = (TextView) findViewById(R.id.courseVal);
        TextView monthVal = (TextView) findViewById(R.id.monthVal);
        TextView presentVal = (TextView) findViewById(R.id.presentVal);
        TextView absentVal = (TextView) findViewById(R.id.absentVal);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Bundle bundle = getIntent().getExtras();
        student_id = bundle.getInt("student_id");
        month = bundle.getInt("month");
        course_id = 0;

        if(bundle.containsKey("course_id")) {
            course_id = bundle.getInt("course_id");
        }

        if(month == 0) {
            showStatsOfCourse();
        } else {
            showStatsOfCourseInMonth();
        }

        if(student_id > 0) {
            studentVal.setText(Student.findStudentName(this, student_id));
        } else studentVal.setText("All");

        if(course_id > 0) {
            courseVal.setText(Course.findCourseName(this, course_id));
        } else courseVal.setText("All");

        if(month > 0) {
            monthVal.setText(Attendance.getMonthName(month));
        } else monthVal.setText("All");

        presentVal.setText(String.valueOf(all.get(0)));
        absentVal.setText(String.valueOf(all.get(1)));
    }

    public void showStatsOfCourse() {
        students = Course.getStudents(this, course_id);

        all = Attendance.getCourseStats(this, course_id);

        for(int i=0; i<students.size()/2; ++i) {
            int id = Student.findStudent(this, students.get(2 * i + 1), students.get(2 * i));

            ArrayList<Integer> curStudent = Attendance.getStudentStatsByCourse(this, id, course_id);

            presents.add(curStudent.get(0));
            absents.add(curStudent.get(1));
        }
        populateList();
    }

    public void showStatsOfCourseInMonth() {
        students = Course.getStudents(this, course_id);
        all = Attendance.getCourseStatsByMonth(this, month, course_id);

        for(int i=0; i<students.size()/2; ++i) {
            int id = Student.findStudent(this, students.get(2 * i + 1), students.get(2 * i));

            ArrayList<Integer> curStudent = Attendance.getStudentStatsByCourseAndMonth(this, id, course_id, month);

            presents.add(curStudent.get(0));
            absents.add(curStudent.get(1));
        }
        populateList();
    }

    public void populateList() {
        ListView list = (ListView) findViewById(R.id.listCourseStats);

        presents.add(0, 0);
        absents.add(0, 0);
        students.add(0, "");
        students.add(0, "");

        list.setAdapter(new StudentListAdapter(this, presents));


    }

    class StudentListAdapter extends ArrayAdapter<Integer> {

        private final Activity context;

        public StudentListAdapter(Context context, ArrayList<Integer> ids) {
            super(context, R.layout.single_row_student, ids);
            this.context = (Activity) context;
        }

        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.single_row_student, null, true);

            TextView firstNameView = (TextView) rowView.findViewById(R.id.firstNameStudentCourseStats);
            TextView lastNameView = (TextView) rowView.findViewById(R.id.lastNameStudentCourseStats);
            TextView presentsView = (TextView) rowView.findViewById(R.id.presentStatsTxt);
            TextView absentsView = (TextView) rowView.findViewById(R.id.textView11);

            if(position == 0) {
                firstNameView.setText("First Name");
                lastNameView.setText("Last Name");
                presentsView.setText("Presents");
                absentsView.setText("Absents");
            } else {
                firstNameView.setText(students.get(2 * position + 1));
                lastNameView.setText(students.get(2 * position));
                presentsView.setText(String.valueOf(presents.get(position)));
                absentsView.setText(String.valueOf(absents.get(position)));
            }

            return rowView;
        }
    }

}
