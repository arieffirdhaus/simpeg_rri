package com.devteam.mobile.simpegrri.data.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.devteam.mobile.simpegrri.R;
import com.devteam.mobile.simpegrri.ui.dataterkini.Keluarga;
import com.devteam.mobile.simpegrri.ui.riwayat.Kepangkatan;
import com.devteam.mobile.simpegrri.utils.Pustaka;


/**
 * Created by Firdhaus on 27/02/2018.
 */

public class AdapterRiwayat extends RecyclerView.Adapter<AdapterRiwayat.ViewHolder> {
    Context mContext;
    private String[] mDataSet;
    Pustaka pustaka = new Pustaka();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.riwayat_item_format,parent,false);
        ViewHolder viewHolder= new ViewHolder(v);
        return viewHolder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textRiwayat;
        public RelativeLayout relatifTampil;

        public ViewHolder(View v){
            super(v);
            textRiwayat = v.findViewById(R.id.textRi);
            relatifTampil = v.findViewById(R.id.relatif_tampilan);
        }
    }
    public AdapterRiwayat(String[] myDataSet, Context mContext){
        mDataSet = myDataSet;
        this.mContext = mContext;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textRiwayat.setText(mDataSet[position]);

        View.OnClickListener klikRiwayat = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewHolder vHolder = (ViewHolder)v.getTag();
                int position = vHolder.getPosition();

                switch (position){
                    case 0 :
                        Intent pindah = new Intent(mContext, Kepangkatan.class);
                        mContext.startActivity(pindah);
                        break;
                    case 1:
//                        pustaka.pindah(mContext,Keluarga.class,false);
//                        break;
//                    case 2:
//                        pustaka.pindah(mContext,PenyesuaianGaji.class,false);
//                        break;
//                    case 3:
//                        pustaka.pindah(mContext,RiwayatJabatan.class,false);
//                        break;
//                    case 4:
//                        pustaka.pindah(mContext,AngkaKredit.class,false);
//                        break;
//                    case 5:
//                        pustaka.pindah(mContext,PendidikanRiwayat.class,false);
//                        break;
//                    case 6:
//                        pustaka.pindah(mContext,DiklatKepem.class,false);
//                        break;
//                    case 7:
//                        pustaka.pindah(mContext,OrangTua.class,false);
//                        break;
//                    case 8:
//                        pustaka.pindah(mContext,IstriSuami.class,false);
//                        break;
//                    case 9:
//                        pustaka.pindah(mContext,Anak.class,false);
//                        break;
//                    case 10:
//                        pustaka.pindah(mContext,Saudara.class,false);
//                        break;
//                    case 11:
//                        pustaka.pindah(mContext,Penghargaan.class,false);
//                        break;
//                    case 12:
//                        pustaka.pindah(mContext,PembinaDisiplin.class,false);
//                        break;
//                    case 13:
//                        pustaka.pindah(mContext,TempatKerja.class,false);
//                        break;
//                    case 14:
//                        pustaka.pindah(mContext,DiklatFungsi.class,false);
//                        break;
//                    case 15:
//                        pustaka.pindah(mContext,DiklatTeknis.class,false);
//                        break;
//                    case 16:
//                        pustaka.pindah(mContext,Penataran.class,false);
//                        break;
//                    case 17:
//                        pustaka.pindah(mContext,SeminarLokarya.class,false);
//                        break;
//                    case 18:
//                        pustaka.pindah(mContext,PenilaianKinerja.class,false);
//                        break;
//                    case 19:
//                        pustaka.pindah(mContext,Organisasi.class,false);
//                        break;
//                    case 20:
//                        pustaka.pindah(mContext,TugasLuarNegeri.class,false);
//                        break;
//                    case 21:
//                        pustaka.pindah(mContext,KuasaBahasa.class,false);
//                        break;
//                    case 22:
//                        pustaka.pindah(mContext,TesPsikologi.class,false);
//                        break;
//                    case 23:
//                        pustaka.pindah(mContext,PelanggaranDisiplin.class,false);
//                        break;
//
                }
//                Intent link = new Intent(mContext,Kepangkatan.class);
//                mContext.startActivity(link);
            }
        };
        holder.relatifTampil.setOnClickListener(klikRiwayat);
        holder.relatifTampil.setTag(holder);
    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
    }
}
