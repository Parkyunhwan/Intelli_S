package com.login_signup_screendesign_demo.binder;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.login_signup_screendesign_demo.R;

import androidx.recyclerview.widget.RecyclerView;

public class LandscapeDescriptionBinder extends RecycleBinder<DemoViewType> {

  private final String mText;

  @Override public RecyclerView.ViewHolder onCreateViewHolder(View v) {
    return new ViewHolder(v);
  }
  public LandscapeDescriptionBinder(Activity activity, String text) {
    super(activity, DemoViewType.LANDSCAPE_DESCRIPTION);
    mText = text;
  }

  @Override public int layoutResId() {
    return R.layout.row_landscape_description;
  }


  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
    ViewHolder holder = (ViewHolder) viewHolder;
    holder.mTextView.setText(mText);
  }

  @Override
  public void onViewRecycled(RecyclerView.ViewHolder holder) {

  }


  private static final class ViewHolder extends RecyclerView.ViewHolder {

    private final TextView mTextView;

    public ViewHolder(View itemView) {
      super(itemView);
      mTextView = (TextView) itemView.findViewById(R.id.txt_landscape_description);
    }
  }
}
