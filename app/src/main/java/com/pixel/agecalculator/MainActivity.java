package com.pixel.agecalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText day, month, year;
    TextView ageTextView;
    int _year, _month, _day;
    DateValidator dateValidator;
    Date cDate;
    String Age;
    int[] monthDay = {31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        day = (EditText) findViewById(R.id.dob_day);
        month = (EditText) findViewById(R.id.dob_month);
        year = (EditText) findViewById(R.id.dob_year);
        ageTextView = (TextView) findViewById(R.id.ageTextView);
        ageTextView.setVisibility(View.INVISIBLE);
        dateValidator = new DateValidator();
    }

    public void getAge(View view) {


        String bDayString = day.getText().toString();
        String bMonthString = month.getText().toString();
        String bYearString = year.getText().toString();
        String bDate = bDayString + "/" + bMonthString + "/" + bYearString;
        if (dateValidator.validate(bDate)) {

            int bDay = Integer.parseInt(bDayString);
            int bMonth = Integer.parseInt(bMonthString);
            int bYear = Integer.parseInt(bYearString);
            String age = getAge(bYear, bMonth, bDay);
            ageTextView.setVisibility(View.VISIBLE);
            ageTextView.setText(age);
            Toast.makeText(getApplicationContext(), age, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Enter valid Birth date", Toast.LENGTH_SHORT).show();
            ageTextView.setVisibility(View.INVISIBLE);
        }
    }

    public String getAge(int Year, int Month, int Day) {

        Calendar cal = Calendar.getInstance();
        cDate = cal.getTime();

        int cDateDay = cal.get(Calendar.DAY_OF_MONTH);
        int cDateMonth = cal.get(Calendar.MONTH) + 1;
        int cDateYear = cal.get(Calendar.YEAR);

        int bDateDay = Day;
        int bDateMonth = Month;
        int bDateYear = Year;

        int increment = 0;
        if (bDateDay > cDateDay) {
            increment = this.monthDay[bDateMonth - 1];
        }

        if (increment == -1) {
            if (bDateDay % 4 == 0) {
                increment = 29;
            } else {
                increment = 28;
            }
        }

        if (increment != 0) {
            _day = (cDateDay + increment) - bDateDay;
            increment = 1;
        } else {
            _day = cDateDay - bDateDay;
        }

        if ((bDateMonth + increment) > cDateMonth) {
            _month = (cDateMonth + 12) - (bDateMonth + increment);
            increment = 1;
        } else {
            _month = (cDateMonth) - (bDateMonth + increment);
            increment = 0;
        }

        _year = cDateYear - (bDateYear + increment);

        String s1, s2, s3;
        if (_year == 1) {
            s1 = "Year";
        } else {
            s1 = "Years";
        }
        if (_month == 1) {
            s2 = "Month";
        } else {
            s2 = "Months";
        }
        if (_day == 1) {
            s3 = "Day";
        } else {
            s3 = "Days";
        }

        Age = _year + " " + s1 + "  " + _month + " " + s2 + "  " + _day + " " + s3;
        return Age;
    }
}
