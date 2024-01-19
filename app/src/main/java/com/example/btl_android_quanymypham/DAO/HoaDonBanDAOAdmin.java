package com.example.btl_android_quanymypham.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.btl_android_quanymypham.database.DataBaseHandler;
import com.example.btl_android_quanymypham.model.GioHangUser;

import java.util.List;

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

    public void InsertListData(Integer NguoiTao, String TenKH, String DiaChi, String Sdt, String NgayBan, Long TongTien, List<GioHangUser> gioHangUserList) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.beginTransaction();

            ContentValues HoaDonBanValues = new ContentValues();
            HoaDonBanValues.put("NguoiTao", NguoiTao);
            HoaDonBanValues.put("TenKH", TenKH);
            HoaDonBanValues.put("DiaChi", DiaChi);
            HoaDonBanValues.put("Sdt", Sdt);
            HoaDonBanValues.put("NgayBan", NgayBan);
            HoaDonBanValues.put("TongTien", TongTien);

            long hoaDonBanId = db.insert("HoaDonBan", null, HoaDonBanValues);

            if (hoaDonBanId != -1) {
                for (GioHangUser gioHangUser : gioHangUserList) {
                    ContentValues chiTietValues = new ContentValues();
                    chiTietValues.put("MaHDB", hoaDonBanId);
                    chiTietValues.put("MaMP", gioHangUser.getMamp());
                    chiTietValues.put("SoLuong", gioHangUser.getSoluong());
                    chiTietValues.put("DonGia", gioHangUser.getGia());
                    chiTietValues.put("TongTien", gioHangUser.getSoluong()* gioHangUser.getGia());

                    db.insert("ChiTietHoaDonBan", null, chiTietValues);

                    updateSoLuongThongTinMyPham(db,gioHangUserList);
                }

                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public void updateSoLuongThongTinMyPham(SQLiteDatabase db,List<GioHangUser> gioHangUserList) {

        try {
            for (GioHangUser gioHangUser : gioHangUserList) {
                int maMP = gioHangUser.getMamp();
                int soLuongMoi = gioHangUser.getSoluong();

                String updateQuery = "UPDATE ThongTinMyPham SET SoLuong = SoLuong - " + soLuongMoi + " WHERE id = " + maMP;
                db.execSQL(updateQuery);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Cursor getAllHoaDonBan() {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT HoaDonBan.*, DangNhap.HoTen " +
                "FROM HoaDonBan " +
                "INNER JOIN DangNhap ON HoaDonBan.NguoiTao = DangNhap.id";

        return db.rawQuery(selectQuery, null);
    }

    public Cursor getHoaDonByNguoiTao(int nguoiTaoId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT HoaDonBan.*, DangNhap.HoTen " +
                "FROM HoaDonBan " +
                "INNER JOIN DangNhap ON HoaDonBan.NguoiTao = DangNhap.id " +
                "WHERE HoaDonBan.NguoiTao = ?";

        String[] selectionArgs = {String.valueOf(nguoiTaoId)};
        return db.rawQuery(selectQuery, selectionArgs);
    }


    public void deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};
        db.delete("HoaDonBan", whereClause, whereArgs);
        db.close();
    }

    public void updateDownSoluongThongTinMyPham(int maMP, int soLuongMoi) {
        SQLiteDatabase db = this.getWritableDatabase();
        String updateQuery = "UPDATE ThongTinMyPham SET SoLuong = SoLuong - " + soLuongMoi + " WHERE id = " + maMP;
        db.execSQL(updateQuery);
    }

}
