package com.login_signup_screendesign_demo.api;

import com.login_signup_screendesign_demo.models.Response;
import com.login_signup_screendesign_demo.models.User;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;


public interface LoginServiceInterface {
    @POST("register")
    Observable<Response> register(
           @Body User user);

    @POST("login")
    Observable<Response> login();

    @POST("users/{email}/password")
    Observable<Response> resetPasswordInit(@Path("email") String email);

}
