package com.login_signup_screendesign_demo;

import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD
=======
import androidx.appcompat.app.AppCompatActivity;
>>>>>>> fa09d78ffeb57fa5657ae85dddbd00c23ca6ab09
import android.widget.TextView;

import com.login_signup_screendesign_demo.api.IntelliSApi;

import androidx.appcompat.app.AppCompatActivity;

public class ContentActivity extends AppCompatActivity {
    private IntelliSApi intellisApi;
    String text;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_layout);
        textView = (TextView) findViewById(R.id.textview);

        Intent intent = getIntent();
        text = intent.getStringExtra("text");
        textView.setText(text);
       // getPosttext();
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

