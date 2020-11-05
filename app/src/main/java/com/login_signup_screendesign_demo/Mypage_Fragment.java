package com.login_signup_screendesign_demo;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 *
 * create an instance of this fragment.
 */
public class Mypage_Fragment extends Fragment {


    public Mypage_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     */



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        TextView textView;
        TextView textView2;
        TextView textView3;

        View view = inflater.inflate(R.layout.fragment_mypage_, container, false);
            return view;
    }
}