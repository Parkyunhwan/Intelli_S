package com.login_signup_screendesign_demo;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AlarmActivity extends AppCompatActivity {
    ListView listView1;
    ArrayAdapter<String> adapter;
    ArrayList<String> listItem;

    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        setTitle("알림 목록");

        listItem = new ArrayList<String>();


        // 아이템 추가한다.
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                listItem.add(editText1.getText().toString());
//                adapter.notifyDataSetChanged(); // 변경되었음을 어답터에 알려준다.
//                editText1.setText("");
//            }
//        });

        adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,listItem);
        listView1 = findViewById(R.id.listView1);
        listView1.setAdapter(adapter);

        // 각 아이템 클릭시 해당 아이템 삭제한다.
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // 콜백매개변수는 순서대로 어댑터뷰, 해당 아이템의 뷰, 클릭한 순번, 항목의 아이디
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Toast.makeText(getApplicationContext(),listItem.get(i).toString() + " 삭제되었습니다.",Toast.LENGTH_SHORT).show();
                listItem.remove(i);
                adapter.notifyDataSetChanged();
            }
        });
        try {
            request();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void request() throws JSONException {
        //url 요청주소 넣는 editText를 받아 url만들기
        String url = "http://192.168.137.1:8080/log/list";
        //JSON형식으로 데이터 통신을 진행합니다!

        //입력해둔 edittext의 id와 pw값을 받아와 put해줍니다 : 데이터를 json형식으로 바꿔 넣어주었습니다.

        //이제 전송해볼까요?
        final RequestQueue requestQueue = Volley.newRequestQueue(AlarmActivity.this);
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            //데이터 전달을 끝내고 이제 그 응답을 받을 차례입니다.
            @Override
            public void onResponse(JSONArray response) {
                try {
                    System.out.println("데이터전송 성공");

                    for(int i = 0; i < response.length(); i++) {
                        String title = response.getJSONObject(i).getString("title");
                        String content = response.getJSONObject(i).getString("content");
                        listItem.add(0, title + "\n" + content);
                        adapter.notifyDataSetChanged();
                    }

                } catch (Exception e) {
                    Toast.makeText(AlarmActivity.this, "알람이 오지 않았습니다.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
            //서버로 데이터 전달 및 응답 받기에 실패한 경우 아래 코드가 실행됩니다.
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(AlarmActivity.this, "서버 연결 실패", Toast.LENGTH_SHORT).show();
            }
        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);
        //
    }
}
