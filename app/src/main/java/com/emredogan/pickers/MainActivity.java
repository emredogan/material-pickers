package com.emredogan.pickers;

import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    TextView timeText;
    TextView dateText;

    AppCompatButton pickTime;
    AppCompatButton pickDate;
    java.util.Calendar calendar;
    TimePickerDialog timePickerDialog;
    TimePickerDialog finalPicker;
    Boolean time24 = true;

    DatePickerDialog datePickerDialog;
    DatePickerDialog finalDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeText = (TextView) findViewById(R.id.timeTextView_id);
        dateText = (TextView) findViewById(R.id.dateTextView_id);
        pickTime = (AppCompatButton) findViewById(R.id.timeButton_id);
        pickDate = (AppCompatButton) findViewById(R.id.dateButton_id);

        pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendar = java.util.Calendar.getInstance();

                int currentHour = calendar.get(java.util.Calendar.HOUR_OF_DAY);
                int currentMin = calendar.get(java.util.Calendar.MINUTE);
                int currentSec = calendar.get(java.util.Calendar.SECOND);

                timePickerDialog = TimePickerDialog.newInstance(MainActivity.this,currentHour,currentMin,currentSec,time24);

                timePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));

                timePickerDialog.setTitle("TimePicky");

                timePickerDialog.setThemeDark(false);

                timePickerDialog.dismissOnPause(false);

                timePickerDialog.setOkText("SET");

                timePickerDialog.setCancelText("NOPE");

                timePickerDialog.enableSeconds(true);

                timePickerDialog.show(getFragmentManager(),"timePicker");

                timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        System.out.println("CANCEL");
                    }
                });

            }
        });

        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = java.util.Calendar.getInstance();

                int currentDay = calendar.get(java.util.Calendar.DAY_OF_MONTH);
                int currentMonth = calendar.get(java.util.Calendar.MONTH);
                int currentYear = calendar.get(java.util.Calendar.YEAR);

                datePickerDialog = DatePickerDialog.newInstance(MainActivity.this,currentYear,currentMonth,currentDay);

                datePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));

                datePickerDialog.setTitle("DatePicky");

                datePickerDialog.setOkText("YEP");

                datePickerDialog.setCancelText("NOPE");

                datePickerDialog.setThemeDark(true);

                datePickerDialog.dismissOnPause(true);

                datePickerDialog.showYearPickerFirst(true);


                datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                    }
                });

                datePickerDialog.show(getFragmentManager(),"datePicker");



            }
        });


        finalPicker = (TimePickerDialog) getFragmentManager().findFragmentByTag("timePicker");
        finalDatePicker = (DatePickerDialog) getFragmentManager().findFragmentByTag("datePicker");

        if (finalDatePicker != null) {
            finalDatePicker.setOnDateSetListener(this);
        }

        if(finalPicker != null) {
            finalPicker.setOnTimeSetListener(this);
        }
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

        System.out.println(hourOfDay);
        System.out.println(minute);
        System.out.println(second);

        String hour = hourOfDay<10 ? "0" + hourOfDay : "" + hourOfDay;
        String min = minute<10 ? "0" + minute : "" + minute;
        String sec = second<10 ? "0" + second : "" + second;

        String time = "You picked " + hour + "h " + ":" + min + "m " + ":" + sec + "s ";

        timeText.setText(time);

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        String date = "You picked :" + dayOfMonth + "/" +(++monthOfYear) + "/" + year;

        dateText.setText(date);



    }
}
