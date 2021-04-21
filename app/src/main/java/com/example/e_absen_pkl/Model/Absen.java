package com.example.e_absen_pkl.Model;

public class Absen {
    String id_absen,tanggal_absen,jam,status,SIA,laporan,lokasi,keterangan,id,id_siswa;

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getId_absen() {
        return id_absen;
    }

    public void setId_absen(String id_absen) {
        this.id_absen = id_absen;
    }

    public String getTanggal_absen() {
        return tanggal_absen;
    }

    public void setTanggal_absen(String tanggal_absen) {
        this.tanggal_absen = tanggal_absen;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSIA(String SIA) {
        this.SIA = SIA;
    }

    public String getSIA() {
        return SIA;
    }

    public String getLaporan() {
        return laporan;
    }

    public void setLaporan(String laporan) {
        this.laporan = laporan;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setId_siswa(String id_siswa) {
        this.id_siswa = id_siswa;
    }

    public String getId_siswa() {
        return id_siswa;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
