package com.example.btl_android_quanymypham.DAO;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.btl_android_quanymypham.database.DataBaseHandler;

public class NhaCungCapDAOAdmin extends DataBaseHandler {
    public NhaCungCapDAOAdmin(@Nullable Context context) {
        super(context);
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
