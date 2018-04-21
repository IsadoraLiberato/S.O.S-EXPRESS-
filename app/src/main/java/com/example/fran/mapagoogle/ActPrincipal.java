package com.example.fran.mapagoogle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class ActPrincipal extends AppCompatActivity {

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
                Intent intent = new Intent(ActPrincipal.this, Login.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
