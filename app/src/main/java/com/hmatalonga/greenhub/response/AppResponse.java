
package com.hmatalonga.greenhub.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class AppResponse implements Serializable {

    @SerializedName("date")
    private String mDate;
    @SerializedName("list")
    private String mList;

    private String apps;
    private String bluetooth;
    private String tourch;
    private String health;
    private String level;
    private String memory;
    private String temperature;

    public String getApps() {
        return apps;
    }

    public void setApps(String apps) {
        this.apps = apps;
    }

    public String getBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(String bluetooth) {
        this.bluetooth = bluetooth;
    }

    public String getTourch() {
        return tourch;
    }

    public void setTourch(String tourch) {
        this.tourch = tourch;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getList() {
        return mList;
    }

    public void setList(String list) {
        mList = list;
    }

}
