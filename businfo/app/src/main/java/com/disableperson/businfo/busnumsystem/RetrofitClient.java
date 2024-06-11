package com.disableperson.businfo.busnumsystem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    //수정완
    private static final String BASE_URL = "http://192.168.0.15:8080/";
    public static RetrofitInterface getApiService(){
        return getInstance().create(RetrofitInterface.class);
    }
    public static Retrofit getInstance(){
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
