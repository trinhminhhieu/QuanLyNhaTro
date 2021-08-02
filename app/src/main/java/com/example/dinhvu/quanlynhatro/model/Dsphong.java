package com.example.dinhvu.quanlynhatro.model;

import java.io.Serializable;

/**
 * Created by DINHVU on 7/26/2017.
 */

public class Dsphong implements Serializable {
    String sophong,loaiphong,vitriphong,chutro;
    double giaphong;
    int songuoi,idphong;

    public Dsphong() {
    }

    public Dsphong(String sophong, String loaiphong, String vitriphong, String chutro, double giaphong) {
        this.sophong = sophong;
        this.loaiphong = loaiphong;
        this.vitriphong = vitriphong;
        this.chutro = chutro;
        this.giaphong = giaphong;
    }

    public Dsphong(String sophong, String loaiphong, String vitriphong, String chutro, double giaphong, int idphong) {
        this.sophong = sophong;
        this.loaiphong = loaiphong;
        this.vitriphong = vitriphong;
        this.chutro = chutro;
        this.giaphong = giaphong;
        this.idphong = idphong;
    }

    public int getIdphong() {
        return idphong;
    }

    public void setIdphong(int idphong) {
        this.idphong = idphong;
    }

    public String getLoaiphong() {
        return loaiphong;
    }

    public void setLoaiphong(String loaiphong) {
        this.loaiphong = loaiphong;
    }

    public String getVitriphong() {
        return vitriphong;
    }

    public void setVitriphong(String vitriphong) {
        this.vitriphong = vitriphong;
    }

    public String getChutro() {
        return chutro;
    }

    public void setChutro(String chutro) {
        this.chutro = chutro;
    }

    public double getGiaphong() {
        return giaphong;
    }

    public void setGiaphong(double giaphong) {
        this.giaphong = giaphong;
    }

    public String getSophong() {
        return sophong;
    }

    public void setSophong(String sophong) {
        this.sophong = sophong;
    }

    public int getSonguoi() {
        return songuoi;
    }

    public void setSonguoi(int songuoi) {
        this.songuoi = songuoi;
    }
}
