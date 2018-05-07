package com.example.fran.mapagoogle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OpcaoCadastro extends AppCompatActivity {

    private Button btn_ficina;
    private Button btn_usu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opcao_cadastro);


        btn_ficina =  findViewById(R.id.btn_oficina);
        btn_usu = findViewById(R.id.btn_usuario);
    }

    public void telaCadOficina(View view){
        Intent intent = new Intent(OpcaoCadastro.this, CdsOficina.class);
        startActivity(intent);
    }

    public void telaCadUsuario(View view){
        Intent intent = new Intent(OpcaoCadastro.this, CdsUsuario.class);
        startActivity(intent);
    }

}


