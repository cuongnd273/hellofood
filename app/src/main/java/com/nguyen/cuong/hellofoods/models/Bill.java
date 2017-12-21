package com.nguyen.cuong.hellofoods.models;

/**
 * Created by cuong on 11/30/2017.
 */

public class Bill {
    private String time;
    private int status;
    private int totalMoney;

    public Bill(String time, int status, int totalMoney) {
        this.time = time;
        this.status = status;
        this.totalMoney = totalMoney;
    }

    public String getTime() {
        return time;
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
