package com.login_signup_screendesign_demo.binder;

import android.app.Activity;
import android.view.View;

import com.login_signup_screendesign_demo.AspectRatioImageView;
import com.login_signup_screendesign_demo.R;

import androidx.annotation.DrawableRes;
import androidx.recyclerview.widget.RecyclerView;

public class PageBinder extends RecycleBinder<DemoViewType> {

  @DrawableRes private final int mResId;

  public PageBinder(Activity activity, @DrawableRes int resId) {
    super(activity, DemoViewType.PAGE);
    mResId = resId;
  }

  @Override public int layoutResId() {
    return R.layout.row_page;
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(View v) {
    return new ViewHolder(v);
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
    ViewHolder holder = (ViewHolder) viewHolder;
    holder.mImageView.setImageResource(mResId);
  }

  @Override
  public void onViewRecycled(RecyclerView.ViewHolder holder) {

  }

  private static final class ViewHolder extends RecyclerView.ViewHolder {

    private final AspectRatioImageView mImageView;

    public ViewHolder(View itemView) {
      super(itemView);
      mImageView = (AspectRatioImageView) itemView.findViewById(R.id.img_page);
    }
  }
}
