package com.example.btl_android_quanymypham.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.btl_android_quanymypham.database.DataBaseHandler;

public class ChiTietHoaDonNhapDAOAdmin extends DataBaseHandler {
    public ChiTietHoaDonNhapDAOAdmin(@Nullable Context context) {
        super(context);
    }

    public void insertChiTietHoaDonNhap(int maHDN, int maMP, int soLuong, long donGia, long tongTien) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaHDN", maHDN);
        values.put("MaMP", maMP);
        values.put("SoLuong", soLuong);
        values.put("DonGia", donGia);
        values.put("TongTien", tongTien);

        db.insert("ChiTietHoaDonNhap", null, values);

        db.close();
    }

    public void updateChiTietHoaDonNhap(int id, int maHDN, int maMP, int soLuong, long donGia, long tongTien) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaHDN", maHDN);
        values.put("MaMP", maMP);
        values.put("SoLuong", soLuong);
        values.put("DonGia", donGia);
        values.put("TongTien", tongTien);

        db.update("ChiTietHoaDonNhap", values, "id=?", new String[]{String.valueOf(id)});

        db.close();
    }

    public void deleteChiTietHoaDonNhap(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("ChiTietHoaDonNhap", "id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public Cursor getAllChiTietHoaDonNhapByMaHDN(int maHDN) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT ChiTietHoaDonNhap.*, ThongTinMyPham.TenMyPham, ThongTinMyPham.AnhSanPham " +
                "FROM ChiTietHoaDonNhap " +
                "INNER JOIN ThongTinMyPham ON ChiTietHoaDonNhap.MaMP = ThongTinMyPham.id " +
                "WHERE ChiTietHoaDonNhap.MaHDN = ?";
        return db.rawQuery(selectQuery, new String[]{String.valueOf(maHDN)});
    }


}
