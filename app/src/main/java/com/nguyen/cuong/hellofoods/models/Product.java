package com.nguyen.cuong.hellofoods.models;

import java.io.Serializable;

/**
 * Created by cuong on 11/20/2017.
 */

public class Product implements Serializable{
    private int id;
    private String name;
    private String image;
    private String description;
    private int price;
    private int sale;

    public Product(String name, String image, String description, int price, int sale) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.sale = sale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
