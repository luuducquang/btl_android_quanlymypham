package com.example.btl_android_quanymypham.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class LoaiMyPhamDataBaseHandlerAdmin extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "btl_quanlymypham.db";
    private static final int DATABASE_VERSION = 1;

    public LoaiMyPhamDataBaseHandlerAdmin(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS LoaiMyPham (id INTEGER PRIMARY KEY AUTOINCREMENT, TenLoai TEXT, MoTa TEXT);";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertData(String TenLoai, String MoTa) {
        SQLiteDatabase db = this.getWritableDatabase();
        String insertQuery = "INSERT INTO LoaiMyPham (TenLoai, MoTa) VALUES ('" + TenLoai  + "', '" + MoTa + "');";
        db.execSQL(insertQuery);
        db.close();
    }

    public void updateData(int id,String TenLoai, String MoTa ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenLoai", TenLoai);
        values.put("MoTa", MoTa);
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};
        db.update("LoaiMyPham", values, whereClause, whereArgs);
        db.close();
    }

    public void deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};
        db.delete("LoaiMyPham", whereClause, whereArgs);
        db.close();
    }


    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM LoaiMyPham";
        return db.rawQuery(selectQuery, null);
    }
}
