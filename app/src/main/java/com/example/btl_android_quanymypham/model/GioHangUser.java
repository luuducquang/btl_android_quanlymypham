package com.example.btl_android_quanymypham.model;

import java.io.Serializable;

public class GioHangUser implements Serializable {
    private int id;
    private int mamp;
    private int nguoitao;
    private int soluong;
    private Long gia;
    private byte[] anh;
    private String tenmp;
    private String hoten;

    public GioHangUser(int id, int mamp, int nguoitao, int soluong, Long gia, byte[] anh, String tenmp, String hoten) {
        this.id = id;
        this.mamp = mamp;
        this.nguoitao = nguoitao;
        this.soluong = soluong;
        this.gia = gia;
        this.anh = anh;
        this.tenmp = tenmp;
        this.hoten = hoten;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMamp() {
        return mamp;
    }

    public void setMamp(int mamp) {
        this.mamp = mamp;
    }

    public int getNguoitao() {
        return nguoitao;
    }

    public void setNguoitao(int nguoitao) {
        this.nguoitao = nguoitao;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public Long getGia() {
        return gia;
    }

    public void setGia(Long gia) {
        this.gia = gia;
    }

    public byte[] getAnh() {
        return anh;
    }

    public void setAnh(byte[] anh) {
        this.anh = anh;
    }

    public String getTenmp() {
        return tenmp;
    }

    public void setTenmp(String tenmp) {
        this.tenmp = tenmp;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }
}
