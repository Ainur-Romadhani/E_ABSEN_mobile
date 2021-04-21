package com.example.e_absen_pkl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.e_absen_pkl.Model.Absen;
import com.example.e_absen_pkl.R;
import com.example.e_absen_pkl.network.Api;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class AbsensiAdapter extends BaseAdapter {
    Context context;
    List<Absen> list;

    public AbsensiAdapter(Context context,List<Absen>list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(this.context);
            convertView = inflater.inflate(R.layout.listhistory,null);
        }

        Absen absen = list.get(position);
        TextView hisjam = convertView.findViewById(R.id.hisjam);
        TextView histanggal = convertView.findViewById(R.id.histanggal);
        TextView histempat = convertView.findViewById(R.id.histempat);
        TextView hissia = convertView.findViewById(R.id.hissia);
        TextView tolaksetuju = convertView.findViewById(R.id.histolaksetuju);
        ImageView image = convertView.findViewById(R.id.image);

        hisjam.setText(absen.getJam());
        histanggal.setText(absen.getTanggal_absen());
        histempat.setText(absen.getLokasi());
        hissia.setText(absen.getSIA());
        tolaksetuju.setText(absen.getLaporan());

        return convertView;
    }

}
