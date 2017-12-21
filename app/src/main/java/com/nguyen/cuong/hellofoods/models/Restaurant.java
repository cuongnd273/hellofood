package com.nguyen.cuong.hellofoods.models;

/**
 * Created by cuong on 11/26/2017.
 */

public class Restaurant {
    private String name;
    private String address;

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

    public Restaurant(String name, String address) {

        this.name = name;
        this.address = address;
    }
}
