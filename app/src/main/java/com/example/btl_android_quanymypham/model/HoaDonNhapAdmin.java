package com.example.btl_android_quanymypham.model;

import java.io.Serializable;

public class HoaDonNhapAdmin implements Serializable {
    private int id;
    private int NguoiTao;
    private String HoTen;
    private int MaNCC;
    private String TenNCC;
    private String NgayNhap;
    private Long TongTien;

    public HoaDonNhapAdmin(int id, int nguoiTao, String hoTen, int maNCC, String tenNCC, String ngayNhap, Long tongTien) {
        this.id = id;
        NguoiTao = nguoiTao;
        HoTen = hoTen;
        MaNCC = maNCC;
        TenNCC = tenNCC;
        NgayNhap = ngayNhap;
        TongTien = tongTien;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNguoiTao() {
        return NguoiTao;
    }

    public void setNguoiTao(int nguoiTao) {
        NguoiTao = nguoiTao;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public int getMaNCC() {
        return MaNCC;
    }

    public void setMaNCC(int maNCC) {
        MaNCC = maNCC;
    }

    public String getTenNCC() {
        return TenNCC;
    }

    public void setTenNCC(String tenNCC) {
        TenNCC = tenNCC;
    }

    public String getNgayNhap() {
        return NgayNhap;
    }

    public void setNgayNhap(String ngayNhap) {
        NgayNhap = ngayNhap;
    }

    public Long getTongTien() {
        return TongTien;
    }

    public void setTongTien(Long tongTien) {
        TongTien = tongTien;
    }
}
