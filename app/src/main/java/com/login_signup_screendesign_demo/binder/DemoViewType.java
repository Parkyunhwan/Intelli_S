package com.login_signup_screendesign_demo.binder;


public enum DemoViewType implements ViewType {
  TITLE, LANDSCAPE_ITEM, LANDSCAPE_TILE, LANDSCAPE_DESCRIPTION,PAGE;

  @Override public int viewType() {
    return ordinal();
  }
}
