package com.nguyen.cuong.hellofoods.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by cuong on 11/26/2017.
 */

public class Restaurant {
    @SerializedName("ID_NhaHang")
    @Expose
    private int id;
    @SerializedName("TenNhaHang")
    @Expose
    private String name;
    @SerializedName("DiaChi")
    @Expose
    private String address;
    @SerializedName("ViTri")
    @Expose
    String location;
    @SerializedName("SoDienThoai")
    @Expose
    String phone;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
