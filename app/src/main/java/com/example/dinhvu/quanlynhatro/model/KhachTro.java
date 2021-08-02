package com.example.dinhvu.quanlynhatro.model;

import java.io.Serializable;

/**
 * Created by DINHVU on 7/26/2017.
 */

public class KhachTro implements Serializable {
    String hoten,sodienthoai,diachi,email,sophong;
    int id,idphong;
    String ngaythue;

    public String getNgaythue() {
        return ngaythue;
    }

    public void setNgaythue(String ngaythue) {
        this.ngaythue = ngaythue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public KhachTro(String hoten, String sodienthoai, String diachi, String email,int id) {
        this.hoten = hoten;
        this.sodienthoai = sodienthoai;
        this.diachi = diachi;
        this.email = email;
        this.id=id;
    }

    public int getIdphong() {
        return idphong;
    }

    public void setIdphong(int idphong) {
        this.idphong = idphong;
    }

    public KhachTro() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSophong() {
        return sophong;
    }

    public void setSophong(String sophong) {
        this.sophong = sophong;
    }


}
