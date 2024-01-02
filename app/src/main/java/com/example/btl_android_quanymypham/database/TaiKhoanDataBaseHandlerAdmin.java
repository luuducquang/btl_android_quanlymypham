package com.example.btl_android_quanymypham.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.btl_android_quanymypham.model.TaiKhoanAdmin;

public class TaiKhoanDataBaseHandlerAdmin extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "btl_quanlymypham1.db";
    private static final int DATABASE_VERSION = 9;
    public TaiKhoanDataBaseHandlerAdmin(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS DangNhap (id INTEGER PRIMARY KEY AUTOINCREMENT, TaiKhoan TEXT, MatKhau TEXT, HoTen TEXT, Email TEXT,AnhDaiDien BLOB,Quyen TEXT);";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS DangNhap");
        onCreate(db);
    }

    public void insertData(String TaiKhoan, String MatKhau, String HoTen, String Email, byte[] AnhDaiDien, String Quyen ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TaiKhoan", TaiKhoan);
        values.put("MatKhau", MatKhau);
        values.put("HoTen", HoTen);
        values.put("Email", Email);
        values.put("AnhDaiDien", AnhDaiDien);
        values.put("Quyen", Quyen);

        db.insert("DangNhap", null, values);

        db.close();
    }

    public void updateData(int id, String TaiKhoan, String MatKhau, String HoTen, String Email, byte[] AnhDaiDien, String Quyen) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TaiKhoan", TaiKhoan);
        values.put("MatKhau", MatKhau);
        values.put("HoTen", HoTen);
        values.put("Email", Email);
        values.put("AnhDaiDien", AnhDaiDien);
        values.put("Quyen", Quyen);

        db.update("DangNhap", values, "ID=?", new String[]{String.valueOf(id)});

        db.close();
    }

    public void deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("DangNhap", "ID=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM DangNhap";
        return db.rawQuery(selectQuery, null);
    }

    public boolean checkLogin(String taiKhoan, String matKhau) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM DangNhap WHERE TaiKhoan=? AND MatKhau=?";
        String[] selectionArgs = {taiKhoan, matKhau};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count > 0;
    }

    public TaiKhoanAdmin getAccountInfo(String taiKhoan, String matKhau) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM DangNhap WHERE TaiKhoan=? AND MatKhau=?";
        String[] selectionArgs = {taiKhoan, matKhau};
        Cursor cursor = db.rawQuery(query, selectionArgs);

        TaiKhoanAdmin taiKhoanAdmin = null;

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String hoTen = cursor.getString(3);
            String email = cursor.getString(4);
            byte[] anhDaiDien = cursor.getBlob(5);
            String quyen = cursor.getString(6);

            taiKhoanAdmin = new TaiKhoanAdmin(id, taiKhoan, matKhau, hoTen, email, anhDaiDien, quyen);
        }

        cursor.close();
        return taiKhoanAdmin;
    }
}
