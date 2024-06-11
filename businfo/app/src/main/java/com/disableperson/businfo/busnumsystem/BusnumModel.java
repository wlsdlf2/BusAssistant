package com.disableperson.businfo.busnumsystem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusnumModel
{
    //수정 완
    @SerializedName("busNumber") //body에 써있는 키값(700-2앞에 써있는 이름)을 일치시켜야함
    @Expose
    private String busNum;
    public String getBusNum(){
        return busNum;
    }
//    @SerializedName("userId")
//    @Expose
//    private String userId;
//    public String getUserId() {
//        return userId;
//    }
}
