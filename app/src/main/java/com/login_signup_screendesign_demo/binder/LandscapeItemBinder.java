package com.login_signup_screendesign_demo.binder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.login_signup_screendesign_demo.MenuActivity;
import com.login_signup_screendesign_demo.R;

import androidx.annotation.DrawableRes;
import androidx.recyclerview.widget.RecyclerView;

public class LandscapeItemBinder extends RecycleBinder<DemoViewType> {

  @DrawableRes private final int mResId;
  private final String mText;
  int bno =0;
  public LandscapeItemBinder(Activity activity, @DrawableRes int resId, String text) {
    super(activity, DemoViewType.LANDSCAPE_ITEM);
    mResId = resId;
    mText = text;
  }

  @Override public int layoutResId() {
    return R.layout.row_landscape_item;
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(View v) {
    return new ViewHolder(v);
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

    ViewHolder holder = (ViewHolder) viewHolder;
    holder.mImageView.setImageResource(mResId);
    holder.mImageView.setOnClickListener(new View.OnClickListener(){

      @Override
              public void onClick(View v){

        if(position==1){ bno=0;}
        else if(position==3){bno =2;}
        else if(position==5) { bno=1;}
        Context context = v.getContext();

        Intent myIntent = new Intent(context, MenuActivity.class);
        System.out.println("position:"+position);
        System.out.println("bno:"+bno);
        myIntent.putExtra("bno",bno);

        context.startActivity(myIntent);
      }

    });
  }

  @Override
  public void onViewRecycled(RecyclerView.ViewHolder holder) {

  }

  private static final class ViewHolder extends RecyclerView.ViewHolder {

    private final ImageView mImageView;


    public ViewHolder(View itemView) {
      super(itemView);
      mImageView = (ImageView) itemView.findViewById(R.id.img_landscape_item);

    }
  }
}
