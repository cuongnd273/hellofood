package com.nguyen.cuong.hellofoods.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nguyen.cuong.hellofoods.models.Cart;

import java.util.ArrayList;

/**
 * Created by cuong on 11/29/2017.
 */

public class CartDao {
    public static String TABLE="cart";
    public static String ID="idproduct";
    public static String NAME="name";
    public static String COUNT="count";
    public static String PRICE="price";
    public static String IMAGE="image";
    private HelloFoodsDatabase database;
    private SQLiteDatabase db;
    public CartDao(Context context) {
        this.database=new HelloFoodsDatabase(context);
    }
    public void open(){
        this.db=this.database.getWritableDatabase();
    }
    public void close(){
        this.db.close();
        this.database.close();
    }
    public ArrayList<Cart> getCarts(){
        open();
        ArrayList<Cart> carts=new ArrayList<>();
        Cursor cursor=this.db.query(TABLE,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            Cart cart=new Cart();
            cart.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            cart.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            cart.setCount(cursor.getInt(cursor.getColumnIndex(COUNT)));
            cart.setPrice(cursor.getInt(cursor.getColumnIndex(PRICE)));
            cart.setImage(cursor.getString(cursor.getColumnIndex(IMAGE)));
            carts.add(cart);
        }
        return carts;
    }
    public void edit(Cart cart){
        open();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COUNT,cart.getCount());
        this.db.update(TABLE,contentValues,ID+"=?",new String[]{String.valueOf(cart.getId())});
    }
    public long addCart(Cart cart){
        open();
        Cursor cursor=this.db.query(TABLE,null,ID+"=?",new String[]{String.valueOf(cart.getId())},null,null,null);
        if(cursor.moveToFirst()){
            ContentValues contentValues=new ContentValues();
            contentValues.put("count",cart.getCount()+cursor.getInt(cursor.getColumnIndex(COUNT)));
            return this.db.update(TABLE,contentValues,ID+"=?",new String[]{String.valueOf(cart.getId())});
        }else{
            ContentValues contentValues=new ContentValues();
            contentValues.put(ID,cart.getId());
            contentValues.put("name",cart.getName());
            contentValues.put("count",cart.getCount());
            contentValues.put("price",cart.getPrice());
            contentValues.put("image",cart.getImage());
            return this.db.insert(TABLE,null,contentValues);
        }
    }
    public void deleteCart(int id){
        open();
        this.db.delete(TABLE,ID+"=?",new String[]{String.valueOf(id)});
    }
    public void deleteAllCarts(){
        open();
        this.db.delete(TABLE,null,null);
    }
}
