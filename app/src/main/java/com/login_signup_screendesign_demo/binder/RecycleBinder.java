package com.login_signup_screendesign_demo.binder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;


public abstract class RecycleBinder<V extends ViewType>
        implements Binder<V, RecyclerView.ViewHolder> {

    private final V mViewType;
    private Context mContext;

    protected RecycleBinder(final Context context, final V viewType) {
        mContext = context;
        mViewType = viewType;
    }

    @LayoutRes
    public abstract int layoutResId();

    public abstract RecyclerView.ViewHolder onCreateViewHolder(final View v);

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent) {
        return onCreateViewHolder(LayoutInflater.from(mContext).inflate(layoutResId(), parent, false));
    }


    @Override
    public void onRemoved() {
        mContext = null;
    }

    @Override
    public V getViewType() {
        return mViewType;
    }

    public final Context getContext() {
        return mContext;
    }

}