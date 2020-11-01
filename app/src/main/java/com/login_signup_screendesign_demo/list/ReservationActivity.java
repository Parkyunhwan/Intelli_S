
package com.login_signup_screendesign_demo.list;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.login_signup_screendesign_demo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

public class ReservationActivity extends AppCompatActivity {

    private TextView textView_Date;
    private DatePickerDialog.OnDateSetListener callbackMethod;

    private TextView textView_Starttime;
    private TimePickerDialog.OnTimeSetListener callbackStarttimeMethod;

    private TextView textView_Endtime;
    private TimePickerDialog.OnTimeSetListener callbackEndtimeMethod;
    private Boolean allDay = false;
    String currBuilding = "";
    String SDate = "";
    String STime = "";
    String ETime = "";
    String[] Building = {"- 건물 선택 -","형남공학관","문화관","중앙도서관"};
    int SelectedBuildingID = 0;
//    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    int bno = 0;
    int rno = 0;
    View[] im = new View[8];
    public int[][] imv= {{R.id.view00, R.id.view01, R.id.view02, R.id.view03,R.id.view04, R.id.view05, R.id.view06, R.id.view07},
            {R.id.view10, R.id.view11, R.id.view12, R.id.view13,R.id.view14, R.id.view15, R.id.view16, R.id.view17},
            {R.id.view20, R.id.view21, R.id.view22, R.id.view23,R.id.view24, R.id.view25, R.id.view26, R.id.view27}};

    Spinner spiner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

//        arrayList = new ArrayList<>();
//        arrayList.add("형남공학관");
//        arrayList.add("중앙도서관");
//        arrayList.add("문화관");

        //Spinner 코드
        spiner = (Spinner) findViewById(R.id.spinner);
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                Building);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner.setAdapter(arrayAdapter);
        spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
                    Toast.makeText(getApplicationContext(),"건물을 선택해주세요.",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                Toast.makeText(getApplicationContext(),Building[i]+"이 선택되었습니다.",
                        Toast.LENGTH_SHORT).show();
                        SelectedBuildingID = i;
                        currBuilding = Building[i];
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        //buttonLookup
        Button lookupButton = (Button) findViewById(R.id.buttonLookup);
        lookupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // 서버로 조회 하기 (서버 통신)
                if (SelectedBuildingID != 0 && !SDate.equals(""))
                    requestLookup();
                else
                    Toast.makeText(getApplicationContext(),"건물과 날짜를 선택해주세요.",
                            Toast.LENGTH_SHORT).show();
            }
        });


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
                TimePickerDialog dialogtime = new TimePickerDialog(ReservationActivity.this, callbackEndtimeMethod, 9, 0, true);
                dialogtime.show();
            }
        });

        Button ReservationButton = (Button) findViewById(R.id.buttonReservation);
        //TextView NumberBno = (TextView) findViewById(R.id.TextBno);
        TextView NumberRno = (TextView) findViewById(R.id.TextRno);
        ReservationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                rno = Integer.parseInt(NumberRno.getText().toString()); // check
                requestReserve();
