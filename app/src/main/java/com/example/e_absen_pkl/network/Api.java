package com.example.e_absen_pkl.network;

import com.example.e_absen_pkl.Model.Absen;
import com.example.e_absen_pkl.Model.Siswa;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {
    @FormUrlEncoded
    @POST("loginsiswa")
    Call<List<Siswa>>login(@Field("nis")String nis);


    @FormUrlEncoded
    @POST("datasiswa")
    Call<List<Siswa>> datasiswa(@Field("id_siswa") String id_siswa);

    @FormUrlEncoded
    @POST("hadir")
    Call<ResponseBody> hadir
            (@Field("lokasi") String lokasi,
             @Field("id_siswa") String id_siswa,
             @Field("id") String id);

    @FormUrlEncoded
    @POST("izinsakit")
    Call<ResponseBody> izinsakit
            (@Field("SIA") String SIA,
            @Field("keterangan") String keterangan,
            @Field("lokasi") String lokasi,
             @Field("id_siswa") String id_siswa,
             @Field("id") String id);

    @GET("history/{id}/{siswa}")
    Call<List<Absen>>history(@Path("id") String id, @Path("siswa") String siswa);
}
