package com.example.e_absen_pkl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_absen_pkl.Model.Siswa;
import com.example.e_absen_pkl.network.Api;
import com.example.e_absen_pkl.network.RetrofitClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;
import com.tapadoo.alerter.Alerter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Call<List<Siswa>> call;
    Api api;
    private static final String TAG = "MainActivity";
    private  String KEY_LOKASI = "LOKASI";
    private LocationManager locManager;
    private LocationListener locListener;
    private SwipeRefreshLayout refresh;

    ImageButton history,izin,absen,logout;
    SessionManager sessionManager;
    TextView nama,kelas,jurusan,nis,TextView5;
    FusedLocationProviderClient fusedLocationProviderClient;
    CircleImageView profile;
    String lokasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout = findViewById(R.id.logout);
        history = findViewById(R.id.history);
        izin =findViewById(R.id.izin);
        absen = findViewById(R.id.absen);
        nama = findViewById(R.id.nama);
        nis=findViewById(R.id.setnis);
        kelas =findViewById(R.id.setkelas);
        jurusan = findViewById(R.id.setjurusan);
     TextView setlokasi = findViewById(R.id.btnsetlokasi);
        TextView5 = findViewById(R.id.TextView5);

        profile = findViewById(R.id.profile);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        api = RetrofitClient.createService(Api.class);
        call = api.datasiswa(getIntent().getStringExtra("nis"));
        call.enqueue(new Callback<List<Siswa>>() {
            @Override
            public void onResponse(Call<List<Siswa>> call, Response<List<Siswa>> response) {
                nama.setText(response.body().get(0).getNama());
                nis.setText(response.body().get(0).getNis());
                kelas.setText(response.body().get(0).getKelas());
                jurusan.setText(response.body().get(0).getJurusan());
                if (response.body().get(0).getFoto() != null && response.body().get(0).getFoto().length() > 0 ){
                    Picasso.get().load("http://192.168.43.229:8000/fotosiswa/" +
                            response.body().get(0).getFoto()).placeholder(R.drawable.profile).into(profile);
                }else{
                    Picasso.get().load(R.drawable.profile).into(profile);
                }
                Alerter.create(MainActivity.this)
                                    .setTitle("Berhasil Login !")
                                    .setText("Selamat datang")
                                    .setBackgroundColor(R.color.dot_inactive_screen2)
                                    .setIcon(R.drawable.ic_face)
                                    .setDuration(3000)
                                    .show();
                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Location")
                        .setContentText("Refresh lokasi anda")
                        .setConfirmText("Yes!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                if (ActivityCompat.checkSelfPermission(MainActivity.this
                                        ,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                                    getLocation();
                                }else{
                                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                                }
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        }).setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                }).show();
            }

            @Override
            public void onFailure(Call<List<Siswa>> call, Throwable t) {
                Log.w(TAG, "onresponse" + t.getMessage());
            }
        });
        setlokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this
                ,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    getLocation();
                }else{
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                }
            }
        });

        absen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,AbsenHadir.class);
                i.putExtra("nis", getIntent().getStringExtra("nis"));
                i.putExtra(KEY_LOKASI,lokasi);
                startActivity(i);
            }
        });
        izin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,AbsenIzin.class);
                i.putExtra("nis", getIntent().getStringExtra("nis"));
                i.putExtra(KEY_LOKASI,lokasi);
                startActivity(i);
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,History.class);
                i.putExtra("id_siswa", getIntent().getStringExtra("id_siswa"));
                startActivity(i);

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Keluar Dari Aplikasi ??")
                .setConfirmText("Yes!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
            sDialog.dismissWithAnimation();
//            sessionManager.clearLoggedInUser(getBaseContext());
            startActivity(new Intent(getBaseContext(),Login.class));
            finish();
        }
            }).setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                        .show();
            }
        });
    }

    private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
           Location location = task.getResult();
           if (location != null){
               try {
                   Geocoder geocoder = new Geocoder(MainActivity.this,
                           Locale.getDefault());
                   List<Address> addresses = geocoder.getFromLocation(
                           location.getLatitude(),location.getLongitude(),1
                   );
                   TextView5.setText(
                                   addresses.get(0).getAddressLine(0)
                   );
                   lokasi = addresses.get(0).getAddressLine(0);
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
            }
        });
    }

}
