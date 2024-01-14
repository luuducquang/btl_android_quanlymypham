package com.example.btl_android_quanymypham.model;

import java.io.Serializable;

public class HoaDonBanAdmin implements Serializable {
    private int id;
    private int NguoiTao;
    private String TenKH;
    private String DiaChi;
    private String Sdt;
    private String NgayBan;
    private Long TongTien;
    private String HoTen;

    public HoaDonBanAdmin(int id, int nguoiTao, String tenKH, String diaChi, String sdt, String ngayBan, Long tongTien, String hoTen) {
        this.id = id;
        NguoiTao = nguoiTao;
        TenKH = tenKH;
        DiaChi = diaChi;
        Sdt = sdt;
        NgayBan = ngayBan;
        TongTien = tongTien;
        HoTen = hoTen;
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

    public String getTenKH() {
        return TenKH;
    }

    public void setTenKH(String tenKH) {
        TenKH = tenKH;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getSdt() {
        return Sdt;
    }

    public void setSdt(String sdt) {
        Sdt = sdt;
    }

    public String getNgayBan() {
        return NgayBan;
    }

    public void setNgayBan(String ngayBan) {
        NgayBan = ngayBan;
    }

    public Long getTongTien() {
        return TongTien;
    }

    public void setTongTien(Long tongTien) {
        TongTien = tongTien;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }
}
