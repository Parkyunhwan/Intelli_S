package com.login_signup_screendesign_demo.api;

import com.login_signup_screendesign_demo.models.IntellisPost;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IntelliSApi {

   @GET("app/notice/list/{bno}")
    Call<List<IntellisPost>> getPosts(
            @Path("bno") int postbno
    );

    @GET("app/notice/get/{id}")
    Call<IntellisPost> getoneidPost(
            @Path("id") String id
    );
}
