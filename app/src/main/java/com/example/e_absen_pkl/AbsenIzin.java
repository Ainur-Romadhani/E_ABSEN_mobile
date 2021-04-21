package com.example.e_absen_pkl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class AbsenIzin extends AppCompatActivity {
    Button sbmt;
    private String KEY_LOKASI = "LOKASI";
    private String lokasi;
    TextView izid_siswa,izid,iznis,izinis,izlokasii;
    String sia[]={"Pilih","Sakit","Izin"};
    EditText ket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absen_izin);

        TextView back = findViewById(R.id.back);
        izid = findViewById(R.id.izid);
        izid_siswa = findViewById(R.id.izid_siswa);
        izinis = findViewById(R.id.izinis);
        iznis = findViewById(R.id.iznis);
        izlokasii = findViewById(R.id.izlokasii);
        ket = findViewById(R.id.keterangan);
        sbmt = findViewById(R.id.submit);

        Bundle extras = getIntent().getExtras();
        lokasi =extras.getString(KEY_LOKASI);
        izlokasii.setText(lokasi);
        iznis.setText(getIntent().getStringExtra("nis"));

        Spinner spinner = (Spinner) findViewById(R.id.pilihan);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,    android.R.layout.simple_spinner_item,sia);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        sbmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AbsenIzin.this,QrIzin.class);
                i.putExtra(KEY_LOKASI,lokasi);
                i.putExtra("nis", getIntent().getStringExtra("nis"));
                i.putExtra("getdata",spinner.getSelectedItem().toString());
                i.putExtra("keterangan",ket.getText().toString());
                startActivity(i);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
