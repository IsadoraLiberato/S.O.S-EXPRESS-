package com.example.fran.mapagoogle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AcaoUsuario extends AppCompatActivity {

    private Button btn_opcao_cadastro;
    private Button btn_opcao_login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_tela_acao);

        btn_opcao_cadastro = findViewById(R.id.btn_opcao_cadastro);
        btn_opcao_login = findViewById(R.id.btn_opcao_login);


    }

    public void opcaoLogin(View view){
        Intent i = new Intent(AcaoUsuario.this, OpcaoLogin.class);
        startActivity(i);
        finish();
    }

    public void opcaoCadastro(View view){
        Intent i = new Intent(AcaoUsuario.this, OpcaoCadastro.class);
        startActivity(i);
        finish();
    }
}
