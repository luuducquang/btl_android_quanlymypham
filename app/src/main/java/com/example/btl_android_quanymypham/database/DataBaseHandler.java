package com.example.btl_android_quanymypham.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.btl_android_quanymypham.model.TaiKhoanAdmin;

public class DataBaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "btl_quanlymypham_luuducquang7.db";
    private static final int DATABASE_VERSION = 1;
    public DataBaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQueryDangNhap = "CREATE TABLE IF NOT EXISTS DangNhap (id INTEGER PRIMARY KEY AUTOINCREMENT, TaiKhoan TEXT, MatKhau TEXT, HoTen TEXT, Email TEXT,AnhDaiDien BLOB,Quyen TEXT);";
        db.execSQL(createTableQueryDangNhap);

        String createTableQueryNhaCungCap = "CREATE TABLE IF NOT EXISTS NhaCungCap (id INTEGER PRIMARY KEY AUTOINCREMENT, TenNCC TEXT, DiaChiNCC TEXT, SdtNCC TEXT);";
        db.execSQL(createTableQueryNhaCungCap);

        String createTableQueryLoaiMyPham = "CREATE TABLE IF NOT EXISTS LoaiMyPham (id INTEGER PRIMARY KEY AUTOINCREMENT, TenLoai TEXT, MoTa TEXT);";
        db.execSQL(createTableQueryLoaiMyPham);

        String createTableQueryThongTinMyPham = "CREATE TABLE IF NOT EXISTS ThongTinMyPham " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TenMyPham TEXT, " +
                "DungTich TEXT, " +
                "LoaiMyPham INTEGER, " +
                "AnhSanPham BLOB, " +
                "GiaBan LONG, " +
                "MoTa TEXT, " +
                "ChiTiet TEXT, " +
                "FOREIGN KEY (LoaiMyPham) REFERENCES LoaiMyPham(id));";
        db.execSQL(createTableQueryThongTinMyPham);

        String createTableQueryHoaDonBan = "CREATE TABLE IF NOT EXISTS HoaDonBan " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NguoiTao INTEGER, " +
                "TenKH TEXT, " +
                "DiaChi TEXT, " +
                "Sdt TEXT, " +
                "NgayBan TEXT, " +
                "TongTien LONG, " +
                "FOREIGN KEY (NguoiTao) REFERENCES DangNhap(id));";
        db.execSQL(createTableQueryHoaDonBan);

        String createTableQueryChiTietHoaDonBan = "CREATE TABLE IF NOT EXISTS ChiTietHoaDonBan " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "MaHDB INTEGER, " +
                "MaMP INTEGER, " +
                "SoLuong INTEGER, " +
                "DonGia LONG, " +
                "TongTien LONG, " +
                "FOREIGN KEY (MaHDB) REFERENCES HoaDonBan(id), " +
                "FOREIGN KEY (MaMP) REFERENCES ThongTinMyPham(id));";
        db.execSQL(createTableQueryChiTietHoaDonBan);

        String createTableQueryHoaDonNhap = "CREATE TABLE IF NOT EXISTS HoaDonNhap " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NguoiTao INTEGER, " +
                "MaNCC INTEGER, " +
                "NgayNhap TEXT, " +
                "TongTien LONG, " +
                "FOREIGN KEY (NguoiTao) REFERENCES DangNhap(id), " +
                "FOREIGN KEY (MaNCC) REFERENCES NhaCungCap(id));";
        db.execSQL(createTableQueryHoaDonNhap);

        String createTableQueryChiTietHoaDonNhap = "CREATE TABLE IF NOT EXISTS ChiTietHoaDonNhap " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "MaHDN INTEGER, " +
                "MaMP INTEGER, " +
                "SoLuong INTEGER, " +
                "DonGia LONG, " +
                "TongTien LONG, " +
                "FOREIGN KEY (MaHDN) REFERENCES HoaDonNhap(id), " +
                "FOREIGN KEY (MaMP) REFERENCES ThongTinMyPham(id));";
        db.execSQL(createTableQueryChiTietHoaDonNhap);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
