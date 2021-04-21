package com.example.e_absen_pkl;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.e_absen_pkl.Adapter.AbsensiAdapter;
import com.example.e_absen_pkl.Model.Absen;
import com.example.e_absen_pkl.network.Api;
import com.example.e_absen_pkl.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 2/28/2017.
 */

public class Tab1Fragment extends Fragment {
    private static final String TAG = "Tab1Fragment";

   ListView listallabsen;
   Call<List<Absen>> call;
   AbsensiAdapter adapter;
   Api api;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment,container,false);
        listallabsen = view.findViewById(R.id.listallabsen);
        api = RetrofitClient.createService(Api.class);
        call = api.history(getArguments().getString("data"), getActivity().getIntent().getStringExtra("id_siswa"));
        call.enqueue(new Callback<List<Absen>>() {
            @Override
            public void onResponse(Call<List<Absen>> call, Response<List<Absen>> response) {
                if (response.body().size() == 0){
                    Toast.makeText(getActivity(), "Data tidak ada", Toast.LENGTH_LONG).show();
                }else{
                    isiData(response.body());
                }
                Log.w(TAG, response.body().size() + "");

            }

            @Override
            public void onFailure(Call<List<Absen>> call, Throwable t) {
                Log.w(TAG, t.getMessage() + "");
            }
        });


        return view;
    }
    public void isiData(List<Absen> absen){
        adapter = new AbsensiAdapter(getActivity(), absen);
        listallabsen.setAdapter(adapter);
    }
}
