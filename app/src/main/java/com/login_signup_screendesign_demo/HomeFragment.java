package com.login_signup_screendesign_demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.login_signup_screendesign_demo.api.IntelliSApi;
import com.login_signup_screendesign_demo.api.NetworkUtil;
import com.login_signup_screendesign_demo.list.RangingActivity;
import com.login_signup_screendesign_demo.list.ReservationActivity;
import com.login_signup_screendesign_demo.models.IntellisPost;
import com.login_signup_screendesign_demo.utils.ListViewAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {


    private IntelliSApi intellisApi;
    TextView textViewResult;
    private ListViewAdapter listAdapter;
    private List<String> list;
    ListView listView;
    static private TextView morebutton;
    static private TextView morebutton2;
    static private TextView morebutton3;
    static private ImageView backbutton;
    static private TextView buildingnumview;
    int bno;
    public HomeFragment() {


        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        View v;
        Bundle args = getArguments();
        bno = args.getInt("bno");
        System.out.println("home fragment bno:"+bno);;
        initViews(view);
        Retrofit retrofit = NetworkUtil.getRetrofit();
        intellisApi = retrofit.create(IntelliSApi.class);
        getPosts();
        return view;
    }

    private void initViews(View v) {
        listView = (ListView) v.findViewById(R.id.listView);
        morebutton = (TextView) v.findViewById(R.id.morebutton);
        morebutton2 = (TextView) v.findViewById(R.id.morebutton2);
        morebutton3 = (TextView) v.findViewById(R.id.morebutton3);
        backbutton = (ImageView) v.findViewById(R.id.backicon);
        buildingnumview = (TextView) v.findViewById(R.id.buildingnumber);
        String k ;
        if(bno ==0 ){ k= "현재 위치는\n형남공학관 입니다";buildingnumview.setText(k);}
        else if(bno ==1)
        { k = "현재 위치는\n문화관 입니다"; buildingnumview.setText(k);}
        else if (bno ==2){
            k= "현재 위치는\n중앙도서관 입니다"; buildingnumview.setText(k);
        }

        v.findViewById(R.id.morebutton).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Intent myIntent = new Intent(getActivity(), NoticeActivity.class);
                        myIntent.putExtra("bno",bno);
                        startActivity(myIntent);

                    }
                });

        morebutton2.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {

                        Intent myIntent = new Intent(getActivity(), ReservationActivity.class);
                        myIntent.putExtra("bno",bno);
                        startActivity(myIntent);

                    }
                });
        morebutton3.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Intent myIntent = new Intent(getActivity(), RangingActivity.class);
                        myIntent.putExtra("bno",bno);
                        startActivity(myIntent);

                    }
                });

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
                    content += post.getTitle() + "\n";
                  //  content += post.getUpdatetime() + "\n";
                    //   textViewResult.append(content);
                    list.add(content);

                }
                listAdapter = new ListViewAdapter((getActivity()), list);
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