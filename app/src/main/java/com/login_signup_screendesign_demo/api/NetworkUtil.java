package com.login_signup_screendesign_demo.api;

import android.util.Base64;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

import static com.login_signup_screendesign_demo.api.apiURL.BASE_URL;

public class NetworkUtil {


    //private TextView textViewResult;
    public static Retrofit getRetrofit() {

        //   RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.137.1:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // .build().create(LoginServiceInterface.class);
        return retrofit;
    }





public static LoginServiceInterface getRetrofit(String email, String password){
    String credentials = email + ":" + password;
    final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(),Base64.NO_WRAP);
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    httpClient.addInterceptor(new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request original = chain.request();
            Request.Builder builder = original.newBuilder()
                    .addHeader("Authorization", basic)
                    .method(original.method(), original.body());
            return chain.proceed(builder.build());

        }
    });

    RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

    return new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .addCallAdapterFactory(rxAdapter)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(LoginServiceInterface.class);

}


}
