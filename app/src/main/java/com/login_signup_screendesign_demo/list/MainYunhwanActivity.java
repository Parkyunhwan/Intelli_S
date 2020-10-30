package com.login_signup_screendesign_demo.list;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

import com.login_signup_screendesign_demo.R;

//import es.situm.wayfinding.sample.R;
//import es.situm.wayfinding.sample.samples.ActivitySampleDashboardTheme;

public class MainYunhwanActivity extends AppCompatActivity implements ViewHolderHelper.OnSampleClickListener {
    protected static final String TAG = "MainActivity";
//    private RecyclerView mRecyclerView;
//    private ViewHolderHelper mSamplesList;

    private static final int PERMISSION_REQUEST_COARSE_LOCATION=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_yunhwan_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        mRecyclerView = findViewById(R.id.samples_recycler_view);
//        mSamplesList = new ViewHolderHelper();
//        mSamplesList.init(this, mRecyclerView, this);

//        mSamplesList.addItem(R.string.situm_sample_title_simple_map, ActivitySampleSimpleMap.class);
//        mSamplesList.addItem(R.string.situm_sample_title_log_events, ActivitySampleLogEvents.class);
//        mSamplesList.addItem(R.string.situm_sample_title_delegate_back_events, ActivitySampleDelegateBackEvents.class);
//        mSamplesList.addItem(R.string.situm_sample_title_library, ActivitySampleUsingLibrary.class);
//        mSamplesList.addItem(R.string.situm_sample_title_no_search_view, ActivitySampleNoSearchView.class);
//        mSamplesList.addItem(R.string.situm_sample_title_dashboard_theme, ActivitySampleDashboardTheme.class);
//        mSamplesList.addItem(R.string.situm_sample_title_interceptors, ActivitySampleCustomizeRequests.class);
//        mSamplesList.addItem(R.string.situm_sample_title_one_building_mode, ActivitySampleOneBuildingMode.class);
//        mSamplesList.addItem(R.string.situm_sample_title_autostart_positioning, ActivitySampleAutostartPositioning.class);
//        mSamplesList.addItem(R.string.situm_sample_title_customize_ui, ActivitySampleCustomizeUI.class);

        Button button1 = (Button) findViewById(R.id.button1) ;
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainYunhwanActivity.this, RangingActivity.class);
                startActivity(intent);
            }
        });
        Button reservation = (Button) findViewById(R.id.reservation) ;
        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainYunhwanActivity.this, ReservationActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onSampleClick(ViewHolderHelper.DataHolder item) {
        Intent launchSample = new Intent(this, item.getSampleClass());
        startActivity(launchSample);
    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case PERMISSION_REQUEST_COARSE_LOCATION: {
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Log.d(TAG, "coarse location permission granted");
//                } else {
//                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                    builder.setTitle("Functionality limited");
//                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
//                    builder.setPositiveButton(android.R.string.ok, null);
//                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
//
//                        @Override
//                        public void onDismiss(DialogInterface dialog) {
//                        }
//
//                    });
//                    builder.show();
//                }
//                return;
//            }
//        }
//    }
}