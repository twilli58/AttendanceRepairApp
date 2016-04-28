package edu.westga.tamikowilliamsattendancerepairapp.UIActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import edu.westga.tamikowilliamsattendancerepairapp.Model.Course;
import edu.westga.tamikowilliamsattendancerepairapp.Model.Student;
import edu.westga.tamikowilliamsattendancerepairapp.R;


public class StudentSearchActivity extends AppCompatActivity {

    int current=0;
    Spinner spinner;
    ArrayList<String> months = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner = (Spinner) findViewById(R.id.spinnerMonth3);

        months.add("Select Month");
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("Novemer");
        months.add("December");



        CustomAdapter customAdapter = new CustomAdapter(this, months);
        spinner.setAdapter(customAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                current = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public class CustomAdapter extends ArrayAdapter<String> {

        private Activity activity;

        /*************  CustomAdapter Constructor *****************/
        public CustomAdapter(Activity activity, ArrayList<String> spinnerList)
        {
            super(activity, R.layout.single_row, spinnerList);
            this.activity = activity;
        }

        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        // This funtion called for each row ( Called data.size() times )
        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = activity.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.course_item, null, true);

            TextView name = (TextView) rowView.findViewById(R.id.nameCourseItem);
            name.setText(months.get(position));

            return rowView;
        }
    }

    public void clickGo(View view) {
        RadioButton searchStudent = (RadioButton) findViewById(R.id.searchStudentRBtn);
        RadioButton searchCourse = (RadioButton) findViewById(R.id.searchCourseRBtn);
        RadioButton searchBoth = (RadioButton) findViewById(R.id.searchBothRBtn);
        CheckBox byTime = (CheckBox) findViewById(R.id.courseMonthChkBox);
        Bundle bundle = new Bundle();

        if(!searchStudent.isChecked() && !searchCourse.isChecked() && !searchBoth.isChecked()) {
            Toast.makeText(this, "Please select an option.", Toast.LENGTH_SHORT).show();
            return;
        }

        EditText lastNameView = (EditText) findViewById(R.id.studentLastStatsEditTxt);
        EditText firstNameView = (EditText) findViewById(R.id.studentFirstStatsEditTxt);

        EditText nameView = (EditText) findViewById(R.id.courseNameStatsEditTxt);

        String lastName = lastNameView.getText().toString();
        String firstName = firstNameView.getText().toString();

        String name = nameView.getText().toString();

        if(searchStudent.isChecked()) {
            int id = Student.findStudent(this, firstName, lastName);

            if(id == -1) {
                Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show();
                return;
            }

            bundle.putInt("student_id", id);

            if(byTime.isChecked()) {
                if(current == 0) {
                    Toast.makeText(this, "Select a month", Toast.LENGTH_SHORT).show();
                    return;
                }
                bundle.putInt("month", current);
            } else bundle.putInt("month", 0);

            Intent intent = new Intent(this, StudentStatsActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if(searchCourse.isChecked()) {

            int id = Course.findCourse(this, name);

            if(id == -1) {
                Toast.makeText(this, "Course not found", Toast.LENGTH_SHORT).show();
                return;
            }

            bundle.putInt("student_id", 0);
            bundle.putInt("course_id", id);

            if(byTime.isChecked()) {
                if(current == 0) {
                    Toast.makeText(this, "Select a month", Toast.LENGTH_SHORT).show();
                    return;
                }
                bundle.putInt("month", current);
            } else bundle.putInt("month", 0);

            Intent intent = new Intent(this, CourseStatsActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if(searchBoth.isChecked()) {
            int id = Student.findStudent(this, firstName, lastName);

            if(id == -1) {
                Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show();
                return;
            }

            int cid = Course.findCourse(this, name);

            if(cid == -1) {
                Toast.makeText(this, "Course not found", Toast.LENGTH_SHORT).show();
                return;
            }
            Log.e("miko", "Student id is: " + String.valueOf(id));
            bundle.putInt("student_id", id);
            bundle.putInt("course_id", cid);
            Log.e("miko", "course id is: " + String.valueOf(cid));


            if(byTime.isChecked()) {
                if(current == 0) {
                    Toast.makeText(this, "Select a month", Toast.LENGTH_SHORT).show();
                    return;
                }
                bundle.putInt("month", current);
            } else bundle.putInt("month", 0);

            Intent intent = new Intent(this, StudentStatsActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

}
