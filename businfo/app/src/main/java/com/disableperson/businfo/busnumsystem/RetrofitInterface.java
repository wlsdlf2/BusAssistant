package com.disableperson.businfo.busnumsystem;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitInterface {
    @FormUrlEncoded
    @POST("bus/login")
    Call<BusnumModel> api_post(
            @FieldMap HashMap<String, Object> param);

    //수정 posts 대신 만들어진 서버 경로 입력 완
    @GET("bus/{BusNum}")
    Call<DataModel> api_get(
            @Path("BusNum") String busnumber) ;
//    @GET("posts/")
//    Call<DataModel> api_get2() ;

    }

