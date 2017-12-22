package com.nguyen.cuong.hellofoods.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by cuong on 11/26/2017.
 */

public class InfoBill {
    private int idBill;
    @SerializedName("TenMonAn")
    @Expose
    private String name;
    @SerializedName("SrcImg")
    @Expose
    private String image;
    @SerializedName("SoLuong")
    @Expose
    private int count;
    @SerializedName("Gia")
    @Expose
    private int price;

    public InfoBill(String name, String image, int count, int price) {
        this.name = name;
        this.image = image;
        this.count = count;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
