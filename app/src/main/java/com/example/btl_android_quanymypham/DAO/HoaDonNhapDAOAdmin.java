package com.example.btl_android_quanymypham.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.btl_android_quanymypham.database.DataBaseHandler;

public class HoaDonNhapDAOAdmin extends DataBaseHandler {
    public HoaDonNhapDAOAdmin(@Nullable Context context) {
        super(context);
    }

    public void InsertData(Integer NguoiTao,Integer TenNCC, String NgayNhap, Long TongTien, Integer MaMP, Integer SoLuong, Long DonGia, Long TongGia) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.beginTransaction();

            ContentValues hoaDonNhapValues = new ContentValues();
            hoaDonNhapValues.put("NguoiTao",NguoiTao);
            hoaDonNhapValues.put("MaNCC", TenNCC);
            hoaDonNhapValues.put("NgayNhap", NgayNhap);
            hoaDonNhapValues.put("TongTien", TongTien);

            long hoaDonNhapId = db.insert("HoaDonNhap", null, hoaDonNhapValues);

            if (hoaDonNhapId != -1) {
                ContentValues chiTietValues = new ContentValues();
                chiTietValues.put("MaHDN", hoaDonNhapId);
                chiTietValues.put("MaMP", MaMP);
                chiTietValues.put("SoLuong", SoLuong);
                chiTietValues.put("DonGia", DonGia);
                chiTietValues.put("TongTien", TongGia);

                db.insert("ChiTietHoaDonNhap", null, chiTietValues);

                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public Cursor getAllHoaDonNhap() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT HoaDonNhap.id, HoaDonNhap.NguoiTao, DangNhap.HoTen, HoaDonNhap.MaNCC,NhaCungCap.TenNCC, HoaDonNhap.NgayNhap, HoaDonNhap.TongTien " +
                    "FROM HoaDonNhap " +
                    "INNER JOIN NhaCungCap ON HoaDonNhap.MaNCC = NhaCungCap.id " +
                    "INNER JOIN DangNhap ON HoaDonNhap.NguoiTao = DangNhap.id";

            cursor = db.rawQuery(query, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cursor;
    }

    public void deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};
        db.delete("HoaDonNhap", whereClause, whereArgs);
        db.close();
    }

}
