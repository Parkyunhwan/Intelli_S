package com.login_signup_screendesign_demo;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.login_signup_screendesign_demo.api.IntelliSApi;
import com.login_signup_screendesign_demo.api.NetworkUtil;
import com.login_signup_screendesign_demo.list.MainYunhwanActivity;
import com.login_signup_screendesign_demo.models.IntellisPost;
import com.login_signup_screendesign_demo.samples.ActivitySampleDashboardTheme;
import com.login_signup_screendesign_demo.utils.ListViewAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class MenuActivity extends AppCompatActivity {

    //private JsonPlaceHolderApi jsonPlaceHolderApi;
    BottomNavigationView bottomNavigationView;
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction;
    BottomMenu BottomMenuFragment = new BottomMenu();
    Notification_Fragment notificationFragment = new Notification_Fragment();
    Mypage_Fragment mypageFragment = new Mypage_Fragment();
    Settings_Fragment settingsFragment = new Settings_Fragment();

    private IntelliSApi intellisApi;
    TextView textViewResult;
    private ListViewAdapter listAdapter;
    private List<String> list;
    ListView listView;
    static private TextView morebutton;
    //ListView listView = (ListView) findViewById(R.id.listView);
  //  Retrofit retrofit = NetworkUtil.jphgetRetrofit();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.notification:
                        fragmentTransaction.replace(R.id.frameLayout, notificationFragment).commitAllowingStateLoss();
                        break;
                    case R.id.map:
                        Intent myIntent = new Intent(getApplicationContext(), ActivitySampleDashboardTheme.class);
                        startActivity(myIntent);
                        break;
                    case R.id.home:
                        Intent intent = new Intent(getApplicationContext(), MainYunhwanActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.mypage:
                        fragmentTransaction.replace(R.id.frameLayout, mypageFragment).commitAllowingStateLoss();
                        break;
                    case R.id.settings:
                        fragmentTransaction.replace(R.id.frameLayout, settingsFragment).commitAllowingStateLoss();
                        break;

                }
                return false;
            }
        });


    //    textViewResult = (TextView)findViewById(R.id.text_view_result);
        initViews();
        Retrofit retrofit = NetworkUtil.getRetrofit();
        intellisApi = retrofit.create(IntelliSApi.class);
        getPosts();
        findViewById(R.id.morebutton).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Intent myIntent = new Intent(getApplicationContext(), NoticeActivity.class);
                        startActivity(myIntent);

                    }
                });

       //finish();
    }


    private void initViews() {
        listView = (ListView) findViewById(R.id.listView);
        morebutton = (TextView) findViewById(R.id.morebutton);
    }





    private void getPosts(){


        list = new ArrayList<>();


        int bno =2;


        Call<List<IntellisPost>> call = intellisApi.getPosts(bno);
        call.enqueue(new Callback<List<IntellisPost>>() {
            @Override
            public void onResponse(Call<List<IntellisPost>> call, retrofit2.Response<List<IntellisPost>> response) {

                if (!response.isSuccessful()) {
                    //list.add("Code:" + response.code());
                    textViewResult.setText("Code:" + response.code());
                    list.add("error");
                    return;
                }
                List<IntellisPost> posts = response.body();
               for (IntellisPost post : posts) {
                    String content = "";

                    content += "Writer: " + post.getWriter() + "\n";
                    content += "Title:" + post.getTitle() + "\n";
                    content += "bno:" + post.getBno() + "\n";
                    content += "regtime:"+ post.getRegTime() + "\n";
                   content += "updatetime:"+ post.getUpdatetime() + "\n";
                    content += "content:"+ post.getContent() + "\n";
                 //   textViewResult.append(content);
                    list.add(content);

                }
                listAdapter = new ListViewAdapter((getApplicationContext()), list);
                listView.setAdapter(listAdapter);
            }

            @Override
            public void onFailure(Call<List<IntellisPost>> call, Throwable t) {
                list.add("failure");
              //  textViewResult.setText(t.getMessage());
                //list.add( t.getMessage());

            }
        });
      //  list.add("hohoho");

    }


}
