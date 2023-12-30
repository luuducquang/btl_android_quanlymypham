package com.example.btl_android_quanymypham.model;

import java.io.Serializable;

public class LoaiMyPhamAdmin implements Serializable {
    private int id;
    private String tenloai;
    private String mota;

    public LoaiMyPhamAdmin(int id, String tenloai, String mota) {
        this.id = id;
        this.tenloai = tenloai;
        this.mota = mota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }
}
