package com.example.fran.mapagoogle;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.EditText;
import android.widget.Toast;

import com.example.fran.mapagoogle.util.Preferences;



public class ActPrincipal extends AppCompatActivity {
    private final int MODE = 0;
    private final String NOME_ARQUIVO = "sos.preferences";

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_principal);


        fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.container, new MapaUsuario(), "MapaUsuario");
        transaction.commitAllowingStateLoss();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
      getMenuInflater().inflate(R.menu.act_principal,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.sair:
                SharedPreferences.Editor editor = getSharedPreferences(NOME_ARQUIVO,MODE).edit();
                editor.remove("email_cliente");
                editor.remove("senha_cliente");
                editor.commit();
                finish();
                Intent intent = new Intent(ActPrincipal.this, Login_Oficina.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }



}
