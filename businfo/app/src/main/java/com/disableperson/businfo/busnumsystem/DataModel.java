package com.disableperson.businfo.busnumsystem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataModel {

    @SerializedName("alarm")
    @Expose
    private String alarm;
    @SerializedName("busStopName")
    @Expose
    private String busStopName;


    public String getAlarm(){
        return alarm;
    }
    public String getBusStopName(){
        return busStopName;
    }

}
