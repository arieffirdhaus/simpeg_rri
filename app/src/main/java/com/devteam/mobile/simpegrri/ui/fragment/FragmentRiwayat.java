package com.devteam.mobile.simpegrri.ui.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.devteam.mobile.simpegrri.R;
import com.devteam.mobile.simpegrri.data.adapter.AdapterRiwayat;

public class FragmentRiwayat extends Fragment {

    @Nullable
    private RecyclerView rv;
    private GridLayoutManager mLayoutManager;
    AdapterRiwayat adapterRiwayat;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View s = inflater.inflate(R.layout.riwayat_layout,container,false);
        rv = s.findViewById(R.id.listRiwayat);
        rv.setHasFixedSize(true);
        rv.setFocusable(false);
        //layout manager
//        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager = new GridLayoutManager(getActivity(),3);
        rv.setLayoutManager(mLayoutManager);
        //adapter
        String[] myDataSet = getActivity().getResources().getStringArray(R.array.riwayat);
        adapterRiwayat = new AdapterRiwayat(myDataSet,getActivity());
        rv.setAdapter(adapterRiwayat);
        return s ;

    }
}
