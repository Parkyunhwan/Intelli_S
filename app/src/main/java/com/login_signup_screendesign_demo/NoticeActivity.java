package com.login_signup_screendesign_demo;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.login_signup_screendesign_demo.api.IntelliSApi;
import com.login_signup_screendesign_demo.api.JsonPlaceHolderApi;
import com.login_signup_screendesign_demo.api.NetworkUtil;
import com.login_signup_screendesign_demo.models.IntellisPost;
import com.login_signup_screendesign_demo.utils.ListViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class NoticeActivity extends AppCompatActivity {
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private IntelliSApi intellisApi;
    private ListViewAdapter listAdapter;
    private List<String> list;
    private List<String> textlist;
    private List<String> titlelist;
    private List<String> updatedatelist;
    ListView listView;
    int bno;
    Integer id;
    String text;
    String title;
    String updateDatelist;
    //List<Integer>idarr = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_list);
        Intent intent = getIntent();
        bno = intent.getIntExtra("bno",0);
        System.out.println("get_value:::::::::::::::::::::::::::::::::: -- -- - -- -" + bno + "- - - - -");
       listView = (ListView) findViewById(R.id.listView);

        Retrofit retrofit = NetworkUtil.getRetrofit();
        intellisApi = retrofit.create(IntelliSApi.class);

        getPosts();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ContentActivity.class);
                //id = idarr.get(i);
                text = textlist.get(i);
                title = titlelist.get(i);
                updateDatelist = updatedatelist.get(i);
                intent.putExtra("title",title);
                intent.putExtra("text",text);
                intent.putExtra("updatedate",updateDatelist);

                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.backicon).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Intent myIntent = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(myIntent);

                    }
                });

    }


    private void getPosts(){
        titlelist = new ArrayList<>();
        list = new ArrayList<>();
        textlist = new ArrayList<>();
        updatedatelist= new ArrayList<>();
        Map<String, String> parameters = new HashMap<>();
        //parameters.put("_sort","id");
        //parameters.put("_order","desc");
        Call<List<IntellisPost>> call = intellisApi.getPosts(bno);
        call.enqueue(new Callback<List<IntellisPost>>() {
            @Override
            public void onResponse(Call<List<IntellisPost>> call, retrofit2.Response<List<IntellisPost>> response) {

                if (!response.isSuccessful()) {
                    //list.add("Code:" + response.code());

                    list.add("error");
                    return;
                }
                List<IntellisPost> posts = response.body();
                for (IntellisPost post : posts) {
                    String content = "";

                    content += post.getTitle() + "\n";
                  //  content += post.getUpdatetime() +"\n";
                    //   textViewResult.append(content);
                    list.add(content);
                    textlist.add(post.getContent());
                    titlelist.add(post.getTitle());
                    updatedatelist.add(post.getUpdatetime());

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


    }
}