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

import com.login_signup_screendesign_demo.samples.ActivitySampleDashboardTheme;
import com.login_signup_screendesign_demo.utils.Utils;

public class MenuActivity extends AppCompatActivity {

    //private JsonPlaceHolderApi jsonPlaceHolderApi;
    BottomNavigationView bottomNavigationView;
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction;
    BottomMenu BottomMenuFragment = new BottomMenu();
    Notification_Fragment notificationFragment = new Notification_Fragment();
    Mypage_Fragment mypageFragment = new Mypage_Fragment();
    Settings_Fragment settingsFragment = new Settings_Fragment();
    HomeFragment homeFragment = new HomeFragment();
    private int bid = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frameLayout, new HomeFragment(),
                            Utils.Home_Fragment).commit();
        }

        findViewById(R.id.backicon).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(myIntent);

                    }
                });
        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.notification:
                        if (savedInstanceState == null) {
                            fragmentManager
                                    .beginTransaction()
                                    .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                                    .replace(R.id.frameLayout, notificationFragment,
                                            Utils.Notification_Fragment).commit();
                        }
                        break;
                    case R.id.map:
                        Intent myIntent = new Intent(getApplicationContext(), ActivitySampleDashboardTheme.class);
                        startActivity(myIntent);
                        break;
                    case R.id.home:
                        if (savedInstanceState == null) {
                            fragmentManager
                                    .beginTransaction()
                                    .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                                    .replace(R.id.frameLayout, new HomeFragment(),
                                            Utils.Home_Fragment).commit();
                        }
                        break;
                    case R.id.mypage:
                        if (savedInstanceState == null) {
                            fragmentManager
                                    .beginTransaction()
                                    .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                                    .replace(R.id.frameLayout, mypageFragment,
                                            Utils.Mypage_Fragment).commit();
                        };
                        break;
                    case R.id.settings:
                        if (savedInstanceState == null) {
                            fragmentManager
                                    .beginTransaction()
                                    .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                                    .replace(R.id.frameLayout, settingsFragment,
                                            Utils.Settings_Fragment).commit();
                        };

                }
                return false;
            }
        });


    }











}
