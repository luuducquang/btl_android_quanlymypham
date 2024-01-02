package com.example.btl_android_quanymypham.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class NhaCungCapDataBaseHandlerAdmin extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "btl_quanlymypham21.db";
    private static final int DATABASE_VERSION = 2;
    public NhaCungCapDataBaseHandlerAdmin(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS NhaCungCap (id INTEGER PRIMARY KEY AUTOINCREMENT, TenNCC TEXT, DiaChiNCC TEXT, SdtNCC TEXT);";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertData(String TenNCC, String DiaChiNCC, String SdtNCC) {
        SQLiteDatabase db = this.getWritableDatabase();
        String insertQuery = "INSERT INTO NhaCungCap (TenNCC, DiaChiNCC,SdtNCC) VALUES ('" + TenNCC  + "', '" + DiaChiNCC + "','" + SdtNCC + "');";
        db.execSQL(insertQuery);
        db.close();
    }

    public void updateData(int id, String TenNCC, String DiaChiNCC, String SdtNCC) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("TenNCC", TenNCC);
        values.put("DiaChiNCC", DiaChiNCC);
        values.put("SdtNCC", SdtNCC);

        String[] args = {String.valueOf(id)};
        db.update("NhaCungCap", values, "ID=?", args);

        db.close();
    }

    public void deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String[] args = {String.valueOf(id)};
        db.delete("NhaCungCap", "ID=?", args);

        db.close();
    }



    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM NhaCungCap";
        return db.rawQuery(selectQuery, null);
    }
}
