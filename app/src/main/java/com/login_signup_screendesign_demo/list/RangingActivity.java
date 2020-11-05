package com.login_signup_screendesign_demo.list;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.login_signup_screendesign_demo.R;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.List;
//import java.util.Collection;
//import java.util.List;

//import es.situm.wayfinding.sample.R;

// //  //  // / / / /573 578 5782 5787 5761

public class RangingActivity extends AppCompatActivity implements BeaconConsumer {

    protected static final String TAG1 = "::MonitoringActivity::";
    protected static final String TAG2 = "::RangingActivity::";
    private BeaconManager beaconManager;
    static  String strJson = "";
    String major="1";
    String minor="22";
    String uuid="24ddf411-8cf1-440c-87cd-e368daf9c93e";
    Boolean flag = false;
    TextView tvResponse;
    int Bid; // 전역변수!!!!!!!!!!@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //public int[] BuildingTextID = {R.id.room1,R.id.room2,R.id.room3};
    public String[] BuildingName = {"형남공학관","문화관","중앙도서관"};
    public String[][] RoomName = {{"424호","522호","1101호"},{"소프트웨어실습실","하드웨어실습실","541호"},{"101호","102호","401호"}};
    TextView tv1;
    TextView tv2;
    TextView tv3;
    Button button1;
    Button button2;
    Button button3;
    boolean flag1 = true;
    boolean flag2 = true;
    boolean flag3 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranging);
        Intent intent = getIntent();
        Bid = intent.getIntExtra("bno",0);
        //tvResponse = (TextView)findViewById(R.id.tvResponse); check
        TextView bn = (TextView)findViewById(R.id.bname);
        bn.setText(BuildingName[Bid] +" " + "회의실");
        tv1 = (TextView)findViewById(R.id.text1);
        tv2 = (TextView)findViewById(R.id.text2);
        tv3 = (TextView)findViewById(R.id.text3);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        tv1.setText(RoomName[Bid][0] + " 회의실");
        tv2.setText(RoomName[Bid][1] + " 회의실");
        tv3.setText(RoomName[Bid][2] + " 회의실");
        //Button button1 = (Button)findViewById(R.id.button1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpAsyncTask httpTask = new HttpAsyncTask(RangingActivity.this);
                httpTask.execute("http://192.168.137.1:8080/app/beacon/open/" + Integer.toString(Bid), uuid, "801", "5761");
                Log.i(TAG2, "send - message");
            }
        });
        //Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpAsyncTask httpTask = new HttpAsyncTask(RangingActivity.this);
                httpTask.execute("http://192.168.137.1:8080/app/beacon/open/" + Integer.toString(Bid), uuid, "801", "578");
                Log.i(TAG2, "send - message");
            }
        });
        //Button button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpAsyncTask httpTask = new HttpAsyncTask(RangingActivity.this);
                httpTask.execute("http://192.168.137.1:8080/app/beacon/open/" + Integer.toString(Bid), uuid,"801", "5782");
                Log.i(TAG2, "send - message");
            }
        });

        beaconManager = BeaconManager.getInstanceForApplication(this);
        //getBeaconParsers() = 활성 비콘 파서 목록을 가져옴
        //거기에 새로운 비콘파서 형식을 만들어서 .add()
        //setBeaconLayout(String) = BLE 알림 내에서 0으로 색인화 된 오프셋을 바이트로 지정하는 문자열을 기반으로 비콘 필드 구문 분석 알고리즘을 정의
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        //Android Activity또는 Service에 바인딩 합니다 BeaconService.
        beaconManager.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Android Activity또는 Service에 바인딩 을 해제. BeaconService.
        beaconManager.unbind(this);
    }
    @Override
    //비콘 서비스가 실행 중이고 BeaconManager를 통해 명령을 수락 할 준비가되면 호출.
    public void onBeaconServiceConnect() {
        //모든 범위 알리미를 제거한다.
        beaconManager.removeAllRangeNotifiers();
        //BeaconService비콘 영역을 보거나 멈출 때 마다 호출해야하는 클래스를 지정.
        beaconManager.addMonitorNotifier(new MonitorNotifier() {
            @Override
            //하나 이상의 비콘 Region이 표시 될 때 호출
            public void didEnterRegion(Region region) {
                Log.i(TAG1, ":최소하나의 비콘 발견:");
            }
            @Override
            //비콘 Region이 보이지 않을 때 호출
            public void didExitRegion(Region region) {
                Log.i(TAG1, ":비콘을 찾을 수 없음:");
//                    button1.setEnabled(false);
//                    button1.setBackgroundColor(Color.parseColor("#999999"));
//                    tv1.setBackgroundColor(Color.parseColor("#68615E"));
//
//                    button2.setEnabled(false);
//                    button2.setBackgroundColor(Color.parseColor("#999999"));
//                    tv2.setBackgroundColor(Color.parseColor("#68615E"));
//button1.setEnabled(false);
//                    button1.setBackgroundColor(Color.parseColor("#999999"));
//                    tv1.setBackgroundColor(Color.parseColor("#68615E"));
//
//                    button2.setEnabled(false);
//                    button2.setBackgroundColor(Color.parseColor("#999999"));
//                    tv2.setBackgroundColor(Color.parseColor("#68615E"));
//
//                    button3.setEnabled(false);
//                    button3.setBackgroundColor(Color.parseColor("#999999"));
//                    tv3.setBackgroundColor(Color.parseColor("#68615E"));
//                    button3.setEnabled(false);
//                    button3.setBackgroundColor(Color.parseColor("#999999"));
//                    tv3.setBackgroundColor(Color.parseColor("#68615E"));
            }
            @Override
            //하나 이상의 비콘 Region이 표시 될 때 MonitorNotifier.INSIDE 상태 값으로 호출
            public void didDetermineStateForRegion(int state, Region region) {
                if(state==0){
                    Log.i(TAG1, ":::::비콘이 보이는 상태이다. state : "+state + ":::::");
                } else {
                    Log.i(TAG1, ":::::비콘이 보이지 않는 상태이다. state : "+state +":::::");
                }
            }
        });

        //범위한정 알림을 추가한다

        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            //눈에 보이는 비콘에 대한 mDistance(major 또는 minor와의 거리를 뜻하는)의 추정치를 제공하기 위해 초당 한 번 호출
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                List<Beacon> list = (List<Beacon>)beacons;
                flag1 = false;
                flag2 = false;
                flag3 = false;
                if (beacons.size() > 0) {
                    for (Beacon B : list) {
                        //Log.i(TAG2, "Beacon : " + B + ":::");
                        Log.i(TAG2, ":::::------------------------------------------------------------------:::::");
//                        Log.i(TAG2, ":::::This :: U U I D :: of beacon   :  " + B.getId1().toString() + ":::::");
//                        Log.i(TAG2, ":::::This ::M a j o r:: of beacon   :  " + B.getId2().toString() + ":::::");
                        Log.i(TAG2, ":::::This ::M i n o r:: of beacon   :  " + B.getId3().toString() + ":::::");
                        Log.i(TAG2, ":::::The beacon Distance is about " + B.getDistance() + " meters away.:::::");
                        // 통신 코드
                        if(B.getDistance() < 0.6) { // 1.5 meter
                            uuid = B.getId1().toString();
                            major = B.getId2().toString();
                            minor = B.getId3().toString();
                            BeaconFlagSet(Integer.parseInt(B.getId3().toString()));
//                            if (flag) {
//                                HttpAsyncTask httpTask = new HttpAsyncTask(RangingActivity.this);
//                                httpTask.execute("http://hmkcode.appspot.com/jsonservlet", B.getId1().toString(), B.getId2().toString(), B.getId3().toString());
//                            }
                            // 문 개폐 일정거리 정해놓고 서버로 보냄
                        }
                        else{
                            Log.e(TAG2, "::::: " + B.getId3() + "out out out"+ B.getDistance() + " meters away.:::::");
                            //Toast.makeText(RangingActivity.this, ": " + B.getId3() + "out"+ B.getDistance() + " meters away.:", Toast.LENGTH_SHORT).show();
                            if (B.getId3().toString().equals("5761")) {
                                flag1 = true;
                                button1.setEnabled(false);
                                button1.setBackgroundColor(Color.parseColor("#999999"));
                                tv1.setBackgroundColor(Color.parseColor("#68615E"));
                            }
                            else if (B.getId3().toString().equals("578")) {
                                flag2 = true;
                                button2.setEnabled(false);
                                button2.setBackgroundColor(Color.parseColor("#999999"));
                                tv2.setBackgroundColor(Color.parseColor("#68615E"));
                            }
                            else if (B.getId3().toString().equals("5782")) {
                                flag3 = true;
                                button3.setEnabled(false);
                                button3.setBackgroundColor(Color.parseColor("#999999"));
                                tv3.setBackgroundColor(Color.parseColor("#68615E"));
                            }
                        }
                    }
                }
//                if (!flag1){
//                    button1.setEnabled(false);
//                    button1.setBackgroundColor(Color.parseColor("#999999"));
//                    tv1.setBackgroundColor(Color.parseColor("#68615E"));
//                }
//                else if (!flag2){
//                    button2.setEnabled(false);
//                    button2.setBackgroundColor(Color.parseColor("#999999"));
//                    tv2.setBackgroundColor(Color.parseColor("#68615E"));
//                }
//                else if (!flag3){
//                    button3.setEnabled(false);
//                    button3.setBackgroundColor(Color.parseColor("#999999"));
//                    tv3.setBackgroundColor(Color.parseColor("#68615E"));
//                }
            }
        });
        try {
            //알려주는 BeaconService전달 일치 비콘을 찾고 시작하는 Region개체를 지역에서 비콘을 볼 수있는 동안 추정 mDistance에있는 모든 초 업데이트를 제공
            beaconManager.startRangingBeaconsInRegion(new Region("C2:02:DD:00:13:DD", null, null, null));
        } catch (RemoteException e) {    }
    }

    public static String POST(String url, JSONObject jsonbeacon){
        InputStream is = null;
        String result = "";
        try {
            URL urlCon = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection)urlCon.openConnection();

            String json = "";

            // build jsonObject // 필요없을듯.
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.accumulate("name", person.getName());
//            jsonObject.accumulate("country", person.getCountry());
//            jsonObject.accumulate("twitter", person.getTwitter());

            // convert JSONObject to JSON to String
            //json = jsonObject.toString();
            //추가
            json = jsonbeacon.toString();
            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // Set some headers to inform server about the type of the content
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setRequestProperty("Content-type", "application/json");

            // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
            Log.i(TAG2, json);
            httpCon.setDoOutput(true);
            // InputStream으로 서버로 부터 응답을 받겠다는 옵션.
            httpCon.setDoInput(true);
            Log.i(TAG2, "Post message to server");
            OutputStream os = httpCon.getOutputStream();
            os.write(json.getBytes("euc-kr"));
            os.flush();
            // receive response as inputStream
            try {
                is = httpCon.getInputStream();
                // convert inputstream to string
                if(is != null)
                    result = convertInputStreamToString(is);
                else
                    result = "Did not work!";
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                httpCon.disconnect();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
//    @Override
//    public void onClick(View view) {
//
//        switch(view.getId()){
//            case R.id.btnPost:
//                if(!validate())
//                    Toast.makeText(getBaseContext(), "Enter some data!", Toast.LENGTH_LONG).show();
//                else {
//                    // call AsynTask to perform network operation on separate thread
//                    //HttpAsyncTask httpTask = new HttpAsyncTask(MainActivity.this);
//                    //httpTask.execute("http://hmkcode.appspot.com/jsonservlet", etName.getText().toString(), etCountry.getText().toString(), etTwitter.getText().toString());
//                }
//                break;
//        }

//    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        private   RangingActivity rangAct;

        HttpAsyncTask(RangingActivity rangingActivity) {
            this.rangAct = rangingActivity;
        }
        @Override
        protected String doInBackground(String... urls) {
            JSONObject jsonObject = new JSONObject();
            try {
                Log.i(TAG2, urls[0]+urls[1]+urls[2]+urls[3]);
                jsonObject.accumulate("uuid", urls[1]);
                jsonObject.accumulate("major", urls[2]);
                jsonObject.accumulate("minor", urls[3]);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return POST(urls[0],jsonObject);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            strJson = result;
            rangAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Toast.makeText(rangAct, "Received!", Toast.LENGTH_LONG).show();
                    //Toast.makeText(RangingActivity.this, "Received!", Toast.LENGTH_SHORT).show();
                    try {
                        JSONArray json = new JSONArray(strJson);
                        //rangAct.tvResponse.setText(json.toString(1)); check
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }

//    private boolean validate(){
////        if(etName.getText().toString().trim().equals(""))
////            return false;
////        else if(etCountry.getText().toString().trim().equals(""))
////            return false;
////        else if(etTwitter.getText().toString().trim().equals(""))
////            return false;
////        else
//            return true;
//    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
    void BeaconFlagSet(Integer id){
        //573 578 5782 5787 5761 // 0 1 2 3 4 5
        if (id == 5761){
            flag1 = true;
            tv1.setBackgroundColor(Color.parseColor("#3F51B5"));
            tv1.setText(RoomName[Bid][0]+ " " + "회의실");
            button1.setEnabled(true);
            button1.setBackgroundColor(Color.parseColor("#017488"));
        }
        else if (id == 578){
            flag2 = true;
            tv2.setBackgroundColor(Color.parseColor("#3F51B5"));
            tv2.setText(RoomName[Bid][1]+ " " + "회의실");
            button2.setEnabled(true);
            button2.setBackgroundColor(Color.parseColor("#017488"));
        }
        else if (id == 5782){
            flag3 = true;
            tv3.setBackgroundColor(Color.parseColor("#3F51B5"));
            tv3.setText(RoomName[Bid][2]+ " " + "회의실");
            button3.setEnabled(true);
            button3.setBackgroundColor(Color.parseColor("#017488"));
        }



    }
}