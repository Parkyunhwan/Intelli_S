package com.login_signup_screendesign_demo.utils;

import android.app.Application;

public class BuildingID extends Application {
    private int buildingID; //0, 1, 2 형남, 문화, 중앙도서관

    @Override
    public void onCreate() {
        //전역 변수 초기화
        super.onCreate();
        buildingID = 0;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public int getData() { return buildingID; }
    public void setData(int data)
    {
        this.buildingID = data;
    }
}
//import android.app.Application;
//
//public class MyApplication extends Application {
//
//    private int state;
//
//    @Override
//    public void onCreate() {
//        //전역 변수 초기화
//        state = 0;
//        super.onCreate();
//    }
//
//    @Override
//    public void onTerminate() {
//        super.onTerminate();
//    }
//
//    public void setState(int state){
//        this.state = state;
//    }
//
//    public int getState(){
//        return state;
//    }
//
//}