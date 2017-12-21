package com.nguyen.cuong.hellofoods.models;

/**
 * Created by cuong on 11/28/2017.
 */

public class Setting {
    private int id;
    private String name;

    public Setting(int id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
