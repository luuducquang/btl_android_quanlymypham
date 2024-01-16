package com.example.btl_android_quanymypham.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.btl_android_quanymypham.database.DataBaseHandler;

import java.util.List;

public class GioHangDAOUser extends DataBaseHandler {
    public GioHangDAOUser(@Nullable Context context) {
        super(context);
    }

    public void insertGioHang(int maMP, int nguoiTao, int soLuong) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaMP", maMP);
        values.put("NguoiTao", nguoiTao);
        values.put("SoLuong", soLuong);

        db.insert("GioHang", null, values);

        db.close();
    }

    public void deleteGioHang(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("GioHang", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public Cursor getByNguoiTao(int nguoiTao) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT GioHang.id, ThongTinMyPham.id, GioHang.NguoiTao, GioHang.SoLuong, ThongTinMyPham.GiaBan, ThongTinMyPham.AnhSanPham, ThongTinMyPham.TenMyPham, DangNhap.HoTen " +
                "FROM GioHang " +
                "JOIN ThongTinMyPham ON GioHang.MaMP = ThongTinMyPham.id " +
                "JOIN DangNhap ON GioHang.NguoiTao = DangNhap.id " +
                "WHERE GioHang.NguoiTao = " + nguoiTao;

        return db.rawQuery(query, null);
    }

    public void Tangsoluong(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int currentQuantity = getCurrentQuantity(db, id);

        ContentValues values = new ContentValues();
        values.put("SoLuong", currentQuantity + 1);
        db.update("GioHang", values, "id = ?", new String[]{String.valueOf(id)});

        db.close();
    }
    public void Giamsoluong(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int currentQuantity = getCurrentQuantity(db, id);
        ContentValues values = new ContentValues();
        if (currentQuantity > 1) {
            values.put("SoLuong", currentQuantity - 1);
            db.update("GioHang", values, "id = ?", new String[]{String.valueOf(id)});
        }
        db.close();
    }

    private int getCurrentQuantity(SQLiteDatabase db, int id) {
        String query = "SELECT SoLuong FROM GioHang WHERE id = " + id;
        Cursor cursor = db.rawQuery(query, null);
        int currentQuantity = 0;
        if (cursor != null && cursor.moveToFirst()) {
            currentQuantity = cursor.getInt(0);
            cursor.close();
        }
        return currentQuantity;
    }

    public boolean checkProductExist(String tenSanPham, int nguoiTao) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT COUNT(*) FROM GioHang " +
                    "JOIN ThongTinMyPham ON GioHang.MaMP = ThongTinMyPham.id " +
                    "WHERE ThongTinMyPham.TenMyPham = ? AND GioHang.NguoiTao = ?";
            cursor = db.rawQuery(query, new String[]{tenSanPham, String.valueOf(nguoiTao)});

            if (cursor != null && cursor.moveToFirst()) {
                int count = cursor.getInt(0);
                return count > 0;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return false;
    }

    public void Tangsoluongbymamp(int maSanPham) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE GioHang SET SoLuong = SoLuong + 1 WHERE MaMP = ?";
        db.execSQL(query, new String[]{String.valueOf(maSanPham)});
        db.close();
    }

    public void deleteProducts(List<Integer> ids) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.beginTransaction();

            for (int id : ids) {
                db.delete("GioHang", "id = ?", new String[]{String.valueOf(id)});
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }


}
