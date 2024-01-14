package com.example.btl_android_quanymypham.model;

import java.io.Serializable;

public class ChiTietHoaDonBanAdmin implements Serializable {
    private int id;
    private int MaHDB;
    private int MaMP;
    private int SoLuong;
    private Long DonGia;
    private Long TongTien;
    private String TenMP;
    private byte[] HinhAnh;

    public ChiTietHoaDonBanAdmin(int id, int MaHDB, int maMP, int soLuong, Long donGia, Long tongTien, String tenMP, byte[] hinhAnh) {
        this.id = id;
        MaHDB = MaHDB;
        MaMP = maMP;
        SoLuong = soLuong;
        DonGia = donGia;
        TongTien = tongTien;
        TenMP = tenMP;
        HinhAnh = hinhAnh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaHDN() {
        return MaHDB;
    }

    public void setMaHDN(int maHDN) {
        MaHDB = MaHDB;
    }

    public int getMaMP() {
        return MaMP;
    }

    public void setMaMP(int maMP) {
        MaMP = maMP;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public Long getDonGia() {
        return DonGia;
    }

    public void setDonGia(Long donGia) {
        DonGia = donGia;
    }

    public Long getTongTien() {
        return TongTien;
    }

    public void setTongTien(Long tongTien) {
        TongTien = tongTien;
    }

    public String getTenMP() {
        return TenMP;
    }

    public void setTenMP(String tenMP) {
        TenMP = tenMP;
    }

    public byte[] getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        HinhAnh = hinhAnh;
    }
}
