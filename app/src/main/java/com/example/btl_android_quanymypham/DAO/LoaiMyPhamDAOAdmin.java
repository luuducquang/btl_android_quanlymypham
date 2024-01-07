package com.example.btl_android_quanymypham.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.btl_android_quanymypham.database.DataBaseHandler;

public class LoaiMyPhamDAOAdmin extends DataBaseHandler  {
    public LoaiMyPhamDAOAdmin(@Nullable Context context) {
        super(context);
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
