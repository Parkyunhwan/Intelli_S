
package com.login_signup_screendesign_demo.list;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.login_signup_screendesign_demo.R;

import java.util.Calendar;
import java.util.Date;

public class ReservationActivity extends AppCompatActivity {

    private TextView textView_Date;
    private DatePickerDialog.OnDateSetListener callbackMethod;

    private TextView textView_Starttime;
    private TimePickerDialog.OnTimeSetListener callbackStarttimeMethod;

    private TextView textView_Endtime;
    private TimePickerDialog.OnTimeSetListener callbackEndtimeMethod;
    private DatePickerDialog dpd;
    private Boolean allDay = false;
    String SDate = "";
    String STime = "";
    String ETime = "";
    int bno = 0;
    int rno = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        this.InitializeView();
        this.InitializeListener();
        textView_Starttime = (TextView)findViewById(R.id.textView_Starttime);
        textView_Endtime = (TextView)findViewById(R.id.textView_Endtime);
        Calendar now = Calendar.getInstance();

        Button DateButton = (Button) findViewById(R.id.button);
        DateButton.setOnClickListener(new View.OnClickListener() {
            Calendar now = Calendar.getInstance();

            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(ReservationActivity.this, callbackMethod, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE));
                Date nDate = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(nDate); // 10분 더하기
                dialog.getDatePicker().setMinDate(cal.getTime().getTime());
                cal.add(Calendar.DATE, 6);
                dialog.getDatePicker().setMaxDate(cal.getTime().getTime());
                dialog.show();
            }
        });

        Button StartTimeButton = (Button) findViewById(R.id.buttonStartTime);
        StartTimeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TimePickerDialog dialogtime = new TimePickerDialog(ReservationActivity.this, callbackStarttimeMethod, 9, 0, false);
                Date nDate = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(nDate);

                dialogtime.show();
            }
        });

        Button EndTimeButton = (Button) findViewById(R.id.buttonEndTime);
        EndTimeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TimePickerDialog dialogtime = new TimePickerDialog(ReservationActivity.this, callbackEndtimeMethod, 9, 0, false);
                dialogtime.show();
            }
        });

        Button ReservationButton = (Button) findViewById(R.id.buttonReservation);
        TextView NumberBno = (TextView) findViewById(R.id.TextBno);
        TextView NumberRno = (TextView) findViewById(R.id.TextRno);
        ReservationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                bno = Integer.parseInt((String) NumberBno.getText());
//                rno = Integer.parseInt((String) NumberRno.getText());
                String combi = SDate + STime + ETime + NumberBno.getText() + NumberRno.getText();
                NumberBno.setText(combi);
            }
        });

    }

    public void InitializeView()
    {
        textView_Date = (TextView)findViewById(R.id.textView_date);
        textView_Starttime = (TextView)findViewById(R.id.textView_Starttime);
        textView_Endtime = (TextView)findViewById(R.id.textView_Endtime);
    }

    public void InitializeListener()
    {

        callbackStarttimeMethod = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                textView_Starttime.setText(hourOfDay + "시" + minute + "분");
                if(hourOfDay < 10){
                    STime = "0" + hourOfDay + ":";
                }
                else{
                    STime = hourOfDay + ":";
                }

                if(minute < 10){
                    STime += "0" + minute + ":00";
                }
                else{
                    STime = minute + ":00";
                }
            }

        };

        callbackEndtimeMethod = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                textView_Endtime.setText(hourOfDay + "시" + minute + "분");
                if(hourOfDay < 10){
                    ETime = "0" + hourOfDay + ":";
                }
                else{
                    ETime = hourOfDay + ":";
                }

                if(minute < 10){
                    ETime += "0" + minute + ":00";
                }
                else{
                    ETime = minute + ":00";
                }

            }
        };

        callbackMethod = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                textView_Date.setText(year + "년" + monthOfYear + "월" + dayOfMonth + "일");
                if(monthOfYear < 10){
                    SDate = year + "/0" + monthOfYear;
                }
                else{
                    SDate = year + "/" + monthOfYear;
                }

                if(dayOfMonth < 10){
                    SDate += "/0" + dayOfMonth;
                }
                else{
                    SDate += "/" + dayOfMonth;
                }

            }
        };


    }


//    public void OnClickHandler(View view)
//    {
//    }
}