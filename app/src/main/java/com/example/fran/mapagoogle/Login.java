package com.example.fran.mapagoogle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {

    private Button btn_acessar;
    private Button btn_cadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_acessar =  findViewById(R.id.btn_acessar);
        btn_cadastro =  findViewById(R.id.btn_cadastro);

    }

    public void fazerLogin(View view){
        telamapa();
    }

    public void telaOpcao(View view){
        telaEscolhaCadastro();
    }

    private void telaEscolhaCadastro(){
        Intent intent = new Intent(Login.this, CadastroOficina.class);
        startActivity(intent);
    }

    private void telamapa (){
        Intent intent = new Intent(Login.this, ActPrincipal.class);
        startActivity(intent);
    }
}
