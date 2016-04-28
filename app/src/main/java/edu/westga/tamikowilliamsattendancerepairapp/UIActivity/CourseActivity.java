package edu.westga.tamikowilliamsattendancerepairapp.UIActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.westga.tamikowilliamsattendancerepairapp.Model.Course;
import edu.westga.tamikowilliamsattendancerepairapp.R;

public class CourseActivity extends AppCompatActivity {
    EditText nameView;
    TextView idView;
    TextView numStudentsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nameView = (EditText) findViewById(R.id.courseNameEdit);
        idView = (TextView) findViewById(R.id.notAssigned2);
        numStudentsView = (TextView) findViewById(R.id.numEdit);
    }

    public void findCourse(View v) {
        String name = nameView.getText().toString();

        if(name.length() == 0) {
            nameView.setError("Name cannot be empty");
            return;
        }

        int id = Course.findCourse(this, name.toLowerCase());
        int count = 0;

        if(id == -1) {
            Toast.makeText(this, "Course not present", Toast.LENGTH_SHORT).show();
        } else {
            idView.setText(String.valueOf(id));
            count = Course.countStudents(this, id);
            numStudentsView.setText(String.valueOf(count));
            Toast.makeText(this, "Info updated.", Toast.LENGTH_SHORT).show();
        }
    }

    public void newCourse(View v) {
        String name = nameView.getText().toString();

        if(name.length() == 0) {
            nameView.setError("Name cannot be empty");
            return;
        }

        if(Course.addCourse(this, name.toLowerCase())) {
            Toast.makeText(this, "Course Added", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, "Course already present", Toast.LENGTH_SHORT).show();
    }

    public void editCourse(View v) {
        String idText = idView.getText().toString();

        if(idText.equalsIgnoreCase("not assigned")) {
            Toast.makeText(this, "Find a Course first.", Toast.LENGTH_SHORT).show();
            return;
        }

        int id = Integer.parseInt(idText);

        if(id == -1) {
            Toast.makeText(this, "Find a course first.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, CourseEditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void removeCourse(View v) {
        String name = nameView.getText().toString();

        if(name.length() == 0) {
            nameView.setError("Name cannot be empty");
            return;
        }

        int id = Course.findCourse(this, name.toLowerCase());
        int count = 0;

        if(id == -1) {
            Toast.makeText(this, "Course not present", Toast.LENGTH_SHORT).show();
        } else {
            idView.setText(String.valueOf(id));
            count = Course.countStudents(this, id);
            numStudentsView.setText(String.valueOf(count));
            if(count == 0) {
                Course.removeCourse(this, id);
                Toast.makeText(this, "Course deleted.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "There are students enrolled. Cannot Delete.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
