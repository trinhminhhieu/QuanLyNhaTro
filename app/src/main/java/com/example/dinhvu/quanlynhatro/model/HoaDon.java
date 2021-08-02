package com.example.dinhvu.quanlynhatro.model;

import java.io.Serializable;

/**
 * Created by DINHVU on 7/28/2017.
 */

public class HoaDon implements Serializable{
    String ngaylap,ngaythanhtoan;
    String sophong;
    double tienphong,dongaidien,dongianuoc,internet,dathu,conno;
    int trangthai;
    int sodien,sonuoc,id;

    public HoaDon(double internet, int sodien, int sonuoc) {
        this.internet = internet;
        this.sodien = sodien;
        this.sonuoc = sonuoc;
    }

    public double getDathu() {
        return dathu;
    }

    public void setDathu(double dathu) {
        this.dathu = dathu;
    }

    public double getConno() {
        return conno;
    }

    public void setConno(double conno) {
        this.conno = conno;
    }

    public String getSophong() {
        return sophong;
    }

    public void setSophong(String sophong) {
        this.sophong = sophong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNgaylap() {
        return ngaylap;
    }

    public void setNgaylap(String ngaylap) {
        this.ngaylap = ngaylap;
    }

    public String getNgaythanhtoan() {
        return ngaythanhtoan;
    }

    public void setNgaythanhtoan(String ngaythanhtoan) {
        this.ngaythanhtoan = ngaythanhtoan;
    }

    public double getDongaidien() {
        return dongaidien;
    }

    public void setDongaidien(double dongaidien) {
        this.dongaidien = dongaidien;
    }

    public double getDongianuoc() {
        return dongianuoc;
    }

    public void setDongianuoc(double dongianuoc) {
        this.dongianuoc = dongianuoc;
    }

    public double getInternet() {
        return internet;
    }

    public void setInternet(double internet) {
        this.internet = internet;
    }

    public int getSodien() {
        return sodien;
    }

    public void setSodien(int sodien) {
        this.sodien = sodien;
    }

    public int getSonuoc() {
        return sonuoc;
    }

    public void setSonuoc(int sonuoc) {
        this.sonuoc = sonuoc;
    }


    public double getTienPhong() {
        return tienphong;
    }

    public void setTienPhong(double tien) {
        this.tienphong = tien;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public HoaDon() {

    }
    public double getTongTien(){
        return getTienPhong()+(getSodien()*getDongaidien())+(getSonuoc()*getDongianuoc())+getInternet();
    }
}
