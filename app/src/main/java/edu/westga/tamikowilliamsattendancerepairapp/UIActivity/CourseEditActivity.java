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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.westga.tamikowilliamsattendancerepairapp.Model.Course;
import edu.westga.tamikowilliamsattendancerepairapp.Model.Student;
import edu.westga.tamikowilliamsattendancerepairapp.R;

public class CourseEditActivity extends AppCompatActivity {
    TextView nameView;
    TextView idView;
    ListView listView;

    EditText lastNameStudentView;
    EditText firstNameStudentView;

    ArrayList<String> studentsInCourse = null;
    ArrayList<Boolean> deleteFromCourse = null;
    int courseId = -1;
    String courseName = null;

    public StudentListAdapter studentListAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_edit);

        lastNameStudentView = (EditText) findViewById(R.id.lastNameStudentCourseEdit);
        firstNameStudentView = (EditText) findViewById(R.id.firstNameStudentCourseEdit);
        nameView = (TextView) findViewById(R.id.nameEditText);
        idView = (TextView) findViewById(R.id.idEditText);

        Bundle bundle = getIntent().getExtras();
        courseId = bundle.getInt("id");
        studentsInCourse = Course.getStudents(this, courseId);
        courseName = Course.findCourseName(this, courseId);

        idView.setText(String.valueOf(courseId));
        nameView.setText(courseName);

        if(studentsInCourse != null) {
            Log.e("miko", studentsInCourse.toString());
            this.populateCourseList();
        } else Log.e("miko", "it is null for " + String.valueOf(courseId));

    }

    public void addStudent(View v) {
        String lastName = lastNameStudentView.getText().toString();
        String firstName = firstNameStudentView.getText().toString();

        if(lastName.length() == 0) {
            lastNameStudentView.setError("Last Name cannot be empty.");
            return;
        }

        if(firstName.length() == 0) {
            firstNameStudentView.setError("First Name cannot be empty.");
            return;
        }

        if(studentsInCourse != null) {
            int lastNameIndex = studentsInCourse.lastIndexOf(lastName);
            if (lastNameIndex != -1) {
                if (studentsInCourse.lastIndexOf(firstName) == (lastNameIndex + 1)) {
                    Toast.makeText(this, "Student already added", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        Student.addStudent(this, lastName, firstName);
        int studentId = Student.findStudent(this, lastName, firstName);

        if(studentId == -1) {
            Toast.makeText(this, "Error occured.", Toast.LENGTH_SHORT).show();
            return;
        }

        Course.addStudentInCourse(this, studentId, courseId);
        if(deleteFromCourse == null) {
            deleteFromCourse = new ArrayList<>();
            studentsInCourse = new ArrayList<>();
        }
        deleteFromCourse.add(0, false);
        studentsInCourse.add(0, firstName);
        studentsInCourse.add(0, lastName);
        Toast.makeText(this, "Student Enrolled", Toast.LENGTH_SHORT).show();
        if(studentListAdapter == null) {
            populateCourseList();
        } else {
            studentListAdapter.notifyDataSetChanged();
        }


    }
    private void populateCourseList() {
        listView = (ListView) findViewById(R.id.courseEditlistView);
        deleteFromCourse = new ArrayList<>();

        for(int i=0; i<studentsInCourse.size()/2; ++i) {
            deleteFromCourse.add(false);
        }

        studentListAdapter = new StudentListAdapter(this, deleteFromCourse);
        listView.setAdapter(studentListAdapter);

    }

    class StudentListAdapter extends ArrayAdapter<Boolean> {

        private final Activity context;

        public StudentListAdapter(Context context, ArrayList<Boolean> ids) {
            super(context, R.layout.single_row_course, ids);
            this.context = (Activity) context;
        }

        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.single_row_course, null, true);

            TextView studentName = (TextView) rowView.findViewById(R.id.nameStudentCourseEdit);

            studentName.setText(studentsInCourse.get(2*position) + " " + studentsInCourse.get(2*position + 1));

            return rowView;
        }
    }

    public void save(View v) {
        if(deleteFromCourse != null) {
            int studentId;
            ListView list = (ListView) findViewById(R.id.courseEditlistView);
            for (int i = 0; i < list.getChildCount(); ++i) {
                CheckBox deleteCheckBox = (CheckBox) list.getChildAt(i).findViewById(R.id.deleteCheckBoxCourseEdit);

                if(!deleteCheckBox.isChecked()) continue;

                studentId = Student.findStudent(this, studentsInCourse.get(2 * i + 1), studentsInCourse.get(2 * i));

                Log.e("miko", "student ID: " + String.valueOf(studentId));

                if(studentId != -1) {

                    switch (Course.removeStudentFromCourse(this, studentId, courseId)) {
                        case 0:
                            Toast.makeText(this, "No such enrollment found", Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            Toast.makeText(this, "Already removed Student", Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            Toast.makeText(this, "Student Removed", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }
        }
        finish();
    }
}
