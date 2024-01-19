package com.example.btl_android_quanymypham.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.btl_android_quanymypham.database.DataBaseHandler;

public class TTMyPhamDAOAdmin extends DataBaseHandler {
    public TTMyPhamDAOAdmin(@Nullable Context context) {
        super(context);
    }

    public void insertThongTinMyPham(String TenMyPham, String DungTich, int LoaiMyPham, byte[] AnhSanPham,Long GiaBan, String MoTa, String ChiTiet) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenMyPham", TenMyPham);
        values.put("DungTich", DungTich);
        values.put("LoaiMyPham", LoaiMyPham);
        values.put("AnhSanPham", AnhSanPham);
        values.put("GiaBan", GiaBan);
        values.put("MoTa", MoTa);
        values.put("ChiTiet", ChiTiet);

        db.insert("ThongTinMyPham", null, values);

        db.close();
    }

    public void updateThongTinMyPham(int id, String TenMyPham, String DungTich, int LoaiMyPham, byte[] AnhSanPham,Long GiaBan, String MoTa, String ChiTiet) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenMyPham", TenMyPham);
        values.put("DungTich", DungTich);
        values.put("LoaiMyPham", LoaiMyPham);
        values.put("AnhSanPham", AnhSanPham);
        values.put("GiaBan", GiaBan);
        values.put("MoTa", MoTa);
        values.put("ChiTiet", ChiTiet);

        db.update("ThongTinMyPham", values, "id=?", new String[]{String.valueOf(id)});

        db.close();
    }

    public void deleteThongTinMyPham(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("ThongTinMyPham", "id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public Cursor getAllThongTinMyPham() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM ThongTinMyPham";
        return db.rawQuery(selectQuery, null);
    }

    public Cursor getTenLoaiThongTinMyPham() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT ThongTinMyPham.*, LoaiMyPham.TenLoai " +
                "FROM ThongTinMyPham " +
                "JOIN LoaiMyPham ON ThongTinMyPham.LoaiMyPham = LoaiMyPham.id";

        return db.rawQuery(selectQuery, null);
    }

    public boolean kiemTraSoLuong(int maMP, int soLuongCanKiemTra) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT SoLuong FROM ThongTinMyPham WHERE id = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(maMP)});

            if (cursor.moveToFirst()) {
                int soLuongTrongKho = cursor.getInt(0);

                return soLuongTrongKho >= soLuongCanKiemTra;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return false;
    }


}
