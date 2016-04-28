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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.westga.tamikowilliamsattendancerepairapp.Model.Attendance;
import edu.westga.tamikowilliamsattendancerepairapp.Model.Course;
import edu.westga.tamikowilliamsattendancerepairapp.Model.Student;
import edu.westga.tamikowilliamsattendancerepairapp.R;

public class CourseStudentListActivity extends AppCompatActivity {

    ListView listView;
    private ArrayList<String> studentsInCourse = null;
    private ArrayList<Boolean> isPresent = null;
    private int courseId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_student_list);

        Bundle bundle = getIntent().getExtras();
        courseId = bundle.getInt("id");
        studentsInCourse = Course.getStudents(this, courseId);

        if (studentsInCourse == null) {
            Toast.makeText(this, "No student in course", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        this.populateCourseList();

    }

    private void populateCourseList() {
        listView = (ListView) findViewById(R.id.studentListView);

        isPresent = new ArrayList<>();

        for (int i = 0; i < studentsInCourse.size() / 2; ++i) {
            isPresent.add(null);
        }

        StudentListAdapter studentListAdapter = new StudentListAdapter(this, isPresent);

        listView.setAdapter(studentListAdapter);
    }

    class StudentListAdapter extends ArrayAdapter<Boolean> {

        public final String LOG_TAG = "miko";

        private final Activity context;

        public StudentListAdapter(Context context, ArrayList<Boolean> ids) {
            super(context, R.layout.single_row, ids);
            this.context = (Activity) context;
        }

        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.single_row, null, true);

            TextView lastNameView = (TextView) rowView.findViewById(R.id.lastNameStudentCourseRollcall);
            TextView firstNameView = (TextView) rowView.findViewById(R.id.firstNameStudentCourseRollCall);


            lastNameView.setText(studentsInCourse.get(2 * position));
            firstNameView.setText(studentsInCourse.get(2 * position + 1));

            return rowView;
        }
    }

    public void save(View v) {

        ListView list = (ListView) findViewById(R.id.studentListView);

        for(int j=0; j<list.getChildCount(); ++j) {
            RadioButton isPresentView = (RadioButton) list.getChildAt(j).findViewById(R.id.isPresentRadioButton);
            RadioButton isAbsentView = (RadioButton) list.getChildAt(j).findViewById(R.id.isAbsentRadioButton);

            if(!(isPresentView.isChecked() || isAbsentView.isChecked())) {
                Toast.makeText(this, "Attendance is not complete.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        for(int j=0; j<list.getChildCount(); ++j) {
            RadioButton isPresentView = (RadioButton) list.getChildAt(j).findViewById(R.id.isPresentRadioButton);
            RadioButton isAbsentView = (RadioButton) list.getChildAt(j).findViewById(R.id.isAbsentRadioButton);

            int studentId = Student.findStudent(this, studentsInCourse.get(2 * j + 1), studentsInCourse.get(2 * j));

            int enrollId = Course.getEnrollId(this, studentId, courseId);


            if(isPresentView.isChecked()) {
                Attendance.markAttendance(this, 1, enrollId);
            } else if(isAbsentView.isChecked()) {
                Attendance.markAttendance(this, 0, enrollId);
            }
        }
        finish();
    }
}
