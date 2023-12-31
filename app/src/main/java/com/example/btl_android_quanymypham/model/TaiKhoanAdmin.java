package com.example.btl_android_quanymypham.model;

import java.io.Serializable;

public class TaiKhoanAdmin implements Serializable {

    private int id;
    private String taikhoan;
    private String matkhau;
    private String hoten;
    private String email;
    private byte[] anhdaidien;
    private String quyen;

    public TaiKhoanAdmin(int id, String taikhoan, String matkhau, String hoten, String email, byte[] anhdaidien, String quyen) {
        this.id = id;
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
        this.hoten = hoten;
        this.email = email;
        this.anhdaidien = anhdaidien;
        this.quyen = quyen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getAnhdaidien() {
        return anhdaidien;
    }

    public void setAnhdaidien(byte[] anhdaidien) {
        this.anhdaidien = anhdaidien;
    }

    public String getQuyen() {
        return quyen;
    }

    public void setQuyen(String quyen) {
        this.quyen = quyen;
    }
}
