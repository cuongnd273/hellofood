package com.nguyen.cuong.hellofoods.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cuong on 12/22/2017.
 */

public class Order {
    @SerializedName("idStore")
    @Expose
    private int idStore;
    @SerializedName("idUser")
    @Expose
    private int idUser;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("total")
    @Expose
    private int total;
    @SerializedName("products")
    @Expose
    private List<InfoOrder> products;

    public Order(int idStore, int idUser, String address, List<InfoOrder> products, int total) {
        this.idStore = idStore;
        this.idUser = idUser;
        this.address = address;
        this.products = products;
        this.total = total;
    }
}
