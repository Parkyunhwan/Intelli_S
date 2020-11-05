package com.login_signup_screendesign_demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.login_signup_screendesign_demo.binder.DemoSectionType;
import com.login_signup_screendesign_demo.binder.DemoViewType;
import com.login_signup_screendesign_demo.binder.LandscapeItemBinder;
import com.login_signup_screendesign_demo.binder.RecyclerBinderAdapter;
import com.login_signup_screendesign_demo.binder.TitleBinder;
import com.login_signup_screendesign_demo.utils.ItemDecorations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class BuildingPick_Activity extends AppCompatActivity {

    public static void startActivity(Activity activity) {
        activity.startActivity(new Intent(activity, BuildingPick_Activity.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buildingpick);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_vertical);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // create ItemDecoration
        RecyclerView.ItemDecoration decoration = ItemDecorations.vertical(this)
                .first(R.drawable.shape_decoration_grey_h_16)
                .type(DemoViewType.LANDSCAPE_ITEM.ordinal(), R.drawable.shape_decoration_grey_h_16)

                .create();
        recyclerView.addItemDecoration(decoration);

        RecyclerBinderAdapter<DemoSectionType, DemoViewType> adapter = new RecyclerBinderAdapter<>();
        adapter.add(DemoSectionType.ITEM, new TitleBinder(this, "형남공학관"));
        adapter.add(DemoSectionType.ITEM, new LandscapeItemBinder(this, R.drawable.hyungnam, "형남공학관"));
        adapter.add(DemoSectionType.ITEM, new TitleBinder(this, "도서관"));
        adapter.add(DemoSectionType.ITEM, new LandscapeItemBinder(this, R.drawable.library, "도서관"));
        adapter.add(DemoSectionType.ITEM, new TitleBinder(this, "문화관"));
        adapter.add(DemoSectionType.ITEM, new LandscapeItemBinder(this, R.drawable.culturebuilding, "문화관"));
        recyclerView.setAdapter(adapter);

        findViewById(R.id.backicon).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(myIntent);

                    }
                });


    }


}




