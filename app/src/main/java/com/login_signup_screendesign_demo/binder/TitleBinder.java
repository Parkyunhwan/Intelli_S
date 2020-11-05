package com.login_signup_screendesign_demo.binder;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.login_signup_screendesign_demo.R;

import androidx.recyclerview.widget.RecyclerView;

public class TitleBinder extends RecycleBinder<DemoViewType> {

  private final String mTitle;

  public TitleBinder(Activity activity, String title) {
    super(activity, DemoViewType.TITLE);
    mTitle = title;
  }

  @Override public int layoutResId() {
    return R.layout.row_title;
  }

  @Override public  RecyclerView.ViewHolder onCreateViewHolder(View v) {
    return new ViewHolder(v);
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
    ViewHolder holder = (ViewHolder) viewHolder;
    holder.mTextView.setText(mTitle);
  }

  @Override
  public void onViewRecycled(RecyclerView.ViewHolder holder) {

  }

  private static final class ViewHolder extends RecyclerView.ViewHolder {

    private final TextView mTextView;

    public ViewHolder(View itemView) {
      super(itemView);
      mTextView = (TextView) itemView.findViewById(R.id.txt_row_title);
    }
  }
}