//                bno = Integer.parseInt((String) NumberBno.getText());
//                rno = Integer.parseInt((String) NumberRno.getText());
                //String combi = SDate + STime + ETime + NumberRno.getText();

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
    public void requestLookup(){
        //url 요청주소 넣는 editText를 받아 url만들기
        String url = ""; // url 정보 SDate, SelectedBuildingID

        //JSON형식으로 데이터 통신을 진행합니다!
        JSONObject testjson = new JSONObject();
        try {
            //입력해둔 edittext의 id와 pw값을 받아와 put해줍니다 : 데이터를 json형식으로 바꿔 넣어주었습니다.
            testjson.put("BuildingID", SelectedBuildingID); // or currBuilding->string
            testjson.put("LookupDate", SDate);
            String jsonString = testjson.toString(); //완성된 json 포맷

            //이제 전송
            final RequestQueue requestQueue = Volley.newRequestQueue(ReservationActivity.this);
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,testjson, new Response.Listener<JSONObject>() {

                //데이터 전달을 끝내고 이제 그 응답을 받을 차례
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        System.out.println("데이터전송 성공");

                        //받은 json형식의 응답을 받아
                        JSONObject jsonObject = new JSONObject(response.toString());

                        //key값에 따라 value값을 쪼개 받아옵니다.
                        Boolean[] reserveTime = new Boolean[8];
                        String roomNum = jsonObject.getString("roomNum");
                        JSONArray reserveArray = jsonObject.getJSONArray("reserveInfo");

                        for (int i = 0; i < reserveArray.length(); i++)
                            reserveTime[i] = reserveArray.getBoolean(i);

                        // 서버 정보 조회후
                        //String roomNum = "1222"; Boolean[] reserveTime = {true,true,false,false,true,false,true,true};
                        TextView room1text = (TextView) findViewById(R.id.room1);
                        room1text.setText(currBuilding + " " + roomNum + "호");
                        View view00 = (View) findViewById(R.id.view00);

                        for(int i=0;i<imv[0].length;i++)
                        {
                            im[i] = (View) findViewById(imv[0][i]);//Attaching ids
                            if (reserveTime[i]) {
                                im[i].setBackgroundColor(Color.parseColor("#50ADBD"));
                            } else {
                                im[i].setBackgroundColor(Color.parseColor("#999999"));
                            }
                        }

                        //만약 그 값이 같다면 로그인에 성공한 것입니다.
//                        if(resultId.equals("OK") & resultPassword.equals("OK")){
//
//                            //이 곳에 성공 시 화면이동을 하는 등의 코드를 입력하시면 됩니다.
//                        }else{
//                            //로그인에 실패했을 경우 실행할 코드를 입력하시면 됩니다.
//                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //서버로 데이터 전달 및 응답 받기에 실패한 경우 아래 코드가 실행됩니다.
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    //Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonObjectRequest);
            //
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void requestReserve(){
        //url 요청주소 넣는 editText를 받아 url만들기
        String url = ""; // url 정보 SDate, SelectedBuildingID

        //JSON형식으로 데이터 통신을 진행합니다!
        JSONObject testjson = new JSONObject();
        try {
            //입력 put해줍니다 : 데이터를 json형식으로 바꿔 넣어주었습니다.
            testjson.put("day", SDate); // or currBuilding->string
            testjson.put("start", STime);
            testjson.put("end", ETime);
            testjson.put("bno",SelectedBuildingID);
            testjson.put("rno",rno);
            //String jsonString = testjson.toString(); //완성된 json 포맷

            //이제 전송
            final RequestQueue requestQueue = Volley.newRequestQueue(ReservationActivity.this);
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,testjson, new Response.Listener<JSONObject>() {

                //데이터 전달을 끝내고 이제 그 응답을 받을 차례
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        System.out.println("예약 성공");

                        //받은 json형식의 응답을 받아
                        JSONObject jsonObject = new JSONObject(response.toString());

                        //key값에 따라 value값을 쪼개 받아옵니다.
                        Boolean[] reserveTime = new Boolean[8];
                        String roomNum = jsonObject.getString("roomNum");
                        JSONArray reserveArray = jsonObject.getJSONArray("reserveInfo");

                        for (int i = 0; i < reserveArray.length(); i++)
                            reserveTime[i] = reserveArray.getBoolean(i);

                        // 서버 정보 조회후
                        //String roomNum = "1222"; Boolean[] reserveTime = {true,true,false,false,true,false,true,true};
                        TextView room1text = (TextView) findViewById(R.id.room1);
                        room1text.setText(currBuilding + " " + roomNum + "호");
                        View view00 = (View) findViewById(R.id.view00);

                        for(int i=0;i<imv[0].length;i++)
                        {
                            im[i] = (View) findViewById(imv[0][i]);//Attaching ids
                            if (reserveTime[i]) {
                                im[i].setBackgroundColor(Color.parseColor("#50ADBD"));
                            } else {
                                im[i].setBackgroundColor(Color.parseColor("#999999"));
                            }
                        }

                        //만약 그 값이 같다면 로그인에 성공한 것입니다.
//                        if(resultId.equals("OK") & resultPassword.equals("OK")){
//
//                            //이 곳에 성공 시 화면이동을 하는 등의 코드를 입력하시면 됩니다.
//                        }else{
//                            //로그인에 실패했을 경우 실행할 코드를 입력하시면 됩니다.
//                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //서버로 데이터 전달 및 응답 받기에 실패한 경우 아래 코드가 실행됩니다.
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    //Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonObjectRequest);
            //
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}