package com.example.fran.mapagoogle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OpcaodeLogin extends AppCompatActivity {

    Button btn_ficina;
    Button btn_usu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcao_de_login);

        btn_ficina =  findViewById(R.id.btn_oficina);
        btn_usu = findViewById(R.id.btn_usuario);

        btn_ficina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telaloginoficina();
            }
        });

        btn_usu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telaloginusuario();
            }
        });
    }

    private void telaloginusuario() {
        Intent intent = new Intent(OpcaodeLogin.this, LoginCliente.class);
        startActivity(intent);
    }

    private void telaloginoficina() {
        Intent intent = new Intent(OpcaodeLogin.this, LoginOficina.class);
        startActivity(intent);
    }




}


