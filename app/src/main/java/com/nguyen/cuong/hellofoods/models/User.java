package com.nguyen.cuong.hellofoods.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by cuong on 12/22/2017.
 */

public class User {
    @SerializedName("$id")
    @Expose
    private String $id;
    @SerializedName("ID_TaiKhoan")
    @Expose
    private Integer iDTaiKhoan;
    @SerializedName("TenTaiKhoan")
    @Expose
    private String tenTaiKhoan;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("SoDienThoai")
    @Expose
    private String soDienThoai;
    @SerializedName("TienNap")
    @Expose
    private String tienNap;
    @SerializedName("MatKhau")
    @Expose
    private String matKhau;
    @SerializedName("Online")
    @Expose
    private Object online;

    public User() {
    }

    public User(String email, String matKhau) {
        this.email = email;
        this.matKhau = matKhau;
    }

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public Integer getIDTaiKhoan() {
        return iDTaiKhoan;
    }

    public void setIDTaiKhoan(Integer iDTaiKhoan) {
        this.iDTaiKhoan = iDTaiKhoan;
    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getTienNap() {
        return tienNap;
    }

    public void setTienNap(String tienNap) {
        this.tienNap = tienNap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public Object getOnline() {
        return online;
    }

    public void setOnline(Object online) {
        this.online = online;
    }
}
