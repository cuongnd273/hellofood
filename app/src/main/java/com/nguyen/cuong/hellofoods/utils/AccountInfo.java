package com.nguyen.cuong.hellofoods.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.nguyen.cuong.hellofoods.models.Account;

/**
 * Created by cuong on 12/2/2017.
 */

public class AccountInfo {
    private SharedPreferences preferences;
    private static String NAME="account";
    public AccountInfo(Context context) {
        this.preferences = context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
    }
    public Account getAccount(){
        Account account=null;
        if(preferences.getInt("id",0)!=0){
            preferences.getInt("id",0);
            account=new Account();
            account.setId(preferences.getInt("id",0));
            account.setName(preferences.getString("name",""));
            account.setPhone(preferences.getString("phone",""));
            account.setAddress(preferences.getString("address",""));
            account.setAvatar(preferences.getString("avatar",""));
            account.setEmail(preferences.getString("email",""));
        }
        return account;
    }
    public void deleteAccount(){
        SharedPreferences.Editor editor=preferences.edit();
        editor.clear();
        editor.commit();
    }
    public void setAccount(Account account){
        SharedPreferences.Editor editor=preferences.edit();
        editor.clear();
        editor.commit();
        editor.putInt("id",account.getId());
        editor.putString("name",account.getName());
        editor.putString("phone",account.getPhone());
        editor.putString("address",account.getAddress());
        editor.putString("avatar",account.getAvatar());
        editor.putString("email",account.getEmail());
        editor.commit();
    }
}
