package com.example.e_absen_pkl.Model;

public class Siswa {

    String id_siswa,nis,nama,alamat,pembimbing_sekolah,pembimbing_magang,kelas,
            jurusan,tempat_pkl,foto;

    public String getId_siswa() {
        return id_siswa;
    }

    public void setId_siswa(String id_siswa) {
        this.id_siswa = id_siswa;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getPembimbing_magang() {
        return pembimbing_magang;
    }

    public void setPembimbing_magang(String pembimbing_magang) {
        this.pembimbing_magang = pembimbing_magang;
    }

    public String getPembimbing_sekolah() {
        return pembimbing_sekolah;
    }

    public void setPembimbing_sekolah(String pembimbing_sekolah) {
        this.pembimbing_sekolah = pembimbing_sekolah;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getTempat_pkl() {
        return tempat_pkl;
    }

    public void setTempat_pkl(String tempat_pkl) {
        this.tempat_pkl = tempat_pkl;
    }
}
