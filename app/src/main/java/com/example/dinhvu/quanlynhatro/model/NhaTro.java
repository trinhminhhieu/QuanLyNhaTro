package com.example.dinhvu.quanlynhatro.model;

import java.io.Serializable;

/**
 * Created by DINHVU on 7/27/2017.
 */

public class NhaTro implements Serializable {
    String hoten,sodienthoai,diachi,tenxomtro;
    int dongiadien,dongianuoc;

    public NhaTro() {
    }

    public NhaTro(String hoten, String sodienthoai, String diachi, String tenxomtro, int dongiadien, int dongianuoc) {
        this.hoten = hoten;
        this.sodienthoai = sodienthoai;
        this.diachi = diachi;
        this.tenxomtro = tenxomtro;
        this.dongiadien = dongiadien;
        this.dongianuoc = dongianuoc;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getTenxomtro() {
        return tenxomtro;
    }

    public void setTenxomtro(String tenxomtro) {
        this.tenxomtro = tenxomtro;
    }

    public int getDongiadien() {
        return dongiadien;
    }

    public void setDongiadien(int dongiadien) {
        this.dongiadien = dongiadien;
    }

    public int getDongianuoc() {
        return dongianuoc;
    }

    public void setDongianuoc(int dongianuoc) {
        this.dongianuoc = dongianuoc;
    }
}
