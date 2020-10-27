package com.login_signup_screendesign_demo.api;

import com.login_signup_screendesign_demo.models.Post;

import org.w3c.dom.Comment;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface JsonPlaceHolderApi {
    @GET("posts")//url 다음에 나오는 부분
    Call<List<Post>> getPosts(
            @Query("userId") Integer userId,//integer는 null가능
            @Query("userId") Integer userId2,
            @Query("_sort")String sort,
            @Query("_order")String order
    );

    @GET("posts")//url 다음에 나오는 부분
    Call<List<Post>> getPosts(@QueryMap Map<String,String>parameters);
    //parameters: name of the parameter and value
    @GET("post")
    Call<Post> getoneIdPost(@QueryMap Map<String,String>parameters);

    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int postId);

    @GET
    Call<List<Comment>>getComments(@Url String url);

    @POST("posts")
    Call<Post> createPost(@Body Post post);

    @FormUrlEncoded
    @POST("posts")
    Call<Post>createpost(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String text
    );

}


