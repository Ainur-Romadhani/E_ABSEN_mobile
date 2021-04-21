package com.example.e_absen_pkl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.e_absen_pkl.Model.Siswa;
import com.example.e_absen_pkl.network.Api;
import com.example.e_absen_pkl.network.RetrofitClient;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.tapadoo.alerter.Alerter;

import org.w3c.dom.Text;

import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AbsenHadir extends AppCompatActivity {
    private static final String TAG = "AbsenHadir";
    private ImageView ivBgContent;
    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;
    private String KEY_LOKASI = "LOKASI";
    private String lokasi;
    Api api;
    TextView id_siswa,nis;
    Call<ResponseBody> call;
    Call<List<Siswa>>calll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absen_hadir);

        ivBgContent = findViewById(R.id.ivBgContent);
        scannerView = findViewById(R.id.scannerView);
        id_siswa = findViewById(R.id.id_siswa);
        nis = findViewById(R.id.nis);
        TextView lokasii = findViewById(R.id.lokasii);
        TextView id = findViewById(R.id.id);
        TextView inis = findViewById(R.id.inis);
        TextView back = findViewById(R.id.absenback);

        Bundle extras = getIntent().getExtras();
        lokasi =extras.getString(KEY_LOKASI);
        lokasii.setText(lokasi);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        nis.setText(getIntent().getStringExtra("nis"));
        api = RetrofitClient.createService(Api.class);
        calll = api.datasiswa(getIntent().getStringExtra("nis"));
        calll.enqueue(new Callback<List<Siswa>>() {
            @Override
            public void onResponse(Call<List<Siswa>> call, Response<List<Siswa>> response) {
                id_siswa.setText(response.body().get(0).getId_siswa());
                id.setText(response.body().get(0).getPembimbing_magang());
                inis.setText(response.body().get(0).getNis());

            }

            @Override
            public void onFailure(Call<List<Siswa>> call, Throwable t) {

            }
        });
        ivBgContent.bringToFront();
        mCodeScanner = new CodeScanner(this,scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(result.getText().equals(inis.getText()) ){
                            try {
                                String alamat = lokasii.getText().toString();
                                call = api.hadir(alamat,id_siswa.getText().toString(),id.getText().toString());
                                call.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if(response.isSuccessful()){
                                            Alerter.create(AbsenHadir.this)
                                                    .setTitle("Berhasil Absen")
                                                    .setText("Silahkan Cek History")
                                                    .setBackgroundColor(R.color.dot_inactive_screen2)
                                                    .setIcon(R.drawable.ic_check_box_black_24dp)
                                                    .setDuration(3000)
                                                    .show();
                                        }
                                        else {
                                          if (response.code() == 401){
                                              Alerter.create(AbsenHadir.this)
                                                      .setTitle("Gagal Absen")
                                                      .setText("Anda sudah absen ,silahkan absen kembali besok !!")
                                                      .setBackgroundColor(R.color.dot_active_screen1)
                                                      .setIcon(R.drawable.ic_close_black_24dp)
                                                      .setDuration(3000)
                                                      .show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                                    }
                                });

                             }catch (Exception ex){
                            Log.w(TAG,""+ ex.getMessage());
                        }
                        }else{
                            Alerter.create(AbsenHadir.this)
                                    .setTitle("Gagal Absen")
                                    .setText("QR code tidak terdaftar")
                                    .setBackgroundColor(R.color.dot_active_screen1)
                                    .setIcon(R.drawable.ic_close_black_24dp)
                                    .setDuration(3000)
                                    .show();
                        }
                    }
                });
            }
        });
        checkCameraPermission();
    }

    protected void onResume(){
        super.onResume();
        checkCameraPermission();
    }

    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    private void checkCameraPermission(){
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mCodeScanner.startPreview();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission,
                                                                   PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .check();
    }

}