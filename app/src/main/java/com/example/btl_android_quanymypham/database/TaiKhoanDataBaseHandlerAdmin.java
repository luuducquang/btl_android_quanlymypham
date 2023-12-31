package com.example.btl_android_quanymypham.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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
    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM DangNhap";
        return db.rawQuery(selectQuery, null);
    }
}
