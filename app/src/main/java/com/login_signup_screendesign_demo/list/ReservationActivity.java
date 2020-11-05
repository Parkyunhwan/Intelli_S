package com.login_signup_screendesign_demo.list;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.login_signup_screendesign_demo.R;
import com.login_signup_screendesign_demo.utils.BuildingID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReservationActivity extends AppCompatActivity {
    private TextView textView_Date;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    private TextView UsageName;
    private TextView textView_Starttime;
    private TimePickerDialog.OnTimeSetListener callbackStarttimeMethod;
    private TextView textView_Endtime;
    private TimePickerDialog.OnTimeSetListener callbackEndtimeMethod;
    private Boolean allDay = false;
    String SDate = "";
    String STime = "";
    String ETime = "";
    String[] Usage = {"- 용도 선택 -","학습","회의","수업","기타"};
    String currUsage = "";
    //전역변수 사용할 것 check
    int SelectedBuildingID;

    ArrayAdapter<String> arrayAdapter;
    int bno;
    int rno = 0;
    View[] im = new View[8];
    public int[][] imv= {{R.id.view00, R.id.view01, R.id.view02, R.id.view03,R.id.view04, R.id.view05, R.id.view06, R.id.view07},
            {R.id.view10, R.id.view11, R.id.view12, R.id.view13,R.id.view14, R.id.view15, R.id.view16, R.id.view17},
            {R.id.view20, R.id.view21, R.id.view22, R.id.view23,R.id.view24, R.id.view25, R.id.view26, R.id.view27}};

    // 형남, 문화, 중앙
    public int[] BuildingTextID = {R.id.room1,R.id.room2,R.id.room3};
    public String[] BuildingName = {"형남공학관","문화관","중앙도서관"};
    public String[][] RoomName = {{"424호","522호","1101호","- 회의실 선택 -"},{"소프트웨어실습실","하드웨어실습실","541호","- 회의실 선택 -"},{"101호","102호","401호","- 회의실 선택 -"}};

    Spinner spiner;
    Spinner spiner2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        Intent intent = getIntent();
        SelectedBuildingID = intent.getIntExtra("bno",0);

        Calendar now = Calendar.getInstance();
        this.InitializeView();
        this.InitializeListener(); // SDate의 갱신
        //Spinner 코드
        TextView buildingText = (TextView) findViewById(R.id.Building_text);
        buildingText.setText(BuildingName[SelectedBuildingID] + " 회의실 예약하기");
        spiner = (Spinner) findViewById(R.id.spinner);
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                Usage);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner.setAdapter(arrayAdapter);
        spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
                    Toast.makeText(getApplicationContext(),"날짜와 용도를 선택해주세요.",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                Toast.makeText(getApplicationContext(),Usage[i]+"이(가) 선택되었습니다.",
                        Toast.LENGTH_SHORT).show();
                currUsage = Usage[i];
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        for (int i = 0; i < 3; i++) {
            TextView t = (TextView) findViewById(BuildingTextID[i]);
            t.setText(BuildingName[SelectedBuildingID] + " " + RoomName[SelectedBuildingID][i]);
        }
        //buttonLookup
        Button lookupButton = (Button) findViewById(R.id.buttonLookup);
        lookupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // 서버로 조회 하기 (서버 통신)
                if (!SDate.equals("")) {
                    requestLookup();
                }
                else
                    Toast.makeText(getApplicationContext(),"건물과 날짜를 선택해주세요.",
                            Toast.LENGTH_SHORT).show();
            }
        });



        onDateFormat(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE));
        requestLookup();
        textView_Starttime = (TextView)findViewById(R.id.textView_Starttime);
        textView_Endtime = (TextView)findViewById(R.id.textView_Endtime);


        Button DateButton = (Button) findViewById(R.id.button);
        DateButton.setOnClickListener(new View.OnClickListener() {
            //Calendar now = Calendar.getInstance();

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
                TimePickerDialog dialogtime = new TimePickerDialog(ReservationActivity.this, callbackStarttimeMethod, 9, 0, true);
                Date nDate = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(nDate);
                dialogtime.show();
            }
        });

        Button EndTimeButton = (Button) findViewById(R.id.buttonEndTime);
        EndTimeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TimePickerDialog dialogtime = new TimePickerDialog(ReservationActivity.this, callbackEndtimeMethod, 10, 0, true);
                dialogtime.show();
            }
        });

        spiner2 = (Spinner) findViewById(R.id.spinner2);
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                RoomName[SelectedBuildingID]);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner2.setAdapter(arrayAdapter);
        spiner2.setSelection(3);
        spiner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==3){
                    Toast.makeText(getApplicationContext(),"회의실을 선택해주세요.",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),RoomName[SelectedBuildingID][i]+"이(가) 선택되었습니다.",
                            Toast.LENGTH_SHORT).show();
                    rno = i;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        Button ReservationButton = (Button) findViewById(R.id.buttonReservation);
        ReservationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (rno == 4) {
                    Toast.makeText(getApplicationContext(), "회의실을 선택해주세요.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                requestReserve();
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
                STime += "00:00";
//                if(minute < 10){
//                    STime += "00:00";
//                }
//                else{
//                    STime = minute + ":00";
//                }
                textView_Starttime.setText(STime);
            }

        };

        callbackEndtimeMethod = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                textView_Endtime.setText(hourOfDay + "시" + minute + "분");
                if(hourOfDay < 9){
                    if(minute!=0)
                        ETime = "0" + (hourOfDay+1) + ":";
                    else
                        ETime = "0" + hourOfDay + ":";
                }
                else{
                    if(minute!=0)
                        ETime = (hourOfDay+1) + ":";
                    else
                        ETime = hourOfDay + ":";
                }
                ETime += "00:00";
