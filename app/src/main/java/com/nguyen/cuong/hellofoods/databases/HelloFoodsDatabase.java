package com.nguyen.cuong.hellofoods.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by cuong on 11/29/2017.
 */

public class HelloFoodsDatabase extends SQLiteOpenHelper {
    public static String DATABASE="hellofoods";
    public static int VERSION=1;
    public static String CREATE_CART="create table "+CartDao.TABLE+"( "+CartDao.ID+" integer primary key,"+CartDao.NAME+" text,"+CartDao.COUNT+" interger,"+CartDao.PRICE+" interger,"+CartDao.IMAGE+" text)";
    public HelloFoodsDatabase(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CART);
        Log.i("AAA",CREATE_CART);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST "+CartDao.TABLE);
        onCreate(db);
    }
}
