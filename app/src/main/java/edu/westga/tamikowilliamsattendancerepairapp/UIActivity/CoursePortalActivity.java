package edu.westga.tamikowilliamsattendancerepairapp.UIActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.westga.tamikowilliamsattendancerepairapp.Model.Course;
import edu.westga.tamikowilliamsattendancerepairapp.R;

public class CoursePortalActivity extends AppCompatActivity {
    Spinner spinner;
    ArrayList<String> courses = null;
    int current = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        courses = Course.getCourses(this);
        if(courses == null) {
            Toast.makeText(this, "No course found", Toast.LENGTH_SHORT).show();
            return;
        }

        setContentView(R.layout.activity_course_portal);

        spinner = (Spinner) findViewById(R.id.spinner);

        courses.add(0, "Select Course");

        CustomAdapter customAdapter = new CustomAdapter(this, courses);
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

    public void click_Select(View view) {
        if(current == 0) {
            Toast.makeText(this, "Select a course", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(view.getContext(), CourseStudentListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", Course.findCourse(this, courses.get(current)));
        intent.putExtras(bundle);
        startActivity(intent);
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
            name.setText(courses.get(position));

            return rowView;
        }
    }
}
