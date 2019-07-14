package com.example.node;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseUtil {


    public static SQLiteDatabase returnDataBase(Context context){
        String path="nodepad.db";

        SQLiteOpenHelper helper=new SQLiteOpenHelper(context,path,null,1) {
            @Override
            public void onCreate(SQLiteDatabase db) {

                //创建
                String sql = "create table nodepad(_id integer primary key autoincrement," + "title varchar(50),"+"content varchar(500),"+"date varchar(50),"+"type integer)";
                db.execSQL(sql);
            }
            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                //升级
            }
        };
        return helper.getReadableDatabase();

    }
}
