package es.situm.wayfinding.sample.list;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import es.situm.wayfinding.sample.R;
import es.situm.wayfinding.sample.samples.ActivitySampleDashboardTheme;

public class MainActivity extends AppCompatActivity implements ViewHolderHelper.OnSampleClickListener {
    protected static final String TAG = "MainActivity";
    private RecyclerView mRecyclerView;
    private ViewHolderHelper mSamplesList;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            // Android M Permission check 
//            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("This app needs location access");
//                builder.setMessage("Please grant location access so this app can detect beacons.");
//                builder.setPositiveButton(android.R.string.ok, null);
//                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialog) {
//                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
//                    }
//                });
//                builder.show();
//            }
//        }
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = findViewById(R.id.samples_recycler_view);
        mSamplesList = new ViewHolderHelper();
        mSamplesList.init(this, mRecyclerView, this);

//        mSamplesList.addItem(R.string.situm_sample_title_simple_map, ActivitySampleSimpleMap.class);
//        mSamplesList.addItem(R.string.situm_sample_title_log_events, ActivitySampleLogEvents.class);
//        mSamplesList.addItem(R.string.situm_sample_title_delegate_back_events, ActivitySampleDelegateBackEvents.class);
//        mSamplesList.addItem(R.string.situm_sample_title_library, ActivitySampleUsingLibrary.class);
//        mSamplesList.addItem(R.string.situm_sample_title_no_search_view, ActivitySampleNoSearchView.class);
        mSamplesList.addItem(R.string.situm_sample_title_dashboard_theme, ActivitySampleDashboardTheme.class);
//        mSamplesList.addItem(R.string.situm_sample_title_interceptors, ActivitySampleCustomizeRequests.class);
//        mSamplesList.addItem(R.string.situm_sample_title_one_building_mode, ActivitySampleOneBuildingMode.class);
//        mSamplesList.addItem(R.string.situm_sample_title_autostart_positioning, ActivitySampleAutostartPositioning.class);
//        mSamplesList.addItem(R.string.situm_sample_title_customize_ui, ActivitySampleCustomizeUI.class);

        Button button1 = (Button) findViewById(R.id.button1) ;
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RangingActivity.class);
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
