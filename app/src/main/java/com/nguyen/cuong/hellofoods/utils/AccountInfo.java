package com.nguyen.cuong.hellofoods.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.nguyen.cuong.hellofoods.models.User;

/**
 * Created by cuong on 12/2/2017.
 */

public class AccountInfo {
    private SharedPreferences preferences;
    private static String NAME="account";
    public AccountInfo(Context context) {
        this.preferences = context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
    }
    public User getAccount(){
        User user=null;
        if(preferences.getInt("id",0)!=0){
            preferences.getInt("id",0);
            user=new User();
            user.setIDTaiKhoan(preferences.getInt("id",0));
            user.setTenTaiKhoan(preferences.getString("name",""));
            user.setSoDienThoai(preferences.getString("phone",""));
            user.setEmail(preferences.getString("email",""));
        }
        return user;
    }
    public void deleteAccount(){
        SharedPreferences.Editor editor=preferences.edit();
        editor.clear();
        editor.commit();
    }
    public void setAccount(User user){
        SharedPreferences.Editor editor=preferences.edit();
        editor.clear();
        editor.commit();
        editor.putInt("id",user.getIDTaiKhoan());
        editor.putString("name",user.getTenTaiKhoan());
        editor.putString("phone",user.getSoDienThoai());
        editor.putString("email",user.getEmail());
        editor.commit();
    }
}
