package com.example.e_absen_pkl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_absen_pkl.Model.Siswa;
import com.example.e_absen_pkl.network.Api;
import com.example.e_absen_pkl.network.RetrofitClient;
import com.tapadoo.alerter.Alerter;

import java.util.List;
import java.util.prefs.Preferences;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
 Button login;
 EditText nis;
 Call<List<Siswa>> call;
 Api api;


//    https://gist.github.com/fryctze
 SessionManager sessionManager;

 private static final String TAG ="Login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button)findViewById(R.id.cirLoginButton);
        api = RetrofitClient.createService(Api.class);
        nis = (EditText)findViewById(R.id.nis);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Inis = nis.getText().toString();
                call= api.login(Inis);
                call.enqueue(new Callback<List<Siswa>>() {
                    @Override
                    public void onResponse(Call<List<Siswa>> call, Response<List<Siswa>> response) {
                        if(response.isSuccessful()){
                            Intent i = new Intent(Login.this,MainActivity.class);
                            i.putExtra("nis",response.body().get(0).getNis());
//                            i.putExtra("nis",nis.getText().toString());   i.putExtra()
                            i.putExtra("id_siswa",response.body().get(0).getId_siswa());
//                            sessionManager.setLoggedInStatus(getBaseContext(),true);
                            startActivity(i);
                            finish();
                            Log.w(TAG,""+response.body());
                        }
                        else {
                            if (response.code() == 401){
//                                Toast.makeText(Login.this,"Salah GOBLOK !!!",Toast.LENGTH_LONG).show();
//                                new SweetAlertDialog(Login.this)
//                                        .setTitleText("NIS Tidak Terdaftar!")
//                                        .setContentText("Silahkan Hubungi Pembimbing DU/DI")
//                                        .show();
                                Alerter.create(Login.this)
                                        .setTitle("NIS Tidak Terdaftar!")
                                        .setText("Silahkan Hubungi Pembimbing DU/DI")
                                        .setBackgroundColor(R.color.dot_inactive_screen1)
                                        .setIcon(R.drawable.ic_notifications)
                                        .setDuration(5000)
                                        .show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Siswa>> call, Throwable t) {
//                        new SweetAlertDialog(Login.this, SweetAlertDialog.ERROR_TYPE)
//                                .setTitleText("Oops...")
//                                .setContentText("Anda tidak terhubung dengan server!")
//                                .show();
                        Alerter.create(Login.this)
                                .setTitle("Oops")
                                .setText(t.getMessage())
                                .setBackgroundColor(R.color.red)
                                .setIcon(R.drawable.ic_close_black_24dp)
                                .setDuration(5000)
                                .show();
                    }
                });
            }

        });
//        @Override
//        protected void onStart() {
//            super.onStart();
//            if (sessionManager.getLoggedInStatus(getBaseContext())){
//                startActivity(new Intent(getBaseContext(),MainActivity.class));
//                finish();
//            }
        if (sessionManager.getLoggedInStatus(getBaseContext())){
                startActivity(new Intent(getBaseContext(),MainActivity.class));
                finish();
            }

    }
}
