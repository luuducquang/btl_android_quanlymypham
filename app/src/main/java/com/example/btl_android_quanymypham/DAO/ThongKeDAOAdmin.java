package com.example.btl_android_quanymypham.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.btl_android_quanymypham.database.DataBaseHandler;
import com.example.btl_android_quanymypham.model.HoaDonNhapAdmin;

import java.util.ArrayList;
import java.util.List;

public class ThongKeDAOAdmin extends DataBaseHandler {
    public ThongKeDAOAdmin(@Nullable Context context) {
        super(context);
    }
    public Cursor thongKeHoaDonNhap(String ngayBatDau, String ngayKetThuc) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT HoaDonNhap.id, HoaDonNhap.NguoiTao, DangNhap.HoTen, HoaDonNhap.MaNCC, NhaCungCap.TenNCC, HoaDonNhap.NgayNhap, HoaDonNhap.TongTien " +
                "FROM HoaDonNhap " +
                "INNER JOIN NhaCungCap ON HoaDonNhap.MaNCC = NhaCungCap.id " +
                "INNER JOIN DangNhap ON HoaDonNhap.NguoiTao = DangNhap.id " +
                "WHERE HoaDonNhap.NgayNhap BETWEEN ? AND ?";

        String[] selectionArgs = {ngayBatDau, ngayKetThuc};
        return db.rawQuery(query, selectionArgs);
    }

    public Cursor thongKeHoaDonBan(String ngayBatDau, String ngayKetThuc) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT HoaDonBan.*,DangNhap.HoTen " +
                "FROM HoaDonBan " +
                "INNER JOIN DangNhap ON HoaDonBan.NguoiTao = DangNhap.id " +
                "WHERE HoaDonBan.NgayBan BETWEEN ? AND ?";

        String[] selectionArgs = {ngayBatDau, ngayKetThuc};
        return db.rawQuery(query, selectionArgs);
    }

    public Cursor thongKeSanPhamBanChay(String ngayBatDau, String ngayKetThuc) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT ThongTinMyPham.*, SUM(ChiTietHoaDonBan.SoLuong) AS TongSoLuongBan " +
                "FROM ThongTinMyPham " +
                "INNER JOIN ChiTietHoaDonBan ON ThongTinMyPham.id = ChiTietHoaDonBan.MaMP " +
                "INNER JOIN HoaDonBan ON ChiTietHoaDonBan.MaHDB = HoaDonBan.id " +
                "WHERE HoaDonBan.NgayBan BETWEEN ? AND ? " +
                "GROUP BY ThongTinMyPham.id " +
                "HAVING TongSoLuongBan > 10 " +
                "ORDER BY TongSoLuongBan DESC";

        String[] selectionArgs = {ngayBatDau, ngayKetThuc};
        return db.rawQuery(query, selectionArgs);
    }

    public Cursor thongKeSanPhamBanCham(String ngayBatDau, String ngayKetThuc) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT ThongTinMyPham.*, SUM(ChiTietHoaDonBan.SoLuong) AS TongSoLuongBan " +
                "FROM ThongTinMyPham " +
                "INNER JOIN ChiTietHoaDonBan ON ThongTinMyPham.id = ChiTietHoaDonBan.MaMP " +
                "INNER JOIN HoaDonBan ON ChiTietHoaDonBan.MaHDB = HoaDonBan.id " +
                "WHERE HoaDonBan.NgayBan BETWEEN ? AND ? " +
                "GROUP BY ThongTinMyPham.id " +
                "HAVING TongSoLuongBan <= 10 " +
                "ORDER BY TongSoLuongBan DESC";

        String[] selectionArgs = {ngayBatDau, ngayKetThuc};
        return db.rawQuery(query, selectionArgs);
    }

    public Cursor thongKeSanPhamSapHet() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * " +
                "FROM ThongTinMyPham " +
                "WHERE ThongTinMyPham.SoLuong <= 5";

        return db.rawQuery(query, null);
    }


}
