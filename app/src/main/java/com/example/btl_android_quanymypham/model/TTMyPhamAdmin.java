package com.example.btl_android_quanymypham.model;

import java.io.Serializable;

public class TTMyPhamAdmin implements Serializable {
    private int id;
    private String tenmypham;
    private String dungtich;
    private int loaimypham;
    private byte[] anhsanpham;
    private Long gia;
    private String mota;
    private String chitiet;
    private String tenloaimypham;

    public TTMyPhamAdmin(int id, String tenmypham, String dungtich, int loaimypham, byte[] anhsanpham, Long gia, String mota, String chitiet, String tenloaimypham) {
        this.id = id;
        this.tenmypham = tenmypham;
        this.dungtich = dungtich;
        this.loaimypham = loaimypham;
        this.anhsanpham = anhsanpham;
        this.gia = gia;
        this.mota = mota;
        this.chitiet = chitiet;
        this.tenloaimypham = tenloaimypham;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenmypham() {
        return tenmypham;
    }

    public void setTenmypham(String tenmypham) {
        this.tenmypham = tenmypham;
    }

    public String getDungtich() {
        return dungtich;
    }

    public void setDungtich(String dungtich) {
        this.dungtich = dungtich;
    }

    public int getLoaimypham() {
        return loaimypham;
    }

    public void setLoaimypham(int loaimypham) {
        this.loaimypham = loaimypham;
    }

    public byte[] getAnhsanpham() {
        return anhsanpham;
    }

    public void setAnhsanpham(byte[] anhsanpham) {
        this.anhsanpham = anhsanpham;
    }

    public Long getGia() {
        return gia;
    }

    public void setGia(Long gia) {
        this.gia = gia;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getChitiet() {
        return chitiet;
    }

    public void setChitiet(String chitiet) {
        this.chitiet = chitiet;
    }

    public String getTenloaimypham() {
        return tenloaimypham;
    }

    public void setTenloaimypham(String tenloaimypham) {
        this.tenloaimypham = tenloaimypham;
    }
}
