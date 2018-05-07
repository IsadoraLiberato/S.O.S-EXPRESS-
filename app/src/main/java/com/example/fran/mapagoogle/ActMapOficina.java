package com.example.fran.mapagoogle;

import android.content.Intent;
import android.renderscript.Sampler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.MapFragment;

public class ActMapOficina extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private  Fragment myFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_map_oficina);

        //pegar os dados do Adapter
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String lat = bundle.getString("lat");
        String log = bundle.getString("log");
        String nomeUser= bundle.getString("nome");

        //passar dados para o mapsfragment
        MapsFragment mapsFragment = new MapsFragment();
        Bundle data = new Bundle();
        data.putString("lat",lat);
        data.putString("log",log);
        data.putString("nome",nomeUser);
        mapsFragment.setArguments(data);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.containerOficina, mapsFragment, "MapsFragment");
        transaction.commitAllowingStateLoss();
    }
}
