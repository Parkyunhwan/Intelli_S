package com.login_signup_screendesign_demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.login_signup_screendesign_demo.api.IntelliSApi;

import androidx.appcompat.app.AppCompatActivity;

public class ContentActivity extends AppCompatActivity {
    private IntelliSApi intellisApi;
    String text;
    String title;
    String updatedate;
    TextView textView;
    TextView textView2;
    TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_layout);
        textView = (TextView) findViewById(R.id.content);
        textView2 = (TextView)findViewById(R.id.title);
        textView3 = (TextView)findViewById(R.id.update_date);
        Intent intent = getIntent();
        text = intent.getStringExtra("text")+'\n';
        title = intent.getStringExtra("title") + '\n';
        updatedate = intent.getStringExtra("updatedate");
        textView.setText(text);
        textView2.setText(title);
        textView3.setText(updatedate);
       // getPosttext();

        findViewById(R.id.backicon).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Intent myIntent = new Intent(getApplicationContext(), NoticeActivity.class);
                        startActivity(myIntent);

                    }
                });
    }


    /*private void getPosttext(){


        Map<String, String> parameters = new HashMap<>();
       // parameters.put("userId","1");
       // parameters.put("id",Integer.toString(Id));

        Call<IntellisPost> call = intellisApi.getoneidPost(Id);
        call.enqueue(new Callback<IntellisPost>() {
                @Override
            public void onResponse(Call<IntellisPost> call, retrofit2.Response<IntellisPost> response) {

                if (!response.isSuccessful()) {

                     textView.setText("Code:" + response.code());
                     return;
                }
                    IntellisPost post = response.body();

                    String content = "";
                    content = post.getContent();
                    textView.setText(content);

            }

            @Override
            public void onFailure(Call<IntellisPost> call, Throwable t) {

                textView.setText(t.getMessage());
            }
        });
        //  list.add("hohoho");*/

   // }
}

