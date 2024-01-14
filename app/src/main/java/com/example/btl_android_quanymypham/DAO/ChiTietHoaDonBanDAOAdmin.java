package com.example.btl_android_quanymypham.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.btl_android_quanymypham.database.DataBaseHandler;

public class ChiTietHoaDonBanDAOAdmin extends DataBaseHandler {
    public ChiTietHoaDonBanDAOAdmin(@Nullable Context context) {
        super(context);
    }

    public void insertChiTietHoaDonBan(int maHDB, int maMP, int soLuong, long donGia, long tongTien) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maHDB", maHDB);
        values.put("MaMP", maMP);
        values.put("SoLuong", soLuong);
        values.put("DonGia", donGia);
        values.put("TongTien", tongTien);

        db.insert("ChiTietHoaDonBan", null, values);

        db.close();
    }

    public void updateChiTietHoaDonBan(int id, int maHDB, int maMP, int soLuong, long donGia, long tongTien) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaHDB", maHDB);
        values.put("MaMP", maMP);
        values.put("SoLuong", soLuong);
        values.put("DonGia", donGia);
        values.put("TongTien", tongTien);

        db.update("ChiTietHoaDonBan", values, "id=?", new String[]{String.valueOf(id)});

        db.close();
    }

    public void deleteChiTietHoaDonBan(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("ChiTietHoaDonBan", "id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public Cursor getAllChiTietHoaDonNhapByMaHDB(int maHDB) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT ChiTietHoaDonBan.*, ThongTinMyPham.TenMyPham, ThongTinMyPham.AnhSanPham " +
                "FROM ChiTietHoaDonBan " +
                "INNER JOIN ThongTinMyPham ON ChiTietHoaDonBan.MaMP = ThongTinMyPham.id " +
                "WHERE ChiTietHoaDonBan.MaHDB = ?";
        return db.rawQuery(selectQuery, new String[]{String.valueOf(maHDB)});
    }


}
