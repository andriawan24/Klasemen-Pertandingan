package com.example.klasemensepakbola.model;

public class Klub {

    String namaKlub, kotaKlub;
    Integer jumlahMain;
    Integer jumlahMenang;
    Integer jumlahSeri;
    Integer jumlahKalah;
    Integer jumlahPoin;
    Integer jumlahGoalMenang;
    Integer jumlahGoalKalah;

    public Klub(String namaKlub, String kotaKlub, Integer jumlahMain, Integer jumlahMenang, Integer jumlahSeri, Integer jumlahKalah, Integer jumlahPoin, Integer jumlahGoalMenang, Integer jumlahGoalKalah) {
        this.namaKlub = namaKlub;
        this.kotaKlub = kotaKlub;
        this.jumlahMain = jumlahMain;
        this.jumlahMenang = jumlahMenang;
        this.jumlahSeri = jumlahSeri;
        this.jumlahKalah = jumlahKalah;
        this.jumlahPoin = jumlahPoin;
        this.jumlahGoalMenang = jumlahGoalMenang;
        this.jumlahGoalKalah = jumlahGoalKalah;
    }

    public Integer getJumlahGoalMenang() {
        return jumlahGoalMenang;
    }

    public void setJumlahGoalMenang(Integer jumlahGoalMenang) {
        this.jumlahGoalMenang = jumlahGoalMenang;
    }

    public Integer getJumlahGoalKalah() {
        return jumlahGoalKalah;
    }

    public void setJumlahGoalKalah(Integer jumlahGoalKalah) {
        this.jumlahGoalKalah = jumlahGoalKalah;
    }

    public String getNamaKlub() {
        return namaKlub;
    }

    public void setNamaKlub(String namaKlub) {
        this.namaKlub = namaKlub;
    }

    public String getKotaKlub() {
        return kotaKlub;
    }

    public void setKotaKlub(String kotaKlub) {
        this.kotaKlub = kotaKlub;
    }

    public Integer getJumlahMain() {
        return jumlahMain;
    }

    public void setJumlahMain(Integer jumlahMain) {
        this.jumlahMain = jumlahMain;
    }

    public Integer getJumlahMenang() {
        return jumlahMenang;
    }

    public void setJumlahMenang(Integer jumlahMenang) {
        this.jumlahMenang = jumlahMenang;
    }

    public Integer getJumlahSeri() {
        return jumlahSeri;
    }

    public void setJumlahSeri(Integer jumlahSeri) {
        this.jumlahSeri = jumlahSeri;
    }

    public Integer getJumlahKalah() {
        return jumlahKalah;
    }

    public void setJumlahKalah(Integer jumlahKalah) {
        this.jumlahKalah = jumlahKalah;
    }

    public Integer getJumlahPoin() {
        return jumlahPoin;
    }

    public void setJumlahPoin(Integer jumlahPoin) {
        this.jumlahPoin = jumlahPoin;
    }
}
