package com.devteam.mobile.simpegrri.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.devteam.mobile.simpegrri.R;
import com.devteam.mobile.simpegrri.data.adapter.AdapterTerkini;
import com.devteam.mobile.simpegrri.utils.Constants;
import com.devteam.mobile.simpegrri.utils.Pustaka;
import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentDatauser extends Fragment {

    TextView pgNama,pgNip,pgTglahir,pgTmplahir,pgAlamat,pgSatker,pgGolruang,pgKarpeg;
    RecyclerView rv;
    ImageView pgfoto ;
    GridLayoutManager mLayoutManager;
    AdapterTerkini adapter;
    SharedPreferences shared;
    SharedPreferences.Editor editor;
    Pustaka pustaka = new Pustaka();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.datauser_layout,container,false);

        shared = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = shared.edit();
        pgNama = v.findViewById(R.id.namaPegawai);
        pgNip = v.findViewById(R.id.idPegawai);
        pgTglahir = v.findViewById(R.id.tgLahir);
        pgTmplahir = v.findViewById(R.id.tmLahir);
        pgAlamat = v.findViewById(R.id.alamatPegawai);
        pgSatker = v.findViewById(R.id.satker);
        pgGolruang = v.findViewById(R.id.golRuang);
        pgKarpeg = v.findViewById(R.id.no_karpeg);
        pgfoto = v.findViewById(R.id.fotoPegawai);
        rv = v.findViewById(R.id.menuterkini);
        rv.setHasFixedSize(true);
        rv.setFocusable(false);
        mLayoutManager = new GridLayoutManager(getActivity(),2);
        rv.setLayoutManager(mLayoutManager);
        String[] menuTerkini = getActivity().getResources().getStringArray(R.array.data_terkini);
        adapter = new AdapterTerkini(menuTerkini,getActivity());
        rv.setAdapter(adapter);

        pgNama.setText(shared.getString(Constants.PREF_NAMA,""));
        pgNip.setText(shared.getString(Constants.PREF_NIP,""));
        pgAlamat.setText(shared.getString(Constants.PREF_ALAMAT,""));
        pgTglahir.setText(shared.getString(Constants.PREF_TGLAHIR,""));
        pgTmplahir.setText(shared.getString(Constants.PREF_TMPLAHIR,""));
        pgSatker.setText(shared.getString(Constants.PREF_DEPT,""));
        pgGolruang.setText(shared.getString(Constants.PREF_GOL,""));
        pgKarpeg.setText(shared.getString(Constants.PREF_KARPEG,""));
        Glide.with(getActivity())
                .load(shared.getString(Constants.PREF_PHOTO,""))
                .thumbnail(0.5f)
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.placeholderOf(R.drawable.no_photo))
                .into(pgfoto);

//        getData();

        return v;
    }

//    private void getData(){
//        JSONObject body = new JSONObject();
//        try {
//            JSONObject parameter = new JSONObject();
//            parameter.accumulate("api_key",shared.getString(Constants.PREF_API_KEY,""));
//            JSONObject param = new JSONObject(parameter.toString());
//
//            body.accumulate("jsonprc",2);
//            body.accumulate("method","GET");
//            body.accumulate("object","login");
//            body.accumulate("filter",1);
//            body.accumulate("param","param");
//
//            Log.i("FragmentDataUser","param : "+param);
//
//            AndroidNetworking.post(Api.BASE_URL)
//                    .addJSONObjectBody(body)
//                    .setTag("Data User")
//                    .setPriority(Priority.MEDIUM)
//                    .build()
//                    .getAsJSONObject(new JSONObjectRequestListener() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            Log.i("FragmentDatauser","response : "+response.toString());
//
//                            try {
//                                JSONObject hasil = response.getJSONObject("result").getJSONObject("data");
//                                Log.i("FragmentDatauser","Nip : "+hasil.getJSONObject("data").getString("nip"));
//
//                                pgNama.setText(Constants.PREF_NAMA);
//                                Toast.makeText(getActivity(), hasil.getJSONObject("data").getString("nama"), Toast.LENGTH_SHORT).show();
//                                pgNip.setText(Constants.PREF_NIP);
//                                pgTmplahir.setText(hasil.getString("tgl_lahir"));
//                                pgTglahir.setText(hasil.getJSONObject("data").getString("tempat_lahir"));
//                                pgAlamat.setText(hasil.getJSONObject("data").getString("alamat"));
//                                pgSatker.setText(hasil.getJSONObject("data").getString("satker_nama"));
//                                pgGolruang.setText(hasil.getJSONObject("data").getString("pangkat_nama"));
//                                pgKarpeg.setText(hasil.getJSONObject("data").getString("karpeg"));
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        @Override
//                        public void onError(ANError anError) {
//
//                        }
//                    });
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

}
