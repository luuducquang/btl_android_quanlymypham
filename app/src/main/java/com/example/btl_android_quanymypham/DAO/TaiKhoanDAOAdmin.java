package com.example.btl_android_quanymypham.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.btl_android_quanymypham.database.DataBaseHandler;
import com.example.btl_android_quanymypham.model.TaiKhoanAdmin;

public class TaiKhoanDAOAdmin extends DataBaseHandler {

    public TaiKhoanDAOAdmin(@Nullable Context context) {
        super(context);
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

    public boolean isUsernameExist(String taiKhoan) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM DangNhap WHERE TaiKhoan=?";
        String[] selectionArgs = {taiKhoan};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count > 0;
    }

    public void changePassword(int accountId, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MatKhau", newPassword);

        db.update("DangNhap", values, "ID=?", new String[]{String.valueOf(accountId)});

        db.close();
    }

    public boolean isLoginValid(int accountId, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT COUNT(*) FROM DangNhap WHERE ID=? AND MatKhau=?";
        String[] selectionArgs = {String.valueOf(accountId), password};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        boolean isValid = false;

        if (cursor != null) {
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            isValid = count > 0;
            cursor.close();
        }

        return isValid;
    }



}
