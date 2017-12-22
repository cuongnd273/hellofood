package com.nguyen.cuong.hellofoods.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by cuong on 11/30/2017.
 */

public class Bill {
    @SerializedName("ID_DonHang")
    @Expose
    private int id;
    @SerializedName("ThoiGian")
    @Expose
    private String time;
    @SerializedName("TrangThai")
    @Expose
    private int status;
    @SerializedName("TongTien")
    @Expose
    private int totalMoney;

    public Bill(String time, int status, int totalMoney) {
        this.time = time;
        this.status = status;
        this.totalMoney = totalMoney;
    }

    public String getTime() {
        return time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }
}