//                if(minute < 10){
//                    ETime += "00:00";
//                }
//                else{
//                    ETime = minute + ":00";
//                }
                if (hourOfDay == 17){
                    ETime = "05:00:00";
                }
                textView_Endtime.setText(ETime);
            }
        };

        callbackMethod = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                monthOfYear += 1;
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
    public void requestLookup(){
        System.out.println("requestLookup 시작");
        //url 요청주소 넣는 editText를 받아 url만들기
        String url = "http://192.168.137.1:8080/reserve/lookup"; // url 정보 SDate, SelectedBuildingID

        //JSON형식으로 데이터 통신을 진행합니다!
        url += "?day=" + SDate + "&bno=" + SelectedBuildingID;

        //이제 전송
        final RequestQueue requestQueue = Volley.newRequestQueue(ReservationActivity.this);
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            //데이터 전달을 끝내고 이제 그 응답을 받을 차례
            @Override
            public void onResponse(JSONArray response) {
                try {
                    System.out.println("데이터전송 성공");
                    for(int i = 0; i < 3; i++) {
                        for(int j = 0; j < 8; j++) {
                            View v = (View) findViewById(imv[i][j]);
                            v.setBackgroundColor(Color.parseColor("#50ADBD")); //예약된 색상
                        }
                    }

                    // response 갯수만큼 반복
                    // 매 반복에서 start_time, end_time, rno를 꺼냄
                    Toast.makeText(ReservationActivity.this, "회의실 정보가 조회되었습니다.", Toast.LENGTH_SHORT).show();
                    for (int i=0; i < response.length(); i++) {
                        System.out.println("데이터전송 성공");

                        System.out.println(response.getJSONObject(i).getInt("start"));
                        System.out.println(response.getJSONObject(i).getInt("end"));
                        System.out.println(response.getJSONObject(i).getInt("rno"));
                        //9 11 - 0 1
                        int s = response.getJSONObject(i).getInt("start") - 9;
                        int e = response.getJSONObject(i).getInt("end") - 9;
                        int rn = response.getJSONObject(i).getInt("rno"); // rno는 회의실 순서 ex) 0:522 1:424 2:525 // bno -> 0, 1, 2 형남, 문화, 중앙도서관
//                        TextView t = (TextView) findViewById(BuildingTextID[rn]);
//                        t.setText(BuildingName[SelectedBuildingID] + " " + RoomName[rn]);
                        // 시작시간 부터 종료예약시간까지 색깔을 바꿔줌
                        for(int j = s; j < e; j++) {
                            View v = (View) findViewById(imv[rn][j]);
                            v.setBackgroundColor(Color.parseColor("#999999")); //예약된 색상
                        }
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //서버로 데이터 전달 및 응답 받기에 실패한 경우 아래 코드가 실행됩니다.
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(ReservationActivity.this, "서버 연결 실패!", Toast.LENGTH_SHORT).show();
            }
        });
        //jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);
        //
    }

    public void requestReserve(){
        //url 요청주소 넣는 editText를 받아 url만들기
        String url = "http://192.168.137.1:8080/reserve/register"; // url 정보 SDate, SelectedBuildingID

        //JSON형식으로 데이터 통신을 진행합니다!
        JSONObject testjson = new JSONObject();
        try {
            //입력 put해줍니다 : 데이터를 json형식으로 바꿔 넣어주었습니다.
            //JSONArray jsonArr1 = new JSONArray();
            testjson.put("title", currUsage);
            testjson.put("day", SDate); // or currBuilding->string
            testjson.put("start", STime);
            testjson.put("end", ETime);
            testjson.put("bno",SelectedBuildingID);
            testjson.put("rno",rno);
            //jsonArr1.put(testjson);
            //String jsonString = testjson.toString(); //완성된 json 포맷

            //이제 전송
            final RequestQueue requestQueue = Volley.newRequestQueue(ReservationActivity.this);
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, testjson, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        System.out.println("예약 성공");

                        //받은 json형식의 응답을 받아
                        //JSONObject jsonarray = new JSONObject(response.toString());
                        String flag = response.getString("ret");

                        if (flag.equals("success")){
                            Toast.makeText(ReservationActivity.this, "회의실이 예약되었습니다.", Toast.LENGTH_SHORT).show();
                            requestLookup();
                        }
                        else
                            Toast.makeText(getApplicationContext(),"해당 시간은 이미 예약되어있습니다. 다른 시간을 선택해주세요.",
                                    Toast.LENGTH_SHORT).show();
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //서버로 데이터 전달 및 응답 받기에 실패한 경우 아래 코드가 실행됩니다.
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(ReservationActivity.this, "해당 시간은 예약이 불가능합니다. (9시 ~ 17시 선택)", Toast.LENGTH_SHORT).show();
                }
            });
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonObjectRequest);
            //
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onDateFormat(int year, int monthOfYear, int dayOfMonth)
    {
        monthOfYear += 1;
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


}