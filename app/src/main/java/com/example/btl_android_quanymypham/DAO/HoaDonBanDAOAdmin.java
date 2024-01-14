package com.example.btl_android_quanymypham.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.btl_android_quanymypham.database.DataBaseHandler;

public class HoaDonBanDAOAdmin extends DataBaseHandler {
    public HoaDonBanDAOAdmin(@Nullable Context context) {
        super(context);
    }

    public void InsertData(Integer NguoiTao,String TenKH,String DiaChi,String Sdt, String NgayBan, Long TongTien, Integer MaMP, Integer SoLuong, Long DonGia, Long TongGia) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.beginTransaction();

            ContentValues HoaDonBanValues = new ContentValues();
            HoaDonBanValues.put("NguoiTao",NguoiTao);
            HoaDonBanValues.put("TenKH", TenKH);
            HoaDonBanValues.put("DiaChi", DiaChi);
            HoaDonBanValues.put("Sdt", Sdt);
            HoaDonBanValues.put("NgayBan", NgayBan);
            HoaDonBanValues.put("TongTien", TongTien);

            long hoaDonBanId = db.insert("HoaDonBan", null, HoaDonBanValues);

            if (hoaDonBanId != -1) {
                ContentValues chiTietValues = new ContentValues();
                chiTietValues.put("MaHDB", hoaDonBanId);
                chiTietValues.put("MaMP", MaMP);
                chiTietValues.put("SoLuong", SoLuong);
                chiTietValues.put("DonGia", DonGia);
                chiTietValues.put("TongTien", TongGia);

                db.insert("ChiTietHoaDonBan", null, chiTietValues);

                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public Cursor getAllHoaDonBan() {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT HoaDonBan.*, DangNhap.HoTen " +
                "FROM HoaDonBan " +
                "INNER JOIN DangNhap ON HoaDonBan.NguoiTao = DangNhap.id";

        return db.rawQuery(selectQuery, null);
    }

    public void deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};
        db.delete("HoaDonBan", whereClause, whereArgs);
        db.close();
    }

}
